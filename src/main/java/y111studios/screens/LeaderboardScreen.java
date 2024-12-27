package y111studios.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import y111studios.AssetPaths;
import y111studios.Main;
import y111studios.screens.leaderboard.Leaderboard;
import y111studios.screens.leaderboard.LeaderboardScore;


/**
 * Displays the Leaderboard.
 */
public class LeaderboardScreen extends ScreenWithBackground {
  private Leaderboard leaderboard;
  private int width = 640;
  private int height = 480;
  private final Image leaderboardTitle;
  private Label[] leaderboardRows;
  private Stage stage;
  private Table table;
  private Cell<TextButton> backButtonCell;
  private Cell<Image> titleCell;
  public static final Skin SKIN = new Skin(
      Gdx.files.internal("assets/skins/default/uiskin.json"));

  /**
   * Create a new Leaderboard Screen which draws the UI for the game leaderboard.
   *
   * @param game - a reference to the main class.
   */
  public LeaderboardScreen(Main game) {
    super(game);
    leaderboard = game.leaderboard;
    stage = new Stage(viewport);
    table = new Table(SKIN);
    table.setFillParent(true);

    leaderboardTitle = new Image(game.getAsset(AssetPaths.LEADERBOARD_TITLE));
    TextButton backButton = new TextButton("Return to Menu", SKIN);
    backButton.addListener(new InputListener() {
      public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
        Gdx.app.log("#INFO", "Back button clicked");
        game.setScreen(new StartScreen(game));
        return false;
      }
    });

    stage.addListener(new InputListener() {
      public boolean keyDown(InputEvent e, int keycode) {
        if (keycode == Keys.F) {
          Gdx.app.log("#INFO", "F key pressed");
        }
        return false;
      }
    });

    titleCell = table.add(leaderboardTitle).colspan(2);
    createLeaderboard();
    backButtonCell = table.add(backButton).colspan(2);
    stage.addActor(table);

    InputMultiplexer inputMultiplexer = new InputMultiplexer();
    inputMultiplexer.addProcessor(game.universalInputProcessor);
    inputMultiplexer.addProcessor(stage);
    Gdx.input.setInputProcessor(inputMultiplexer);
  }

  /**
   * Generates UI elements corresponding to each element in the leaderboard
   * and handles empty score slots.
   */
  private void createLeaderboard() {
    leaderboardRows = new Label[2 * Leaderboard.MAX_SIZE + 2];
    // reserve the last two spaces for the heading labels
    leaderboardRows[Leaderboard.MAX_SIZE] = new Label("-- Name --", SKIN);
    leaderboardRows[Leaderboard.MAX_SIZE + 1] = new Label("-- Score --", SKIN);
    table.row();
    table.add(leaderboardRows[Leaderboard.MAX_SIZE]);
    table.add(leaderboardRows[Leaderboard.MAX_SIZE + 1]);
    table.row();
    // programmatically create labels for each element in the leaderboard
    // this approach allows us to have leaderboards of any MAX_SIZE
    for (int i = 1; i < 2 * Leaderboard.MAX_SIZE; i += 2) {
      LeaderboardScore score = leaderboard.getScore(i / 2);
      if (score == null) {
        leaderboardRows[i] = new Label(((i  / 2) + 1) + ". -- empty --", SKIN);
        leaderboardRows[i + 1] = new Label("-- empty --", SKIN);
      } else {
        leaderboardRows[i] = new Label(((i / 2) + 1) + ". " + score.getName(), SKIN);
        leaderboardRows[i + 1] = new Label(Double.toString(score.getScore()), SKIN);
      }
      table.row();
      table.add(leaderboardRows[i]);
      table.add(leaderboardRows[i + 1]);
    }
    table.row();
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
    this.width = width;
    this.height = height;
    final float guiScale = height * 0.75f;
    backButtonCell.width(guiScale / 3).height(height * 0.06f);
    titleCell.width(height * 0.45f).height(height * 0.08f);
    for (Cell<Actor> cell : table.getCells()) {
      if (cell.getActor() instanceof TextButton) {
        ((TextButton) (cell.getActor())).getLabel().setFontScale(height * 0.0015f);
        cell.pad(height * 0.04f);
      } else if (cell.getActor() instanceof Label) {
        ((Label) (cell.getActor())).setFontScale(height * 0.0015f);
        cell.pad(height * 0.02f);
      }
    }
  }
}
