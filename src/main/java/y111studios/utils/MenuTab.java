package y111studios.utils;

/**
 * Allows the user to select buildings to place on the map.
 */
public enum MenuTab {
  ACCOMMODATION, CATERING, TEACHING, RECREATION, MISCELLANEOUS;

  /**
   * Converts the MenuTab to an integer based on the order of the tabs. This is
   * agnostic to the order of the variants described in the enum.
   *
   * @return the tab's order in the list
   */
  public int toInt() {
      return switch (this) {
          case ACCOMMODATION -> 0;
          case CATERING -> 1;
          case TEACHING -> 2;
          case RECREATION -> 3;
          case MISCELLANEOUS -> 4;
      };
  }
}
