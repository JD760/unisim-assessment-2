package y111studios.events;

import y111studios.GameState;

public class ResearchBreakthroughEvent implements Event {
  private GameState gameState;

  public ResearchBreakthroughEvent(GameState gameState) {
    this.gameState = gameState;
  }

  public void render(float delta) {}

  public float getIntensity() {
    int timeSinceEventStart = gameState.getNumTicks() % (60 * 62);
    return (float)Math.sin((float)timeSinceEventStart / (60 * 62) * Math.PI);
  }
}
