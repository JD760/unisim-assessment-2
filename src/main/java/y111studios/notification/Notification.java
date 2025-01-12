package y111studios.notification;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import lombok.Getter;
import y111studios.AssetPaths;
import y111studios.Main;

/**
 * Represents a notification drawn onto the screen when, for example, an event occurs
 * or an achevement is earned.
 */
public class Notification {
  private Main game;
  private @Getter AssetPaths path;
  private @Getter int age;
  private @Getter boolean active;
  private @Getter final int maxAge;

  /**
   * Create a new notification.
   *
   * @param maxAge - The maximum number of ticks a notification can exist for
   * @param path - The {@link AssetPaths} to the asset displayed on the notification
   * @param game - A reference to the {@link Main} class.
   */
  public Notification(int maxAge, AssetPaths path, Main game) {
    this.game = game;
    this.path = path;
    // notifications are not displayed to the screen until activated
    active = false;
    this.maxAge = maxAge;
  }

  /**
   * Increment the age of the notification.
   *
   * @return - true if the notification should still be displayed, false when it should be removed
   */
  public boolean tick() {
    age++;
    if (age > maxAge) {
      return false;
    }
    return true;
  }

  /**
   * Set the notification as active, i.e. being drawn onto the screen.
   */
  public void setActive() {
    active = true;
  }

  public Image getImage() {
    return new Image(game.getAsset(path));
  }

  public void remove() {
    age = maxAge;
  }
}
