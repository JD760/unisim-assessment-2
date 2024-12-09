package y111studios;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector3;

public class WorldInputProcessor implements InputProcessor {
  private World world;
  private BuildingMenu buildingMenu;
  private int cursorX;
  private int cursorY;
  private int clickX;
  private int clickY;
  private boolean clickedOnMap = false;
  private boolean dragging = true;

  public WorldInputProcessor(World world, BuildingMenu buildingMenu) {
    this.world = world;
    this.buildingMenu = buildingMenu;
  }

  public boolean keyDown(int keyCode) {
    switch (keyCode) {
      case Keys.NUM_1:
        buildingMenu.setCurrentMenuItem(0);
        break;
      case Keys.NUM_2:
        buildingMenu.setCurrentMenuItem(1);
        break;
      case Keys.NUM_3:
        buildingMenu.setCurrentMenuItem(2);
        break;
      case Keys.NUM_4:
        buildingMenu.setCurrentMenuItem(3);
        break;
      case Keys.NUM_5:
        buildingMenu.setCurrentMenuItem(4);
        break;
      case Keys.NUM_6:
        buildingMenu.flipBuildings();
        break;
      case Keys.NUM_7:
        buildingMenu.setCurrentMenuItem(6);
        break;
      case Keys.F:
        buildingMenu.flipBuildings();
        break;
      default:
        break;
    }
    return false;
  }

  public boolean keyUp(int keyCode) {
    return false;
  }

  public boolean keyTyped(char character) {
    return false;
  }

  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    clickX = cursorX = screenX;
    clickY = cursorY = screenY;
    clickedOnMap = true;
    dragging = false;
    return true;
  }

  public boolean touchUp(int x, int y, int pointer, int button) {
    clickedOnMap = false;
    if (!dragging) {
      // Try to place a building in the world
      Vector3 screenPos = world.getViewport().getCamera().unproject(
          new Vector3(x, y, 0),
          world.getViewport().getScreenX(), world.getViewport().getScreenY(),
          world.getViewport().getScreenWidth(), world.getViewport().getScreenHeight());
      if (buildingMenu.getCurrentMenuItem() >= 0 && buildingMenu.getCurrentMenuItem() < 5) {
        world.addObject(
            buildingMenu.getBuildingVariants().get(buildingMenu.getCurrentMenuTab())[buildingMenu.getCurrentMenuItem()],
            world.pixelToTile(
                (int) (screenPos.x * world.getCamera().scale),
                (int) (screenPos.y * world.getCamera().scale)),
            buildingMenu.getFlipped());
        buildingMenu.setCurrentMenuItem(-1);
      } else if (buildingMenu.getCurrentMenuItem() == 6) {
        try {
          world.removeObject(world.pixelToTile((int) (screenPos.x * world.getCamera().scale),
              (int) (screenPos.y * world.getCamera().scale)));
        } catch (IllegalStateException ignored) {
        }
      }
    }
    dragging = true;
    return false;
  }

  public boolean touchDragged(int x, int y, int pointer) {
    world.setCursorScreenPos(world.getViewport().getCamera().unproject(
        new Vector3(x, y, 0),
        world.getViewport().getScreenX(), world.getViewport().getScreenY(),
        world.getViewport().getScreenWidth(), world.getViewport().getScreenHeight()));
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

  public boolean mouseMoved(int x, int y) {
    world.setDeleteMode(buildingMenu.getCurrentMenuItem() == 6);
    world.setCursorScreenPos(world.getViewport().getCamera().unproject(
        new Vector3(x, y, 0),
        world.getViewport().getScreenX(), world.getViewport().getScreenY(),
        world.getViewport().getScreenWidth(), world.getViewport().getScreenHeight()));
    return true;
  }

  public boolean scrolled(float amountX, float amountY) {
    world.getCamera().vZoom += 0.001f * amountY;
    return true;
  }
}
