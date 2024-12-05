package y111studios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Graphics.Monitor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

/**
 * Runs before any other input processors and handles key presses that do things that should happen
 * on all screens such as full-screening the window
 */
@SuppressWarnings("OuterTypeFilename")
public class UniversalInputProcessor implements InputProcessor {
  int windowWidth;
  int windowHeight;
  boolean fullscreen = false;

  public void resize(int width, int height) {
    if (!fullscreen) {
      windowWidth = width;
      windowHeight = height;
    }
  }

  public boolean keyDown(int keycode) {
      // Toggle fullscreen
      if (keycode == Keys.F11) {
          Monitor currentMonitor = Gdx.graphics.getMonitor();
          DisplayMode displayMode = Gdx.graphics.getDisplayMode(currentMonitor);
          fullscreen = !fullscreen;
          if (fullscreen) {
              Gdx.graphics.setFullscreenMode(displayMode);
          } else {
              Gdx.graphics.setWindowedMode(windowWidth, windowHeight);
          }
          return true;
      }
      return false;
  }

  public boolean keyUp(int keycode) {
    return false;
  }

  public boolean keyTyped(char character) {
    return false;
  }

  @Override
  public boolean touchDown(int x, int y, int pointer, int button) {
    return false;
  }

  public boolean touchUp(int x, int y, int pointer, int button) {
    return false;
  }

  public boolean touchDragged(int x, int y, int pointer) {
    return false;
  }

  public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  public boolean mouseMoved(int x, int y) {
    return false;
  }

  public boolean scrolled(float amountX, float amountY) {
    return false;
  }
}
