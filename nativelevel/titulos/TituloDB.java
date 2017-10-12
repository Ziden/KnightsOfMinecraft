package nativelevel.titulos;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import nativelevel.KoM;
import nativelevel.playerboolean.Stage;
import nativelevel.playerboolean.StageDB;
import nativelevel.rankings.Estatistica;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author vntgasl
 */
public class TituloDB {

    public static long semana = 0;

    static String table = "CREATE TABLE IF NOT EXISTS Players (id INTEGER AUTO_INCREMENT PRIMARY KEY,name VARCHAR(32),uuid VARCHAR(36),sexo boolean, titulo varchar(20) default null,cortitulo varchar(20) default null)";
    public static List<UUID> firstlogin = new ArrayList();

    public static void init() {
        try {
            KoM.database.pegaConexao().createStatement().executeUpdate(table);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static PData getPlayerData(Player p) {
        ResultSet rs = null;
        Statement st = null;
        try {
            st = KoM.database.pegaConexao().createStatement();
            rs = st.executeQuery("SELECT * FROM Players where uuid='" + p.getUniqueId() + "'");
            if (rs.next()) {
                ChatColor cor = ChatColor.WHITE;
                if (rs.getString("cortitulo") != null) {
                    cor = ChatColor.valueOf(rs.getString("cortitulo"));
                }
                return new PData(rs.getString("name"), p.getUniqueId(), rs.getBoolean("sexo") ? Sexo.MULHER : Sexo.HOMEM, rs.getString("titulo"), cor, rs.getInt("id"));
            } else {
                KoM.database.pegaConexao().createStatement().executeUpdate("INSERT INTO Players (uuid,name,sexo) VALUES('" + p.getUniqueId().toString() + "','" + p.getName() + "',0);");
                firstlogin.add(p.getUniqueId());
                return new PData(p.getName(), p.getUniqueId(), Sexo.HOMEM, "", null, -1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    if (st != null) {
                        st.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(TituloDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        System.out.println("DEU PAU NO BANCOOOOOOOOOOOOOOOOOO!");
        return null;
    }

    public static class PData {

        String name;
        UUID uid;
        Sexo s;
        String titulo;
        ChatColor cortitul;
        int id;

        public ChatColor getCortitulo() {
            return cortitul;
        }

        public Sexo getSexo() {
            return s;
        }

        public PData(String name, UUID uid, Sexo s, String titulo, ChatColor cortitulo, int id) {
            this.name = name;
            this.uid = uid;
            this.s = s;
            this.titulo = titulo;
            this.id = id;
            this.cortitul = cortitulo;
            if (this.cortitul == null) {
                cortitul = ChatColor.WHITE;
            }
        }

        public void setSexo(Sexo s) {
            this.s = s;
            try {
                String se = s == Sexo.HOMEM ? "0" : "1";
                KoM.database.pegaConexao().createStatement().executeUpdate("UPDATE Players set sexo = " + se + " where id =" + id);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        public void setTitulo(String titulo, ChatColor cor) {
            this.titulo = titulo;
            this.cortitul = cor;
            try {
                KoM.debug("ATT TITULO");
                KoM.database.pegaConexao().createStatement().executeUpdate("UPDATE Players set titulo ='" + titulo + "',cortitulo ='" + cor.name() + "' where id =" + id);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        public UUID getUUID() {
            return uid;
        }

        public String getTitulo() {
            Player p = Bukkit.getPlayer(uid);
            if (p != null) {
                if (this.getSexo() == Sexo.MULHER) {
                    return this.getSexo().feminiza(titulo);
                }
            }
            return titulo;
        }

        public String getTitulo(boolean feminiza) {
            Player p = Bukkit.getPlayer(uid);
            if (p != null) {
                if (this.getSexo() == Sexo.MULHER) {
                    return this.getSexo().feminiza(titulo);
                }
            }
            return titulo;
        }

        public String getName() {
            return name;
        }
    }

    public static void InitMysql() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            PreparedStatement statement = KoM.database.pegaConexao().prepareStatement("CREATE TABLE IF NOT EXISTS TitulosNew (uuid varchar(200), titulo varchar(200), cor varchar(20), PRIMARY KEY(uuid,titulo,cor));");
            statement.executeUpdate();

            init();

            if (!KoM.database.pegaConexao().isValid(100)) {
                throw new SQLException();
            }
        } catch (Exception e) {

            ErroMysql(e);
        }
    }

    private static HashMap<UUID, HashMap<String, List<ChatColor>>> cacheTitulos = new HashMap<UUID, HashMap<String, List<ChatColor>>>();

    public static void clearRankings() {
        Statement statement = null;
        try {
            for (Estatistica stat : Estatistica.values()) {
                statement = KoM.database.pegaConexao().createStatement();
                statement.executeUpdate("delete from TitulosNew where titulo = '" + stat.titulo + "'");
            }
        } catch (SQLException ex) {
            ErroMysql(ex);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(TituloDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void addTitulo(UUID p, String titulo, ChatColor cor, boolean send) {
        Statement statement = null;

        try {
            HashMap<String, List<ChatColor>> titulos = getTitulos(p);
            if (titulos.containsKey(titulo)) {
                if (titulos.get(titulo).contains(cor)) {
                    return;
                }
            }
            statement = KoM.database.pegaConexao().createStatement();
            statement.executeUpdate("insert into TitulosNew (uuid, titulo,cor) values ('" + p.toString() + "', '" + titulo + "','" + cor.name() + "') ;");
            if (titulos.containsKey(titulo)) {
                titulos.get(titulo).add(cor);
            } else {
                ArrayList list = new ArrayList();
                list.add(cor);
                titulos.put(titulo, list);
            }
            cacheTitulos.put(p, titulos);
            Player pl = Bukkit.getPlayer(p);
            if (pl != null && send) {
                pl.sendMessage(ChatColor.GREEN + "Parabens , voce ganhou o titulo " + cor + titulo + ChatColor.GREEN + " !!!!");
                pl.sendMessage(ChatColor.GREEN + "Digite " + ChatColor.YELLOW + "/titulo" + ChatColor.GREEN + " para ver e editar seus titulos !");
                boolean jaTem = StageDB.getPlayerStage(pl).stagesCompleted.contains(Stage.PredefinedStages.ESCOLHEUSEXO);
                if (!jaTem) {
                    pl.sendMessage(ChatColor.GREEN + "Digite " + ChatColor.YELLOW + "/genero" + ChatColor.GREEN + " para escolher seu sexo (uma vez apenas) !");
                }
            }
        } catch (SQLException ex) {
            ErroMysql(ex);
        } finally {

            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(TituloDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void addTitulo(UUID p, String titulo, ChatColor cor) {
        addTitulo(p, titulo, cor, true);
    }

//       titulo     list cor
    public static HashMap<String, List<ChatColor>> getTitulos(UUID p) {

        KoM.debug("GET TITULOS " + p);
        HashMap<String, List<ChatColor>> titulos = new HashMap<String, List<ChatColor>>();
        if (cacheTitulos.containsKey(p)) {
            return (HashMap<String, List<ChatColor>>) cacheTitulos.get(p);
        }
        Statement statement = null;
        ResultSet rs = null;
        try {
            // dando os top 1 os titulos  
            statement = KoM.database.pegaConexao().createStatement();
            rs = statement.executeQuery("select titulo, cor from TitulosNew where uuid = '" + p.toString() + "'");
            while (rs.next()) {
                String titulo = rs.getString("titulo");
                if (titulos.containsKey(titulo)) {
                    titulos.get(titulo).add(ChatColor.valueOf(rs.getString("cor")));
                } else {
                    ArrayList list = new ArrayList();
                    list.add(ChatColor.valueOf(rs.getString("cor")));
                    titulos.put(rs.getString("titulo"), list);
                }
            }
        } catch (SQLException ex) {
            ErroMysql(ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(TituloDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return titulos;
    }

    public static void ErroMysql(Exception e) {
        //Fazer oq acontece quando da erro no mysql
        e.printStackTrace();
    }
}
