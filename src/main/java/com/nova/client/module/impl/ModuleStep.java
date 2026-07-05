package com.nova.client.module.impl;

import com.nova.client.module.Category;
import com.nova.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

/** Lets the player walk up full-block-height ledges without jumping. */
public class ModuleStep extends Module {

    private float previousStepHeight;

    public ModuleStep() {
        super("Step", Category.MOVEMENT);
    }

    @Override
    public void onEnable() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player == null) {
            return;
        }
        previousStepHeight = player.stepHeight;
        player.stepHeight = 1.0F;
    }

    @Override
    public void onDisable() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player == null) {
            return;
        }
        player.stepHeight = previousStepHeight;
    }
}
