package y111studios.buildings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import y111studios.buildings.premade_variants.AccommodationVariant;
import y111studios.buildings.premade_variants.CateringVariant;
import y111studios.buildings.premade_variants.RecreationVariant;
import y111studios.buildings.premade_variants.TeachingVariant;
import y111studios.position.GridPosition;

/**
 * Test working with Buildings of different types.
 */
public class BuildingTypeTest {

  @Test
  void fromBuilding() {
    assertThrows(IllegalArgumentException.class, () -> BuildingType.fromBuilding(null));

    final GridPosition origin = new GridPosition(0, 0);
    Building building;

    building = BuildingFactory.createBuilding(AccommodationVariant.SMALL_HOUSE, origin);
    assertEquals(BuildingType.ACCOMMODATION, BuildingType.fromBuilding(building));

    building = BuildingFactory.createBuilding(CateringVariant.FAST_FOOD, origin);
    assertEquals(BuildingType.CATERING, BuildingType.fromBuilding(building));

    building = BuildingFactory.createBuilding(RecreationVariant.PARK, origin);
    assertEquals(BuildingType.RECREATION, BuildingType.fromBuilding(building));

    building = BuildingFactory.createBuilding(TeachingVariant.SMALL_CLASSROOM, origin);
    assertEquals(BuildingType.TEACHING, BuildingType.fromBuilding(building));
  }

  /**
   * Attempt to create a building with a type that is not recognised
   *  by creating a custom mock building class.
   */
  @Test
  public void testUnrecognisedBuilding() {
    AccommodationVariant variant = AccommodationVariant.MEDIUM_HOUSE;
    try {
      BuildingType.fromBuilding(new UnrecognisedBuilding(new GridPosition(0, 0), variant));
    } catch (IllegalArgumentException e) {
      return;
    }
    fail("Unrecognised building created");
  }

  class UnrecognisedBuilding extends Building {
    public UnrecognisedBuilding(GridPosition position, AccommodationVariant variant) {
      super(position, variant);
    }
  }
}
