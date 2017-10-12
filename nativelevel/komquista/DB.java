package nativelevel.komquista;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nativelevel.KoM;

public class DB {

    private static Connection conn = null;

    public static void createTables() {
        try {
            conn.createStatement().execute("CREATE TABLE IF NOT EXISTS komquistas (Date date,tag VARCHAR(10))");
            conn.createStatement().execute("CREATE TABLE IF NOT EXISTS glds (tag VARCHAR(10) PRIMARY KEY,qtd Integer)");
            conn.createStatement().execute("CREATE TABLE IF NOT EXISTS last (tag VARCHAR(10))");
            conn.createStatement().execute("CREATE TABLE IF NOT EXISTS holograms (id INTEGER PRIMARY KEY AUTO_INCREMENT,loc VARCHAR(100))");
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void removeWins(String tag) {
        try {
            conn.createStatement().execute("DELETE FROM komquistas WHERE tag = '" + tag + "'");
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void removelast() {
        try {
            conn.createStatement().execute("DELETE FROM last");
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean hasLastWinner() {
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM last");
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void setLast(String tag) {
        try {
            if (!hasLastWinner()) {
                conn.createStatement().execute("INSERT INTO last (`tag`) VALUES('" + tag + "')");
            } else {
                conn.createStatement().executeUpdate("UPDATE last SET tag = '" + tag + "'");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static List<String> getWinners() {
        List<String> guildas = new ArrayList();
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * from glds ORDER BY `qtd` DESC");
            while (rs.next()) {
                guildas.add(rs.getString("tag") + ";" + rs.getInt("qtd"));
            }

            return guildas;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return guildas;
    }

    public static boolean removeLoc(int id) {
        try {
            return conn.createStatement().executeUpdate("DELETE FROM holograms WHERE id = " + id + "") > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static String getLast() {
        try {
            if (!hasLastWinner()) {
                return null;
            }
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM last");
            if (rs.next()) {
                return rs.getString("tag");
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void addWin(String tag) {
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT qtd FROM glds WHERE tag ='" + tag + "'");
            if (!rs.next()) {
                conn.createStatement().execute("INSERT INTO glds (`tag`,`qtd`) VALUES('" + tag + "','1')");
            } else {
                int tem = rs.getInt("qtd") + 1;
                conn.createStatement().executeUpdate("UPDATE glds SET qtd = '" + tem + "' where tag = '" + tag + "'");
            }
            conn.createStatement().execute("INSERT INTO komquistas (`date`,`tag`) VALUES(CURRENT_DATE,'" + tag + "')");
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void startDatabase() {
        if (conn == null) {
            createConnection();
            createTables();
        }
    }

    public static String getWinOfDay(Date d) {
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT FROM komquistas WHERE `data` = '" + d + "'");
            if (rs.next()) {
                return rs.getString("tag");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static int getWinsOfTag(String tag) {
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM komquistas WHERE `tag` = '" + tag + "'");
            int i = 0;
            while (rs.next()) {
                i++;
            }
            return i;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public static String dbName = "kom";
    public static String connStr = "jdbc:mysql://localhost:3306/kom?autoReconnect=true";

    private static Connection createConnection() {
        try {
            connStr = "jdbc:mysql://localhost:3306/" + dbName + "?autoReconnect=true";
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (InstantiationException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
            conn = DriverManager.getConnection(connStr, "root", KoM.camila);
            conn.setAutoCommit(true);

            return conn;
        } catch (ClassNotFoundException e) {
            KomQuista.log.info("[KoMLevel] nao achei a lib");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            KomQuista.log.info("[KoMLevel] sql");
            e.printStackTrace();
        }
        return null;
    }
}
