package com.nova.client.module.impl;

import com.nova.client.module.Category;
import com.nova.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

/** Maxes out gamma while enabled so dark areas become visible. */
public class ModuleFullbright extends Module {

    private float previousGamma;

    public ModuleFullbright() {
        super("Fullbright", Category.RENDER);
    }

    @Override
    public void onEnable() {
        GameSettings settings = Minecraft.getMinecraft().gameSettings;
        previousGamma = settings.gammaSetting;
        settings.gammaSetting = 1000.0F;
    }

    @Override
    public void onDisable() {
        Minecraft.getMinecraft().gameSettings.gammaSetting = previousGamma;
    }
}
