package edu.pdx.cs410J.sokam;

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
    public void noArgumentHasExitCodeOf1() {
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
    }

    @Test
    public void oneCommandLineArgumentPrintMissingArgumentToStandardError(){
        String errorMessage = Project1.NOT_ENOUGH_COMMAND_LINE_ARGUMENTS;
        assertThatStandardErrorContains(errorMessage, "fail");
    }

    @Test
    public void whenArgumentAreMissingUsageMessageIsPrintToStandardError(){
        String errorMessage = Project1.USAGE;
        assertThatStandardErrorContains(errorMessage);
    }

    @Test
    public void whenTooManyArgumentPrintArgumentOverflowToStandardError(){
        String errorMessage = Project1.TOO_MANY_COMMAND_LINE_ARGUMENTS;
        String[] args = createArgumentsForTest(Project1.ARGUMENT_UPPER_LIMIT + 1);
        assertThatStandardErrorContains(errorMessage, args);
    }

    private String[] createArgumentsForTest(int numberOfArguments) {
        String[] args = new String[numberOfArguments];
        for (int i = 0; i < numberOfArguments; i++)
            args[i] = String.valueOf(i);
        return args;
    }

    @Test
    public void whenThereAreLowerLimitOfArgumentExitCodeIsZero(){
        String[] args = createArgumentsForTest(Project1.ARGUMENT_LOWER_LIMIT);
        assertThatArgumentsAreValid(args);
    }

    private void assertThatArgumentsAreValid(String... args) {
        MainMethodResult result = invokeProjectMain(args);
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void whenThereAreUpperLimitOfArgumentExitCodeIsZero(){
        String[] args = createArgumentsForTest(Project1.ARGUMENT_UPPER_LIMIT);
        assertThatArgumentsAreValid(args);
    }
}