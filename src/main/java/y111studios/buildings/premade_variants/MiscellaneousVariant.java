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

  TREE1(3, 3, AssetPaths.TREE1,"Round Tree", AssetPaths.TREE1SNOW),
  TREE2(1, 1, AssetPaths.TREE2,"Pine Tree", AssetPaths.TREE2SNOW),
  TREE3(2, 2, AssetPaths.TREE3,"Oak Tree", AssetPaths.TREE3SNOW),
  BIKE_SHED(5, 5, AssetPaths.BIKE_SHED, "Bike Shed", AssetPaths.BIKE_SHEDSNOW),
  STRAIGHT_ROAD(2, 2, AssetPaths.STRAIGHT_ROAD, "Straight Road", AssetPaths.STRAIGHT_ROADSNOW),
  ROAD_CROSS(2, 2, AssetPaths.ROAD_CROSS,"Cross Road", AssetPaths.ROAD_CROSSSNOW),
  ROAD_BEND1(2, 2, AssetPaths.ROAD_BEND1,"Road Bend 1", AssetPaths.ROAD_BEND1SNOW),
  ROAD_BEND2(2, 2, AssetPaths.ROAD_BEND2,"Road Bend 2", AssetPaths.ROAD_BEND2SNOW),
  ROAD_BEND2_FLIPPED(2, 2, AssetPaths.ROAD_BEND2_FLIPPED, "Road Bend 2");


  private final int width;
  private final int height;
  private final AssetPaths texturePath;
  private final AssetPaths texturePathSnow;
  private final AssetPaths texturePathFlood;
  private final String name;

  MiscellaneousVariant(int width, int height, AssetPaths texturePath, String name) {
    this.width = width;
    this.height = height;
    this.texturePath = texturePath;
    this.texturePathSnow = texturePath;
    this.texturePathFlood = texturePath;
    this.name = name;
  }

  MiscellaneousVariant(int width, int height, AssetPaths texturePath, String name, AssetPaths texturePathSnow) {
    this.width = width;
    this.height = height;
    this.texturePath = texturePath;
    this.texturePathSnow = texturePathSnow;
    this.texturePathFlood = texturePath;
    this.name = name;
  }

  MiscellaneousVariant(int width, int height, AssetPaths texturePath, String name, AssetPaths texturePathSnow, AssetPaths texturePathFlood) {
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
    return MiscellaneousVariant.class;
  }

  @Override
  public String getName() {
    return name;
  }
}
