package com.nova.client.util;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

/**
 * Small collection of drawing helpers used by the ClickGUI so its style
 * doesn't look like the default vanilla / generic hacked-client GUI.
 */
public final class RenderUtil {

    private RenderUtil() {
    }

    /** Flat-color rectangle, thin wrapper kept here so GUI code reads cleanly. */
    public static void drawRect(int left, int top, int right, int bottom, int color) {
        Gui.drawRect(left, top, right, bottom, color);
    }

    /** Simple 1px outline around a rectangle. */
    public static void drawOutline(int left, int top, int right, int bottom, int color) {
        Gui.drawRect(left, top, right, top + 1, color);
        Gui.drawRect(left, bottom - 1, right, bottom, color);
        Gui.drawRect(left, top, left + 1, bottom, color);
        Gui.drawRect(right - 1, top, right, bottom, color);
    }

    /** Vertical two-color gradient rectangle (used for panel headers / accents). */
    public static void drawVerticalGradient(int left, int top, int right, int bottom, int colorTop, int colorBottom) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);

        float aTop = (colorTop >> 24 & 255) / 255.0F;
        float rTop = (colorTop >> 16 & 255) / 255.0F;
        float gTop = (colorTop >> 8 & 255) / 255.0F;
        float bTop = (colorTop & 255) / 255.0F;

        float aBot = (colorBottom >> 24 & 255) / 255.0F;
        float rBot = (colorBottom >> 16 & 255) / 255.0F;
        float gBot = (colorBottom >> 8 & 255) / 255.0F;
        float bBot = (colorBottom & 255) / 255.0F;

        net.minecraft.client.renderer.Tessellator tessellator = net.minecraft.client.renderer.Tessellator.getInstance();
        net.minecraft.client.renderer.WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(GL11.GL_QUADS, net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(right, top, 0.0D).color(rTop, gTop, bTop, aTop).endVertex();
        worldrenderer.pos(left, top, 0.0D).color(rTop, gTop, bTop, aTop).endVertex();
        worldrenderer.pos(left, bottom, 0.0D).color(rBot, gBot, bBot, aBot).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).color(rBot, gBot, bBot, aBot).endVertex();
        tessellator.draw();

        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
}
