package y111studios.buildings.premade_variants;

import y111studios.AssetPaths;
import y111studios.buildings.AccommodationBuilding;

/**
 * An enum representing the different predefined variants an
 * {@link AccommodationBuilding AccommodationBuilding}.
 * This enum is used in conjunction with the
 * {@link y111studios.buildings.BuildingFactory
 * BuildingFactory} class to create instances of {@link AccommodationBuilding
 * AccommodationBuilding}.
 */
public enum AccommodationVariant implements VariantProperties {
  SMALL_HOUSE(5, 5, AssetPaths.ACC1, "Small House"),
  MEDIUM_HOUSE(3, 3, AssetPaths.ACC2, "Medium House"),
  MODERN_FLAT(2, 2, AssetPaths.ACC3, "Modern Flat"),
  LUXURY_FLAT(3, 3, AssetPaths.ACC4, "Luxury Flat"),
  PRIVATE_HOUSE(3, 3, AssetPaths.ACC5, "Private House"),;

  private final int width;
  private final int height;
  private final AssetPaths texturePath;
  private final String name;

  AccommodationVariant(int width, int height, AssetPaths texturePath, String name) {
    this.width = width;
    this.height = height;
    this.texturePath = texturePath;
    this.name = name;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public String getName() { return name; }

  @Override
  public AssetPaths getTexturePath() {
    return texturePath;
  }

  @Override
  public Class<? extends VariantProperties> getVariantClass() {
    return AccommodationVariant.class;
  }

}
