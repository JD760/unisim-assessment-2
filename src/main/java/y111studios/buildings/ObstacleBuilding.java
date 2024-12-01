package y111studios.buildings;

import y111studios.buildings.premade_variants.ObstacleVariant;
import y111studios.position.GridPosition;

/**
 * A class representing an accommodation building within the game. This class
 * extends the
 * {@link Building} class.

 * @see Building
 */
public class ObstacleBuilding extends Building {
  /**
   * Creates a new accommodation building at the given position of the given
   * variant.

   * @param position The position of the building
   * @param variant  The variant type of the building
   */
  public ObstacleBuilding(
      GridPosition position, ObstacleVariant variant, boolean flipped
  ) {
    super(position, variant, flipped);
  }

}
