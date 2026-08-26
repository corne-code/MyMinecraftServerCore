package nl.tricraft.tricraftcore.npc;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NPCEditorManager {

    private final Map<UUID, String> editingNPCs = new HashMap<>();

    public void startEditing(
            Player player,
            NPCData npc
    ) {
        editingNPCs.put(
                player.getUniqueId(),
                npc.getId()
        );
    }

    public void stopEditing(Player player) {
        editingNPCs.remove(
                player.getUniqueId()
        );
    }

    public NPCData getEditingNPC(
            Player player,
            NPCManager npcManager
    ) {

        String npcId =
                editingNPCs.get(
                        player.getUniqueId()
                );

        if (npcId == null) {
            return null;
        }

        return npcManager.getNPCById(npcId);
    }

    public boolean isEditing(Player player) {
        return editingNPCs.containsKey(
                player.getUniqueId()
        );
    }
}
