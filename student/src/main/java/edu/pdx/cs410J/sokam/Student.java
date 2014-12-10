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
  public Student(String name, ArrayList classes, double gpa, String gender) {
    super(name);
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

  static boolean isNumber(String number) {
    return number.matches("\\d+(\\.\\d+)?");
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
    String pronouns = "";
    String stat = PRINT_STAT;
    String classes = "";
    String comment = PRINT_COMMENT;
    String output = "";

    if (gender.equalsIgnoreCase("male"))
      pronouns = "He";
    else if (gender.equalsIgnoreCase("female"))
      pronouns = "She";
    else {
      System.err.println(GENDER_ERR);
      System.exit(1);
    }

    if (!isNumber(gpa)) {
      System.err.println(GPA_ERR);
      System.exit(1);
    }

    output = String.format(stat + comment, name, gpa, 0, classes, pronouns);
    System.out.println(output);
    System.exit(0);
  }
}