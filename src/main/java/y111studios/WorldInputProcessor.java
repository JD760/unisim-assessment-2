package y111studios;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import y111studios.buildings.premade_variants.MiscellaneousVariant;
import y111studios.buildings.premade_variants.VariantProperties;
import y111studios.screens.InstructionsScreen;
import y111studios.screens.StartScreen;

/**
 * Handles input events related to the world, such as building placement.
 */
public class WorldInputProcessor implements InputProcessor {
  private final World world;
  private final BuildingMenu buildingMenu;
  private int cursorX;
  private int cursorY;
  private int clickX;
  private int clickY;
  private boolean clickedOnMap = false;
  private boolean dragging = true;
  private List<Integer> keysDown = new ArrayList<>(3);

  public WorldInputProcessor(World world, BuildingMenu buildingMenu) {
    this.world = world;
    this.buildingMenu = buildingMenu;
  }

  @Override
  public boolean keyDown(int keyCode) {
    keysDown.add(keyCode);

    if (keysDown.contains(Keys.CONTROL_LEFT) || keysDown.contains(Keys.CONTROL_RIGHT)) {
      if (keysDown.contains(Keys.Z)) {
        world.undoPlacement();
      }
    }
    GameState state = world.getGameState();
    switch (keyCode) {
      case Keys.NUM_1:
        setItem(0);
        break;
      case Keys.NUM_2:
        setItem(1);
        break;
      case Keys.NUM_3:
        setItem(2);
        break;
      case Keys.NUM_4:
        setItem(3);
        break;
      case Keys.NUM_5:
        setItem(4);
        break;
      case Keys.R:
        buildingMenu.flipBuildings();
        break;
      case Keys.D:
        setItem(6);
        break;
      case Keys.TAB:
        buildingMenu.updateTab((buildingMenu.getCurrentMenuTab().toInt() + 1) % 5);
        break;
      case Keys.U:
        world.undoPlacement();
        break;
      case Keys.SPACE:
        if (state.isPaused()) {
          state.resume();
        } else {
          state.pause();
        }
        break;
      case Keys.ESCAPE:
        buildingMenu.setCurrentMenuItem(-1);
        break;
      case Keys.I:
        InstructionsScreen instructionsScreen = new InstructionsScreen(
            world.getGame(), world.getGame().getScreen());
        world.getGame().setScreen(instructionsScreen);
        break;
      default:
        break;
    }
    return false;
  }

  private void setItem(int item) {
    if (buildingMenu.getCurrentMenuItem() == item) {
      // selecting the same item twice clears the cursor
      buildingMenu.setCurrentMenuItem(-1);
    } else {
      buildingMenu.setCurrentMenuItem(item);
    }
  }

  public boolean keyUp(int keyCode) {
    keysDown.remove(Integer.valueOf(keyCode));
    return false;
  }

  public boolean keyTyped(char character) {
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    if (world.getViewport().getWorldHeight() - screenY < buildingMenu.getScreenHeight()) {
      return false;
    }
    float infoBarHeight = world.getParentScreen().getInfoBar().getInfoBarHeight();
    if (screenY < infoBarHeight) {
      return false;
    }

    clickX = cursorX = screenX;
    clickY = cursorY = screenY;
    clickedOnMap = true;
    dragging = false;
    return true;
  }

  @Override
  public boolean touchUp(int x, int y, int pointer, int button) {
    clickedOnMap = false;
    if (!dragging) {
      // Try to place a building in the world
      Vector3 screenPos = world.getViewport().getCamera().unproject(
          new Vector3(x, y, 0),
          world.getViewport().getScreenX(), world.getViewport().getScreenY(),
          world.getViewport().getScreenWidth(), world.getViewport().getScreenHeight());
      if (buildingMenu.getCurrentMenuItem() >= 0 && buildingMenu.getCurrentMenuItem() < 5) {
        VariantProperties variant = buildingMenu.getBuildingVariants().get(
            buildingMenu.getCurrentMenuTab())[buildingMenu.getCurrentMenuItem()];
        if (world.addObject(
            variant,
            world.currentGridPosition(),
            buildingMenu.isFlipped()
        )) {
          if (
              variant != MiscellaneousVariant.STRAIGHT_ROAD
              && variant != MiscellaneousVariant.ROAD_CROSS
              && variant != MiscellaneousVariant.ROAD_BEND1
              && variant != MiscellaneousVariant.ROAD_BEND2
          ) {
            buildingMenu.setCurrentMenuItem(-1);
          }
        }
      } else if (buildingMenu.getCurrentMenuItem() == 6) {
        try {
          world.removeObject(world.pixelToTile((int) (screenPos.x * world.getCamera().scale),
              (int) (screenPos.y * world.getCamera().scale)));
        } catch (IllegalStateException ignored) {}
      }
    }
    dragging = true;
    return false;
  }

  @Override
  public boolean touchDragged(int x, int y, int pointer) {
    world.setCursorScreenPos(new Vector3(x, y, 0));
    if (clickedOnMap) {
      if (Math.max(Math.abs(cursorX - clickX),
          Math.abs(cursorY - clickY)) > 5) {
        dragging = true;
      }
      world.getCamera().pan(cursorX - x, cursorY - y);
      cursorX = x;
      cursorY = y;
      return true;
    }
    return false;
  }

  public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean mouseMoved(int x, int y) {
    world.setDeleteMode(buildingMenu.getCurrentMenuItem() == 6);
    world.setCursorScreenPos(new Vector3(x, y, 0));
    return true;
  }

  public boolean scrolled(float amountX, float amountY) {
    world.getCamera().vZoom += 0.001f * amountY;
    return true;
  }
}
