package nl.tricraft.tricraftcore.shop;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class ShopManager {

    private final Map<Material, ShopItem> items = new HashMap<>();

    public ShopManager() {
        addItem(Material.DIAMOND, 1, 500, 250);
        addItem(Material.EMERALD, 1, 300, 150);
        addItem(Material.GOLD_INGOT, 1, 100, 50);
        addItem(Material.IRON_INGOT, 1, 50, 25);
        addItem(Material.COAL, 1, 20, 10);
        addItem(Material.OAK_LOG, 16, 100, 50);
        addItem(Material.COBBLESTONE, 64, 50, 25);
        addItem(Material.BREAD, 16, 75, 35);
    }

    public void addItem(
            Material material,
            int amount,
            double buyPrice,
            double sellPrice
    ) {
        items.put(
                material,
                new ShopItem(
                        material,
                        amount,
                        buyPrice,
                        sellPrice
                )
        );
    }

    public ShopItem getItem(Material material) {
        return items.get(material);
    }

    public Map<Material, ShopItem> getItems() {
        return items;
    }
}
