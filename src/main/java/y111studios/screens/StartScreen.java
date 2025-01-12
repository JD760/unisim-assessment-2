package y111studios.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import y111studios.AssetPaths;
import y111studios.Main;

/**
 * The initial screen when the game is started.
 */
public class StartScreen extends ScreenWithBackground {
  final Main game;
  Texture background;
  Stage stage;
  Table table;
  Skin skin = new Skin(Gdx.files.internal("assets/skins/default/uiskin.json"));
  Image logo;
  Cell<Image> logoCell;
  Button playButton;
  Cell<Button> playButtonCell;
  Button leaderboardButton;
  Cell<Button> leaderboardButtonCell;
  Button instructionsButton;
  Cell<Button> instructionsButtonCell;
  Button quitButton;
  Cell<Button> quitButtonCell;

  /**
   * Sets up the camera and loads the background.
   *
   * @param game reference to game manager
   */
  public StartScreen(final Main game) {
    super(game);
    this.game = game;

    stage = new Stage(viewport);

    createMenu();
    InputMultiplexer inputMultiplexer = new InputMultiplexer();
    inputMultiplexer.addProcessor(game.universalInputProcessor);
    inputMultiplexer.addProcessor(stage);
    Gdx.input.setInputProcessor(inputMultiplexer);
  }

  private void createMenu() {
    table = new Table();
    table.setFillParent(true);
    logo = new Image(game.getAsset(AssetPaths.UNISIM_LOGO));
    final Button playButton = new TextButton("New Game", skin);
    playButton.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
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
    instructionsButton = new TextButton("Instructions", skin);
    instructionsButton.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
        game.setScreen(new InstructionsScreen(game, null));
        return false;
      }
    });
    quitButton = new TextButton("Quit", skin);
    quitButton.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
        Gdx.app.exit();
        return false;
      }
    });
    logoCell = table.add(logo).colspan(2);
    table.row();
    playButtonCell = table.add(playButton).colspan(2);
    table.row().height(viewport.getScreenHeight() * 0.25f);
    leaderboardButtonCell = table.add(leaderboardButton);
    instructionsButtonCell = table.add(instructionsButton);
    table.row();
    quitButtonCell = table.add(quitButton).colspan(2);
    stage.addActor(table);
  }

  @Override
  public void show() {
  }

  @Override
  public void render(float delta) {
    super.render(delta);
    stage.act();
    stage.draw();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void resize(int width, int height) {
    super.resize(width, height);
    final float guiScale = height * 0.75f;
    table.setSize(width, height);
    logoCell.width(height * 0.8f).height(height * 0.25f);
    playButtonCell.width(guiScale).height(height * 0.06f);
    leaderboardButtonCell.width(guiScale / 2).height(height * 0.06f);
    instructionsButtonCell.width(guiScale / 2).height(height * 0.06f);
    quitButtonCell.width(guiScale / 2).height(height * 0.06f);
    for (Cell<Actor> cell : table.getCells()) {
      if (cell.getActor() instanceof TextButton) {
        ((TextButton) (cell.getActor())).getLabel().setFontScale(height * 0.0015f);
      }
    }
    playButtonCell.pad(viewport.getScreenHeight() * 0.02f);
    leaderboardButtonCell.pad(viewport.getScreenHeight() * 0.02f);
    instructionsButtonCell.pad(viewport.getScreenHeight() * 0.02f);
    quitButtonCell.pad(viewport.getScreenHeight() * 0.02f);
  }

  @Override
  public void hide() {
  }

  @Override
  public void dispose() {
    game.dispose();
  }

}
