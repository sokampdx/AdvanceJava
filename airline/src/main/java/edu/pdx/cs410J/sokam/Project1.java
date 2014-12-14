package edu.pdx.cs410J.sokam;

import edu.pdx.cs410J.AbstractAirline;

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

  public static void main(String[] args) {
    Class c = AbstractAirline.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath

    if (isWithinArgsRange(args) && isValidOption(args))
      System.exit(0);
    else
      System.exit(1);
//        for (String arg : args) {
//          System.out.println(arg);
//        }
  }

  private static boolean isValidOption(String[] args) {
    boolean isValid = true;

    if (args.length == (ARGUMENT_WITH_ONE_OPTION) && isNotOption(args[0])) {
      printArgumentIsNotValid(INCORRECT_COMMAND_LINE_ARGUMENTS);
      isValid = false;
    }
    if (args.length == (ARGUMENT_WITH_TWO_OPTION) &&
        (args[0] == args[1] || isNotOption(args[0]) || isNotOption(args[1]))) {
      printArgumentIsNotValid(INCORRECT_COMMAND_LINE_ARGUMENTS);
      isValid = false;
    }

    return isValid;
  }

  private static boolean isWithinArgsRange(String[] args) {
    boolean isInRange = true;

    if (args.length < ARGUMENT_WITH_NO_OPTION) {
      printArgumentIsNotValid(NOT_ENOUGH_COMMAND_LINE_ARGUMENTS);
      isInRange = false;
    }
    if (args.length > ARGUMENT_WITH_TWO_OPTION) {
      printArgumentIsNotValid(TOO_MANY_COMMAND_LINE_ARGUMENTS);
      isInRange = false;
    }

    return isInRange;
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