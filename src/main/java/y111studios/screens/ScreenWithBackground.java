package y111studios.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import y111studios.AssetPaths;
import y111studios.Main;

/**
 * Extension of Screen with template code for rendering the generic background image
 * used across our UI.
 */
public abstract class ScreenWithBackground extends ScreenAdapter {
  Main game;
  ScreenViewport viewport;
  Texture background;

  /**
   * Create a new screen with the UI background image drawn behind all UI Components.
   *
   * @param game - A reference to the Main class of the program.
   */
  public ScreenWithBackground(Main game) {
    this.game = game;
    viewport = new ScreenViewport();
    background = game.assetLib.manager.get(AssetPaths.START_SCREEN.getPath());
  }

  @Override
  public void render(float delta) {
    game.spritebatch.setProjectionMatrix(viewport.getCamera().combined);
    game.spritebatch.begin();
    float backgroundWidth = (float) viewport.getScreenWidth() / viewport.getScreenHeight()
        * background.getHeight();
    game.spritebatch.draw(
        background,
        0, 0, viewport.getScreenWidth(), viewport.getScreenHeight(),
        (int) (background.getWidth() / 2.0 - backgroundWidth / 2.0), 0,
        (int) backgroundWidth, background.getHeight(),
        false, false);
    game.spritebatch.end();
  }

  public static TextButton backButton(Table table, Skin skin, Main game) {
    TextButton backButton = new TextButton("Return to Menu", skin);
    backButton.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
        game.setScreen(new StartScreen(game));
        return false;
      }
    });
    return backButton;
  }
}
