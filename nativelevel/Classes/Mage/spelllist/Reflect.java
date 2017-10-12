package nativelevel.Classes.Mage.spelllist;

import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Listeners.GeneralListener;
import nativelevel.Classes.Mage.Elements;
import nativelevel.Classes.Mage.MageSpell;
import nativelevel.MetaShit;
import nativelevel.spec.PlayerSpec;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;

/**
 *
 * @author User
 *
 */
public class Reflect extends MageSpell {

    public Reflect() {
        super("Escudo Reflexor");
    }

    @Override
    public void cast(final Player p) {
        p.sendMessage(ChatColor.GREEN + L.m("Voce se protegeu com um escudo reflexor por 6 segundos"));
        MetaShit.setMetaString("shield", p, "1");
        Runnable r = new Runnable() {

            public void run() {
                if (p.hasMetadata("shield")) {
                    p.sendMessage(ChatColor.RED + L.m("Seu escudo se dissipou"));
                    p.removeMetadata("shield", KoM._instance);
                }
            }
        };
        Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r, 20 * 6);
    }

    @Override
    public double getManaCost() {
        return 30;
    }

    @Override
    public double getExpRatio() {
        return 1;
    }

    @Override
    public int getMinSkill() {
        return 65;
    }

    @Override
    public Elements[] getElements() {
        return new Elements[]{Elements.Fogo, Elements.Fogo, Elements.Terra};
    }

    @Override
    public int getCooldownInSeconds() {
        return 1;
    }

}
