/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Attributes.Buffs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import nativelevel.KoM;
import nativelevel.Equipment.EquipMeta;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;


/**
 *
 * @author User
 */
public class BuffControl {

    public static void BuffControl() {
        Runnable r = new Runnable() {
            public void run() {

                for (UUID u : buffs.keySet()) {

                    List<Buff> rem = new ArrayList<Buff>();

                    List<Buff> lista = buffs.get(u);

                    for (Buff b : lista) {
                        if (b.hasEnded()) {
                            rem.add(b);
                        } else {
                            b.secondTick();
                        }
                    }
                    
                    for (Buff b : rem) {
                        remBuff(b.getEntity(), b);
                    }

                }
            }
        };
        Bukkit.getScheduler().scheduleSyncRepeatingTask(KoM._instance, r, 20, 20);
    }

   // private static List<Buff> activeBuffs = new LinkedList<Buff>();
    public static HashMap<UUID, List<Buff>> buffs = new HashMap<UUID, List<Buff>>();

    private static HashMap<UUID, List<EquipMeta>> modifiers = new HashMap<UUID, List<EquipMeta>>();

    public static boolean temBuff(Class<? extends Buff> buff, LivingEntity e) {
        for(Buff b : getBuffs(e)) {
            if(b.getClass().equals(buff.getClass())) 
                return true;
        }
        return false;
    }
    
    private static List<Buff> getBuffs(LivingEntity p) {
        if (!buffs.containsKey(p.getUniqueId())) {
            buffs.put(p.getUniqueId(), new ArrayList<Buff>());
        }
        return buffs.get(p.getUniqueId());
    }
    
    public static Buff getBuff(LivingEntity p, Class<? extends Buff> buff) {
        if (!buffs.containsKey(p.getUniqueId())) {
            buffs.put(p.getUniqueId(), new ArrayList<Buff>());
        }
        for(Buff b : buffs.get(p.getUniqueId())) {
            if(b.getClass().equals(buff.getClass()))
                return b;
        }
        return null;
    }

    public static EquipMeta getBuffModifiers(Player p) {
        if (modifiers.containsKey(p.getUniqueId())) {
            EquipMeta master = new EquipMeta();
            for (EquipMeta mod : modifiers.get(p.getUniqueId())) {
                EquipMeta.addMeta(master, mod);
            }
            return master;
        }
        return null;
    }

    public static void remBuff(LivingEntity e, Buff b) {
        if (b.getEquipBonus() != null) {
            EquipMeta bonus = b.getEquipBonus();
            if (modifiers.containsKey(e.getUniqueId())) {
                modifiers.get(e.getUniqueId()).remove(bonus);
                if (modifiers.get(e.getUniqueId()).size() == 0) {
                    modifiers.remove(e.getUniqueId());
                }
            }
        }
        getBuffs(e).remove(b);
        if (b.getEntity().getType() == EntityType.PLAYER) {
            b.getEntity().sendMessage("&9[-] &6" + b.getName());
        }
    }

    public static void addBuff(LivingEntity e, Buff b) {
        getBuffs(e).add(b);
        if (b.getEquipBonus() != null) {
            EquipMeta bonus = b.getEquipBonus();
            if (modifiers.containsKey(e.getUniqueId())) {
                modifiers.get(e.getUniqueId()).add(bonus);
            } else {
                modifiers.put(e.getUniqueId(), new ArrayList<EquipMeta>());
                modifiers.get(e.getUniqueId()).add(bonus);
            }
        }
        if (b.getEntity().getType() == EntityType.PLAYER) {
            b.getEntity().sendMessage("&9[+] &6" + b.getName());
        }
    }

}
