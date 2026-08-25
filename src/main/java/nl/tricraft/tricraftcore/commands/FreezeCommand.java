package nl.tricraft.tricraftcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FreezeCommand implements CommandExecutor {

    private final Set<UUID> frozenPlayers = new HashSet<>();

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {

        if (!sender.hasPermission("tricraft.freeze")) {
            sender.sendMessage(
                    ChatColor.RED
                            + "Je hebt geen toestemming om spelers te freezen."
            );
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(
                    ChatColor.RED
                            + "Gebruik: /freeze <speler>"
            );
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null) {
            sender.sendMessage(
                    ChatColor.RED
                            + "Deze speler is niet online."
            );
            return true;
        }

        UUID uuid = target.getUniqueId();

        if (frozenPlayers.contains(uuid)) {

            frozenPlayers.remove(uuid);

            target.sendMessage(
                    ChatColor.GREEN
                            + "Je bent niet meer gefreezed."
            );

            sender.sendMessage(
                    ChatColor.GREEN
                            + target.getName()
                            + " is niet meer gefreezed."
            );

        } else {

            frozenPlayers.add(uuid);

            target.sendMessage(
                    ChatColor.RED
                            + "Je bent gefreezed door een moderator."
            );

            sender.sendMessage(
                    ChatColor.GREEN
                            + target.getName()
                            + " is gefreezed."
            );
        }

        return true;
    }

    public boolean isFrozen(Player player) {
        return frozenPlayers.contains(
                player.getUniqueId()
        );
    }
}
