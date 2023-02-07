package com.github.idimabr.listeners;

import com.github.idimabr.manager.ActionManager;
import com.github.idimabr.model.ActionData;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Random;

@RequiredArgsConstructor
public class MobActionListener implements Listener {

    private final ActionManager manager;
    private Random random = new Random();

    @EventHandler
    public void onDeathMob(EntityDeathEvent e){
        final LivingEntity entity = e.getEntity();
        if(!manager.validWorld(entity.getWorld().getName())) return;
        final Player player = entity.getKiller();
        if(player == null) return;
        if(!manager.has(entity.getType().name())) return;

        final ActionData data = manager.get(entity.getType().name());
        if(random.nextInt(100) > data.getChance()) return;

        for (String command : data.getCommands()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("{player}", player.getName()));
        }
    }
}
