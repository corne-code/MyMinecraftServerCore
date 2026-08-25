package nl.tricraft.tricraftcore.shop;

import org.bukkit.Material;

public class ShopItem {

    private final Material material;
    private final int amount;
    private final double buyPrice;
    private final double sellPrice;

    public ShopItem(
            Material material,
            int amount,
            double buyPrice,
            double sellPrice
    ) {
        this.material = material;
        this.amount = amount;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public Material getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }
}
