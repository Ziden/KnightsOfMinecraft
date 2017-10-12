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

/**
 *
 * @author User
 * 
 */
public class Repel extends MageSpell {

    public Repel() {
        super("Repulsão");
    }

    @Override
    public void cast(Player p) {
       GeneralListener.wizard.castRepel(p, p.getLevel());
    }

    @Override
    public double getManaCost() {
        return 50;
    }

    @Override
    public double getExpRatio() {
        return 1;
    }

    @Override
    public int getMinSkill() {
        return 35;
    }

    @Override
    public Elements[] getElements() {
        return new Elements[]{Elements.Terra, Elements.Terra, Elements.Fogo};
    }

    @Override
    public int getCooldownInSeconds() {
        return 1;
    }

}
