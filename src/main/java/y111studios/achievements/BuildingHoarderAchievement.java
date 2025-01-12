package y111studios.achievements;

import com.badlogic.gdx.Gdx;
import y111studios.AssetPaths;
import y111studios.GameState;
import y111studios.StudentSatisfaction;
import y111studios.World;

/**
 * This achievement is granted for placing at least 100 buildings on the map.
 */
public class BuildingHoarderAchievement extends Achievement {
  private World world;
  private static final String DISPLAY_NAME = "Building Hoarder!";
  private static final String DESCRIPTION = "Place 100 buildings";

  public BuildingHoarderAchievement(String name, AchievementManager manager,
      World world, AssetPaths notificationPath) {
    super(name, manager, DISPLAY_NAME, DESCRIPTION, 50, world, notificationPath);
    this.world = world;
  }

  @Override
  public boolean condition() {
    GameState state = world.getGameState();
    if (state.buildingManager.counter.getCount() >= 100) {
      return true;
    }
    return false;
  }

  @Override
  public void result() {
    // use the superclass method to deliver an achievement notification
    super.result();
    return;
  }
}
