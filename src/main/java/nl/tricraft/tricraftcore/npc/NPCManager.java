package nl.tricraft.tricraftcore.npc;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class NPCManager {

    private final Map<String, NPCData> npcs = new HashMap<>();

    public void addNPC(NPCData npc) {
        npcs.put(npc.getId().toLowerCase(), npc);
    }

    public void removeNPC(String id) {
        npcs.remove(id.toLowerCase());
    }

    public NPCData getNPC(String id) {
        return npcs.get(id.toLowerCase());
    }

    public Collection<NPCData> getNPCs() {
        return npcs.values();
    }

    public boolean exists(String id) {
        return npcs.containsKey(id.toLowerCase());
    }
}
