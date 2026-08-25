package nl.tricraft.tricraftcore.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InvseeListener implements Listener {

    private static final String PREFIX =
            ChatColor.DARK_AQUA + "Inventory: ";

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getView().getTitle().startsWith(PREFIX)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {

        if (event.getView().getTitle().startsWith(PREFIX)) {
            event.setCancelled(true);
        }
    }
}
