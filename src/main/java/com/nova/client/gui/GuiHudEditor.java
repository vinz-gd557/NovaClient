package com.nova.client.gui;

import com.nova.client.NovaClient;
import com.nova.client.module.HudModule;
import com.nova.client.module.Module;
import com.nova.client.util.RenderUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

/**
 * Lets the player click-and-drag every HUD element (Keystrokes, Coordinates,
 * FPS Display, etc.) into any position on screen. Opened via the "Edit HUD"
 * button inside the ClickGUI. Positions apply immediately and stick for the
 * rest of the session even for elements that are currently disabled, so you
 * can lay everything out before turning it on.
 */
public class GuiHudEditor extends GuiScreen {

    private HudModule dragging;
    private int dragOffsetX;
    private int dragOffsetY;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(fontRendererObj, "Drag elements to move them - Esc or Right Shift to finish", width / 2, 8, 0xFFFFFFFF);

        ScaledResolution sr = new ScaledResolution(mc);
        for (Module module : NovaClient.moduleManager.getModules()) {
            if (!(module instanceof HudModule)) {
                continue;
            }
            HudModule hud = (HudModule) module;
            hud.ensureDefaultPosition(sr);

            int w = hud.getBoxWidth();
            int h = hud.getBoxHeight();
            boolean hovered = isHovering(mouseX, mouseY, hud.getX(), hud.getY(), w, h);
            int fill = hud == dragging ? 0x662F6FED : (hovered ? 0x4400E5FF : 0x2200E5FF);

            RenderUtil.drawRect(hud.getX(), hud.getY(), hud.getX() + w, hud.getY() + h, fill);
            RenderUtil.drawOutline(hud.getX(), hud.getY(), hud.getX() + w, hud.getY() + h, 0xFF00E5FF);
            drawString(fontRendererObj, hud.getName(), hud.getX() + 2, hud.getY() + 2, 0xFFFFFFFF);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton != 0) {
            return;
        }

        for (Module module : NovaClient.moduleManager.getModules()) {
            if (!(module instanceof HudModule)) {
                continue;
            }
            HudModule hud = (HudModule) module;
            if (isHovering(mouseX, mouseY, hud.getX(), hud.getY(), hud.getBoxWidth(), hud.getBoxHeight())) {
                dragging = hud;
                dragOffsetX = mouseX - hud.getX();
                dragOffsetY = mouseY - hud.getY();
                break;
            }
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (dragging != null) {
            dragging.setPosition(mouseX - dragOffsetX, mouseY - dragOffsetY);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        dragging = null;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE || keyCode == NovaClient.menuKey.getKeyCode()) {
            mc.displayGuiScreen(null);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private boolean isHovering(int mouseX, int mouseY, int x, int y, int w, int h) {
        return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
    }
}
