package com.nova.client.gui;

import com.nova.client.util.RenderUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;

import java.io.IOException;

/**
 * Replaces the vanilla title screen with a Nova Client branded one:
 * blue-to-white gradient background instead of the panorama, plain buttons
 * for the same actions (Singleplayer / Multiplayer / Options / Quit).
 */
public class GuiNovaMainMenu extends GuiScreen {

    private static final int TOP_COLOR = 0xFF0A1A33;
    private static final int BOTTOM_COLOR = 0xFFE8F4FF;

    @Override
    public void initGui() {
        buttonList.clear();
        int buttonWidth = 200;
        int x = width / 2 - buttonWidth / 2;
        int y = height / 4 + 48;

        buttonList.add(new GuiButton(0, x, y, buttonWidth, 20, "Singleplayer"));
        buttonList.add(new GuiButton(1, x, y + 24, buttonWidth, 20, "Multiplayer"));
        buttonList.add(new GuiButton(2, x, y + 48, buttonWidth, 20, "Options"));
        buttonList.add(new GuiButton(3, x, y + 72, buttonWidth, 20, "Quit Game"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawVerticalGradient(0, 0, width, height, TOP_COLOR, BOTTOM_COLOR);

        String title = "Nova Client";
        String subtitle = "Minecraft 1.8.9  -  Press Right Shift in-game for the mod menu";

        drawCenteredString(fontRendererObj, title, width / 2, height / 4 - 20, 0xFFFFFFFF);
        drawCenteredString(fontRendererObj, subtitle, width / 2, height / 4 - 6, 0xFFBBD9FF);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                mc.displayGuiScreen(new GuiSelectWorld(this));
                break;
            case 1:
                mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            case 2:
                mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                break;
            case 3:
                mc.shutdown();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
