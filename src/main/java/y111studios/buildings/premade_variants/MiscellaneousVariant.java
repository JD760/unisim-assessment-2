package y111studios.buildings.premade_variants;

import y111studios.AssetPaths;
import y111studios.position.GridPosition;

/**
 * An enum representing the different predefined variants a
 * {@link y111studios.buildings.MiscellaneousBuilding MiscellaneousBuilding}.
 * This enum is used in conjunction with the
 * {@link y111studios.buildings.BuildingFactory
 * BuildingFactory} class to create instances of
 * {@link y111studios.buildings.MiscellaneousBuilding
 * MiscellaneousBuilding}.
 **/
public enum MiscellaneousVariant implements VariantProperties {
  TREE1(3, 3, AssetPaths.TREE1, AssetPaths.TREE1SNOW),
  TREE2(1, 1, AssetPaths.TREE2, AssetPaths.TREE2SNOW),
  TREE3(2, 2, AssetPaths.TREE3, AssetPaths.TREE3SNOW),
  BIKE_SHED(5, 5, AssetPaths.BIKE_SHED, AssetPaths.BIKE_SHEDSNOW),
  STRAIGHT_ROAD(2, 2, AssetPaths.STRAIGHT_ROAD, AssetPaths.STRAIGHT_ROADSNOW),
  ROAD_CROSS(2, 2, AssetPaths.ROAD_CROSS, AssetPaths.ROAD_CROSSSNOW),
  ROAD_BEND1(2, 2, AssetPaths.ROAD_BEND1, AssetPaths.ROAD_BEND1SNOW),
  ROAD_BEND2(2, 2, AssetPaths.ROAD_BEND2, AssetPaths.ROAD_BEND2SNOW);

  private final int width;
  private final int height;
  private final AssetPaths texturePath;
  private final AssetPaths texturePathSnow;
  private final AssetPaths texturePathFlood;

  MiscellaneousVariant(int width, int height, AssetPaths texturePath) {
    this.width = width;
    this.height = height;
    this.texturePath = texturePath;
    this.texturePathSnow = texturePath;
    this.texturePathFlood = texturePath;
  }

  MiscellaneousVariant(int width, int height, AssetPaths texturePath, AssetPaths texturePathSnow) {
    this.width = width;
    this.height = height;
    this.texturePath = texturePath;
    this.texturePathSnow = texturePathSnow;
    this.texturePathFlood = texturePath;
  }

  MiscellaneousVariant(int width, int height, AssetPaths texturePath, AssetPaths texturePathSnow, AssetPaths texturePathFlood) {
    this.width = width;
    this.height = height;
    this.texturePath = texturePath;
    this.texturePathSnow = texturePathSnow;
    this.texturePathFlood = texturePathFlood;
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
    return MiscellaneousVariant.class;
  }

}
