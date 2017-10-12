package nativelevel.Equipment;

import nativelevel.CustomEvents.PlayerEquipEvent;
import nativelevel.CustomEvents.PlayerUnequipEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.UUID;
import nativelevel.KoM;
import nativelevel.MetaShit;
import org.bukkit.Bukkit;
import nativelevel.Attributes.Buffs.BuffControl;
import nativelevel.sisteminhas.KomSystem;

public class EquipManager extends KomSystem {

    private static final HashMap<UUID, Equipamento> equipment = new HashMap<UUID, Equipamento>();

    public static void updateHand(Player p, ItemStack mao) {
        if (equipment.containsKey(p.getUniqueId())) {
            equipment.get(p.getUniqueId()).hand = new ItemEquipavel(mao);
        }
    }

    public static double getPlayerAttribute(Atributo attr, Player p) {
        return getPlayerEquipmentMeta(p).getAttribute(attr);
    }

    public static EquipMeta getPlayerEquipmentMeta(Player p) {
        if (p.hasMetadata("EquipMeta")) {
            EquipMeta meta = (EquipMeta) MetaShit.getMetaObject("EquipMeta", p);
            EquipMeta mod = BuffControl.getBuffModifiers(p);
            if (mod != null) {
                EquipMeta.addMeta(mod, meta);
                return mod;
            }
            return meta;
        } else {
            EquipMeta meta = new EquipMeta(new HashMap<Atributo, Double>());
            EquipMeta mod = BuffControl.getBuffModifiers(p);
            if (mod != null) {
                return mod;
            } else {
                return meta;
            }
        }
    }

    public static void setPlayerEquipmentMeta(Player p, EquipMeta meta) {
        MetaShit.setMetaObject("EquipMeta", p, meta);
    }

    public static void debug(ItemEquipavel[] ss) {
        if (!KoM.debugMode) {
            return;
        }
        if (ss == null) {
            return;
        }
        String d = "";
        for (ItemEquipavel s : ss) {
            if (s == null) {
                d += "NULL   ";
            } else {
                d += (s.mat.name() + "   ");
            }
        }
        KoM.debug(d);
    }

    public static void checkEquips(final Player player) {
        KoM.debug("Reload equips de " + player.getName());
        Bukkit.getServer().getScheduler().runTaskLater(KoM._instance, new Runnable() {
            @Override
            public void run() {
                Equipamento equipsAtuais = new Equipamento(player);
                ItemEquipavel[] equips = equipsAtuais.armadura;

                KoM.debug("EQUIPS");
                debug(equips);

                if (!equipment.containsKey(player.getUniqueId())) {
                    equipment.put(player.getUniqueId(), new Equipamento(player));
                }

                Equipamento equipsAntigos = equipment.get(player.getUniqueId());
                ItemEquipavel[] previous = equipsAntigos.armadura;

                KoM.debug("JA TINHA ");
                debug(previous);

                ItemEquipavel newHand = new ItemEquipavel(player.getInventory().getItemInMainHand());
                ItemEquipavel newOffHand = new ItemEquipavel(player.getInventory().getItemInOffHand());
                ItemEquipavel oldOffHand = equipsAntigos.offHand;
                if (newOffHand.meta == null) {
                    KoM.debug("NEW EH NULLLL");
                }
                if (oldOffHand.meta == null) {
                    KoM.debug("OLD EH NULLLLL");
                }

                
                ItemEquipavel oldHand = equipsAntigos.hand;

                // i took weapon off
                if ((newOffHand == null) && (oldOffHand != null)) {
                    KoM.debug("took off weapon off");
                    if (EquipmentEvents.isOffHand(oldOffHand.mat)) {
                        Bukkit.getServer().getPluginManager().callEvent(new PlayerUnequipEvent(player, oldOffHand.meta));
                    }
                }
                // i place weapon in
                if ((oldOffHand == null) && (newOffHand != null)) {
                    KoM.debug("place weapon in");
                    if (EquipmentEvents.isOffHand(newOffHand.mat)) {
                        Bukkit.getServer().getPluginManager().callEvent(new PlayerEquipEvent(player, newOffHand.meta));
                    }
                }
                // replace weapon
                if ((oldOffHand != null) && (newOffHand != null) && !oldOffHand.meta.isEqual(newOffHand.meta)) {
                    KoM.debug("replace weapon");
                    if (EquipmentEvents.isOffHand(newOffHand.mat)) {
                        Bukkit.getServer().getPluginManager().callEvent(new PlayerEquipEvent(player, newOffHand.meta));
                    }
                    if (EquipmentEvents.isOffHand(oldOffHand.mat)) {
                        Bukkit.getServer().getPluginManager().callEvent(new PlayerUnequipEvent(player, oldOffHand.meta));
                    }
                }
                // i took weapon off
                if ((newHand == null) && (oldHand != null)) {
                    KoM.debug("took weapon off");
                    if (EquipmentEvents.isWeapon(oldHand.mat)) {
                        Bukkit.getServer().getPluginManager().callEvent(new PlayerUnequipEvent(player, oldHand.meta));
                    }
                }

                // i place weapon in
                if ((oldHand == null) && (newHand != null)) {
                    KoM.debug("place weapon in");
                    if (EquipmentEvents.isWeapon(newHand.mat)) {
                        Bukkit.getServer().getPluginManager().callEvent(new PlayerEquipEvent(player, newHand.meta));
                    }
                }

                // replace weapon
                if ((oldHand != null) && (newHand != null) && !oldHand.meta.isEqual(newHand.meta)) {
                    KoM.debug("replace weapon");
                    if (EquipmentEvents.isWeapon(newHand.mat)) {
                        Bukkit.getServer().getPluginManager().callEvent(new PlayerEquipEvent(player, newHand.meta));
                    }
                    if (EquipmentEvents.isWeapon(oldHand.mat)) {
                        Bukkit.getServer().getPluginManager().callEvent(new PlayerUnequipEvent(player, oldHand.meta));
                    }
                }

                for (int i = 0; i < equips.length; i++) {

                    /// taking a item off
                    if (equips[i] == null && (previous[i] != null)) {
                        Bukkit.getServer().getPluginManager().callEvent(new PlayerUnequipEvent(player, previous[i].meta));

                        // puttin a new item
                    } else if (equips[i] != null && (previous[i] == null)) {
                        Bukkit.getServer().getPluginManager().callEvent(new PlayerEquipEvent(player, equips[i].meta));

                        // replacing item
                    } else if (previous[i] != null && (equips[i] != null) && !equips[i].meta.isEqual(previous[i].meta)) {
                        Bukkit.getServer().getPluginManager().callEvent(new PlayerUnequipEvent(player, previous[i].meta));
                        Bukkit.getServer().getPluginManager().callEvent(new PlayerEquipEvent(player, equips[i].meta));
                    }
                }

                equipsAntigos.armadura = equips;
                equipsAntigos.hand = newHand;
                equipsAntigos.offHand = newOffHand;

                KoM.debug("Equips finais:");
                debug(equipsAntigos.armadura);
            }
        }, 1);
    }

}
