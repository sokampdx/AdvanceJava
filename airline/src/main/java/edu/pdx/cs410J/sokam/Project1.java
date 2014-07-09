package edu.pdx.cs410J.sokam;

import edu.pdx.cs410J.AbstractAirline;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

    public static final String USAGE = "usage: java edu.pdx.cs410J.sokam.Project1 [-(print|README)] name flightNumber src departTime dest arriveTime";
    public static final String NOT_ENOUGH_COMMAND_LINE_ARGUMENTS = "Not enough command line arguments";
    public static final String TOO_MANY_COMMAND_LINE_ARGUMENTS = "There are too many command line arguments.";
    public static final int ARGUMENT_LOWER_LIMIT = 6;
    public static final int ARGUMENT_UPPER_LIMIT = 8;
    public static final String PRINT = "-print";
    public static final String README = "README";
    public static final String INCORRECT_COMMAND_LINE_ARGUMENTS = "Incorrect Command Line Arguments";

    public static void main(String[] args) {
        Class c = AbstractAirline.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath
        if (args.length < ARGUMENT_LOWER_LIMIT || args.length > ARGUMENT_UPPER_LIMIT)
            printNumberOfArgumentIsNotValid(args);
        if (args.length == (ARGUMENT_LOWER_LIMIT+1) && isNotOption(args[0]))
            System.err.println(INCORRECT_COMMAND_LINE_ARGUMENTS);
        if (args.length == (ARGUMENT_UPPER_LIMIT) && (args[0] == args[1] || isNotOption(args[0]) || isNotOption(args[1])))
            System.err.println(INCORRECT_COMMAND_LINE_ARGUMENTS);

        System.exit(0);
//        for (String arg : args) {
//          System.out.println(arg);
//        }
    }

    private static boolean isNotOption(String args) {
        return !(args == PRINT || args == README);
    }

    private static void printNumberOfArgumentIsNotValid(String[] args) {
        if (args.length < ARGUMENT_LOWER_LIMIT)
            System.err.println(NOT_ENOUGH_COMMAND_LINE_ARGUMENTS);
        if (args.length > ARGUMENT_UPPER_LIMIT)
            System.err.println(TOO_MANY_COMMAND_LINE_ARGUMENTS);

        System.err.println();
        System.err.println(USAGE);
        System.exit(1);
    }

}