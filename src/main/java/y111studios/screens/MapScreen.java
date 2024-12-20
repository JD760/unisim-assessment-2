package y111studios.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import lombok.Getter;
import y111studios.AssetPaths;
import y111studios.BuildingMenu;
import y111studios.GameOverMenu;
import y111studios.GameState;
import y111studios.InfoBar;
import y111studios.Main;
import y111studios.World;
import y111studios.WorldInputProcessor;
import y111studios.achievements.AchievementManager;
import y111studios.buildings.BuildingFactory;
import y111studios.buildings.premade_variants.MiscellaneousVariant;
import y111studios.buildings.premade_variants.VariantProperties;
import y111studios.notification.NotificationManager;

/**
 * A class to interact with LibGDX to render the game window.
 */
public class MapScreen extends ScreenAdapter {
  // Proportional width of the display.
  private @Getter int width = 640;
  // Proportional height of the display.
  private @Getter int height = 480;
  // Width of map in tiles.
  public static final int TILE_WIDTH = 76;
  // Height of map in tiles.
  public static final int TILE_HEIGHT = 76;

  final Main game;
  private final @Getter InfoBar infoBar;
  GameState gameState;
  Viewport viewport;
  Texture pauseMenu;
  GameOverMenu gameOverMenu;
  World world;
  private Table table = new Table();
  BuildingMenu buildingMenu;
  private @Getter NotificationManager notificationManager;
  private @Getter AchievementManager achievementManager;
  InputMultiplexer inputMultiplexer;
  Stage stage = new Stage(new ScreenViewport());

  /**
   * Sets up the camera and loads the background.
   *
   * @param game Reference to game manager
   */
  public MapScreen(final Main game) {
    this.game = game;
    this.gameState = new GameState(TILE_WIDTH, TILE_HEIGHT, game);
    viewport = new FitViewport(width, height);
    viewport.getCamera().position.set(width / 2f, height / 2f, 0);
    viewport.getCamera().update();
    pauseMenu = game.getAsset(AssetPaths.PAUSE);

    world = new World(game, gameState, this);
    buildingMenu = new BuildingMenu(game, stage);
    infoBar = new InfoBar(gameState, game, stage);
    gameOverMenu = null;

    // create the notification table and align it such that notifications
    // will be stacked in the top right corner
    table.top().right();
    notificationManager = new NotificationManager(table, game);
    notificationManager.createNotification(500, AssetPaths.SNOW_EVENT);
    achievementManager = new AchievementManager(world);
    achievementManager.setupAchievements();
    stage.addActor(table);

    inputMultiplexer = new InputMultiplexer();
    inputMultiplexer.addProcessor(game.universalInputProcessor);
    inputMultiplexer.addProcessor(stage);
    inputMultiplexer.addProcessor(new WorldInputProcessor(world, buildingMenu));
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(inputMultiplexer);
  }

  @Override
  public void render(float delta) {
    game.spritebatch.setProjectionMatrix(viewport.getCamera().combined);
    if (buildingMenu.getCurrentMenuItem() >= 0 && buildingMenu.getCurrentMenuItem() < 5) {
      VariantProperties variant = buildingMenu.getBuildingVariants().get(
          buildingMenu.getCurrentMenuTab())[buildingMenu.getCurrentMenuItem()];
      if (variant == MiscellaneousVariant.ROAD_BEND2 && buildingMenu.isFlipped()) {
        world.setSelectedBuilding(BuildingFactory.createBuilding(
            MiscellaneousVariant.ROAD_BEND2_FLIPPED, world.currentGridPosition(),
            buildingMenu.isFlipped()));
      } else {
        world.setSelectedBuilding(BuildingFactory.createBuilding(
            variant, world.currentGridPosition(), buildingMenu.isFlipped()));
      }
    } else {
      world.setSelectedBuilding(null);
    }

    gameState.tick();
    if (!gameState.isPaused()) {
      notificationManager.tick();
      achievementManager.checkConditions();
    }
    if (gameState.getTimer().isTimeUp() && gameOverMenu == null) {
      gameOverMenu = new GameOverMenu(game, stage);
      buildingMenu.removeActors();
    }

    world.render(delta);
    if (gameOverMenu == null) {
      buildingMenu.render();
    } else {
      gameOverMenu.render();
    }
    infoBar.render();
    stage.act(delta);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    if (width == 0 || height == 0) {
      return;
    }
    viewport.update(width, height, true);
    stage.getViewport().update(width, height, true);
    world.resize(width, height);
    buildingMenu.resize(width, height);
    table.setSize(width, height * 0.9f);
    stage.getViewport().update(width, height, true);
    infoBar.resize(width, height);
  }

  @Override
  public void hide() {
    return;
  }

  @Override
  public void dispose() {
    game.dispose();
  }
}
