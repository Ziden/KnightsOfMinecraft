package nativelevel.mercadinho.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import nativelevel.KoM;
import nativelevel.Lang.LangMinecraft;
import nativelevel.mercadinho.common.MarketItem;
import nativelevel.mercadinho.utils.Utils;
import nativelevel.utils.CategoriaUtils.CategoriaItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Gabriel
 * 
 */

public class MercadoSQL {

    public static String url = "jdbc:mysql://localhost:3306/";
    public static String user = "root";
    public static Connection connection;

    public static void InitMysql() {
        try {
            url = url + "kom";
            MercadoSQL.ConnectMySQL();

            PreparedStatement statement = connection.prepareStatement("CREATE TABLE if not exists PRODUTOS (\n"
                    + " `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + " `owner` varchar(50) NOT NULL,\n"
                    + " `name` varchar(50) NOT NULL,\n"
                    + " `preco` double NOT NULL,\n"
                    + " `cash` int NOT NULL DEFAULT 0,\n"
                    + " `nickowner` varchar(50) NOT NULL,\n"
                    + " `itemstack` blob NOT NULL,\n"
                    + " `data` date DEFAULT NULL,\n"
                    + " `customname` varchar(255) NOT NULL DEFAULT '',\n"
                    + " `quantidade` int(11) NOT NULL DEFAULT '1',\n"
                    + " `pt_BR` varchar(255) NOT NULL DEFAULT '',\n"
                    + " `local` varchar(255) NOT NULL DEFAULT '',\n"
                    + " `tipo` varchar(255) NOT NULL DEFAULT '',\n"
                    + " PRIMARY KEY (`id`)\n"
                    + ") ENGINE=InnoDB AUTO_INCREMENT=3066 DEFAULT CHARSET=latin1");
            statement.executeUpdate();

            statement.close();
            statement = connection.prepareStatement("CREATE TABLE if not exists RETORNO (uuid varchar(100) PRIMARY KEY, qtd INTEGER)");
            statement.executeUpdate();
            statement.close();

        } catch (Exception e) {
            ErroMysql(e);
            Bukkit.getServer().shutdown();
        }
    }

    public static void zeraRetorno(Player p) {
        int r = 0;
        Statement st = null;
        ResultSet rs = null;
        try {
            st = connection.createStatement();
            // da o saldo pra ele
            st.executeUpdate("delete from RETORNO where uuid = '" + p.getUniqueId().toString() + "';");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int getRetorno(Player p) {
        int r = 0;
        Statement st = null;
        ResultSet rs = null;
        try {
            MercadoSQL.ConnectMySQL();
            st = connection.createStatement();
            rs = st.executeQuery("select qtd from RETORNO where uuid = '" + p.getUniqueId() + "'");
            if (rs.next()) {
                return rs.getInt("qtd");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return r;
    }

    public static int addRetorno(UUID p, int qtd) {
        int r = 0;
        Statement st = null;
        ResultSet rs = null;
        try {
            MercadoSQL.ConnectMySQL();
            st = connection.createStatement();
            // se o kra nao tem nada em saldo
            rs = st.executeQuery("select qtd from RETORNO where uuid = '" + p.toString() + "'");
            if (!rs.next()) {
                st.close();
                rs.close();
                st = connection.createStatement();
                // da o saldo pra ele
                st.executeUpdate("insert into RETORNO (uuid, qtd) values ('" + p.toString() + "', " + qtd + ");");
            } else {
                // se o kra ja tinha um saldo no mercado
                int saldoAtual = rs.getInt("qtd");
                saldoAtual = saldoAtual + qtd; // adicionando saldo pro kra
                // salvando
                st.close();
                rs.close();
                st = connection.createStatement();
                st.executeUpdate("update RETORNO set qtd = " + saldoAtual + " where uuid = '" + p.toString() + "'");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return r;
    }

    public static void ConnectMySQL() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            if (connection == null) {
                connection = DriverManager.getConnection(url, user, KoM.camila);
                if (!connection.isValid(100)) {
                    Bukkit.getServer().shutdown();
                    throw new Exception();
                }
            } else if (connection.isClosed()) {
                connection = DriverManager.getConnection(url, user, KoM.camila);
                if (!connection.isValid(100)) {
                    Bukkit.getServer().shutdown();
                    throw new Exception();
                }
            }
            connection.setAutoCommit(true);
        } catch (Exception ex) {
            ErroMysql(ex);
            Bukkit.getServer().shutdown();
        }
    }

    //SELECT * FROM produtos where data+interval 2 day < now(); PEGA VENCIDOS
    public static boolean InserirProduto(MarketItem item) {
        if (item.ItemStack.getType() == Material.AIR) {
            return false;
        }
        try {
            MercadoSQL.ConnectMySQL();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO PRODUTOS (owner, name, preco, nickowner, itemstack,customname,quantidade,pt_BR,data, tipo,cash) VALUES (?,?,?,?,?,?,?,?, NOW(), ?,?);");
            statement.setString(1, item.dono.toString());
            statement.setString(2, item.ItemStack.getType().name());
            statement.setDouble(3, item.Valor);
            statement.setString(4, item.nickdono);

            statement.setBlob(5, Utils.BytesToBlob(Utils.serializeItemStacks(item.ItemStack)));
            if (item.ItemStack.hasItemMeta() && item.ItemStack.getItemMeta().hasDisplayName()) {
                statement.setString(6, ChatColor.stripColor(item.ItemStack.getItemMeta().getDisplayName()));
            } else {
                statement.setString(6, "");

            }
            statement.setInt(7, item.ItemStack.getAmount());
            statement.setString(8, LangMinecraft.get().get(item.ItemStack));
            statement.setString(9, item.categoria.name());
            statement.setInt(10, item.cash ? 1 : 0);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (Exception ex) {
            ErroMysql(ex);
            return false;
        }
    }

    static String vencido = "(data+interval 7 day) > now()";

    public static boolean RemoverProduto(MarketItem item) {
        try {
            MercadoSQL.ConnectMySQL();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM PRODUTOS WHERE owner=? AND id=? LIMIT 1;");
            statement.setString(1, item.dono.toString());
            statement.setInt(2, item.ID);
            statement.executeUpdate();
            return true;
        } catch (Exception ex) {
            ErroMysql(ex);
            return false;
        }
    }

    public static boolean RemoverProdutoOld(MarketItem item) {
        try {
            MercadoSQL.ConnectMySQL();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM PRODUTOS WHERE owner=? AND name=? AND preco=? AND itemstack=? LIMIT 1;");
            statement.setString(1, item.dono.toString());
            statement.setString(2, item.ItemStack.getType().name());
            statement.setDouble(3, item.Valor);
            statement.setBlob(4, Utils.BytesToBlob(Utils.serializeItemStacks(item.ItemStack)));
            statement.executeUpdate();
            return true;
        } catch (Exception ex) {
            ErroMysql(ex);
            return false;
        }
    }

    public static int QuantidadeProdutos(Player p) {
        try {
            MercadoSQL.ConnectMySQL();
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM PRODUTOS WHERE owner = ?");
            statement.setString(1, p.getUniqueId().toString());
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (Exception ex) {
            ErroMysql(ex);
            return 0;
        }
    }

    public static boolean ExisteProdutoOld(MarketItem item) {
        try {
            MercadoSQL.ConnectMySQL();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PRODUTOS WHERE owner=? AND name=? AND preco=? AND itemstack=? LIMIT 1;");
            statement.setString(1, item.dono.toString());
            statement.setString(2, item.ItemStack.getType().name());
            statement.setDouble(3, item.Valor);
            statement.setBlob(4, Utils.BytesToBlob(Utils.serializeItemStacks(item.ItemStack)));
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (Exception ex) {
            ErroMysql(ex);
            return false;
        }
    }

    public static boolean ExisteProduto(MarketItem item) {
        try {
            MercadoSQL.ConnectMySQL();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PRODUTOS WHERE id=? LIMIT 1;");
            statement.setInt(1, item.ID);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (Exception ex) {
            ErroMysql(ex);
            return false;
        }
    }

    public static String orderby = "ORDER BY preco ASC";

    public static List<MarketItem> CarregaTodosProdutosCatalogo(CategoriaItem categoria) {
        List<MarketItem> ListaProdutos = new ArrayList();
        try {
            MercadoSQL.ConnectMySQL();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM PRODUTOS WHERE " + vencido + " and tipo = '" + categoria.name() + "' " + orderby;
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {

                ItemStack item = Utils.deserializeItemStacks(Utils.BlobToBytes(rs.getBlob("itemstack")));

                /*
                 if (item.getType() != Material.COBBLESTONE
                 && item.getType() != Material.DIRT
                 && item.getType() != Material.SAND
                 && item.getType() != Material.SAPLING
                 && item.getType() != Material.EMERALD
                 && item.getType() != Material.EMERALD_BLOCK
                 && item.getType() != Material.WOOD_SWORD
                 && item.getType() != Material.WOOD_PICKAXE
                 && item.getType() != Material.WOOD_SPADE
                 && item.getType() != Material.WOOD_AXE
                 && item.getType() != Material.WOOD_HOE
                 && item.getType() != Material.STICK) {
                 */
                ListaProdutos.add(new MarketItem(UUID.fromString(rs.getString("owner")), item, rs.getDouble("preco"), rs.getString("nickowner"), rs.getInt("id"), rs.getInt("cash") == 1 ? true : false));
                //}
            }
        } catch (Exception ex) {
            ErroMysql(ex);
        }
        return ListaProdutos;
    }

    public static void DevolveItem(final Player p) {
        if (p != null && p.isOnline()) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        int retorno = MercadoSQL.getRetorno(p);
                        if (retorno > 0) {
                            p.sendMessage("§2[Mercado] §6Você tem dinheiro no mercado!");
                        }
                        Statement statement = connection.createStatement();
                        String sql = "SELECT 1 FROM PRODUTOS WHERE owner='" + p.getUniqueId().toString() + "' and (data+interval 2 day) <=now();";
                        ResultSet rs = statement.executeQuery(sql);
                        if (rs.next()) {
                            p.sendMessage("§2[Mercado] §6Você tem itens vencidos no mercado !");
                        }
                    } catch (Exception ex) {
                        ErroMysql(ex);
                    }

                }
            }.start();
        }
    }

    public static List<MarketItem> CarregaProdutosPorNick(String nick, boolean self) {
        List<MarketItem> ListaProdutos = new ArrayList();
        try {
            MercadoSQL.ConnectMySQL();
            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM PRODUTOS where nickowner LIKE '" + nick + "' ";
            if (!self) {
                sql += "and " + vencido + " ";

            }
            sql += orderby;
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                ItemStack item = Utils.deserializeItemStacks(Utils.BlobToBytes(rs.getBlob("itemstack")));
                MarketItem mi = new MarketItem(UUID.fromString(rs.getString("owner")), item, rs.getDouble("preco"), rs.getString("nickowner"), rs.getInt("id"), rs.getInt("cash") == 1 ? true : false);
                Calendar c = Calendar.getInstance();
                c.setTime(rs.getDate("data"));
                c.add(Calendar.DATE, 2);

                if (new Date().after(c.getTime())) {
                    mi.setVencido(true);
                }
                ListaProdutos.add(mi);
            }
        } catch (Exception ex) {
            ErroMysql(ex);
        }
        return ListaProdutos;
    }

    public static List<MarketItem> CarreProdutosPorNome(String nome) {
        List<MarketItem> ListaProdutos = new ArrayList();
        try {
            MercadoSQL.ConnectMySQL();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM PRODUTOS where (name LIKE '%" + nome + "%' OR customname LIKE '%" + nome + "%' or pt_BR LIKE '%" + nome + "%') and " + vencido + " " + orderby + ";";

            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                ItemStack item = Utils.deserializeItemStacks(Utils.BlobToBytes(rs.getBlob("itemstack")));
                MarketItem mi = new MarketItem(UUID.fromString(rs.getString("owner")), item, rs.getDouble("preco"), rs.getString("nickowner"), rs.getInt("id"), rs.getInt("cash") == 1 ? true : false);

                ListaProdutos.add(mi);
            }
        } catch (Exception ex) {
            ErroMysql(ex);
        }
        return ListaProdutos;
    }

    public static void ErroMysql(Exception e) {
        //Fazer oq acontece quando da erro no mysql
        Utils.AddLog("ERRO MYSQL");
        e.printStackTrace();
    }

}
