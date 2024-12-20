package y111studios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Random;
import lombok.Getter;
import lombok.Setter;
import y111studios.buildings.Building;
import y111studios.buildings.BuildingController;
import y111studios.buildings.BuildingManager;
import y111studios.buildings.ObstacleBuilding;
import y111studios.clock.Clock;
import y111studios.clock.GameTimer;
import y111studios.events.Event;
import y111studios.events.FloodEvent;
import y111studios.events.OpenDayEvent;
import y111studios.events.PandemicEvent;
import y111studios.events.ResearchBreakthroughEvent;
import y111studios.events.SnowEvent;
import y111studios.map.CollisionDetection;
import y111studios.position.GridPosition;
import y111studios.screens.Leaderboard;
import y111studios.utils.Score;

/**
 * A class representing the sum state of the game. This class contains the
 * clock, building manager and collision detection. This class is used to
 * manage the state of the game by exposing the necessary methods to alter
 * the state of the game in a controlled manner.
 *
 * @see Clock
 * @see BuildingManager
 * @see CollisionDetection
 */
public class GameState implements GameTimer, BuildingController {
  private static final int NUM_EVENT_TYPES = 4;
  private @Getter static Leaderboard leaderboard = new Leaderboard();
  private @Getter GameTimer timer;
  private Main game;
  public BuildingManager buildingManager;
  CollisionDetection collisionDetection;
  private @Getter StudentSatisfaction studentSatisfaction;
  private @Setter @Getter Event currentEvent;
  private long lastTickTime;
  private @Setter Camera camera;
  private @Getter int numTicks;
  private ArrayList<Integer> unplayedEvents;

  /**
   * Constructor for the GameState class.
   *
   * @param width  width of the game map
   * @param height height of the game map
   */
  public GameState(int width, int height, Main game) {
    this.game = game;
    timer = new Clock();
    lastTickTime = 0;
    buildingManager = new BuildingManager();
    numTicks = 0;
    int[][] staticObjects = new int[][] {
        { 42, 12, 16, 16 }, { 46, 10, 11, 2 }, { 52, 15, 4, 13 }, { 46, 28, 6, 1 },  // Big rock
        { 48, 29, 2, 1 }, { 41, 13, 1, 11 }, { 40, 17, 1, 2 },  // Big rock
        { 56, 18, 2, 15 }, { 31, 32, 26, 2 }, { 30, 33, 2, 19 }, { 27, 52, 4, 2 },  // River
        { 26, 53, 2, 7 }, { 19, 60, 7, 2 }, { 15, 59, 4, 4 },  // River
        { 15, 15, 7, 8 }, { 18, 23, 3, 1 }, { 14, 17, 1, 5 }, { 17, 14, 6, 7 },  // Small rock
        { 11, 35, 10, 14 }, { 21, 35, 2, 8 },  // Big cliff thing
        { 55, 49, 8, 12 },  // Small cliff thing
        { 32, 43, 1, 3 }  // Bridge
    };
    collisionDetection = new CollisionDetection(width, height, staticObjects);
    studentSatisfaction = new StudentSatisfaction(buildingManager, staticObjects);
    unplayedEvents = new ArrayList<>();
    for (int i = 0; i < NUM_EVENT_TYPES; i++) {
      unplayedEvents.add(i);
    }
    currentEvent = null;
  }

  // BuildingController methods

  @Override
  public Building getBuilding(GridPosition position) {
    if (position == null) {
      return null;
    }
    if (collisionDetection.canPlaceBuilding(position)) {
      return null;
    }
    return this.buildingManager.getBuilding(position);
  }

  @Override
  public boolean push(Building building) {
    if (building == null) {
      return true;
    }
    // Cannot push into a full building manager
    if (buildingManager.isFull()) {
      return false;
    }
    // Cannot push if the building area is not empty
    if (!collisionDetection.placeBuilding(building.getArea())) {
      return false;
    }
    // Push the building into the building manager
    this.buildingManager.push(building);
    return true;
  }

  @Override
  public boolean removePosition(GridPosition position) {
    if (position == null) {
      return false;
    }
    // Get building being removed
    final Building building = this.buildingManager.getBuilding(position);
    if (building == null || building instanceof ObstacleBuilding) {
      // This should never happen provided push is correctly implemented
      throw new IllegalStateException("Building not found at position: " + position);
    }
    // Remove the building from the collision detection
    this.collisionDetection.removeBuilding(building.getArea());
    this.buildingManager.removePosition(position);
    return true;
  }

  @Override
  public int getCount() {
    return this.buildingManager.getCount();
  }

  @Override
  public boolean isFull() {
    return this.buildingManager.isFull();
  }

  // GameTimer methods

  @Override
  public boolean isPaused() {
    return timer.isPaused();
  }

  @Override
  public void pause() {
    timer.pause();
  }

  @Override
  public void resume() {
    timer.resume();
  }

  @Override
  public boolean isTimeUp() {
    return timer.isTimeUp();
  }

  @Override
  public Duration timeRemaining() {
    return timer.timeRemaining();
  }

  public static Leaderboard getLeaderboard() {
    return leaderboard;
  }

  /**
   * Returns if the building can be placed given the current state of the game.
   *
   * @param building the building to be placed
   * @return true if the building can be placed, false otherwise
   */
  public boolean canPlaceBuilding(Building building) {
    return collisionDetection.canPlaceBuilding(building.getArea());
  }

  public void setScreen(Screen screen) {
    game.setScreen(screen);
  }

  public Main getGame() {
    return game;
  }

  /**
   * Simulates 1/60th of a second of game time.
   * Updates student satisfaction and events.
   */
  public void tick() {
    // Only tick the game if the timer is unpaused
    if (!timer.isPaused()) {
      // If the timer has just been unpaused, also don't tick the game
      if (lastTickTime == 0) {
        lastTickTime = System.currentTimeMillis();
        return;
      }
      // Calculate the number of ticks that should be simulated
      long currentTimeRemaining = System.currentTimeMillis();
      long numTicksToSimulate = (currentTimeRemaining - lastTickTime) / (1000 / 60);
      long remainder = (currentTimeRemaining - lastTickTime) % (1000 / 60);
      lastTickTime = currentTimeRemaining - remainder;
      // Simulate ticks
      while (numTicksToSimulate-- > 0) {
        studentSatisfaction.tick();
        buildingManager.tick();

        // Update the current event every 62 seconds
        if (numTicks % (60 * 62) == 0 && numTicks > 0) {
          if (numTicks > 60 * 62 * 4) {
            currentEvent = null;
          } else if (numTicks == 60 * 10 * 4) {
            currentEvent = new OpenDayEvent(game, this);
          } else {
            int eventIndex = new Random().nextInt(unplayedEvents.size());
            int eventNum = unplayedEvents.get(eventIndex).intValue();
            unplayedEvents.remove(eventIndex);
            switch (eventNum) {
              case 0:
                currentEvent = new FloodEvent(game, this, camera);
                break;
              case 1:
                currentEvent = new SnowEvent(game, this, camera);
                break;
              case 2:
                currentEvent = new ResearchBreakthroughEvent(game, this);
                break;
              case 3:
                currentEvent = new PandemicEvent(game, this);
                break;
              default:
                break;
            }
          }
          Gdx.app.log("#INFO", "Event started: " + currentEvent.getClass().toString());
          studentSatisfaction.setCurrentEvent(currentEvent);
          currentEvent.setNotification();
        }

        numTicks++;
      }

      if (timer.isTimeUp()) {
        timer.pause();
      }
    } else {
      lastTickTime = 0;
    }
  }
}
