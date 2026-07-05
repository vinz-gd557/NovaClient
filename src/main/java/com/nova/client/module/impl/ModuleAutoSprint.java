package com.nova.client.module.impl;

import com.nova.client.module.Category;
import com.nova.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

/** Keeps the player sprinting automatically while moving forward. */
public class ModuleAutoSprint extends Module {

    public ModuleAutoSprint() {
        super("AutoSprint", Category.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player == null) {
            return;
        }
        if (player.moveForward > 0.0F && player.getFoodStats().getFoodLevel() > 6 && !player.isSprinting()) {
            player.setSprinting(true);
        }
    }
}
