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

    public NPCActionListener(
            NPCManager npcManager,
            NPCEditorManager editorManager
    ) {
        this.npcManager = npcManager;
        this.editorManager = editorManager;
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
                            + "Je bewerkt geen NPC."
            );

            return;
        }

        boolean leftClick =
                editorManager.isEditingLeftClick(
                        player
                );

        NPCActionType type;

        switch (item.getType()) {

            case BARRIER:
                type = NPCActionType.NONE;

                saveAction(
                        player,
                        npc,
                        type,
                        "",
                        leftClick
                );
                return;

            case COMMAND_BLOCK:
                type = NPCActionType.COMMAND;

                startInput(
                        player,
                        type
                );
                return;

            case PAPER:
                type = NPCActionType.MESSAGE;

                startInput(
                        player,
                        type
                );
                return;

            case ENDER_PEARL:
                type = NPCActionType.TELEPORT;

                saveAction(
                        player,
                        npc,
                        type,
                        locationToString(
                                player
                        ),
                        leftClick
                );
                return;

            case EMERALD:
                type = NPCActionType.SHOP;

                saveAction(
                        player,
                        npc,
                        type,
                        "survival",
                        leftClick
                );
                return;

            case DIAMOND_SWORD:
                type = NPCActionType.PVP;

                saveAction(
                        player,
                        npc,
                        type,
                        "",
                        leftClick
                );
                return;

            case GRASS_BLOCK:
                type = NPCActionType.SKYBLOCK;

                saveAction(
                        player,
                        npc,
                        type,
                        "",
                        leftClick
                );
                return;

            default:
                return;
        }
    }

    private void startInput(
            Player player,
            NPCActionType type
    ) {

        editorManager.startInput(
                player,
                type
        );

        player.closeInventory();

        player.sendMessage(
                ChatColor.YELLOW
                        + "Typ nu de waarde in de chat."
        );

        player.sendMessage(
                ChatColor.GRAY
                        + "Typ "
                        + ChatColor.RED
                        + "cancel"
                        + ChatColor.GRAY
                        + " om te annuleren."
        );
    }

    private void saveAction(
            Player player,
            NPCData npc,
            NPCActionType type,
            String value,
            boolean leftClick
    ) {

        NPCAction action =
                new NPCAction(
                        type,
                        value
                );

        if (leftClick) {

            npc.setLeftClickAction(
                    action
            );

        } else {

            npc.setRightClickAction(
                    action
            );
        }

        player.closeInventory();

        player.sendMessage(
                ChatColor.GREEN
                        + "NPC-actie opgeslagen."
        );

        player.sendMessage(
                ChatColor.GRAY
                        + "Actie: "
                        + ChatColor.YELLOW
                        + type.name()
        );
    }

    private String locationToString(
            Player player
    ) {

        var location =
                player.getLocation();

        return location.getWorld().getName()
                + ","
                + location.getX()
                + ","
                + location.getY()
                + ","
                + location.getZ()
                + ","
                + location.getYaw()
                + ","
                + location.getPitch();
    }
}
