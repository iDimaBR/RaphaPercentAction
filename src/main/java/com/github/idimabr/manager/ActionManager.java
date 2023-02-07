package com.github.idimabr.manager;

import com.github.idimabr.RaphaPercentAction;
import com.github.idimabr.model.ActionData;
import com.github.idimabr.util.ConfigUtil;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ActionManager {

    private final ConfigUtil config;
    private Map<String, ActionData> cache = Maps.newConcurrentMap();
    private List<String> worlds = new ArrayList<>();

    public void put(String material, ActionData data){
        cache.put(material, data);
    }

    public ActionData get(String material){
        return cache.get(material);
    }

    public boolean has(String material){
        return cache.containsKey(material);
    }

    public boolean validWorld(String name){
        return worlds.contains(name);
    }

    public void loadCache() {
        cache.clear();
        worlds.addAll(config.getStringList("WorldList"));
        for (String key : config.getConfigurationSection("Blocks").getKeys(false)) {
            final ConfigurationSection section = config.getConfigurationSection("Blocks." + key);

            final String type = section.getString("type");
            if(type == null) continue;

            final Material material = Enum.valueOf(Material.class, type);
            final double chance = section.getDouble("chance");
            final List<String> commands = section.getStringList("commands");

            put(type.toUpperCase(), new ActionData(material, chance, commands, -1));
        }

        for (String key : config.getConfigurationSection("Farm").getKeys(false)) {
            final ConfigurationSection section = config.getConfigurationSection("Farm." + key);

            final String type = section.getString("type");
            if(type == null) continue;

            final Material material = Enum.valueOf(Material.class, type);
            final double chance = section.getDouble("chance");
            final List<String> commands = section.getStringList("commands");
            final int age = section.getInt("age", 0);

            put(type.toUpperCase(), new ActionData(material, chance, commands, age));
        }

        for (String key : config.getConfigurationSection("Mobs").getKeys(false)) {
            final ConfigurationSection section = config.getConfigurationSection("Mobs." + key);

            final String type = section.getString("type");
            if(type == null) continue;

            final EntityType entityType = Enum.valueOf(EntityType.class, type);
            final double chance = section.getDouble("chance");
            final List<String> commands = section.getStringList("commands");

            put(type.toUpperCase(), new ActionData(entityType, chance, commands, 0));
        }

        System.out.println("Foram carregados " + cache.size() + " ações no cache.");
    }
}
