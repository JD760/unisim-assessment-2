package y111studios;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import y111studios.screens.MapScreen;
import y111studios.screens.StartScreen;

/**
 * Holds most driver code for the game, handles graphics/windowing and textures management.
 */
public class Main extends Game {
  public AssetLibrary assetLib;
  public SpriteBatch spritebatch;
  public BitmapFont font;

  @Override
  public void create() {
    assetLib = AssetLibrary.getInstance();
    assetLib.init();
    spritebatch = new SpriteBatch();
    font = new BitmapFont();
    font.setColor(Color.BLACK);
    font.getData().setScale(1.3f);
    Gdx.graphics.setWindowedMode(1280, 720);
    this.setScreen(new StartScreen(this));
  }

  /**
   * Get the asset from the asset library.
   *
   * @param path the asset's load path
   * @return the asset
   */
  public Texture getAsset(AssetPaths path) {
    return assetLib.manager.get(path.getPath());
  }

  @Override
  public void dispose() {
    spritebatch.dispose();
    font.dispose();
    assetLib.manager.dispose();
  }

  @Override
  public void render() {
    super.render();
  }
}
