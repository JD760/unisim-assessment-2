package y111studios.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import y111studios.Main;

/**
 * Displays the settings available for the user to change.
 */
public class InstructionsScreen extends ScreenWithBackground {
  public Main game;
  private Stage stage;
  private Table table;
  private static final Skin SKIN = new Skin(Gdx.files.internal("assets/skins/default/uiskin.json"));

  private TextButton backButton;

  /**
   * Create a new Settings Screen using an instance of the game.

   * @param game - an instance of the Main class used to obtain the spritebatch.
   */
  public InstructionsScreen(Main game, Screen screen) {
    super(game);
    this.game = game;

    backButton = new TextButton("Back", SKIN);
    backButton.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
        // preserve the game state when accessing the instructions in-game
        if (screen == null) {
          game.setScreen(new StartScreen(game));
        } else {
          game.setScreen(screen);
        }
        return false;
      }
    });
    stage = new Stage(viewport);
    table = new Table();
    table.setFillParent(true);
    table.setDebug(true);

    table.add(backButton);

    stage.addActor(table);
  }

  @Override
  public void render(float delta) {
    super.render(delta);
    stage.act();
    stage.draw();
    Gdx.input.setInputProcessor(stage);
  }
}
