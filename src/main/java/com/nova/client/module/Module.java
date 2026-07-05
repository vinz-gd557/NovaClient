package com.nova.client.module;

/**
 * Base class every feature/module of NovaClient must extend.
 * A module is a single toggleable feature shown as a button in the ClickGUI.
 */
public abstract class Module {

    private final String name;
    private final Category category;
    private boolean enabled;

    protected Module(String name, Category category) {
        this.name = name;
        this.category = category;
        this.enabled = false;
    }

    /** Flips the enabled state and fires the matching lifecycle callback. */
    public final void toggle() {
        enabled = !enabled;
        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    /** Called once, right when the module gets turned on. */
    public void onEnable() {
    }

    /** Called once, right when the module gets turned off. */
    public void onDisable() {
    }

    /** Called every client tick while the module is enabled. */
    public void onUpdate() {
    }

    /** Called every frame (overlay render pass) while the module is enabled. */
    public void onRender() {
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
