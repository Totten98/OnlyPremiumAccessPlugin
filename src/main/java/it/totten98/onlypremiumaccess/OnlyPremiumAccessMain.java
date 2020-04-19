package it.totten98.onlypremiumaccess;

import org.bukkit.plugin.java.JavaPlugin;

public class OnlyPremiumAccessMain extends JavaPlugin {

    public static OnlyPremiumAccessMain plugin;

    @Override
    public void onEnable() {
        super.onEnable();

        getServer().getPluginManager().registerEvents(new WarListener(this), this);

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
