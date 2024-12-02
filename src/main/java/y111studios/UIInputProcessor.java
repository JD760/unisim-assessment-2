package y111studios;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;


/**
 * Handles input events relating to the game UI such as resizing and pause/resume.
 */
@SuppressWarnings("AbbreviationAsWordInName")
public class UIInputProcessor implements InputProcessor {
  World world;
  GameState gameState;
  boolean[] showDebugInfo;
  BuildingMenu buildingMenu;
  int windowWidth;
  int windowHeight;

  public UIInputProcessor(
      BuildingMenu buildingMenu, World world, GameState gameState, boolean[] showDebugInfo) {
    this.buildingMenu = buildingMenu;
    this.world = world;
    this.gameState = gameState;
    this.showDebugInfo = showDebugInfo;
  }

  public void resize(int width, int height) {
    windowWidth = width;
    windowHeight = height;
  }

  public boolean keyDown(int keyCode) {
    if (keyCode == Input.Keys.ESCAPE) {
      if (gameState.isPaused()) {
        gameState.resume();
      } else {
        gameState.pause();
      }
      return true;
    }
    if (gameState.isPaused()) {
      return true;
    }
    if (keyCode == Input.Keys.TAB) {
      showDebugInfo[0] = !showDebugInfo[0];
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

  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    if (gameState.isPaused() || screenY > windowHeight * 0.85f) {
      return true;
    }
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
