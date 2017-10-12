package nativelevel.sisteminhas;

import java.util.Arrays;
import java.util.List;
import nativelevel.Classes.Farmer;
import static nativelevel.Classes.Farmer.pegaCor;
import static nativelevel.Classes.Farmer.pegaTipo;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Listeners.BlockListener;
import nativelevel.Listeners.GeneralListener;
import nativelevel.Menu.Menu;
import nativelevel.MetaShit;
import nativelevel.lojaagricola.LojaAgricola;
import nativelevel.utils.Cooldown;
import net.minecraft.server.v1_12_R1.AttributeInstance;
import net.minecraft.server.v1_12_R1.EntityInsentient;
import net.minecraft.server.v1_12_R1.GenericAttributes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

public class Horses extends KomSystem {

    @EventHandler
    public void pula(HorseJumpEvent ev) {
        if (ev.getEntity().getPassenger() != null && ev.getEntity().getPassenger().getType() == EntityType.PLAYER) {
            Player p = (Player) ev.getEntity().getPassenger();
            if (p.isOp()) {
                p.sendMessage("Pulo:" + ev.getEntity().getJumpStrength());
            }
        }
    }

    public static void setHorseSpeed(Horse h, double speed) {
        AttributeInstance attributes = ((EntityInsentient) ((CraftLivingEntity) h).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
        attributes.setValue(speed);
    }

    public static double getHorseSpeed(Horse h) {
        AttributeInstance attributes = ((EntityInsentient) ((CraftLivingEntity) h).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
        return attributes.getValue();
    }

    public static void takeOutFromEgg(PlayerInteractEvent ev) {
        if (ev.getPlayer().getWorld().getName().equalsIgnoreCase("dungeon") || ev.getPlayer().getVehicle() != null || Cooldown.isCooldown(ev.getPlayer(), "cavalo")) {
            ev.setCancelled(true);
            return;
        }

        KoM.debug("Take out from combat");

        int cbt = GeneralListener.taEmCombate(ev.getPlayer());
        if (cbt > 0) {
            ev.setCancelled(true);
            ev.getPlayer().sendMessage(ChatColor.RED + "Aguarde " + cbt + " segundos para poder fazer isto !");
            return;
        }

        Block b = ev.getPlayer().getLocation().getBlock();
        if (!BlockListener.passaveis.contains(b.getRelative(BlockFace.WEST).getType()) || !BlockListener.passaveis.contains(b.getRelative(BlockFace.EAST).getType()) || !BlockListener.passaveis.contains(b.getRelative(BlockFace.NORTH).getType()) || !BlockListener.passaveis.contains(b.getRelative(BlockFace.SOUTH).getType())) {
            ev.setCancelled(true);
            ev.getPlayer().sendMessage(ChatColor.RED + "Não tem espaço suficiente para voce fazer isto !");
            return;
        }

        ItemMeta meta = ev.getPlayer().getItemInHand().getItemMeta();
        final Horse w = (Horse) ev.getPlayer().getWorld().spawnEntity(ev.getPlayer().getLocation(), EntityType.HORSE);
        if (w == null || !w.isValid()) {
            ev.setCancelled(true);
            ev.getPlayer().sendMessage(ChatColor.RED + "Voce nao pode fazer isto aqui");
            return;
        }

        if (ev.getPlayer().getItemInHand().getAmount() > 1) {
            ev.getPlayer().getItemInHand().setAmount(ev.getPlayer().getItemInHand().getAmount() - 1);
        } else {
            ev.getPlayer().setItemInHand(null);
        }

        w.setAdult();
        w.setTamed(true);
        w.setOwner(ev.getPlayer());
        w.setAgeLock(true);

        w.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
        if (meta != null && meta.getLore() != null && meta.getLore().size() > 2) {

            String[] s1 = meta.getLore().get(0).split(":");
            String[] s2 = meta.getLore().get(1).split(":");
            String[] s3 = meta.getLore().get(2).split(":");
            String[] s4 = null;
            String[] s5 = null;
            if (meta.getLore().size() >= 4) {
                s4 = meta.getLore().get(3).split(":");
            }
            if (meta.getLore().size() >= 5) {
                s5 = meta.getLore().get(4).split(":");
                double velo = Double.valueOf(s5[1]);
                Horses.setHorseSpeed(w, velo);
                MetaShit.setMetaObject("velocidade", w, velo);
            } else {
                Horses.setHorseSpeed(w, 0.22);
                MetaShit.setMetaObject("velocidade", w, 0.19d);
            }

            if (meta.getLore().size() >= 6) {
                s5 = meta.getLore().get(5).split(":");
                double velo = Double.valueOf(s5[1]);
                w.setJumpStrength(velo);
                MetaShit.setMetaObject("pulo", w, velo);
            } else {
                w.setJumpStrength(0.5);
                MetaShit.setMetaObject("pulo", w, 0.6d);
            }

            final String nome = s1[1].trim();
            final String cor = s2[1];
            final String tipo = s3[1];
            final String raca = s4 == null ? null : s4[1];

            Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, new Runnable() {
                public void run() {
                    w.setStyle(pegaTipo(tipo));
                    w.setColor(pegaCor(cor));
                    if (raca != null) {
                        //w.setVariant(Farmer.pegaVariant(raca));
                    }

                }
            }, 1);

            //w.setStyle(pegaTipo(tipo));
            //w.setColor(pegaCor(cor));
            if (nome.length() > 0) {
                w.setCustomName(nome);
                w.setCustomNameVisible(false);

            }
        } else {
            w.setStyle(Horse.Style.NONE);
            Horses.setHorseSpeed(w, 0.22);
            w.setJumpStrength(0.4);
            w.setColor(Horse.Color.CREAMY);
            w.setCustomName("Cavalo de " + ev.getPlayer().getName());

        }
        MetaShit.setMetaObject("mount", w, "sim");
        Cooldown.setMetaCooldown(ev.getPlayer(), "cavalo", 2000);
        w.setOwner(ev.getPlayer());
        w.setPassenger(ev.getPlayer());
        ev.getPlayer().getLocation().getWorld().playEffect(ev.getPlayer().getLocation(), Effect.LARGE_SMOKE, 1);
        //PlayEffect.play(VisualEffect.SMOKE_LARGE, ev.getPlayer().getLocation(), "num:1");
        ev.getPlayer().sendMessage(ChatColor.GREEN + "Voce montou no animal");

        ev.getPlayer().getLocation().getWorld().playEffect(ev.getPlayer().getLocation(), Effect.SPELL, 1);
        //PlayEffect.play(VisualEffect.SPELL_INSTANT, ev.getPlayer().getLocation(), "num:10");
        ev.setCancelled(true);
    }

    public static void monta(VehicleEnterEvent ev) {
        if (ev.getVehicle().getType() == EntityType.PIG && ev.getEntered() instanceof Player) {
            Player montou = (Player) ev.getEntered();
            int lvlFazendeiro = Jobs.getJobLevel("Fazendeiro", montou);
            if (lvlFazendeiro != 1) { // se eh primário
                ev.setCancelled(true);
                montou.sendMessage(Menu.getSimbolo("Fazendeiro") + ChatColor.RED + L.m("Voce nao sabe montar em porcos !"));
            }
        }
        if (ev.getVehicle().getType() == EntityType.HORSE && ev.getEntered() instanceof Player) {
            Horse h = (Horse) ev.getVehicle();
            if (h == null) {
                return;
            }
            AnimalTamer nego = h.getOwner();
            if (nego != null && !nego.getName().equalsIgnoreCase(((Player) ev.getEntered()).getName())) {
                ((Player) ev.getEntered()).sendMessage(ChatColor.RED + L.m("Este cavalo ja tem dono !"));
                Tralhas.doRandomKnock((Player) ev.getEntered(), 0.7f);
                ev.setCancelled(true);
            }
        }
    }

    public static void interactHorse(PlayerInteractEntityEvent ev) {
        if (ev.getRightClicked() != null && ev.getRightClicked() instanceof Horse) {

            Horse h = (Horse) ev.getRightClicked();
            if (h.getOwner() != null && h.getOwner().getName() != null && !h.getOwner().getName().equalsIgnoreCase(ev.getPlayer().getName())) {
                ev.setCancelled(true);
                ev.getPlayer().sendMessage(ChatColor.RED + L.m("Você não pode mecher em um cavalo que não e seu !"));
            } else if (ev.getPlayer().getItemInHand() != null && ev.getPlayer().getItemInHand().getType() == Material.BONE) {
                h.damage(9999999D);
            }
        }
    }

    public static ItemStack getCavalo(Horse h) {
        Location l = h.getLocation();
        ItemStack ovo = new ItemStack(Material.MONSTER_EGG, 1);
        ovo.setDurability((short) 100);
        double velocidade = getHorseSpeed(h);
        double pulo = h.getJumpStrength();
        if (h.hasMetadata("velocidade")) {
            velocidade = (Double) MetaShit.getMetaObject("velocidade", h);
            if (KoM.debugMode) {

            }
        }
        if (h.hasMetadata("pulo")) {
            pulo = (Double) MetaShit.getMetaObject("pulo", h);
        }
        ItemMeta meta = ovo.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Montaria (Nao Dropa Qnd Morre)");
        meta.setLore(Arrays.asList(new String[]{
            ChatColor.GREEN + "Nome" + ChatColor.YELLOW + ": " + ((h.getCustomName() == null || h.getCustomName().length() == 0) ? "Pocotó" : h.getCustomName()),
            ChatColor.GREEN + "Cor" + ChatColor.YELLOW + ": " + h.getColor().name(),
            ChatColor.GREEN + "Tipo" + ChatColor.YELLOW + ": " + h.getStyle().name(),
            ChatColor.GREEN + "Raça" + ChatColor.YELLOW + ": " + h.getVariant().name(),
            ChatColor.GREEN + "Velocidade" + ChatColor.YELLOW + ": " + String.format("%.2f", velocidade),
            ChatColor.GREEN + "Salto" + ChatColor.YELLOW + ": " + String.format("%.2f", pulo),}));
        ovo.setItemMeta(meta);
        return ovo;
    }

    // AQUI
    public static void retornaCavalo(Player p) {
        if (p.getVehicle() != null) {
            if (p.getVehicle().hasMetadata("mount")) {
                if (p.getVehicle() instanceof Horse) {
                    Entity ve = p.getVehicle();
                    ve.removeMetadata("mount", KoM._instance);
                    ve.remove();
                    ItemStack ovo = getCavalo((Horse) p.getVehicle());
                    p.getInventory().addItem(ovo);
                    // p.getVehicle().eject();
                    p.sendMessage(ChatColor.GREEN + "Desmontou");

                }
            }
        }
    }

    @EventHandler
    public void onVehicleExit(final VehicleExitEvent ev) {
        // Return Minecart to inventory.
        if (ev.getVehicle().getType() == EntityType.MINECART) {
            ev.getVehicle().remove();
            if (ev.getExited().getType() == EntityType.PLAYER) {
                ((Player) ev.getExited()).getInventory().addItem(new ItemStack(Material.MINECART, 1));
            }
        } else if (ev.getExited().getType() == EntityType.PLAYER && ev.getVehicle().hasMetadata("mount")) {
            
            if (ev.getVehicle() instanceof LivingEntity) {
                LivingEntity le = (LivingEntity) ev.getVehicle();
                if (le.hasPotionEffect(PotionEffectType.SLOW)) {
                    le.removePotionEffect(PotionEffectType.SLOW);
                }
                if (le.hasPotionEffect(PotionEffectType.SPEED)) {
                    le.removePotionEffect(PotionEffectType.SPEED);
                }
            }

            retornaCavalo((Player) ev.getExited());

        }
    }

}
