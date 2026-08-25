package nl.tricraft.tricraftcore.npc;

import org.bukkit.Location;

public class NPCData {

    private final String id;
    private String name;
    private NPCType type;
    private Location location;

    public NPCData(
            String id,
            String name,
            NPCType type,
            Location location
    ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public NPCType getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(NPCType type) {
        this.type = type;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
