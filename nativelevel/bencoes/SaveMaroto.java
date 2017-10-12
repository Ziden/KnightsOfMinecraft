/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.bencoes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Level;
import nativelevel.Custom.Items.DoubleXP;
import static nativelevel.Custom.Items.DoubleXP.ativo;
import nativelevel.KoM;
import nativelevel.Lang.L;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author User
 */
public class SaveMaroto implements Serializable {

    private HashMap<UUID, TipoBless> buffs = new HashMap<UUID, TipoBless>();
    private HashMap<UUID, Long> tempos = new HashMap<UUID, Long>();

    private boolean doubleXP = false;
    
    public int tem(Player p) {

        if (tempos.containsKey(p.getUniqueId())) {
            long acabaEm = tempos.get(p.getUniqueId());
            long agora = System.currentTimeMillis() / 1000;
            if (acabaEm < agora) {
                KoM.log.info("ACABOU");
                remove(p);
                return 0;
            } else {
                return (int) (acabaEm - agora);
            }
        }
        return 0;
        
    }

    public TipoBless getTipo(Player p) {
        if (buffs.containsKey(p.getUniqueId())) {
            int tempo = tem(p);
            if (tempo > 0) {
                return buffs.get(p.getUniqueId());
            }
        }
        return null;
    }

    public void remove(Player p) {
        buffs.remove(p.getUniqueId());
        tempos.remove(p.getUniqueId());
    }

    public void add(Player p, TipoBless tipo) {
        buffs.put(p.getUniqueId(), tipo);
        tempos.put(p.getUniqueId(), (System.currentTimeMillis() / 1000) + (60 * 20));
    }

    public SaveMaroto Load() {

        KoM.log.info("----- LOADANDO COISAS ------");

        if (TipoBless.dat.length() == 0) {
            return null;
        }
        ObjectInputStream ois;
        SaveMaroto dados = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(TipoBless.dat));
            dados = (SaveMaroto) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (dados != null && dados.doubleXP) {
            /*
            DoubleXP.ativo = true;
            Runnable termina = new Runnable() {
                public void run() {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p != null) {
                            p.sendMessage(ChatColor.AQUA + L.m("[ O Double XP Terminou !!! ]"));
                            ativo = false;
                        }
                    }
                }
            };
            Bukkit.getScheduler().scheduleSyncDelayedTask(KnightsOfMania._instance, termina, 20 * 60 * 30);
            */
          }

        return dados;
    }

    public final void Save() {
        //Se o Arquivo dos Dados Nao Existir, Nós o Criamos.

        KoM.log.info("----- SALVANDO COISAS ------");

        if (DoubleXP.ativo) {
            doubleXP = true;
        } else {
            doubleXP = false;
        }

        if (!TipoBless.dat.exists()) {
            try {
                TipoBless.dat.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(TipoBless.dat));
            oos.writeObject(this);
            oos.flush();
            oos.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
