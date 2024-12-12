package y111studios.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import lombok.Getter;
import y111studios.AssetPaths;
import y111studios.BuildingMenu;
import y111studios.GameState;
import y111studios.InfoBar;
import y111studios.Main;
import y111studios.UniversalInputProcessor;
import y111studios.World;
import y111studios.WorldInputProcessor;
import y111studios.buildings.BuildingFactory;
import y111studios.buildings.premade_variants.VariantProperties;
import y111studios.notification.Notification;
import y111studios.notification.Notification.NotificationType;

/**
 * A class to interact with LibGDX to render the game window.
 */
public class MapScreen extends ScreenAdapter {
  // Proportional width of the display.
  static int width = 640;
  // Proportional height of the display.
  static int height = 480;
  // Width of map in tiles.
  public static final int TILE_WIDTH = 76;
  // Height of map in tiles.
  public static final int TILE_HEIGHT = 76;

  final Main game;
  private final @Getter InfoBar infoBar;
  GameState gameState;
  Viewport viewport;
  Texture pauseMenu;
  boolean[] showDebugInfo = { false };
  World world;
  Notification notification;
  public boolean notificationShown = false;
  BuildingMenu buildingMenu;
  InputMultiplexer inputMultiplexer;
  UniversalInputProcessor universalInputProcessor = new UniversalInputProcessor();
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

    notification = new Notification(width, height, NotificationType.EVENT, game);
  
    world = new World(game, gameState, this);
    buildingMenu = new BuildingMenu(game, stage);
    infoBar = new InfoBar(gameState, game, stage);
    

    inputMultiplexer = new InputMultiplexer();
    inputMultiplexer.addProcessor(universalInputProcessor);
    inputMultiplexer.addProcessor(stage);
    inputMultiplexer.addProcessor(new WorldInputProcessor(world, buildingMenu));

    stage.addListener(new InputListener() {
      @Override
      public boolean keyDown(InputEvent e, int keycode) {
        switch (keycode) {
          case Keys.N:
            if (!notificationShown) {
              stage.addActor(notification);
            } else {
              notification.remove();
            }
            notificationShown = !notificationShown;
            notification.resetAge();
            break;
          default:
            break;
        }
        return false;
      }
    });
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

    gameState.tick();
    if (notificationShown) {
      if (!notification.tick()) {
        notification.remove();
        notificationShown = false;
      }
    }

    world.render(delta);
    buildingMenu.render();
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
    notification.resize(width, height);
    universalInputProcessor.resize(width, height);
    buildingMenu.resize(width, height);
    stage.getViewport().update(width, height, true);
    infoBar.resize(width, height);
  }

  @Override
  public void hide() {
  }

  @Override
  public void dispose() {
    game.dispose();
  }
}
