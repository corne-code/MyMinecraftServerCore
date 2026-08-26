package nl.tricraft.tricraftcore.npc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class NPCEditorGUI {

    public static final String TITLE =
            ChatColor.DARK_AQUA + "NPC Instellingen";

    private final NPCManager npcManager;

    public NPCEditorGUI(NPCManager npcManager) {
        this.npcManager = npcManager;
    }

    public void open(Player player, NPCData npc) {

        Inventory inventory = Bukkit.createInventory(
                null,
                27,
                TITLE
        );

        inventory.setItem(
                10,
                createItem(
                        Material.MOUSE,
                        ChatColor.YELLOW + "Linkermuisklik",
                        ChatColor.GRAY + "Stel in wat er gebeurt",
                        ChatColor.GRAY + "bij een linkermuisklik."
                )
        );

        inventory.setItem(
                12,
                createItem(
                        Material.MOUSE,
                        ChatColor.YELLOW + "Rechtermuisklik",
                        ChatColor.GRAY + "Stel in wat er gebeurt",
                        ChatColor.GRAY + "bij een rechtermuisklik."
                )
        );

        inventory.setItem(
                14,
                createItem(
                        Material.NAME_TAG,
                        ChatColor.GREEN + "Naam aanpassen",
                        ChatColor.GRAY + "NPC: "
                                + ChatColor.WHITE
                                + npc.getName()
                )
        );

        inventory.setItem(
                16,
                createItem(
                        Material.ENDER_PEARL,
                        ChatColor.AQUA + "NPC verplaatsen",
                        ChatColor.GRAY + "Verplaats de NPC",
                        ChatColor.GRAY + "naar jouw huidige locatie."
                )
        );

        inventory.setItem(
                22,
                createItem(
                        Material.BARRIER,
                        ChatColor.RED + "NPC verwijderen",
                        ChatColor.GRAY + "Verwijder deze NPC."
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
