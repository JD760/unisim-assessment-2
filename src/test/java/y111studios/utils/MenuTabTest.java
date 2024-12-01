package y111studios.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test the Menu Tab enum.
 */
public class MenuTabTest {
  @Test
  public void testToInt() {
    assertEquals(0, MenuTab.ACCOMMODATION.toInt());
    assertEquals(1, MenuTab.CATERING_RECREATION.toInt());
    assertEquals(2, MenuTab.TEACHING.toInt());
  }
}