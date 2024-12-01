package y111studios;

import java.util.Map;

import lombok.Getter;
import y111studios.buildings.Building;
import y111studios.buildings.BuildingManager;
import y111studios.buildings.BuildingType;

class StudentSatisfaction {
  private BuildingManager buildingManager;
  private @Getter double satisfaction;

  StudentSatisfaction(BuildingManager buildingManager) {
    this.buildingManager = buildingManager;
    satisfaction = 0;
  }

  public double calculate() {
    satisfaction = 100.0;
    Map<BuildingType, Integer> buildingCounts = buildingManager.getCounter().getBuildingMap();
    if (
        buildingCounts.get(BuildingType.ACCOMMODATION) == 0
        || buildingCounts.get(BuildingType.CATERING) == 0
    )
      satisfaction = 0.0;

    return satisfaction;
  }

  private double getDistance(Building building1, Building building2) {
    int xDist = building1.getArea().getOrigin().getX() + building1.getArea().getWidth()
      - building2.getArea().getOrigin().getX() - building2.getArea().getWidth();
    int yDist = building1.getArea().getOrigin().getY() + building1.getArea().getHeight()
      - building2.getArea().getOrigin().getY() - building2.getArea().getHeight();
    return Math.sqrt(xDist * xDist + yDist * yDist);
  }
}
