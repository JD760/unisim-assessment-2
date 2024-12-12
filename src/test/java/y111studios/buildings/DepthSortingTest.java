package y111studios.buildings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import y111studios.GameState;
import y111studios.Main;
import y111studios.World;
import y111studios.buildings.premade_variants.TeachingVariant;
import y111studios.position.GridPosition;

public class DepthSortingTest {
  @Test
  public void insertingBehind() {
    GameState gameState = new GameState(75, 75, new Main());
    gameState.resume();
    World world = new World(null, gameState, null);

    world.addObject(TeachingVariant.SUBJECT_HUB, new GridPosition(3, 3), false);
    world.addObject(TeachingVariant.SMALL_CLASSROOM, new GridPosition(6, 5), false);

      assertEquals(world.getBuildings().get(0).getArea().getOrigin(), new GridPosition(6, 5),
              "Adding a building behind another building should result in it being sorted first");
  }

  @Test
  public void insertingInFront() {
    GameState gameState = new GameState(75, 75, new Main());
    gameState.resume();
    World world = new World(null, gameState, null);

    world.addObject(TeachingVariant.SUBJECT_HUB, new GridPosition(3, 3), false);
    world.addObject(TeachingVariant.SMALL_CLASSROOM, new GridPosition(5, 6), false);

      assertEquals(world.getBuildings().get(1).getArea().getOrigin(), new GridPosition(5, 6),
              "Adding a building in front of another building should result in it being sorted second");
  }
}
