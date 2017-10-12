package nativelevel.Attributes;

import java.util.HashMap;
import java.util.UUID;
import nativelevel.Custom.CustomItem;
import nativelevel.Equipment.Atributo;
import nativelevel.Equipment.EquipManager;
import nativelevel.Equipment.EquipMeta;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.scores.SBCore;
import nativelevel.spec.PlayerSpec;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class Mana {

    public int maxMana = 70;
    public int mana = 70;
    public static HashMap<UUID, Mana> manas = new HashMap();

    public static Mana getMana(Player p) {
        if (manas.containsKey(p.getUniqueId())) {
            return (Mana) manas.get(p.getUniqueId());
        }
        Mana m = new Mana();
        manas.put(p.getUniqueId(), m);
        return m;
    }

    public static void startRegenTimer() {
        Runnable r = new Runnable() {
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    Mana.changeMana(p, 20);
                }
            }
        };
        Bukkit.getScheduler().scheduleSyncRepeatingTask(KoM._instance, r, 400L, 400L);
        Runnable r2 = new Runnable() {
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    Stamina.changeStamina(p, 20);
                }
            }
        };
        Bukkit.getScheduler().scheduleSyncRepeatingTask(KoM._instance, r2, 400L, 400L);
    }

    public static int getMax(Player p) {
        EquipMeta equip = EquipManager.getPlayerEquipmentMeta(p);
        int stat = (int) equip.getAttribute(Atributo.Mana);
        return p.getLevel() * 2 + 70 + stat;
    }

    public static boolean spendMana(Player p, int qt) {
        if (PlayerSpec.temSpec(p, PlayerSpec.Sabio)) {
            qt += 10;
        }
        if (PlayerSpec.temSpec(p, PlayerSpec.Sacerdote)) {
            qt = (int) (qt / 1.5D);
        }
        Mana mana = getMana(p);
        if (mana.mana >= qt) {
            changeMana(p, -qt);
            return true;
        }
        p.sendMessage(ChatColor.BLUE + L.m("Falta mana em voce: ") + mana.mana + "/" + mana.maxMana + " - " + ChatColor.RED + qt);
        return false;
    }

    public static void enxe(Player p) {
        Mana mana = getMana(p);

        mana.maxMana = getMax(p);
        mana.mana = mana.maxMana;
        p.sendMessage(ChatColor.BLUE + L.m("Mana: ") + mana.mana + "/" + mana.maxMana);
        SBCore.AtualizaObjetivos(p);
    }

    public static void changeMana(Player p, int qtd) {
        Mana mana = getMana(p);

        if (qtd > 0) {
            EquipMeta equip = EquipManager.getPlayerEquipmentMeta(p);
            double stat = equip.getAttribute(Atributo.Regen_Mana);
            if (stat > 0) {
                double bonus = qtd * (stat / 100d);
                qtd = qtd + (int) Math.round(bonus);
            }
        }

        mana.maxMana = getMax(p);
        if ((mana.mana == mana.maxMana) && (qtd > 0)) {
            return;
        }
        mana.mana += qtd;
        if (mana.mana > mana.maxMana) {
            mana.mana = mana.maxMana;
        }
        if (mana.mana < 0) {
            mana.mana = 0;
        }
        p.sendMessage(ChatColor.BLUE + L.m("Mana: ") + mana.mana + "/" + mana.maxMana);
        SBCore.AtualizaObjetivos(p);
    }
}
