package test.java.y111studios.score;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import y111studios.score.ScoreManager;

public class ScoreManagerTest {
  @Test
  void testAddScore() {
    ScoreManager scoreManager = new ScoreManager();
    scoreManager.addScore(10);
    assertEquals(scoreManager.getScore(), 10);
    scoreManager.addScore(6);
    assertEquals(scoreManager.getScore(), 16);
  }

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
