package y111studios;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import y111studios.position.GridPosition;
import y111studios.buildings.MapObject;
import y111studios.buildings.BuildingManager;
import y111studios.StudentSatisfaction;
import y111studios.buildings.AccommodationBuilding;
import y111studios.buildings.TeachingBuilding;
import y111studios.buildings.RecreationBuilding;
import y111studios.buildings.CateringBuilding;
import y111studios.buildings.MiscellaneousBuilding;
import y111studios.buildings.premade_variants.AccommodationVariant;
import y111studios.buildings.premade_variants.TeachingVariant;
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
    assertEquals(satisfaction.calculate(), 0.0);
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
    assertEquals(satisfaction.calculate(), 0.0);
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
    assertEquals(satisfaction.calculate(), 0.0);
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
    assertEquals(satisfaction.calculate(), 0.0);
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
}
