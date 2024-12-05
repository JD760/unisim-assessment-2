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
  int maxScore = Integer.MIN_VALUE;
  int minScore = Integer.MAX_VALUE;

  /**
   * Add a new score to the leaderboard. Updates the maximum and minimum scores
   * and ensures the size of the leaderboard stays below the maximum.
   *
   * @param name - The name displayed on the leaderboard
   * @param score - The score achieved by that user
   * @return - false if the name was empty or null, true otherwise
   */
  public boolean insertScore(String name, int score) {
    if (name == null || name == "") {
      return false;
    }

    // account for adding an element increasing the size by 1
    if (scores.size() >= MAX_SIZE - 1) {
      findAndRemoveLowest();
    }
  
    scores.add(new Score(name, score));
    if (score > maxScore) {
      maxScore = score;
    }
    if (score < minScore) {
      minScore = score;
    }
    return true;
  }

  /**
   * Removes all values from the leaderboard.
   */
  public void clearLeaderboard() {
    scores.clear();
    minScore = Integer.MAX_VALUE;
    maxScore = Integer.MIN_VALUE;
  }

  /**
   * Finds and removes the lowest score present in the leaderboard.
   */
  private void findAndRemoveLowest() {
    int lowest = Integer.MAX_VALUE;
    int index = -1;

    // removing the lowest from a size one collection is identical to just clearing it
    if (scores.size() == 1) {
      clearLeaderboard();
    }

    // we can guarantee there are at least two elements so need not worry about the maxScore
    // falling out of sync here
    for (int i = 0; i < scores.size(); i++) {
      Score score = scores.get(i);
      if (score.getScore() == minScore) {
        index = i;
      } else if (score.getScore() < lowest) {
        lowest = score.getScore();
      }
    }
    minScore = lowest;
    // shift all the items to replace the removed score
    for (int i = index; i < scores.size(); i++) {
      if (i < MAX_SIZE) {
        scores.set(i, scores.get(i + 1));
      }
    }
  }

  public List<Score> getScores() {
    return scores;
  }

  public void printLeaderboard() {
    for (int i = 0; i < scores.size(); i++) {
      Score score = scores.get(i);
      System.out.println((i + 1) + " - " + score.getName() + " - " + score.getScore());
    }
  }


}