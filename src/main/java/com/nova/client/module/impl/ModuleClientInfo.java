package com.nova.client.module.impl;

import com.nova.client.NovaClient;
import com.nova.client.module.Category;
import com.nova.client.module.HudModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

/** Small watermark showing the client name and current FPS. Draggable. */
public class ModuleClientInfo extends HudModule {

    public ModuleClientInfo() {
        super("Client Info", Category.HUD);
    }

    @Override
    protected void setDefaultPosition(ScaledResolution sr) {
        setPosition(sr.getScaledWidth() - getBoxWidth() - 4, 4);
    }

    @Override
    public int getBoxWidth() {
        return 90;
    }

    @Override
    public int getBoxHeight() {
        return 20;
    }

    @Override
    public void onRender() {
        Minecraft mc = Minecraft.getMinecraft();
        ensureDefaultPosition(new ScaledResolution(mc));

        String line1 = NovaClient.NAME + " " + NovaClient.VERSION;
        String line2 = "FPS: " + Minecraft.getDebugFPS();
        mc.fontRendererObj.drawStringWithShadow(line1, getX(), getY(), 0xFF00E5FF);
        mc.fontRendererObj.drawStringWithShadow(line2, getX(), getY() + 10, 0xFFFFFFFF);
    }
}
