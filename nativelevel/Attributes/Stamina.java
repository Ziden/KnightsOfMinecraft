package nativelevel.Attributes;

import java.util.HashMap;
import java.util.UUID;
import nativelevel.Equipment.Atributo;
import nativelevel.Equipment.EquipManager;
import nativelevel.Equipment.EquipMeta;
import nativelevel.Lang.L;
import nativelevel.scores.SBCore;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Stamina {

    public int maxStamina = 100;
    public int stamina = 100;
    public static HashMap<UUID, Stamina> staminas = new HashMap();

    public static Stamina getStamina(Player p) {
        if (staminas.containsKey(p.getUniqueId())) {
            return (Stamina) staminas.get(p.getUniqueId());
        }
        Stamina m = new Stamina();
        staminas.put(p.getUniqueId(), m);
        return m;
    }

    public static int getMax(Player p) {
        
          EquipMeta equip = EquipManager.getPlayerEquipmentMeta(p);
            int stat = (int)equip.getAttribute(Atributo.Stamina);
        
        return p.getLevel() * 2 + 100 + stat;
        
    }

    public static void enxe(Player p) {
        Stamina mana = getStamina(p);

        mana.maxStamina = getMax(p);
        mana.stamina = mana.maxStamina;
        p.sendMessage(ChatColor.BLUE + L.m("Stamina: ") + mana.stamina + "/" + mana.maxStamina);
        SBCore.AtualizaObjetivos(p);
    }

    public static boolean spendStamina(Player p, int qt) {
        Stamina mana = getStamina(p);
        if (mana.stamina >= qt) {
            changeStamina(p, -qt);
            return true;
        }
        p.sendMessage(ChatColor.BLUE + L.m("Falta stamina em voce: ") + mana.stamina + "/" + mana.maxStamina + " - " + ChatColor.RED + qt);
        return false;
    }

    public static void changeStamina(Player p, int qtd) {
        Stamina mana = getStamina(p);

        if (qtd > 0) {
            EquipMeta equip = EquipManager.getPlayerEquipmentMeta(p);
            double stat = equip.getAttribute(Atributo.Regen_Stamina);
            if (stat > 0) {
                double bonus = qtd * (stat / 100d);
                qtd = qtd + (int) Math.round(bonus);
            }
        }

        mana.maxStamina = getMax(p);
        if ((mana.stamina == mana.maxStamina) && (qtd > 0)) {
            return;
        }
        mana.stamina += qtd;
        if (mana.stamina > mana.maxStamina) {
            mana.stamina = mana.maxStamina;
        }
        if (mana.stamina < 0) {
            mana.stamina = 0;
        }
        if (qtd > 0) {
            p.sendMessage(ChatColor.BLUE + "Stamina: " + mana.stamina + "/" + mana.maxStamina);
        }
        SBCore.AtualizaObjetivos(p);
    }
}
