package y111studios.buildings;

import lombok.Getter;
import lombok.Setter;
import y111studios.AssetPaths;
import y111studios.position.GridArea;
import y111studios.position.GridPosition;

/**
 * A class representing a map object within the game. This class is the
 * superclass of all objects
 * that can be placed onto the game map.
 */
public abstract class MapObject {

  protected @Getter GridArea area;
  protected @Getter AssetPaths texturePath;
  protected @Getter AssetPaths texturePathSnow;
  protected @Getter AssetPaths texturePathFlood;
  protected @Setter @Getter int age;  // The number of game ticks since the object was placed
  protected boolean flipped;
  public static final int BUILDING_TIME = 300;

  /**
   * Constructs a new map object with the specified area.
   *
   * @param area the area of the map object
   *
   * @throws IllegalArgumentException if the area or texture are null
   */
  protected MapObject(
      GridArea area, AssetPaths texturePath, AssetPaths texturePathSnow,
      AssetPaths texturePathFlood, boolean flipped) {
    if (area == null) {
      throw new IllegalArgumentException("GridArea must not be null");
    }
    if (texturePath == null) {
      throw new IllegalArgumentException("Texture Path must not be null");
    }
    this.area = area;
    this.texturePath = texturePath;
    this.texturePathSnow = texturePathSnow;
    this.texturePathFlood = texturePathFlood;
    this.flipped = flipped;
    this.age = 0;
  }

  /**
   * Returns if the position is contained within the area of this map object.
   *
   * @param position the GridPosition to check
   * @return true if the specified position is within this area, false otherwise
   *
   * @see GridArea#contains(GridPosition)
   */
  public boolean contains(GridPosition position) {
    return area.contains(position);
  }

  public boolean getFlipped() {
    return flipped;
  }

  public void tick() {
    age++;
  }
}
