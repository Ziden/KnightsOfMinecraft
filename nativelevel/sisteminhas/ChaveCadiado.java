package nativelevel.sisteminhas;

import java.util.ArrayList;
import java.util.List;
import nativelevel.Classes.Thief;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.Items.Lock;
import nativelevel.Custom.Items.LockKey;
import nativelevel.Custom.Items.LockPick;
import nativelevel.CustomEvents.FinishCraftEvent;
import nativelevel.Jobs;
import nativelevel.Jobs.TipoClasse;
import nativelevel.KoM;
import nativelevel.Lang.L;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Ziden
 */
public class ChaveCadiado extends KomSystem {

    @EventHandler
    public void crafta(FinishCraftEvent ev) {

        if (ev.getResult() != null) {

            CustomItem item = CustomItem.getItem(ev.getResult());

            if (item != null && item instanceof Lock) {

                double mySkill = ev.getPlayer().getLevel();

                TipoClasse tipo = Jobs.getJobLevel(Jobs.Classe.Engenheiro, ev.getPlayer());
                if (tipo == TipoClasse.NADA) {
                    if (mySkill > 25) {
                        mySkill = 25;
                    }
                } else if (tipo == TipoClasse.SEGUNDARIA) {
                    if (mySkill > 70) {
                        mySkill = 70;
                    }
                }

                int lockLevel = (int) (mySkill / 2) + Jobs.rnd.nextInt((int) (mySkill / 2) + Jobs.rnd.nextInt((int) (mySkill / 4)));
                ev.setResult(((Lock) CustomItem.getItem(Lock.class)).create(lockLevel));
                ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Voce fez uma tranca nivel ") + lockLevel);
            }
        }
    }

    public static int getLockLevel(Block chest) {
        Chest c = (Chest) chest.getState();
        boolean locked = false;
        // looking if the chest has a lock
        ItemStack[] coisas = c.getInventory().getContents();
        for (ItemStack ss : coisas) {
            if (ss != null) {
                if (ss.getType() == Material.OBSERVER) {
                    CustomItem item = CustomItem.getItem(ss);
                    // it has a lock !
                    if (item != null && item instanceof Lock) {
                        Lock lock = (Lock) CustomItem.getItem(Lock.class);
                        int lockLevel = lock.getLockLevel(ss);
                        return lockLevel;
                    }
                }
            }
        }
        return 0;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void interact(PlayerInteractEvent ev) {
        if (ev.getClickedBlock() != null && ev.getClickedBlock().getType() == Material.CHEST) {
            Chest c = (Chest) ev.getClickedBlock().getState();
            boolean locked = false;
            // looking if the chest has a lock
            for (ItemStack ss : c.getInventory().getContents()) {
                if (ss != null) {
                    if (ss.getType() == Material.OBSERVER) {
                        CustomItem item = CustomItem.getItem(ss);
                        // it has a lock !
                        if (item != null && item instanceof Lock) {
                            Lock lock = (Lock) CustomItem.getItem(Lock.class);
                            int lockLevel = lock.getLockLevel(ss);
                            int lockId = lock.getId(ss);
                            // does the player have the key in hand ?
                            if (ev.getPlayer().getItemInHand() != null && ev.getPlayer().getItemInHand().getType() == Material.TRIPWIRE_HOOK) {
                                CustomItem key = CustomItem.getItem(ev.getPlayer().getItemInHand());
                                // hes with a key in hand ?
                                if (key != null && key instanceof LockKey) {
                                    int keyId = LockKey.getId(ev.getPlayer().getItemInHand());
                                    // is it the right key ?
                                    if (keyId != lockId) {
                                        ev.setCancelled(true);
                                        ev.getPlayer().sendMessage(ChatColor.RED + L.m("Esta chave nao abre esse bau !"));
                                        return;
                                    } else {
                                        // OK
                                        ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Voce usou a chave para abrir o baú"));
                                    }
                                } else {
                                    ev.getPlayer().sendMessage(ChatColor.RED + L.m("Este baú está trancado !"));
                                    ev.setCancelled(true);
                                }
                                // he has no key, checking if he has a lockpick
                            } else {
                                if (ev.getPlayer().getItemInHand() != null) {
                                    CustomItem ci = CustomItem.getItem(ev.getPlayer().getItemInHand());
                                    if (ci != null && ci instanceof LockPick) {
                                        Thief.bisbilhota(ev.getPlayer(), c.getBlock());
                                        break;
                                    }
                                }
                                ev.getPlayer().sendMessage(ChatColor.RED + L.m("Este baú está trancado !"));
                                ev.setCancelled(true);
                                if (Jobs.getJobLevel(Jobs.Classe.Ladino, ev.getPlayer()) == TipoClasse.PRIMARIA) {
                                    ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce pode tentar arrombar baús com lockpicks !!"));
                                }
                            }
                            break;
                        }
                    }
                }
            }
        } else {
            if (ev.getClickedBlock() != null && ev.getClickedBlock().getType() == Material.WOOD_DOOR) {
                if (ev.getClickedBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType() != Material.CHEST) {
                    return;
                }
                Chest c = (Chest) ev.getClickedBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getState();
                boolean locked = false;
                // looking if the chest has a lock
                for (ItemStack ss : c.getInventory().getContents()) {
                    if (ss != null) {
                        if (ss.getType() == Material.TRIPWIRE_HOOK) {
                            CustomItem item = CustomItem.getItem(ss);
                            // it has a lock !
                            if (item != null && item instanceof Lock) {
                                Lock lock = (Lock) CustomItem.getItem(Lock.class);
                                int lockLevel = lock.getLockLevel(ss);
                                int lockId = lock.getId(ss);
                                // does the player have the key in hand ?
                                if (ev.getPlayer().getItemInHand() != null && ev.getPlayer().getItemInHand().getType() == Material.TRIPWIRE_HOOK) {
                                    CustomItem key = CustomItem.getItem(ev.getPlayer().getItemInHand());
                                    // hes with a key in hand ?
                                    if (key != null && key instanceof LockKey) {
                                        int keyId = LockKey.getId(ev.getPlayer().getItemInHand());
                                        // is it the right key ?
                                        if (keyId != lockId) {
                                            ev.setCancelled(true);
                                            ev.getPlayer().sendMessage(ChatColor.RED + L.m("Esta chave não abre esta porta !"));
                                            return;
                                        } else {
                                            // OK
                                            ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Voce usou a chave para abrir a porta"));
                                        }
                                    }
                                    // he has no key, checking if he has a lockpick 
                                } else {
                                    ev.getPlayer().sendMessage(ChatColor.RED + L.m("Esta porta está trancada !"));
                                    ev.setCancelled(true);
                                }
                                break;
                            }
                        }
                    }
                }
            }

        }
    }

}
