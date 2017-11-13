package nativelevel.ajuda.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Supplier;
import java.util.logging.Level;
import nativelevel.KoM;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class DBDefault
{
  private Connection connection;
  private Statement st;
  
  public void iniciarBanco()
  {
    Bukkit.getLogger().log(Level.INFO, "{0}DATABASE", ChatColor.DARK_AQUA);
    try
    {
      this.connection = getConnection();
      this.st = this.connection.createStatement();
      this.st.executeUpdate("create table if not exists Pixel_Perguntas (id MEDIUMINT NOT NULL AUTO_INCREMENT primary key, UUID varchar(100), nome varchar(50), pergunta TEXT, servidor varchar(50))");
      
      this.st = this.connection.createStatement();
      
      this.st.executeUpdate("create table if not exists Pixel_Respostas (numero MEDIUMINT NOT NULL AUTO_INCREMENT primary key,UUID varchar(100), nome varchar(50), id mediumint, pergunta text, resposta text, playerOffline varchar(100), servidor varchar(50))");
    }
    catch (SQLException ex)
    {
      Bukkit.getLogger().info("ERRO: Conexao ao banco de dados MySQL falhou!");
      Bukkit.getLogger().info("ERRO: " + ex);
    }
  }
  
  public Connection criarConnection()
    throws SQLException
  {
    try
    {
      String endereco = "jdbc:mysql://149.56.29.230:3306/bungee?autoReconnect=true";
      try
      {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
      }
      catch (InstantiationException|IllegalAccessException ex)
      {
        Bukkit.getLogger().info("ERRO: Conexao ao banco de dados MySQL falhou!");
        Bukkit.getLogger().info("ERRO: " + ex);
      }
      this.connection = DriverManager.getConnection(endereco, "root", KoM.camila);
      return this.connection;
    }
    catch (ClassNotFoundException e)
    {
      Bukkit.getLogger().info("ERRO: Conexao ao banco de dados MySQL falhou!");
      Bukkit.getLogger().info("ERRO: " + e.toString());
      Bukkit.getLogger().log(Level.SEVERE, (Supplier)e);
    }
    return null;
  }
  
  public synchronized Connection getConnection()
    throws SQLException
  {
    if (this.connection == null)
    {
      this.connection = criarConnection();
      try
      {
        this.connection.setAutoCommit(true);
      }
      catch (SQLException ex)
      {
        Bukkit.getLogger().info("ERRO: Conexao ao banco de dados MySQL falhou!");
        Bukkit.getLogger().info("ERRO: " + ex);
      }
    }
    return this.connection;
  }
}
