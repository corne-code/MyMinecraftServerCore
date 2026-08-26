package nl.tricraft.tricraftcore.npc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataType;

public class NPCLeftClickListener implements Listener {

    private final NPCManager npcManager;
    private final NamespacedKey npcKey;

    public NPCLeftClickListener(
            org.bukkit.plugin.java.JavaPlugin plugin,
            NPCManager npcManager
    ) {
        this.npcManager = npcManager;

        this.npcKey = new NamespacedKey(
                plugin,
                "tricraft_npc"
        );
    }

    @EventHandler
    public void onNPCLeftClick(
            EntityDamageByEntityEvent event
    ) {

        if (!(event.getDamager() instanceof Player player)) {
            return;
        }

        Entity entity =
                event.getEntity();

        String npcId =
                entity.getPersistentDataContainer()
                        .get(
                                npcKey,
                                PersistentDataType.STRING
                        );

        if (npcId == null) {
            return;
        }

        // Voorkom dat de NPC schade krijgt
        event.setCancelled(true);

        NPCData npc =
                npcManager.getNPCById(npcId);

        if (npc == null) {
            return;
        }

        NPCAction action =
                npc.getLeftClickAction();

        executeAction(
                player,
                action
        );
    }

    private void executeAction(
            Player player,
            NPCAction action
    ) {

        if (action == null) {
            return;
        }

        NPCActionType type =
                action.getType();

        String value =
                action.getValue();

        switch (type) {

            case NONE:
                break;

            case MESSAGE:

                player.sendMessage(
                        ChatColor.translateAlternateColorCodes(
                                '&',
                                value
                        )
                );

                break;

            case COMMAND:

                String command = value;

                if (command.startsWith("/")) {
                    command =
                            command.substring(1);
                }

                Bukkit.dispatchCommand(
                        player,
                        command
                );

                break;

            case TELEPORT:

                teleportPlayer(
                        player,
                        value
                );

                break;

            case SHOP:

                player.sendMessage(
                        ChatColor.GOLD
                                + "Shop wordt binnenkort geopend."
                );

                break;

            case PVP:

                player.sendMessage(
                        ChatColor.RED
                                + "PvP-menu wordt binnenkort geopend."
                );

                break;

            case SKYBLOCK:

                player.sendMessage(
                        ChatColor.GREEN
                                + "Skyblock wordt binnenkort geopend."
                );

                break;
        }
    }

    private void teleportPlayer(
            Player player,
            String value
    ) {

        try {

            String[] parts =
                    value.split(",");

            if (parts.length < 4) {
                return;
            }

            String worldName =
                    parts[0];

            double x =
                    Double.parseDouble(parts[1]);

            double y =
                    Double.parseDouble(parts[2]);

            double z =
                    Double.parseDouble(parts[3]);

            float yaw = 0;
            float pitch = 0;

            if (parts.length >= 6) {

                yaw =
                        Float.parseFloat(parts[4]);

                pitch =
                        Float.parseFloat(parts[5]);
            }

            World world =
                    Bukkit.getWorld(worldName);

            if (world == null) {

                player.sendMessage(
                        ChatColor.RED
                                + "De wereld bestaat niet."
                );

                return;
            }

            Location location =
                    new Location(
                            world,
                            x,
                            y,
                            z,
                            yaw,
                            pitch
                    );

            player.teleport(location);

        } catch (Exception e) {

            player.sendMessage(
                    ChatColor.RED
                            + "Ongeldige teleportlocatie."
            );
        }
    }
}
