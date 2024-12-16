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
  SMALL_HOUSE(5, 5, AssetPaths.ACC1 ,AssetPaths.ACC1SNOW),
  MEDIUM_HOUSE(3, 3, AssetPaths.ACC2 , AssetPaths.ACC2SNOW),
  MODERN_FLAT(2, 2, AssetPaths.ACC3 ,AssetPaths.ACC3SNOW),
  LUXURY_FLAT(3, 3, AssetPaths.ACC4,AssetPaths.ACC4SNOW),
  PRIVATE_HOUSE(3, 3, AssetPaths.ACC5 ,AssetPaths.ACC5SNOW);

  private final int width;
  private final int height;
  private final AssetPaths texturePath;
  private final AssetPaths texturePathSnow;

  AccommodationVariant(int width, int height, AssetPaths texturePath) {
    this.width = width;
    this.height = height;
    this.texturePath = texturePath;
    this.texturePathSnow = texturePath;
  }
  AccommodationVariant(int width, int height, AssetPaths texturePath, AssetPaths texturePathSnow) {
    this.width = width;
    this.height = height;
    this.texturePath = texturePath;
    this.texturePathSnow = texturePathSnow;
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
  public AssetPaths getTexturePath() {
    return texturePath;
  }
  public AssetPaths getTexturePathSnow() {
    return texturePathSnow;
  }

  @Override
  public Class<? extends VariantProperties> getVariantClass() {
    return AccommodationVariant.class;
  }

}
