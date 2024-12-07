package y111studios.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.Map;

import y111studios.*;
import y111studios.buildings.BuildingCounter;
import y111studios.buildings.BuildingFactory;
import y111studios.buildings.BuildingManager;
import y111studios.buildings.BuildingType;
import y111studios.buildings.premade_variants.VariantProperties;

import static y111studios.AssetPaths.GAME_OVER;

/**
 * A class to interact with LibGDX to render the game window.
 */
public class MapScreen extends ScreenAdapter {
  // Proportional width of the display.
  static int width = 640;
  // Proportional height of the display.
  static int height = 480;
  // Width of map in tiles.
  public static final int TILE_WIDTH = 75;
  // Height of map in tiles.
  public static final int TILE_HEIGHT = 75;

  final Main game;
  private final InfoBar infoBar;
  GameState gameState;
  Viewport viewport;
  Texture pauseMenu;
  boolean[] showDebugInfo = { false };
  World world;
  BuildingMenu buildingMenu;
  InputMultiplexer inputMultiplexer;
  UniversalInputProcessor universalInputProcessor = new UniversalInputProcessor();
  UIInputProcessor uiInputProcessor;
  Stage stage = new Stage(new ScreenViewport());

  /**
   * Sets up the camera and loads the background.
   *
   * @param game Reference to game manager
   */
  public MapScreen(final Main game) {
    this.game = game;
    this.gameState = new GameState(TILE_WIDTH, TILE_HEIGHT);
    viewport = new FitViewport(width, height);
    viewport.getCamera().position.set(width / 2f, height / 2f, 0);
    viewport.getCamera().update();
    pauseMenu = game.getAsset(AssetPaths.PAUSE);

    world = new World(game, gameState);
    buildingMenu = new BuildingMenu(game, stage);
    infoBar = new InfoBar(gameState, game, stage);

    uiInputProcessor = new UIInputProcessor(
        buildingMenu, world, gameState, showDebugInfo);

    inputMultiplexer = new InputMultiplexer();
    inputMultiplexer.addProcessor(universalInputProcessor);
    inputMultiplexer.addProcessor(stage);
    inputMultiplexer.addProcessor(uiInputProcessor);
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
      world.setSelectedBuilding(BuildingFactory.createBuilding(
          variant, world.currentGridPosition(), buildingMenu.isFlipped()));
    } else {
      world.setSelectedBuilding(null);
    }
    world.render(delta);
    buildingMenu.render();
    infoBar.render();
    stage.act(delta);
    stage.draw();

    viewport.apply();
    game.spritebatch.begin();

    // Draw the pause menu if paused
    if (gameState.isPaused()) {
      game.spritebatch.setColor(1, 1, 1, 1);
      if (gameState.isTimeUp()) {
        // Covers case where the game is locked paused due to time running out
        game.spritebatch.draw(game.getAsset(GAME_OVER), world.getViewport().getWorldWidth()/2f, world.getViewport().getWorldHeight()/2);
      } else {
        game.spritebatch.draw(pauseMenu, world.getViewport().getWorldWidth()/2f , world.getViewport().getWorldHeight()/2);
      }
    }

    game.spritebatch.end();

    // Check for game over
    if (gameState.isTimeUp()) {
      gameState.pause(); // Lock pause
    }
  }

  @Override
  public void resize(int width, int height) {
    if (width == 0 || height == 0) {
      return;
    }
    viewport.update(width, height, true);
    world.resize(width, height);
    universalInputProcessor.resize(width, height);
    uiInputProcessor.resize(width, height);
    buildingMenu.resize(width, height);
    stage.getViewport().update(width, height, true);
  }

  @Override
  public void hide() {
  }

  @Override
  public void dispose() {
    game.dispose();
  }
}
