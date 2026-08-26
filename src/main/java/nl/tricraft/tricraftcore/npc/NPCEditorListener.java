package nl.tricraft.tricraftcore.npc;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class NPCEditorListener implements Listener {

    private final NPCManager npcManager;
    private final NPCEditorManager editorManager;
    private final NPCEditorGUI editorGUI;
    private final NPCActionGUI actionGUI;

    public NPCEditorListener(
            NPCManager npcManager,
            NPCEditorManager editorManager,
            NPCEditorGUI editorGUI,
            NPCActionGUI actionGUI
    ) {
        this.npcManager = npcManager;
        this.editorManager = editorManager;
        this.editorGUI = editorGUI;
        this.actionGUI = actionGUI;
    }

    @EventHandler
    public void onInventoryClick(
            InventoryClickEvent event
    ) {

        if (!event.getView().getTitle()
                .equals(NPCEditorGUI.TITLE)) {
            return;
        }

        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        NPCData npc =
                editorManager.getEditingNPC(
                        player,
                        npcManager
                );

        if (npc == null) {
            player.closeInventory();

            player.sendMessage(
                    ChatColor.RED
                            + "Je bewerkt momenteel geen NPC."
            );

            return;
        }

        int slot = event.getRawSlot();

        switch (slot) {

            case 10:
                // Linkermuisklik
                editorManager.setEditingLeftClick(
                        player,
                        true
                );

                actionGUI.open(
                        player,
                        true
                );
                break;

            case 12:
                // Rechtermuisklik
                editorManager.setEditingLeftClick(
                        player,
                        false
                );

                actionGUI.open(
                        player,
                        false
                );
                break;

            case 14:
                player.closeInventory();

                player.sendMessage(
                        ChatColor.YELLOW
                                + "Gebruik later de naam-editor."
                );
                break;

            case 16:

                npc.setLocation(
                        player.getLocation().clone()
                );

                player.sendMessage(
                        ChatColor.GREEN
                                + "NPC verplaatst naar jouw locatie."
                );

                player.closeInventory();
                break;

            case 22:

                npcManager.removeNPC(
                        npc.getName()
                );

                player.closeInventory();

                player.sendMessage(
                        ChatColor.RED
                                + "NPC verwijderd."
                );
                break;

            default:
                break;
        }
    }
}
