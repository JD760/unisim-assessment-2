package y111studios.events;

import y111studios.AssetPaths;
import y111studios.GameState;
import y111studios.Main;

/**
 * Occurs randomly throughout gameplay, provides a penalty to student satisfaction
 * which can be mitigated by improving the nature bonus - placing additional trees
 * and buildings close to water.
 */
public class PandemicEvent extends Event {
  private GameState gameState;

  public PandemicEvent(Main game, GameState gameState) {
    super(game, AssetPaths.PANDEMIC_EVENT);
    this.gameState = gameState;
  }

  public void render(float delta) {}

  public float getIntensity() {
    int timeSinceEventStart = gameState.getNumTicks() % (60 * 62);
    return (float) Math.sin((float) timeSinceEventStart / (60 * 62) * Math.PI);
  }
}
