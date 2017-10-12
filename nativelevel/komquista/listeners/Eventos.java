package nativelevel.komquista.listeners;

import java.util.HashMap;
import nativelevel.KoM;
import nativelevel.komquista.KomQuista;
import nativelevel.komquista.managers.KomQuistaManager;
import nativelevel.komquista.utils.ChatUtils;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.managers.ClanManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitScheduler;




public class Eventos
  implements Listener
{
  @EventHandler
  public void clickSponge(PlayerInteractEvent ev)
  {
    if ((ev.getAction() == Action.RIGHT_CLICK_BLOCK) && 
      (ev.getClickedBlock().getType() == Material.SPONGE) && 
      (ev.getClickedBlock().getRelative(BlockFace.DOWN).getType() == Material.REDSTONE_BLOCK)) {
      KomQuistaManager.tacaPlayernoKomQ(ev.getPlayer());
    }
  }
  

  @EventHandler
  public void openChest(PlayerInteractEvent ev)
  {
    if ((ev.getAction() == Action.RIGHT_CLICK_BLOCK) && 
      (ev.getClickedBlock().getType() == Material.CHEST) && 
      (KomQuistaManager.baus.containsKey(ev.getClickedBlock().getLocation()))) {
      final ClanPlayer cp = KomQuista.getClanManager().getClanPlayer(ev.getPlayer());
      if ((cp == null) || (cp.getClan() == null)) {
        ev.getPlayer().sendMessage("§aBau bloqueado para você!");
        ev.setCancelled(true);
        return;
      }
      if (!cp.getClan().getTag().equals(KomQuistaManager.baus.get(ev.getClickedBlock().getLocation()))) {
        ev.getPlayer().sendMessage("§aBau bloqueado para você!");
        ev.setCancelled(true);
        return;
      }
      if (!cp.isLeader()) {
        ev.getPlayer().sendMessage("§aBau bloqueado para você!");
        ev.setCancelled(true);
        return;
      }
      if (ev.getClickedBlock().hasMetadata("aberto")) {
        return;
      }
      
      ev.getClickedBlock().setMetadata("aberto", new FixedMetadataValue( KoM._instance, ""));
      final Location loc = ev.getClickedBlock().getLocation();
      Bukkit.getScheduler().scheduleSyncDelayedTask( KoM._instance, new Runnable()
      {
        public void run()
        {
          if (KomQuistaManager.baus.containsKey(loc)) {
            KomQuistaManager.baus.remove(loc);
            
            if ((cp != null) && (cp.getClan() != null)) {
              for (ClanPlayer p : cp.getClan().getOnlineMembers()) {
                ChatUtils.SendPreffix(p.toPlayer());
              }
              KomQuistaManager.sendClanMessage(cp.getClan(), "§9• §5O Premio foi recebido pelo seu lider " + cp.getName() + " !");
              for (ClanPlayer p : cp.getClan().getOnlineMembers())
                ChatUtils.SendSuffix(p.toPlayer()); } } } }, 200L);
    }
  }
  

















  @EventHandler
  public void blockbre(BlockBreakEvent ev)
  {
    if (KomQuistaManager.baus.containsKey(ev.getBlock().getLocation())) {
      ev.getPlayer().sendMessage("§aNão pode quebrar baus de recompensa!");
      ev.setCancelled(true);
    }
  }
}
