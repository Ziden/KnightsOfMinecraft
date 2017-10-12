package nativelevel.DataBase.API;

import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Ziden
 * 
 */

public class Dados {

    private HashMap<String, Object> dados = new HashMap<String, Object>();
    
    public void add(String nome, Object dado) {
        dados.put(nome, dado);
    }
    
    public Set<String> getKeys() {
        return dados.keySet();
    }
    
}
