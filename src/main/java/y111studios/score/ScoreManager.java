package y111studios.score;

import lombok.Getter;
import y111studios.GameState;
import y111studios.Main;
/**
 * Handles logic related to tracking and calculating the total score as the game progresses.
 */
public class ScoreManager {
  private final GameState gameState;
  private final Main game;
  private @Getter int score;
  public static final int PEAK_SATISFACTION_FACTOR = 5;
  public static final int AVERAGE_SATISFACTION_FACTOR = 10;
  private int ticksElapsed = 0;
  private int satisfactionSum = 0;
  private double peakSatisfaction = 0;

  public ScoreManager(GameState gameState, Main game) {
    this.gameState = gameState;
    this.game = game;
  }

  /**
   * Called once per tick.
   */
  public void tick() {
    double satisfaction = gameState.getStudentSatisfaction().getSatisfaction();
    satisfactionSum += satisfaction;
    if (satisfaction > peakSatisfaction) {
      peakSatisfaction = satisfaction;
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
   * Adds value to the current score for this game, for example as an achievement reward.
   *
   * @param value - the amount to add to the score
   */
  public void addScore(int value) {
    score += value;
  }
}
