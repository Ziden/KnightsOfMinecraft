package nativelevel.ajuda.database;

import java.sql.Connection;
import java.sql.Statement;

public class MetodosRemover
{
  private static DBDefault db = new DBDefault();
  
  public static void removerResposta(int id)
  {
    try
    {
      db.getConnection().createStatement().executeUpdate("DELETE FROM Pixel_Respostas WHERE id='" + id + "'");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public static void removerPergunta(int id)
  {
    try
    {
      db.getConnection().createStatement().executeUpdate("DELETE FROM Pixel_Perguntas WHERE id='" + id + "'");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
