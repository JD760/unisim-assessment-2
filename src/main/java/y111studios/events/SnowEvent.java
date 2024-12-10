package y111studios.events;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.math.Vector3;

import y111studios.GameState;
import y111studios.Main;
import y111studios.World;

public class SnowEvent implements Event {
  private static final Vector3 minStartPoint = new Vector3(0f, 0f, 2f);
  private static final Vector3 maxStartPoint = new Vector3(1000f, 1000f, 20f);
  private ArrayList<Vector3> snowflakes;
  private final Main game;
  private GameState gameState;
  private World world;

  public SnowEvent(final Main game, GameState gameState, World world) {
    this.game = game;
    this.gameState = gameState;
    this.world = world;
    snowflakes = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      snowflakes.add(new Vector3(
        minStartPoint.x + new Random().nextFloat() * (maxStartPoint.x - minStartPoint.x),
        minStartPoint.y + new Random().nextFloat() * (maxStartPoint.y - minStartPoint.y),
        minStartPoint.z + new Random().nextFloat() * (maxStartPoint.z - minStartPoint.z)
      ));
    }
  }

  public void render(float delta) {
    for (Vector3 snowflake : snowflakes) {
    }
  }
}
