package com.github.idimabr.listeners;

import com.github.idimabr.manager.ActionManager;
import com.github.idimabr.model.ActionData;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

@RequiredArgsConstructor
public class BlockActionListener implements Listener {

    private final ActionManager manager;
    private Random random = new Random();
    @EventHandler
    public void onBreak(BlockBreakEvent e){
        final Block block = e.getBlock();
        if(!manager.validWorld(block.getWorld().getName())) return;

        final Player player = e.getPlayer();
        final ItemStack item = player.getItemInHand();
        if(item != null && item.getType().name().contains("SWORD")) return;
        if(!manager.has(block.getType().name())) return;

        final ActionData data = manager.get(block.getType().name());
        if(random.nextInt(100) > data.getChance()) return;
        if(data.isFarm() && !sameAge(block, data.getAge())) return;

        for (String command : data.getCommands()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("{player}", player.getName()));
        }
    }

    public boolean sameAge(Block block, int age){
        if(!(block.getBlockData() instanceof Ageable)) return false;
        return ((Ageable) block.getBlockData()).getAge() == age;
    }
}
