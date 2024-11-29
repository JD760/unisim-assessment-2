package y111studios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import y111studios.utils.MenuTab;
import y111studios.buildings.BuildingManager;
import y111studios.buildings.BuildingType;
import y111studios.buildings.premade_variants.*;

/**
 * A class to interact with LibGDX to render the game window.
 */
public class MapScreen extends ScreenAdapter {
    // Proportional width of the display.
    int width = 640;
    // Proportional height of the display.
    int height = 480;
    // Width of map in tiles.
    public static final int TILE_WIDTH = 75;
    // Height of map in tiles.
    public static final int TILE_HEIGHT = 75;

    final Main game;
    GameState gameState;
    Viewport viewport;
    Texture pauseMenu;
    boolean[] showDebugInfo = {false};
    World world;
    BuildingMenu buildingMenu;
    InputMultiplexer inputMultiplexer;
    UniversalInputProcessor universalInputProcessor = new UniversalInputProcessor();

    /**
     * Sets up the camera and loads the background
     *
     * @param game Reference to game manager
     */
    public MapScreen(final Main game) {
        this.game = game;
        this.gameState = new GameState(TILE_WIDTH, TILE_HEIGHT);
        viewport = new FitViewport(width, height);
        viewport.getCamera().position.set(width / 2f, height / 2f, 0);
        viewport.getCamera().update();
        pauseMenu = game.getAsset(AssetPaths.PAUSE);

        world = new World(game, gameState);
        buildingMenu = new BuildingMenu(game, gameState);

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(universalInputProcessor);
        inputMultiplexer.addProcessor(new UIInputProcessor(
            buildingMenu, world, gameState, showDebugInfo
        ));
        inputMultiplexer.addProcessor(new WorldInputProcessor(world, buildingMenu));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        world.render(delta);
        buildingMenu.render();

        viewport.apply();
        game.spritebatch.begin();

        // Render the time remaining at the top of the screen
        Duration timeRemaining = gameState.timeRemaining();
        String timeString = String.format("%02d:%02d", timeRemaining.toMinutesPart(), timeRemaining.toSecondsPart());

        GlyphLayout layout = new GlyphLayout(game.font, timeString);
        float textWidth = layout.width;
        float textX = (viewport.getWorldWidth() - textWidth) / 2;
        float textY = viewport.getWorldHeight() - 20;
        game.font.draw(game.spritebatch, timeString, textX, textY);

        // Render the total count of buildings placed
        if (showDebugInfo[0]) {
            int buildingCount = gameState.getCount();
            String buildingString = String.format("Count: %d / %d", buildingCount, BuildingManager.MAX_BUILDINGS);

            float buildingX = 15;
            float buildingY = 120;
            game.font.draw(game.spritebatch, buildingString, buildingX, buildingY);

            // Render individual building counts
            Map<BuildingType, Integer> buildingCounts = gameState.buildingManager.getCounter().getBuildingMap();
            for (BuildingType type : BuildingType.values()) {
                int count = buildingCounts.get(type);
                String countString = String.format("%c: %d", type.toString().toCharArray()[0], count);
                buildingY += 20;
                game.font.draw(game.spritebatch, countString, buildingX, buildingY);
            }

            game.font.draw(game.spritebatch, String.valueOf(Gdx.graphics.getFramesPerSecond()), 15, (buildingY + 20));
        }

        // Draw the pause menu if paused
        if(gameState.isPaused()) {
            game.spritebatch.setColor(1, 1, 1, 1);
            if (gameState.isTimeUp()) {
                // Covers case where the game is locked paused due to time running out
                game.spritebatch.draw(game.getAsset(AssetPaths.GAME_OVER), 220, 204);
            } else {
                game.spritebatch.draw(pauseMenu, 220, 204);
            }
        }

        game.spritebatch.end();

        // Check for game over
        if (gameState.isTimeUp()) {
            gameState.pause(); // Lock pause
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        world.resize(width, height);
        universalInputProcessor.resize(width, height);
        buildingMenu.resize(width, height);
    }

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        game.dispose();
    }
}
