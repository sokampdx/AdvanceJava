package edu.pdx.cs410J.sokam;

import edu.pdx.cs410J.InvokeMainTestCase;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;

/**
 * JUnit tests for the Student class.  These tests extend <code>InvokeMainTestCase</code>
 * which allows them to easily invoke the <code>main</code> method of <code>Student</code>.
 * They also make use of the <a href="http://hamcrest.org/JavaHamcrest/">hamcrest</a>matchers
 * for more readable assertion statements.
 */
public class StudentTest extends InvokeMainTestCase
{

  @Test
  public void invokingMainWithNoArgumentsHasExitCodeOf1() {
    MainMethodResult result = invokeStudentMain();
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  public void invokingMainWithNoArgumentsPrintsMissingArgumentsToStandardError() {
    assertThatStandardErrorContain(Student.NOT_ENOUGH_ARGS_ERR);
  }

  @Test
  public void notEnoughCommandLineArgumentsShouldError() {
    assertThatStandardErrorContain(Student.NOT_ENOUGH_ARGS_ERR, "fail");
  }

  @Test
  public void notEnoughCommandLineArgumentsShouldPrintUsage() {
    assertThatStandardErrorContain(Student.USAGE);
  }

  @Ignore
  @Test
  public void correctCommandLineArgumentsShouldExitZero() {
    MainMethodResult result = invokeStudentMain("Dave", "male", "3.64");
    assertThat(result.getExitCode(), equalTo(0));
  }

  @Test
  public void nameShouldAppearAsOutput() {
    String output = "Dave";
    assertThatStandardOutputContain(output, output, "male", "3.64");
  }

  @Test
  public void genderNotMaleOrFemaleShouldError() {
    assertThatStandardErrorContain(Student.GENDER_ERR, "Dave", "cat", "1.23");
  }

  @Test
  public void genderMaleShouldAppearAsHeInOutput() {
    assertThatStandardOutputContain("He", "Dave", "male", "3.64");
  }

  @Test
  public void genderFemaleShouldAppearAsSheInOutput() {
    assertThatStandardOutputContain("She", "Daisy", "female", "3.64");
  }

  @Test
  public void caseInsensitiveFemaleIsValid() {
    assertThatStandardOutputContain("She", "Daisy", "FeMale", "1.23");
  }

  @Test
  public void caseInsensitiveMaleIsValid() {
    assertThatStandardOutputContain("He", "Dave", "MaLe", "1.23");
  }

  @Test
  public void gpaNotDoubleShouldError() {
    assertThatStandardErrorContain(Student.GPA_ERR, "Dave", "male", "abc");
  }

  @Test
  public void gpaShouldAppearInOutput() {
    String output = "3.64";
    assertThatStandardOutputContain(output, "Dave", "male", output);
  }

  @Test
  public void noClassShouldOutputZeroClass() {
    String name = "Dave";
    String gender = "male";
    String gpa = "3.64";
    String classes = ". ";
    String stat = String.format(Student.PRINT_STAT, name, gpa, 0, classes);
    String comment = String.format(Student.PRINT_COMMENT, "He");
    assertThatStandardOutputContain(stat + comment, name, gender, gpa);
  }

  @Test
  public void oneClassShouldOutputOneClass() {
    String name = "Dave";
    String gender = "male";
    String gpa = "3.64";
    String class1 = "Algorithm";
    String classes = ": " + class1 + ". ";
    String stat = String.format(Student.PRINT_STAT, name, gpa, 1, classes);
    String comment = String.format(Student.PRINT_COMMENT, "He");
    assertThatStandardOutputContain(stat + comment, name, gender, gpa, class1);
  }

  @Test
  public void twoClassShouldOutputTwoClassesWithAnd() {
    String name = "Dave";
    String gender = "male";
    String gpa = "3.64";
    String class1 = "Algorithm";
    String class2 = "Operating Systems";
    String classes = "es: " + class1 + " and " + class2 + ". ";
    String stat = String.format(Student.PRINT_STAT, name, gpa, 2, classes);
    String comment = String.format(Student.PRINT_COMMENT, "He");
    assertThatStandardOutputContain(stat + comment, name, gender, gpa, class1, class2);
  }

  @Test
  public void threeClassShouldOutputClassesWithSeparatorWithAnd() {
    String name = "Dave";
    String gender = "male";
    String gpa = "3.64";
    String class1 = "Algorithm";
    String class2 = "Operating Systems";
    String class3 = "Java";
    String classes = "es: " + class1 + ", " + class2 + "," + " and " + class3 + ". ";
    String stat = String.format(Student.PRINT_STAT, name, gpa, 3, classes);
    String comment = String.format(Student.PRINT_COMMENT, "He");
    assertThatStandardOutputContain(stat + comment, name, gender, gpa, class1, class2, class3);
  }

  @Test
  public void fiveOrMoreClassShouldOutputClassesWithSeparatorWithAnd() {
    String name = "Dave";
    String gender = "male";
    String gpa = "3.64";
    String class1 = "Algorithm";
    String class2 = "Operating Systems";
    String class3 = "Java";
    String class4 = "Compiler";
    String class5 = "Security";
    String classes = "es: " + class1 + ", " + class2 + ", " + class3 + ", " + class4 + "," + " and " + class5 + ". ";
    String stat = String.format(Student.PRINT_STAT, name, gpa, 5, classes);
    String comment = String.format(Student.PRINT_COMMENT, "He");
    assertThatStandardOutputContain(stat + comment, name, gender, gpa, class1, class2, class3, class4, class5);
  }

  private MainMethodResult invokeStudentMain(String... args) {
    return invokeMain(Student.class, args);
  }

  private void assertThatStandardErrorContain(String errorMessage, String... args) {
    MainMethodResult result = invokeStudentMain(args);
    assertThat(result.getErr(), containsString(errorMessage));
    assertThat(result.getExitCode(), equalTo(1));
  }

  private void assertThatStandardOutputContain(String outputMessage, String... args) {
    MainMethodResult result = invokeStudentMain(args);
    assertThat(result.getOut(), containsString(outputMessage));
    assertThat(result.getExitCode(), equalTo(0));
  }

}
