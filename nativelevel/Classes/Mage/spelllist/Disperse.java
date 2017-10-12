/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Classes.Mage.spelllist;

import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Listeners.GeneralListener;
import nativelevel.Classes.Mage.Elements;
import nativelevel.Classes.Mage.MageSpell;
import nativelevel.MetaShit;
import nativelevel.spec.PlayerSpec;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author User
 * 
 */
public class Disperse extends MageSpell {

    public Disperse() {
        super("Dispersão Astral"); // pensando
    }

    PotionEffect ef1 = new PotionEffect(PotionEffectType.ABSORPTION, 20*3, 1);
    PotionEffect ef2 = new PotionEffect(PotionEffectType.BLINDNESS, 20*3, 1);
    PotionEffect ef3 = new PotionEffect(PotionEffectType.INVISIBILITY, 20*3, 1);
    PotionEffect ef4 = new PotionEffect(PotionEffectType.SPEED, 20*3, 1);
    
    @Override
    public void cast(Player p) {
       p.addPotionEffect(ef1);
       p.addPotionEffect(ef2);
       p.addPotionEffect(ef3);
       p.addPotionEffect(ef4);
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
        return 45;
    }

    @Override
    public Elements[] getElements() {
        return new Elements[]{Elements.Raio, Elements.Fogo, Elements.Raio};
    }

    @Override
    public int getCooldownInSeconds() {
        return 1;
    }

}
