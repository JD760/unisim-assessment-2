package y111studios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import lombok.Getter;
import y111studios.screens.StartScreen;
import y111studios.utils.Score;
/**
 * A class to interact with LibGDX to render the game window.
 */
public class GameOverMenu {
  private final Main game;
  private final Texture menuBackground;
  private @Getter Viewport viewport;
  private Table titleTable;
  private Table mainTable;
  private TextField nameInput;
  private Cell<Actor> nameInputCell;
  private TextButton backButton;
  private Cell<Actor> backButtonCell;
  public static final Skin SKIN = new Skin(
      Gdx.files.internal("assets/skins/default/uiskin.json"));

  /**
   * Sets up the camera and loads the background.
   *
   * @param game Reference to game manager
   */
  public GameOverMenu(GameState gameState, final Main game, Stage stage) {
    this.game = game;
    viewport = stage.getViewport();
    menuBackground = game.getAsset(AssetPaths.MENU_BACKGROUND);

    titleTable = new Table();
    titleTable.add(new Label("Game Over", SKIN));

    mainTable = new Table();
    nameInput = new TextField("", SKIN);
    nameInput.setMessageText("Name");
    nameInputCell = mainTable.add(nameInput);
    mainTable.row();
    backButton = new TextButton("Save Score and go Home", SKIN);
    backButton.addListener(new ClickListener() {
      @Override
      public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
        String name = nameInput.getText();
        name = name == "" ? "Unnamed Player" : name;
        Score score = new Score(name, gameState.getStudentSatisfaction().getSatisfaction());
        GameState.getLeaderboard().insertScore(score);
        gameState.setScreen(new StartScreen(game));
      }
    });
    backButtonCell = mainTable.add(backButton);

    stage.addActor(titleTable);
    stage.addActor(mainTable);
    resize(stage.getViewport().getScreenWidth(), stage.getViewport().getScreenHeight());
  }

  /**
   * Renders the background of the menu.
   */
  public void render() {
    game.spritebatch.setProjectionMatrix(viewport.getCamera().combined);
    viewport.apply();
    game.spritebatch.begin();

    // Draw the menu background
    float menuHeight = viewport.getScreenHeight() * 0.15f;
    game.spritebatch.draw(menuBackground, 0, 0, viewport.getScreenWidth(), menuHeight, 0, 0, 1,
      menuBackground.getHeight(), false, false);

    game.spritebatch.end();
  }

  /**
   * Handles resizing of the game window.
   *
   * @param width  The new width of the window.
   * @param height The new height of the window.
   */
  @SuppressWarnings("unchecked")
  public void resize(int width, int height) {
    titleTable.setBounds(0, height * 0.08f, width, height * 0.11f);
    for (Cell<Actor> cell : titleTable.getCells()) {
      cell.width(
          viewport.getScreenHeight() * 0.14f).height(viewport.getScreenHeight() * 0.025f);
          ((Label)cell.getActor()).setFontScale(viewport.getScreenHeight() * 0.0016f);
    }

    mainTable.setBounds(0, 0, width, height * 0.08f);
    nameInputCell.height(height * 0.03f).width(height * 0.14f);
    backButtonCell.pad(height * 0.02f);
  }

  public int getScreenHeight() {
    return (int) (viewport.getScreenHeight() * 0.15f);
  }
}
