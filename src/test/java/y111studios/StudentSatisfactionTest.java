package test.java.y111studios;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import y111studios.position.GridPosition;
import y111studios.buildings.MapObject;
import y111studios.buildings.BuildingManager;
import y111studios.GameState;
import y111studios.Camera;
import y111studios.StudentSatisfaction;
import y111studios.buildings.AccommodationBuilding;
import y111studios.buildings.TeachingBuilding;
import y111studios.buildings.RecreationBuilding;
import y111studios.buildings.CateringBuilding;
import y111studios.buildings.MiscellaneousBuilding;
import y111studios.buildings.premade_variants.AccommodationVariant;
import y111studios.buildings.premade_variants.TeachingVariant;
import y111studios.events.SnowEvent;
import y111studios.events.FloodEvent;
import y111studios.events.ResearchBreakthroughEvent;
import y111studios.events.PandemicEvent;
import y111studios.events.OpenDayEvent;
import y111studios.buildings.premade_variants.RecreationVariant;
import y111studios.buildings.premade_variants.CateringVariant;
import y111studios.buildings.premade_variants.MiscellaneousVariant;

/**
 * Test the student satisfaction calculator.
 */
public class StudentSatisfactionTest {
  /**
   * Test that student satisfaction defaults to 0 and that you need at least 1
   * accommodation building and 1 catering building to reach a satisfaction
   * that is greater than 0.
  */
  @Test
  void zeroTest() {
    BuildingManager buildingManager = new BuildingManager();
    StudentSatisfaction satisfaction = new StudentSatisfaction(buildingManager, new int[0][0]);
    assertEquals(satisfaction.calculate(), 0.0);
    buildingManager.push(new AccommodationBuilding(
      new GridPosition(0, 10), AccommodationVariant.SMALL_HOUSE, false
    ));
    buildingManager.push(new TeachingBuilding(
      new GridPosition(0, 20), TeachingVariant.SMALL_CLASSROOM, false
    ));
    buildingManager.push(new RecreationBuilding(
      new GridPosition(0, 30), RecreationVariant.GYM, false
    ));
    // Tick the game enough to build all the buildings
    for (int i = 0; i < MapObject.BUILDING_TIME + 1; i++)
      buildingManager.tick();
    assertEquals(satisfaction.calculate(), 0.0);

    buildingManager.removePosition(new GridPosition(0, 10));
    buildingManager.push(new CateringBuilding(
      new GridPosition(0, 10), CateringVariant.FAST_FOOD, false
    ));
    // Tick the game enough to build all the buildings
    for (int i = 0; i < MapObject.BUILDING_TIME + 1; i++)
      buildingManager.tick();
    assertEquals(satisfaction.calculate(), 0.0);

    buildingManager.push(new AccommodationBuilding(
      new GridPosition(0, 40), AccommodationVariant.SMALL_HOUSE, false
    ));
    // Tick the game enough to build all the buildings
    for (int i = 0; i < MapObject.BUILDING_TIME + 1; i++)
      buildingManager.tick();
    assertTrue(satisfaction.calculate() > 0.0);
  }

  /**
   * Test that satisfaction is higher when accommodation buildings are placed
   * nearer catering builidngs.
  */
  @Test
  void accommodationNearCateringTest() {
    BuildingManager buildingManager = new BuildingManager();
    StudentSatisfaction satisfaction = new StudentSatisfaction(buildingManager, new int[0][0]);
    buildingManager.push(new AccommodationBuilding(
      new GridPosition(0, 10), AccommodationVariant.SMALL_HOUSE, false
    ));
    buildingManager.push(new CateringBuilding(
      new GridPosition(0, 20), CateringVariant.FAST_FOOD, false
    ));
    // Tick the game enough to build all the buildings
    for (int i = 0; i < MapObject.BUILDING_TIME + 1; i++)
      buildingManager.tick();
    double originalSatisfaction = satisfaction.calculate();

    buildingManager.removePosition(new GridPosition(0, 20));
    buildingManager.push(new CateringBuilding(
      new GridPosition(0, 40), CateringVariant.FAST_FOOD, false
    ));
    // Tick the game enough to build all the buildings
    for (int i = 0; i < MapObject.BUILDING_TIME + 1; i++)
      buildingManager.tick();
    assertTrue(satisfaction.calculate() < originalSatisfaction);
  }

  /**
   * Test that satisfaction is higher when teaching buildings are placed
   * nearer catering builidngs.
  */
  @Test
  void teachingNearCateringTest() {
    BuildingManager buildingManager = new BuildingManager();
    StudentSatisfaction satisfaction = new StudentSatisfaction(buildingManager, new int[0][0]);
    buildingManager.push(new AccommodationBuilding(
      new GridPosition(0, 10), AccommodationVariant.SMALL_HOUSE, false
    ));
    buildingManager.push(new TeachingBuilding(
      new GridPosition(0, 20), TeachingVariant.SMALL_CLASSROOM, false
    ));
    buildingManager.push(new CateringBuilding(
      new GridPosition(0, 30), CateringVariant.FAST_FOOD, false
    ));
    // Tick the game enough to build all the buildings
    for (int i = 0; i < MapObject.BUILDING_TIME + 1; i++)
      buildingManager.tick();
    double originalSatisfaction = satisfaction.calculate();

    buildingManager.removePosition(new GridPosition(0, 20));
    buildingManager.push(new CateringBuilding(
      new GridPosition(0, 40), CateringVariant.FAST_FOOD, false
    ));
    // Tick the game enough to build all the buildings
    for (int i = 0; i < MapObject.BUILDING_TIME + 1; i++)
      buildingManager.tick();
    assertTrue(satisfaction.calculate() < originalSatisfaction);
  }

  /**
   * Test that satisfaction is higher when teaching buildings are placed
   * nearer accommodation builidngs.
  */
  @Test
  void teachingNearAccommodationTest() {
    BuildingManager buildingManager = new BuildingManager();
    StudentSatisfaction satisfaction = new StudentSatisfaction(buildingManager, new int[0][0]);
    buildingManager.push(new CateringBuilding(
      new GridPosition(0, 10), CateringVariant.FAST_FOOD, false
    ));
    buildingManager.push(new AccommodationBuilding(
      new GridPosition(0, 20), AccommodationVariant.SMALL_HOUSE, false
    ));
    buildingManager.push(new TeachingBuilding(
      new GridPosition(0, 30), TeachingVariant.SMALL_CLASSROOM, false
    ));
    buildingManager.push(new CateringBuilding(
      new GridPosition(0, 35), CateringVariant.FAST_FOOD, false
    ));
    // Tick the game enough to build all the buildings
    for (int i = 0; i < MapObject.BUILDING_TIME + 1; i++)
      buildingManager.tick();
    double originalSatisfaction = satisfaction.calculate();

    buildingManager.removePosition(new GridPosition(0, 30));
    buildingManager.removePosition(new GridPosition(0, 35));
    buildingManager.push(new TeachingBuilding(
      new GridPosition(0, 40), TeachingVariant.SMALL_CLASSROOM, false
    ));
    buildingManager.push(new CateringBuilding(
      new GridPosition(0, 45), CateringVariant.FAST_FOOD, false
    ));
    // Tick the game enough to build all the buildings
    for (int i = 0; i < MapObject.BUILDING_TIME + 1; i++)
      buildingManager.tick();
    assertTrue(satisfaction.calculate() < originalSatisfaction);
  }

  /**
   * Test that satisfaction is higher when recreation buildings are placed
   * nearer accommodation builidngs.
  */
  @Test
  void recreationNearAccommodationTest() {
    BuildingManager buildingManager = new BuildingManager();
    StudentSatisfaction satisfaction = new StudentSatisfaction(buildingManager, new int[0][0]);
    buildingManager.push(new CateringBuilding(
      new GridPosition(0, 10), CateringVariant.FAST_FOOD, false
    ));
    buildingManager.push(new AccommodationBuilding(
      new GridPosition(0, 20), AccommodationVariant.SMALL_HOUSE, false
    ));
    buildingManager.push(new RecreationBuilding(
      new GridPosition(0, 30), RecreationVariant.GYM, false
    ));
    buildingManager.push(new CateringBuilding(
      new GridPosition(0, 35), CateringVariant.FAST_FOOD, false
    ));
    // Tick the game enough to build all the buildings
    for (int i = 0; i < MapObject.BUILDING_TIME + 1; i++)
      buildingManager.tick();
    double originalSatisfaction = satisfaction.calculate();

    buildingManager.removePosition(new GridPosition(0, 30));
    buildingManager.removePosition(new GridPosition(0, 35));
    buildingManager.push(new RecreationBuilding(
      new GridPosition(0, 40), RecreationVariant.GYM, false
    ));
    buildingManager.push(new CateringBuilding(
      new GridPosition(0, 45), CateringVariant.FAST_FOOD, false
    ));
    // Tick the game enough to build all the buildings
    for (int i = 0; i < MapObject.BUILDING_TIME + 1; i++)
      buildingManager.tick();
    assertTrue(satisfaction.calculate() < originalSatisfaction);
  }

  /**
   * Test that satisfaction is higher when the correct ratio of buildings have
   * been placed.
  */
  @Test
  void buildingRatiosTest() {
    BuildingManager buildingManager = new BuildingManager();
    StudentSatisfaction satisfaction = new StudentSatisfaction(buildingManager, new int[0][0]);
    buildingManager.push(new CateringBuilding(
      new GridPosition(0, 10), CateringVariant.FAST_FOOD, false
    ));
    buildingManager.push(new AccommodationBuilding(
      new GridPosition(0, 20), AccommodationVariant.SMALL_HOUSE, false
    ));
    buildingManager.push(new RecreationBuilding(
      new GridPosition(0, 30), RecreationVariant.GYM, false
    ));
    buildingManager.push(new TeachingBuilding(
      new GridPosition(0, 35), TeachingVariant.SMALL_CLASSROOM, false
    ));
    // Tick the game enough to build all the buildings
    for (int i = 0; i < MapObject.BUILDING_TIME + 1; i++)
      buildingManager.tick();
    double originalSatisfaction = satisfaction.calculate();

    buildingManager.push(new AccommodationBuilding(
      new GridPosition(6, 20), AccommodationVariant.MEDIUM_HOUSE, false
    ));
    // Tick the game enough to build all the buildings
    for (int i = 0; i < MapObject.BUILDING_TIME + 1; i++)
      buildingManager.tick();
    assertTrue(satisfaction.calculate() > originalSatisfaction);
  }

  /**
   * Test that satisfaction is higher when buildings are placed near roads
  */
  @Test
  void buildingsNearRoadsTest() {
    BuildingManager buildingManager = new BuildingManager();
    StudentSatisfaction satisfaction = new StudentSatisfaction(buildingManager, new int[0][0]);
    buildingManager.push(new CateringBuilding(
      new GridPosition(0, 10), CateringVariant.FAST_FOOD, false
    ));
    buildingManager.push(new AccommodationBuilding(
      new GridPosition(0, 20), AccommodationVariant.SMALL_HOUSE, false
    ));
    // Tick the game enough to build all the buildings
    for (int i = 0; i < MapObject.BUILDING_TIME + 1; i++)
      buildingManager.tick();
    double originalSatisfaction = satisfaction.calculate();

    buildingManager.push(new MiscellaneousBuilding(
      new GridPosition(0, 15), MiscellaneousVariant.STRAIGHT_ROAD, false
    ));
    // Tick the game enough to build all the buildings
    for (int i = 0; i < MapObject.BUILDING_TIME + 1; i++)
      buildingManager.tick();
    assertTrue(satisfaction.calculate() > originalSatisfaction);
  }

  /**
   * Test that satisfaction is higher when roads connect up to each other correctly
  */
  @Test
  void roadConnectionTest() {
    BuildingManager buildingManager = new BuildingManager();
    StudentSatisfaction satisfaction = new StudentSatisfaction(buildingManager, new int[0][0]);
    buildingManager.push(new CateringBuilding(
      new GridPosition(0, 10), CateringVariant.FAST_FOOD, false
    ));
    buildingManager.push(new AccommodationBuilding(
      new GridPosition(0, 20), AccommodationVariant.SMALL_HOUSE, false
    ));
    buildingManager.push(new MiscellaneousBuilding(
      new GridPosition(0, 15), MiscellaneousVariant.STRAIGHT_ROAD, false
    ));
    buildingManager.push(new MiscellaneousBuilding(
      new GridPosition(2, 15), MiscellaneousVariant.STRAIGHT_ROAD, true
    ));
    // Tick the game enough to build all the buildings
    for (int i = 0; i < MapObject.BUILDING_TIME + 1; i++)
      buildingManager.tick();
    double originalSatisfaction = satisfaction.calculate();

    buildingManager.removePosition(new GridPosition(2, 15));
    buildingManager.push(new MiscellaneousBuilding(
      new GridPosition(2, 15), MiscellaneousVariant.STRAIGHT_ROAD, false
    ));
    // Tick the game enough to build all the buildings
    for (int i = 0; i < MapObject.BUILDING_TIME + 1; i++)
      buildingManager.tick();
    assertTrue(satisfaction.calculate() > originalSatisfaction);
  }

  /**
   * Test that satisfaction is affected by the current event
  */
  @Test
  void eventImpactSatisfactionTest() {
    BuildingManager buildingManager = new BuildingManager();
    StudentSatisfaction satisfaction = new StudentSatisfaction(buildingManager, new int[0][0]);
    buildingManager.push(new CateringBuilding(
      new GridPosition(0, 10), CateringVariant.FAST_FOOD, false
    ));
    buildingManager.push(new AccommodationBuilding(
      new GridPosition(0, 20), AccommodationVariant.SMALL_HOUSE, false
    ));
    // Tick the game enough to build all the buildings
    for (int i = 0; i < MapObject.BUILDING_TIME + 1; i++)
      buildingManager.tick();
    double originalSatisfaction = satisfaction.calculate();

    // Snow event should increase satisfaction
    satisfaction.setCurrentEvent(new SnowEvent(
      null, new GameState(76, 76, null), new Camera(1, 1, 1, 1)
    ));
    assertTrue(satisfaction.calculate() > originalSatisfaction);

    // Flood event should decrease satisfaction
    satisfaction.setCurrentEvent(new FloodEvent(
      null, new GameState(76, 76, null), new Camera(1, 1, 1, 1)
    ));
    assertTrue(satisfaction.calculate() < originalSatisfaction);

    // Research Breakthrough event should increase satisfaction
    satisfaction.setCurrentEvent(new ResearchBreakthroughEvent(
      null, new GameState(76, 76, null)
    ));
    assertTrue(satisfaction.calculate() > originalSatisfaction);

    // Pandemic event should decrease satisfaction
    satisfaction.setCurrentEvent(new PandemicEvent(
      null, new GameState(76, 76, null)
    ));
    assertTrue(satisfaction.calculate() < originalSatisfaction);

    // Open Day event should have no effect on satisfaction
    satisfaction.setCurrentEvent(new OpenDayEvent(
      null, new GameState(76, 76, null)
    ));
    assertTrue(satisfaction.calculate() == originalSatisfaction);
  }
}
