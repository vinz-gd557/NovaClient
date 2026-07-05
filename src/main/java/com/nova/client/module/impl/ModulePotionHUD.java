package com.nova.client.module.impl;

import com.nova.client.module.Category;
import com.nova.client.module.HudModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;

/** Lists currently active potion effects and their amplifier. Draggable. */
public class ModulePotionHUD extends HudModule {

    public ModulePotionHUD() {
        super("Potion Effects", Category.HUD);
    }

    @Override
    protected void setDefaultPosition(ScaledResolution sr) {
        setPosition(4, 90);
    }

    @Override
    public int getBoxWidth() {
        return 120;
    }

    @Override
    public void onRender() {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.thePlayer;
        if (player == null) {
            return;
        }
        ensureDefaultPosition(new ScaledResolution(mc));

        int y = getY();
        for (Object obj : player.getActivePotionEffects()) {
            PotionEffect effect = (PotionEffect) obj;
            String name = StatCollector.translateToLocal(effect.getEffectName());
            String line = name + " " + (effect.getAmplifier() + 1);
            mc.fontRendererObj.drawStringWithShadow(line, getX(), y, 0xFFAAFFAA);
            y += 10;
        }
    }
}
