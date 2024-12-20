package y111studios.buildings.premade_variants;

import y111studios.AssetPaths;

/**
 * An enum representing the different predefined variants a
 * {@link y111studios.buildings.RecreationBuilding RecreationBuilding}.
 * This enum is used in conjunction with the
 * {@link y111studios.buildings.BuildingFactory
 * BuildingFactory} class to create instances of
 * {@link y111studios.buildings.RecreationBuilding
 * RecreationBuilding}.
 **/
public enum RecreationVariant implements VariantProperties {

  PARK(2, 3, AssetPaths.REC1, "Park", AssetPaths.REC1SNOW),
  GYM(3, 5, AssetPaths.REC2, "Gym", AssetPaths.REC2SNOW),;


  private final int width;
  private final int height;
  private final AssetPaths texturePath;
  private final AssetPaths texturePathSnow;
  private final AssetPaths texturePathFlood;
  private final String name;

  RecreationVariant(int width, int height, AssetPaths texturePath, String name) {
    this.width = width;
    this.height = height;
    this.texturePath = texturePath;
    this.texturePathSnow = texturePath;
    this.texturePathFlood = texturePath;
    this.name = name;
  }

  RecreationVariant(int width, int height, AssetPaths texturePath, String name, AssetPaths texturePathSnow) {
    this.width = width;
    this.height = height;
    this.texturePath = texturePath;
    this.texturePathSnow = texturePathSnow;
    this.texturePathFlood = texturePath;
    this.name = name;
  }

  RecreationVariant(int width, int height, AssetPaths texturePath, String name, AssetPaths texturePathSnow, AssetPaths texturePathFlood) {
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
    return RecreationVariant.class;
  }

  @Override
  public String getName(){
    return name;
  }

}
