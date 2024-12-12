package y111studios.buildings.premade_variants;

import lombok.Getter;
import y111studios.AssetPaths;
import y111studios.buildings.ObstacleBuilding;
import y111studios.position.GridPosition;

/**
 * An enum representing the different predefined variants an
 * {@link ObstacleBuilding ObstacleBuilding}.
 * This enum is used in conjunction with the
 * {@link y111studios.buildings.BuildingFactory
 * BuildingFactory} class to create instances of {@link ObstacleBuilding
 * AccommodationBuilding}.
 */
public enum ObstacleVariant implements VariantProperties {
  OBSTACLE1(12, 14, 11, 35, AssetPaths.OBSTACLE1, "obstacle1"),
  OBSTACLE2(8, 12, 55, 49, AssetPaths.OBSTACLE2, "obstacle2"),
  OBSTACLE3(7, 7, 15, 16, AssetPaths.OBSTACLE3, "obstacle3"),
  OBSTACLE4(17, 19, 43, 12, AssetPaths.OBSTACLE4, "obstacle4");

  private final int width;
  private final int height;
  @Getter
  private final GridPosition position;
  private final AssetPaths texturePath;
  private final String name;

  ObstacleVariant(int width, int height, int xPos, int yPos, AssetPaths texturePath, String name) {
    this.width = width;
    this.height = height;
    this.position = new GridPosition(xPos, yPos);
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
    return ObstacleVariant.class;
  }

  @Override
  public String getName() {
    return name;
  }
}
