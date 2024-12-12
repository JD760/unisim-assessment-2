package y111studios.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import y111studios.Main;

/**
 * Displays all achievements the user has completed.
 */
public class AchievementsScreen extends ScreenWithBackground {
  private int width = 640;
  private int height = 480;
  private Stage stage;
  private Table table;
  Table completeTable = new Table(SKIN);
  Table incompleteTable = new Table(SKIN);
  private static final Skin SKIN = new Skin(Gdx.files.internal("assets/skins/default/uiskin.json"));

  /**
   * Create a new Achievements screen, displaying the complete and incomplete achievements.
   *
   * @param game - a reference to the Main class
   */
  public AchievementsScreen(Main game) {
    super(game);

    stage = new Stage(viewport);
    table = new Table(SKIN);
    table.setDebug(true);
    table.setFillParent(true);

    TextButton backButton = ScreenWithBackground.backButton(table, SKIN, game);
    completeTable.setFillParent(true);
    incompleteTable.setFillParent(true);

    table.add(completeTable);
    table.add(incompleteTable);
    table.row();
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
    this.width = width;
    this.height = height;

    completeTable.setSize(width * 0.3f, height * 0.6f);
    incompleteTable.setSize(width * 0.3f, height * 0.6f);
  }
}
