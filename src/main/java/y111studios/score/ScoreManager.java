package y111studios.score;

/**
 * Handles logic related to tracking and calculating the total score as the game progresses.
 */
public class ScoreManager {
  private int ticksElapsed;
  private float peakSatisfaction;

  public ScoreManager() {
    ticksElapsed = 0;
    peakSatisfaction = 0.0f;
  }

  /**
   * Called once per tick.
   */
  public void tick() {
    ticksElapsed++;
  }
}
