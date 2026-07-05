package com.nova.client.module.impl;

import com.nova.client.module.Category;
import com.nova.client.module.HudModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

/** Draws the current FPS counter. Drag it in the HUD editor to move it. */
public class ModuleFPS extends HudModule {

    public ModuleFPS() {
        super("FPS Display", Category.HUD);
    }

    @Override
    protected void setDefaultPosition(ScaledResolution sr) {
        setPosition(4, 16);
    }

    @Override
    public int getBoxWidth() {
        return 60;
    }

    @Override
    public void onRender() {
        Minecraft mc = Minecraft.getMinecraft();
        ensureDefaultPosition(new ScaledResolution(mc));

        String text = "FPS: " + Minecraft.getDebugFPS();
        mc.fontRendererObj.drawStringWithShadow(text, getX(), getY(), 0xFFFFFF55);
    }
}
