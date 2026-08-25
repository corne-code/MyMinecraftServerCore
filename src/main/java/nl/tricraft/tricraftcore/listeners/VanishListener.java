package nl.tricraft.tricraftcore.listeners;

import nl.tricraft.tricraftcore.commands.VanishCommand;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Player;

public class VanishListener implements Listener {

    private final VanishCommand vanishCommand;
    private final Plugin plugin;

    public VanishListener(
            Plugin plugin,
            VanishCommand vanishCommand
    ) {
        this.plugin = plugin;
        this.vanishCommand = vanishCommand;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player joiningPlayer = event.getPlayer();

        for (Player online : Bukkit.getOnlinePlayers()) {

            if (!vanishCommand.isVanished(online)) {
                continue;
            }

            if (joiningPlayer.equals(online)) {
                continue;
            }

            if (!joiningPlayer.hasPermission(
                    "tricraft.vanish.see"
            )) {

                joiningPlayer.hidePlayer(
                        plugin,
                        online
                );
            }
        }
    }
}
