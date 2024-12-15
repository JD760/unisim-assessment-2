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
  PARK(2, 3, AssetPaths.REC1),
  GYM(3, 5, AssetPaths.REC2),;

  private final int width;
  private final int height;
  private final AssetPaths texturePath;
  private final AssetPaths texturePathSnow;

  RecreationVariant(int width, int height, AssetPaths texturePath) {
    this.width = width;
    this.height = height;
    this.texturePath = texturePath;
    this.texturePathSnow = texturePath;
  }

  RecreationVariant(int width, int height, AssetPaths texturePath, AssetPaths texturePathSnow) {
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
    return RecreationVariant.class;
  }

}
