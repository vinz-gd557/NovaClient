package com.nova.client.module;

import com.nova.client.module.impl.ModuleAntiAFK;
import com.nova.client.module.impl.ModuleArmorHUD;
import com.nova.client.module.impl.ModuleAutoSprint;
import com.nova.client.module.impl.ModuleClientInfo;
import com.nova.client.module.impl.ModuleCoordinates;
import com.nova.client.module.impl.ModuleDamageIndicator;
import com.nova.client.module.impl.ModuleEntityESP;
import com.nova.client.module.impl.ModuleFPS;
import com.nova.client.module.impl.ModuleFly;
import com.nova.client.module.impl.ModuleFullbright;
import com.nova.client.module.impl.ModuleJesus;
import com.nova.client.module.impl.ModuleKeystrokes;
import com.nova.client.module.impl.ModuleKillAura;
import com.nova.client.module.impl.ModuleNoFall;
import com.nova.client.module.impl.ModulePotionHUD;
import com.nova.client.module.impl.ModuleSpeed;
import com.nova.client.module.impl.ModuleStep;
import com.nova.client.module.impl.ModuleXRay;
import com.nova.client.module.impl.ModuleZoom;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Registers every module and dispatches tick/render events to whichever ones
 * are currently enabled. Add new modules to the constructor to make them
 * show up in the ClickGUI automatically.
 *
 * Note: modules that render in the 3D world (X-Ray, Entity ESP) don't use
 * onRender() here — they hook RenderWorldLastEvent directly themselves,
 * since onRender()/RenderGameOverlayEvent is only for the flat 2D overlay.
 */
public class ModuleManager {

    private final List<Module> modules = new ArrayList<Module>();

    public ModuleManager() {
        // Combat
        modules.add(new ModuleKillAura());
        modules.add(new ModuleDamageIndicator());

        // Movement
        modules.add(new ModuleSpeed());
        modules.add(new ModuleFly());
        modules.add(new ModuleNoFall());
        modules.add(new ModuleStep());
        modules.add(new ModuleJesus());
        modules.add(new ModuleAutoSprint());

        // Render
        modules.add(new ModuleFullbright());
        modules.add(new ModuleZoom());
        modules.add(new ModuleXRay());
        modules.add(new ModuleEntityESP());

        // HUD (draggable)
        modules.add(new ModuleKeystrokes());
        modules.add(new ModuleCoordinates());
        modules.add(new ModuleFPS());
        modules.add(new ModuleArmorHUD());
        modules.add(new ModulePotionHUD());
        modules.add(new ModuleClientInfo());

        // Misc
        modules.add(new ModuleAntiAFK());

        // Add your own modules here, e.g.:
        // modules.add(new ModuleYourFeature());
    }

    public List<Module> getModules() {
        return modules;
    }

    public Module getModuleByName(String name) {
        for (Module module : modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        for (Module module : modules) {
            if (module.isEnabled()) {
                module.onUpdate();
            }
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        for (Module module : modules) {
            if (module.isEnabled()) {
                module.onRender();
            }
        }
    }
}
