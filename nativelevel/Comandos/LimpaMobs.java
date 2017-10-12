package nativelevel.Comandos;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.Iterator;
import nativelevel.KoM;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class LimpaMobs
  implements CommandExecutor
{
  public boolean onCommand(CommandSender cs, Command cmd, String string, String[] strings)
  {
    if (!cs.isOp()) {
      return false;
    }
    if (cmd.getName().equalsIgnoreCase("limpamobs"))
    {
      if (strings.length != 2)
      {
        cs.sendMessage("�6Use /limpamobs mundo região!");
        return false;
      }
      String rg = strings[1];
      World w = Bukkit.getWorld(strings[0]);
      if (w == null)
      {
        cs.sendMessage("�bEste mundo ainda n�o foi criado!");
        return false;
      }
      int lim = 0;
      for (LivingEntity e : w.getLivingEntities()) {
        if (!(e instanceof Player))
        {
          ApplicableRegionSet set = KoM.worldGuard.getRegionManager(e.getWorld()).getApplicableRegions(e.getLocation());
          if (set.size() > 0)
          {
            Iterator<ProtectedRegion> i = set.iterator();
            while (i.hasNext())
            {
              ProtectedRegion region = (ProtectedRegion)i.next();
              if (region.getId().equals(rg))
              {
                e.remove();
                lim++;
              }
            }
          }
        }
      }
      cs.sendMessage(ChatColor.BLUE + "Foi limpo �c" + lim + ChatColor.BLUE + " mobs na regi�o �c" + rg + ChatColor.BLUE + " do mundo �c" + w.getName() + " !");
    }
    return false;
  }
}
