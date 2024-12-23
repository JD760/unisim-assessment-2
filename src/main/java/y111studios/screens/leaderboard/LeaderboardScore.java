package y111studios.screens.leaderboard;

import lombok.Getter;

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
