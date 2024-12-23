package y111studios.utils;

import lombok.Getter;

public class LeaderboardScore {
  private @Getter String name;
  private @Getter int score;

  public LeaderboardScore(String name, int score) {
    this.name = name;
    this.score = score;
  }
}
