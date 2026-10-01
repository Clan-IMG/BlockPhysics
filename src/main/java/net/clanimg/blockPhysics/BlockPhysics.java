package net.clanimg.blockPhysics;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class BlockPhysics extends JavaPlugin {

    private final Map<String, Boolean> worldPhysicsEnabled = new HashMap<>();

    @Override
    public void onEnable() {
        getCommand("physic").setExecutor(new PhysicCommand(this));
        Bukkit.getPluginManager().registerEvents(new PhysicsListener(this), this);
    }

    @Override
    public void onDisable() {
        worldPhysicsEnabled.clear();
    }

    public boolean isPhysicsEnabled(World world) {
        return worldPhysicsEnabled.getOrDefault(world.getName(), true);
    }

    public void setPhysicsEnabled(World world, boolean enabled) {
        worldPhysicsEnabled.put(world.getName(), enabled);
    }
}
