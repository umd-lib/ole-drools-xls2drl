# xsl2drl

Simple Java application for converting Excel spreadsheet into a Drools DRL file

## Requirements

* Java 1.7
* Gradle 2.6

## Build/Setup

1) Install the required software

2) Clone this repository

3) In a terminal/command prompt switch to the "xsl2drl" and run the following Gradle command:

```
gradle distZip
```

This will create a "xls2drl-1.0-SNAPSHOT.zip" file in the build/distributions/ subdirectory. Copy the zip file to a convenient location. and unzip the file. This will create a "xls2drl-1.0-SNAPSHOT" directory. Switch to the xls2drl-1.0-SNAPSHOT/bin/ subdirectory and run the "xls2drl" script (on Linux/Mac OSX) or the "xls2drl.bat" script (on Windows).

## Usage

The xls2drl/xls2drl.bat script takes the following arguments:

```
  [--help]
        Prints this help message.

  [(-o|--outfile) <outfile>]
        The filename to output the Drools rules to. If not present, will print
        rules to standard output.

  <infile>
        The filename of the Excel spreadsheet to convert.
```

The -o/--outfile argument is optional.

## License

This software is provided under the CC0 1.0 Universal license
(http://creativecommons.org/publicdomain/zero/1.0/).

This application uses third-party jars distributed under various licenses. See 3RD_PARTY_LICENSES for more information about these jars and their licenses.
