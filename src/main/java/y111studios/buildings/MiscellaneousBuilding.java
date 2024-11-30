package y111studios.buildings;

import y111studios.buildings.premade_variants.MiscellaneousVariant;
import y111studios.position.GridPosition;

/**
 * A class representing a miscellaneous building within the game. This class
 * extends the
 * {@link Building} class.

 * @see Building
 */
public class MiscellaneousBuilding extends Building {
  /**
   * Creates a new accommodation building at the given position of the given
   * variant.

   * @param position The position of the building
   * @param variant  The variant type of the building
   */
  public MiscellaneousBuilding(GridPosition position, MiscellaneousVariant variant) {
    super(position, variant);
  }
}
