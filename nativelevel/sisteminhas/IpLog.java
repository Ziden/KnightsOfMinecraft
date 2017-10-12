/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.sisteminhas;

/**
 *
 * @author usuario
 */
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;
import nativelevel.KoM;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class IpLog implements CommandExecutor, Listener {

    private File PlayerFile;
    private File IpFile;
    private FileConfiguration Players;
    private FileConfiguration Ips;
    private HashSet<String> list = new HashSet();
    File subdir = new File("plugins/Kom/");
    public KoM plugin;

    public void onEnable() {
        /*
        plugin = KnightsOfMinecraft._instance;
        LoadConfiguration();

        PlayerFile = new File(KnightsOfMinecraft._instance.getDataFolder(), "players.yml");
        IpFile = new File(KnightsOfMinecraft._instance.getDataFolder(), "ips.yml");

        if (!subdir.exists()) {
            subdir.mkdir();
        }
        if (!PlayerFile.exists()) {
            try {
                PlayerFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!IpFile.exists()) {
            try {
                IpFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Players = new YamlConfiguration();
        Ips = new YamlConfiguration();
        loadYamls();
        Bukkit.getServer().getPluginManager().registerEvents(this, KnightsOfMinecraft._instance);
                */
    }

    public void LoadConfiguration() {
        String path = "IpAdvLog.UseAuthCheck";
        String path2 = "IpAdvLog.Alert.AlertAdmins";
        String path3 = "IpAdvLog.Alert.AlertMessage";
        KoM._instance.getConfig().addDefault(path, Boolean.valueOf(true));
        KoM._instance.getConfig().addDefault(path2, Boolean.valueOf(true));
        KoM._instance.getConfig().addDefault(path3, "[&cAIL&f]&2%name% &aAlt accounts %alts%");
        KoM._instance.getConfig().options().copyDefaults(true);
        KoM._instance.saveConfig();
    }

    public void saveYamls() {
        try {
            Players.save(PlayerFile);
            Ips.save(IpFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadYamls() {
        try {
            Players.load(PlayerFile);
            Ips.load(IpFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDisable() {
        list.clear();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String playername = p.getName();
        if (KoM._instance.getConfig().getBoolean("IpAdvLog.UseAuthCheck")) {
            list.add(playername);
        } else {
            addYaml(playername, p.getAddress().getAddress().getHostAddress());
        }

        if ((KoM._instance.getConfig().getBoolean("IpAdvLog.Alert.AlertAdmins")) && (!p.hasPermission("ail.ignorealert"))) {
            List alts = new ArrayList();
            String c;
            if (!Players.getStringList(playername.toLowerCase()).isEmpty()) {
                for (String b : Players.getStringList(playername.toLowerCase())) {
                    b = b.replaceAll("\\.", "_");
                    for (Iterator localIterator2 = Ips.getStringList(b).iterator(); localIterator2.hasNext();) {
                        c = (String) localIterator2.next();
                        if (!alts.contains(c)) {
                            alts.add(c);
                        }
                    }
                }
            }
            if (alts.size() > 1) {
                String msg = KoM._instance.getConfig().getString("IpAdvLog.Alert.AlertMessage");
                msg = msg.replaceAll("%name%", playername);
                msg = msg.replaceAll("%alts%", alts.toString());
                msg = msg.replaceAll("&([0-9a-fA-F])", "§$1");
                Player[] arrayOfPlayer;
                for (Player cc : Bukkit.getOnlinePlayers()) {
                    if (cc.hasPermission("ail.alert")) {
                        cc.sendMessage(ChatColor.DARK_GREEN + playername + ChatColor.YELLOW + " Personagems:" + alts);
                    }
                }
            }
        }
    }

    public void addYaml(String playername, String ip) {
        if (Players.getStringList(playername) == null) {
            List list = new ArrayList();
            list.add(ip);
            Players.addDefault(playername, list);
        } else {
            List list2 = Players.getStringList(playername);
            if (!list2.contains(ip)) {
                list2.add(ip);
                Players.set(playername, list2);
            }
        }
        try {
            Players.save(PlayerFile);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        ip = ip.replaceAll("\\.", "_");
        if (Ips.getStringList(ip) == null) {
            List list3 = new ArrayList();
            list3.add(playername);
            Ips.addDefault(ip, list3);
        } else {
            List<String> list4 = Ips.getStringList(ip);
            Boolean doesit = Boolean.valueOf(false);
            for (String b : list4) {
                if (b.equalsIgnoreCase(playername)) {
                    doesit = Boolean.valueOf(true);
                }
            }
            if (!doesit.booleanValue()) {
                list4.add(playername);
                Ips.set(ip, list4);
            }
            try {
                Ips.save(IpFile);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        String playername = p.getName();
        if (list.contains(playername)) {
            list.remove(playername);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        try {
            if (KoM.debugMode) {
                KoM.log.info("label de cmd: " + commandLabel);
            }
            if (commandLabel.equalsIgnoreCase("fakes") || commandLabel.equalsIgnoreCase("/fakes")) {

                if (args.length < 1) {
                    sender.sendMessage(ChatColor.YELLOW + "__________ " + ChatColor.GREEN + "Caçador de Fakes" + ChatColor.YELLOW + " __________");
                    sender.sendMessage(ChatColor.YELLOW + "/fakes player <PlayerName>" + ChatColor.GREEN + "  Lista ips que o jogador usa");
                    sender.sendMessage(ChatColor.YELLOW + "/fakes ip <ip>" + ChatColor.GREEN + "  Lista jogadores usando este ip");
                    sender.sendMessage(ChatColor.YELLOW + "/fakes check <PlayerName>" + ChatColor.GREEN + "  Will find all players alternate accounts");
                    //sender.sendMessage(ChatColor.DARK_GREEN + "/fakes reload" + ChatColor.GREEN + "  Will reload the plugin");
                } else {
                    if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("player")) {
                            if (sender.hasPermission("ail.player")) {
                                sender.sendMessage(ChatColor.RED + "Uso: " + ChatColor.YELLOW + "/fakes player <PlayerName>");
                            }
                        } else if (args[0].equalsIgnoreCase("ip")) {
                            if (sender.hasPermission("ail.ip")) {
                                sender.sendMessage(ChatColor.RED + "Uso: " + ChatColor.YELLOW + "/fakes ip <ip>");
                            }
                        } else if (args[0].equalsIgnoreCase("check")) {
                            if (sender.hasPermission("ail.ip")) {
                                sender.sendMessage(ChatColor.RED + "Uso: " + ChatColor.YELLOW + "/fakes check <PlayerName>");
                            }
                        } else if ((args[0].equalsIgnoreCase("reload"))
                                && (sender.hasPermission("ail.reload"))) {
                            KoM._instance.getConfig();
                            KoM._instance.reloadConfig();
                            KoM._instance.getServer().getPluginManager().disablePlugin(plugin);
                            KoM._instance.getServer().getPluginManager().enablePlugin(plugin);
                            sender.sendMessage(ChatColor.YELLOW + "Config de Fakes recarregada");
                        }
                    }

                    if (args.length == 2) {
                        List<String> checker;
                        if (args[0].equalsIgnoreCase("player")) {
                            if ((sender.hasPermission("ail.player"))
                                    && (Players.getStringList(args[1].toLowerCase()) != null)) {
                                if (Players.getStringList(args[1].toLowerCase()).isEmpty()) {
                                    sender.sendMessage(ChatColor.RED + "Nao achei o jogador " + args[1]);
                                } else {
                                    sender.sendMessage(ChatColor.YELLOW + "IPS usados por " + ChatColor.GREEN + args[1]);
                                    for (String a : Players.getStringList(args[1].toLowerCase())) {
                                        checker = new ArrayList();
                                        a = a.replaceAll("\\.", "_");
                                        for (String c : Ips.getStringList(a)) {
                                            if (!checker.contains(c)) {
                                                checker.add(c);
                                            }
                                        }
                                        String alts = "";
                                        if (checker.size() > 1) {
                                            alts = "[" + checker.size() + " jogadores]";
                                        } else {
                                            alts = "";
                                        }

                                        a = a.replaceAll("\\_", ".");
                                        sender.sendMessage(ChatColor.DARK_GREEN + "- " + a + "  " + ChatColor.RED + alts);
                                    }
                                }
                            }
                        } else if (args[0].equalsIgnoreCase("ip")) {
                            if ((sender.hasPermission("ail.ip"))
                                    && (Ips.getStringList(args[1].replaceAll("\\.", "_")) != null)) {
                                if (Ips.getStringList(args[1].replaceAll("\\.", "_")).isEmpty()) {
                                    sender.sendMessage(ChatColor.RED + "Nao achei ip " + args[1]);
                                } else {
                                    sender.sendMessage(ChatColor.YELLOW + "Jogadores com ip " + ChatColor.GREEN + args[1]);
                                    for (String a : Ips.getStringList(args[1].replaceAll("\\.", "_"))) {
                                        sender.sendMessage(ChatColor.DARK_GREEN + "- " + a);
                                    }
                                }
                            }
                        } else if ((args[0].equalsIgnoreCase("check"))
                                && (sender.hasPermission("ail.check"))) {
                            checker = new ArrayList();
                            if (Players.getStringList(args[1].toLowerCase()).isEmpty()) {
                                sender.sendMessage(ChatColor.RED + "Nao achei o jogador " + args[1]);
                            } else {
                                for (String b : Players.getStringList(args[1].toLowerCase())) {
                                    b = b.replaceAll("\\.", "_");
                                    for (String c : Ips.getStringList(b)) {
                                        if (!checker.contains(c)) {
                                            checker.add(c);
                                        }
                                    }
                                }
                                sender.sendMessage(ChatColor.YELLOW + "Outros personagems de  " + ChatColor.GREEN + args[1]);
                                for (String d : checker) {
                                    sender.sendMessage(ChatColor.DARK_GREEN + "- " + d);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Nenhum fake foi catalogado ainda");
            e.printStackTrace();
        }
        return true;
    }
}