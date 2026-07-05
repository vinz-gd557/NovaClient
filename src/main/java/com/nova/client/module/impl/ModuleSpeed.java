package com.nova.client.module.impl;

import com.nova.client.module.Category;
import com.nova.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

/** Boosts horizontal movement speed while on the ground. */
public class ModuleSpeed extends Module {

    private static final double BOOST = 0.06D;

    public ModuleSpeed() {
        super("Speed", Category.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player == null || !player.onGround) {
            return;
        }

        float forward = player.moveForward;
        float strafe = player.moveStrafing;
        if (forward == 0.0F && strafe == 0.0F) {
            return;
        }

        double yaw = Math.toRadians(player.rotationYaw);
        double sin = Math.sin(yaw);
        double cos = Math.cos(yaw);

        double moveX = strafe * cos - forward * sin;
        double moveZ = forward * cos + strafe * sin;
        double mag = Math.sqrt(moveX * moveX + moveZ * moveZ);
        if (mag == 0) {
            return;
        }

        player.motionX += (moveX / mag) * BOOST;
        player.motionZ += (moveZ / mag) * BOOST;
    }
}
