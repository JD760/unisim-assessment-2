package y111studios.achievements;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import y111studios.AssetPaths;
import y111studios.World;

/**
 * Contains a collection of all achievements and checks whether they have been met on each
 * pass of the game loop.
 */
public class AchievementManager {
  World world;
  private @Getter Map<String, Achievement> achievements;
  private @Getter Map<String, Achievement> completedAchievements;

  /**
   * Create a new achievement manager to handle checking for completion and awarding achievements.
   *
   * @param world - a reference to the {@link World}.
   */
  public AchievementManager(World world) {
    this.world = world;
    achievements = new HashMap<>();
    completedAchievements = new HashMap<>();
  }

  /**
   * Add a new achievement to the manager. Achievements must have unique names.
   *
   * @param achievement - the achievement to add.
   * @throws IllegalArgumentException - If there already exists an achievement with the same name
   */
  public void add(Achievement achievement) {
    if (achievements.containsKey(achievement.getName())) {
      throw new IllegalArgumentException("Achievement names must be unique");
    }
    achievements.put(achievement.getName(), achievement);
  }

  /**
   * Check the conditions of all achievements and award any that have met their requirements. 
   */
  public void checkConditions() {
    for (String key : achievements.keySet()) {
      Achievement achievement = achievements.get(key);
      if (achievement.condition()) {
        awardAchievement(achievement);
      }
    }
  }

  /**
   * Award an achievement to the user by applying the result defined by the achievement
   * and marking it as completed.
   *
   * @param achievement - the achievement to award.
   */
  public void awardAchievement(Achievement achievement) {
    achievements.remove(achievement.getName());
    completedAchievements.put(achievement.getName(), achievement);
    // apply the consequences of the achievement
    achievement.result();
    return;
  }

  /**
   * Add the achievements to the manager.
   */
  public void setupAchievements() {
    add(new SatisfactionAchievement(
        "satisfactionAchievement", this, world, AssetPaths.SATISFACTION_ACHIEVEMENT));
    add(new BuildingHoarderAchievement(
        "buildingHoarderAchievement", this, world, AssetPaths.BUILDING_HOARDER_ACHIEVEMENT));
  }
}
