package com.nova.client;

import com.nova.client.handler.GuiHandler;
import com.nova.client.handler.KeyInputHandler;
import com.nova.client.module.ModuleManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.lwjgl.input.Keyboard;

/**
 * NovaClient - a custom utility client for Minecraft 1.8.9.
 * Press RIGHT SHIFT in-game to open the mod menu (ClickGUI).
 */
@Mod(modid = NovaClient.MODID, name = NovaClient.NAME, version = NovaClient.VERSION, clientSideOnly = true)
public class NovaClient {

    public static final String MODID = "novaclient";
    public static final String NAME = "NovaClient";
    public static final String VERSION = "1.0.0";

    /** Keybinding used to toggle the mod menu. Default: Right Shift. */
    public static KeyBinding menuKey;

    /** Holds and updates every module registered in the client. */
    public static ModuleManager moduleManager;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        menuKey = new KeyBinding("NovaClient Menu", Keyboard.KEY_RSHIFT, "NovaClient");
        ClientRegistry.registerKeyBinding(menuKey);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        moduleManager = new ModuleManager();

        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
        MinecraftForge.EVENT_BUS.register(new GuiHandler());
        MinecraftForge.EVENT_BUS.register(moduleManager);
    }
}
