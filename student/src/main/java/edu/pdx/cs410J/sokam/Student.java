package edu.pdx.cs410J.sokam;

import com.sun.tools.javac.code.Attribute;
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
  static final String PRINT_STAT = "%s has a GPA of %.2f and is taking %d class%s";
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

  protected String name;
  protected String gender;
  protected double gpa;
  protected ArrayList<String> classes;

  public Student(String name, String gender, double gpa, ArrayList<String> classes) {
    super(name);
    this.name = name;
    this.gender = gender;
    this.gpa = gpa;
    this.classes = new ArrayList<>(classes);
  }

  /**                                                                               
   * All students say "This class is too much work"                                 
   */
  @Override
  public String says() {
    return String.format(PRINT_COMMENT, getPronouns());
  }
                                                                                    
  /**                                                                               
   * Returns a <code>String</code> that describes this                              
   * <code>Student</code>.                                                          
   */
  @Override
  public String toString() {
    return String.format(PRINT_STAT, this.name, this.gpa, this.classes.size(), getClassesString());
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
    String gender = toVerifiedGender(args[1]);
    double gpa = toNumberGpa(args[2]);
    ArrayList<String> classes = new ArrayList<>(toClassArray(args));

    Student student = new Student (name, gender, gpa, classes);
    System.out.println(student.toString() + student.says());
    System.exit(0);
  }

  private static String toVerifiedGender(String gender) {
    if (isNotTheRightGender(gender)) {
      System.err.println(GENDER_ERR);
      System.exit(1);
    }
    return gender;
  }

  private static boolean isNotTheRightGender(String gender) {
    return !(gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female"));
  }


  private static ArrayList<String> toClassArray(String... args) {
    int lastIndex = args.length;
    ArrayList<String> classes = new ArrayList<String>();

    for (int i = MINIMUM_ARGS; i < lastIndex; ++i) {
      classes.add(args[i]);
    }

    return classes;
  }

  private String getClassesString() {
    int numClasses = classes.size();
    int lastIndex = numClasses -1;
    String begin = "";
    String classesString = ". ";
    String lastConnector = "";
    String betweenConnector = "";

    if (numClasses > 0) {
      if (numClasses > 1) {
        begin = "es";
        lastConnector = " and";
      }
      if (numClasses > 2)
        betweenConnector = ",";

      for (int i = lastIndex; i >= 0; --i) {
        classesString = " " + classes.get(i) + classesString;
        if (i == lastIndex)
          classesString = lastConnector + classesString;
        if (i != 0)
          classesString = betweenConnector + classesString;
      }
      classesString = begin + ":" + classesString;
    }
    return classesString;
  }

  private static double toNumberGpa(String gpa) {
    if (!isNumber(gpa)) {
      System.err.println(GPA_ERR);
      System.exit(1);
    }
    return Double.parseDouble(gpa);
  }

  private static boolean isNumber(String number) {
    return number.matches("\\d+(\\.\\d+)?");
  }

  private String getPronouns() {
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