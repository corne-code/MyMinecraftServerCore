package nl.tricraft.tricraftcore.npc;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class NPCActionListener implements Listener {

    private final NPCManager npcManager;
    private final NPCEditorManager editorManager;
    private final NPCActionGUI actionGUI;

    public NPCActionListener(
            NPCManager npcManager,
            NPCEditorManager editorManager,
            NPCActionGUI actionGUI
    ) {
        this.npcManager = npcManager;
        this.editorManager = editorManager;
        this.actionGUI = actionGUI;
    }

    @EventHandler
    public void onActionClick(
            InventoryClickEvent event
    ) {

        if (!event.getView().getTitle()
                .equals(NPCActionGUI.TITLE)) {
            return;
        }

        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        ItemStack item =
                event.getCurrentItem();

        if (item == null
                || item.getType() == Material.AIR) {
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
                            + "Je bent geen NPC aan het bewerken."
            );

            return;
        }

        boolean leftClick =
                editorManager.isEditingLeftClick(player);

        NPCActionType actionType;

        switch (item.getType()) {

            case BARRIER:
                actionType = NPCActionType.NONE;
                break;

            case COMMAND_BLOCK:
                actionType = NPCActionType.COMMAND;
                break;

            case PAPER:
                actionType = NPCActionType.MESSAGE;
                break;

            case ENDER_PEARL:
                actionType = NPCActionType.TELEPORT;
                break;

            case EMERALD:
                actionType = NPCActionType.SHOP;
                break;

            case DIAMOND_SWORD:
                actionType = NPCActionType.PVP;
                break;

            case GRASS_BLOCK:
                actionType = NPCActionType.SKYBLOCK;
                break;

            default:
                return;
        }

        NPCAction action =
                new NPCAction(
                        actionType,
                        ""
                );

        if (leftClick) {

            npc.setLeftClickAction(action);

        } else {

            npc.setRightClickAction(action);
        }

        player.closeInventory();

        player.sendMessage(
                ChatColor.GREEN
                        + "Actie ingesteld: "
                        + ChatColor.YELLOW
                        + actionType.name()
        );

        /*
         * Voor COMMAND, MESSAGE en TELEPORT
         * vragen we hierna nog om extra informatie.
         */
    }
}
