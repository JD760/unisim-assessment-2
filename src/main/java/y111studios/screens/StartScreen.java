package y111studios.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import y111studios.AssetPaths;
import y111studios.Main;

/**
 * The initial screen when the game is started.
 */
public class StartScreen extends ScreenAdapter {

  final Main game;

  Texture startScreen;

  OrthographicCamera camera;
  ScreenViewport viewport;
  Stage stage;
  Table backgroundImageTable;
  Cell<Image> startScreenCell;

  /**
   * Sets up the camera and loads the background.
   *
   * @param game reference to game manager
   */
  public StartScreen(final Main game) {
    this.game = game;
    viewport = new ScreenViewport();
    camera = (OrthographicCamera) viewport.getCamera();
    camera.setToOrtho(false, 640, 480);
    startScreen = game.assetLib.manager.get(AssetPaths.START_SCREEN.getPath());

    stage = new Stage(viewport);
    backgroundImageTable = new Table();
    backgroundImageTable.setDebug(true);
    startScreenCell = backgroundImageTable.add(new Image(startScreen)).center().fill();
    //stage.addActor(backgroundImageTable);
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(new InputAdapter() {
      @Override
      public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.SPACE) {
          game.setScreen(new MapScreen(game));
        }
        return true;
      }
    });
  }

  @Override
  public void render(float delta) {
    ScreenUtils.clear(0, 0, 0.2f, 0);
    game.spritebatch.setProjectionMatrix(camera.combined);

    game.spritebatch.begin();
    game.spritebatch.draw(
        startScreen,
        0, 0, viewport.getScreenWidth(), viewport.getScreenHeight(),
        0, 0, startScreen.getWidth(), startScreen.getHeight(),
        false, false);
    game.spritebatch.end();

    stage.act();
    stage.draw();
    camera.update();
  }

  @Override
  public void resize(int width, int height) {
    viewport.update(width, height, true);
    backgroundImageTable.setSize(width, height);
  }

  @Override
  public void hide() {
    Gdx.input.setInputProcessor(null);
  }

  @Override
  public void dispose() {
    game.dispose();
  }

}
