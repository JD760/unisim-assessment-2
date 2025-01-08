package y111studios.screens.leaderboard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Collection of tests to ensure the leaderboard handles adding, removing
 * and sorting scores properly.
 */
public class LeaderboardTest {
  @Test
  public void testInsert() {
    Leaderboard leaderboard = new Leaderboard(false);
    // test inserting a score to an empty leaderboard
    assertTrue(leaderboard.insertScore(new LeaderboardScore("Jacob", 1200)));
    assertEquals(1_200, leaderboard.getScore(0).getScore());
    // test inserting a higher score and check the sorting order
    assertTrue(leaderboard.insertScore(new LeaderboardScore("Test", 10000)));
    assertEquals(10_000, leaderboard.getScore(0).getScore());

    assertEquals(2, leaderboard.getSize());
  }

  @Test
  public void testInsertValidScore() {
    Leaderboard leaderboard = new Leaderboard(false);
    // allow valid scores
    assertTrue(leaderboard.insertScore(new LeaderboardScore("Jacob", 1200)));
    leaderboard.insertScore(new LeaderboardScore("Test", 1500));
    // prevent empty string
    assertFalse(leaderboard.insertScore(new LeaderboardScore("", 100)));
    // prevent negative score
    assertFalse(leaderboard.insertScore(new LeaderboardScore("Test", -10)));
    // prevent null string
    assertFalse(leaderboard.insertScore(new LeaderboardScore(null, 100)));
  }

  @Test
  public void testClear() {
    Leaderboard leaderboard = new Leaderboard(false);

    leaderboard.insertScore(new LeaderboardScore("Jacob", 1200));
    assertEquals(1, leaderboard.getSize());
    leaderboard.clearLeaderboard();
    assertEquals(0, leaderboard.getSize());
  }

  @Test
  public void testGetScores() {
    Leaderboard leaderboard = new Leaderboard(false);
    assertEquals(new ArrayList<LeaderboardScore>(), leaderboard.getScores());
    LeaderboardScore testScore = new LeaderboardScore("test", 1200);
    LeaderboardScore otherScore = new LeaderboardScore("other", 10_000);

    leaderboard.insertScore(testScore);
    leaderboard.insertScore(otherScore);

    List<LeaderboardScore> scores = new ArrayList<LeaderboardScore>();
    scores.add(otherScore);
    scores.add(testScore);
    assertTrue(scores.equals(leaderboard.getScores()));
  }

  @Test
  public void testRemoveLowest() {
    Leaderboard leaderboard = new Leaderboard(false);
    leaderboard.insertScore(new LeaderboardScore("One", 100));
    leaderboard.insertScore(new LeaderboardScore("Two", 200));
    leaderboard.insertScore(new LeaderboardScore("Three", 300));
    leaderboard.insertScore(new LeaderboardScore("Four", 400));
    leaderboard.insertScore(new LeaderboardScore("Five", 500));
    // test that a new score is not inserted if it would be the new lowest
    assertFalse(leaderboard.insertScore(new LeaderboardScore("Tiny", 10)));
    // test that a new larger score should be inserted
    assertTrue(leaderboard.insertScore(new LeaderboardScore("Big", 10_000)));
  }

  @Test
  public void testGetScore() {
    Leaderboard leaderboard = new Leaderboard(false);
    LeaderboardScore oneScore = new LeaderboardScore("One", 100);
    LeaderboardScore testScore = new LeaderboardScore("Test", 1200);
    leaderboard.insertScore(oneScore);
    leaderboard.insertScore(testScore);

    assertEquals(oneScore, leaderboard.getScore(1));
    assertEquals(testScore, leaderboard.getScore(0));
    // test indices above, below and on the boundary of the range
    // of the list of scores return the correct values
    assertNull(leaderboard.getScore(-5));
    assertNull(leaderboard.getScore(leaderboard.getSize()));
    assertNull(leaderboard.getScore(Integer.MAX_VALUE - 10));
  }
}
