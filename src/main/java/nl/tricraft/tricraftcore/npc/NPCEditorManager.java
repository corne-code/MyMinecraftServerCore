package nl.tricraft.tricraftcore.npc;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NPCEditorManager {

    private final Map<UUID, String> editingNPCs = new HashMap<>();

    private final Map<UUID, Boolean> editingLeftClick =
            new HashMap<>();

    public void startEditing(
            Player player,
            NPCData npc
    ) {
        editingNPCs.put(
                player.getUniqueId(),
                npc.getId()
        );

        editingLeftClick.put(
                player.getUniqueId(),
                true
        );
    }

    public void stopEditing(Player player) {

        UUID uuid =
                player.getUniqueId();

        editingNPCs.remove(uuid);
        editingLeftClick.remove(uuid);
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

    public void setEditingLeftClick(
            Player player,
            boolean leftClick
    ) {

        editingLeftClick.put(
                player.getUniqueId(),
                leftClick
        );
    }

    public boolean isEditingLeftClick(
            Player player
    ) {

        return editingLeftClick.getOrDefault(
                player.getUniqueId(),
                true
        );
    }
}
