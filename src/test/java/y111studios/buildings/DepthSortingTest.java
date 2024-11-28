package y111studios.buildings;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import y111studios.GameState;
import y111studios.Main;
import y111studios.World;
import y111studios.buildings.premade_variants.TeachingVariant;
import y111studios.position.GridPosition;

public class DepthSortingTest {
  @Test
  public void insertingBehind() {
    GameState gameState = new GameState(75, 75);
    gameState.resume();
    World world = new World(null, gameState);

    world.addObject(TeachingVariant.SUBJECT_HUB, new GridPosition(3, 3));
    world.addObject(TeachingVariant.SMALL_CLASSROOM, new GridPosition(6, 5));

    assertTrue(world.getBuildings().get(0).getArea().getOrigin().equals(new GridPosition(6, 5)),
      "Adding a building behind another building should result in it being sorted first");
  }

  @Test
  public void insertingInfront() {
    GameState gameState = new GameState(75, 75);
    gameState.resume();
    World world = new World(null, gameState);

    world.addObject(TeachingVariant.SUBJECT_HUB, new GridPosition(3, 3));
    world.addObject(TeachingVariant.SMALL_CLASSROOM, new GridPosition(5, 6));

    assertTrue(world.getBuildings().get(1).getArea().getOrigin().equals(new GridPosition(5, 6)),
      "Adding a building infront of another building should result in it being sorted second");
  }
}
