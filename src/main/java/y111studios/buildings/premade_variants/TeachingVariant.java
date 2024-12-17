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
  SMALL_CLASSROOM(3, 3, AssetPaths.TEACH1, "Small Classroom", AssetPaths.TEACH1SNOW),
  MEDIUM_CLASSROOM(3, 3, AssetPaths.TEACH2, "Medium Classroom", AssetPaths.TEACH2SNOW),
  SUBJECT_HUB(3, 2, AssetPaths.TEACH3, "Subject Hub", AssetPaths.TEACH3SNOW),
  DEPARTMENT(6, 6, AssetPaths.TEACH4, "Department", AssetPaths.TEACH4SNOW),
  LAB(3, 4, AssetPaths.TEACH5, "Lab", AssetPaths.TEACH5SNOW),;


  private final int width;
  private final int height;
  private final AssetPaths texturePath;
  private final AssetPaths texturePathSnow;
  private final AssetPaths texturePathFlood;
  private final String name;

  TeachingVariant(int width, int height, AssetPaths texturePath, String name) {
    this.width = width;
    this.height = height;
    this.texturePath = texturePath;
    this.texturePathSnow = texturePath;
    this.texturePathFlood = texturePath;
    this.name = name;
  }

  TeachingVariant(int width, int height, AssetPaths texturePath, String name, AssetPaths texturePathSnow) {
    this.width = width;
    this.height = height;
    this.texturePath = texturePath;
    this.texturePathSnow = texturePathSnow;
    this.texturePathFlood = texturePath;
    this.name = name;
  }

  TeachingVariant(int width, int height, AssetPaths texturePath, String name, AssetPaths texturePathSnow, AssetPaths texturePathFlood) {
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
  public String getName(){ return name; }

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
    return TeachingVariant.class;
  }
}
