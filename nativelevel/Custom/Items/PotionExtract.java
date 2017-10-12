/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Custom.Items;

import java.util.ArrayList;
import java.util.List;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.CustomPotion;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author User
 * 
 */
public class PotionExtract extends CustomItem {

    public PotionExtract() {
        super(Material.POTION, "Extrato de Poção", "Aqueça corretamente", CustomItem.COMUM);
    }
    
    public ItemStack createPotionExtract(CustomPotion pot) {
        ItemStack extract = this.generateItem(1);
        ItemMeta meta = extract.getItemMeta();
        List<String> lore = new ArrayList<String>(meta.getLore());
        lore.add(0, ChatColor.BLUE+"Extrato de:"+pot.name);
        meta.setLore(lore);
        extract.setItemMeta(meta);
        return extract;
    }
    
    public CustomPotion getPotionFromExtract(ItemStack extract) {
        ItemMeta meta = extract.getItemMeta();
        String potionName = meta.getLore().get(0).split(":")[1];
        CustomPotion pot = CustomPotion.getItem(potionName);
        return pot;
    }
    
    public boolean displayOnItems() {
        return false;
    }

    @Override
    public boolean onItemInteract(Player p) {
        return true;
    }
    
}
