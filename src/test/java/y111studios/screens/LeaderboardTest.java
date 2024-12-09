package y111studios.screens;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import y111studios.utils.Score;

/**
 * Collection of tests to ensure the leaderboard handles adding, removing
 * and sorting scores properly.
 */
public class LeaderboardTest {

  @Test
  public void testInsert() {
    Leaderboard leaderboard = new Leaderboard();
    // test inserting a score to an empty leaderboard
    assertTrue(leaderboard.insertScore(new Score("Jacob", 1200)));
    assertEquals(1_200, leaderboard.getScore(0).getScore());
    // test inserting a higher score and check the sorting order
    assertTrue(leaderboard.insertScore(new Score("Test", 10000)));
    assertEquals(10_000, leaderboard.getScore(0).getScore());

    assertEquals(2, leaderboard.getSize());
  }

  @Test
  public void testInsertValidScore() {
    Leaderboard leaderboard = new Leaderboard();
    // allow valid scores
    assertTrue(leaderboard.insertScore(new Score("Jacob", 1200)));
    leaderboard.insertScore(new Score("Test", 1500));
    // prevent empty string
    assertFalse(leaderboard.insertScore(new Score("", 100)));
    // prevent negative score
    assertFalse(leaderboard.insertScore(new Score("Test", -10)));
    // prevent null string
    assertFalse(leaderboard.insertScore(new Score(null, 100)));
  }

  @Test
  public void testClear() {
    Leaderboard leaderboard = new Leaderboard();

    leaderboard.insertScore(new Score("Jacob", 1200));
    assertEquals(1, leaderboard.getSize());
    leaderboard.clearLeaderboard();
    assertEquals(0, leaderboard.getSize());
  }


}
