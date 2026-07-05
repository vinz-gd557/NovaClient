package com.nova.client.module.impl;

import com.nova.client.module.Category;
import com.nova.client.module.HudModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;

/** Lists each equipped armor piece and its remaining durability. Draggable. */
public class ModuleArmorHUD extends HudModule {

    public ModuleArmorHUD() {
        super("Armor HUD", Category.HUD);
    }

    @Override
    protected void setDefaultPosition(ScaledResolution sr) {
        setPosition(4, 28);
    }

    @Override
    public int getBoxWidth() {
        return 110;
    }

    @Override
    public int getBoxHeight() {
        return 40;
    }

    @Override
    public void onRender() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null) {
            return;
        }
        ensureDefaultPosition(new ScaledResolution(mc));

        int y = getY();
        for (ItemStack stack : mc.thePlayer.inventory.armorInventory) {
            if (stack == null) {
                continue;
            }
            int maxDamage = stack.getMaxDamage();
            int remaining = maxDamage - stack.getItemDamage();
            String text = stack.getDisplayName() + " - " + remaining + "/" + maxDamage;
            mc.fontRendererObj.drawStringWithShadow(text, getX(), y, 0xFF55FF88);
            y += 10;
        }
    }
}
