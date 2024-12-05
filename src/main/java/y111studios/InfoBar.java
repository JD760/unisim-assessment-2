package y111studios;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.time.Duration;

public class InfoBar {
    private final Texture infoBarBackground;
    private final GameState gameState;
    private final Main game;
    private final World world;
    private final Stage stage;

    public InfoBar(World world, GameState gameState, Main game, Stage stage) {

        this.gameState = gameState;
        this.game = game;
        this.stage = stage;
        this.world = world;
        this.infoBarBackground = game.getAsset(AssetPaths.INFO_BAR_BACKGROUND);
    }

    public void create(){

    }

    public void render() {
        game.spritebatch.begin();
        float menuHeight = stage.getViewport().getScreenHeight() * 0.10f;
        game.spritebatch.draw(infoBarBackground,
                0, stage.getViewport().getScreenHeight()-menuHeight,
                stage.getViewport().getScreenWidth(),
                menuHeight,
                0,
                0,
                1,
                infoBarBackground.getHeight(),
                false, false);

        // Render the time remaining at the top of the screen
        Duration timeRemaining = gameState.timeRemaining();
        String timeString = String.format(
                "%02d:%02d", timeRemaining.toMinutesPart(), timeRemaining.toSecondsPart()
        );

        GlyphLayout timeStringLayout = new GlyphLayout(game.font, timeString);
        float textWidth = timeStringLayout.width;
        float textHeight = timeStringLayout.height;
        float textX = (world.getViewport().getWorldWidth() - textWidth) / 2;
        float textY = world.getViewport().getWorldHeight() - ((menuHeight-textHeight)/2) ;
        game.font.draw(game.spritebatch, timeString, textX, textY);

        game.spritebatch.end();
    }

}
