package nl.tricraft.tricraftcore.npc;

import org.bukkit.Location;

public class NPCData {

    private final String id;

    private String name;

    private Location location;

    private NPCAction leftClickAction;

    private NPCAction rightClickAction;

    public NPCData(
            String id,
            String name,
            Location location
    ) {
        this.id = id;
        this.name = name;
        this.location = location;

        this.leftClickAction =
                new NPCAction(
                        NPCActionType.NONE,
                        ""
                );

        this.rightClickAction =
                new NPCAction(
                        NPCActionType.NONE,
                        ""
                );
    }

    // =========================
    // ID
    // =========================

    public String getId() {
        return id;
    }

    // =========================
    // NAAM
    // =========================

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // =========================
    // LOCATIE
    // =========================

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    // =========================
    // LINKERMUISKLIK
    // =========================

    public NPCAction getLeftClickAction() {
        return leftClickAction;
    }

    public void setLeftClickAction(
            NPCAction action
    ) {
        this.leftClickAction = action;
    }

    // =========================
    // RECHTERMUISKLIK
    // =========================

    public NPCAction getRightClickAction() {
        return rightClickAction;
    }

    public void setRightClickAction(
            NPCAction action
    ) {
        this.rightClickAction = action;
    }
}
