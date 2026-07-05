package com.nova.client.handler;

import com.nova.client.gui.GuiNovaMainMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/** Swaps the vanilla title screen for {@link GuiNovaMainMenu} whenever it tries to open. */
public class GuiHandler {

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (event.gui != null && event.gui.getClass() == GuiMainMenu.class) {
            event.gui = new GuiNovaMainMenu();
        }
    }
}
