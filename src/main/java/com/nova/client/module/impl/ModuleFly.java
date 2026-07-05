package com.nova.client.module.impl;

import com.nova.client.module.Category;
import com.nova.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Enables creative-style flight in survival. Note: on most public servers
 * with anti-cheat this will get flagged/reverted almost instantly — best
 * used in singleplayer or on servers that explicitly allow it.
 */
public class ModuleFly extends Module {

    private boolean previousAllowFlying;
    private boolean previousFlying;

    public ModuleFly() {
        super("Fly", Category.MOVEMENT);
    }

    @Override
    public void onEnable() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player == null) {
            return;
        }
        previousAllowFlying = player.capabilities.allowFlying;
        previousFlying = player.capabilities.isFlying;
        player.capabilities.allowFlying = true;
        player.capabilities.isFlying = true;
    }

    @Override
    public void onDisable() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player == null) {
            return;
        }
        player.capabilities.allowFlying = previousAllowFlying;
        player.capabilities.isFlying = previousAllowFlying && previousFlying;
    }
}
