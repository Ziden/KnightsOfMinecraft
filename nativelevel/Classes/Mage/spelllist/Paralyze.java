/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Classes.Mage.spelllist;

import java.util.HashSet;
import java.util.UUID;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.KoM;
import nativelevel.Listeners.GeneralListener;
import nativelevel.Classes.Mage.Elements;
import nativelevel.Classes.Mage.MageSpell;
import nativelevel.Classes.Mage.SpellParticleEffects;
import nativelevel.MetaShit;
import nativelevel.spec.PlayerSpec;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftSnowball;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;

/**
 *
 * @author User
 *
 */

public class Paralyze extends MageSpell {

    public Paralyze() {
        super("Paralizar");
    }

    public static HashSet<UUID> paralizados = new HashSet<UUID>();

    public static void paraliza(LivingEntity e) {
          KoM.debug("Adicionando para");
        paralizados.add(e.getUniqueId());
        SpellParticleEffects.ativos.put(e, SpellParticleEffects.ParticleType.BLUE_REDSTONE);
    }

    public static boolean isParalizado(LivingEntity e) {
        return paralizados.contains(e.getUniqueId());
    }

    public static void removeParalize(LivingEntity e) {
        KoM.debug("Removendo para");
        paralizados.remove(e.getUniqueId());
        SpellParticleEffects.ativos.remove(e);
        KoM.efeitoBlocos(e, Material.WEB);
    }

    @Override
    public void cast(Player p) {
        Snowball s = p.launchProjectile(Snowball.class);
        for (Player pl : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) pl).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(((CraftSnowball) s).getHandle().getId()));
        }
        SpellParticleEffects.ativos.put(s, SpellParticleEffects.ParticleType.BLUE_REDSTONE);
        s.setVelocity(s.getVelocity().multiply(2f));
        MetaShit.setMetaObject("spell", s, this);
    }

    @Override
    public void spellHit(final LivingEntity hit, Location l, Projectile p) {
        if (hit == null) {
            PlayEffect.play(VisualEffect.FIREWORKS_SPARK, l, "num:10 speed:0.15");
        } else {
            if (hit.getType() == EntityType.PLAYER) {
                Player tomou = (Player) hit;
                tomou.sendMessage(ChatColor.AQUA + "* você foi paralizado *");
            }
            paraliza(hit);
            Runnable r = new Runnable() {
                public void run() {
                    if (hit != null && hit.isValid() && isParalizado(hit)) {
                        removeParalize(hit);
                        if (hit.getType() == EntityType.PLAYER) {
                            ((Player) hit).sendMessage(ChatColor.GREEN + "Voce consegue se mover novamente.");
                        }
                    }

                }
            };
            Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r, 20 * 10);
        }
    }

    @Override
    public double getManaCost() {
        return 40;
    }

    @Override
    public double getExpRatio() {
        return 1;
    }

    @Override
    public int getMinSkill() {
        return 85;
    }

    @Override
    public Elements[] getElements() {
        return new Elements[]{Elements.Raio, Elements.Terra, Elements.Terra};
    }

    @Override
    public int getCooldownInSeconds() {
        return 1;
    }

}
