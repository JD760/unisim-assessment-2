package y111studios.screens.leaderboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represents the leaderboard. Contains the names associated with the top 5 recent scores.
 */
public class Leaderboard {
  public static final int MAX_SIZE = 5;
  List<LeaderboardScore> scores = new ArrayList<>(MAX_SIZE);
  boolean persistent;
  FileHandle leaderboardFile;

  public Leaderboard(boolean persistent) {
    this.persistent = persistent;
    if (!persistent) {
      return;
    }
    Gdx.app.log("#INFO", Gdx.files.getLocalStoragePath());
    leaderboardFile = Gdx.files.local("data/leaderboard.json");
    if (leaderboardFile.exists()) {
      String leaderboardJson = leaderboardFile.readString();
      if (leaderboardJson != null && leaderboardJson != "") {
        loadJson(leaderboardJson);
      }
    }
  }

  /**
   * Add a new score to the leaderboard. Ensures the size stays below the maximum
   * by removing old scores as more are added. Performs an insertion sort to keep
   * the leaderboard properly ordered.
   *
   * @param score - The score to insert to the leaderboard
   * @return - false if the name or score are invalid, or if the score is not in
   *           the top MAX_SIZE scores
   */
  public boolean insertScore(LeaderboardScore score) {
    if (score.getScore() < 0 || score.getName() == null || score.getName() == "") {
      return false;
    }

    scores.add(score);
    scores.sort(new SortByScore());
    while(scores.size() > MAX_SIZE)
      scores.remove(MAX_SIZE);

    return scores.contains(score);
  }

  /**
   * Save the leaderboard to a JSON file.
   */
  public void saveJson() {
    if (!persistent) {
      return;
    }

    Json json = new Json();
    Scores currentScores = new Scores();
    currentScores.setScores(scores);
    OutputStream stream = leaderboardFile.write(false);
    String jsonStr = json.toJson(currentScores);
    try {
      stream.write(jsonStr.getBytes(), 0, jsonStr.length());
    } catch (IOException e) {
      Gdx.app.log("#WARN", "Failed to save JSON");
      return;
    }
  }

  /**
   * Attempt to load a JSON string representation of the leaderboard scores.
   *
   * @param jsonStr - a string that should be valid JSON encoding a {@link Scores} object
   */
  public void loadJson(String jsonStr) {
    Json json = new Json();
    Scores loadedScores = json.fromJson(Scores.class, jsonStr);
    if (loadedScores == null) {
      Gdx.app.log("#WARN", "Failed to load leaderboard JSON, falling back to empty leaderboard");
      scores = new ArrayList<>();
      return;
    }

    scores = loadedScores.getScores();
    return;
  }

  /**
   * Removes all values from the leaderboard.
   */
  public void clearLeaderboard() {
    scores.clear();
  }

  public List<LeaderboardScore> getScores() {
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
  public LeaderboardScore getScore(int index) {
    if (index < 0 || index >= scores.size()) {
      return null;
    }
    return scores.get(index);
  }

  class SortByScore implements Comparator<LeaderboardScore> {
    public int compare(LeaderboardScore a, LeaderboardScore b) {
      // any null item should be smaller than any non-null item so the leaderboard has no gaps
      if (a == null) {
        return 1;
      }
      if (b == null) {
        return -1;
      }
      // sort scores from highest to lowest.
      return a.getScore() > b.getScore() ? -1 : 1;
    }
  }
}
