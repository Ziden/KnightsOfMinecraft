/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.rankings;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author User
 */
public class RankCache {
    
    public static HashMap<UUID, Estatistica> souTopEm = new HashMap<UUID, Estatistica>();
    
    public static void loadTops() {
        for(Estatistica stat : Estatistica.values()) {
            List<RankedPlayer> lista =RankDB.getTopPlayers(stat, 3);
            for(RankedPlayer rp : lista) {
                souTopEm.put(rp.id, stat);
            }
            
        }
    }
    
    public static HashMap<UUID, RankCache> players = new HashMap<UUID, RankCache>();
    
    public HashMap<Estatistica, Integer> ganhos = new HashMap<Estatistica, Integer>();
    public String nome;
    public RankCache(String s) {
        nome = s;
    }
}
