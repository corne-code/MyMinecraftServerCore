package nl.jouwserver.core;

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

public class ServerCore extends JavaPlugin implements CommandExecutor, Listener {

    // Databases in het geheugen (Voor productie kun je dit later aan MySQL/SQLite koppelen)
    private final HashMap<UUID, Double> economyBalances = new HashMap<>();
    private final HashMap<UUID, Location> playerClaimsPos1 = new HashMap<>();
    private final HashMap<UUID, Location> playerClaimsPos2 = new HashMap<>();
    
    private final String SKYBLOCK_MENU_TITLE = "§aSkyblock Eiland Selectie";
    private final String SHOP_MENU_TITLE = "§6Server Economy Shop";

    @Override
    public void onEnable() {
        // Registreer commando's
        getCommand("skyblockmenu").setExecutor(this);
        getCommand("shop").setExecutor(this);
        getCommand("balance").setExecutor(this);
        getCommand("spawn").setExecutor(this);
        getCommand("claim").setExecutor(this);

        // Registreer events
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("ServerCore is succesvol geladen! Hub, Economy en Portalen zijn actief.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Alleen spelers kunnen dit uitvoeren.");
            return true;
        }
        Player player = (Player) sender;

        // 1. SKYBLOCK MENU
        if (cmd.getName().equalsIgnoreCase("skyblockmenu")) {
            openSkyblockMenu(player);
            return true;
        }

        // 2. ECONOMY SHOP MENU
        if (cmd.getName().equalsIgnoreCase("shop")) {
            openShopMenu(player);
            return true;
        }

        // 3. SALDO BEKIJKEN (/balance)
        if (cmd.getName().equalsIgnoreCase("balance")) {
            double bal = economyBalances.getOrDefault(player.getUniqueId(), 500.0); // 500 startgeld
            player.sendMessage("§2Jouw saldo: §a€" + bal);
            return true;
        }

        // 4. SPAWN TELEPORT (Vervangt EssentialsSpawn)
        if (cmd.getName().equalsIgnoreCase("spawn")) {
            Location spawnLoc = new Location(Bukkit.getWorld("Hub"), -533.5, 128.0, -673.5);
            player.teleport(spawnLoc);
            player.sendMessage("§eJe bent geteleporteerd naar de Spawn!");
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
            return true;
        }

        // 5. LAND CLAIMS BASIS
        if (cmd.getName().equalsIgnoreCase("claim")) {
            player.sendMessage("§eGebruik een gouden bijl om hoek 1 (links) en hoek 2 (rechts) te selecteren voor je claim!");
            return true;
        }

        return false;
    }

    // --- GUI MENUS ---
    private void openSkyblockMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9, SKYBLOCK_MENU_TITLE);
        inv.setItem(2, createItem(Material.MAGMA_BLOCK, "§c§lVuur Eiland", "§7Klik om te starten op een vulkanisch eiland!"));
        inv.setItem(4, createItem(Material.PRISMARINE_BRICKS, "§b§lWater Eiland", "§7Klik om te starten op een oceaan eiland!"));
        inv.setItem(6, createItem(Material.GRASS_BLOCK, "§a§lAarde Eiland", "§7Klik om te starten op een klassiek eiland!"));
        player.openInventory(inv);
    }

    private void openShopMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, SHOP_MENU_TITLE);
        // Voorbeeld items: Koop Diamond voor €100, Verkoop Cobblestone voor €5
        inv.setItem(11, createItem(Material.DIAMOND, "§bKoop Diamant", "§7Prijs: §a€100\n§eKlik om te kopen!"));
        inv.setItem(15, createItem(Material.COBBLESTONE, "§7Verkoop Cobblestone", "§7Opbrengst: §a€5\n§eKlik om 64 te verkopen!"));
        player.openInventory(inv);
    }

    private ItemStack createItem(Material mat, String name, String lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(Arrays.asList(lore.split("\n")));
            item.setItemMeta(meta);
        }
        return item;
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        Player player = (Player) event.getWhoClicked();
        String title = event.getView().getTitle();

        // Skyblock Menu Logica
        if (title.equals(SKYBLOCK_MENU_TITLE)) {
            event.setCancelled(true);
            player.closeInventory();
            if (event.getCurrentItem().getType() == Material.MAGMA_BLOCK) player.performCommand("is create vuur");
            if (event.getCurrentItem().getType() == Material.PRISMARINE_BRICKS) player.performCommand("is create water");
            if (event.getCurrentItem().getType() == Material.GRASS_BLOCK) player.performCommand("is create aarde");
        }

        // Shop Economy Logica
        if (title.equals(SHOP_MENU_TITLE)) {
            event.setCancelled(true);
            UUID uuid = player.getUniqueId();
            double bal = economyBalances.getOrDefault(uuid, 500.0);

            if (event.getCurrentItem().getType() == Material.DIAMOND) {
                if (bal >= 100.0) {
                    economyBalances.put(uuid, bal - 100.0);
                    player.getInventory().addItem(new ItemStack(Material.DIAMOND, 1));
                    player.sendMessage("§aJe hebt 1 Diamant gekocht voor €100!");
                } else {
                    player.sendMessage("§cJe hebt niet genoeg geld!");
                }
            }
            
            if (event.getCurrentItem().getType() == Material.COBBLESTONE) {
                if (player.getInventory().contains(Material.COBBLESTONE, 64)) {
                    player.getInventory().removeItem(new ItemStack(Material.COBBLESTONE, 64));
                    economyBalances.put(uuid, bal + 5.0);
                    player.sendMessage("§aJe hebt 64 Cobblestone verkocht voor €5!");
                } else {
                    player.sendMessage("§cJe hebt geen 64 Cobblestone in je inventaris!");
                }
            }
        }
    }

    // --- INTEGRATIE PORTALEN (Zonder losse plugins!) ---
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location loc = player.getLocation();

        // Voorbeeld: Als een speler door de coördinaten van je Hub vuurportaal loopt
        if (loc.getWorld().getName().equalsIgnoreCase("Hub")) {
            // Controleer of de speler zich binnen de X en Z assen van de vuurpoort bevindt
            if (loc.getBlockX() == -404 && loc.getBlockY() >= 129 && loc.getBlockY() <= 132 && loc.getBlockZ() >= -775 && loc.getBlockZ() <= -770) {
                // Teleporteer ze direct of open het menu!
                openSkyblockMenu(player);
                player.sendMessage("§eJe bent in het Skyblock portaal gestapt!");
            }
        }
    }

    // --- ESSENTIALS CHAT & JOIN INDELING ---
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        // Geef startgeld als ze nieuw zijn
        if (!economyBalances.containsKey(player.getUniqueId())) {
            economyBalances.put(player.getUniqueId(), 500.0);
        }
        event.setJoinMessage("§7[§a+§7] §e" + player.getName() + " is de server binnengevlogen!");
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        // Hier kun je LuckPerms prefixes uitlezen, of een strakke basisindeling hanteren:
        String prefix = player.isOp() ? "§4[Eigenaar] " : "§7[Speler] ";
        event.setFormat(prefix + "§f" + player.getName() + " §8» §7" + event.getMessage());
    }
}
