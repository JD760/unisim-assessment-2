package y111studios.notification;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import y111studios.AssetPaths;
import y111studios.Main;

/**
 * Draw a notification onto the screen to inform the user of, for example,
 * a new event or achievement earned.
 */
public class Notification extends Dialog {
  private Image achievementNotification;
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
  public Notification(int screenWidth, int screenHeight, NotificationType type, Main game) {
    super(type == NotificationType.ACHIEVEMENT ? "Achievement!" : "New Event!", SKIN);

    achievementNotification = new Image((Texture) game.assetLib.manager.get(
      AssetPaths.ACHIEVEMENT_NOTIFICATION.getPath()));
    
    //addActor(achievementNotification);
    setScreenSize(screenWidth, screenHeight);
  }

  public void setScreenSize(int screenWidth, int screenHeight) {
    setSize(screenWidth * 0.1f, screenHeight * 0.1f);
    setPosition(screenWidth - getWidth(), screenHeight * 0.9f - getHeight());
  }
}
