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
  Skin skin = new Skin(Gdx.files.internal("assets/skins/default/uiskin.json"));
  Image logo;
  Cell<Image> logoCell;
  Button playButton;
  Cell<Button> playButtonCell;
  Button leaderboardButton;
  Cell<Button> leaderboardButtonCell;
  Button achievementsButton;
  Cell<Button> acheivementsButtonCell;
  Button settingsButton;
  Cell<Button> settingsButtonCell;
  Button creditsButton;
  Cell<Button> creditsButtonCell;

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
    table.setDebug(true);
    logo = new Image(game.getAsset(AssetPaths.UNISIM_LOGO));
    final Button playButton = new TextButton("New Game", skin);
    playButton.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
        Gdx.app.log("#INFO", "Play button clicked");
        game.setScreen(new MapScreen(game));
        return false;
      }
    });
    leaderboardButton = new TextButton("Leaderboard", skin);
    leaderboardButton.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
        game.setScreen(new LeaderboardScreen(game));
        return false;
      }
    });
    achievementsButton = new TextButton("Achievements", skin);
    achievementsButton.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
        game.setScreen(new AchievementsScreen(game));
        return false;
      }
    });
    settingsButton = new TextButton("Settings", skin);
    settingsButton.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
        game.setScreen(new SettingsScreen(game));
        return false;
      }
    });
    creditsButton = new TextButton("Credits", skin);
    creditsButton.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
        game.setScreen(new CreditsScreen());
        return false;
      }
    });
    logoCell = table.add(logo).colspan(3).padLeft(width * 0.05f);
    table.row();
    playButtonCell = table.add(playButton).colspan(3)
        .pad(height * 0.02f);
    table.row().height(height * 0.25f);
    leaderboardButtonCell = table.add(leaderboardButton)
        .pad(height * 0.02f);
    acheivementsButtonCell = table.add(achievementsButton)
        .pad(height * 0.02f);
    settingsButtonCell = table.add(settingsButton)
        .pad(height * 0.02f);
    table.row();
    creditsButtonCell = table.add(creditsButton).colspan(3)
        .pad(height * 0.02f);
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
    final float guiScale = height * 0.75f;
    this.width = width;
    this.height = height;
    viewport.update(width, height, true);
    table.setSize(width, height);
    logoCell.width(height * 0.8f).height(height * 0.25f);
    playButtonCell.width(guiScale).height(height * 0.06f);
    leaderboardButtonCell.width(guiScale / 3).height(height * 0.06f);
    acheivementsButtonCell.width(guiScale / 3).height(height * 0.06f);
    settingsButtonCell.width(guiScale / 3).height(height * 0.06f);
    creditsButtonCell.width(guiScale / 3).height(height * 0.06f);
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
