package nl.tricraft.tricraftcore.shop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopGUI {

    private final ShopManager shopManager;

    public ShopGUI(ShopManager shopManager) {
        this.shopManager = shopManager;
    }

    public void open(Player player) {

        Inventory inventory = Bukkit.createInventory(
                null,
                54,
                ChatColor.DARK_GREEN + "Tricraft Shop"
        );

        int slot = 0;

        for (ShopItem shopItem : shopManager.getItems().values()) {

            if (slot >= 54) {
                break;
            }

            ItemStack item = new ItemStack(
                    shopItem.getMaterial(),
                    shopItem.getAmount()
            );

            ItemMeta meta = item.getItemMeta();

            if (meta != null) {

                meta.setDisplayName(
                        ChatColor.YELLOW
                                + shopItem.getMaterial().name()
                );

                meta.setLore(java.util.List.of(
                        ChatColor.GREEN
                                + "Kopen: $"
                                + shopItem.getBuyPrice(),

                        ChatColor.RED
                                + "Verkopen: $"
                                + shopItem.getSellPrice(),

                        "",
                        ChatColor.GRAY
                                + "Klik om te kopen"
                ));

                item.setItemMeta(meta);
            }

            inventory.setItem(slot, item);

            slot++;
        }

        player.openInventory(inventory);
    }
}
