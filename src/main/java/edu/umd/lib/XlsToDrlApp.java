/*
 * This file is licensed under Creative Commons 1.0 Universal License.
 * See the LICENSE.txt accompanying this distribution. 
 */
package edu.umd.lib;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Parameter;
import com.martiansoftware.jsap.SimpleJSAP;
import com.martiansoftware.jsap.UnflaggedOption;

/**
 * Simple application for converting an Excel spreadsheet into a
 * Drools Rules Language (drl) file.
 */
public class XlsToDrlApp
{
  private static final Logger log = LogManager.getLogger(XlsToDrlApp.class);
  
  /**
   * The main method
   * 
   * @param args the command-line arguments
   */
  public static void main( String[] args )
  {
    log.entry(Arrays.deepToString(args));
    
    // Set up the JSAP command-line parser with the possible command-line
    // arguments
    SimpleJSAP jsap = null;
    try {
      jsap = new SimpleJSAP( 
          "XlsToDrlApp", 
          "Converts Excel spreadsheet to Drools rules file",
          new Parameter[] {
              new FlaggedOption( "outfile", JSAP.STRING_PARSER, JSAP.NO_DEFAULT, JSAP.NOT_REQUIRED, 'o', "outfile", 
                  "The filename to output the Drools rules to. If not present, will print rules to standard output." ),
              new UnflaggedOption( "infile", JSAP.STRING_PARSER, JSAP.NO_DEFAULT, JSAP.REQUIRED, JSAP.NOT_GREEDY, 
                  "The filename of the Excel spreadsheet to convert." )
          }
      );
    } catch( JSAPException je ) {
      log.error("An exception occcured parsing the command-line arguments.", je );
      log.exit( "Exiting due to JSAP exception." );
      System.exit(1);
    }
    
    // Parse the command-line arguments
    JSAPResult config = jsap.parse(args);    

    boolean messagePrinted = jsap.messagePrinted();
    log.debug( "JSAP message printed: {}" + messagePrinted );
    if ( messagePrinted )
    {
      // If JSAP printed a message, the command-line is not valid, so exit.
      log.exit("JSAP help or error message was printed");
      System.exit( 1 );
    }
    
    // Retrieve the command-line arguments
    String infileStr = config.getString("infile");
    String outfileStr = config.getString("outfile");
    
    // Process the Excel spreadsheet into a String containing the Drools
    // rules language file for output.
    String droolsString = null;
    try ( InputStream in = new FileInputStream( infileStr ) ) {
      SpreadsheetCompiler sc = new SpreadsheetCompiler();
      droolsString = sc.compile(in, InputType.XLS);
    } catch ( IOException ioe ) {
      log.error("An I/O exception occurred with input file \"" + 
          infileStr + "\"", ioe);
    }
    
    // Default to standard out for output
    PrintStream out = System.out;
    try {
      if ( outfileStr != null )
      {
        // But if an output file has been specified, send output to that file.
        out = new PrintStream( new FileOutputStream( outfileStr ) );
        log.debug( "Printing to file " + outfileStr );
      }
      
      out.println(droolsString);
    } catch( FileNotFoundException fnfe ) {
      log.error("An exception occurred opening the output file \"" + 
          outfileStr + "\"", fnfe);
    } finally {
      // If not using standard out, make sure output stream is closed.
      if ( out != System.out )
      {
        log.debug( "Closing file output stream." );
        out.close();
      }
    }
    
    log.exit();
  }
}