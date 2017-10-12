/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Attributes.Buffs;

import java.util.UUID;
import nativelevel.Equipment.EquipMeta;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

/**
 *
 * @author User
 */
public abstract class Buff {

    public abstract String getName();

    public abstract EquipMeta getEquipBonus();

    public abstract int getDurationSeconds();

    public abstract void secondTick();

    public abstract BuffType getType();

    private long started;
    private LivingEntity affected;

    protected LivingEntity getEntity() {
        return affected;
    }

    public long endsAt() {
        return getDurationSeconds() + System.currentTimeMillis() / 1000;
    }

    public Buff(LivingEntity affected) {
        this.affected = affected;
        started = System.currentTimeMillis() / 1000;
    }

    public boolean hasEnded() {
        if(getDurationSeconds()<=0)
            return false;
        long now = System.currentTimeMillis() / 1000;
        long finishIn = started + getDurationSeconds();
        if (now >= finishIn) {
            return true;
        }
        return false;
    }

}
