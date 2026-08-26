package nl.tricraft.tricraftcore.npc;

import java.util.ArrayList;
import java.util.List;

public class NPCManager {

    private final List<NPCData> npcs =
            new ArrayList<>();

    // =========================
    // NPC TOEVOEGEN
    // =========================

    public void addNPC(NPCData npc) {

        if (npc == null) {
            return;
        }

        // Voorkom dubbele ID's
        if (getNPCById(npc.getId()) != null) {
            return;
        }

        npcs.add(npc);
    }

    // =========================
    // NPC VERWIJDEREN
    // =========================

    public void removeNPC(String name) {

        NPCData npc =
                getNPC(name);

        if (npc != null) {
            npcs.remove(npc);
        }
    }

    public void removeNPC(NPCData npc) {

        if (npc != null) {
            npcs.remove(npc);
        }
    }

    // =========================
    // NPC OP NAAM
    // =========================

    public NPCData getNPC(String name) {

        for (NPCData npc : npcs) {

            if (npc.getName()
                    .equalsIgnoreCase(name)) {

                return npc;
            }
        }

        return null;
    }

    // =========================
    // NPC OP ID
    // =========================

    public NPCData getNPCById(String id) {

        if (id == null) {
            return null;
        }

        for (NPCData npc : npcs) {

            if (npc.getId()
                    .equals(id)) {

                return npc;
            }
        }

        return null;
    }

    // =========================
    // BESTAAT NPC?
    // =========================

    public boolean exists(String name) {

        return getNPC(name) != null;
    }

    // =========================
    // ALLE NPC'S
    // =========================

    public List<NPCData> getNPCs() {

        return npcs;
    }

    // =========================
    // ALLES WISSEN
    // =========================

    public void clear() {

        npcs.clear();
    }

    // =========================
    // AANTAL NPC'S
    // =========================

    public int size() {

        return npcs.size();
    }
}
