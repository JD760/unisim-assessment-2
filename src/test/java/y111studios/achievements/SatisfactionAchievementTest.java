package y111studios.achievements;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import y111studios.AssetPaths;
import y111studios.GameState;
import y111studios.World;

/**
 * Tests that the SatisfactionAchievement is granted if and only if the condition is met.
 * Tests UR_ACHIEVEMENTS
 */
public class SatisfactionAchievementTest {

  private SatisfactionAchievement satisfactionAchievement;
  private AchievementManager achievementManager;
  private World dummyWorld;
  public GameState gameState;

  @BeforeEach
  void setUp() {
    gameState = new GameState(100, 100, null);
    dummyWorld = new World(null, gameState, null);
    achievementManager = new AchievementManager(dummyWorld);
    satisfactionAchievement = new SatisfactionAchievement("satisfactionAchievement",
        achievementManager, dummyWorld, AssetPaths.ACHIEVEMENT_NOTIFICATION);
    achievementManager.add(satisfactionAchievement);
  }

  @Test
  void testConditionNotMet() {
    // Set satisfaction to a value below 40%
    dummyWorld.getGameState().getStudentSatisfaction().setSatisfaction(30);
    assertFalse(satisfactionAchievement.condition(),
        "Condition should not be met when satisfaction is below 40%");
  }

  @Test
  void testConditionMet() {
    achievementManager.checkConditions();
    int originalScore = gameState.getScoreManager().getScore();
    // Set satisfaction to a value above 40%
    dummyWorld.getGameState().getStudentSatisfaction().setSatisfaction(50);
    achievementManager.checkConditions();
    assertTrue(satisfactionAchievement.condition(),
        "Condition should be met when satisfaction is above 40%");
    assertTrue(gameState.getScoreManager().getScore() > originalScore,
        "Final score should be higher if the achievement has been achieved");
  }
}
