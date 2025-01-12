package y111studios.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Collection of tests for the UnreachableException.
 */
public class UnreachableExceptionTest {
  private static final String DEFAULT_MESSAGE = "Presumed unreachable code executed";
  private static final String TEST_MESSAGE = "Test message";

  @Test
  public void testDefaultMessage() {
    UnreachableException e = new UnreachableException();
    assertEquals(e.getMessage(), DEFAULT_MESSAGE + ".");
  }

  @Test
  public void testExceptionWithCause() {
    Throwable cause = new Throwable("Test message");
    UnreachableException e = new UnreachableException(cause);
    assertEquals(DEFAULT_MESSAGE + ".", e.getMessage());
    assertEquals(cause, e.getCause());
  }

  @Test
  public void testExceptionWithMessage() {
    UnreachableException e = new UnreachableException(TEST_MESSAGE);
    assertEquals(DEFAULT_MESSAGE + " : " + TEST_MESSAGE, e.getMessage());
  }

  @Test
  public void testExceptionWithCauseAndMessage() {
    Throwable cause = new Throwable("Cause");
    UnreachableException e = new UnreachableException(TEST_MESSAGE, cause);
    assertEquals(DEFAULT_MESSAGE + " : " + TEST_MESSAGE, e.getMessage());
    assertEquals(cause, e.getCause());
  }
}
