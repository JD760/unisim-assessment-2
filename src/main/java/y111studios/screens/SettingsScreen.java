package y111studios.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import y111studios.Main;

/**
 * Displays the settings available for the user to change.
 */
public class SettingsScreen extends ScreenWithBackground {
  public Main game;
  Stage stage;

  /**
   * Create a new Settings Screen using an instance of the game.

   * @param game - an instance of the Main class used to obtain the spritebatch.
   */
  public SettingsScreen(Main game) {
    super(game);
    this.game = game;

    stage = new Stage(viewport);
  }

  @Override
  public void render(float delta) {
    super.render(delta);
  }
}
