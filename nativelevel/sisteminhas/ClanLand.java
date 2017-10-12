/*

 ╭╮╭━╮╱╱╭━╮╭━╮
 ┃┃┃╭╯╱╱┃┃╰╯┃┃
 ┃╰╯╯╭━━┫╭╮╭╮┃
 ┃╭╮┃┃╭╮┃┃┃┃┃┃
 ┃┃┃╰┫╰╯┃┃┃┃┃┃
 ╰╯╰━┻━━┻╯╰╯╰╯

 Desenvolvedor: ZidenVentania
 Colaboradores: NeT32, Gabripj, Feldmann
 Patrocionio: InstaMC

 */
package nativelevel.sisteminhas;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import nativelevel.utils.TitleAPI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import nativelevel.ArenaGuilda2x2.Arena2x2;
import nativelevel.Comandos.Terreno;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Attributes.AttributeInfo;
import nativelevel.MetaShit;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import net.sacredlabyrinth.phaed.simpleclans.managers.ClanManager;
import net.sacredlabyrinth.phaed.simpleclans.managers.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 *
 * @author Daniel
 */
public class ClanLand {

    public static ClanManager manager;
    public static StorageManager storage;
    public static ClanLand plug;
    private static Logger log;
    public static Economy econ;
    private static Statement est;
    public static Permission permission = null;

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = KoM._instance.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    public static boolean isSameChunk(Location one, Location two) {
        if (one.getBlockX() >> 4 != two.getBlockX() >> 4) {
            return false;
        }
        if (one.getBlockZ() >> 4 != two.getBlockZ() >> 4) {
            return false;
        }
        return one.getWorld() == two.getWorld();
    }

    public static void marcaChunk(final Player p, Chunk c) {

        final List<Location> locais = new ArrayList<Location>();

        locais.add(c.getWorld().getHighestBlockAt(c.getBlock(0, 0, 0).getLocation()).getLocation().add(0, -1, 0));
        locais.add(c.getWorld().getHighestBlockAt(c.getBlock(1, 0, 0).getLocation()).getLocation().add(0, -1, 0));
        locais.add(c.getWorld().getHighestBlockAt(c.getBlock(0, 0, 1).getLocation()).getLocation().add(0, -1, 0));

        locais.add(c.getWorld().getHighestBlockAt(c.getBlock(15, 0, 0).getLocation()).getLocation().add(0, -1, 0));
        locais.add(c.getWorld().getHighestBlockAt(c.getBlock(15, 0, 1).getLocation()).getLocation().add(0, -1, 0));
        locais.add(c.getWorld().getHighestBlockAt(c.getBlock(14, 0, 0).getLocation()).getLocation().add(0, -1, 0));

        locais.add(c.getWorld().getHighestBlockAt(c.getBlock(0, 0, 15).getLocation()).getLocation().add(0, -1, 0));
        locais.add(c.getWorld().getHighestBlockAt(c.getBlock(1, 0, 15).getLocation()).getLocation().add(0, -1, 0));
        locais.add(c.getWorld().getHighestBlockAt(c.getBlock(0, 0, 14).getLocation()).getLocation().add(0, -1, 0));

        locais.add(c.getWorld().getHighestBlockAt(c.getBlock(15, 0, 15).getLocation()).getLocation().add(0, -1, 0));
        locais.add(c.getWorld().getHighestBlockAt(c.getBlock(14, 0, 15).getLocation()).getLocation().add(0, -1, 0));
        locais.add(c.getWorld().getHighestBlockAt(c.getBlock(15, 0, 14).getLocation()).getLocation().add(0, -1, 0));

        new Thread() {
            public void run() {
                if (p.hasMetadata("marcacao")) {
                    List<Location> locais = (List<Location>) MetaShit.getMetaObject("marcacao", p);
                    for (Location l : locais) {
                        p.sendBlockChange(l, l.getBlock().getType(), l.getBlock().getData());
                    }
                }

                MetaShit.setMetaObject("marcacao", p, locais);

                for (Location l : locais) {
                    p.sendBlockChange(l, Material.YELLOW_GLAZED_TERRACOTTA, (byte) 0);
                }
            }
        }.start();

    }

    public static void update(Player p, Location to) {
        String type = getTypeAt(to);
        if (type.equalsIgnoreCase("SAFE")) {
            //rawMsg(p, ChatColor.AQUA + " ~" + ChatColor.GOLD + "SafeZone");
            TitleAPI.sendTitle(p, 10, 10, 60, ChatColor.GOLD + "Cidade", "");
        } else if (type.equalsIgnoreCase("WARZ")) {
            TitleAPI.sendTitle(p, 10, 10, 60, ChatColor.DARK_RED + "WarZone", "");
            // rawMsg(p, ChatColor.AQUA + " ~" + ChatColor.DARK_RED + "WarZone");
        } else if (type.equalsIgnoreCase("WILD")) {
            //rawMsg(p, ChatColor.AQUA + " ~" + ChatColor.DARK_GREEN + L.m("Terras sem Dono"));
            TitleAPI.sendTitle(p, 10, 10, 60, ChatColor.DARK_GREEN + L.m("Terras sem Dono"), "");
        } else {
            Clan c = getClanAt(to);
            if (c.getAllMembers().contains(getPlayer(p.getName()))) {
                String owner = getOwnerAt(to);
                if (owner.equals("none")) {
                    // rawMsg(p, ChatColor.AQUA + "~§r" + c.getColorTag() + ChatColor.AQUA
                    //        + " - " + "§r" + c.getName() + ChatColor.AQUA
                    //         + " - " + ChatColor.DARK_GREEN + L.m("Publico"));
                    TitleAPI.sendTitle(p, 10, 10, 60, c.getColorTag() + ChatColor.AQUA
                            + " - " + "§r" + c.getName() + ChatColor.AQUA, ChatColor.AQUA + "Terreno Publico");
                } else {
                    OfflinePlayer dono = Bukkit.getPlayer(UUID.fromString(owner));
                    if (dono == null) {
                        dono = Bukkit.getOfflinePlayer(UUID.fromString(owner));
                    }
                    if (dono == null) {
                        owner = L.m("erro, falar com staff !");
                    }
                    owner = dono.getName();
                    //rawMsg(p, ChatColor.AQUA + "~§r" + c.getColorTag() + ChatColor.AQUA
                    //        + " - " + "§r" + c.getName() + ChatColor.AQUA
                    //        + " - " + ChatColor.DARK_GREEN + L.m("Terreno de") + "§r" + owner);
                    TitleAPI.sendTitle(p, 10, 10, 60, c.getColorTag() + ChatColor.AQUA
                            + " - " + "§r" + c.getName() + ChatColor.AQUA, ChatColor.AQUA + "Terreno de " + owner);
                }
            } else {
                //rawMsg(p, ChatColor.AQUA + "~§r" + c.getColorTag() + ChatColor.AQUA + " - " + "§r" + c.getName());
                TitleAPI.sendTitle(p, 10, 10, 60, c.getColorTag() + ChatColor.AQUA
                        + " - " + "§r" + c.getName() + ChatColor.AQUA, "");
            }
        }
    }

    public static void rawMsg(Player p, String string) {
        p.sendMessage(string);
    }

    public static void sincroniza() {
        /*
         int ptos = 0;
         try {
         est = KnightsOfMania.database.pegaConexao().createStatement();
         ResultSet rs = est.executeQuery("select ptos from ptosPilhagem where minhaTag='"+minhaTag+"' and tagInimiga='"+tagInimiga+"'");
         if(rs.next()) {
         return rs.getInt("ptos");
         } else {
         est.execute("insert into ptosPilhagem (ptos, minhaTag, tagInimiga) values (0,'"+minhaTag+"' ,'"+tagInimiga+"')"); 
         }
         } catch (SQLException ex) {
         NativeLevel.log.info("ZUOU BANCO:"+ex.getMessage());
         ex.printStackTrace();
         }
         return ptos;
         * 
         */
    }

    public static String getGuildaAli(Chunk c) {
        try {
            String tag = null;
            est = KoM.database.pegaConexao().createStatement();
            ResultSet rs = est.executeQuery("select tag from kk where world = '" + c.getWorld().getName() + "' and x =" + c.getX() + " and z = " + c.getZ());
            if (rs.next()) {
                tag = rs.getString("tag");
            }
            rs.close();
            return tag;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                est.close();
                KoM.database.pegaConexao().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void limpaGuildaEDesfaz(final String tag, final boolean regen) {

        KoM.log.info("desfazendo terreno do clan |" + tag + "|");

        Arena2x2.sql.resetaGuilda(tag);

        try {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        List<Location> limpar = new ArrayList<Location>();
                        est = KoM.database.pegaConexao().createStatement();
                        List<Chunk> removidos = new ArrayList<Chunk>();
                        KoM.log.info("peski");
                        ResultSet rs = est.executeQuery("select world, x, z from kk where tag = '" + tag + "'");
                        KoM.log.info("peskizei");
                        while (rs.next()) {
                            KoM.log.info("achei um terreno do clan " + tag);
                            final World w = Bukkit.getWorld(rs.getString("world"));
                            if (w != null) {
                                Chunk c = w.getChunkAt(rs.getInt("x"), rs.getInt("z"));
                                Clan clan = ClanLand.getClanAt(c.getBlock(0, 0, 0).getLocation());
                                if (regen) {
                                    if (!c.isLoaded()) {
                                        if (clan.getInactiveDays() > 7) {
                                            c.load(true);
                                        } else {
                                            c.load();
                                        }
                                    }
                                }
                                if (clan != null && clan.getTag().equalsIgnoreCase(tag)) {
                                    limpar.add(c.getBlock(0, 0, 0).getLocation());
                                    // if (KnightsOfMinecraft.debugMode) {
                                    KoM.log.info("defiz um terreno do clan " + tag);
                                    //}
                                }
                            }
                        }
                        rs.close();
                        for (Location l : limpar) {
                            ClanLand.removeClanAt(l);
                            ClanLand.setClanAt(l, "WILD");
                        }
                        KoM.log.info("limpado");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        est = KoM.database.pegaConexao().createStatement();
                        est.executeUpdate("delete from kk where tag='" + tag + "'");
                        est = KoM.database.pegaConexao().createStatement();
                        est.executeUpdate("delete from ptosPilhagem where minhaTag='" + tag + "'");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r, 1);
            //KnightsOfMania.database.pegaConexao().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        } finally {
            try {
                est.close();
                KoM.database.pegaConexao().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onEnable() {
        plug = this;
        manager = ((SimpleClans) Bukkit.getServer().getPluginManager()
                .getPlugin("SimpleClans")).getClanManager();
        storage = ((SimpleClans) Bukkit.getServer().getPluginManager()
                .getPlugin("SimpleClans")).getStorageManager();
        log = Logger.getLogger("Minecraft");
        setupEconomy();
        setupPermissions();
        String dbName = KoM.config.getConfig().getString("database.name");
        String connStr = "jdbc:mysql://localhost:3306/kom?autoReconnect=true";
        if(KoM.serverTestes)
                connStr = "jdbc:mysql://localhost:3306/komtestes?autoReconnect=true";
        KoM._instance.getDataFolder().mkdirs();
        Bukkit.getPluginCommand("terreno").setExecutor(new Terreno());
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            est = KoM.database.pegaConexao().createStatement();
            est.executeUpdate("CREATE TABLE IF NOT EXISTS kk ("
                    + "tag VARCHAR(10) PRIMARY KEY, "
                    + "world VARCHAR(20), "
                    + "x INT, "
                    + "z INT, "
                    + "owner VARCHAR(200), "
                    + "port INT)");
            est.executeUpdate("CREATE TABLE IF NOT EXISTS ptosPilhagem ("
                    + "minhaTag VARCHAR(10) PRIMARY KEY, "
                    + "tagInimiga VARCHAR(3), "
                    + "ptos INT ); ");
            est.executeUpdate("CREATE TABLE IF NOT EXISTS poder ("
                    + "minhaTag VARCHAR(10) PRIMARY KEY, "
                    + "qtd INT ); ");

        } catch (Exception ex) {
            log.log(Level.SEVERE, null, ex);
            Bukkit.getServer().shutdown();
        } finally {
            try {
                est.close();
                KoM.database.pegaConexao().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void limpaChunks(String tag) {
        try {
            AttributeInfo info;
            est = KoM.database.pegaConexao().createStatement();
            ResultSet rs = est.executeQuery("select mundo, x, z from kk where tag = '" + tag + "'");
            while (rs.next()) {
                World w = Bukkit.getWorld(rs.getString("mundo"));
                int x = rs.getInt("x");
                int z = rs.getInt("z");
                KoM.log.info("limpando terreno " + rs.getString("mundo") + " " + x + " " + z);
                if (w != null) {
                    w.regenerateChunk(x, z);
                    KoM.log.info("regenerado !");
                }
            }
            //KnightsOfMania.database.pegaConexao().commit();
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        }
    }

    // registrando o metodo de economia do vault
    private boolean setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static int getQtdTerrenos(String minhaTag) {
        int ptos = 0;
        ResultSet rs = null;
        try {
            est = KoM.database.pegaConexao().createStatement();
            rs = est.executeQuery("select count(tag) as qtd from kk where tag='" + minhaTag + "'");
            if (rs.next()) {
                return rs.getInt("qtd");
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                rs.close();
                est.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ptos;
    }

    public static int getPoder(String minhaTag) {
        int ptos = 0;
        ResultSet rs = null;
        try {
            est = KoM.database.pegaConexao().createStatement();
            rs = est.executeQuery("select qtd from poder where minhaTag='" + minhaTag + "'");
            if (rs.next()) {
                return rs.getInt("qtd");
            } else {
                est.execute("insert into poder (minhaTag, qtd) values ('" + minhaTag + "' ,0)");
            }
            //KnightsOfMania.database.pegaConexao().commit();
        } catch (SQLException ex) {
            KoM.log.info(ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
                est.close();
                KoM.database.pegaConexao().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ptos;
    }

    public static void setPoder(String minhaTag, int ptos) {
        try {
            est = KoM.database.pegaConexao().createStatement();
            est.executeUpdate("update poder set qtd = " + ptos + " where minhaTag='" + minhaTag + "'");
            //KnightsOfMania.database.pegaConexao().commit();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, null, ex);
        } finally {
            try {
                est.close();
                KoM.database.pegaConexao().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int getPtosPilagem(String minhaTag, String tagInimiga) {
        int ptos = 0;
        ResultSet rs = null;
        try {
            est = KoM.database.pegaConexao().createStatement();
            rs = est.executeQuery("select ptos from ptosPilhagem where minhaTag='" + minhaTag + "' and tagInimiga='" + tagInimiga + "'");
            if (rs.next()) {
                return rs.getInt("ptos");
            } else {
                est.execute("insert into ptosPilhagem (ptos, minhaTag, tagInimiga) values (0,'" + minhaTag + "' ,'" + tagInimiga + "')");
            }
            //KnightsOfMania.database.pegaConexao().commit();
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
                est.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ptos;
    }

    public static void setPtosPilhagem(String minhaTag, String tagInimiga, int ptos) {
        try {
            est = KoM.database.pegaConexao().createStatement();
            est.executeUpdate("update ptosPilhagem set ptos = " + ptos + " where minhaTag='" + minhaTag + "' and tagInimiga='" + tagInimiga + "'");
            //KnightsOfMania.database.pegaConexao().commit();
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                est.close();
                KoM.database.pegaConexao().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onDisable() {
        try {
            KoM.database.pegaConexao().close();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, null, ex);
        }
    }

    public static Clan getClanAt(Location l) {
        BookMeta bm = getBookMeta(l);
        if (bm == null) {
            return null;
        }
        if (bm.getTitle() == null || bm.getTitle().equals("WILD")) {
            return null;
        }
        return getClan(bm.getTitle());
    }

    public static String getOwnerAt(Location l) {
        BookMeta bm = getBookMeta(l);
        return bm.getAuthor();
    }

    public static int[] getChunkLocation(Location l) {
        l = l.getChunk().getBlock(0, 0, 0).getLocation();
        return new int[]{
            (int) l.getX() / 16, (int) l.getZ() / 16
        };
    }

    public static Location locOfChunk(String w, int x, int z) {
        return Bukkit.getWorld(w).getChunkAt(x * 16, z * 16).getBlock(0, 0, 0).getLocation();
    }

    public static void setOwnerAt(Location l, String owner) {
        if (owner == null) {
            owner = "none";
        }

        BookMeta bm = getBookMeta(l);
        bm.setAuthor(owner);
        bm.setPage(1, "");
        setBookMeta(l, bm);
        int[] xz = getChunkLocation(l);
        try {
            est = KoM.database.pegaConexao().createStatement();
            est.executeUpdate("UPDATE kk SET owner='" + owner + "' WHERE tag='" + bm.getTitle() + "' AND x='" + xz[0] + "' AND z='" + xz[1] + "'  AND port='" + Bukkit.getPort() + "'");
            //KnightsOfMania.database.pegaConexao().commit();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, null, ex);
        } finally {
            try {
                est.close();
                KoM.database.pegaConexao().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void addMemberAt(Location l, String friend) {
        BookMeta bm = getBookMeta(l);
        if (bm.getPage(1).isEmpty()) {
            bm.setPage(1, friend);
            setBookMeta(l, bm);
            return;
        }
        bm.setPage(1, bm.getPage(1) + "\n" + friend);
        setBookMeta(l, bm);
    }

    public static void removeMemberAt(Location l, String friend) {
        BookMeta bm = getBookMeta(l);
        String[] members = bm.getPage(1).split("\n");
        StringBuilder end = new StringBuilder();
        for (String member : members) {
            if (member.equals(friend)) {
                continue;
            }
            end.append(member);
            if (!members[members.length - 1].equals(member)) {
                end.append("\n");
            }
        }
        bm.setPage(1, end.toString());
        setBookMeta(l, bm);
    }

    public static boolean isMemberAt(Location l, String friend) {
        return getMembersAt(l).contains(friend);
    }

    public static void clearMembersAt(Location l) {
        BookMeta bm = getBookMeta(l);
        bm.setPage(1, "");
        setBookMeta(l, bm);
    }

    public static List<String> getMembersAt(Location l) {
        BookMeta bm = getBookMeta(l);
        return Arrays.asList(bm.getPage(1).split("\n"));
    }

    private static BookMeta getBookMeta(Location l) {
        Block b = l.getChunk().getBlock(0, 0, 0);
        if (!b.getType().equals(Material.CHEST)) {
            b.getRelative(BlockFace.UP).setType(Material.BEDROCK);
            b.setType(Material.CHEST);
        }
        BookMeta bm;
        Inventory inv = ((Chest) b.getState()).getBlockInventory();
        if (inv == null) {
            KoM.log.info("BOOOK SENDO NULL !!!!");
        }

        if (inv.getItem(0) == null || !inv.getItem(0).getType().equals(Material.WRITTEN_BOOK)) {
            log.log(Level.INFO, "creating info for chunk {0}, {1}", new Object[]{
                l.getChunk().getX(), l.getChunk().getZ()
            });
            inv.setItem(0, new ItemStack(Material.WRITTEN_BOOK));
            bm = (BookMeta) inv.getItem(0).getItemMeta();
            bm.setAuthor("none");
            bm.addPage("");
            bm.setTitle("WILD");
            inv.getItem(0).setItemMeta(bm);
        }
        return (BookMeta) inv.getItem(0).getItemMeta();
    }

    // metodo magico pra pegar o nivel do mob q nasce aki
    public static int getChunkDistanceFromSpawn(Location l) {
        return ((int) l.getChunk().getBlock(0, 0, 0).getLocation().distance(l.getWorld().getSpawnLocation().getChunk().getBlock(0, 0, 0).getLocation()) >> 4);
    }

    public static int getMobLevel(Location l) {
        if (l.getWorld().getEnvironment() == Environment.THE_END) {
            return 19;
        }
        ApplicableRegionSet set = KoM.worldGuard.getRegionManager(l.getWorld()).getApplicableRegions(l);
        if (set == null || set.size() == 0) {
            if (l.getWorld().getName().equalsIgnoreCase("dungeon")) {
                return 1;
            }
            int distancia = getChunkDistanceFromSpawn(l);
            return ((distancia >> 3) / 2);// / 2;
        } else {
            while (set.iterator().hasNext()) {
                ProtectedRegion regiao = set.iterator().next();
                if (regiao.getPriority() > 0 && regiao.getId().contains("mobspawn")) {
                    return regiao.getPriority();
                } else {
                    int distancia = getChunkDistanceFromSpawn(l);
                    return (distancia >> 3) / 2;
                }
            }
            return 0;
        }
    }

    public static boolean isSafeZone(Location l) {
        if (l.getWorld().getName().equalsIgnoreCase("vila")) {
            return true;
        }
        BookMeta bm = getBookMeta(l);
        if (bm == null || bm.getTitle() == null) {
            return false;
        }
        return bm.getTitle().equals("SAFE");
    }

    public static boolean isWarZone(Location l) {
        BookMeta bm = getBookMeta(l);
        if (bm == null || bm.getTitle() == null) {
            return false;
        }
        return bm.getTitle().equals("WARZ");
    }

    private static void setBookMeta(Location l, BookMeta bm) {
        Block b = l.getChunk().getBlock(0, 0, 0);
        Inventory inv = ((Chest) b.getState()).getBlockInventory();
        inv.getItem(0).setItemMeta(bm);
        if (bm.getTitle().equals("WILD")) {
            b.setType(Material.BEDROCK);
        }
    }

    public static void setClanAt(Location l, String clan) {
        BookMeta bm = getBookMeta(l);
        bm.setAuthor("none");
        bm.setTitle(clan);
        bm.setPage(1, "");
        setBookMeta(l, bm);
        if (KoM.debugMode) {
            KoM.log.info(l.toString());
            KoM.log.info(clan);
        }
        if (clan.equalsIgnoreCase("WILD") || clan.equalsIgnoreCase("SAFE") || clan.equalsIgnoreCase("WARZ")) {
            return;
        }
        int[] xz = getChunkLocation(l);
        try {
            est = KoM.database.pegaConexao().createStatement();
            est.executeUpdate("INSERT INTO kk (tag ,world, x , z ,owner, port) VALUES ("
                    + "'" + clan + "', '" + l.getWorld().getName() + "','" + xz[0] + "', '" + xz[1] + "', 'none', '" + Bukkit.getPort() + "')");
            //KnightsOfMania.database.pegaConexao().commit();
        } catch (SQLException ex) {
            KoM.log.info(ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                est.close();
                KoM.database.pegaConexao().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void removeClanAt(Location l) {
        try {
            est = KoM.database.pegaConexao().createStatement();
            l.getChunk().getBlock(0, 0, 0).setType(Material.BEDROCK);
            int[] xz = getChunkLocation(l);
            est.executeUpdate("DELETE FROM kk WHERE world='"
                    + l.getWorld().getName() + "' AND x='" + xz[0] + "' AND z='" + xz[1] + "'  AND port='" + Bukkit.getPort() + "'");

        } catch (SQLException ex) {
            KoM.log.info(ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                est.close();
                KoM.database.pegaConexao().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Clan getClan(String s) {
        Clan clan;
        clan = manager.getClan(s);
        if (clan != null) {
            return clan;
        }
        return null;
    }

    public static ClanPlayer getPlayer(String s) {
        return manager.getClanPlayer(s);
    }

    public static String getTypeAt(Location l) {
        if (l.getWorld().getName().equalsIgnoreCase("woe") || l.getWorld().getName().equalsIgnoreCase("arena")) {
            return "WARZ";
        }
        BookMeta bm = getBookMeta(l);
        Clan c = getClanAt(l);
        if (c == null) {
            if (isSafeZone(l)) {
                return "SAFE";
            } else if (isWarZone(l)) {
                return "WARZ";
            } else {
                return "WILD";
            }
        } else {
            return "CLAN";//c.getTag();
        }
    }

    public static void msg(CommandSender cs, String msg) {
        cs.sendMessage(ChatColor.RED + "[Kom] " + ChatColor.GREEN + msg);
    }
}
