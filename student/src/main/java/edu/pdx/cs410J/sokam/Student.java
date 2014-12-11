package edu.pdx.cs410J.sokam;

import edu.pdx.cs410J.lang.Human;

import java.util.ArrayList;
                                                                                    
/**                                                                                 
 * This class is represents a <code>Student</code>.                                 
 */                                                                                 
public class Student extends Human {

  static final int MINIMUM_ARGS = 3;
  static final String NOT_ENOUGH_ARGS_ERR = "Not enough command line arguments!";
  static final String GENDER_ERR = "Gender must be male or female!";
  static final String GPA_ERR = "GPA must be a number!";
  static final String USAGE = "usage: java edu.pdx.cs410J.sokam.Student name gender gpa class*";
  static final String PRINT_STAT = "%s has a GPA of %s and is taking %d class%s";
  static final String PRINT_COMMENT = "%s says \"This class is too much work\".";

  /**                                                                               
   * Creates a new <code>Student</code>                                             
   *                                                                                
   * @param name                                                                    
   *        The student's name                                                      
   * @param classes                                                                 
   *        The names of the classes the student is taking.  A student              
   *        may take zero or more classes.                                          
   * @param gpa                                                                     
   *        The student's grade point average                                       
   * @param gender                                                                  
   *        The student's gender ("male" or "female", case insensitive)             
   */                                                                               

//  protected String name;
//  protected String gender;
//  protected double gpa;
//  protected ArrayList<String> classes;

  public Student(String name, String gender, double gpa, ArrayList classes) {
    super(name);
//    this.name = name;
//    this.gender = gender;
//    this.gpa = gpa;
//    this.classes = new ArrayList<String>(classes);
  }

  /**                                                                               
   * All students say "This class is too much work"                                 
   */
  @Override
  public String says() {                                                            
    return null;
  }
                                                                                    
  /**                                                                               
   * Returns a <code>String</code> that describes this                              
   * <code>Student</code>.                                                          
   */                                                                               
  public String toString() {
    return null;
  }

  /**
   * Main program that parses the command line, creates a
   * <code>Student</code>, and prints a description of the student to
   * standard out by invoking its <code>toString</code> method.
   */
  public static void main(String[] args) {
    if (args.length < MINIMUM_ARGS) {
      System.err.println(NOT_ENOUGH_ARGS_ERR);
      System.err.println();
      System.err.println(USAGE);
      System.exit(1);
    }

    String name = args[0];
    String gender = args[1];
    String gpa = args[2];

    String pronouns = getPronouns(gender);
    int numArgs = args.length;
    int numClasses = numArgs - MINIMUM_ARGS;
    String classes = getClasses(numArgs, numClasses, args);
    VerityGpa(gpa);

    String stat = PRINT_STAT;
    String comment = PRINT_COMMENT;
    String output = String.format(stat + comment, name, gpa, numClasses, classes, pronouns);

    System.out.println(output);
    System.exit(0);
  }

  private static String getClasses(int numArgs, int numClasses, String... args) {
    int lastIndex = numArgs -1;
    String begin = "";
    String classes = ". ";
    String lastConnector = "";
    String betweenConnector = "";

    if (numClasses > 0) {
      if (numClasses > 1) {
        begin = "es";
        lastConnector = " and";
      }
      if (numClasses > 2)
        betweenConnector = ",";

      for (int i = lastIndex; i >= MINIMUM_ARGS; --i) {
        classes = " " + args[i] + classes;
        if (i == lastIndex)
          classes = lastConnector + classes;
        if (i != MINIMUM_ARGS)
          classes = betweenConnector + classes;
      }
      classes = begin + ":" + classes;
    }
    return classes;
  }

  private static void VerityGpa(String gpa) {
    if (!isNumber(gpa)) {
      System.err.println(GPA_ERR);
      System.exit(1);
    }
  }

  private static boolean isNumber(String number) {
    return number.matches("\\d+(\\.\\d+)?");
  }

  private static String getPronouns(String gender) {
    String pronouns = "";
    if (gender.equalsIgnoreCase("male"))
      pronouns = "He";
    else if (gender.equalsIgnoreCase("female"))
      pronouns = "She";
    else {
      System.err.println(GENDER_ERR);
      System.exit(1);
    }
    return pronouns;
  }
}