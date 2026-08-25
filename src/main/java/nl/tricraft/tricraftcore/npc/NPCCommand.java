package nl.tricraft.tricraftcore.npc;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class NPCCommand implements CommandExecutor {

    private final NPCManager npcManager;

    public NPCCommand(NPCManager npcManager) {
        this.npcManager = npcManager;
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(
                    "Dit commando kan alleen door een speler worden gebruikt."
            );
            return true;
        }

        if (!player.hasPermission("tricraft.npc")) {
            player.sendMessage(
                    ChatColor.RED
                            + "Je hebt geen toestemming om NPC's te beheren."
            );
            return true;
        }

        if (args.length == 0) {
            sendHelp(player);
            return true;
        }

        switch (args[0].toLowerCase()) {

            case "create":
                createNPC(player, args);
                break;

            case "list":
                listNPCs(player);
                break;

            case "remove":
                removeNPC(player, args);
                break;

            case "rename":
                renameNPC(player, args);
                break;

            case "move":
                moveNPC(player, args);
                break;

            default:
                sendHelp(player);
                break;
        }

        return true;
    }

    private void createNPC(
            Player player,
            String[] args
    ) {

        if (args.length != 2) {
            player.sendMessage(
                    ChatColor.RED
                            + "Gebruik: /npc create <naam>"
            );
            return;
        }

        String name = args[1];

        if (npcManager.exists(name)) {
            player.sendMessage(
                    ChatColor.RED
                            + "Er bestaat al een NPC met deze naam."
            );
            return;
        }

        Location location =
                player.getLocation().clone();

        NPCData npc = new NPCData(
                UUID.randomUUID().toString(),
                name,
                location
        );

        npcManager.addNPC(npc);

        player.sendMessage(
                ChatColor.GREEN
                        + "NPC "
                        + ChatColor.YELLOW
                        + name
                        + ChatColor.GREEN
                        + " aangemaakt."
        );

        player.sendMessage(
                ChatColor.GRAY
                        + "Gebruik "
                        + ChatColor.YELLOW
                        + "/npc edit "
                        + name
                        + ChatColor.GRAY
                        + " om hem in te stellen."
        );
    }

    private void listNPCs(Player player) {

        if (npcManager.getNPCs().isEmpty()) {

            player.sendMessage(
                    ChatColor.YELLOW
                            + "Er zijn nog geen NPC's."
            );

            return;
        }

        player.sendMessage(
                ChatColor.DARK_AQUA
                        + "===== Tricraft NPC's ====="
        );

        for (NPCData npc : npcManager.getNPCs()) {

            player.sendMessage(
                    ChatColor.YELLOW
                            + npc.getName()
                            + ChatColor.GRAY
                            + " | "
                            + ChatColor.WHITE
                            + npc.getLocation().getWorld().getName()
            );
        }
    }

    private void removeNPC(
            Player player,
            String[] args
    ) {

        if (args.length != 2) {
            player.sendMessage(
                    ChatColor.RED
                            + "Gebruik: /npc remove <naam>"
            );
            return;
        }

        String name = args[1];

        if (!npcManager.exists(name)) {
            player.sendMessage(
                    ChatColor.RED
                            + "Deze NPC bestaat niet."
            );
            return;
        }

        npcManager.removeNPC(name);

        player.sendMessage(
                ChatColor.GREEN
                        + "NPC "
                        + ChatColor.YELLOW
                        + name
                        + ChatColor.GREEN
                        + " verwijderd."
        );
    }

    private void renameNPC(
            Player player,
            String[] args
    ) {

        if (args.length != 3) {
            player.sendMessage(
                    ChatColor.RED
                            + "Gebruik: /npc rename <naam> <nieuweNaam>"
            );
            return;
        }

        NPCData npc =
                npcManager.getNPC(args[1]);

        if (npc == null) {
            player.sendMessage(
                    ChatColor.RED
                            + "Deze NPC bestaat niet."
            );
            return;
        }

        if (npcManager.exists(args[2])) {
            player.sendMessage(
                    ChatColor.RED
                            + "Die naam wordt al gebruikt."
            );
            return;
        }

        String oldName = npc.getName();

        npcManager.removeNPC(oldName);

        npc.setName(args[2]);

        npcManager.addNPC(npc);

        player.sendMessage(
                ChatColor.GREEN
                        + "NPC hernoemd naar "
                        + ChatColor.YELLOW
                        + args[2]
        );
    }

    private void moveNPC(
            Player player,
            String[] args
    ) {

        if (args.length != 2) {
            player.sendMessage(
                    ChatColor.RED
                            + "Gebruik: /npc move <naam>"
            );
            return;
        }

        NPCData npc =
                npcManager.getNPC(args[1]);

        if (npc == null) {
            player.sendMessage(
                    ChatColor.RED
                            + "Deze NPC bestaat niet."
            );
            return;
        }

        npc.setLocation(
                player.getLocation().clone()
        );

        player.sendMessage(
                ChatColor.GREEN
                        + "NPC "
                        + ChatColor.YELLOW
                        + npc.getName()
                        + ChatColor.GREEN
                        + " verplaatst."
        );
    }

    private void sendHelp(Player player) {

        player.sendMessage(
                ChatColor.DARK_AQUA
                        + "===== Tricraft NPC ====="
        );

        player.sendMessage(
                ChatColor.YELLOW
                        + "/npc create <naam>"
        );

        player.sendMessage(
                ChatColor.YELLOW
                        + "/npc list"
        );

        player.sendMessage(
                ChatColor.YELLOW
                        + "/npc remove <naam>"
        );

        player.sendMessage(
                ChatColor.YELLOW
                        + "/npc rename <naam> <nieuweNaam>"
        );

        player.sendMessage(
                ChatColor.YELLOW
                        + "/npc move <naam>"
        );
    }
}
