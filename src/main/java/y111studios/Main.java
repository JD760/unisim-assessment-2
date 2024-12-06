package y111studios;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import y111studios.screens.StartScreen;

/**
 * Holds most driver code for the game, handles graphics/windowing and textures management.
 */
public class Main extends Game {
  public AssetLibrary assetLib;
  public SpriteBatch spritebatch;
  public BitmapFont font;
  Music backgroundMusic;

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

    Audio audio = Gdx.audio;
    backgroundMusic = audio.newMusic(Gdx.files.internal("assets/retro-8bit-happy-videogame-music-243998.mp3"));
    backgroundMusic.setVolume(0.5f);
    backgroundMusic.setLooping(true);
    backgroundMusic.play();
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
}
