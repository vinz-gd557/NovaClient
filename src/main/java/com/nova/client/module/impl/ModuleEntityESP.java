package com.nova.client.module.impl;

import com.nova.client.module.Category;
import com.nova.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

/**
 * Draws a wireframe box around nearby living entities, visible through
 * walls — players in red, mobs in green.
 */
public class ModuleEntityESP extends Module {

    public ModuleEntityESP() {
        super("Entity ESP", Category.RENDER);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (!isEnabled()) {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer self = mc.thePlayer;
        if (self == null || mc.theWorld == null) {
            return;
        }

        double rx = self.lastTickPosX + (self.posX - self.lastTickPosX) * event.partialTicks;
        double ry = self.lastTickPosY + (self.posY - self.lastTickPosY) * event.partialTicks;
        double rz = self.lastTickPosZ + (self.posZ - self.lastTickPosZ) * event.partialTicks;

        GlStateManager.pushMatrix();
        GlStateManager.translate(-rx, -ry, -rz);
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glLineWidth(1.5F);

        for (Object obj : mc.theWorld.loadedEntityList) {
            if (!(obj instanceof EntityLivingBase) || obj == self) {
                continue;
            }
            EntityLivingBase entity = (EntityLivingBase) obj;
            if (entity.isDead) {
                continue;
            }

            boolean isPlayer = entity instanceof EntityPlayer;
            float r = isPlayer ? 1.0F : 0.2F;
            float g = isPlayer ? 0.2F : 1.0F;
            float b = 0.2F;

            double ex = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * event.partialTicks;
            double ey = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * event.partialTicks;
            double ez = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * event.partialTicks;
            float halfWidth = entity.width / 2.0F;
            float height = entity.height;

            AxisAlignedBB box = new AxisAlignedBB(
                    ex - halfWidth, ey, ez - halfWidth,
                    ex + halfWidth, ey + height, ez + halfWidth);
            RenderGlobal.drawSelectionBoundingBox(box, r, g, b, 0.9F);
        }

        GL11.glLineWidth(1.0F);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }
}
