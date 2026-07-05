package com.nova.client.module.impl;

import com.nova.client.module.Category;
import com.nova.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

/** Nudges the player's view slightly every few seconds to avoid AFK kicks. */
public class ModuleAntiAFK extends Module {

    private int tickCounter;

    public ModuleAntiAFK() {
        super("AntiAFK", Category.MISC);
    }

    @Override
    public void onEnable() {
        tickCounter = 0;
    }

    @Override
    public void onUpdate() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player == null) {
            return;
        }
        tickCounter++;
        if (tickCounter % 100 == 0) {
            player.rotationYaw += 1.0F;
        }
    }
}
