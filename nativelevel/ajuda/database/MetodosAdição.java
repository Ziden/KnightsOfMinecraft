package nativelevel.ajuda.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class MetodosAdição
{
  private static DBDefault db = new DBDefault();
  
  public static void inserirPergunta(UUID uuid, String nome, String pergunta, String servidor)
    throws SQLException
  {
    db.getConnection().createStatement().executeUpdate("INSERT INTO Pixel_Perguntas(UUID,nome,pergunta,servidor) VALUES ('" + uuid + "' , '" + nome + "' ,'" + pergunta + "', '" + servidor + "')");
  }
  
  public static void inserirResposta(UUID uuid, String nome, int id, String resposta, String servidor)
    throws SQLException
  {
    String pergunta = MetodosGet.getInfoPorId_DatabasePerguntas(id, "pergunta");
    String jogador = MetodosGet.getInfoPorId_DatabasePerguntas(id, "uuid");
    db.getConnection().createStatement().executeUpdate("INSERT INTO Pixel_Respostas(UUID,nome,id,pergunta,resposta,playerOffline,servidor) VALUES ('" + uuid + "','" + nome + "','" + id + "','" + pergunta + "','" + resposta + "','" + jogador + "','" + servidor + "')");
  }
}
