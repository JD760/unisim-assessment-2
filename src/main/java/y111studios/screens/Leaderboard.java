package y111studios.screens;

import java.util.ArrayList;
import java.util.List;
import y111studios.utils.Score;

/**
 * Represents the leaderboard. Contains the names associated with the top 5 recent scores.
 */
public class Leaderboard {
  public static final int MAX_SIZE = 5;
  List<Score> scores = new ArrayList<>(MAX_SIZE);

  /**
   * Add a new score to the leaderboard. Ensures the size stays below the maximum
   * by removing old scores as more are added
   *
   * @param score - The score to insert to the leaderboard
   * @return - false if the name or score are invalid.
   */
  public boolean insertScore(Score score) {
    if (score.getScore() < 0 || score.getName() == null || score.getName() == "") {
      return false;
    }

    // account for adding an element increasing the size by 1
    if (scores.size() >= MAX_SIZE - 1) {
      removeLowestScore();
    }

    scores.add(score);
    return true;
  }

  /**
   * Removes all values from the leaderboard.
   */
  public void clearLeaderboard() {
    scores.clear();
  }

  /**
   * Finds and removes the lowest score present in the leaderboard.
   */
  private void removeLowestScore() {
    // if only one element is present, clear the leaderboard as it has become empty
    if (scores.size() == 1) {
      clearLeaderboard();
    }
    scores.set(scores.size() - 1, null);
  }

  public List<Score> getScores() {
    return scores;
  }

  public int getSize() {
    return scores.size();
  }

  public Score getScore(int index) {
    return scores.get(index);
  }


}