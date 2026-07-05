package com.nova.client.module;

import net.minecraft.client.gui.ScaledResolution;

/**
 * Base class for on-screen HUD elements that the player can reposition by
 * dragging them around in {@link com.nova.client.gui.GuiHudEditor}.
 *
 * Position is stored in "scaled GUI" pixel coordinates (the same space used
 * by {@link ScaledResolution}) so it stays correct regardless of GUI scale.
 */
public abstract class HudModule extends Module {

    private static final int UNSET = Integer.MIN_VALUE;

    private int x = UNSET;
    private int y = UNSET;

    protected HudModule(String name, Category category) {
        super(name, category);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Approximate footprint used for drag hit-testing and the edit-mode preview box. */
    public int getBoxWidth() {
        return 70;
    }

    public int getBoxHeight() {
        return 10;
    }

    /** Subclasses pick a sensible starting spot the first time they ever render. */
    protected abstract void setDefaultPosition(ScaledResolution sr);

    /** Call at the top of onRender() (and from the HUD editor) before reading getX()/getY(). */
    public void ensureDefaultPosition(ScaledResolution sr) {
        if (x == UNSET || y == UNSET) {
            setDefaultPosition(sr);
        }
    }
}
