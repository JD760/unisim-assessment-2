package y111studios.notification;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import y111studios.AssetPaths;
import y111studios.Main;

/**
 * Draw a notification onto the screen to inform the user of, for example,
 * a new event or achievement earned.
 */
public class Notification extends Table {
  private int maxAge;
  private int age;
  private int screenWidth;
  private int screenHeight;

  public static final Skin SKIN = new Skin(
      Gdx.files.internal("assets/skins/default/uiskin.json"));
  /**
   * Create a new notification displayed on the game screen.

   * @param screenWidth - the width of the screen the notification is displayed on
   * @param screenHeight - the height of the screen the notification is displayed on
   * @param path - the path to the image that should be drawn
   * @param game - a reference to the Main class
   */
  public Notification(
      int screenWidth, int screenHeight, AssetPaths path, Main game, int maxAge) { 
    super();

    Gdx.app.log("#INFO", "Notification created with args: " + screenWidth + " , " + screenHeight + ", " + path.toString() + ", " + maxAge);

    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
    setDebug(true);
    this.maxAge = maxAge;
    
    Image image = new Image(game.getAsset(path));
    image.setSize(getWidth(), getHeight());
    add(image);
  }

  /* Create a Notification with the default lifetime of 250 ticks */
  public Notification(int screenWidth, int screenHeight, AssetPaths path, Main game) {
    this(screenWidth, screenHeight, path, game, 250);
  }

  /**
   * Called whenever the screen size changes. Adjusts the scale of UI elements to ensure
   * consistency when resizing.
   *
   * @param width - the new width of the screen
   * @param height - the new height of the screen
   */
  public void resize(int width, int height) {
    setSize(width * 0.3f, height * 0.15f);
    setPosition(width - (getWidth() * 0.85f), height * 0.85f - (getHeight() / 2));
    //setSize(400, 200);
    //setPosition(0, 0);
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
