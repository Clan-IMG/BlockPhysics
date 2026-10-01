package net.clanimg.blockPhysics;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.Set;

public final class PhysicsListener implements Listener {

    private static final Set<Material> BLOCKED_GRAVITY_BLOCKS = Set.of(
            Material.SAND,
            Material.RED_SAND,
            Material.GRAVEL,
            Material.ANVIL,
            Material.CHIPPED_ANVIL,
            Material.DAMAGED_ANVIL,
            Material.WHITE_CONCRETE_POWDER,
            Material.ORANGE_CONCRETE_POWDER,
            Material.MAGENTA_CONCRETE_POWDER,
            Material.LIGHT_BLUE_CONCRETE_POWDER,
            Material.YELLOW_CONCRETE_POWDER,
            Material.LIME_CONCRETE_POWDER,
            Material.PINK_CONCRETE_POWDER,
            Material.GRAY_CONCRETE_POWDER,
            Material.LIGHT_GRAY_CONCRETE_POWDER,
            Material.CYAN_CONCRETE_POWDER,
            Material.PURPLE_CONCRETE_POWDER,
            Material.BLUE_CONCRETE_POWDER,
            Material.BROWN_CONCRETE_POWDER,
            Material.GREEN_CONCRETE_POWDER,
            Material.RED_CONCRETE_POWDER,
            Material.BLACK_CONCRETE_POWDER
    );

    private final BlockPhysics plugin;

    public PhysicsListener(BlockPhysics plugin) {
        this.plugin = plugin;
    }

    // BlockPhysicsEvent: unterbindet physikalisches Fallen, wenn in der Welt deaktiviert
    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent event) {
        Block block = event.getBlock();
        if (plugin.isPhysicsEnabled(block.getWorld())) {
            return;
        }

        if (BLOCKED_GRAVITY_BLOCKS.contains(block.getType())) {
            Block below = block.getRelative(0, -1, 0);
            if (below.isEmpty() || below.isPassable()) {
                event.setCancelled(true);
            }
        }
    }

    // Fallback: falls doch ein FallingBlock spawnt, in einer Welt mit deaktivierter Physik
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof FallingBlock fb)) {
            return;
        }
        if (plugin.isPhysicsEnabled(fb.getWorld())) {
            return;
        }

        BlockData data = fb.getBlockData();
        Block block = fb.getLocation().getBlock();

        fb.remove();

        // Nur ersetzen, wenn der Block nicht mehr vorhanden ist
        if (block.getType().isAir()) {
            block.setBlockData(data, false);
        }
    }
}
