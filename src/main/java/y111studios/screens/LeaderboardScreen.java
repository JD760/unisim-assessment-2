package y111studios.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import y111studios.Main;


public class LeaderboardScreen extends ScreenWithBackground {
  private Main game;
  private Stage stage;
  private Table table;
  public static final Skin SKIN = new Skin(
      Gdx.files.internal("assets/skins/default/uiskin.json"));

  public LeaderboardScreen(Main game) {
    super(game);
    stage = new Stage(viewport);
    table = new Table(SKIN);
    Gdx.input.setInputProcessor(stage);
    
    table.setDebug(true);
    table.setFillParent(true);
    TextButton backButton = new TextButton("Return to Menu", SKIN);
    table.add(backButton);
    backButton.addListener(new InputListener() {
      public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
        game.setScreen(new StartScreen(game));
        return false;
      }
    });
    stage.addActor(table);
  }

  @Override
  public void render(float delta) {
    super.render(delta);
    stage.act();
    stage.draw();
  }
}
