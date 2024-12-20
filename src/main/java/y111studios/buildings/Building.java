package y111studios.buildings;

import lombok.Getter;
import y111studios.buildings.premade_variants.VariantProperties;
import y111studios.position.GridArea;
import y111studios.position.GridPosition;

/**
 * A class representing a building within the game.

 * @see MapObject
 */
@Getter
public abstract class Building extends MapObject {
  private final VariantProperties variant;
  private @Getter GridPosition position;

  /**
   * Constructor for a building that starts at a position and is of a specified
   * variant.

   * @param position the position of the building
   * @param variant  the variant information of the building
   */
  protected Building(GridPosition position, VariantProperties variant, boolean flipped) {
    super(
      new GridArea(
        position, flipped ? variant.getHeight() : variant.getWidth(),
        flipped ? variant.getWidth() : variant.getHeight()
      ), variant.getTexturePath(), variant.getTexturePathSnow(), variant.getTexturePathFlood(), flipped
    );
    this.variant = variant;
    this.position = position;
  }
}
