package y111studios.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import y111studios.Main;

/**
 * Displays credits for both code authors and assets used in the project.
 */
public class CreditsScreen extends ScreenWithBackground {
  private Stage stage;
  private Table table;
  private TextButton backButton;
  private static final Skin SKIN = new Skin(Gdx.files.internal("assets/skins/default/uiskin.json"));

  /**
   * Create a new credits screen.
   *
   * @param game - a reference to the Main class
   */
  public CreditsScreen(Main game) {
    super(game);
    stage = new Stage(viewport);
    table = new Table();
    table.setFillParent(true);

    backButton = new TextButton("Return to menu", SKIN);
    backButton.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
        game.setScreen(new StartScreen(game));
        return false;
      }
    });

    table.add(backButton);
    stage.addActor(table);

    InputMultiplexer inputMultiplexer = new InputMultiplexer();
    inputMultiplexer.addProcessor(game.universalInputProcessor);
    inputMultiplexer.addProcessor(stage);
    Gdx.input.setInputProcessor(inputMultiplexer);
  }

  @Override
  public void render(float delta) {
    super.render(delta);
    stage.draw();
    stage.act();
  }

  @Override
  public void resize(int width, int height) {
    super.resize(width, height);
  }
}
