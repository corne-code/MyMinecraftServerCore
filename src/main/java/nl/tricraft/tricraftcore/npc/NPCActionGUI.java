package nl.tricraft.tricraftcore.npc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class NPCActionGUI {

    public static final String TITLE =
            ChatColor.DARK_AQUA + "NPC Actie kiezen";

    public void open(
            Player player,
            boolean leftClick
    ) {

        Inventory inventory = Bukkit.createInventory(
                null,
                27,
                TITLE
        );

        inventory.setItem(
                10,
                createItem(
                        Material.BARRIER,
                        ChatColor.GRAY + "Geen actie",
                        ChatColor.GRAY + "Doe niets."
                )
        );

        inventory.setItem(
                11,
                createItem(
                        Material.COMMAND_BLOCK,
                        ChatColor.YELLOW + "Command",
                        ChatColor.GRAY + "Voer een command uit."
                )
        );

        inventory.setItem(
                12,
                createItem(
                        Material.PAPER,
                        ChatColor.GREEN + "Bericht",
                        ChatColor.GRAY + "Laat een bericht zien."
                )
        );

        inventory.setItem(
                13,
                createItem(
                        Material.ENDER_PEARL,
                        ChatColor.AQUA + "Teleport",
                        ChatColor.GRAY + "Teleport naar een locatie."
                )
        );

        inventory.setItem(
                14,
                createItem(
                        Material.EMERALD,
                        ChatColor.GOLD + "Shop",
                        ChatColor.GRAY + "Open een shop."
                )
        );

        inventory.setItem(
                15,
                createItem(
                        Material.DIAMOND_SWORD,
                        ChatColor.RED + "PvP",
                        ChatColor.GRAY + "Open het PvP-menu."
                )
        );

        inventory.setItem(
                16,
                createItem(
                        Material.GRASS_BLOCK,
                        ChatColor.GREEN + "Skyblock",
                        ChatColor.GRAY + "Open Skyblock."
                )
        );

        player.openInventory(inventory);
    }

    private ItemStack createItem(
            Material material,
            String name,
            String... lore
    ) {

        ItemStack item =
                new ItemStack(material);

        ItemMeta meta =
                item.getItemMeta();

        if (meta != null) {

            meta.setDisplayName(name);

            meta.setLore(
                    Arrays.asList(lore)
            );

            item.setItemMeta(meta);
        }

        return item;
    }
}
