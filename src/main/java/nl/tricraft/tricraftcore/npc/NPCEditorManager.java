package nl.tricraft.tricraftcore.npc;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NPCEditorManager {

    private final Map<UUID, String> editingNPCs =
            new HashMap<>();

    private final Map<UUID, Boolean> editingLeftClick =
            new HashMap<>();

    private final Map<UUID, NPCActionType> inputTypes =
            new HashMap<>();


    // =========================
    // NPC BEWERKEN
    // =========================

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


    public void stopEditing(
            Player player
    ) {

        UUID uuid =
                player.getUniqueId();

        editingNPCs.remove(uuid);
        editingLeftClick.remove(uuid);
        inputTypes.remove(uuid);
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

        return npcManager.getNPCById(
                npcId
        );
    }


    public boolean isEditing(
            Player player
    ) {

        return editingNPCs.containsKey(
                player.getUniqueId()
        );
    }


    // =========================
    // LINKER / RECHTER KLIK
    // =========================

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


    // =========================
    // CHAT INPUT
    // =========================

    public void startInput(
            Player player,
            NPCActionType type
    ) {

        inputTypes.put(
                player.getUniqueId(),
                type
        );
    }


    public boolean isWaitingForInput(
            Player player
    ) {

        return inputTypes.containsKey(
                player.getUniqueId()
        );
    }


    public NPCActionType getInputType(
            Player player
    ) {

        return inputTypes.get(
                player.getUniqueId()
        );
    }


    public void stopInput(
            Player player
    ) {

        inputTypes.remove(
                player.getUniqueId()
        );
    }
}
