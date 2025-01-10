package y111studios.events;

import y111studios.AssetPaths;
import y111studios.Main;
import y111studios.notification.NotificationManager;
import y111studios.screens.MapScreen;

/**
 * Represents an event, which may occur randomly throughout the game. All events
 * generate a notification which is delivered to the user when it starts
 */
public abstract class Event {
  private AssetPaths texturePath;
  private MapScreen screen;

  public abstract void render(float delta);

  public abstract float getIntensity();

  /**
   * Create a new event and set up the associated notification.
   *
   * @param game - a reference to the main class
   */
  public Event(Main game, AssetPaths texturePath) {
    this.texturePath = texturePath;
    if (game != null) {
      this.screen = (MapScreen) game.getScreen();
    }
  }

  public void setNotification() {
    NotificationManager manager = screen.getNotificationManager();
    manager.createNotification(1000, texturePath);
  }
}
