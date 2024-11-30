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
    switch (this) {
      case ACCOMMODATION:
        return 0;
      case CATERING:
        return 1;
      case TEACHING:
        return 2;
      case RECREATION:
        return 3;
      case MISCELLANEOUS:
        return 4;
      default:
        throw new UnreachableException("MenuTab variant not defined");
    }
  }
}
