package y111studios.buildings;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import y111studios.buildings.premade_variants.AccommodationVariant;
import y111studios.buildings.premade_variants.CateringVariant;
import y111studios.buildings.premade_variants.RecreationVariant;
import y111studios.buildings.premade_variants.TeachingVariant;
import y111studios.position.GridPosition;

/**
 * Test the Building Factory.
 */
public class BuildingFactoryTest {

  private static final GridPosition DEFAULT_POSITION = new GridPosition(0, 0);

  @Nested
  @Tag("Constructor")
  class ConstructorTests {

    /**
     * Ensure that attempting to create a building with a null variant
     * will throw an IllegalArgumentException.
     */
    @Test
    void preventNullCreation() {
      assertThrows(IllegalArgumentException.class, () -> {
        BuildingFactory.createBuilding((AccommodationVariant) null, DEFAULT_POSITION, false);
        BuildingFactory.createBuilding((CateringVariant) null, DEFAULT_POSITION, false);
        BuildingFactory.createBuilding((RecreationVariant) null, DEFAULT_POSITION, false);
        BuildingFactory.createBuilding((TeachingVariant) null, DEFAULT_POSITION, false);
      });
      assertThrows(IllegalArgumentException.class, () -> {
        BuildingFactory.createBuilding(AccommodationVariant.SMALL_HOUSE, null, false);
      });
    }

    @ParameterizedTest
    @EnumSource(value = AccommodationVariant.class)
    void accommodationBuildingCreation(AccommodationVariant variant) {
      assertDoesNotThrow(() -> {
        BuildingFactory.createBuilding(variant, DEFAULT_POSITION, false);
      });
    }

    @ParameterizedTest
    @EnumSource(value = CateringVariant.class)
    void cateringBuildingCreation(CateringVariant variant) {
      assertDoesNotThrow(() -> {
        BuildingFactory.createBuilding(variant, DEFAULT_POSITION, false);
      });
    }

    @ParameterizedTest
    @EnumSource(value = RecreationVariant.class)
    void recreationBuildingCreation(RecreationVariant variant) {
      assertDoesNotThrow(() -> {
        BuildingFactory.createBuilding(variant, DEFAULT_POSITION, false);
      });
    }

    @ParameterizedTest
    @EnumSource(value = TeachingVariant.class)
    void teachingBuildingCreation(TeachingVariant variant) {
      assertDoesNotThrow(() -> {
        BuildingFactory.createBuilding(variant, DEFAULT_POSITION, false);
      });
    }

  }

}
