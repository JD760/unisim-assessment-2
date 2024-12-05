package y111studios.screens;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import y111studios.utils.Score;

public class LeaderboardTest {
  @Test
  public void testInsertValidScore() {
    Leaderboard leaderboard = new Leaderboard();
    // allow valid scores
    assertTrue(leaderboard.insertScore(new Score("Jacob", 1500)));
    leaderboard.insertScore(new Score("Test", 1200));
    // prevent empty string
    assertFalse(leaderboard.insertScore(new Score("", 100)));
    // prevent negative score
    assertFalse(leaderboard.insertScore(new Score("Test", -10)));
    // prevent null string
    assertFalse(leaderboard.insertScore(new Score(null, 100)));
  }


}
