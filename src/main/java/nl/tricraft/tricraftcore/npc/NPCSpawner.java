package nl.tricraft.tricraftcore.npc;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class NPCSpawner {

    private final JavaPlugin plugin;
    private final NPCManager npcManager;

    private final NamespacedKey npcKey;

    public NPCSpawner(
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

    public void spawnAll() {

        for (NPCData npc : npcManager.getNPCs()) {
            spawnNPC(npc);
        }
    }

    public void spawnNPC(NPCData npc) {

        Location location =
                npc.getLocation();

        if (location.getWorld() == null) {
            return;
        }

        // Verwijder een bestaande NPC op dezelfde locatie
        for (Entity entity :
                location.getWorld().getNearbyEntities(
                        location,
                        1.0,
                        2.0,
                        1.0
                )) {

            String existing =
                    entity.getPersistentDataContainer()
                            .get(
                                    npcKey,
                                    PersistentDataType.STRING
                            );

            if (existing != null
                    && existing.equals(npc.getId())) {

                entity.remove();
            }
        }

        Villager villager =
                (Villager) location.getWorld().spawnEntity(
                        location,
                        EntityType.VILLAGER
                );

        villager.setAI(false);
        villager.setInvulnerable(true);
        villager.setSilent(true);
        villager.setCollidable(false);

        villager.setCustomName(
                ChatColor.YELLOW
                        + npc.getName()
        );

        villager.setCustomNameVisible(true);

        villager.getPersistentDataContainer()
                .set(
                        npcKey,
                        PersistentDataType.STRING,
                        npc.getId()
                );
    }
}
