/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nativelevel.scores.ScoreboardManager;
import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Team;

/**
 *
 * @author ciro
 */
public class GeneralUtils {

    public static boolean hasItem(Inventory inv, Material type, int amount, byte data) {
        for (ItemStack is : inv.getContents()) {
            if (is != null && is.getType() == type && is.getData().getData() == data) {
                if (is.getAmount() >= amount) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void removeInventoryItems(Inventory inv, Material type, int amount, byte data) {
        for (ItemStack is : inv.getContents()) {
            if (is != null && is.getType() == type && is.getData().getData() == data) {
                int slot = inv.first(is.getType());
                int newamount = is.getAmount() - amount;
                if (newamount > 0) {
                    is.setAmount(newamount);
                    break;
                } else {
                    inv.setItem(slot, null);
                    amount = -newamount;
                    if (amount == 0) {
                        break;
                    }
                }
            }
        }
    }

    public static boolean isInt(String n) {
        try {
            int newN = Integer.parseInt(n);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void sendActionBar(Player player, String title) {
        CraftPlayer craftplayer = (CraftPlayer) player;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        IChatBaseComponent titleJSON = ChatSerializer.a("{'text': '" + title + "'}");
        PacketPlayOutChat packet = new PacketPlayOutChat(titleJSON, ChatMessageType.GAME_INFO);
        connection.sendPacket(packet);
    }

    //adicionar contents
    public static Inventory createInventory(Player p, int size, String name) {
        Inventory invent = Bukkit.createInventory(p, size, name);
        return invent;
    }

    public static boolean isEqual(ItemStack ss, ItemStack ss2) {
        if (ss.getType() != ss2.getType()) {
            return false;
        }
        if (ss.getData().getData() != ss2.getData().getData()) {
            return false;
        }
        ItemMeta m1 = ss.getItemMeta();
        ItemMeta m2 = ss2.getItemMeta();
        if (m1.getDisplayName() != null && m2.getDisplayName() != null && !m1.getDisplayName().equalsIgnoreCase(m2.getDisplayName())) {
            return false;
        }
        if (m1.getDisplayName() != null && m2.getDisplayName() == null) {
            return false;
        }
        if (m2.getDisplayName() != null && m1.getDisplayName() == null) {
            return false;
        }
        List<String> lore1 = m1.getLore();
        List<String> lore2 = m2.getLore();
        if (lore1 != null && lore2 != null && lore1.size() != lore2.size()) {
            return false;
        }

        if (lore1 != null && lore2 != null) {
            for (int i = 0; i < lore1.size(); i++) {
                if (!lore1.get(i).equalsIgnoreCase(lore2.get(i))) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean isEqualSemData(ItemStack ss, ItemStack ss2) {
        if (ss.getType() != ss2.getType()) {
            return false;
        }

        ItemMeta m1 = ss.getItemMeta();
        ItemMeta m2 = ss2.getItemMeta();
        if (m1.getDisplayName() != null && m2.getDisplayName() != null && !m1.getDisplayName().equalsIgnoreCase(m2.getDisplayName())) {
            return false;
        }
        if (m1.getDisplayName() != null && m2.getDisplayName() == null) {
            return false;
        }
        if (m2.getDisplayName() != null && m1.getDisplayName() == null) {
            return false;
        }
        List<String> lore1 = m1.getLore();
        List<String> lore2 = m2.getLore();
        if (lore1 != null && lore2 != null && lore1.size() != lore2.size()) {
            return false;
        }

        if (lore1 != null && lore2 != null) {
            for (int i = 0; i < lore1.size(); i++) {
                if (!lore1.get(i).equalsIgnoreCase(lore2.get(i))) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static ItemStack createItem(ItemStack it, String displayName, short durability, String loretext) {
        ItemStack item = it;
        ItemMeta metaItem = item.getItemMeta();
        metaItem.setDisplayName(displayName);
        item.setDurability(durability);
        List<String> lore = new ArrayList<String>();
        lore.addAll(Arrays.asList(loretext.split("@")));
        metaItem.setLore(lore);
        item.setItemMeta(metaItem);
        return item;
    }
}
