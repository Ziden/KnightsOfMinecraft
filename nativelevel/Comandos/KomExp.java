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

import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Classes.Farmer;
import nativelevel.Custom.Items.TeleportScroll;
import nativelevel.Custom.Items.ValeSorteio;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Menu.netMenu;
import nativelevel.MetaShit;
import nativelevel.Attributes.AttributeInfo;
import nativelevel.Attributes.Mana;
import nativelevel.Attributes.MenuAtributos;
import nativelevel.bencoes.TipoBless;
import nativelevel.integration.BungeeCordKom;
import nativelevel.lojaagricola.ConfigLoja;
import nativelevel.lojaagricola.LojaAgricola;
import nativelevel.lojaagricola.Vendavel;
import nativelevel.skills.SkillMaster;
import nativelevel.spec.PlayerSpec;
import nativelevel.sisteminhas.BookPortal;
import nativelevel.utils.BungLocation;
import nativelevel.sisteminhas.Html;
import nativelevel.sisteminhas.IronGolem;
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

public class KomExp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        if (cs instanceof Player) {
            Player p = (Player) cs;
            if(!p.isOp()) 
                return true;
            if(args.length == 0) {
                p.sendMessage("/verexp <nivel>");
                p.sendMessage("/verexp ganhar <xp>");
            } else if(args.length == 1) {
                try {
                    int nivel = Integer.valueOf(args[0]);
                    if(nivel > 100) {
                        p.sendMessage("Nivel max eh 100 manolo...");
                        return true;
                    } else if(nivel < 1) {
                        p.sendMessage("Nivel minimo eh 1 manolo...");
                        return true;
                    }
                    XP.debugLevel(p, nivel-1);
                } catch(Exception e) {
                    p.sendMessage("Numero invalido");
                }
            } else if(args.length == 2) {
                if(args[0].equalsIgnoreCase("ganhar")) {
                    int xp = Integer.valueOf(args[1]);
                    XP.changeExp(p, xp, 1);
                }
            }
        }
        return true;
    }
}
