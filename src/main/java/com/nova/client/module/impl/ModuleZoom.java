package com.nova.client.module.impl;

import com.nova.client.module.Category;
import com.nova.client.module.Module;
import net.minecraft.client.Minecraft;

/** Narrows the FOV while enabled to act as a simple zoom. */
public class ModuleZoom extends Module {

    private float previousFov;

    public ModuleZoom() {
        super("Zoom", Category.RENDER);
    }

    @Override
    public void onEnable() {
        previousFov = Minecraft.getMinecraft().gameSettings.fovSetting;
        Minecraft.getMinecraft().gameSettings.fovSetting = 30.0F;
    }

    @Override
    public void onDisable() {
        Minecraft.getMinecraft().gameSettings.fovSetting = previousFov;
    }
}
