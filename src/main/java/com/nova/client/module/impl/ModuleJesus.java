package com.nova.client.module.impl;

import com.nova.client.module.Category;
import com.nova.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

/** Keeps the player at the water's surface instead of sinking. Hold sneak to dive normally. */
public class ModuleJesus extends Module {

    public ModuleJesus() {
        super("Jesus", Category.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player == null) {
            return;
        }
        if (player.isInWater() && !player.isSneaking() && player.motionY < 0) {
            player.motionY = 0.0D;
        }
    }
}
