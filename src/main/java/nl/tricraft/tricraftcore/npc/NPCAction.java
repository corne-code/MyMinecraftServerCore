package nl.tricraft.tricraftcore.npc;

public class NPCAction {

    private NPCActionType type;
    private String value;

    public NPCAction(
            NPCActionType type,
            String value
    ) {
        this.type = type;
        this.value = value;
    }

    public NPCActionType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public void setType(NPCActionType type) {
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
