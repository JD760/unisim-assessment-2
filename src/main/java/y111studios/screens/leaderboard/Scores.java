package y111studios.screens.leaderboard;

import java.util.List;

/**
 * Model type provided to the JSON wrapper when reading a list of leaderboard scores.
 */
public class Scores {
  private List<LeaderboardScore> scores;

  public Scores() {}

  public void setScores(List<LeaderboardScore> scores) {
    this.scores = scores;
  }

  public List<LeaderboardScore> getScores() {
    return scores;
  }
}
