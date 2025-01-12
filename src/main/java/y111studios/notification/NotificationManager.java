package y111studios.notification;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Queue;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import y111studios.AssetPaths;
import y111studios.Main;

/**
 * Handles currently shown and available notifications, and tracks the remaining
 * lifetime of notifications.
 */
public class NotificationManager {
  private final Main game;
  private static int MAX_NOTIFICATIONS = 3;
  private ArrayList<Cell<Image>> notificationCells = new ArrayList<>(MAX_NOTIFICATIONS);
  private ArrayList<Notification> activeNotifications = new ArrayList<>(MAX_NOTIFICATIONS);
  private Queue<Notification> queuedNotifications = new Queue<>();

  /**
   * Create a new Notification Manager drawing notifications into the provided table.
   * This class does not control the layout of the table.
   *
   * @param table - A reference to a table in which the notifications are drawn
   * @param game - A reference to the Main class
   */
  public NotificationManager(Table table, Main game) {
    this.game = game;
    // create notification slots
    for (int i = 0; i < MAX_NOTIFICATIONS; i++) {
      //Cell<Image> cell = table.add(new Image(game.getAsset(AssetPaths.SNOW_EVENT)));
      Cell<Image> cell = table.add(new Image());
      cell.pad(0.02f);
      notificationCells.add(cell);
      // once we have a reference to the cell created, set its' contents to null so we can
      // treat it as empty.
      cell.setActor(null);
      table.row();
    }
  }

  /**
   * Called on each render loop, increments the age of all active notifications and removes
   * those that have exceeded their maximum age.
   */
  public void tick() {
    int size = activeNotifications.size();
    for (Iterator<Notification> it = activeNotifications.iterator(); it.hasNext();) {
      Notification notification = it.next();
      if (notification == null) {
        it.remove();
      }
    }
    if (size != activeNotifications.size()) {
      Gdx.app.log("#INFO", "Null items removed");
    }

    for (int i = 0; i < activeNotifications.size(); i++) {
      Notification notification = activeNotifications.get(i);
      if (notification == null) {
        continue;
      }
      if (!notification.tick()) {
        activeNotifications.set(i, null);
      }
    }

    while (activeNotifications.size() < MAX_NOTIFICATIONS && queuedNotifications.size > 0) {
      activeNotifications.add(queuedNotifications.removeFirst());
    }

    // sort the active notifications by the remaining time. This is the natural way
    // to display notifications in a queue style.
    activeNotifications.sort(new NotificationComparator());

    drawNotifications();
  }

  /**
   * Called on each tick, maps each member of activeNotifications to a Cell in the table.
   */
  private void drawNotifications() {
    if (activeNotifications.size() == 0) {
      return;
    }

    for (int i = 0; i < activeNotifications.size(); i++) {
      Notification notification = activeNotifications.get(i);
      // we know this always exists as we prefill all these values up to MAX_NOTIFICATIONS
      Cell<Image> cell = notificationCells.get(i);

      if (notification == null) {
        cell.setActor(null);
      } else {
        Image image = notification.getImage();
        image.addListener(new ClickToDismissListener(notification));
        cell.setActor(image);
      }
    }
  }

  /**
   * Create a new notification and insert it into the queue.
   *
   * @param maxAge - the number of ticks before the notification is removed
   * @param path - the path to the asset to draw on the notification
   */
  public void createNotification(int maxAge, AssetPaths path) {
    Gdx.app.log("#INFO", "Notification Created!");
    if (path == null) {
      throw new IllegalArgumentException("Notification must have a non-null asset path");
    }

    Notification notification = new Notification(maxAge, path, game);
    queuedNotifications.addLast(notification);
  }

  /**
   * Called whenever the window size changes.
   *
   * @param width - the new width of the window
   * @param height - the new height of the window
   */
  public void resize(int width, int height) {
    for (Cell<Image> cell : notificationCells) {
      cell.width(height * 0.4f).height(height * 0.2f);
    }
  }

  /**
   * Used to sort the activeNotifications array based on remaining time.
   */
  class NotificationComparator implements Comparator<Notification> {
    public int compare(Notification notification, Notification otherNotification) {
      // push null notifications to the bottom of the list.
      if (notification == null) {
        return 1;
      } else if (otherNotification == null) {
        return -1;
      }


      return (notification.getMaxAge() - notification.getAge()) 
        - (otherNotification.getMaxAge() - otherNotification.getAge());
    }
  }

  class ClickToDismissListener extends InputListener {
    private Notification notification;

    public ClickToDismissListener(Notification notification) {
      this.notification = notification;
    }

    public boolean touchDown(InputEvent e, float x, float y, int pointer, int parent) {
      notification.remove();
      return false;
    }
  }
}
