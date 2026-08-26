package nl.jouwserver.core;

import nl.tricraft.tricraftcore.npc.NPCActionGUI;
import nl.tricraft.tricraftcore.npc.NPCActionInputListener;
import nl.tricraft.tricraftcore.npc.NPCActionListener;
import nl.tricraft.tricraftcore.npc.NPCCommand;
import nl.tricraft.tricraftcore.npc.NPCEditorGUI;
import nl.tricraft.tricraftcore.npc.NPCEditorListener;
import nl.tricraft.tricraftcore.npc.NPCEditorManager;
import nl.tricraft.tricraftcore.npc.NPCLeftClickListener;
import nl.tricraft.tricraftcore.npc.NPCListener;
import nl.tricraft.tricraftcore.npc.NPCManager;
import nl.tricraft.tricraftcore.npc.NPCSpawner;
import nl.tricraft.tricraftcore.npc.NPCStorage;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class ServerCore extends JavaPlugin
        implements CommandExecutor, Listener {

    // =========================================
    // ECONOMY
    // =========================================

    private final HashMap<UUID, Double> economyBalances =
            new HashMap<>();

    // =========================================
    // CLAIMS
    // =========================================

    private final HashMap<UUID, Location> playerClaimsPos1 =
            new HashMap<>();

    private final HashMap<UUID, Location> playerClaimsPos2 =
            new HashMap<>();

    // =========================================
    // GUI TITELS
    // =========================================

    private final String SKYBLOCK_MENU_TITLE =
            "§aSkyblock Eiland Selectie";

    private final String SHOP_MENU_TITLE =
            "§6Server Economy Shop";

    // =========================================
    // NPC SYSTEEM
    // =========================================

    private NPCManager npcManager;

    private NPCEditorManager npcEditorManager;

    private NPCEditorGUI npcEditorGUI;

    private NPCActionGUI npcActionGUI;

    private NPCSpawner npcSpawner;

    private NPCStorage npcStorage;


    // =========================================
    // ENABLE
    // =========================================

    @Override
    public void onEnable() {

        // -----------------------------------------
        // BESTAANDE COMMANDO'S
        // -----------------------------------------

        registerCommand("skyblockmenu");
        registerCommand("shop");
        registerCommand("balance");
        registerCommand("spawn");
        registerCommand("claim");


        // -----------------------------------------
        // NPC MANAGERS
        // -----------------------------------------

        npcManager =
                new NPCManager();

        npcEditorManager =
                new NPCEditorManager();

        npcEditorGUI =
                new NPCEditorGUI(
                        npcManager
                );

        npcActionGUI =
                new NPCActionGUI();

        npcSpawner =
                new NPCSpawner(
                        this,
                        npcManager
                );

        npcStorage =
                new NPCStorage(
                        this,
                        npcManager
                );


        // -----------------------------------------
        // NPC DATA LADEN
        // -----------------------------------------

        npcStorage.load();


        // -----------------------------------------
        // NPC'S SPAWNEN
        // -----------------------------------------

        npcSpawner.spawnAll();


        // -----------------------------------------
        // NPC LISTENERS
        // -----------------------------------------

        getServer()
                .getPluginManager()
                .registerEvents(
                        new NPCListener(
                                this,
                                npcManager
                        ),
                        this
                );

        getServer()
                .getPluginManager()
                .registerEvents(
                        new NPCLeftClickListener(
                                this,
                                npcManager
                        ),
                        this
                );

        getServer()
                .getPluginManager()
                .registerEvents(
                        new NPCEditorListener(
                                npcManager,
                                npcEditorManager,
                                npcEditorGUI,
                                npcActionGUI
                        ),
                        this
                );

        getServer()
                .getPluginManager()
                .registerEvents(
                        new NPCActionListener(
                                npcManager,
                                npcEditorManager
                        ),
                        this
                );

        getServer()
                .getPluginManager()
                .registerEvents(
                        new NPCActionInputListener(
                                npcManager,
                                npcEditorManager
                        ),
                        this
                );


        // -----------------------------------------
        // NPC COMMAND
        // -----------------------------------------

        if (getCommand("npc") != null) {

            getCommand("npc").setExecutor(
                    new NPCCommand(
                            npcManager,
                            npcEditorManager,
                            npcEditorGUI
                    )
            );
        }


        // -----------------------------------------
        // SERVERCORE EVENTS
        // -----------------------------------------

        getServer()
                .getPluginManager()
                .registerEvents(
                        this,
                        this
                );


        getLogger().info(
                "================================="
        );

        getLogger().info(
                "ServerCore is geladen!"
        );

        getLogger().info(
                "Economy: actief"
        );

        getLogger().info(
                "Skyblock menu: actief"
        );

        getLogger().info(
                "Shop: actief"
        );

        getLogger().info(
                "Portalen: actief"
        );

        getLogger().info(
                "NPC systeem: actief"
        );

        getLogger().info(
                "================================="
        );
    }


    // =========================================
    // COMMAND REGISTRATIE
    // =========================================

    private void registerCommand(
            String name
    ) {

        if (getCommand(name) != null) {

            getCommand(name)
                    .setExecutor(this);
        }
    }


    // =========================================
    // DISABLE
    // =========================================

    @Override
    public void onDisable() {

        if (npcStorage != null) {

            npcStorage.save();
        }

        getLogger().info(
                "NPC's opgeslagen."
        );

        getLogger().info(
                "ServerCore is uitgeschakeld."
        );
    }


    // =========================================
    // COMMANDS
    // =========================================

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command cmd,
            String label,
            String[] args
    ) {

        if (!(sender instanceof Player)) {

            sender.sendMessage(
                    "Alleen spelers kunnen dit uitvoeren."
            );

            return true;
        }

        Player player =
                (Player) sender;


        // =====================================
        // SKYBLOCK MENU
        // =====================================

        if (cmd.getName()
                .equalsIgnoreCase("skyblockmenu")) {

            openSkyblockMenu(player);

            return true;
        }


        // =====================================
        // SHOP
        // =====================================

        if (cmd.getName()
                .equalsIgnoreCase("shop")) {

            openShopMenu(player);

            return true;
        }


        // =====================================
        // BALANCE
        // =====================================

        if (cmd.getName()
                .equalsIgnoreCase("balance")) {

            double bal =
                    economyBalances.getOrDefault(
                            player.getUniqueId(),
                            500.0
                    );

            player.sendMessage(
                    "§2Jouw saldo: §a€"
                            + bal
            );

            return true;
        }


        // =====================================
        // SPAWN
        // =====================================

        if (cmd.getName()
                .equalsIgnoreCase("spawn")) {

            Location spawnLoc =
                    new Location(
                            Bukkit.getWorld("Hub"),
                            -533.5,
                            128.0,
                            -673.5
                    );

            if (spawnLoc.getWorld() == null) {

                player.sendMessage(
                        "§cDe Hub wereld bestaat niet."
                );

                return true;
            }

            player.teleport(
                    spawnLoc
            );

            player.sendMessage(
                    "§eJe bent geteleporteerd naar de Spawn!"
            );

            player.playSound(
                    player.getLocation(),
                    Sound.ENTITY_ENDERMAN_TELEPORT,
                    1.0f,
                    1.0f
            );

            return true;
        }


        // =====================================
        // CLAIM
        // =====================================

        if (cmd.getName()
                .equalsIgnoreCase("claim")) {

            player.sendMessage(
                    "§eGebruik een gouden bijl om hoek 1 "
                            + "(links) en hoek 2 "
                            + "(rechts) te selecteren "
                            + "voor je claim!"
            );

            return true;
        }


        return false;
    }


    // =========================================
    // SKYBLOCK MENU
    // =========================================

    private void openSkyblockMenu(
            Player player
    ) {

        Inventory inv =
                Bukkit.createInventory(
                        null,
                        9,
                        SKYBLOCK_MENU_TITLE
                );

        inv.setItem(
                2,
                createItem(
                        Material.MAGMA_BLOCK,
                        "§c§lVuur Eiland",
                        "§7Klik om te starten op een vulkanisch eiland!"
                )
        );

        inv.setItem(
                4,
                createItem(
                        Material.PRISMARINE_BRICKS,
                        "§b§lWater Eiland",
                        "§7Klik om te starten op een oceaan eiland!"
                )
        );

        inv.setItem(
                6,
                createItem(
                        Material.GRASS_BLOCK,
                        "§a§lAarde Eiland",
                        "§7Klik om te starten op een klassiek eiland!"
                )
        );

        player.openInventory(inv);
    }


    // =========================================
    // SHOP
    // =========================================

    private void openShopMenu(
            Player player
    ) {

        Inventory inv =
                Bukkit.createInventory(
                        null,
                        27,
                        SHOP_MENU_TITLE
                );

        inv.setItem(
                11,
                createItem(
                        Material.DIAMOND,
                        "§bKoop Diamant",
                        "§7Prijs: §a€100\n"
                                + "§eKlik om te kopen!"
                )
        );

        inv.setItem(
                15,
                createItem(
                        Material.COBBLESTONE,
                        "§7Verkoop Cobblestone",
                        "§7Opbrengst: §a€5\n"
                                + "§eKlik om 64 te verkopen!"
                )
        );

        player.openInventory(inv);
    }


    // =========================================
    // ITEM MAKEN
    // =========================================

    private ItemStack createItem(
            Material mat,
            String name,
            String lore
    ) {

        ItemStack item =
                new ItemStack(mat);

        ItemMeta meta =
                item.getItemMeta();

        if (meta != null) {

            meta.setDisplayName(name);

            meta.setLore(
                    Arrays.asList(
                            lore.split("\n")
                    )
            );

            item.setItemMeta(meta);
        }

        return item;
    }


    // =========================================
    // GUI CLICKS
    // =========================================

    @EventHandler
    public void onMenuClick(
            InventoryClickEvent event
    ) {

        if (event.getCurrentItem() == null) {
            return;
        }

        if (!(event.getWhoClicked()
                instanceof Player)) {

            return;
        }

        Player player =
                (Player) event.getWhoClicked();

        String title =
                event.getView().getTitle();


        // =====================================
        // SKYBLOCK
        // =====================================

        if (title.equals(
                SKYBLOCK_MENU_TITLE
        )) {

            event.setCancelled(true);

            Material material =
                    event.getCurrentItem()
                            .getType();

            player.closeInventory();

            if (material ==
                    Material.MAGMA_BLOCK) {

                player.performCommand(
                        "is create vuur"
                );
            }

            else if (material ==
                    Material.PRISMARINE_BRICKS) {

                player.performCommand(
                        "is create water"
                );
            }

            else if (material ==
                    Material.GRASS_BLOCK) {

                player.performCommand(
                        "is create aarde"
                );
            }

            return;
        }


        // =====================================
        // SHOP
        // =====================================

        if (title.equals(
                SHOP_MENU_TITLE
        )) {

            event.setCancelled(true);

            UUID uuid =
                    player.getUniqueId();

            double bal =
                    economyBalances.getOrDefault(
                            uuid,
                            500.0
                    );


            // DIAMOND KOPEN

            if (event.getCurrentItem()
                    .getType()
                    == Material.DIAMOND) {

                if (bal >= 100.0) {

                    economyBalances.put(
                            uuid,
                            bal - 100.0
                    );

                    player.getInventory()
                            .addItem(
                                    new ItemStack(
                                            Material.DIAMOND,
                                            1
                                    )
                            );

                    player.sendMessage(
                            "§aJe hebt 1 Diamant gekocht voor €100!"
                    );

                } else {

                    player.sendMessage(
                            "§cJe hebt niet genoeg geld!"
                    );
                }
            }


            // COBBLESTONE VERKOPEN

            else if (event.getCurrentItem()
                    .getType()
                    == Material.COBBLESTONE) {

                if (player.getInventory()
                        .contains(
                                Material.COBBLESTONE,
                                64
                        )) {

                    player.getInventory()
                            .removeItem(
                                    new ItemStack(
                                            Material.COBBLESTONE,
                                            64
                                    )
                            );

                    economyBalances.put(
                            uuid,
                            bal + 5.0
                    );

                    player.sendMessage(
                            "§aJe hebt 64 Cobblestone verkocht voor €5!"
                    );

                } else {

                    player.sendMessage(
                            "§cJe hebt geen 64 Cobblestone in je inventaris!"
                    );
                }
            }
        }
    }


    // =========================================
    // PORTALEN
    // =========================================

    @EventHandler
    public void onPlayerMove(
            PlayerMoveEvent event
    ) {

        Player player =
                event.getPlayer();

        Location loc =
                player.getLocation();

        if (loc.getWorld() == null) {
            return;
        }

        if (loc.getWorld()
                .getName()
                .equalsIgnoreCase("Hub")) {

            if (
                    loc.getBlockX() == -404
                            && loc.getBlockY() >= 129
                            && loc.getBlockY() <= 132
                            && loc.getBlockZ() >= -775
                            && loc.getBlockZ() <= -770
            ) {

                openSkyblockMenu(player);

                player.sendMessage(
                        "§eJe bent in het Skyblock portaal gestapt!"
                );
            }
        }
    }


    // =========================================
    // JOIN
    // =========================================

    @EventHandler
    public void onJoin(
            PlayerJoinEvent event
    ) {

        Player player =
                event.getPlayer();

        if (!economyBalances.containsKey(
                player.getUniqueId()
        )) {

            economyBalances.put(
                    player.getUniqueId(),
                    500.0
            );
        }

        event.setJoinMessage(
                "§7[§a+§7] §e"
                        + player.getName()
                        + " is de server binnengevlogen!"
        );
    }


    // =========================================
    // CHAT
    // =========================================

    @EventHandler
    public void onChat(
            AsyncPlayerChatEvent event
    ) {

        Player player =
                event.getPlayer();

        String prefix;

        if (player.isOp()) {

            prefix =
                    "§4[Eigenaar] ";

        } else {

            prefix =
                    "§7[Speler] ";
        }

        event.setFormat(
                prefix
                        + "§f"
                        + player.getName()
                        + " §8» §7"
                        + event.getMessage()
        );
    }
}
