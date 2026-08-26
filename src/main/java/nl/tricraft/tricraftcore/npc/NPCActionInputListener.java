package nl.tricraft.tricraftcore.npc;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class NPCActionInputListener implements Listener {

    private final NPCManager npcManager;
    private final NPCEditorManager editorManager;

    public NPCActionInputListener(
            NPCManager npcManager,
            NPCEditorManager editorManager
    ) {
        this.npcManager = npcManager;
        this.editorManager = editorManager;
    }

    @EventHandler
    public void onChat(
            AsyncPlayerChatEvent event
    ) {

        Player player = event.getPlayer();

        if (!editorManager.isWaitingForInput(player)) {
            return;
        }

        event.setCancelled(true);

        String input = event.getMessage();

        // Annuleren
        if (input.equalsIgnoreCase("cancel")) {

            editorManager.stopInput(player);

            player.sendMessage(
                    ChatColor.RED
                            + "NPC-actie geannuleerd."
            );

            return;
        }

        NPCData npc =
                editorManager.getEditingNPC(
                        player,
                        npcManager
                );

        if (npc == null) {

            editorManager.stopInput(player);

            player.sendMessage(
                    ChatColor.RED
                            + "De NPC bestaat niet meer."
            );

            return;
        }

        NPCActionType type =
                editorManager.getInputType(player);

        if (type == null) {

            editorManager.stopInput(player);

            player.sendMessage(
                    ChatColor.RED
                            + "Er is geen actie geselecteerd."
            );

            return;
        }

        boolean leftClick =
                editorManager.isEditingLeftClick(
                        player
                );

        NPCAction action =
                new NPCAction(
                        type,
                        input
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

        editorManager.stopInput(player);

        player.sendMessage(
                ChatColor.GREEN
                        + "NPC-actie opgeslagen."
        );

        player.sendMessage(
                ChatColor.GRAY
                        + "Type: "
                        + ChatColor.YELLOW
                        + type.name()
        );

        player.sendMessage(
                ChatColor.GRAY
                        + "Waarde: "
                        + ChatColor.WHITE
                        + input
        );
    }
}
