package y111studios;

import java.util.Map;

import lombok.Getter;
import y111studios.buildings.Building;
import y111studios.buildings.BuildingManager;
import y111studios.buildings.BuildingType;
import y111studios.buildings.premade_variants.AccommodationVariant;
import y111studios.buildings.premade_variants.CateringVariant;
import y111studios.buildings.premade_variants.MiscellaneousVariant;
import y111studios.buildings.premade_variants.RecreationVariant;
import y111studios.buildings.premade_variants.TeachingVariant;

class StudentSatisfaction {
  private BuildingManager buildingManager;
  private @Getter double satisfaction;

  StudentSatisfaction(BuildingManager buildingManager) {
    this.buildingManager = buildingManager;
    satisfaction = 0;
  }

  public double calculate() {
    Map<BuildingType, Integer> buildingCounts = buildingManager.getCounter().getBuildingMap();

    // Punish not having at least one accomodation building and catering building
    if (
        buildingCounts.get(BuildingType.ACCOMMODATION) == 0
        || buildingCounts.get(BuildingType.CATERING) == 0
    ) {
      satisfaction = 0.0;
      return 0.0;
    }
    satisfaction = 100.0;

    // Punish building ratios
    double teachingRatio = (double)buildingCounts.get(BuildingType.TEACHING)
        / buildingCounts.get(BuildingType.ACCOMMODATION);
    double cateringRatio = (double)buildingCounts.get(BuildingType.CATERING)
        / buildingCounts.get(BuildingType.ACCOMMODATION);
    double recreationalRatio = (double)buildingCounts.get(BuildingType.RECREATION)
        / buildingCounts.get(BuildingType.ACCOMMODATION);
    satisfaction /= 1 + Math.abs(teachingRatio - 0.3);
    satisfaction /= 1 + Math.max(0.2 - cateringRatio, 0.0);
    satisfaction /= 1 + Math.max(0.2 - recreationalRatio, 0.0);

    int buildingRotationTotal, numRoads;
    buildingRotationTotal = numRoads = 0;
    double totalRoadFlow = 0.0;
    for (Building building : buildingManager.getBuildings()) {
      if (building == null)
        continue;
      buildingRotationTotal += building.getFlipped() ? 1 : -1;
      double accomodationDistance, cateringDistance, teachingDistance, recreationDistance,
        roadDistance, roadFlow;
      accomodationDistance = cateringDistance = teachingDistance = recreationDistance =
        roadDistance = Integer.MAX_VALUE;
      roadFlow = 0.0;
      boolean isRoad = false;

      for (Building otherBuilding : buildingManager.getBuildings()) {
        if (otherBuilding == null || otherBuilding == building)
          continue;
        // Only calculate the distance squared for now as it saves a costly square root
        double distance = getSquaredDistance(building, otherBuilding);
        if (otherBuilding.getVariant() instanceof AccommodationVariant) {
          accomodationDistance = Math.min(accomodationDistance, distance);
        } else if (otherBuilding.getVariant() instanceof CateringVariant) {
          cateringDistance = Math.min(cateringDistance, distance);
        } else if (otherBuilding.getVariant() instanceof TeachingVariant) {
          teachingDistance = Math.min(teachingDistance, distance);
        } else if (otherBuilding.getVariant() instanceof RecreationVariant) {
          recreationDistance = Math.min(recreationDistance, distance);
        } else if (otherBuilding.getVariant() == MiscellaneousVariant.ROAD_BEND1
            || otherBuilding.getVariant() == MiscellaneousVariant.ROAD_BEND2
            || otherBuilding.getVariant() == MiscellaneousVariant.ROAD_CROSS
            || otherBuilding.getVariant() == MiscellaneousVariant.STRAIGHT_ROAD) {
          roadDistance = Math.min(roadDistance, distance);
          if (building.getVariant() == MiscellaneousVariant.ROAD_BEND1
              || building.getVariant() == MiscellaneousVariant.ROAD_BEND2
              || building.getVariant() == MiscellaneousVariant.ROAD_CROSS
              || building.getVariant() == MiscellaneousVariant.STRAIGHT_ROAD) {
            isRoad = true;
            if (Math.abs(distance - 4.0) < 0.1) {
              // Reward roads being connected to each other
              roadFlow += 0.06;
              if (building.getVariant() == MiscellaneousVariant.STRAIGHT_ROAD
                    && otherBuilding.getVariant() == MiscellaneousVariant.STRAIGHT_ROAD
                    && building.getFlipped() == otherBuilding.getFlipped())
                // Reward road segments facing into each other
                roadFlow += 0.06;
            }
          }
        }
      }

      // Punish distances to other buildings
      if (building.getVariant() instanceof AccommodationVariant) {
        int numAccomodationBuildings = buildingManager.getCounter().getBuildingMap().get(
          BuildingType.ACCOMMODATION
        );
        satisfaction /= 1 + Math.pow(teachingDistance, 0.25) * 0.01 / numAccomodationBuildings;
        satisfaction /= 1 + Math.pow(cateringDistance, 0.25) * 0.01 / numAccomodationBuildings;
        satisfaction /= 1 + Math.pow(recreationDistance, 0.25) * 0.01 / numAccomodationBuildings;
        satisfaction /= 1 + Math.pow(roadDistance, 0.25) * 0.03 / numAccomodationBuildings;
      }
      if (building.getVariant() instanceof TeachingVariant) {
        int numTeachingBuildings = buildingManager.getCounter().getBuildingMap().get(
          BuildingType.TEACHING
        );
        satisfaction /= 1 + Math.pow(cateringDistance, 0.25) * 0.005 / numTeachingBuildings;
        satisfaction /= 1 + Math.pow(recreationDistance, 0.25) * 0.01 / numTeachingBuildings;
        satisfaction /= 1 + Math.pow(roadDistance, 0.25) * 0.03 / numTeachingBuildings;
      }
      if (building.getVariant() instanceof RecreationVariant) {
        int numRecreationBuildlings = buildingManager.getCounter().getBuildingMap().get(
          BuildingType.RECREATION
        );
        satisfaction /= 1 + Math.pow(cateringDistance, 0.25) * 0.005 / numRecreationBuildlings;
        satisfaction /= 1 + Math.pow(roadDistance, 0.25) * 0.03 / numRecreationBuildlings;
      }
      if (building.getVariant() instanceof CateringVariant) {
        int numCateringBuidlings = buildingManager.getCounter().getBuildingMap().get(
          BuildingType.CATERING
        );
        satisfaction /= 1 + Math.pow(roadDistance, 0.25) * 0.03 / numCateringBuidlings;
      }

      numRoads += isRoad ? 1 : 0;
      totalRoadFlow += Math.min(roadFlow, 0.12);
    }

    // Punish building rotations being all the same
    satisfaction /= 1 + Math.abs(buildingRotationTotal) / 100.0;
    // Apply reward for road flow
    double averageRoadFlow = Math.min(totalRoadFlow / Math.max(numRoads, 1), 0.12);
    System.out.println(numRoads);
    System.out.println(totalRoadFlow);
    System.out.println(averageRoadFlow);
    satisfaction = satisfaction * (1.0 - averageRoadFlow) + 100.0 * averageRoadFlow;

    return satisfaction;
  }

  private double getSquaredDistance(Building building1, Building building2) {
    int xDist = building1.getArea().getOrigin().getX() + building1.getArea().getWidth() / 2
      - building2.getArea().getOrigin().getX() - building2.getArea().getWidth() / 2;
    int yDist = building1.getArea().getOrigin().getY() + building1.getArea().getHeight() / 2
      - building2.getArea().getOrigin().getY() - building2.getArea().getHeight() / 2;
    return xDist * xDist + yDist * yDist;
  }
}
