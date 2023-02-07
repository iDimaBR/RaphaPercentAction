package com.github.idimabr.commands;

import com.github.idimabr.manager.ActionManager;
import com.github.idimabr.util.ConfigUtil;
import lombok.AllArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

@AllArgsConstructor
public class ReloadCommand implements CommandExecutor {

    private ConfigUtil config;
    private ActionManager manager;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("raphapercentaction.admin")){
            sender.sendMessage("§cSem permissão!");
            return false;
        }

        config.load();
        manager.loadCache();
        sender.sendMessage("§aPlugin reinicializado!");
        return true;
    }
}
