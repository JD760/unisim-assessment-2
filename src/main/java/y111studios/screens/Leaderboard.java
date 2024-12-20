package y111studios.screens;

import java.util.ArrayList;
import java.util.Comparator;
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
   * by removing old scores as more are added. Performs an insertion sort to keep
   * the leaderboard properly ordered.
   *
   * @param score - The score to insert to the leaderboard
   * @return - false if the name or score are invalid.
   */
  public boolean insertScore(Score score) {
    if (score.getScore() < 0 || score.getName() == null || score.getName() == "") {
      return false;
    }

    // make space by removing the lowest score if a new score is added.
    if (scores.size() >= MAX_SIZE) {
      // only add a new score if it is higher than the current lowest score
      if (score.getScore() > scores.get(MAX_SIZE - 1).getScore()) {
        scores.set(scores.size() - 1, null);
      } else {
        return false;
      }
    }

    scores.add(score);
    scores.sort(new SortByScore());
    return true;
  }

  /**
   * Removes all values from the leaderboard.
   */
  public void clearLeaderboard() {
    scores.clear();
  }

  public List<Score> getScores() {
    return scores;
  }

  public int getSize() {
    return scores.size();
  }

  /**
   * Get the score at the given position in the leaderboard. 
   * This is generally NOT equal to the order of insertion as the leaderboard sorts itself.
   *
   * @param index - the position of the score to return
   * @return - the Score at the provided position, or null if no such position exists.
   */
  public Score getScore(int index) {
    if (index < 0 || index >= scores.size()) {
      return null;
    }
    return scores.get(index);
  }

  class SortByScore implements Comparator<Score> {
    public int compare(Score a, Score b) {
      if (a == null || b == null) {
        // any null item should be smaller than any non-null item so the leaderboard has no gaps
        return -1;
      }
      // sort scores from highest to lowest.
      return b.getScore() > a.getScore() ? 1 : -1;
    }
  }
}
