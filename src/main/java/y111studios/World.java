package y111studios;

import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import y111studios.position.GridPosition;
import y111studios.screens.MapScreen;
import y111studios.utils.UnreachableException;
import y111studios.buildings.Building;
import y111studios.buildings.BuildingFactory;
import y111studios.buildings.ObstacleBuilding;
import y111studios.buildings.premade_variants.*;
import y111studios.events.FloodEvent;
import y111studios.events.SnowEvent;

public class World {
  // Proportional width of the display.
  private int width = 1;
  // Proportional height of the display.
  private int height = 1;
  // Width of map in tiles.
  public static final int TILE_WIDTH = 75;
  // Height of map in tiles.
  public static final int TILE_HEIGHT = 75;

  private static final Color NOT_BUILT = new Color(1f, 0.898f, 0f, 0.55f);
  private static final Color TRANSPARENT_PREVIEW = new Color(1, 1, 1, 0.475f);
  private static final Color INVALID_PREVIEW = new Color(1, 0.5f, 0.5f, 0.475f);
  private static final Color NORMAL = new Color(1, 1, 1, 1);

  private final Main game;
  private @Getter GameState gameState;
  private final Texture[] gameMap = new Texture[4];
  private final Texture[] snowyMap = new Texture[4];
  private final Texture[] floodedMap = new Texture[4];
  private @Setter Vector3 cursorScreenPos;
  private @Getter Camera camera;
  private Building selectedBuilding;
  private @Getter List<Building> buildings;
  private @Getter Viewport viewport;
  private @Setter boolean deleteMode = false;
  private @Getter MapScreen parentScreen;

  /**
   * Sets up the camera and loads the background
   *
   * @param game Reference to game manager
   */
  public World(final Main game, GameState gameState, MapScreen parentScreen) {
    viewport = new ScreenViewport();
    this.game = game;
    this.gameState = gameState;
    this.parentScreen = parentScreen;
    buildings = new LinkedList<>();
    camera = new Camera(2000, 1000, width, height);
    gameState.setCamera(camera);
    if (game != null) {
      gameMap[0] = game.getAsset(AssetPaths.MAP_BACKGROUND_TOP_LEFT);
      gameMap[1] = game.getAsset(AssetPaths.MAP_BACKGROUND_TOP_RIGHT);
      gameMap[2] = game.getAsset(AssetPaths.MAP_BACKGROUND_BOTTOM_LEFT);
      gameMap[3] = game.getAsset(AssetPaths.MAP_BACKGROUND_BOTTOM_RIGHT);
      snowyMap[0] = game.getAsset(AssetPaths.SNOWY_MAP_BACKGROUD_TOP_LEFT);
      snowyMap[1] = game.getAsset(AssetPaths.SNOWY_MAP_BACKGROUD_TOP_RIGHT);
      snowyMap[2] = game.getAsset(AssetPaths.SNOWY_MAP_BACKGROUD_BOTTOM_LEFT);
      snowyMap[3] = game.getAsset(AssetPaths.SNOWY_MAP_BACKGROUD_BOTTOM_RIGHT);
      floodedMap[0] = game.getAsset(AssetPaths.FLOODED_MAP_BACKGROUD_TOP_LEFT);
      floodedMap[1] = game.getAsset(AssetPaths.FLOODED_MAP_BACKGROUD_TOP_RIGHT);
      floodedMap[2] = game.getAsset(AssetPaths.FLOODED_MAP_BACKGROUD_BOTTOM_LEFT);
      floodedMap[3] = game.getAsset(AssetPaths.FLOODED_MAP_BACKGROUD_BOTTOM_RIGHT);
      for (ObstacleVariant variant : ObstacleVariant.values()) {
        addObject(variant, variant.getPosition(), false);
      }
    }
  }

  /**
   * Adds an object to the game.
   *
   * @param variant The object to add.
   * @return Whether the object was added.
   */
  public boolean addObject(VariantProperties variant, GridPosition coords, boolean flipped) {
    Building building = BuildingFactory.createBuilding(variant, coords, flipped);
    if (!gameState.push(building) && !(variant instanceof ObstacleVariant)) {
      return false;
    }
    int buildingHeight = coords.getY() - coords.getX();
    int index;
    for (index = 0; index < buildings.size(); index++) {
      Building current = buildings.get(index);
      if (current.getArea().getY() - current.getArea().getX() > buildingHeight) {
        break;
      }
    }
    buildings.add(index, building);
    // Prevent obstacle buildings from having the 'being built' animation
    if (variant instanceof ObstacleVariant) {
      building.setAge(y111studios.buildings.MapObject.BUILDING_TIME);
    }
    return true;
  }

  /**
   * Removes an object from the game.
   *
   * @param coords The tile coordinates of the object to remove.
   */
  public boolean removeObject(GridPosition coords) {
    if (!gameState.removePosition(coords)) {
      return false;
    }
    for (int i = 0; i < buildings.size(); i++) {
      Building current = buildings.get(i);
      if (current.contains(coords) && !(current instanceof ObstacleBuilding)) {
        buildings.remove(i);
        return true;
      }
    }
    throw new UnreachableException("State de-synced with renderOrdering");
  }

  /**
   * Converts building tile coordinates to pixel coordinates.
   * Must account for camera.scale and building depth separately.
   *
   * @param coords The tile coordinates to convert.
   * @return The pixel coordinates.
   */
  public float[] tileToPixel(GridPosition coords) {
    float pixelX = 129 + (coords.getX() + coords.getY()) * 32 - camera.x;
    float pixelY = -1343 + (coords.getX() - coords.getY()) * 16 + camera.y + (height * camera.scale);
    return new float[] { pixelX, pixelY };
  }

  /**
   * Converts pixel coordinates to tile coordinates. Must account for camera.scale
   * separately.
   *
   * @param x The x pixel coordinate to convert.
   * @param y The y pixel coordinate to convert.
   * @return A {@link GridPosition} containing the tile coordinates.
   */
  public GridPosition pixelToTile(float x, float y) {
    float sum = (x + camera.x - 129) / 32;
    float diff = (y - camera.y - (height * camera.scale) + 1343) / 16;
    int tileY = (int) ((sum - diff) / 2);
    int tileX = (int) (sum - tileY);
    try {
      return new GridPosition(tileX, tileY);
    } catch (IllegalArgumentException e) {
      return new GridPosition(10000, 10000);
    }
  }

  /**
   * Returns the current grid position of the cursor.
   *
   * @return The current grid position of the cursor.
   */
  public GridPosition currentGridPosition() {
    return pixelToTile((int) (cursorScreenPos.x * camera.scale), (int) (cursorScreenPos.y * camera.scale));
  }

  public void renderBuilding(Building building) {
    if (deleteMode && building.getArea().contains(currentGridPosition())
        && !(building instanceof ObstacleBuilding)) {
      game.spritebatch.setColor(INVALID_PREVIEW);
    } else if (building.getAge() < y111studios.buildings.MapObject.BUILDING_TIME) {
      // If the building has not been built yet, tint the colour to show that
      if (gameState.isPaused()) {
        game.spritebatch.setColor(NOT_BUILT);
      } else {
        game.spritebatch.setColor(new Color(
            NOT_BUILT.r, NOT_BUILT.g, NOT_BUILT.b,
            ((float) Math.sin(System.currentTimeMillis() * 0.005) + 3f) / 5f));
      }
    }
    Texture texture = game.getAsset(building.getTexturePath());
    float[] pixelCoords = tileToPixel(building.getArea().getOrigin());
    // draw under the cursor
    game.font.draw(game.spritebatch, "Test!", Gdx.input.getX() + 9, height - Gdx.input.getY());
    game.spritebatch.draw(texture,
        pixelCoords[0] / camera.scale,
        (pixelCoords[1] - building.getArea().getHeight() * 16) / camera.scale,
        2f * texture.getWidth() / camera.scale,
        2f * texture.getHeight() / camera.scale,
        0, 0, texture.getWidth(), texture.getHeight(),
        building.getFlipped(), false);
    game.spritebatch.setColor(NORMAL);
  }

  /**
   * Renders the world each frame.
   *
   * @param delta The time since the previous frame.
   */
  public void render(float delta) {
    // check for any achievement conditions that have been met
    GameState.getAchievementManager().checkConditions();

    game.spritebatch.setProjectionMatrix(viewport.getCamera().combined);
    viewport.apply();
    ScreenUtils.clear(0.2f, 0.6f, 0.8f, 1f);

    camera.updateZoom(delta);
    game.spritebatch.begin();
    game.spritebatch.setColor(NORMAL);
    // Draw the game map
    game.spritebatch.draw(gameMap[0], 0, 0, width, height, (int) camera.x + 1, (int) camera.y + 1,
        (int) (width * camera.scale), (int) (height * camera.scale), false, false);
    game.spritebatch.draw(gameMap[1], 0, 0, width, height, (int) camera.x - gameMap[0].getWidth() + 3,
        (int) camera.y + 1,
        (int) (width * camera.scale), (int) (height * camera.scale), false, false);
    game.spritebatch.draw(gameMap[2], 0, 0, width, height, (int) camera.x + 1,
        (int) camera.y - gameMap[0].getHeight() + 3,
        (int) (width * camera.scale), (int) (height * camera.scale), false, false);
    game.spritebatch.draw(gameMap[3], 0, 0, width, height, (int) camera.x - gameMap[0].getWidth() + 3,
        (int) camera.y - gameMap[0].getHeight() + 3,
        (int) (width * camera.scale), (int) (height * camera.scale), false, false);
    if (gameState.getCurrentEvent() instanceof SnowEvent) {
      game.spritebatch.setColor(new Color(
        1f, 1f, 1f,
        (float)Math.sqrt(gameState.getCurrentEvent().getIntensity())
      ));
      game.spritebatch.draw(snowyMap[0], 0, 0, width, height, (int) camera.x + 1, (int) camera.y + 1,
          (int) (width * camera.scale), (int) (height * camera.scale), false, false);
      game.spritebatch.draw(snowyMap[1], 0, 0, width, height, (int) camera.x - snowyMap[0].getWidth() + 3,
          (int) camera.y + 1,
          (int) (width * camera.scale), (int) (height * camera.scale), false, false);
      game.spritebatch.draw(snowyMap[2], 0, 0, width, height, (int) camera.x + 1,
          (int) camera.y - snowyMap[0].getHeight() + 3,
          (int) (width * camera.scale), (int) (height * camera.scale), false, false);
      game.spritebatch.draw(snowyMap[3], 0, 0, width, height, (int) camera.x - snowyMap[0].getWidth() + 3,
          (int) camera.y - snowyMap[0].getHeight() + 3,
          (int) (width * camera.scale), (int) (height * camera.scale), false, false);
    } else if (gameState.getCurrentEvent() instanceof FloodEvent) {
      game.spritebatch.setColor(new Color(
        1f, 1f, 1f,
        (float)Math.sqrt(gameState.getCurrentEvent().getIntensity())
      ));
      game.spritebatch.draw(floodedMap[0], 0, 0, width, height, (int) camera.x + 1, (int) camera.y + 1,
          (int) (width * camera.scale), (int) (height * camera.scale), false, false);
      game.spritebatch.draw(floodedMap[1], 0, 0, width, height, (int) camera.x - floodedMap[0].getWidth() + 3,
          (int) camera.y + 1,
          (int) (width * camera.scale), (int) (height * camera.scale), false, false);
      game.spritebatch.draw(floodedMap[2], 0, 0, width, height, (int) camera.x + 1,
          (int) camera.y - floodedMap[0].getHeight() + 3,
          (int) (width * camera.scale), (int) (height * camera.scale), false, false);
      game.spritebatch.draw(floodedMap[3], 0, 0, width, height, (int) camera.x - floodedMap[0].getWidth() + 3,
          (int) camera.y - floodedMap[0].getHeight() + 3,
          (int) (width * camera.scale), (int) (height * camera.scale), false, false);
    }

    // Render buildings
    buildings.forEach(this::renderBuilding);

    // Add building placement hologram
    if (selectedBuilding != null) {
      // Set hologram colour
      if (!gameState.canPlaceBuilding(selectedBuilding)) {
        game.spritebatch.setColor(INVALID_PREVIEW);
      } else {
        game.spritebatch.setColor(TRANSPARENT_PREVIEW);
      }

      renderBuilding(selectedBuilding); // Render hologram
      game.spritebatch.setColor(NORMAL);
    }

    if (gameState.getCurrentEvent() != null)
      gameState.getCurrentEvent().render(delta);

    game.spritebatch.end();

    // Check for game over
    if (gameState.isTimeUp()) {
      camera.velocityReset(); // Lock camera
    }
  }

  /**
   * Resizes the world object to fill the window.
   * Should be called every time the window is resized.
   *
   * @param width  - The new width of the window.
   * @param height - The new height of the window.
   */
  public void resize(int width, int height) {
    this.width = width;
    this.height = height;
    camera.resize(width, height);
    viewport.update(width, height, true);
  }

  public void setSelectedBuilding(Building building) {
    selectedBuilding = building;
    if (building != null) {
      selectedBuilding.setAge(y111studios.buildings.MapObject.BUILDING_TIME);
    }
  }

  public Main getGame() {
    return game;
  }
}
