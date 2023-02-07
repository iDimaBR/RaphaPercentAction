package com.github.idimabr;

import com.github.idimabr.commands.ReloadCommand;
import com.github.idimabr.listeners.BlockActionListener;
import com.github.idimabr.listeners.MobActionListener;
import com.github.idimabr.manager.ActionManager;
import com.github.idimabr.util.ConfigUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class RaphaPercentAction extends JavaPlugin {

    private ConfigUtil config;
    private ActionManager manager;

    @Override
    public void onLoad() {
        config = new ConfigUtil(this, "config.yml");
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        manager = new ActionManager(config);
        manager.loadCache();

        final PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new BlockActionListener(manager), this);
        pm.registerEvents(new MobActionListener(manager), this);

        getCommand("percentaction").setExecutor(new ReloadCommand(config, manager));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
