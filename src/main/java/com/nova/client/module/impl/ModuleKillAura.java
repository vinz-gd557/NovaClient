package com.nova.client.module.impl;

import com.nova.client.module.Category;
import com.nova.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Automatically attacks the closest living entity within range.
 * Combat cheats like this are usually against the rules on organized
 * multiplayer servers and get caught by anti-cheat — best for singleplayer.
 */
public class ModuleKillAura extends Module {

    private static final double RANGE = 4.0D;
    private static final int COOLDOWN_TICKS = 10;

    private int cooldown;

    public ModuleKillAura() {
        super("KillAura", Category.COMBAT);
    }

    @Override
    public void onDisable() {
        cooldown = 0;
    }

    @Override
    public void onUpdate() {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.thePlayer;
        if (player == null || mc.theWorld == null) {
            return;
        }

        if (cooldown > 0) {
            cooldown--;
            return;
        }

        Entity target = findClosestTarget(mc, player);
        if (target != null) {
            mc.playerController.attackEntity(player, target);
            player.swingItem();
            cooldown = COOLDOWN_TICKS;
        }
    }

    private Entity findClosestTarget(Minecraft mc, EntityPlayer self) {
        Entity closest = null;
        double closestDistSq = RANGE * RANGE;

        for (Object obj : mc.theWorld.loadedEntityList) {
            if (!(obj instanceof EntityLivingBase) || obj == self) {
                continue;
            }
            EntityLivingBase entity = (EntityLivingBase) obj;
            if (entity.isDead || entity.getHealth() <= 0.0F) {
                continue;
            }
            if (entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.disableDamage) {
                continue;
            }

            double distSq = self.getDistanceSqToEntity(entity);
            if (distSq < closestDistSq) {
                closestDistSq = distSq;
                closest = entity;
            }
        }
        return closest;
    }
}
