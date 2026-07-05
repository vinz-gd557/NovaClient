package com.nova.client.module.impl;

import com.nova.client.module.Category;
import com.nova.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

/** Cancels fall damage by resetting fall distance before it's applied. */
public class ModuleNoFall extends Module {

    public ModuleNoFall() {
        super("NoFall", Category.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player == null) {
            return;
        }
        if (player.fallDistance > 2.0F) {
            player.fallDistance = 0.0F;
        }
    }
}
