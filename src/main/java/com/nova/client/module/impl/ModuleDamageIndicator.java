package com.nova.client.module.impl;

import com.nova.client.module.Category;
import com.nova.client.module.Module;
import com.nova.client.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;

/** Flashes a red border around the screen edges while the hurt animation is playing. */
public class ModuleDamageIndicator extends Module {

    public ModuleDamageIndicator() {
        super("Damage Indicator", Category.COMBAT);
    }

    @Override
    public void onRender() {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.thePlayer;
        if (player == null || player.hurtTime <= 0) {
            return;
        }

        ScaledResolution sr = new ScaledResolution(mc);
        int w = sr.getScaledWidth();
        int h = sr.getScaledHeight();
        int color = 0x66FF0000;

        RenderUtil.drawRect(0, 0, w, 4, color);
        RenderUtil.drawRect(0, h - 4, w, h, color);
        RenderUtil.drawRect(0, 0, 4, h, color);
        RenderUtil.drawRect(w - 4, 0, w, h, color);
    }
}
