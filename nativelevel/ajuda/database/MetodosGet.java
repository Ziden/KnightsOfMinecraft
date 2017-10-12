package nativelevel.ajuda.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MetodosGet
{
  private static DBDefault db = new DBDefault();
  
  public static String getInfoPorId_DatabasePerguntas(int id, String info)
  {
    String i = null;
    try
    {
      ResultSet rs = db.getConnection().createStatement().executeQuery("SELECT * FROM Pixel_Perguntas where id='" + id + "'");
      if (rs.next()) {
        i = rs.getString(info);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return i;
  }
  
  public static String getInfoPorId_DatabaseRespostas(int id, String info)
  {
    String i = null;
    try
    {
      ResultSet rs = db.getConnection().createStatement().executeQuery("SELECT * FROM Pixel_Respostas where id='" + id + "'");
      if (rs.next()) {
        i = rs.getString(info);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return i;
  }
}
