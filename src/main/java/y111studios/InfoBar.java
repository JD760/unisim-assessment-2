package y111studios;
// project imports
import y111studios.buildings.BuildingCounter;
import y111studios.buildings.BuildingManager;
import y111studios.buildings.BuildingType;
// gdx imports
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;
// java imports
import java.time.Duration;
import java.util.Map;

/**
 * Class which handles the creation and rendering of the info bar
 */
public class InfoBar {
    private final Texture infoBarBackground;
    private final GameState gameState;
    private final Main game;
    private final Stage stage;

    /**
     * The InfoBar handles rendering of game information such as BuildingCounter, Clock and StudentSatisfaction
     *
     * @param gameState gameState calculates values such as time remaining, building count and student satisfaction
     * @param game reference to game manager giving access to spritebatch methods
     * @param stage container for actors and spritebatches in libGDX
     */
    public InfoBar(GameState gameState, Main game, Stage stage) {
        this.gameState = gameState;
        this.game = game;
        this.stage = stage;
        this.infoBarBackground = game.getAsset(AssetPaths.INFO_BAR_BACKGROUND);
    }

    /**
     *
     */
    public void render() {
        game.spritebatch.begin();
        float infoBarHeight = stage.getViewport().getScreenHeight() * 0.08f;

        float screenWidth = stage.getViewport().getScreenWidth();
        float screenHeight = stage.getViewport().getScreenHeight();

        // Render the InfoBar background
        game.spritebatch.draw(infoBarBackground,
                0, screenHeight - infoBarHeight, screenWidth, infoBarHeight,
                0, 0, 1, infoBarBackground.getHeight(),
                false, false);

        // Render buildingCount onto InfoBar
        int buildingCount = gameState.getCount();
        String buildingString = String.format("Count: %d / %d", buildingCount, BuildingManager.MAX_BUILDINGS);
        GlyphLayout buildingLayout = new GlyphLayout(game.font, buildingString);

        game.font.draw(game.spritebatch, buildingString,
                screenHeight * 0.01f, screenHeight * 0.99f);

        // Render individual building counts onto infoBar
        BuildingCounter counter = gameState.buildingManager.getCounter();
        Map<BuildingType, Integer> buildingCounts = counter.getBuildingMap();
        float drawIndent = screenHeight * 0.01f ;

        for (BuildingType type : BuildingType.values()) {
            int count = buildingCounts.get(type);
            String countString = String.format("%c: %d", type.toString().toCharArray()[0], count);
            GlyphLayout countLayout = new GlyphLayout(game.font, countString);
            game.font.draw(
                game.spritebatch, countString, drawIndent,
                screenHeight * 1.01f - infoBarHeight + countLayout.height
            );
            drawIndent += countLayout.width + screenHeight * 0.01f;
        }

        // Render the time remaining at the top centre of the infoBar
        Duration timeRemaining = gameState.timeRemaining();
        String timeString = String.format("%02d:%02d", timeRemaining.toMinutesPart(), timeRemaining.toSecondsPart());
        GlyphLayout timeStringLayout = new GlyphLayout(game.font, timeString);
        float textWidth = timeStringLayout.width;
        float textHeight = timeStringLayout.height;

        float timeStringX = (screenWidth - textWidth) / 2;
        float timeStringY = screenHeight - ((infoBarHeight -textHeight)/2);

        game.font.draw(game.spritebatch, timeString, timeStringX, timeStringY);

        // Render the student satisfaction percentage in top right of infoBar
        double satisfaction = gameState.getStudentSatisfaction().calculate();
        String satisfactionString = String.format("Satisfaction: " + "%.2f", satisfaction) + "%";
        GlyphLayout satisfactionStringLayout = new GlyphLayout(game.font, satisfactionString);
        float satisfactionStringWidth = satisfactionStringLayout.width;

        game.font.draw(game.spritebatch,
                satisfactionString,
                screenWidth - satisfactionStringWidth - 10,
                screenHeight - ((infoBarHeight -textHeight)/2));

        game.spritebatch.end();
    }

}
