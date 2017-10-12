package nativelevel.komquista.managers;










public class Dominio
{
  public static int lastid = 0;
  int segundos;
  String tag;
  int id;
  
  public Dominio(int segundos, String tag) {
    this.segundos = segundos;
    this.tag = tag;
    this.id = (lastid + 1);
  }
  
  public int getId() {
    return this.id;
  }
  
  public String getTag() {
    return this.tag;
  }
  
  public int getSegundos() {
    return this.segundos;
  }
}
