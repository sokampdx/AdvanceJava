package edu.pdx.cs410J.sokam;

import com.sun.deploy.util.ArrayUtil;
import com.sun.tools.javac.util.ArrayUtils;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import edu.pdx.cs410J.InvokeMainTestCase;
// import static junit.framework.Assert.assertEquals;

/**
 * Tests the functionality in the {@link Project1} main class.
 */
public class Project1Test extends InvokeMainTestCase {

  /**
   * Invokes the main method of {@link Project1} with the given arguments.
   */
  private MainMethodResult invokeMain(String... args) {
    return invokeMain( Project1.class, args );
  }

   /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  public void noArgumentShouldError() {
    MainMethodResult result = invokeProjectMain();
    assertThat(result.getExitCode(), equalTo(1));
  }

  private MainMethodResult invokeProjectMain(String... args) {
    return invokeMain(Project1.class, args);
  }

  @Test
  public void noArgumentPrintMissingArgumentToStandardError() {
    String errorMessage = Project1.NOT_ENOUGH_COMMAND_LINE_ARGUMENTS;
    assertThatStandardErrorContains(errorMessage);
  }

  private void assertThatStandardErrorContains(String errorMessage, String... args) {
    MainMethodResult result = invokeProjectMain(args);
    assertThat(result.getErr(), containsString(errorMessage));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  public void oneCommandLineArgumentPrintMissingArgumentToStandardError(){
    String errorMessage = Project1.NOT_ENOUGH_COMMAND_LINE_ARGUMENTS;
    assertThatStandardErrorContains(errorMessage, "fail");
  }

  @Test
  public void missingArgumentShouldPrintUsageMessageToStandardError(){
    String errorMessage = Project1.USAGE;
    assertThatStandardErrorContains(errorMessage);
  }

  @Test
  public void tooManyArgumentShouldPrintArgumentOverflowToStandardError(){
    String errorMessage = Project1.TOO_MANY_COMMAND_LINE_ARGUMENTS;
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_TWO_OPTION + 1);
    assertThatStandardErrorContains(errorMessage, args);
  }

  private String[] createArgumentsForTest(int numArgs) {
    String[] info = {"Alaska Air", "1146", "PDX", "10/31/2014", "4:35", "SFO", "11/30/2014", "23:59"};
    String[] options = {Project1.PRINT_OPTION, Project1.README_OPTION};
    String[] both = new String[numArgs];
    int extra = 0;
    if (numArgs > Project1.ARGUMENT_WITH_TWO_OPTION)
      extra = numArgs - Project1.ARGUMENT_WITH_TWO_OPTION;
    int numOption = numArgs - Project1.ARGUMENT_WITH_NO_OPTION - extra;

    System.arraycopy(options, 0, both, 0, numOption);
    System.arraycopy(info, 0, both, numOption, info.length);

    if (extra > 0) {
      for (int i = Project1.ARGUMENT_WITH_TWO_OPTION; i < numArgs; ++i)
        both[i] = Integer.toString(i);
    }
    return both;
  }

  @Test
  public void minNumberOfArgumentShouldExitZero(){
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_NO_OPTION);
    assertThatArgumentsAreValid(args);
  }

  private void assertThatArgumentsAreValid(String... args) {
    MainMethodResult result = invokeProjectMain(args);
    assertThat(result.getExitCode(), equalTo(0));
  }

//  @Test
//  public void whenThereAreUpperLimitOfArgumentExitCodeIsZero(){
//    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_TWO_OPTION);
//    assertThatArgumentsAreValid(args);
//  }

  @Test
  public void whenOneOptionAllowButFirstArgumentsIsNotOptionShouldError() {
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_ONE_OPTION);
    args[0] = "whatever";
    String errorMessage = Project1.INCORRECT_COMMAND_LINE_ARGUMENTS;
    assertThatStandardErrorContains(errorMessage, args);
  }

  @Test
  public void whenTwoOptionAllowButFirstTwoArgumentsAreNotOptionShouldError(){
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_TWO_OPTION);
    args[0] = "whatever";
    args[1] = "whenever";
    String errorMessage = Project1.INCORRECT_COMMAND_LINE_ARGUMENTS;
    assertThatStandardErrorContains(errorMessage, args);
  }

  @Test
  public void whenOneOptionAllowAndFirstArgumentIsAnOptionShouldExitZero(){
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_ONE_OPTION);
    args[0] = Project1.PRINT_OPTION;
    assertThatArgumentsAreValid(args);
  }

  @Test
  public void whenTwoOptionAllowAndFirstTwoArgumentAreDifferentOptionShouldExitZero(){
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_TWO_OPTION);
    args[0] = Project1.PRINT_OPTION;
    args[1] = Project1.README_OPTION;
    assertThatArgumentsAreValid(args);
  }

  @Test
  public void whenTwoOptionAllowButFirstTwoArgumentAreSameShouldError(){
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_TWO_OPTION);
    args[0] = Project1.PRINT_OPTION;
    args[1] = Project1.PRINT_OPTION;
    String errorMessage = Project1.INCORRECT_COMMAND_LINE_ARGUMENTS;
    assertThatStandardErrorContains(errorMessage, args);
  }

  @Test
  public void flightNumberNotANumberShouldError() {
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_NO_OPTION);
    args[1] = "abc";
    String errorMessage = Project1.INVALID_FLIGHT_NUMBER;
    assertThatStandardErrorContains(errorMessage, args);
  }

  @Test
  public void srcAirportCodeIsInvalidWithNoOptionShouldError(){
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_NO_OPTION);
    args[2] = "abcd";
    String errorMessage = Project1.INVALID_AIRPORT_CODE;
    assertThatStandardErrorContains(errorMessage, args);
  }

  @Test
  public void srcAirportCodeIsInvalidWithOneOptionShouldError(){
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_ONE_OPTION);
    args[3] = "abcd";
    String errorMessage = Project1.INVALID_AIRPORT_CODE;
    assertThatStandardErrorContains(errorMessage, args);
  }

  @Test
  public void srcAirportCodeIsInvalidWithTwoOptionShouldError(){
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_TWO_OPTION);
    args[4] = "abcd";
    String errorMessage = Project1.INVALID_AIRPORT_CODE;
    assertThatStandardErrorContains(errorMessage, args);
  }

  @Test
  public void destAirportCodeIsInvalidWithNoOptionShouldError(){
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_NO_OPTION);
    args[5] = "abcd";
    String errorMessage = Project1.INVALID_AIRPORT_CODE;
    assertThatStandardErrorContains(errorMessage, args);
  }

  @Test
  public void departTimeWithInvalidHourShouldError() {
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_NO_OPTION);
    args[4] = "31:00";
    String errorMessage = Project1.INVALID_TIME;
    assertThatStandardErrorContains(errorMessage, args);
  }

  @Test
  public void departTimeWithInvalidMinShouldError() {
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_NO_OPTION);
    args[4] = "21:90";
    String errorMessage = Project1.INVALID_TIME;
    assertThatStandardErrorContains(errorMessage, args);
  }

  @Test
  public void arrivalTimeWithInvalidHourShouldError() {
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_TWO_OPTION);
    args[9] = "31:00";
    String errorMessage = Project1.INVALID_TIME;
    assertThatStandardErrorContains(errorMessage, args);
  }

  @Test
  public void arrivalTimeWithInvalidMinShouldError() {
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_TWO_OPTION);
    args[9] = "21:90";
    String errorMessage = Project1.INVALID_TIME;
    assertThatStandardErrorContains(errorMessage, args);
  }

  @Test
  public void departDateWithInvalidMonthShouldError() {
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_NO_OPTION);
    args[3] = "13/13/2013";
    String errorMessage = Project1.INVALID_DATE;
    assertThatStandardErrorContains(errorMessage, args);
  }

  @Test
  public void departDateWithInvalidDayShouldError() {
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_NO_OPTION);
    args[3] = "4/31/2014";
    String errorMessage = Project1.INVALID_DATE;
    assertThatStandardErrorContains(errorMessage, args);
  }

  @Test
  public void departDateWithInvalidLeapDayShouldError() {
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_NO_OPTION);
    args[3] = "2/29/2014";
    String errorMessage = Project1.INVALID_DATE;
    assertThatStandardErrorContains(errorMessage, args);
  }

  @Test
  public void departDateWithInvalidYearShouldError() {
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_NO_OPTION);
    args[3] = "12/13/0000";
    String errorMessage = Project1.INVALID_DATE;
    assertThatStandardErrorContains(errorMessage, args);
  }

  @Test
  public void departDateWithInvalidFormatShouldError() {
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_NO_OPTION);
    args[3] = "Jan 3 1999";
    String errorMessage = Project1.INVALID_DATE;
    assertThatStandardErrorContains(errorMessage, args);
  }


  @Test
  public void arrivalDateWithInvalidMonthShouldError() {
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_ONE_OPTION);
    args[7] = "11/31/2013";
    String errorMessage = Project1.INVALID_DATE;
    assertThatStandardErrorContains(errorMessage, args);
  }

  @Test
  public void arrivalDateWithInvalidDayShouldError() {
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_TWO_OPTION);
    args[8] = "3/33/2014";
    String errorMessage = Project1.INVALID_DATE;
    assertThatStandardErrorContains(errorMessage, args);
  }

  @Test
  public void arrivalDateWithInvalidLeapDayShouldError() {
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_ONE_OPTION);
    args[7] = "2/29/2014";
    String errorMessage = Project1.INVALID_DATE;
    assertThatStandardErrorContains(errorMessage, args);
  }

  @Test
  public void arrivalDateWithInvalidYearShouldError() {
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_ONE_OPTION);
    args[7] = "12/13/0000";
    String errorMessage = Project1.INVALID_DATE;
    assertThatStandardErrorContains(errorMessage, args);
  }

  @Test
  public void arrivalDateWithInvalidFormatShouldError() {
    String[] args = createArgumentsForTest(Project1.ARGUMENT_WITH_ONE_OPTION);
    args[7] = "Jan 3 1999";
    String errorMessage = Project1.INVALID_DATE;
    assertThatStandardErrorContains(errorMessage, args);
  }



}