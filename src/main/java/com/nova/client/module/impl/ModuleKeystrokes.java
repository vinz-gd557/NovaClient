package com.nova.client.module.impl;

import com.nova.client.module.Category;
import com.nova.client.module.HudModule;
import com.nova.client.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

/** WASD indicator that lights up active movement keys. Draggable. */
public class ModuleKeystrokes extends HudModule {

    private static final int SIZE = 18;

    public ModuleKeystrokes() {
        super("Keystrokes", Category.HUD);
    }

    @Override
    protected void setDefaultPosition(ScaledResolution sr) {
        setPosition(sr.getScaledWidth() - getBoxWidth() - 14, sr.getScaledHeight() - getBoxHeight() - 14);
    }

    @Override
    public int getBoxWidth() {
        return SIZE * 3;
    }

    @Override
    public int getBoxHeight() {
        return SIZE * 2;
    }

    @Override
    public void onRender() {
        Minecraft mc = Minecraft.getMinecraft();
        ensureDefaultPosition(new ScaledResolution(mc));

        int baseX = getX();
        int baseY = getY();

        drawKey(mc, "W", baseX + SIZE, baseY, mc.gameSettings.keyBindForward.getKeyCode());
        drawKey(mc, "A", baseX, baseY + SIZE, mc.gameSettings.keyBindLeft.getKeyCode());
        drawKey(mc, "S", baseX + SIZE, baseY + SIZE, mc.gameSettings.keyBindBack.getKeyCode());
        drawKey(mc, "D", baseX + SIZE * 2, baseY + SIZE, mc.gameSettings.keyBindRight.getKeyCode());
    }

    private void drawKey(Minecraft mc, String label, int x, int y, int keyCode) {
        boolean active = keyCode > 0 && Keyboard.isKeyDown(keyCode);
        int color = active ? 0xAA00E5FF : 0x77111111;
        RenderUtil.drawRect(x, y, x + SIZE - 1, y + SIZE - 1, color);
        int textX = x + SIZE / 2 - mc.fontRendererObj.getStringWidth(label) / 2;
        mc.fontRendererObj.drawStringWithShadow(label, textX, y + SIZE / 2 - 4, 0xFFFFFFFF);
    }
}
