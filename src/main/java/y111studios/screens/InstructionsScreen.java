package y111studios.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import y111studios.AssetPaths;
import y111studios.Main;

/**
 * Displays the settings available for the user to change.
 */
public class InstructionsScreen extends ScreenWithBackground {
  public Main game;
  private Stage stage;
  private Table table;
  private static final Skin SKIN = new Skin(Gdx.files.internal("assets/skins/default/uiskin.json"));
  private Cell<Actor> logoCell;
  private Cell<Actor> backButtonCell;
  private TextButton backButton;

  /**
   * Create a new Settings Screen using an instance of the game.

   * @param game - an instance of the Main class used to obtain the spritebatch.
   */
  public InstructionsScreen(Main game) {
    super(game);
    this.game = game;

    backButton = new TextButton("Return to Menu", SKIN);
    backButton.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
        game.setScreen(new StartScreen(game));
        return false;
      }
    });
    stage = new Stage(viewport);
    table = new Table();
    table.setFillParent(true);

    Image logo = new Image(game.getAsset(AssetPaths.INSTRUCTIONS));
    logoCell = table.add(logo);
    table.row();
    backButtonCell = table.add(backButton);

    stage.addActor(table);

    InputMultiplexer inputMultiplexer = new InputMultiplexer();
    inputMultiplexer.addProcessor(game.universalInputProcessor);
    inputMultiplexer.addProcessor(stage);
    Gdx.input.setInputProcessor(inputMultiplexer);
  }

  @Override
  public void render(float delta) {
    super.render(delta);
    stage.act();
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    super.resize(width, height);
    logoCell.width(height * 0.8f).height(height * 0.8f).pad(height * 0.025f);
    final float guiScale = height * 0.75f;
    backButtonCell.width(guiScale / 3).height(height * 0.06f);
    for (Cell<Actor> cell : table.getCells()) {
      if (cell.getActor() instanceof TextButton) {
        ((TextButton)(cell.getActor())).getLabel().setFontScale(height * 0.0015f);
      }
    }
  }
}
