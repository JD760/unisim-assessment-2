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
  PARK(2, 3, AssetPaths.REC1, "Park"),
  GYM(3, 5, AssetPaths.REC2, "Gym"),;

  private final int width;
  private final int height;
  private final AssetPaths texturePath;
  private final String name;

  RecreationVariant(int width, int height, AssetPaths texturePath, String name) {
    this.width = width;
    this.height = height;
    this.texturePath = texturePath;
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

  @Override
  public Class<? extends VariantProperties> getVariantClass() {
    return RecreationVariant.class;
  }

  @Override
  public String getName(){
    return name;
  }

}
