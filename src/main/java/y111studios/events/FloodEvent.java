package y111studios.events;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import java.util.Random;
import y111studios.AssetPaths;
import y111studios.Camera;
import y111studios.GameState;
import y111studios.Main;

/**
 * The flood event is one of the negative events that may happen randomly throughout the game.
 */
public class FloodEvent extends Event {
  private static final Vector3 minStart = new Vector3(-3000f, -5000f, 1f);
  private static final Vector3 maxStart = new Vector3(7500f, 2000f, 9f);
  private Vector3[] snowflakes;
  private final Main game;
  private GameState gameState;
  private Camera camera;
  private Texture raindropTexture;
  private Random random = new Random();

  /**
   * Create a new flood event, in which the map becomes flooded and a satisfaction penalty
   * is applied.
   *
   * @param game - a reference to the main class
   * @param gameState - a reference to the game state
   * @param camera - the camera to apply the graphical effects to
   */
  public FloodEvent(final Main game, GameState gameState, Camera camera) {
    super(game, AssetPaths.SNOW_EVENT);
    this.game = game;
    this.gameState = gameState;
    this.camera = camera;
    snowflakes = new Vector3[50000];
    if (game != null)
      raindropTexture = game.getAsset(AssetPaths.RAINDROP);

    for (int i = 0; i < 50000; i++) {
      snowflakes[i] = new Vector3(
          minStart.x + random.nextFloat() * (maxStart.x - minStart.x),
          minStart.y + random.nextFloat() * (maxStart.y - minStart.y),
          minStart.z + random.nextFloat() * (maxStart.z - minStart.z));
    }
  }

  /**
   * Draw the graphics associated with the event. Runs every tick.
   */
  public void render(float delta) {
    int numSnowflakes = (int) (getIntensity() * 50000);
    for (int i = 0; i < numSnowflakes; i++) {
      if (!gameState.isPaused()) {
        snowflakes[i].y -= snowflakes[i].z * delta * 500;
        snowflakes[i].z *= (float) Math.pow(0.6f, delta);
        if (snowflakes[i].z < 1f) {
          snowflakes[i].x = minStart.x + random.nextFloat() * (maxStart.x - minStart.x);
          snowflakes[i].y = minStart.y + random.nextFloat() * (maxStart.y - minStart.y);
          snowflakes[i].z = minStart.z + random.nextFloat() * (maxStart.z - minStart.z);
        }
      }

      game.spritebatch.draw(
          raindropTexture,
          (snowflakes[i].x - camera.x) / camera.scale,
          (snowflakes[i].y + camera.y + camera.height * camera.scale) / camera.scale,
          snowflakes[i].z / camera.scale, snowflakes[i].z / camera.scale * 4, 0f, 0f, 1f, 1f);
    }
  }

  public float getIntensity() {
    int timeSinceEventStart = gameState.getNumTicks() % (60 * 62);
    return (float) Math.sin((float) timeSinceEventStart / (60 * 62) * Math.PI);
  }
}
