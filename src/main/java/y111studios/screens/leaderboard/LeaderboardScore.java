package y111studios.screens.leaderboard;

import lombok.Getter;

/**
 * Represents a single score on the leaderboard.
 */
public class LeaderboardScore {
  private @Getter String name;
  private @Getter double score;

  public LeaderboardScore(String name, int score) {
    this.name = name;
    this.score = score;
  }

  public LeaderboardScore() {
    this.name = "broken";
    this.score = 50;
  }
}
