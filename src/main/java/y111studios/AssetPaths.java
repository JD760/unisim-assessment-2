package y111studios;

import lombok.Getter;

/**
 * AssetPaths class used to store the paths to the assets used in the game.
 * 
 * <p>
 * This class is used to store the constant paths to the internal assets to
 * reduce the occurrence of
 * magic strings in the code.
 * </p>
 */
@Getter
public enum AssetPaths {
  START_SCREEN("assets/StartScreen.png"),
  MAP_BACKGROUND_TOP_LEFT("assets/UnisimMapTL.png"),
  MAP_BACKGROUND_TOP_RIGHT("assets/UnisimMapTR.png"),
  MAP_BACKGROUND_BOTTOM_LEFT("assets/UnisimMapBL.png"),
  MAP_BACKGROUND_BOTTOM_RIGHT("assets/UnisimMapBR.png"),
  OBSTACLE1("assets/obstacle1.png"),
  OBSTACLE2("assets/obstacle2.png"),
  OBSTACLE3("assets/obstacle3.png"),
  OBSTACLE4("assets/obstacle4.png"),
  MENU_BACKGROUND("assets/MenuBackground.png"),
  MENU_UNSELECTED_TAB("assets/MenuUnselectedTab.png"),
  MENU_SELECTED_TAB("assets/MenuSelectedTab.png"),
  ACCOMMODATION_MENU("assets/Accommodation.png"),
  CATERING_MENU("assets/Catering.png"),
  TEACHING_MENU("assets/Teaching.png"),
  PAUSE("assets/Pause.png"),
  ACC1("assets/Acc1.png"),
  ACC2("assets/Acc2.png"),
  ACC3("assets/Acc3.png"),
  ACC4("assets/Acc4.png"),
  ACC5("assets/Acc5.png"),
  CATER1("assets/Cater1.png"),
  CATER2("assets/Cater2.png"),
  CATER3("assets/Cater3.png"),
  CATER4("assets/Cater4.png"),
  CATER5("assets/Cater5.png"),
  REC1("assets/Rec1.png"),
  REC2("assets/Rec2.png"),
  TREE1("assets/tree1.png"),
  TREE2("assets/tree3.png"),
  TREE3("assets/tree8.png"),
  TEACH1("assets/Teach1.png"),
  TEACH2("assets/Teach2.png"),
  TEACH3("assets/Teach3.png"),
  TEACH4("assets/Teach4.png"),
  TEACH5("assets/Teach5.png"),
  BIKE_SHED("assets/bike-storage.png"),
  STRAIGHT_ROAD("assets/road1.png"),
  ROAD_CROSS("assets/road5.png"),
  ROAD_BEND1("assets/road4.png"),
  ROAD_BEND2("assets/road7.png"),
  GAME_OVER("assets/GameOver.png"),
  TRASH("assets/Trash.png"),
  ROTATE("assets/axis-z-rotate-clockwise.png"),
  UNISIM_LOGO("assets/UnisimLogo.png");

  private final String path;

  AssetPaths(String path) {
    this.path = path;
  }
}
