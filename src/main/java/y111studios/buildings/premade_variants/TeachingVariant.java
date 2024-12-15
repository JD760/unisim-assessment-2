package y111studios.buildings.premade_variants;

import y111studios.AssetPaths;

/**
 * An enum representing the different predefined variants a
 * {@link y111studios.buildings.TeachingBuilding TeachingBuilding}.
 * This enum is used in conjunction with the
 * {@link y111studios.buildings.BuildingFactory
 * BuildingFactory} class to create instances of
 * {@link y111studios.buildings.TeachingBuilding
 * TeachingBuilding}.
 */
public enum TeachingVariant implements VariantProperties {
  SMALL_CLASSROOM(3, 3, AssetPaths.TEACH1),
  MEDIUM_CLASSROOM(3, 3, AssetPaths.TEACH2),
  SUBJECT_HUB(3, 2, AssetPaths.TEACH3),
  DEPARTMENT(6, 6, AssetPaths.TEACH4),
  LAB(3, 4, AssetPaths.TEACH5),;

  private final int width;
  private final int height;
  private final AssetPaths texturePath;
  private final AssetPaths texturePathSnow;

  TeachingVariant(int width, int height, AssetPaths texturePath) {
    this.width = width;
    this.height = height;
    this.texturePath = texturePath;
    this.texturePathSnow = texturePath;
  }

  TeachingVariant(int width, int height, AssetPaths texturePath, AssetPaths texturePathSnow) {
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
    return TeachingVariant.class;
  }

}
