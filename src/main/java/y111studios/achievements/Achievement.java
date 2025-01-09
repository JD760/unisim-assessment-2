package y111studios.achievements;

import lombok.Getter;
import y111studios.AssetPaths;
import y111studios.GameState;
import y111studios.World;
import y111studios.screens.MapScreen;

/**
 * Represents an achievement, which requires a unique name and a conditio
 * on which it is awarded.
 */
public abstract class Achievement {
  private World world;
  private @Getter String name;
  private AchievementManager manager;
  private @Getter String displayName;
  private @Getter String description;
  private final int scoreContribution;
  private AssetPaths notificationPath;

  /**
   * Create a new achievement.

   * @param name - the unique name for the achievement
   * @param manager - the AchievementManager handling the achievement
   */
  public Achievement(
      String name, AchievementManager manager, String displayName, String description,
      int scoreContribution, World world, AssetPaths notificationPath) {
    this.name = name;
    this.manager = manager;
    this.displayName = displayName;
    this.description = description;
    this.scoreContribution = scoreContribution;
    this.world = world;
    this.notificationPath = notificationPath;

    if (manager.getAchievements().containsKey(name)) {
      throw new IllegalArgumentException("Achievement names must be unique");
    }
  }

  /**
   * The condition on which the achievement is granted. This is checked every tick
   * and as soon as true is returned, the achievement will be awarded.
   *
   * @return - true if the achievement should be awarded, false otherwise.
   */
  public abstract boolean condition();

  /**
   * The effect of the achievement being awarded - this is run once when the achievement
   * is marked as completed.
   */
  public void result() {
    MapScreen screen = (MapScreen) world.getGame().getScreen();
    screen.getNotificationManager().createNotification(500, notificationPath);
    GameState gameState = world.getGameState();
    gameState.getScoreManager().addScore(scoreContribution);
  }

  public boolean isComplete() {
    return manager.getCompletedAchievements().containsKey(name);
  }
}
