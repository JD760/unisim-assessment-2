package y111studios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import y111studios.buildings.premade_variants.AccommodationVariant;
import y111studios.buildings.premade_variants.CateringVariant;
import y111studios.buildings.premade_variants.MiscellaneousVariant;
import y111studios.buildings.premade_variants.RecreationVariant;
import y111studios.buildings.premade_variants.TeachingVariant;
import y111studios.buildings.premade_variants.VariantProperties;
import y111studios.utils.UnreachableException;
import y111studios.utils.MenuTab;
import static y111studios.utils.MenuTab.*;
/**
 * A class to interact with LibGDX to render the game window.
 */
public class GameOverMenu {
  private final Main game;
  private final Texture menuBackground;
  private @Getter Viewport viewport;
  private GlyphLayout buildingNameLayout;
  public static final Skin SKIN = new Skin(
      Gdx.files.internal("assets/skins/default/uiskin.json"));

  /**
   * Sets up the camera and loads the background.
   *
   * @param game Reference to game manager
   */
  @SuppressWarnings("unchecked")
public GameOverMenu(final Main game, Stage stage) {
    this.game = game;
    viewport = stage.getViewport();
    menuBackground = game.getAsset(AssetPaths.MENU_BACKGROUND);
  }

  /**
   * Renders the background of the menu.
   */
  public void render() {
    game.spritebatch.setProjectionMatrix(viewport.getCamera().combined);
    viewport.apply();
    game.spritebatch.begin();

    // Draw the menu background
    float menuHeight = viewport.getScreenHeight() * 0.15f;
    game.spritebatch.draw(menuBackground, 0, 0, viewport.getScreenWidth(), menuHeight, 0, 0, 1,
      menuBackground.getHeight(), false, false);

    game.spritebatch.end();
  }

  /**
   * Handles resizing of the game window.
   *
   * @param width  The new width of the window.
   * @param height The new height of the window.
   */
  public void resize(int width, int height) {
  }

  public int getScreenHeight() {
    return (int) (viewport.getScreenHeight() * 0.15f);
  }

}
