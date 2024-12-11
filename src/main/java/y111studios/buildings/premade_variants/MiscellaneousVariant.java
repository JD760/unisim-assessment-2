package y111studios.buildings.premade_variants;

import y111studios.AssetPaths;

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
  TREE1(1, 1, AssetPaths.TREE1),
  TREE2(1, 1, AssetPaths.TREE2),
  TREE3(1, 1, AssetPaths.TREE3),
  BIKE_SHED(5, 3, AssetPaths.BIKE_SHED),
  STRAIGHT_ROAD(2, 2, AssetPaths.STRAIGHT_ROAD),
  ROAD_CROSS(2, 2, AssetPaths.ROAD_CROSS),
  ROAD_BEND1(2, 2, AssetPaths.ROAD_BEND1),
  ROAD_BEND2(2, 2, AssetPaths.ROAD_BEND2);

  private final int width;
  private final int height;
  private final AssetPaths texturePath;

  MiscellaneousVariant(int width, int height, AssetPaths texturePath) {
    this.width = width;
    this.height = height;
    this.texturePath = texturePath;
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

  @Override
  public Class<? extends VariantProperties> getVariantClass() {
    return MiscellaneousVariant.class;
  }

}
