package y111studios.utils;

import lombok.Getter;

public class Score {
  private @Getter String name;
  private @Getter double score;

  public Score(String name, double score) {
    this.name = name;
    this.score = score;
  }
}
