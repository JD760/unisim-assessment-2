package y111studios.events;

import java.util.ArrayList;
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
  private ArrayList<Vector3> snowflakes;
  private final Main game;
  private GameState gameState;
  private World world;
  private Texture snowflakeTexture;
  private Random random = new Random();

  public SnowEvent(final Main game, GameState gameState, World world) {
    this.game = game;
    this.gameState = gameState;
    this.world = world;
    snowflakes = new ArrayList<>();
    snowflakeTexture = game.getAsset(AssetPaths.SNOWFLAKE);

    for (int i = 0; i < 50000; i++) {
      snowflakes.add(new Vector3(
        minStartPoint.x + random.nextFloat() * (maxStartPoint.x - minStartPoint.x),
        minStartPoint.y + random.nextFloat() * (maxStartPoint.y - minStartPoint.y),
        minStartPoint.z + random.nextFloat() * (maxStartPoint.z - minStartPoint.z)
      ));
    }
  }

  public void render(float delta) {
    Camera camera = world.getCamera();
    for (Vector3 snowflake : snowflakes) {
      snowflake.x += Math.sin(snowflake.z) * snowflake.z * 0.08f;
      snowflake.y -= snowflake.z * delta * 10;
      snowflake.z *= (float)Math.pow(0.6f, delta);
      if (snowflake.z < 1f) {
        snowflake.x = minStartPoint.x + random.nextFloat() * (maxStartPoint.x - minStartPoint.x);
        snowflake.y = minStartPoint.y + random.nextFloat() * (maxStartPoint.y - minStartPoint.y);
        snowflake.z = minStartPoint.z + random.nextFloat() * (maxStartPoint.z - minStartPoint.z);
      }

      game.spritebatch.draw(
        snowflakeTexture,
        (snowflake.x - camera.x) / camera.scale,
        (snowflake.y + camera.y + camera.height * camera.scale) / camera.scale,
        snowflake.z / camera.scale, snowflake.z / camera.scale, 0f, 0f, 1f, 1f
      );
    }
  }
}
