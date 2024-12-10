package y111studios.events;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

import y111studios.AssetPaths;
import y111studios.Camera;
import y111studios.GameState;
import y111studios.Main;
import y111studios.World;

public class SnowEvent implements Event {
  private static final Vector3 minStartPoint = new Vector3(-3000f, -5000f, 1f);
  private static final Vector3 maxStartPoint = new Vector3(7500f, 2000f, 30f);
  private Vector3[] snowflakes;
  private final Main game;
  private GameState gameState;
  private Camera camera;
  private Texture snowflakeTexture;
  private Random random = new Random();

  public SnowEvent(final Main game, GameState gameState, Camera camera) {
    this.game = game;
    this.gameState = gameState;
    this.camera = camera;
    snowflakes = new Vector3[50000];
    snowflakeTexture = game.getAsset(AssetPaths.SNOWFLAKE);

    for (int i = 0; i < 50000; i++) {
      snowflakes[i] = new Vector3(
        minStartPoint.x + random.nextFloat() * (maxStartPoint.x - minStartPoint.x),
        minStartPoint.y + random.nextFloat() * (maxStartPoint.y - minStartPoint.y),
        minStartPoint.z + random.nextFloat() * (maxStartPoint.z - minStartPoint.z)
      );
    }
  }

  public void render(float delta) {
    int timeSinceEventStart = gameState.getNumTicks() % (60 * 62);
    int numSnowflakes = (int)(getIntensity() * 50000);
    for (int i = 0; i < numSnowflakes; i++) {
      if (!gameState.isPaused()) {
        snowflakes[i].x += Math.sin(snowflakes[i].z) * snowflakes[i].z * 0.08f;
        snowflakes[i].y -= snowflakes[i].z * delta * 10;
        snowflakes[i].z *= (float)Math.pow(0.6f, delta);
        if (snowflakes[i].z < 1f) {
          snowflakes[i].x = minStartPoint.x + random.nextFloat() * (maxStartPoint.x - minStartPoint.x);
          snowflakes[i].y = minStartPoint.y + random.nextFloat() * (maxStartPoint.y - minStartPoint.y);
          snowflakes[i].z = minStartPoint.z + random.nextFloat() * (maxStartPoint.z - minStartPoint.z);
        }
      }

      game.spritebatch.draw(
        snowflakeTexture,
        (snowflakes[i].x - camera.x) / camera.scale,
        (snowflakes[i].y + camera.y + camera.height * camera.scale) / camera.scale,
        snowflakes[i].z / camera.scale, snowflakes[i].z / camera.scale, 0f, 0f, 1f, 1f
      );
    }
  }

  public float getIntensity() {
    int timeSinceEventStart = gameState.getNumTicks() % (60 * 62);
    return (float)Math.sin((float)timeSinceEventStart / (60 * 62) * Math.PI);
  }
}
