package y111studios.screens;

import org.junit.jupiter.api.Test;

import y111studios.utils.Score;

public class LeaderboardTest {
  @Test
  public void testInsert() {
    Leaderboard leaderboard = new Leaderboard();
    Score score = new Score("Jacob", 1500);
    leaderboard.insertScore(score.getName(), score.getScore());

    leaderboard.printLeaderboard();
  }
}
