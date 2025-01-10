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
  FAST_FOOD(2, 3, AssetPaths.CATER1, "Fast food", AssetPaths.CATER1SNOW),
  RESTAURANT1(2, 2, AssetPaths.CATER2, "Restaurant", AssetPaths.CATER2SNOW),
  SUPERMARKET(8, 11, AssetPaths.CATER3, "Supermarket", AssetPaths.CATER3SNOW),
  RESTAURANT2(2, 2, AssetPaths.CATER4, "Restaurant 2", AssetPaths.CATER4SNOW),
  CORNER_SHOP(1, 2, AssetPaths.CATER5, "Corner shop", AssetPaths.CATER5SNOW),;


  private final int width;
  private final int height;
  private final AssetPaths texturePath;
  private final AssetPaths texturePathSnow;
  private final AssetPaths texturePathFlood;
  private final String name;


  CateringVariant(int width, int height, AssetPaths texturePath, String name) {
    this.width = width;
    this.height = height;
    this.texturePath = texturePath;
    this.texturePathSnow = texturePath;
    this.texturePathFlood = texturePath;
    this.name = name;
  }

  CateringVariant(
      int width, int height, AssetPaths texturePath, String name, AssetPaths texturePathSnow) {
    this.width = width;
    this.height = height;
    this.texturePath = texturePath;
    this.texturePathSnow = texturePathSnow;
    this.texturePathFlood = texturePath;
    this.name = name;
  }

  CateringVariant(
      int width, int height, AssetPaths texturePath, String name,
      AssetPaths texturePathSnow, AssetPaths texturePathFlood) {
    this.width = width;
    this.height = height;
    this.texturePath = texturePath;
    this.texturePathSnow = texturePathSnow;
    this.texturePathFlood = texturePathFlood;
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
  public String getName() {
    return name;
  }

  @Override
  public AssetPaths getTexturePath() {
    return texturePath;
  }

  public AssetPaths getTexturePathSnow() {
    return texturePathSnow;
  }

  public AssetPaths getTexturePathFlood() {
    return texturePathFlood;
  }

  @Override
  public Class<? extends VariantProperties> getVariantClass() {
    return CateringVariant.class;
  }

}
