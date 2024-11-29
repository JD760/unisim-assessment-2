package y111studios;

import java.util.Map;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;

import y111studios.buildings.BuildingFactory;
import y111studios.buildings.premade_variants.VariantProperties;
import y111studios.utils.MenuTab;

public class WorldInputProcessor implements InputProcessor {
    private int[] currentMenuItem;
    private World world;
    private GameState gameState;
    private Map<MenuTab, VariantProperties[]> buildingVariants;
    private MenuTab[] currentMenuTab;
    private int cursorX;
    private int cursorY;
    private int clickX;
    private int clickY;
    private boolean clickedOnMap = false;
    private boolean dragging = true;

    WorldInputProcessor(
        int[] currentMenuItem, World world, GameState gameState, Map<MenuTab,
        VariantProperties[]> buildingVariants, MenuTab[] currentMenuTab
    ) {
        this.currentMenuItem = currentMenuItem;
        this.world = world;
        this.gameState = gameState;
        this.buildingVariants = buildingVariants;
        this.currentMenuTab = currentMenuTab;
    }

    public boolean keyDown(int keyCode) {
        return false;
    }

    public boolean keyUp(int keyCode) {
        return false;
    }

    public boolean keyTyped (char character) {
        return false;
    }

    public boolean touchDown (int screenX, int screenY, int pointer, int button) {
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
            world.getViewport().getScreenWidth(), world.getViewport().getScreenHeight()
        );
        if (currentMenuItem[0] >= 0 && currentMenuItem[0] < 5) {
            world.addObject(buildingVariants.get(currentMenuTab[0])[currentMenuItem[0]],
                world.pixelToTile(
                    (int)(screenPos.x * world.getCamera().scale),
                    (int)(screenPos.y * world.getCamera().scale)
                )
            );
            currentMenuItem[0] = -1;
            world.setSelectedBuilding(null);
        } else if(currentMenuItem[0] == 5) {
            try{
                world.removeObject(world.pixelToTile((int)(screenPos.x * world.getCamera().scale), (int)(screenPos.y * world.getCamera().scale)));
            } catch(IllegalStateException ignored) {}
        }
    }
    dragging = true;
    return false;
  }

  public boolean touchDragged(int x, int y, int pointer) {
    if (clickedOnMap) {
      if (Math.max(Math.abs(cursorX - clickX),
          Math.abs(cursorY - clickY)) > 5) {
        dragging= true;
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
        world.setCursorScreenPos(world.getViewport().getCamera().unproject(
            new Vector3(x, y, 0),
            world.getViewport().getScreenX(), world.getViewport().getScreenY(),
            world.getViewport().getScreenWidth(), world.getViewport().getScreenHeight()
        ));
        if (currentMenuItem[0] >= 0 && currentMenuItem[0] < 5) {
            VariantProperties variant = buildingVariants.get(currentMenuTab[0])[currentMenuItem[0]];
            world.setSelectedBuilding(BuildingFactory.createBuilding(variant, world.currentGridPosition()));
        }
        return true;
    }

    public boolean scrolled (float amountX, float amountY) {
        world.getCamera().vZoom += 0.001f * amountY;
        return true;
    }
}
