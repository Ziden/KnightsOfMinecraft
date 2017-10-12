/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Classes.Mage.spelllist;

import nativelevel.Classes.Mage.Elements;
import nativelevel.Classes.Mage.MageSpell;
import nativelevel.MetaShit;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

/**
 *
 * @author User
 */
public class FireBomba extends MageSpell {

    public FireBomba() {
        super("Bomba de Fogo");
    }

    private final double MOD_DANO = 1;
    
    @Override
    public void cast(Player p) {
        Fireball fb = null;
        fb = p.launchProjectile(Fireball.class);
        fb.getVelocity().multiply(2);
        fb.setIsIncendiary(false);
        MetaShit.setMetaObject("modDano", fb, MOD_DANO);
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
        return 60;
    }

    @Override
    public Elements[] getElements() {
        return new Elements[]{Elements.Fogo, Elements.Fogo, Elements.Fogo};
    }

    @Override
    public int getCooldownInSeconds() {
        return 1;
    }

}
