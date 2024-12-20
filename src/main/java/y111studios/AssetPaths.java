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
  SNOWY_MAP_BACKGROUD_TOP_LEFT("assets/UnisimMapTL_Snow_final.png"),
  SNOWY_MAP_BACKGROUD_TOP_RIGHT("assets/UnisimMapTR_Snow_final.png"),
  SNOWY_MAP_BACKGROUD_BOTTOM_LEFT("assets/UnisimMapBL_Snow_final.png"),
  SNOWY_MAP_BACKGROUD_BOTTOM_RIGHT("assets/UnisimMapBR_Snow_final.png"),
  FLOODED_MAP_BACKGROUD_TOP_LEFT("assets/UnisimMapTL_flood_final.png"),
  FLOODED_MAP_BACKGROUD_TOP_RIGHT("assets/UnisimMapTR_flood_final.png"),
  FLOODED_MAP_BACKGROUD_BOTTOM_LEFT("assets/UnisimMapBL_flood_final.png"),
  FLOODED_MAP_BACKGROUD_BOTTOM_RIGHT("assets/UnisimMapBR_flood_final.png"),
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
  ACC1("assets/Accommodation_1.png"),
  ACC2("assets/Acc2.png"),
  ACC3("assets/Acc3.png"),
  ACC4("assets/Acc4.png"),
  ACC5("assets/Acc5.png"),
  CATER1("assets/CATER_1.png"),
  CATER2("assets/CATER_2.png"),
  CATER3("assets/CATER_3.png"),
  CATER4("assets/CATER_4.png"),
  CATER5("assets/Cater5.png"),
  REC1("assets/Rec1.png"),
  REC2("assets/Rec2.png"),
  TREE1("assets/tree1.png"),
  TREE2("assets/tree3.png"),
  TREE3("assets/tree_8_2.png"),
  TEACH1("assets/Teach1.png"),
  TEACH2("assets/Teach2.png"),
  TEACH3("assets/TEACH_3.png"),
  TEACH4("assets/TEACH_4.png"),
  TEACH5("assets/Teach5.png"),
  BIKE_SHED("assets/bike_shed.png"),
  STRAIGHT_ROAD("assets/road1.png"),
  STRAIGHT_ROAD2("assets/road2.png"),
  ROAD_CROSS("assets/road5.png"),
  ROAD_BEND1("assets/road4.png"),
  ROAD_BEND2_FLIPPED("assets/road6.png"),
  ROAD_BEND2("assets/road7.png"),
  GAME_OVER("assets/GameOver.png"),
  TRASH("assets/Trash.png"),
  ROTATE("assets/axis-z-rotate-clockwise.png"),
  UNISIM_LOGO("assets/UnisimLogo.png"),
  INSTRUCTIONS("assets/Instructions.png"),
  INFO_BAR_BACKGROUND("assets/InfoBarBackground.png"),
  MUTE_BUTTON("assets/volume-mute-filled.png"),
  UNMUTE_BUTTON("assets/volume-up-filled.png"),
  PAUSE_BUTTON("assets/pause-filled.png"),
  PLAY_BUTTON("assets/play-filled-alt.png"),
  LEADERBOARD_TITLE("assets/LeaderboardTitle.png"),
  ACHIEVEMENT_NOTIFICATION("assets/AchievementNotification.png"),
  SNOWFLAKE("assets/snowflake.png"),
  RAINDROP("assets/raindrop.png"),
  SNOW_EVENT("assets/events/SnowEvent.png"),
  PANDEMIC_EVENT("assets/events/PandemicEvent.png"),
  OPEN_DAY_EVENT("assets/events/OpenDayEvent.png"),
  SATISFACTION_ACHIEVEMENT("assets/achievements/satisfactionAchievement.png");

  private final String path;

  AssetPaths(String path) {
    this.path = path;
  }
}
