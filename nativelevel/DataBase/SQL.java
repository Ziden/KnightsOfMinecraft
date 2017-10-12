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
package nativelevel.DataBase;

import com.google.common.base.Charsets;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.serial.SerialBlob;
import nativelevel.Auras.Aura;
import nativelevel.CFG;
import nativelevel.KoM;
import nativelevel.MetaShit;
import nativelevel.Attributes.AttributeInfo;
import nativelevel.Custom.Items.BussolaMagica.LocalBussola;
import nativelevel.Listeners.GeneralListener;
import nativelevel.blococomando.BlocoComando;
import nativelevel.sisteminhas.XP;
import nativelevel.utils.InventarioSerial;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SQL {

    private Connection connection;

    public static WeakHashMap<String, int[]> bufferDeNiveis = new WeakHashMap<String, int[]>();

    public static WeakHashMap<String, int[]> bufferSpecs = new WeakHashMap<String, int[]>();

    public static WeakHashMap<UUID, HashSet<Aura>> auras = new WeakHashMap<UUID, HashSet<Aura>>();

    private Statement est;

    public KoM plug;

    public SQL(KoM plug) {
        this.plug = plug;
    }

    public synchronized Connection pegaConexao() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = createConnection();
                connection.setAutoCommit(false);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return connection;
    }

    public void fechaConexao() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            KoM.log.log(Level.SEVERE, "[KoMLevel]", e);
        }
    }

    public String dbName = "kom";
    public static String connStr = "jdbc:mysql://localhost:3306/kom?autoReconnect=true";

    private Connection createConnection() {
        try {
            dbName = KoM.config.getConfig().getString("database.name");
            KoM.log.info("Attempting to Connect to Database: " + dbName);
            connStr = "jdbc:mysql://localhost:3306/kom?autoReconnect=true";
            if(KoM.serverTestes)
                connStr = "jdbc:mysql://localhost:3306/komtestes?autoReconnect=true";
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (InstantiationException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
            connection = DriverManager.getConnection(connStr, "root", KoM.camila);
            return connection;
        } catch (ClassNotFoundException e) {
            KoM.log.log(Level.SEVERE, "[KoMLevel] nao achei a lib", e);
            return null;
        } catch (SQLException e) {
            KoM.log.log(Level.SEVERE, "[KoMLevel]", e);
        }
        return null;
    }

    public void inicializa() {
        Connection conn;
        Statement st;
        try {
            conn = KoM.database.pegaConexao();
            if (conn == null) {
                KoM.log.log(Level.SEVERE,
                        "[KoM] CONXEAUMMM VEIO NUUUUL");
                plug.getServer().shutdown();
                return;
            }

            st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS CLASSES (jogador VARCHAR(200) PRIMARY KEY, skills VARCHAR(10), nome VARCHAR(50), slotsbanco INT, cabeca INT, resets INT, nivel INT);");
            st.close();

            st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS AURAS (jogador VARCHAR(200), aura VARCHAR(100))");
            st.close();

            st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS SPECS (jogador VARCHAR(200) PRIMARY KEY, skills VARCHAR(10));");
            st.close();

            st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS Karma (jogador VARCHAR(200) PRIMARY KEY, karma INTEGER, fama INTEGER);");
            st.close();

            st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS Almas (jogador VARCHAR(200) PRIMARY KEY, max INT, tem INT);");
            st.close();

            st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS Atributo (jogador VARCHAR(200) PRIMARY KEY, pontos INT, nivelMax int, str INT, dex INT, inte INT, cons INT, vit INT,agi INT, luck INT,wis INT, pres INT);");
            st.close();

            st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS Torres (tag VARCHAR(200) PRIMARY KEY, x INTEGER, y INTEGER, z INTEGER, world varchar(100));");
            st.close();

            st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS banco (uuid varchar(50) PRIMARY KEY, items BLOB);");
            st.close();

            st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS LOCAIS (nome varchar(100), local varchar(100), nivel integer);");
            st.close();

            st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS BLOCOS (local varchar(100) PRIMARY KEY, comandos varchar(300));");
            st.close();

            st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS LOOTS (uuid varchar(100) PRIMARY KEY, items BLOB);");
            st.close();

            st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS economy ("
                    + "uuid varchar(100) primary key, "
                    + "nick varchar(100), "
                    + "balance int, "
                    + "lastLogin bigint)");
            st.close();

            conn.commit();

            //conn.close();
        } catch (SQLException e) {
            KoM.log.log(Level.SEVERE,
                    "[KoM] Conexao com Banco Fechada");
            KoM.log.log(Level.SEVERE, "[KoM] {0}", e);
            KoM.safeMode = true;
            e.printStackTrace();
            for (int x = 0; x < 10; x++) {
                KoM.log.info("!!! DEU MERDA - SAFE MODE ON !!!");
            }
        }
    }

    public static List<LocalBussola> locais = null;

    public List<LocalBussola> getLocais() {
        if (locais != null && locais.size() > 0) {
            return locais;
        }
        List<LocalBussola> locs = new ArrayList<LocalBussola>();
        try {
            est = connection.createStatement();
            ResultSet rs = est.executeQuery("select nome, local, nivel from LOCAIS");
            while (rs.next()) {
                LocalBussola local = new LocalBussola();
                local.local = rs.getString("local");
                local.nivel = rs.getInt("nivel");
                local.nome = rs.getString("nome");
                locs.add(local);
            }
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
        }
        locais = locs;
        return locs;
    }

    public void addLocal(LocalBussola local) {
        try {
            est = connection.createStatement();
            //("insert into LOCAIS (nome, local, nivel) values ('"+local.nome+"', '"+local.local+"',"+local.nivel+")");
            est.execute("insert into LOCAIS (nome, local, nivel) values ('" + local.nome + "', '" + local.local + "'," + local.nivel + ")");
            connection.commit();
            locais.add(local);
            est.close();
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        }
    }

    public void updateNivel(Player p, int nivel) {
        try {
            est = connection.createStatement();
            //("insert into LOCAIS (nome, local, nivel) values ('"+local.nome+"', '"+local.local+"',"+local.nivel+")");
            est.execute("update CLASSES set nivel = " + nivel + " where jogador = '" + p.getUniqueId().toString() + "'");
            connection.commit();
            est.close();
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        }
    }

    public void removeLocal(LocalBussola local) {
        try {
            est = connection.createStatement();
            //("insert into LOCAIS (nome, local, nivel) values ('"+local.nome+"', '"+local.local+"',"+local.nivel+")");
            est.execute("delete from LOCAIS where nome = '" + local.nome + "'");
            connection.commit();
            locais.remove(local);
            est.close();

        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        }
    }

    /*
    
     BLOCOS DE COMANDO
    
     */
    public static List<BlocoComando> comandos = null;

    public List<BlocoComando> getBlocosComando() {
        if (comandos != null && comandos.size() > 0) {
            return comandos;
        }
        List<BlocoComando> locs = new ArrayList<BlocoComando>();
        try {
            est = connection.createStatement();
            ResultSet rs = est.executeQuery("select local, comandos from BLOCOS");
            while (rs.next()) {
                BlocoComando blocos = new BlocoComando();
                blocos.local = rs.getString("local");
                blocos.comandos = rs.getString("comandos").split("\\|");
                locs.add(blocos);
            }
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
        }
        comandos = locs;
        return locs;
    }

    public void addBloco(BlocoComando local) {
        try {
            est = connection.createStatement();
            //("insert into LOCAIS (nome, local, nivel) values ('"+local.nome+"', '"+local.local+"',"+local.nivel+")");
            String cmds = String.join("\\|", local.comandos);
            est.execute("insert into BLOCOS (comandos, local) values ('" + cmds + "', '" + local.local + "')");
            connection.commit();
            comandos.add(local);
            est.close();
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        }
    }

    public void removeBloco(BlocoComando local) {
        try {
            est = connection.createStatement();
            //("insert into LOCAIS (nome, local, nivel) values ('"+local.nome+"', '"+local.local+"',"+local.nivel+")");
            est.execute("delete from BLOCOS where local = '" + local.local + "'");
            connection.commit();
            locais.remove(local);
            est.close();

        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        }
    }

    public ItemStack[] getBanco(UUID u) {
        ResultSet rs = null;
        try {
            est = connection.createStatement();
            rs = est.executeQuery("select items from banco where uuid = '" + u.toString() + "'");
            if (rs.next()) {
                Blob b = rs.getBlob("items");
                if (b == null) {
                    return new ItemStack[]{};
                }
                byte[] bytes = b.getBytes(1, (int) b.length());
                b.free();
                rs.close();
                est.close();
                return InventarioSerial.deserializeItemStacks(bytes);
            } else {
                est = connection.createStatement();
                est.execute("insert into banco (uuid, items) values ('" + u.toString() + "', null);");
                est.close();
                return null;
            }
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public void loadLOOTS() {

        ResultSet rs = null;
        try {
            est = connection.createStatement();
            rs = est.executeQuery("select items, uuid from LOOTS");
            while (rs.next()) {
                Blob b = rs.getBlob("items");
                String uid = rs.getString("uuid");
                UUID u = UUID.fromString(uid);
                if (b == null) {

                }
                byte[] bytes = b.getBytes(1, (int) b.length());
                b.free();
                ItemStack[] items = InventarioSerial.deserializeItemStacks(bytes);
                GeneralListener.loots.put(u, Arrays.asList(items));
            }
            est = connection.createStatement();
            est.executeUpdate("delete from LOOTS");
            est.close();
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void setBanco(UUID u, ItemStack[] items) {
        try {
            Player pl = Bukkit.getPlayer(u);
            if (pl != null) {
                for (ItemStack ss : items) {
                    if (ss == null) {
                        continue;
                    }
                    if (ss.getType() == Material.BONE) {
                        ItemMeta meta = ss.getItemMeta();
                        if (meta.getDisplayName() != null && meta.getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Osso de Lobo")) {
                            pl.getEnderChest().setItem(0, ss);
                        }
                    }
                }
            }

            PreparedStatement pst = connection.prepareStatement("update banco set items = ? where uuid = '" + u.toString() + "'");
            pst.setBlob(1, new SerialBlob(InventarioSerial.serializeItemStacks(items)));
            pst.execute();
            pst.close();
            connection.commit();
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        }
    }

    public void setLoot(UUID u, ItemStack[] items) {
        try {

            PreparedStatement pst = connection.prepareStatement("insert into LOOTS (items, uuid) values (?,'"+u.toString()+"')");
            pst.setBlob(1, new SerialBlob(InventarioSerial.serializeItemStacks(items)));
            pst.execute();
            connection.commit();
            pst.close();
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        }
    }

    public static String getOfflineUUID(String p, boolean Withdashes) {
        if (Withdashes) {
            return java.util.UUID.nameUUIDFromBytes(("OfflinePlayer:" + p).getBytes(Charsets.UTF_8)).toString();
        } else {
            return java.util.UUID.nameUUIDFromBytes(("OfflinePlayer:" + p).getBytes(Charsets.UTF_8)).toString().replaceAll("-", "");
        }
    }

    public void wipePirata() {
        List<String> wiped = new ArrayList<String>();
        try {
            String sql = "SELECT IFNULL(nickname,'') as nickname, child FROM kom_session.permissions_inheritance P left join new_iconomy_1.money I on I.uuid = P.child where parent ='lord'";
            pegaConexao();
            est = connection.createStatement();
            ResultSet rs = est.executeQuery(sql);
            while (rs.next()) {
                String uuid = rs.getString("child");
                String nickName = rs.getString("nickname");
                if (nickName.equalsIgnoreCase("")) {
                    wiped.add(uuid);
                    continue;
                }
                String uuidPirata = getOfflineUUID(nickName, true);
                if (uuid.equalsIgnoreCase(uuidPirata)) {
                    wiped.add(uuid);
                }
            }
            if (rs != null) {
                rs.close();
            }
            est.close();
            // wipando 
            pegaConexao();
            for (String wipado : wiped) {
                est = connection.createStatement();
                est.executeUpdate("delete from kom_session.permissions_inheritance where child = '" + wipado + "'");
            }
            est.close();
            KoM.log.info("WIPEI " + wiped.size() + " nego !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remTower(String tag) {
        try {
            AttributeInfo info;
            est = connection.createStatement();
            est.executeUpdate("delete from Torres where tag = '" + tag + "'");
            est.close();
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        }
    }

    public void setTower(String tag, Location l) {
        try {
            AttributeInfo info;
            est = connection.createStatement();
            est.executeUpdate("insert into Torres (tag,x,y,z,world) values ('" + tag + "'," + l.getBlockX() + "," + l.getBlockY() + "," + l.getBlockZ() + ",'" + l.getWorld().getName() + "')");
            est.close();
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        }
    }

    public Location getTower(String tag) {
        try {
            est = connection.createStatement();
            ResultSet rs = est.executeQuery("select x,y,z,world from Torres where tag = '" + tag + "'");
            if (rs.next()) {
                return new Location(Bukkit.getWorld(rs.getString("world")), rs.getInt("x"), rs.getInt("y"), rs.getInt("z"));
            }
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        } finally {
            try {
                est.close();
            } catch (SQLException ex) {
                Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public void aprendeAura(UUID jogador, Aura aura) {
        try {
            est = connection.createStatement();
            if (auras.containsKey(jogador)) {
                auras.get(jogador).add(aura);
            } else {
                HashSet<Aura> ars = new HashSet<Aura>();
                ars.add(aura);
                auras.put(jogador, ars);
            }
            est.executeUpdate("insert into AURAS (aura, jogador) values ('" + aura.getNome() + "', '" + jogador.toString() + "')");
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        } finally {
            try {
                est.close();
            } catch (SQLException ex) {
                Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public HashSet<Aura> getAuras(UUID u) {
        ResultSet rs = null;
        try {
            if (auras.containsKey(u)) {
                return auras.get(u);
            }
            est = connection.createStatement();
            HashSet<Aura> lista = new HashSet<Aura>();
            rs = est.executeQuery("select aura from AURAS where jogador = '" + u.toString() + "'");
            while (rs.next()) {
                Aura a = Aura.getAura(rs.getString("aura"));
                if (a != null) {
                    lista.add(a);
                }
            }
            if (lista.size() > 0) {
                auras.put(u, lista);
            }
            return lista;
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                est.close();
            } catch (SQLException ex) {
                Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    private static HashMap<UUID, Integer> karmas = new HashMap<UUID, Integer>();

    public void setKarma(UUID u, int karma) {
        try {
            karmas.put(u, karma);
            AttributeInfo info;
            est = connection.createStatement();
            est.executeUpdate("update Karma set karma = " + karma + " where jogador = '" + u.toString() + "'");
            est.close();
            connection.commit();
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        }
    }

    public int getKarma(UUID u) {
        ResultSet rs = null;
        if (karmas.containsKey(u)) {
            return karmas.get(u);
        }
        try {
            AttributeInfo info;
            est = connection.createStatement();
            rs = est.executeQuery("select karma from Karma where jogador = '" + u.toString() + "'");
            if (rs.next()) {
                return rs.getInt("karma");
            } else {
                if (rs != null) {
                    rs.close();
                }
                est.close();
                est = connection.createStatement();
                est.executeUpdate("insert into Karma (karma, fama, jogador) values (0,0, '" + u.toString() + "');");
                return 0;
            }
        } catch (SQLException ex) {

            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                est.close();
            } catch (SQLException ex) {
                Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return -1;
    }

    public void setFama(UUID u, int karma) {
        try {
            AttributeInfo info;
            est = connection.createStatement();
            est.executeUpdate("update Karma set fama = " + karma + " where jogador = '" + u.toString() + "'");
            est.close();
            connection.commit();
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        }
    }

    public int getFama(UUID u) {
        ResultSet rs = null;
        try {
            AttributeInfo info;
            est = connection.createStatement();
            rs = est.executeQuery("select fama from Karma where jogador = '" + u.toString() + "'");
            if (rs.next()) {
                return rs.getInt("fama");
            } else {
                est.close();
                est = connection.createStatement();
                est.executeUpdate("insert into Karma (karma, , fama, jogador) values (0,0, '" + u.toString() + "');");
                return 0;
            }
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                est.close();
            } catch (SQLException ex) {
                Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return -1;
    }

    public void deletaConta(String nome) {
        ResultSet rs = null;
        try {
            AttributeInfo info;
            est = connection.createStatement();
            rs = est.executeQuery("select jogador from CLASSES where nome = '" + nome + "'");
            if (rs.next()) {
                est = connection.createStatement();
                est.executeUpdate("delete from Atributo where jogador = '" + rs.getString("jogador") + "'");
                est = connection.createStatement();
                est.executeUpdate("delete from CLASSES where nome = '" + nome + "'");

            }
            connection.commit();
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            try {
                est.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public HashMap<String, String> getUuidsAndNames() {
        ResultSet rs = null;
        HashMap<String, String> dore = new HashMap<String, String>();
        try {
            Connection conn;
            conn = pegaConexao();
            est = conn.createStatement();
            rs = est.executeQuery("SELECT jogador,nome FROM CLASSES");
            while (rs.next()) {
                dore.put(rs.getString("jogador"), rs.getString("nome"));
                KoM.log.info(UUID.fromString(rs.getString("jogador")).toString() + " - " + rs.getString("nome"));
                //KnightsOfMinecraft.log.info(Bukkit.getOfflinePlayer(UUID.fromString(rs.getString("jogador"))).getName());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            KoM.log.info("ERRO AO LER UID " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            try {
                est.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return dore;
    }

    public void changeMaxLevel(Player p, int qto) {
        try {
            AttributeInfo info;
            est = connection.createStatement();
            est.executeUpdate("update Atributo set nivelMax = " + qto + " where jogador='" + p.getUniqueId().toString() + "'");
            if (p.hasMetadata("atributo")) {
                info = (AttributeInfo) MetaShit.getMetaObject("atributo", p);
                info.maxLvl = qto;
            }
            connection.commit();
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        }
    }

    public void changePoints(Player p, int qto) {
        try {
            AttributeInfo info;
            est = connection.createStatement();
            est.executeUpdate("update Atributo set pontos= " + qto + " where jogador='" + p.getUniqueId().toString() + "'");
            if (p.hasMetadata("atributo")) {
                info = (AttributeInfo) MetaShit.getMetaObject("atributo", p);
                info.points = qto;
            }
            connection.commit();
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        }
    }

    public void resetPlayer(Player p) {
        try {
            AttributeInfo info;
            est = connection.createStatement();
            est.executeUpdate("update Atributo set nivelMax = 0, pontos = 0, str = 0, dex=0,inte=0,wis=0,luck=0,cons=0,vit=0,agi=0,pres=0 where jogador='" + p.getUniqueId().toString() + "'");
            p.removeMetadata("atributo", KoM._instance);
            connection.commit();
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        } finally {
            try {
                est.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void setResets(Player player, int ptos) {
        try {
            est = connection.createStatement();
            est.executeUpdate("update CLASSES set resets = " + ptos + " where jogador='" + player.getUniqueId().toString() + "'");
            connection.commit();
            MetaShit.setMetaObject("resets", player, ptos);
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        } finally {
            try {
                est.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public int getResets(Player jogador) {
        int ptos = 0;
        if (jogador.hasMetadata("resets")) {
            return (int) MetaShit.getMetaObject("resets", jogador);
        }
        try {
            est = connection.createStatement();
            ResultSet rs = est.executeQuery("select resets as tem from CLASSES where jogador='" + jogador.getUniqueId().toString() + "'");
            if (rs.next()) {
                int resets = rs.getInt("tem");
                MetaShit.setMetaObject("resets", jogador, resets);
                return resets;
            }
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        }
        return ptos;
    }

    public void setPrecoCabeca(String player, int ptos) {
        try {
            est = connection.createStatement();
            est.executeUpdate("update CLASSES set cabeca = " + ptos + " where jogador='" + player + "'");
            connection.commit();
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        }
    }

    public int restauraLevel(Player p) {
        ResultSet rs = null;
        int ptos = 0;
        try {
            est = connection.createStatement();
            rs = est.executeQuery("select A.nivelMax, C.cabeca, C.jogador from CLASSES C inner join Atributo A on A.jogador = C.jogador where C.jogador = '" + p.getUniqueId().toString() + "' and C.cabeca = 0;");
            if (rs.next()) {
                est = connection.createStatement();
                est.executeUpdate("update CLASSES set cabeca = 1 where jogador = '" + p.getUniqueId().toString() + "'");
                XP.setaLevel(p, rs.getInt("nivelMax"));
                connection.commit();
                KoM.log.info("Devolvi lvl player " + p.getName());
                p.teleport(CFG.spawnTree.toLocation());
            }
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                est.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ptos;
    }

    public void setAlmas(String player, int ptos) {
        try {
            est = connection.createStatement();
            est.executeUpdate("update Almas set tem = " + ptos + " where jogador='" + player + "'");
            connection.commit();
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        }
    }

    public int getAlmas(String jogador) {
        int ptos = 0;
        try {
            est = connection.createStatement();
            ResultSet rs = est.executeQuery("select tem from Almas where jogador='" + jogador + "'");
            if (rs.next()) {
                return rs.getInt("tem");
            } else {
                est = connection.createStatement();
                est.executeUpdate("insert into Almas (jogador, max, tem) values('" + jogador + "', 5, 5);");
                connection.commit();
                return 5;
            }
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        }
        return ptos;
    }

    public void setAlmasMax(String player, int ptos) {
        try {
            est = connection.createStatement();
            est.executeUpdate("update Almas set max = " + ptos + " where jogador='" + player + "'");
            connection.commit();
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                est.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int[] getAlmasEMax(String jogador) {
        int ptos = 0;
        ResultSet rs = null;
        try {
            est = connection.createStatement();
            rs = est.executeQuery("select tem, max from Almas where jogador='" + jogador + "'");
            if (rs.next()) {
                return new int[]{rs.getInt("tem"), rs.getInt("max")};
            } else {
                est = connection.createStatement();
                est.executeUpdate("insert into Almas (jogador, max, tem) values('" + jogador + "', 2, 2);");
                connection.commit();
                return new int[]{2, 2};
            }
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                est.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void setSlotsBanco(String player, int ptos) {
        try {
            est = connection.createStatement();
            est.executeUpdate("update CLASSES set slotsbanco = " + ptos + " where jogador='" + player + "'");
            connection.commit();
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        }
    }

    public int getSlotsBanco(String jogador) {
        int ptos = 0;
        ResultSet rs = null;
        try {
            est = connection.createStatement();
            rs = est.executeQuery("select slotsbanco from CLASSES where jogador='" + jogador + "'");
            if (rs.next()) {
                return rs.getInt("slotsbanco");
            }
        } catch (SQLException ex) {
            KoM.log.info("ZUOU BANCO:" + ex.getMessage());
            ex.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                est.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ptos;
    }

    public int[] getSkills(String name) {
        //name = name.toLowerCase();
        if (bufferDeNiveis.containsKey(name)) {
            return bufferDeNiveis.get(name);
        }
        ResultSet rs = null;
        String skills;
        int[] lvl = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2};
        try {
            est = pegaConexao().createStatement();
            rs = est.executeQuery("SELECT skills FROM CLASSES WHERE jogador=('" + name + "')");
            if (rs.next()) {
                skills = rs.getString(1);
                for (int x = 0; x < skills.length(); x++) {
                    lvl[x] = Integer.valueOf(String.valueOf(skills.charAt(x)));
                }
                bufferDeNiveis.put(name, lvl);
            }
            return lvl;
        } catch (SQLException e) {
            KoM.log.log(Level.SEVERE, "[LC] Unable to get row database{0}", e);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                est.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lvl;
    }

    public int[] getSpecs(String uuid) {
        if (bufferSpecs.containsKey(uuid)) {
            return bufferSpecs.get(uuid);
        }
        ResultSet rs = null;
        String skills;
        int[] lvl = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        try {
            est = pegaConexao().createStatement();
            rs = est.executeQuery("SELECT skills FROM SPECS WHERE jogador=('" + uuid + "')");
            if (rs.next()) {
                skills = rs.getString(1);
                for (int x = 0; x < skills.length(); x++) {
                    lvl[x] = Integer.valueOf(String.valueOf(skills.charAt(x)));
                }
                bufferSpecs.put(uuid, lvl);
            }
            //conn.close();
            return lvl;
        } catch (SQLException e) {
            KoM.log.log(Level.SEVERE, "[LC] Unable to get row database{0}", e);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                est.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lvl;
    }

    public void cadastraSpec(String name) {
        ResultSet rs = null;
        try {
            est = pegaConexao().createStatement();
            rs = est.executeQuery("SELECT skills FROM SPECS WHERE jogador=('" + name + "')");
            if (rs.next()) {
                return;
            }
            int[] niveis = {0, 0, 0, 0, 0, 0, 0};
            bufferSpecs.put(name, niveis);
            est = pegaConexao().createStatement();
            est.executeUpdate("INSERT INTO SPECS VALUES ('"
                    + name + "', '00000000')");
            pegaConexao().commit();
            //conn.close();
        } catch (SQLException e) {
            KoM.log.log(Level.SEVERE, "[LC] Erro ao cadastrar jogador {0} - {1}", new Object[]{name, e});
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                est.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void atualizaSpecs(String name, int[] skills) {
        ResultSet rs = null;
        Connection conn;
        StringBuffer skillFinal = new StringBuffer();
        if (bufferSpecs.containsKey(name)) {
            bufferSpecs.remove(name);
        }
        bufferSpecs.put(name, skills);
        for (int sk : skills) {
            skillFinal.append(sk);
        }
        try {
            conn = pegaConexao();
            est = conn.createStatement();
            est.executeUpdate("UPDATE SPECS set skills = '"
                    + skillFinal + "' WHERE jogador='" + name + "'");
            conn.commit();
        } catch (SQLException e) {
            KoM.log.log(Level.SEVERE, "[KoM] Update no banco fail{0}", e);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (est != null) {
                    est.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void atualiza(String name, int[] skills) {
        //  //name = name.toLowerCase();
        Connection conn;
        StringBuffer skillFinal = new StringBuffer();
        if (bufferDeNiveis.containsKey(name)) {
            bufferDeNiveis.remove(name);
        }
        bufferDeNiveis.put(name, skills);
        for (int sk : skills) {
            skillFinal.append(sk);
        }
        try {
            conn = pegaConexao();
            est = conn.createStatement();
            est.executeUpdate("UPDATE CLASSES set skills = '"
                    + skillFinal + "' WHERE jogador='" + name + "'");
            conn.commit();
            //conn.close();
        } catch (SQLException e) {
            KoM.log.log(Level.SEVERE, "[KoM] Update no banco fail{0}", e);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        } finally {
            try {
                est.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean hasRegisteredClass(String name) {

        boolean isTrue = false;
        try {

            est = pegaConexao().createStatement();
            ResultSet rs = est.executeQuery("SELECT jogador FROM CLASSES WHERE jogador=('"
                    + name + "')");
            if (bufferDeNiveis.containsKey(name)) {
                return true;
            } else if (rs.next()) {
                isTrue = true;
                bufferDeNiveis.put(name, getSkills(name));
            }
            pegaConexao().commit();
            //conn.close();
            return isTrue;
        } catch (SQLException e) {
            KoM.log.log(Level.SEVERE, "[KoM] Erro no banco {0}", e);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        } finally {
            try {
                est.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isTrue;
    }

    public boolean jaTemNome(String name) {

        ResultSet rs = null;
        boolean isTrue = false;
        try {

            est = pegaConexao().createStatement();
            rs = est.executeQuery("SELECT jogador FROM CLASSES WHERE nome=('"
                    + name + "')");
            if (rs.next()) {
                return true;
            }
            //conn.close();
            return isTrue;
        } catch (SQLException e) {
            KoM.log.log(Level.SEVERE, "[KoM] Erro no banco {0}", e);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                est.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isTrue;
    }

    public void cadastra(String name, String playerName) {
        int[] niveis = {0, 0, 0, 0, 0, 0, 0};
        bufferDeNiveis.put(name, niveis);
        try {
            est = pegaConexao().createStatement();
            est.executeUpdate("INSERT INTO CLASSES VALUES ('"
                    + name + "', '00000000', '" + playerName + "', 0,1,0,1)");
            pegaConexao().commit();
        } catch (SQLException e) {
            KoM.log.log(Level.SEVERE, "[LC] Erro ao cadastrar jogador {0} - {1}", new Object[]{name, e});
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Reiniciando rapidamente !");
        } finally {
            try {
                est.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
