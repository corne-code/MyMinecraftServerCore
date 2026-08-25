package nl.tricraft.tricraftcore.listeners;

import nl.tricraft.tricraftcore.commands.FreezeCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FreezeListener implements Listener {

    private final FreezeCommand freezeCommand;

    public FreezeListener(FreezeCommand freezeCommand) {
        this.freezeCommand = freezeCommand;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        if (!freezeCommand.isFrozen(event.getPlayer())) {
            return;
        }

        if (event.getTo() == null) {
            return;
        }

        // Alleen beweging blokkeren, draaien mag nog wel.
        if (event.getFrom().getX() != event.getTo().getX()
                || event.getFrom().getY() != event.getTo().getY()
                || event.getFrom().getZ() != event.getTo().getZ()) {

            event.setTo(event.getFrom());
        }
    }
}
