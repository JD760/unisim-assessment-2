package y111studios;

// gdx imports
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
// java imports
import java.time.Duration;
import java.util.Map;
// project imports
import y111studios.buildings.BuildingCounter;
import y111studios.buildings.BuildingManager;
import y111studios.buildings.BuildingType;
import y111studios.events.FloodEvent;
import y111studios.events.SnowEvent;

/**
 * Class which handles the creation and rendering of the info bar.
 */
public class InfoBar {
  private final Texture infoBarBackground;
  private final GameState gameState;
  private final Main game;
  private final Stage stage;
  private final Texture muteTexture;
  private final Texture unmuteTexture;
  private Image muteImage;
  private Image unmuteImage;
  private final Texture pauseTexture;
  private final Texture playTexture;
  private Image pauseImage;
  private Image playImage;
  private Table table;
  @SuppressWarnings("rawtypes")
  private Cell muteButtonCell;
  @SuppressWarnings("rawtypes")
  private Cell pauseButtonCell;
  private float infoBarHeight;

  /**
   * The InfoBar handles rendering of game information such as BuildingCounter,
   * Clock and StudentSatisfaction.
   *
   * @param gameState gameState calculates values such as time remaining, building
   *                  count and student satisfaction
   * @param game      reference to game manager giving access to spritebatch
   *                  methods
   * @param stage     container for actors and spritebatches in libGDX
   */
  public InfoBar(GameState gameState, Main game, Stage stage) {
    this.gameState = gameState;
    this.game = game;
    this.stage = stage;
    infoBarBackground = game.getAsset(AssetPaths.INFO_BAR_BACKGROUND);
    muteTexture = game.getAsset(AssetPaths.MUTE_BUTTON);
    unmuteTexture = game.getAsset(AssetPaths.UNMUTE_BUTTON);
    muteImage = new Image(muteTexture);
    unmuteImage = new Image(unmuteTexture);
    pauseTexture = game.getAsset(AssetPaths.PAUSE_BUTTON);
    playTexture = game.getAsset(AssetPaths.PLAY_BUTTON);
    pauseImage = new Image(pauseTexture);
    playImage = new Image(playTexture);

    muteImage.addListener(new ClickListener() {
      @SuppressWarnings("unchecked")
      @Override
      public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
        game.backgroundMusic.setVolume(0.3f);
        muteButtonCell.setActor(unmuteImage);
      }
    });
    unmuteImage.addListener(new ClickListener() {
      @SuppressWarnings("unchecked")
      @Override
      public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
        game.backgroundMusic.setVolume(0f);
        muteButtonCell.setActor(muteImage);
      }
    });
    playImage.addListener(new ClickListener() {
      @SuppressWarnings("unchecked")
      @Override
      public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
        gameState.resume();
        pauseButtonCell.setActor(pauseImage);
      }
    });
    pauseImage.addListener(new ClickListener() {
      @SuppressWarnings("unchecked")
      @Override
      public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
        gameState.pause();
        pauseButtonCell.setActor(playImage);
      }
    });

    this.table = new Table();
    muteButtonCell = table.add(unmuteImage);
    pauseButtonCell = table.add(playImage);
    stage.addActor(table);
  }

  /**
   * Called every tick, draws the UI components.
   */
  @SuppressWarnings("unchecked")
  public void render() {
    game.spritebatch.begin();

    float screenWidth = stage.getViewport().getScreenWidth();
    float screenHeight = stage.getViewport().getScreenHeight();

    // handle pausing from external sources (eg keypresses)
    if (gameState.isPaused()) {
      pauseButtonCell.setActor(playImage);
    } else {
      pauseButtonCell.setActor(pauseImage);
    }

    // Render the InfoBar background
    game.spritebatch.draw(infoBarBackground,
        0, screenHeight - infoBarHeight, screenWidth, infoBarHeight,
        0, 0, 1, infoBarBackground.getHeight(),
        false, false);

    // Render buildingCount onto InfoBar
    int buildingCount = gameState.getCount();
    String buildingString = String.format(
        "Count: %d / %d", buildingCount, BuildingManager.MAX_BUILDINGS - 4);

    game.font.draw(game.spritebatch, buildingString,
        screenHeight * 0.01f, screenHeight * 0.99f);

    // Render individual building counts onto infoBar
    BuildingCounter counter = gameState.buildingManager.getCounter();
    Map<BuildingType, Integer> buildingCounts = counter.getBuildingMap();
    float drawIndent = screenHeight * 0.01f;

    for (BuildingType type : BuildingType.values()) {
      int count = buildingCounts.get(type);
      String countString = String.format("%c: %d", type.toString().toCharArray()[0], count);
      GlyphLayout countLayout = new GlyphLayout(game.font, countString);
      game.font.draw(
          game.spritebatch, countString, drawIndent,
          screenHeight * 1.01f - infoBarHeight + countLayout.height);
      drawIndent += countLayout.width + screenHeight * 0.01f;
    }

    // Render the time remaining at the top centre of the infoBar
    Duration timeRemaining = gameState.timeRemaining();
    String timeString = String.format(
        "%02d:%02d", timeRemaining.toMinutesPart(), timeRemaining.toSecondsPart());
    GlyphLayout timeStringLayout = new GlyphLayout(game.font, timeString);
    float textWidth = timeStringLayout.width;
    float textHeight = timeStringLayout.height;

    float timeStringX = (screenWidth - textWidth) / 2;
    float timeStringY = screenHeight - ((infoBarHeight - textHeight) / 2);

    game.font.draw(game.spritebatch, timeString, timeStringX, timeStringY);

    // Render the student satisfaction percentage in top right of infoBar
    double satisfaction = gameState.getStudentSatisfaction().getSatisfaction();
    String satisfactionString = String.format("Satisfaction: " + "%.2f", satisfaction) + "%";
    GlyphLayout satisfactionStringLayout = new GlyphLayout(game.font, satisfactionString);
    float satisfactionStringWidth = satisfactionStringLayout.width;

    game.font.draw(game.spritebatch,
        satisfactionString,
        screenWidth - satisfactionStringWidth - 10,
        screenHeight - ((infoBarHeight - textHeight) / 2));

    // Render the current event to the right of the time remaining
    String eventString;
    if (gameState.getCurrentEvent() instanceof FloodEvent) {
      eventString = "Event: Flood";
    } else if (gameState.getCurrentEvent() instanceof SnowEvent) {
      eventString = "Event: Snow";
    } else {
      eventString = "Event: None";
    }
    GlyphLayout eventStringLayout = new GlyphLayout(game.font, eventString);
    textWidth = eventStringLayout.width;

    float eventStringX = screenWidth - textWidth - satisfactionStringWidth * 1.3f;
    float eventStringY = screenHeight - ((infoBarHeight - textHeight) / 2);

    game.font.draw(game.spritebatch, eventString, eventStringX, eventStringY);

    game.spritebatch.end();
  }

  /**
   * Called whenever the window size changes.
   *
   * @param width - the new width of the window
   * @param height - the new height of the window
   */
  public void resize(int width, int height) {
    infoBarHeight = height * 0.08f;
    table.setBounds(0, height - infoBarHeight, width, infoBarHeight);
    muteButtonCell.width(infoBarHeight * 0.6f).height(infoBarHeight * 0.6f)
        .padLeft(height * 0.35f);
    pauseButtonCell.width(infoBarHeight * 0.6f).height(infoBarHeight * 0.6f)
        .padLeft(height * 0.02f).padRight(width * 0.65f);
  }
}
