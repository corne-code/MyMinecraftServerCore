package nl.tricraft.tricraftcore.npc;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class NPCEditorListener implements Listener {

    private final NPCManager npcManager;
    private final NPCEditorGUI editorGUI;

    public NPCEditorListener(
            NPCManager npcManager,
            NPCEditorGUI editorGUI
    ) {
        this.npcManager = npcManager;
        this.editorGUI = editorGUI;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if (!event.getView().getTitle()
                .equals(NPCEditorGUI.TITLE)) {
            return;
        }

        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        ItemStack clicked =
                event.getCurrentItem();

        if (clicked == null
                || clicked.getType() == Material.AIR) {
            return;
        }

        /*
         * We moeten nog weten welke NPC de speler
         * aan het bewerken is.
         *
         * Dat gaan we in de volgende stap toevoegen.
         */
        player.sendMessage(
                ChatColor.YELLOW
                        + "NPC-editor actief."
        );
    }
}
