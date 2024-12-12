package y111studios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
public class BuildingMenu {
  private final Main game;
  private final Texture menuBackground;
  private @Getter MenuTab currentMenuTab;
  private @Getter int currentMenuItem;
  private @Getter Texture[] buildingTextures;
  private @Getter Map<MenuTab, VariantProperties[]> buildingVariants;
  private @Getter Viewport viewport;
  private @Getter boolean flipped;
  private final Table buildingTable;
  private final Table hotkeyTable;
  private final Table tabTable;
  private final Table tabLabelTable;
  private final Image[] unselectedTabImages = new Image[5];
  private final Image[] selectedTabImages = new Image[5];
  private final Image[] buildingImages = new Image[35];
  public static final Skin SKIN = new Skin(
      Gdx.files.internal("assets/skins/default/uiskin.json"));

  /**
   * Sets up the camera and loads the background.
   *
   * @param game Reference to game manager
   */
  @SuppressWarnings("unchecked")
public BuildingMenu(final Main game, Stage stage) {
    this.game = game;
    viewport = stage.getViewport();
    menuBackground = game.getAsset(AssetPaths.MENU_BACKGROUND);
    currentMenuTab = MenuTab.ACCOMMODATION;
    currentMenuItem = -1;
    buildingTextures = new Texture[] {
        game.getAsset(AssetPaths.ACC1), game.getAsset(AssetPaths.ACC2),
        game.getAsset(AssetPaths.ACC3), game.getAsset(AssetPaths.ACC4),
        game.getAsset(AssetPaths.ACC5), game.getAsset(AssetPaths.ROTATE),
        game.getAsset(AssetPaths.TRASH),
        game.getAsset(AssetPaths.CATER1), game.getAsset(AssetPaths.CATER2),
        game.getAsset(AssetPaths.CATER3), game.getAsset(AssetPaths.CATER4),
        game.getAsset(AssetPaths.CATER5), game.getAsset(AssetPaths.ROTATE),
        game.getAsset(AssetPaths.TRASH),
        game.getAsset(AssetPaths.TEACH1), game.getAsset(AssetPaths.TEACH2),
        game.getAsset(AssetPaths.TEACH3), game.getAsset(AssetPaths.TEACH4),
        game.getAsset(AssetPaths.TEACH5), game.getAsset(AssetPaths.ROTATE),
        game.getAsset(AssetPaths.TRASH),
        game.getAsset(AssetPaths.REC1), game.getAsset(AssetPaths.REC2),
        game.getAsset(AssetPaths.TREE1), game.getAsset(AssetPaths.TREE2),
        game.getAsset(AssetPaths.TREE3), game.getAsset(AssetPaths.ROTATE),
        game.getAsset(AssetPaths.TRASH),
        game.getAsset(AssetPaths.BIKE_SHED), game.getAsset(AssetPaths.STRAIGHT_ROAD),
        game.getAsset(AssetPaths.ROAD_CROSS), game.getAsset(AssetPaths.ROAD_BEND1),
        game.getAsset(AssetPaths.ROAD_BEND2), game.getAsset(AssetPaths.ROTATE),
        game.getAsset(AssetPaths.TRASH)
    };
    buildingVariants = new HashMap<>();
    buildingVariants.put(ACCOMMODATION, AccommodationVariant.values());
    buildingVariants.put(CATERING, CateringVariant.values());
    buildingVariants.put(TEACHING, TeachingVariant.values());

    VariantProperties[] jointTabVariants = new VariantProperties[5];
    System.arraycopy(RecreationVariant.values(), 0, jointTabVariants, 0, 2);
    System.arraycopy(MiscellaneousVariant.values(), 0, jointTabVariants, 2, 3);
    buildingVariants.put(RECREATION, jointTabVariants);

    jointTabVariants = new VariantProperties[5];
    System.arraycopy(MiscellaneousVariant.values(), 3, jointTabVariants, 0, 5);
    buildingVariants.put(MISCELLANEOUS, jointTabVariants);

    tabTable = new Table();
    for (int i = 0; i < 5; i++) {
      flipped = false;
      unselectedTabImages[i] = new Image(game.getAsset(AssetPaths.MENU_UNSELECTED_TAB));
      selectedTabImages[i] = new Image(game.getAsset(AssetPaths.MENU_SELECTED_TAB));
      final int tab = i;
      tabTable.add(i == 0 ? selectedTabImages[0] : unselectedTabImages[i]);
    }

    buildingTable = new Table();
    for (int i = 0; i < 35; i++) {
      buildingImages[i] = new Image(buildingTextures[i]);
      final int buildingIndex = i % 7;
      buildingImages[i].addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent e, float x, float y) {
          if (buildingIndex == 5) {
            flipBuildings();
          } else if (buildingIndex == currentMenuItem) {
            setCurrentMenuItem(-1);
          } else {
            setCurrentMenuItem(buildingIndex);
          }
        }
      });
    }
    for (int i = 0; i < 7; i++) {
      buildingTable.add(buildingImages[i]);
    }

    tabLabelTable = new Table();
    tabLabelTable.add(new Label("     Accommodation", SKIN));
    tabLabelTable.add(new Label("           Catering", SKIN));
    tabLabelTable.add(new Label("           Teaching", SKIN));
    tabLabelTable.add(new Label("   Recreation & Trees", SKIN));
    tabLabelTable.add(new Label("       Miscelaneous", SKIN));
    int i = 0;
    for (Cell<Actor> cell : tabLabelTable.getCells()) {
      final int tab = i;
      cell.getActor().addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent e, float x, float y) {
          if (currentMenuTab.toInt() != tab) {
            setCurrentMenuItem(-1);
            updateTab(tab);
          }
        }
      });
      i++;
    }

    hotkeyTable = new Table();
    for (i = 1; i < 6; i++) {
      hotkeyTable.add(new Label("         " + Integer.toString(i), SKIN));
    }
    hotkeyTable.add(new Label("         R", SKIN));
    hotkeyTable.add(new Label("         D", SKIN));

    stage.addActor(tabTable);
    stage.addActor(tabLabelTable);
    stage.addActor(buildingTable);
    stage.addActor(hotkeyTable);
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
    if (currentMenuItem >= 0 && currentMenuItem < 6) {
      game.font.draw(game.spritebatch, setCurrentMenuItem(currentMenuItem),
        menuHeight * 0.03f, menuHeight + game.font.getLineHeight());
    }

    game.spritebatch.end();
  }

  /**
   * Handles resizing of the game window.
   *
   * @param width  The new width of the window.
   * @param height The new height of the window.
   */
  public void resize(int width, int height) {
    buildingTable.setBounds(0, height * 0.01f, width, height * 0.11f);
    hotkeyTable.setBounds(0, 0, width, height * 0.03f);
    tabTable.setBounds(0, height * 0.08f, width, height * 0.1015f);
    tabLabelTable.setBounds(0, height * 0.08f, width, height * 0.1015f);
    updateCellSizes();
    updateBuildingRotations();
  }

  /**
   * Updates the images in both tables depending on the selected tab.
   */
  @SuppressWarnings("unchecked")
  private void updateTab(int tabNumber) {
    // Update currentMenuTab
    switch (tabNumber) {
      case 0:
        currentMenuTab = ACCOMMODATION;
        break;
      case 1:
        currentMenuTab = CATERING;
        break;
      case 2:
        currentMenuTab = TEACHING;
        break;
      case 3:
        currentMenuTab = RECREATION;
        break;
      case 4:
        currentMenuTab = MISCELLANEOUS;
        break;
      default:
        throw new UnreachableException("Unreachable state - menu tab not recognised");
    }

    // Update the tabs to show the correct tab selected
    // performs an unsafe type conversion from Cell to Cell<Actor>.
    int i = 0;
    for (Cell<Actor> cell : tabTable.getCells()) {
      cell.setActor(i == tabNumber ? selectedTabImages[i] : unselectedTabImages[i]);
      i++;
    }

    // Update the buildings depending on the tab
    i = 0;
    for (Cell<Actor> cell : buildingTable.getCells()) {
      cell.setActor(buildingImages[7 * tabNumber + i]);
      i++;
    }

    updateSelectedBuildingHighlight();
    updateCellSizes();
    flipped = false;
    updateBuildingRotations();
  }

  /**
   * Updates the sizes of the cells in all tables to be correct relative to the
   * screen size
   * and the sizes of the images in the cells.
   */
  @SuppressWarnings("unchecked")
  private void updateCellSizes() {
    int cellNum = 0;
    for (Cell<Actor> cell : buildingTable.getCells()) {
      Image buildingImage = (Image) (cell.getActor());
      Vector2 textureSize = new Vector2(buildingImage.getWidth(), buildingImage.getHeight());
      cell.width(
          viewport.getScreenHeight() * 0.1f
              * (textureSize.x < textureSize.y ? textureSize.x / textureSize.y : 1))
          .height(
              viewport.getScreenHeight() * 0.1f
                  * (textureSize.y < textureSize.x ? textureSize.y / textureSize.x : 1))
          .pad(viewport.getScreenHeight() * 0.01f);
      int i = 0;
      for (Cell<Actor> labelCell : hotkeyTable.getCells()) {
        if (i == cellNum) {
          labelCell.width(
            viewport.getScreenHeight() * 0.1f
                * (textureSize.x < textureSize.y ? textureSize.x / textureSize.y : 1))
            .height(viewport.getScreenHeight() * 0.035f).pad(viewport.getScreenHeight() * 0.01f);
          ((Label)labelCell.getActor()).setFontScale(viewport.getScreenHeight() * 0.001f);
        }
        i++;
      }
      cellNum++;
    }
    for (Cell<Actor> cell : tabTable.getCells()) {
      cell.width(
          viewport.getScreenHeight() * 0.025f * 6.667f).height(viewport.getScreenHeight() * 0.025f);
    }
    for (Cell<Actor> cell : tabLabelTable.getCells()) {
      cell.width(
          viewport.getScreenHeight() * 0.025f * 6.667f).height(viewport.getScreenHeight() * 0.025f);
      ((Label)cell.getActor()).setFontScale(viewport.getScreenHeight() * 0.00105f);
    }
  }

  /**
   * Updates the opacity of the building Images to be correct according to the
   * currentMenuItem.
   */
  @SuppressWarnings("unchecked")
  public void updateSelectedBuildingHighlight() {
    int i = 0;
    for (Cell<Actor> cell : buildingTable.getCells()) {
      Image buildingImage = (Image) (cell.getActor());
      buildingImage.setColor(1f, 1f, 1f, i == currentMenuItem ? 0.2f : 1f);
      i++;
    }
  }

  /**
   * updates highlighting of currently selected menu item, returns said menu item's name to be rendered
   *
   * @param itemNum reference to current menu item
   * @return Returns the name of the currently selected menu item
   */
  public String setCurrentMenuItem(int itemNum) {
    currentMenuItem = itemNum;
    updateSelectedBuildingHighlight();

    if (itemNum >= 0 && itemNum < 5) {
      VariantProperties selectedVariant = buildingVariants.get(currentMenuTab)[currentMenuItem];
      return selectedVariant.getName();
    } else if (itemNum == 6) {
      return "item delete";
    } else{
      return "None selected";
    }
  }

  @SuppressWarnings("unchecked")
  private void updateBuildingRotations() {
    for (Cell<Actor> cell : buildingTable.getCells()) {
      Image buildingImage = (Image) (cell.getActor());
      buildingImage.setOrigin(buildingImage.getWidth() / 2, buildingImage.getHeight() / 2);
      buildingImage.setScaleX(flipped ? -1f : 1f); // Horizontal flip
      if (buildingImage == buildingImages[32]) {
        buildingImage.setScaleY(flipped ? -1f : 1f); // Vertical flip
      }

    }
  }

  public void flipBuildings() {
    flipped = !flipped;
    updateBuildingRotations();
  }
}
