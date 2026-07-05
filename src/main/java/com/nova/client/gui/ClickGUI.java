package com.nova.client.gui;

import com.nova.client.NovaClient;
import com.nova.client.module.Category;
import com.nova.client.module.Module;
import com.nova.client.util.RenderUtil;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * NovaClient's mod menu. Opened with Right Shift, closed with Right Shift
 * or Escape. Single centered box: category tabs on the left, modules for
 * the selected category listed on the right — not the "column of panels"
 * look most hacked clients use.
 */
public class ClickGUI extends GuiScreen {

    private static final int BOX_WIDTH = 280;
    private static final int BOX_HEIGHT = 220;
    private static final int HEADER_HEIGHT = 22;
    private static final int TAB_WIDTH = 90;
    private static final int ROW_HEIGHT = 18;

    private static final int ACCENT_TOP = 0xFF2F6FED;
    private static final int ACCENT_BOTTOM = 0xFF00E5FF;
    private static final int BOX_BG = 0xEE0D0D16;
    private static final int TAB_PANEL_BG = 0x33000000;
    private static final int CONTENT_BG = 0x1AFFFFFF;
    private static final int ROW_HOVER = 0x33FFFFFF;
    private static final int ROW_ENABLED = 0xAA2F6FED;

    private Category selected = Category.values()[0];

    private int boxLeft, boxTop, boxRight, boxBottom;
    private int tabX, tabY, tabHeight;
    private int contentX, contentY, contentWidth;
    private int editHudButtonX, editHudButtonY, editHudButtonWidth, editHudButtonHeight;

    @Override
    public void initGui() {
        boxLeft = width / 2 - BOX_WIDTH / 2;
        boxTop = height / 2 - BOX_HEIGHT / 2;
        boxRight = boxLeft + BOX_WIDTH;
        boxBottom = boxTop + BOX_HEIGHT;

        tabX = boxLeft;
        tabY = boxTop + HEADER_HEIGHT;
        tabHeight = BOX_HEIGHT - HEADER_HEIGHT;

        contentX = tabX + TAB_WIDTH;
        contentY = tabY;
        contentWidth = BOX_WIDTH - TAB_WIDTH;

        editHudButtonWidth = 56;
        editHudButtonHeight = 14;
        editHudButtonX = boxRight - editHudButtonWidth - 4;
        editHudButtonY = boxTop + (HEADER_HEIGHT - editHudButtonHeight) / 2;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        // Outer box + header
        RenderUtil.drawRect(boxLeft, boxTop, boxRight, boxBottom, BOX_BG);
        RenderUtil.drawVerticalGradient(boxLeft, boxTop, boxRight, boxTop + HEADER_HEIGHT, ACCENT_TOP, ACCENT_BOTTOM);
        drawString(fontRendererObj, "NovaClient", boxLeft + 8, boxTop + 7, 0xFFFFFFFF);
        RenderUtil.drawOutline(boxLeft, boxTop, boxRight, boxBottom, 0xFF2F6FED);

        // "Edit HUD" button in the header
        boolean editHovered = isHovering(mouseX, mouseY, editHudButtonX, editHudButtonY, editHudButtonWidth, editHudButtonHeight);
        RenderUtil.drawRect(editHudButtonX, editHudButtonY, editHudButtonX + editHudButtonWidth, editHudButtonY + editHudButtonHeight,
                editHovered ? 0xAA000000 : 0x66000000);
        drawCenteredString(fontRendererObj, "Edit HUD", editHudButtonX + editHudButtonWidth / 2, editHudButtonY + 3, 0xFFFFFFFF);

        // Left tab column
        RenderUtil.drawRect(tabX, tabY, tabX + TAB_WIDTH, tabY + tabHeight, TAB_PANEL_BG);
        int ty = tabY;
        for (Category category : Category.values()) {
            boolean isSelected = category == selected;
            boolean hovered = isHovering(mouseX, mouseY, tabX, ty, TAB_WIDTH, ROW_HEIGHT);

            if (isSelected) {
                RenderUtil.drawRect(tabX, ty, tabX + TAB_WIDTH, ty + ROW_HEIGHT, ROW_ENABLED);
            } else if (hovered) {
                RenderUtil.drawRect(tabX, ty, tabX + TAB_WIDTH, ty + ROW_HEIGHT, ROW_HOVER);
            }
            drawString(fontRendererObj, category.name(), tabX + 6, ty + 5, isSelected ? 0xFFFFFFFF : 0xFFCCCCCC);
            ty += ROW_HEIGHT;
        }

        // Right content column: modules for the selected category
        RenderUtil.drawRect(contentX, contentY, contentX + contentWidth, contentY + tabHeight, CONTENT_BG);
        List<Module> modules = modulesFor(selected);
        int my = contentY + 2;
        if (modules.isEmpty()) {
            drawString(fontRendererObj, "No modules here yet.", contentX + 6, my + 4, 0xFFAAAAAA);
        }
        for (Module module : modules) {
            boolean hovered = isHovering(mouseX, mouseY, contentX, my, contentWidth, ROW_HEIGHT);

            if (module.isEnabled()) {
                RenderUtil.drawRect(contentX, my, contentX + contentWidth, my + ROW_HEIGHT, ROW_ENABLED);
            } else if (hovered) {
                RenderUtil.drawRect(contentX, my, contentX + contentWidth, my + ROW_HEIGHT, ROW_HOVER);
            }

            int textColor = module.isEnabled() ? 0xFFFFFFFF : 0xFFE0E0E0;
            drawString(fontRendererObj, module.getName(), contentX + 6, my + 5, textColor);
            String state = module.isEnabled() ? "ON" : "OFF";
            drawString(fontRendererObj, state, contentX + contentWidth - fontRendererObj.getStringWidth(state) - 6, my + 5, textColor);

            my += ROW_HEIGHT;
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton != 0) {
            return;
        }

        if (isHovering(mouseX, mouseY, editHudButtonX, editHudButtonY, editHudButtonWidth, editHudButtonHeight)) {
            mc.displayGuiScreen(new GuiHudEditor());
            return;
        }

        int ty = tabY;
        for (Category category : Category.values()) {
            if (isHovering(mouseX, mouseY, tabX, ty, TAB_WIDTH, ROW_HEIGHT)) {
                selected = category;
                return;
            }
            ty += ROW_HEIGHT;
        }

        List<Module> modules = modulesFor(selected);
        int my = contentY + 2;
        for (Module module : modules) {
            if (isHovering(mouseX, mouseY, contentX, my, contentWidth, ROW_HEIGHT)) {
                module.toggle();
                return;
            }
            my += ROW_HEIGHT;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == NovaClient.menuKey.getKeyCode() || keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
            return;
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private List<Module> modulesFor(Category category) {
        List<Module> result = new ArrayList<Module>();
        for (Module module : NovaClient.moduleManager.getModules()) {
            if (module.getCategory() == category) {
                result.add(module);
            }
        }
        return result;
    }

    private boolean isHovering(int mouseX, int mouseY, int x, int y, int w, int h) {
        return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
    }
}
