package y111studios.position;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Grid Position class.
 */
public class GridPositionTest {
  @Tag("Constructor")
  @Test
  void preventNegativeCreation() {
    assertThrows(IllegalArgumentException.class, () -> {
      new GridPosition(-1, 0);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new GridPosition(0, -1);
    });
  }

  @SuppressWarnings("unlikely-arg-type")
  @Test
  public void testEquals() {
    GridPosition pos = new GridPosition(1, 1);
    // test other position being null
    assertFalse(pos.equals(null));
    // test other position being of the wrong type
    assertFalse(pos.equals(new GridArea(0, 0, 10, 10)));
    // a position needs to be equal to itself
    assertTrue(pos.equals(pos));
    assertTrue(pos.equals(new GridPosition(pos.getX(), pos.getY())));
    // distinct positions should not be equal
    assertFalse(pos.equals(new GridPosition(0, 5)));

  }

}
