package y111studios.notification;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import y111studios.AssetPaths;
import y111studios.Main;
import y111studios.screens.MapScreen;

/**
 * Draw a notification onto the screen to inform the user of, for example,
 * a new event or achievement earned.
 */
public class Notification extends Table {
  private int maxAge;
  private int age;
  private Main game;

  /**
   * Determine the style of notification to render.
   */
  public enum NotificationType {
    ACHIEVEMENT, EVENT
  }

  public static final Skin SKIN = new Skin(
      Gdx.files.internal("assets/skins/default/uiskin.json"));
  /**
   * Create a new notification displayed on the game screen.

   * @param screenWidth - the width of the screen the notification is displayed on
   * @param screenHeight - the height of the screen the notification is displayed on
   * @param type - the type of notification, a {@link NotificationType}
   * @param game - a reference to the Main class
   */
  public Notification(
      int screenWidth, int screenHeight, 
      NotificationType type, String title, String description,
      Main game, int maxAge) { 
    super();
    setDebug(true);
    this.maxAge = maxAge;
    this.game = game;
    
    //TODO: Fix the notification background
    //setBackground(bgDrawable);
    Image image = new Image(game.getAsset(AssetPaths.SNOW_EVENT));
    image.setSize(getWidth(), getHeight());
    add(image);
  }

  /* Create a Notification with the default lifetime of 250 ticks */
  public Notification(int screenWidth, int screenHeight,
      NotificationType type, String title, String description, Main game) {
    this(screenWidth, screenHeight, type, title, description, game, 250);
  }

  public void resize(int width, int height) {
    setSize(width * 0.3f, height * 0.15f);
    MapScreen screen = (MapScreen) game.getScreen();
    int infoBarHeight = (int) screen.getInfoBar().getInfoBarHeight();
    setPosition(width - getWidth(), height - getHeight() - infoBarHeight);
  }

  /**
   * Advance the age of the notification.
   *
   * @return - true if the notification should still exist, false if it should be destroyed
   */
  public boolean tick() {
    age++;

    if (age >= maxAge) {
      return false;
    }
    return true;
  }

  /**
   * When the notification is shown, set its' initial age to zero.
   */
  public void resetAge() {
    age = 0;
  }
  
}
