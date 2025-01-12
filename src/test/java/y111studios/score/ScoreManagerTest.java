package y111studios.score;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import y111studios.score.ScoreManager;

/**
 * Test the Score Manager.
 * Used to test FR_LEADERBOARD and FR_ACHIEVEMENT_AWARDS
 */
public class ScoreManagerTest {
  /**
   * Test that the score can be both incremented and decremented.
   */
  @Test
  void testAddScore() {
    ScoreManager scoreManager = new ScoreManager();
    scoreManager.addScore(10);
    assertEquals(scoreManager.getScore(), 10);
    scoreManager.addScore(6);
    assertEquals(scoreManager.getScore(), 16);
    scoreManager.addScore(-6);
    assertEquals(scoreManager.getScore(), 10);
  }

  /**
   * Test that the score is calculated properly on each game tick.
   */
  @Test
  void testTick() {
    ScoreManager scoreManager = new ScoreManager();
    scoreManager.tick(50.3);
    int worseScore = scoreManager.calculateScore();
    scoreManager = new ScoreManager();
    scoreManager.tick(70.3);
    int betterScore = scoreManager.calculateScore();
    assertTrue(betterScore > worseScore);
  }
}
