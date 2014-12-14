package edu.pdx.cs410J.sokam;

import edu.pdx.cs410J.AbstractAirline;

import java.util.Arrays;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  public static final String USAGE = "usage: java edu.pdx.cs410J.sokam.Project1 [-(print|README_OPTION)] name flightNumber src departTime dest arriveTime";
  public static final String NOT_ENOUGH_COMMAND_LINE_ARGUMENTS = "Not enough command line arguments";
  public static final String TOO_MANY_COMMAND_LINE_ARGUMENTS = "There are too many command line arguments.";
  public static final int ARGUMENT_WITH_NO_OPTION = 8;
  public static final int ARGUMENT_WITH_ONE_OPTION = 9;
  public static final int ARGUMENT_WITH_TWO_OPTION = 10;
  public static final String PRINT_OPTION = "-print";
  public static final String README_OPTION = "-README";
  public static final String INCORRECT_COMMAND_LINE_ARGUMENTS = "Incorrect Command Line Arguments";
  public static final String INVALID_FLIGHT_NUMBER = "Invalid Flight Number. Flight Number is numeric.";
  public static final String INVALID_AIRPORT_CODE = "Invalid Airport Code. Airport Code is a three letters code.";
  public static final String INVALID_TIME="Invalid Time.";
  public static final String INVALID_DATE="Invalid Date.";

  public static void main(String[] args) {
    Class c = AbstractAirline.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath

    int numArgs = args.length;

    if (isNotWithinArgsRange(numArgs) || isNotValidOption(args)) {
      System.exit(1);
    }

    int index = numArgs - 1;
    int numOption = numArgs - ARGUMENT_WITH_NO_OPTION;
    String arriveTime = toVerifiedTime(args[index--]);
    String arriveDate = toVerifiedDate(args[index--]);
    String dest = toVerifiedAirportCode(args[index--]);
    String departTime = toVerifiedTime(args[index--]);
    String departDate = toVerifiedDate(args[index--]);
    String src = toVerifiedAirportCode(args[index--]);
    int flightNum = toVerifiedFlightNam(args[index--]);
    String name = args[index];



    System.exit(0);


//        for (String arg : args) {
//          System.out.println(arg);
//        }
  }

  private static String toVerifiedDate(String date) {
    if (!isValidDate(date)) {
      System.err.println(INVALID_DATE);
      System.exit(1);
    }
    String[] values = date.split("/");
    System.out.println(Arrays.toString(values));
    System.out.println(values[0]);
    System.out.println(values.length);
    return date;
  }

  private static boolean isValidDate(String date) {
    String[] values = date.split("/");
    boolean isValid = false;

    if(values.length == 3 &&
        isNumber(values[0]) && isNumber(values[1]) && isNumber(values[2])) {

      int month = Integer.parseInt(values[0]);
      int day = Integer.parseInt(values[1]);
      int year = Integer.parseInt(values[2]);

      if (isWithinYearRange(year)) {
        isValid = isWithinMonthAndDayRange(month, day, year);
      }
    }
    return isValid;
  }

  private static boolean isWithinYearRange(int year) {
    return year > 2000 && year < 9999;
  }

  private static boolean isWithinMonthAndDayRange(int month, int day, int year) {
    boolean isValid;
    switch (month) {
      case (2):
        if (isLeapYear(year))
          isValid = (day > 0 && day <= 29);
        else
          isValid = (day > 0 && day <= 28);
        break;
      case (1):
      case (3):
      case (5):
      case (7):
      case (8):
      case (10):
      case (12):
        isValid = (day > 0 && day <= 31);
        break;
      case (4):
      case (6):
      case (9):
      case (11):
        isValid = (day > 0 && day <= 30);
        break;
      default:
        isValid = false;
    }
    return isValid;
  }

  private static boolean isLeapYear(int year) {
    return (year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0));
  }

  private static String toVerifiedTime(String time) {
    if (!isValidTime(time)) {
      System.err.println(INVALID_TIME);
      System.exit(1);
    }

    return time;
  }

  private static boolean isValidTime(String time) {
    return time.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]");
  }

  private static String toVerifiedAirportCode(String code) {
    if (!isValidCode(code)) {
      System.err.println(INVALID_AIRPORT_CODE);
      System.exit(1);
    }
    return code.toUpperCase();
  }

  private static boolean isValidCode(String code) {
    return code.matches("[a-zA-Z]{3}");
  }

  private static int toVerifiedFlightNam(String flightStr) {
    if (!isNumber(flightStr)) {
      System.err.println(INVALID_FLIGHT_NUMBER);
      System.exit(1);
    }

    return Integer.parseInt(flightStr);
  }

  private static boolean isNumber(String number) {
    return number.matches("\\d+");
  }


  private static boolean isNotValidOption(String[] args) {
    boolean isNotValid = false;

    if (args.length == (ARGUMENT_WITH_ONE_OPTION) && isNotOption(args[0])) {
      printArgumentIsNotValid(INCORRECT_COMMAND_LINE_ARGUMENTS);
      isNotValid = true;
    }
    if (args.length == (ARGUMENT_WITH_TWO_OPTION) &&
        (args[0] == args[1] || isNotOption(args[0]) || isNotOption(args[1]))) {
      printArgumentIsNotValid(INCORRECT_COMMAND_LINE_ARGUMENTS);
      isNotValid = true;
    }

    return isNotValid;
  }

  private static boolean isNotWithinArgsRange(int numArgs) {
    boolean isNotInRange = false;

    if (numArgs < ARGUMENT_WITH_NO_OPTION) {
      printArgumentIsNotValid(NOT_ENOUGH_COMMAND_LINE_ARGUMENTS);
      isNotInRange = true;
    }
    if (numArgs > ARGUMENT_WITH_TWO_OPTION) {
      printArgumentIsNotValid(TOO_MANY_COMMAND_LINE_ARGUMENTS);
      isNotInRange = true;
    }

    return isNotInRange;
  }

  private static boolean isNotOption(String args) {
      return !(args == PRINT_OPTION || args == README_OPTION);
  }

  private static void printArgumentIsNotValid(String errorMessage) {
      System.err.println(errorMessage);
      System.err.println();
      System.err.println(USAGE);
      System.exit(1);
  }

}