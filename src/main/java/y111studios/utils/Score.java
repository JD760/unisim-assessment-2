package y111studios.utils;

import lombok.Getter;

public class Score {
  private @Getter String name;
  private @Getter int score;

  public Score(String name, int score) {
    this.name = name;
    this.score = score;
  }
}
