package y111studios.utils;

import lombok.Getter;

public abstract class Achievement {
  private @Getter String name;
  private @Getter String description;
  
  public Achievement(String name, String description) {
    this.name = name;
    this.description = description;
  }
}
