package y111studios.buildings.premade_variants;

import y111studios.AssetPaths;

/**
 * An enum representing the different predefined variants a
 * {@link y111studios.buildings.CateringBuilding CateringBuilding}.
 * This enum is used in conjunction with the
 * {@link y111studios.buildings.BuildingFactory
 * BuildingFactory} class to create instances of
 * {@link y111studios.buildings.CateringBuilding
 * CateringBuilding}.
 */
public enum CateringVariant implements VariantProperties {
  FAST_FOOD(2, 3, AssetPaths.CATER1, AssetPaths.CATER1SNOW),
  RESTAURANT1(2, 2, AssetPaths.CATER2, AssetPaths.CATER2SNOW),
  SUPERMARKET(8, 11, AssetPaths.CATER3, AssetPaths.CATER3SNOW),
  REASTURANT2(2, 2, AssetPaths.CATER4, AssetPaths.CATER4SNOW),
  CORNER_SHOP(1, 2, AssetPaths.CATER5, AssetPaths.CATER5SNOW);

  private final int width;
  private final int height;
  private final AssetPaths texturePath;
  private final AssetPaths texturePathSnow;

  CateringVariant(int width, int height, AssetPaths texturePath) {
    this.width = width;
    this.height = height;
    this.texturePath = texturePath;
    this.texturePathSnow = texturePath;
  }
  CateringVariant(int width, int height, AssetPaths texturePath, AssetPaths texturePathSnow) {
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
    return CateringVariant.class;
  }

}
