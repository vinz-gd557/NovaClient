package com.nova.client.handler;

import com.nova.client.NovaClient;
import com.nova.client.gui.ClickGUI;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

/**
 * Listens for the menu keybind (default: Right Shift) and opens the
 * ClickGUI whenever it is pressed while no other screen is open.
 */
public class KeyInputHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.thePlayer == null) {
            return;
        }

        if (mc.currentScreen == null && NovaClient.menuKey.isPressed()) {
            mc.displayGuiScreen(new ClickGUI());
        }
    }
}
