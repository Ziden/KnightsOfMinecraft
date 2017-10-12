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
package nativelevel.Comandos;

import com.sk89q.worldedit.bukkit.selections.Selection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Classes.Farmer;
import nativelevel.Custom.Items.TeleportScroll;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Menu.netMenu;
import nativelevel.MetaShit;
import nativelevel.Attributes.AttributeInfo;
import nativelevel.Attributes.Mana;
import nativelevel.Attributes.MenuAtributos;
import nativelevel.config.Config;
import nativelevel.integration.BungeeCordKom;
import nativelevel.spec.PlayerSpec;
import nativelevel.sisteminhas.BookPortal;
import nativelevel.utils.BungLocation;
import nativelevel.sisteminhas.Html;
import nativelevel.sisteminhas.Reseter;
import nativelevel.sisteminhas.XP;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class CmdFalar implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        //if(cs.isOp()) {
        String nego = args[0];
        Player p = Bukkit.getPlayer(nego);
        if (nego != null) {

            StringBuilder msg = new StringBuilder();
            for (int x = 1; x < args.length; x++) {
                msg.append(args[x] + " ");
            }
            p.sendMessage(msg.toString().replaceAll("&", "§"));
        }
        //}
        return true;
    }

}
