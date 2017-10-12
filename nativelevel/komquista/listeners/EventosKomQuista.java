package nativelevel.komquista.listeners;

import nativelevel.komquista.KomQuista;
import nativelevel.komquista.managers.ControleTempos;
import nativelevel.komquista.managers.KomQuistaManager;
import nativelevel.komquista.utils.ChatUtils;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.KoM;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.managers.ClanManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitScheduler;





public class EventosKomQuista
  implements Listener
{
  public static String placa = "§e[§cKomQ§e]";
  public static boolean vidanova = false;
  
  @EventHandler(priority=EventPriority.LOWEST)
  public void tomadano(EntityDamageEvent ev) {
    if (((ev.getEntity() instanceof Player)) && 
      (ev.getEntity().getWorld().getName().equalsIgnoreCase("WoE")) && 
      (!KomQuistaManager.isAberto())) {
      ev.setDamage(0.0D);
      ev.setCancelled(true);
    }
  }
  

  @EventHandler
  public void interact(PlayerInteractEvent ev)
  {
    if (ev.getAction() == Action.LEFT_CLICK_BLOCK) {
      final Player p = ev.getPlayer();
      if (isSign(ev.getClickedBlock().getType())) {
        Sign s = (Sign)ev.getClickedBlock().getState();
        if ((s.getLine(0).equalsIgnoreCase("[Server]")) && 
          (s.getLine(1).equalsIgnoreCase(placa))) {
          ClanPlayer cp = KomQuista.getClanManager().getClanPlayer(p);
          
          ev.setCancelled(true);
          if (cp != null) {
            if (p.hasMetadata("bateunokomquista"))
            {
              return;
            }
            if (cp.getClan() == null)
            {
              return;
            }
            
            if (cp.getClan().getHomeLocation() == null)
            {
              return;
            }
            
            if (!KomQuistaManager.isAberto()) {
              return;
            }
            






            if ((!ControleTempos.lasttag.equals("")) && (cp.getClan().getTag().equalsIgnoreCase(ControleTempos.lasttag))) {
              p.sendMessage("§aO Castelo já é seu!");
              return;
            }
            if ((s.getLine(2).isEmpty()) || (!s.getLine(2).contains("♥")) || (!s.getLine(2).contains(":")) || (!vidanova)) {
              s.setLine(2, "§4♥§0§r:300");
              vidanova = true;
            }
            int vida = Integer.parseInt(s.getLine(2).split(":")[1]);
            int dano = 1;
            Material namao = ev.getPlayer().getItemInHand().getType();
            
            if ((namao == Material.GOLD_HOE) || (namao == Material.GOLD_AXE) || (namao == Material.GOLD_PICKAXE) || (namao == Material.GOLD_SWORD) || (namao == Material.GOLD_SPADE)) {
              dano += 4;
            } else if ((namao == Material.DIAMOND_HOE) || (namao == Material.DIAMOND_AXE) || (namao == Material.DIAMOND_PICKAXE) || (namao == Material.DIAMOND_SWORD) || (namao == Material.DIAMOND_SPADE)) {
              dano += 3;
            } else if ((namao == Material.IRON_HOE) || (namao == Material.IRON_AXE) || (namao == Material.IRON_PICKAXE) || (namao == Material.IRON_SWORD) || (namao == Material.IRON_SPADE)) {
              dano += 2;
            } else if ((namao == Material.STONE_HOE) || (namao == Material.STONE_AXE) || (namao == Material.STONE_PICKAXE) || (namao == Material.STONE_SWORD) || (namao == Material.STONE_SPADE)) {
              dano++;
            } else if ((namao == Material.BONE) && (p.isOp())) {
              dano += 100;
            }
            
            if (dano > 1) {
              p.getItemInHand().setDurability((short)(p.getItemInHand().getDurability() + 2));
              if (p.getItemInHand().getDurability() >= p.getItemInHand().getType().getMaxDurability())
              {
                p.setItemInHand(new ItemStack(Material.COAL));
                p.sendMessage("§7Seu item que estava na mão quebrou!");
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
              }
              
              p.updateInventory();
            }
            p.sendMessage("§a+ " + dano);
            vida -= dano;
            s.setLine(2, "§4♥§0§r:" + vida);
            PlayEffect.play(VisualEffect.CRIT_MAGIC, s.getLocation(), "num:2");
            PlayEffect.play(VisualEffect.BLOCK_CRACK, s.getLocation(), "item:stained_clay:14 num:10");
            s.getWorld().playSound(s.getLocation(), Sound.ENTITY_BLAZE_HURT, 5.0F, 1.0F);
            if (vida <= 0) {
              for (Player pf : Bukkit.getOnlinePlayers()) {
                ChatUtils.SendPreffix(pf);
                pf.playSound(pf.getLocation(), Sound.ENTITY_FIREWORK_LARGE_BLAST, 99.0F, 1.0F);
              }
              
              KomQuistaManager.domina(cp.getClan().getTag(), s.getLocation());
              KomQuistaManager.expulsadoCastelo(cp.getClan());
              s.setLine(2, "§4♥§0§r:300");
              
              for (Player pf : Bukkit.getOnlinePlayers()) {
                ChatUtils.SendSuffix(pf);
              }
            }
            s.update();
            Object r = new Runnable()
            {
              final Player pr = p;
              
              public void run()
              {
                if (this.pr != null) {
                  this.pr.removeMetadata("bateunokomquista",  KoM._instance );
                }
              }
            };
            Bukkit.getScheduler().scheduleSyncDelayedTask( KoM._instance, (Runnable)r, 10L);
            p.setMetadata("bateunokomquista", new FixedMetadataValue( KoM._instance, ""));
          }
        }
      }
    }
  }
  

  public static boolean isSign(Material b)
  {
    return (b == Material.SIGN_POST) || (b == Material.WALL_SIGN) || (b == Material.SIGN);
  }
  
  @EventHandler
  public void update(SignChangeEvent ev) {
    if ((ev.getPlayer().isOp()) && 
      (ev.getLine(1).equalsIgnoreCase("komq"))) {
      ev.setLine(1, placa);
    }
  }
}
