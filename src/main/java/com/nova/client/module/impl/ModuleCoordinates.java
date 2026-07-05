package com.nova.client.module.impl;

import com.nova.client.module.Category;
import com.nova.client.module.HudModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

/** Draws the player's X/Y/Z position. Drag it in the HUD editor to move it. */
public class ModuleCoordinates extends HudModule {

    public ModuleCoordinates() {
        super("Coordinates", Category.HUD);
    }

    @Override
    protected void setDefaultPosition(ScaledResolution sr) {
        setPosition(4, 4);
    }

    @Override
    public int getBoxWidth() {
        return 90;
    }

    @Override
    public void onRender() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null) {
            return;
        }
        ensureDefaultPosition(new ScaledResolution(mc));

        String text = String.format("XYZ: %.1f / %.1f / %.1f",
                mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
        mc.fontRendererObj.drawStringWithShadow(text, getX(), getY(), 0xFFFFFFFF);
    }
}
