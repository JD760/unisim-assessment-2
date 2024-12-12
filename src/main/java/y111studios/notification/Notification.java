package y111studios.notification;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import y111studios.Main;

/**
 * Draw a notification onto the screen to inform the user of, for example,
 * a new event or achievement earned.
 */
public class Notification extends Table {
  Pixmap bg;
  TextureRegionDrawable bgDrawable;
  private int maxAge;
  private int age;
  

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
      int screenWidth, int screenHeight, NotificationType type, Main game, int maxAge) { 
    super();
    setDebug(true);
    this.maxAge = maxAge;

    bg = new Pixmap(20, 20, Pixmap.Format.RGB565);
    bgDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(bg)));
    bg.setColor(Color.RED);
    bg.fill();
    
    //TODO: Fix the notification background
    //setBackground(bgDrawable);
    
    add(new Label("New Achievement!", SKIN)).top().left();
    row();
    add(new Label("Achievement description", SKIN)).center();
    resize(screenWidth, screenHeight);
  }

  /* Create a Notification with the default lifetime of 250 ticks */
  public Notification(int screenWidth, int screenHeight, NotificationType type, Main game) {
    this(screenWidth, screenHeight, type, game, 250);
  }

  public void resize(int width, int height) {
    setSize(width * 0.2f, height * 0.1f);
    setPosition(width - getWidth(), height * 0.9f - getHeight());
  }

  /**
   * Free memory resources when they are no longer needed.
   */
  public void dispose() {
    bg.dispose();
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
