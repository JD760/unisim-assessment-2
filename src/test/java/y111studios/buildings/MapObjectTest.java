package y111studios.buildings;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import y111studios.AssetPaths;
import y111studios.position.GridArea;

/**
 * Tests the Map Object class by creating a mock.
 */
public class MapObjectTest {

  @Test
  public void testNonNullMapArea() {
    GridArea area = null;
    try {
      new MapObjectMock(area, AssetPaths.ACC1);
    } catch (IllegalArgumentException e) {
      return;
    }
    fail("Map Object with null map area created");
  }
  
  @Test
  public void testNonNullTexturePath() {
    GridArea area = new GridArea(0, 0, 10, 10);
    AssetPaths path = null;
    try {
      new MapObjectMock(area, path);
    } catch (IllegalArgumentException e) {
      return;
    }
    fail("Map Object with null texture path created");
  }

  class MapObjectMock extends MapObject {
    public MapObjectMock(GridArea area, AssetPaths path) {
      super(area, path);
    }
  }
}
