package net.clanimg.blockPhysics;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public final class PhysicCommand implements CommandExecutor, TabCompleter {

    private static final Component PREFIX = Component.text("BlockPhysics » ", NamedTextColor.GRAY);

    private final BlockPhysics plugin;

    public PhysicCommand(BlockPhysics plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(PREFIX.append(Component.text("Dieser Befehl kann nur von einem Spieler ausgeführt werden.", NamedTextColor.RED)));
            return true;
        }

        World world = player.getWorld();

        if (args.length != 1) {
            boolean enabled = plugin.isPhysicsEnabled(world);
            player.sendMessage(PREFIX.append(Component.text(
                    "Physik ist in dieser Welt aktuell " + (enabled ? "an" : "aus") + ". Nutzung: /physic <on|off>",
                    NamedTextColor.YELLOW)));
            return true;
        }

        if (args[0].equalsIgnoreCase("on")) {
            plugin.setPhysicsEnabled(world, true);
            player.sendMessage(PREFIX.append(Component.text("Physik ist in dieser Welt jetzt an.", NamedTextColor.GREEN)));
        } else if (args[0].equalsIgnoreCase("off")) {
            plugin.setPhysicsEnabled(world, false);
            player.sendMessage(PREFIX.append(Component.text("Physik ist in dieser Welt jetzt aus.", NamedTextColor.GREEN)));
        } else {
            player.sendMessage(PREFIX.append(Component.text("Nutzung: /physic <on|off>", NamedTextColor.RED)));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return List.of("on", "off");
        }
        return List.of();
    }
}
