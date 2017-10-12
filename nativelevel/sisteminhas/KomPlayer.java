/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nativelevel.sisteminhas;

import java.util.UUID;
import nativelevel.KoM;
import org.bukkit.OfflinePlayer;

/**
 *
 * @author USER
 */
public class KomPlayer {

    String uuid;
    String nome;
    int emeralds;
    int lvl;
    public KomPlayer(String uuid, String nome) {
     this.uuid = uuid;
     this.nome = nome;
     
    emeralds = Html.Calcula(uuid);
    lvl = Html.getLevel(uuid);
    }
    public int getEmeralds(){
        return emeralds;
    }
    public int getLevel(){
        if(!KoM.database.hasRegisteredClass(uuid))
            return 1;
        return lvl;
    }
    public String getName(){
        return nome;
    }
    public float getKdR(){
    if(ClanLand.manager.getClanPlayer(UUID.fromString(uuid))!=null){
        return ClanLand.manager.getClanPlayer(UUID.fromString(uuid)).getKDR();
    }
        return (float) 0.0;
    }
    
}
