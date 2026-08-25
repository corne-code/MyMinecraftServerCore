package nl.tricraft.tricraftcore.npc;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class NPCStorage {

    private final JavaPlugin plugin;
    private final NPCManager npcManager;

    private final File file;

    public NPCStorage(
            JavaPlugin plugin,
            NPCManager npcManager
    ) {
        this.plugin = plugin;
        this.npcManager = npcManager;

        file = new File(
                plugin.getDataFolder(),
                "npcs.yml"
        );
    }

    public void save() {

        YamlConfiguration config =
                new YamlConfiguration();

        for (NPCData npc : npcManager.getNPCs()) {

            String path =
                    "npcs." + npc.getId();

            Location location =
                    npc.getLocation();

            if (location.getWorld() == null) {
                continue;
            }

            config.set(
                    path + ".name",
                    npc.getName()
            );

            config.set(
                    path + ".type",
                    npc.getType().name()
            );

            config.set(
                    path + ".world",
                    location.getWorld().getName()
            );

            config.set(
                    path + ".x",
                    location.getX()
            );

            config.set(
                    path + ".y",
                    location.getY()
            );

            config.set(
                    path + ".z",
                    location.getZ()
            );

            config.set(
                    path + ".yaw",
                    location.getYaw()
            );

            config.set(
                    path + ".pitch",
                    location.getPitch()
            );
        }

        try {

            config.save(file);

        } catch (IOException e) {

            plugin.getLogger().severe(
                    "NPC's konden niet worden opgeslagen!"
            );

            e.printStackTrace();
        }
    }

    public void load() {

        if (!file.exists()) {
            return;
        }

        YamlConfiguration config =
                YamlConfiguration.loadConfiguration(file);

        ConfigurationSection section =
                config.getConfigurationSection("npcs");

        if (section == null) {
            return;
        }

        for (String id : section.getKeys(false)) {

            String path =
                    "npcs." + id;

            String name =
                    config.getString(
                            path + ".name"
                    );

            String typeName =
                    config.getString(
                            path + ".type"
                    );

            String worldName =
                    config.getString(
                            path + ".world"
                    );

            if (name == null
                    || typeName == null
                    || worldName == null) {

                continue;
            }

            World world =
                    plugin.getServer()
                            .getWorld(worldName);

            if (world == null) {

                plugin.getLogger().warning(
                        "Wereld '" + worldName
                                + "' voor NPC '"
                                + name
                                + "' bestaat niet."
                );

                continue;
            }

            double x =
                    config.getDouble(
                            path + ".x"
                    );

            double y =
                    config.getDouble(
                            path + ".y"
                    );

            double z =
                    config.getDouble(
                            path + ".z"
                    );

            float yaw =
                    (float) config.getDouble(
                            path + ".yaw"
                    );

            float pitch =
                    (float) config.getDouble(
                            path + ".pitch"
                    );

            Location location =
                    new Location(
                            world,
                            x,
                            y,
                            z,
                            yaw,
                            pitch
                    );

            NPCType type;

            try {

                type = NPCType.valueOf(
                        typeName.toUpperCase()
                );

            } catch (IllegalArgumentException e) {

                plugin.getLogger().warning(
                        "Ongeldig NPC-type voor "
                                + name
                );

                continue;
            }

            NPCData npc =
                    new NPCData(
                            id,
                            name,
                            type,
                            location
                    );

            npcManager.addNPC(npc);
        }
    }
}
