package y111studios.achievements;

import lombok.Getter;

/**
 * Represents an achievement, which requires a unique name and a conditio
 * on which it is awarded.
 */
public abstract class Achievement {
  private @Getter String name;
  private AchievementManager manager;

  /**
   * Create a new achievement.

   * @param name - the unique name for the achievement
   * @param manager - the AchievementManager handling the achievement
   */
  public Achievement(String name, AchievementManager manager) {
    this.name = name;
    this.manager = manager;

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
  public abstract void result();

  public boolean isComplete() {
    return manager.getCompletedAchievements().containsKey(name);
  }
}
