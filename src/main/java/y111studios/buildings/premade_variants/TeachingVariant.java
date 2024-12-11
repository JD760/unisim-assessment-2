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
  SMALL_CLASSROOM(3, 3, AssetPaths.TEACH1, "Small Classroom"),
  MEDIUM_CLASSROOM(3, 3, AssetPaths.TEACH2, "Medium Classroom"),
  SUBJECT_HUB(3, 2, AssetPaths.TEACH3, "Subject Hub"),
  DEPARTMENT(6, 6, AssetPaths.TEACH4, "Department"),
  LAB(3, 4, AssetPaths.TEACH5, "Lab"),;

  private final int width;
  private final int height;
  private final AssetPaths texturePath;
  private final String name;

  TeachingVariant(int width, int height, AssetPaths texturePath, String name) {
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
  public String getName(){ return name; }

  @Override
  public AssetPaths getTexturePath() {
    return texturePath;
  }

  @Override
  public Class<? extends VariantProperties> getVariantClass() {
    return TeachingVariant.class;
  }
}
