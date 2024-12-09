package y111studios.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import y111studios.GameState;
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
    table.setDebug(true);
    table.setFillParent(true);
    table.add("Hello World!");
    stage.addActor(table);
  }

  @Override
  public void render(float delta) {
    super.render(delta);
    stage.act();
    stage.draw();
  }
}
