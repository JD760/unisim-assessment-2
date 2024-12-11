package y111studios.notification;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Draw a notification onto the screen to inform the user of, for example,
 * a new event or achievement earned.
 */
public class Notification extends Dialog {
  /**
   * Determine the style of notification to render.
   */
  public enum NotificationType {
    ACHIEVEMENT, EVENT
  }

  public static final Skin SKIN = new Skin(
      Gdx.files.internal("assets/skins/default/uiskin.json"));

  public Notification(int screenWidth, int screenHeight, NotificationType type) {
    super(type == NotificationType.ACHIEVEMENT ? "Achievement!" : "New Event!", SKIN);
    setScreenSize(screenWidth, screenHeight);
  }

  public void setScreenSize(int screenWidth, int screenHeight) {
    setSize(screenWidth * 0.1f, screenHeight * 0.1f);
    setPosition(screenWidth - getWidth(), screenHeight * 0.9f - getHeight());
  }
}
