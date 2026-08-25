package nl.tricraft.tricraftcore.npc;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class NPCListener implements Listener {

    private final JavaPlugin plugin;
    private final NPCManager npcManager;

    private final NamespacedKey npcKey;

    public NPCListener(
            JavaPlugin plugin,
            NPCManager npcManager
    ) {
        this.plugin = plugin;
        this.npcManager = npcManager;

        this.npcKey = new NamespacedKey(
                plugin,
                "tricraft_npc"
        );
    }

    @EventHandler
    public void onNPCClick(
            PlayerInteractEntityEvent event
    ) {

        Player player = event.getPlayer();

        Entity entity = event.getRightClicked();

        String npcId =
                entity.getPersistentDataContainer()
                        .get(
                                npcKey,
                                PersistentDataType.STRING
                        );

        if (npcId == null) {
            return;
        }

        NPCData npc =
                npcManager.getNPCById(npcId);

        if (npc == null) {
            return;
        }

        event.setCancelled(true);

        switch (npc.getType()) {

            case SURVIVAL:

                player.sendMessage(
                        ChatColor.GREEN
                                + "Je gaat naar Survival."
                );

                break;

            case SKYBLOCK:

                player.sendMessage(
                        ChatColor.GREEN
                                + "Skyblock wordt geopend."
                );

                break;

            case PVP:

                player.sendMessage(
                        ChatColor.RED
                                + "PvP-menu wordt geopend."
                );

                break;

            case SHOP:

                player.sendMessage(
                        ChatColor.GOLD
                                + "Shop wordt geopend."
                );

                break;
        }
    }
}
