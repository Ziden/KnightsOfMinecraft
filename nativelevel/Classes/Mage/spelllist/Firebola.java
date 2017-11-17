/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Classes.Mage.spelllist;

import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Classes.Mage.Elements;
import nativelevel.Classes.Mage.MageSpell;
import nativelevel.Equipment.Atributo;
import nativelevel.Equipment.EquipManager;
import nativelevel.MetaShit;
import nativelevel.spec.PlayerSpec;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;

/**
 *
 * @author User
 */
public class Firebola extends MageSpell {

    public Firebola() {
        super("Bola de Fogo");
    }

    @Override
    public void cast(Player p) {
        double magia = EquipManager.getPlayerAttribute(Atributo.Magia, p);
        SmallFireball fb = null;
        fb = p.launchProjectile(SmallFireball.class);
        fb.getVelocity().multiply(3);
        double ratio = 1 + (magia/100);
        if (PlayerSpec.temSpec(p, PlayerSpec.Sacerdote)) {
            ratio *= 0.7;
        }
        MetaShit.setMetaObject("modDano", fb, ratio);
        fb.setIsIncendiary(false);
    }

    @Override
    public double getManaCost() {
        return 10;
    }

    @Override
    public double getExpRatio() {
        return 1;
    }

    @Override
    public int getMinSkill() {
        return 5;
    }

    @Override
    public Elements[] getElements() {
        return new Elements[]{Elements.Fogo, Elements.Fogo, Elements.Raio};
    }

    @Override
    public int getCooldownInSeconds() {
        return 1;
    }

}
