package com.nova.client.module.impl;

import com.nova.client.module.Category;
import com.nova.client.module.Module;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * Scans nearby loaded blocks for valuable ores and draws a wireframe box
 * around each one, visible through terrain. Rescans once a second instead
 * of every tick to keep it cheap.
 *
 * Note: on most survival multiplayer servers, using this to grief the
 * economy or find other players' bases can get you banned even without
 * anti-cheat catching it directly (admins can see the pattern in logs) —
 * this is meant for singleplayer or servers where it's allowed.
 */
public class ModuleXRay extends Module {

    private static final int RADIUS = 16;
    private static final int RESCAN_INTERVAL_TICKS = 20;

    private final List<BlockPos> oreList = new ArrayList<BlockPos>();
    private int tickCounter;

    public ModuleXRay() {
        super("X-Ray", Category.RENDER);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onEnable() {
        oreList.clear();
        tickCounter = RESCAN_INTERVAL_TICKS; // scan immediately on enable
    }

    @Override
    public void onDisable() {
        oreList.clear();
    }

    @Override
    public void onUpdate() {
        tickCounter++;
        if (tickCounter < RESCAN_INTERVAL_TICKS) {
            return;
        }
        tickCounter = 0;
        scan();
    }

    private void scan() {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.thePlayer;
        World world = mc.theWorld;
        if (player == null || world == null) {
            return;
        }

        List<BlockPos> found = new ArrayList<BlockPos>();
        BlockPos center = new BlockPos(player);
        BlockPos from = center.add(-RADIUS, -RADIUS, -RADIUS);
        BlockPos to = center.add(RADIUS, RADIUS, RADIUS);

        for (BlockPos pos : BlockPos.getAllInBox(from, to)) {
            Block block = world.getBlockState(pos).getBlock();
            if (isValuable(block)) {
                found.add(pos.toImmutable());
            }
        }

        oreList.clear();
        oreList.addAll(found);
    }

    private boolean isValuable(Block block) {
        return block == Blocks.diamond_ore
                || block == Blocks.emerald_ore
                || block == Blocks.gold_ore
                || block == Blocks.iron_ore
                || block == Blocks.redstone_ore
                || block == Blocks.lit_redstone_ore
                || block == Blocks.lapis_ore;
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (!isEnabled() || oreList.isEmpty()) {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.thePlayer;
        if (player == null) {
            return;
        }

        double rx = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.partialTicks;
        double ry = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.partialTicks;
        double rz = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.partialTicks;

        GlStateManager.pushMatrix();
        GlStateManager.translate(-rx, -ry, -rz);
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glLineWidth(2.0F);

        for (BlockPos pos : oreList) {
            AxisAlignedBB box = new AxisAlignedBB(
                    pos.getX(), pos.getY(), pos.getZ(),
                    pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
            RenderGlobal.drawSelectionBoundingBox(box, 1.0F, 0.85F, 0.2F, 0.8F);
        }

        GL11.glLineWidth(1.0F);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }
}
