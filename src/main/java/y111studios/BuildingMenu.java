package y111studios;

import java.util.HashMap;
import java.util.Map;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Texture;
import lombok.Getter;
import lombok.Setter;
import y111studios.utils.MenuTab;
import y111studios.buildings.premade_variants.*;

/**
 * A class to interact with LibGDX to render the game window.
 */
public class BuildingMenu {
    final Main game;
    private GameState gameState;
    Texture menu;
    Texture accommodationMenu;
    Texture cateringMenu;
    Texture teachingMenu;
    @Setter @Getter MenuTab currentMenuTab;
    @Setter @Getter int currentMenuItem;
    @Getter Texture[] buildingTextures;
    @Getter Map<MenuTab, VariantProperties[]> buildingVariants;
    @Getter Viewport viewport;
    VariantProperties currentVariant;
    InputMultiplexer inputMultiplexer;
    UniversalInputProcessor universalInputProcessor = new UniversalInputProcessor();

    /**
     * Sets up the camera and loads the background
     *
     * @param game Reference to game manager
     */
    public BuildingMenu(final Main game, GameState gameState) {
        this.game = game;
        this.gameState = gameState;
        viewport = new ScreenViewport();
        menu = game.getAsset(AssetPaths.MENU);
        accommodationMenu = game.getAsset(AssetPaths.ACCOMMODATION_MENU);
        cateringMenu = game.getAsset(AssetPaths.CATERING_MENU);
        teachingMenu = game.getAsset(AssetPaths.TEACHING_MENU);
        currentMenuTab = MenuTab.ACCOMMODATION;
        currentMenuItem = -1;
        buildingTextures = new Texture[] {game.getAsset(AssetPaths.ACC1), game.getAsset(AssetPaths.ACC2), game.getAsset(AssetPaths.ACC3),
                                          game.getAsset(AssetPaths.ACC4), game.getAsset(AssetPaths.ACC5), game.getAsset(AssetPaths.TRASH), game.getAsset(AssetPaths.CATER1),
                                          game.getAsset(AssetPaths.CATER2), game.getAsset(AssetPaths.CATER3), game.getAsset(AssetPaths.REC1),
                                          game.getAsset(AssetPaths.REC2), game.getAsset(AssetPaths.TRASH), game.getAsset(AssetPaths.TEACH1), game.getAsset(AssetPaths.TEACH2),
                                          game.getAsset(AssetPaths.TEACH3), game.getAsset(AssetPaths.TEACH4), game.getAsset(AssetPaths.TEACH5), game.getAsset(AssetPaths.TRASH)};
        buildingVariants = new HashMap<>();
        buildingVariants.put(MenuTab.ACCOMMODATION, AccommodationVariant.values());
        buildingVariants.put(MenuTab.TEACHING, TeachingVariant.values());

        VariantProperties[] jointTabVariants = new VariantProperties[CateringVariant.values().length + RecreationVariant.values().length];
        System.arraycopy(CateringVariant.values(), 0, jointTabVariants, 0, CateringVariant.values().length);
        System.arraycopy(RecreationVariant.values(), 0, jointTabVariants, CateringVariant.values().length, RecreationVariant.values().length);

        buildingVariants.put(MenuTab.CATERING_RECREATION, jointTabVariants);
    }

    /**
     * Renders the game each tick.
     *
     * @param delta The time since the previous tick.
     */
    public void render() {
        viewport.apply();
        game.spritebatch.begin();

        // Draw the menu
        float preferredMenuHeight = viewport.getScreenHeight() * 0.15f;
        float preferredMenuWidth = preferredMenuHeight / menu.getHeight() * menu.getWidth();
        float menuWidth = preferredMenuWidth;
        float menuHeight = preferredMenuHeight;
        game.spritebatch.draw(menu,
            0, 0,
            640,
            menuHeight * 480f / viewport.getScreenHeight(),
            -(int)((1 - menuWidth / viewport.getScreenWidth()) * menu.getWidth() / 2),
            0,
            menu.getWidth() + (int)((1 - menuWidth / viewport.getScreenWidth()) * menu.getWidth()),
            menu.getHeight(),
            false, false
        );
        game.spritebatch.draw(accommodationMenu, 5, 85);
        game.spritebatch.draw(cateringMenu, 248, 85);
        game.spritebatch.draw(teachingMenu, 491, 85);

        // Draw the appropriate items in the menu
        for(int i = 0; i < 6; i++) {
            if(i == currentMenuItem || gameState.isPaused()) {
                game.spritebatch.setColor(1, 1, 1, 0.5f);
            } else {
                game.spritebatch.setColor(1, 1, 1, 1);
            }
            int j = i;
            if(currentMenuTab.toInt() > 0) {
                j += currentMenuTab.toInt() * 6;
            }
            game.spritebatch.draw(buildingTextures[j], 10 + i * 80, 15, 50, (int)((float)buildingTextures[j].getHeight() / buildingTextures[j].getWidth() * 50), 0, 0, buildingTextures[j].getWidth(), buildingTextures[j].getHeight(), false, false);
        }

        game.spritebatch.end();
    }

    /**
     * Handles resizing of the game window.
     *
     * @param width The new width of the window.
     * @param height The new height of the window.
     */
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
