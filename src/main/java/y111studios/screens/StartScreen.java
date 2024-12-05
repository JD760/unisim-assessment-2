package y111studios.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import y111studios.AssetPaths;
import y111studios.Main;

/**
 * The initial screen when the game is started.
 */
public class StartScreen extends ScreenAdapter {

  final Main game;

  Texture background;

  OrthographicCamera camera;
  ScreenViewport viewport;
  int width = 640;
  int height = 480;
  Stage stage;
  Table table;
  Cell<Image> startScreenCell;
  Skin skin = new Skin(Gdx.files.internal("assets/skins/default/uiskin.json"));

  /**
   * Sets up the camera and loads the background.
   *
   * @param game reference to game manager
   */
  public StartScreen(final Main game) {
    this.game = game;
    viewport = new ScreenViewport();
    camera = (OrthographicCamera) viewport.getCamera();
    background = game.assetLib.manager.get(AssetPaths.START_SCREEN.getPath());

    stage = new Stage(viewport);
    stage.addListener(new InputListener() {
      @Override
      public boolean keyDown(InputEvent event, int keycode) {
        if (keycode == Keys.SPACE) {
          game.setScreen(new MapScreen(game));
        }
        return false;
      }
    });
    Gdx.input.setInputProcessor(stage);
    createMenu();
  }

  private void createMenu() {
    table = new Table();
    table.setFillParent(true);
    //table.setDebug(true);
    final Image logo = new Image(game.getAsset(AssetPaths.UNISIM_LOGO));
    final Button playButton = new TextButton("New Game", skin);
    playButton.addListener(new InputListener() {
        @Override
        public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
          Gdx.app.log("#INFO", "Play button clicked");
          game.setScreen(new MapScreen(game));
          return false;
        }
      });
    final Button leaderboardButton = new TextButton("Leaderboard", skin);
    leaderboardButton.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
        game.setScreen(new LeaderboardScreen(game));
        return false;
      }
    });
    final Button achievementsButton = new TextButton("Achievements", skin);
    achievementsButton.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
        game.setScreen(new AchievementsScreen(game));
        return false;
      }
    });
    final Button settingsButton = new TextButton("Settings", skin);
    settingsButton.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
        game.setScreen(new SettingsScreen(game));
        return false;
      }
    });
    final Button creditsButton = new TextButton("Credits", skin);
    creditsButton.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
        game.setScreen(new CreditsScreen());
        return false;
      }
    });
    table.add(logo).colspan(2).padLeft(width * 0.05f);
    table.row();
    table.add(playButton)
        .width(width * 0.4f)
        .height(height * 0.1f)
        .pad(height * 0.02f)
        .colspan(2)
        .center();
    table.row().height(height * 0.25f);
    table.add(leaderboardButton)
        .width((int) (width * 0.15))
        .height((int) (height * 0.1))
        .pad(0f);
    table.add(achievementsButton)
        .width((int) (width * 0.15))
        .height((int) (height * 0.1))
        .pad(0);
    table.add(settingsButton)
        .width((int) (width * 0.15))
        .height((int) (height * 0.1))
        .pad(0f);
    table.row();
    table.add(creditsButton)
        .width((int) (width * 0.085))
        .height((int) (height * 0.05))
        .center()
        .colspan(2)
        .padTop(height * 0.02f);
    stage.addActor(table);
  }

  @Override
  public void show() {
  }

  @Override
  public void render(float delta) {
    ScreenUtils.clear(0, 0, 0.2f, 0);
    game.spritebatch.setProjectionMatrix(camera.combined);

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

    stage.act();
    stage.draw();
    camera.update();
  }

  @Override
  public void resize(int width, int height) {
    this.width = width;
    this.height = height;
    viewport.update(width, height, true);
    table.setSize(width, height);
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
