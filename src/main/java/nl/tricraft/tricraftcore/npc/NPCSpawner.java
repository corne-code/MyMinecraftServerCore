package nl.tricraft.tricraftcore.npc;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
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

        // Zoek een bestaande NPC met dezelfde ID
        for (Entity entity :
                location.getWorld().getNearbyEntities(
                        location,
                        1.5,
                        2.5,
                        1.5
                )) {

            String existingId =
                    entity.getPersistentDataContainer()
                            .get(
                                    npcKey,
                                    PersistentDataType.STRING
                            );

            if (npc.getId().equals(existingId)) {
                entity.remove();
            }
        }

        Villager villager =
                (Villager) location.getWorld().spawnEntity(
                        location,
                        EntityType.VILLAGER
                );

        // NPC instellingen
        villager.setAI(false);
        villager.setInvulnerable(true);
        villager.setSilent(true);
        villager.setCollidable(false);
        villager.setCanPickupItems(false);

        // Geen normale villager trades
        villager.setProfession(
                Villager.Profession.NONE
        );

        villager.setCustomName(
                ChatColor.YELLOW
                        + npc.getName()
        );

        villager.setCustomNameVisible(true);

        // NPC ID opslaan op de entity
        villager.getPersistentDataContainer()
                .set(
                        npcKey,
                        PersistentDataType.STRING,
                        npc.getId()
                );
    }

    public void removeNPC(NPCData npc) {

        Location location =
                npc.getLocation();

        if (location.getWorld() == null) {
            return;
        }

        for (Entity entity :
                location.getWorld().getNearbyEntities(
                        location,
                        2.0,
                        3.0,
                        2.0
                )) {

            String existingId =
                    entity.getPersistentDataContainer()
                            .get(
                                    npcKey,
                                    PersistentDataType.STRING
                            );

            if (npc.getId().equals(existingId)) {
                entity.remove();
            }
        }
    }

    public void respawnNPC(NPCData npc) {

        removeNPC(npc);
        spawnNPC(npc);
    }
}
