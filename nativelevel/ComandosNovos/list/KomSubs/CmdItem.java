/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.ComandosNovos.list.KomSubs;

import java.util.ArrayList;
import java.util.List;
import nativelevel.ComandosNovos.Comando.CommandType;
import nativelevel.ComandosNovos.SubCmd;
import nativelevel.Custom.CustomPotion;
import nativelevel.Equipment.EquipManager;
import nativelevel.Equipment.EquipMeta;
import nativelevel.Equipment.Atributo;
import nativelevel.Equipment.ItemAttributes;
import nativelevel.Equipment.NBTAttrs;
import nativelevel.Equipment.WeaponDamage;
import nativelevel.Equipment.Generator.EquipGenerator;
import nativelevel.Jobs;
import nativelevel.utils.MetaUtils;
import nativelevel.gemas.Raridade;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

/**
 *
 * @author User
 *
 */
public class CmdItem extends SubCmd {

    public CmdItem() {
        super("item", CommandType.OP);
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if (!cs.isOp()) {
            return;
        }
        Player p = (Player) cs;
        if (args.length == 1) {
            cs.sendMessage("use /kom item <atributo> <valor> (soma o atributo)");
            cs.sendMessage("use /kom item debug");
            cs.sendMessage("use /kom item stats");
            cs.sendMessage("use /kom item gera <nivel>");
            cs.sendMessage("use /kom item gera <nivel> <raridade>");
            String as = "";
            for (Atributo a : Atributo.values()) {
                as += a.name() + " ";
            }
            cs.sendMessage(as);
        } else {
            if (args[1].equalsIgnoreCase("wipenbt")) {
                ItemStack mao = p.getItemInHand();
                p.setItemInHand(NBTAttrs.removeAttr(mao));
                p.sendMessage("Wipado");
            } else if (args[1].equalsIgnoreCase("debug")) {
                System.out.println("DEBUGANDO");
                NBTAttrs.debug(p.getItemInHand());
            } else if (args[1].equalsIgnoreCase("durab")) {
                short durab = Short.valueOf(args[2]);
                ItemStack naMao = p.getItemInHand();

                ItemStack ss = new ItemStack(Material.FLINT);
               // ss.setDurability(durab);
                ItemMeta meta = ss.getItemMeta();
                meta.spigot().setUnbreakable(true);
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
                ss.setItemMeta(meta);
                p.setItemInHand(ss);
            } else if (args[1].equalsIgnoreCase("teste")) {
                ItemStack pot = new ItemStack(Material.POTION, 1, (byte) 0);
                PotionMeta meta = (PotionMeta) pot.getItemMeta();
                // meta.setDisplayName("WOLO");
                List<String> lore = new ArrayList<String>();
                lore.add("TESTE");
                meta.setLore(lore);
                meta.setBasePotionData(new PotionData(PotionType.WATER));
                pot.setItemMeta(meta);
                p.getInventory().addItem(pot);
                //p.setItemInHand(novo);
            } else if (args[1].equalsIgnoreCase("gera")) {
                if (args.length == 3) {
                    int nivel = Integer.valueOf(args[2]);
                    ItemStack gerado = EquipGenerator.gera(nivel);
                    p.getInventory().addItem(gerado);
                    p.sendMessage(ChatColor.GREEN + "Gerei um item maroto ai..");
                } else if (args.length == 4) {
                    try {
                        int nivel = Integer.valueOf(args[2]);
                        Raridade rar = Raridade.valueOf(args[3]);
                        ItemStack gerado = EquipGenerator.gera(rar, nivel);
                        p.getInventory().addItem(gerado);
                        p.sendMessage(ChatColor.GREEN + "Gerei um item maroto ai..");
                    } catch (Exception e) {
                        p.sendMessage("Raridade ou numero do nivel invalido. Tente 'Raro' ao invez de 'raro'");
                    }
                }
            } else if (args[1].equalsIgnoreCase("stats")) {
                EquipMeta meta = EquipManager.getPlayerEquipmentMeta(p);
                p.sendMessage(ChatColor.RED + "Vendo atributos");
                for (Atributo a : meta.getAtributos()) {
                    p.sendMessage(a.name() + " " + meta.getAttribute(a));
                }
            } else {
                try {
                    Atributo a = Atributo.valueOf(args[1]);
                    ItemStack ss = p.getItemInHand();
                    ItemAttributes.addAttribute(ss, a, Integer.valueOf(args[2]));
                } catch (Exception e) {
                    p.sendMessage("Atributo inválido, tente um desses:");
                    String as = "";
                    for (Atributo a : Atributo.values()) {
                        as += a.name() + " ";
                    }
                    cs.sendMessage(as);
                }
            }
        }
        return;
    }

}
