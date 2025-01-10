package y111studios.score;

import lombok.Getter;

/**
 * Handles logic related to tracking and calculating the total score as the game
 * progresses.
 */
public class ScoreManager {
  private @Getter int score;
  public static final int PEAK_SATISFACTION_FACTOR = 5;
  public static final int AVERAGE_SATISFACTION_FACTOR = 10;
  private int ticksElapsed = 0;
  private int satisfactionSum = 0;
  private double peakSatisfaction = 0;

  /**
   * Called once per tick.
   */
  public void tick(double currentSatisfaction) {
    satisfactionSum += currentSatisfaction;
    if (currentSatisfaction > peakSatisfaction) {
      peakSatisfaction = currentSatisfaction;
    }
    ticksElapsed++;
  }

  /**
   * Get the total score the player has built up throughout the game.
   *
   * @return - The score to be added to the leaderboard.
   */
  public int calculateScore() {
    double averageSatisfaction = satisfactionSum / (double) ticksElapsed;

    score += peakSatisfaction * PEAK_SATISFACTION_FACTOR;
    score += averageSatisfaction * AVERAGE_SATISFACTION_FACTOR;
    return score;
  }

  /**
   * Adds value to the current score for this game, for example as an achievement
   * reward.
   *
   * @param value - the amount to add to the score
   */
  public void addScore(int value) {
    score += value;
  }
}
