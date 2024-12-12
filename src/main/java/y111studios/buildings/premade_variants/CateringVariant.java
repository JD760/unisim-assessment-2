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
  FAST_FOOD(2, 3, AssetPaths.CATER1, "Fast food"),
  RESTAURANT1(2, 2, AssetPaths.CATER2, "Restaurant"),
  SUPERMARKET(8, 11, AssetPaths.CATER3, "Supermarket"),
  RESTAURANT2(2, 2, AssetPaths.CATER4, "Restaurant 2"),
  CORNER_SHOP(1, 2, AssetPaths.CATER5, "Corner shop"),;

  private final int width;
  private final int height;
  private final AssetPaths texturePath;
  private final String name;

  CateringVariant(int width, int height, AssetPaths texturePath, String name) {
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
  public String getName() {
    return name;
  }

  @Override
  public AssetPaths getTexturePath() {
    return texturePath;
  }

  @Override
  public Class<? extends VariantProperties> getVariantClass() {
    return CateringVariant.class;
  }

}
