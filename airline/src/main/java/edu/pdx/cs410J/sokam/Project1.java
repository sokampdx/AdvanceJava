package edu.pdx.cs410J.sokam;

import edu.pdx.cs410J.AbstractAirline;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

    public static final String USAGE = "usage: java edu.pdx.cs410J.sokam.Project1 [-(print|README)] name flightNumber src departTime dest arriveTime";
    public static final String NOT_ENOUGH_COMMAND_LINE_ARGUMENTS = "Not enough command line arguments";

    public static void main(String[] args) {
        Class c = AbstractAirline.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath
        if (args.length <= 7) {
            System.err.println(NOT_ENOUGH_COMMAND_LINE_ARGUMENTS);
            System.err.println();
            System.err.println(USAGE);
        }
        for (String arg : args) {
          System.out.println(arg);
        }
        System.exit(1);
    }

}