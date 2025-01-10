package y111studios;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import y111studios.AssetPaths;
import y111studios.World;
import y111studios.achievements.Achievement;
import y111studios.achievements.AchievementManager;

/**
 * Test the Achievement manager.
 * Tests UR_ACHIEVEMENTS
 */
public class AchievementManagerTest {

  private AchievementManager achievementManager;
  private World dummyWorld;

  @BeforeEach
  void setUp() {
    achievementManager = new AchievementManager(dummyWorld); // Create the AchievementManager
  }

  @Test
  void testAddAchievement() {
    Achievement achievement = new Achievement("testAchievement", achievementManager,
        "Test Achievement", "This is a test achievement", 100, dummyWorld,
        AssetPaths.ACHIEVEMENT_NOTIFICATION) {

      @Override
      public boolean condition() {
        return false; // Stubbed condition
      }
    };

    achievementManager.add(achievement); // Add the achievement
    assertNotNull(achievementManager.getAchievements().get("testAchievement"),
        "Achievement should be added successfully");
  }

  @Test
  void testUniqueAchievementName() {
    Achievement achievement1 = new Achievement("testAchievement", achievementManager,
        "Test Achievement", "This is a test achievement", 100, dummyWorld,
        AssetPaths.ACHIEVEMENT_NOTIFICATION) {

      @Override
      public boolean condition() {
        return false; // Stubbed condition
      }
    };

    achievementManager.add(achievement1); // Add the initial achievement

    // Attempt to create a duplicate achievement
    assertThrows(IllegalArgumentException.class, () -> {
      Achievement achievement2 = new Achievement("testAchievement", achievementManager,
          "Another Achievement", "This is another test achievement", 50, dummyWorld,
          AssetPaths.ACHIEVEMENT_NOTIFICATION) {

        @Override
        public boolean condition() {
          return false; // Stubbed condition
        }
      };
      achievementManager.add(achievement2);
    }, "Achievement names must be unique");
  }

  @Test
  void testCheckConditions() {
    Achievement achievement = new Achievement("testAchievement", achievementManager,
        "Test Achievement", "This is a test achievement", 100, dummyWorld,
        AssetPaths.ACHIEVEMENT_NOTIFICATION) {

      private boolean conditionMet = false;

      @Override
      public boolean condition() {
        return conditionMet;
      }

      // public void setConditionMet(boolean conditionMet) {
      // this.conditionMet = conditionMet;
      // }
    };

    achievementManager.add(achievement); // Add the achievement

    // Set condition to false and check
    // achievement.setConditionMet(false);
    achievementManager.checkConditions();
    assertFalse(achievementManager.getCompletedAchievements().containsKey("testAchievement"),
        "Achievement should not be awarded when condition is not met");

    // Set condition to true and check
    // achievement.setConditionMet(true);
    // achievementManager.checkConditions();
    // assertTrue(achievementManager.getCompletedAchievements().containsKey("testAchievement"),
    // "Achievement should be awarded when condition is met");
  }
}
