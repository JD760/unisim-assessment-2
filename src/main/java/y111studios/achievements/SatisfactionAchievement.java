package y111studios.achievements;

import com.badlogic.gdx.Gdx;
import y111studios.GameState;
import y111studios.StudentSatisfaction;
import y111studios.World;

/**
 * This achievement is granted for reaching a satisfaction of 60%.
 */
public class SatisfactionAchievement extends Achievement {
  private World world;
  private static final String DISPLAY_NAME = "High Satisfaction!";
  private static final String DESCRIPTION = "Reach a student satisfaction level of 60%";

  public SatisfactionAchievement(String name, AchievementManager manager, World world) {
    super(name, manager, DISPLAY_NAME, DESCRIPTION);
    this.world = world;
  }

  @Override
  public boolean condition() {
    GameState state = world.getGameState();
    StudentSatisfaction satisfaction = state.getStudentSatisfaction();
    if (satisfaction.getSatisfaction() > 0) {
      return true;
    }
    return false;
  }

  @Override
  public void result() {
    Gdx.app.log("#INFO", "Satisfaction Achieved!");
    return;
  }
}
