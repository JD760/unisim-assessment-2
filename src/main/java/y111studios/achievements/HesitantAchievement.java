package main.java.y111studios.achievements;

import y111studios.AssetPaths;
import y111studios.GameState;
import y111studios.World;
import y111studios.achievements.Achievement;
import y111studios.achievements.AchievementManager;

/**
 * This achievement is earned if 30 seconds have elapsed since the last building was placed.
 */
public class HesitantAchievement extends Achievement {
  private World world;
  private static final String DISPLAY_NAME = "Hesitant";
  private static final String DESCRIPTION = "Avoid placing a building for 30 seconds";
  int lastBuildingCount = 0;
  int ticksSinceLastPlaced = 0;

  public HesitantAchievement(String name, AchievementManager manager,
      World world, AssetPaths notificationPath) {
    super(name, manager, DISPLAY_NAME, DESCRIPTION, -50, world, notificationPath);
  }

  @Override
  public boolean condition() {
    GameState state = world.getGameState();
    if (state.buildingManager.counter.getCount() >= lastBuildingCount) {
      timeSinceLastPlaced = 0;
    }
    lastBuildingCount = state.buildingManager.counter.getCount();

    if (ticksSinceLastPlaced >= 1800) {
      return true;
    }

    ticksSinceLastPlaced++;
    return false;
  }

  @Override
  public void result() {
    super.result();
    return;
  }
}
