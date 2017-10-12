package nativelevel.DataBase.API;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import nativelevel.KoM;
import nativelevel.mercadinho.common.MarketItem;
import nativelevel.mercadinho.database.MercadoSQL;
import static nativelevel.mercadinho.database.MercadoSQL.connection;
import static nativelevel.mercadinho.database.MercadoSQL.orderby;
import nativelevel.mercadinho.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Ziden
 */
public class DatabaseAPI {

    /*
    public static void atualiza(Connection conn, String tabela, String where, Dados dados) {
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            String sql = "update "+tabela;
            for(String coluna : dados.getKeys()) {
                sql += " set "+coluna
            }
            rs = statement.executeQuery(query);
            ResultSetMetaData meta = rs.getMetaData();
        } catch (Exception ex) {
            ErroMysql(ex);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
               // KnightsOfMania._instance.getServer().shutdown();
            }
        }
    }
    */
    
    public static Dados getDado(Connection connection, String query) {

        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            ResultSetMetaData meta = rs.getMetaData();
            int colunas = meta.getColumnCount();
            while (rs.next()) {
                Dados dado = new Dados();
                for (int x = 0; x < colunas; x++) {
                    String nomecoluna = meta.getColumnName(x);
                    dado.add(nomecoluna, rs.getObject(nomecoluna));
                }
                return dado;
            }
        } catch (Exception ex) {
            ErroMysql(ex);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
               // KnightsOfMania._instance.getServer().shutdown();
            }
        }
        return null;
    }

    public static List<Dados> getLinhas(Connection connection, String query) {
        List<Dados> dados = new ArrayList();
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            ResultSetMetaData meta = rs.getMetaData();
            int colunas = meta.getColumnCount();
            while (rs.next()) {
                Dados dado = new Dados();
                for (int x = 0; x < colunas; x++) {
                    String nomecoluna = meta.getColumnName(x);
                    dado.add(nomecoluna, rs.getObject(nomecoluna));
                }
                dados.add(dado);
            }
        } catch (Exception ex) {
            ErroMysql(ex);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
               // KnightsOfMania._instance.getServer().shutdown();
            }
        }
        return dados;
    }

    public static void ErroMysql(Exception e) {
        //Fazer oq acontece quando da erro no mysql
        Utils.AddLog("ERRO MYSQL");
        e.printStackTrace();
    }

}
