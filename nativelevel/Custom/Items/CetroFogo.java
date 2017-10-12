/*

 ╭╮╭━╮╱╱╭━╮╭━╮
 ┃┃┃╭╯╱╱┃┃╰╯┃┃
 ┃╰╯╯╭━━┫╭╮╭╮┃
 ┃╭╮┃┃╭╮┃┃┃┃┃┃
 ┃┃┃╰┫╰╯┃┃┃┃┃┃
 ╰╯╰━┻━━┻╯╰╯╰╯

 Desenvolvedor: ZidenVentania
 Colaboradores: NeT32, Gabripj, Feldmann
 Patrocionio: InstaMC

 */
package nativelevel.Custom.Items;

import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Attributes.Mana;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class CetroFogo extends CustomItem {

    public CetroFogo() {
        super(Material.BLAZE_ROD, L.m("Cetro Igneo"),L.m("Fireball"),CustomItem.INCOMUM);
    }

    @Override
    public boolean onItemInteract(Player player) {
         if(Jobs.getJobLevel("Mago", player)!=1) {
            player.sendMessage(ChatColor.RED+L.m("Apenas "+ChatColor.YELLOW+"magos experientes"+ChatColor.RED+" sabem usar este item !"));
            return true;
        }
        
        if(player.hasPotionEffect(PotionEffectType.SLOW)) {
            player.sendMessage(ChatColor.RED+L.m("Voce precisa aguardar um pouco"));
            return true;
        }
        
        if(player.getWorld().getName().equalsIgnoreCase("dungeon") || player.getWorld().getName().equalsIgnoreCase("vila")) {
            player.sendMessage(ChatColor.RED+L.m("Voce nao pode usar isto aqui !"));
            return true;
        }
        
        if(!Mana.spendMana(player, 100))
            return true;
        
        if(!KoM.gastaReagentes(player,player.getInventory(), 1)) {
            player.sendMessage(ChatColor.RED+L.m("Voce precisa de 1 mana potion para usar o cetro !"));
            return true;
        }
        
        
        
        //NativeLevel.removeInventoryItems(player.getInventory(), Material.GLOWSTONE_DUST, 5);
        
        //Mago.consomeReagentes(player, 1);
        
       
        
        Location alvo = player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(2)).toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
        Fireball fireball; 
        if(player.isSneaking()) {
            fireball = (Fireball)player.getWorld().spawn(alvo, SmallFireball.class);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 1));
        } else {
            fireball = (Fireball)player.getWorld().spawn(alvo, Fireball.class);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*5, 1));
        }
        player.getWorld().playEffect(player.getLocation(), Effect.GHAST_SHOOT, 0);
        fireball.getVelocity().divide(new Vector(6,6,6));
        fireball.setShooter(player);
        
        return false;
    }
   
}
