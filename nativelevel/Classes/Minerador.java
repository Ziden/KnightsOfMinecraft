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
package nativelevel.Classes;

import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.CustomEvents.BlockHarvestEvent;
import org.bukkit.util.Vector;
import org.bukkit.event.player.PlayerInteractEvent;
import nativelevel.Listeners.GeneralListener;
import nativelevel.Jobs;
import nativelevel.Lang.PT;
import nativelevel.Menu.Menu;
import nativelevel.sisteminhas.ClanLand;
import org.bukkit.event.block.Action;
import nativelevel.KoM;
import nativelevel.MetaShit;
import nativelevel.Attributes.Mana;
import nativelevel.Attributes.Stamina;
import nativelevel.Equipment.EquipManager;
import nativelevel.bencoes.TipoBless;
import nativelevel.config.ConfigKom;
import nativelevel.config.ItemJob;
import nativelevel.rankings.Estatistica;
import nativelevel.rankings.RankDB;
import nativelevel.spec.PlayerSpec;
import nativelevel.sisteminhas.XP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class Minerador {

    public static void axaItems(BlockBreakEvent ev) {
        int lvl = Jobs.getJobLevel("Minerador", ev.getPlayer());
        if (lvl == 1) {
            int random = Jobs.rnd.nextInt(100);
            if (random <= 1) {
                ev.getPlayer().getWorld().dropItemNaturally(ev.getBlock().getLocation(), new ItemStack(Material.SULPHUR, 1));
            } else if (random == 2) {
                ev.getPlayer().getWorld().dropItemNaturally(ev.getBlock().getLocation(), new ItemStack(Material.MOSSY_COBBLESTONE, 1));
            } else if (random == 9) {
                ev.getPlayer().getWorld().dropItemNaturally(ev.getBlock().getLocation(), new ItemStack(Material.GLOWSTONE_DUST, 1));
            }
        }
    }

    public static Material materiaPrima(Material m) {
        if (m == Material.IRON_ORE) {
            return Material.IRON_INGOT;
        }
        if (m == Material.COAL_ORE) {
            return Material.COAL;
        }
        if (m == Material.GOLD_ORE) {
            return Material.GOLD_INGOT;
        }
        return null;
    }

    public static void blockharv(BlockHarvestEvent ev) {

    }

    public class rearma implements Runnable {

        ItemStack i;
        Player p;
        int s;

        public rearma(ItemStack item, Player pl, int slot) {
            i = item;
            p = pl;
            s = slot;
        }

        @Override
        public void run() {
            if (s >= 0) {
                this.p.getInventory().setItem(s, this.i);
            } else {
                Item item = this.p.getWorld().dropItem(this.p.getLocation(), this.i);
                item.setPickupDelay(0);
            }
        }
    }

    public static void escala(PlayerInteractEvent ev) {
        if (ev.getPlayer().getWorld().getName().equalsIgnoreCase("dungeon")) {
            return;
        }
        
        if(ev.getHand()==EquipmentSlot.OFF_HAND)
            return;

        if (Jobs.getJobLevel("Minerador", ev.getPlayer()) == 1) {
            if (ev.getPlayer().getVelocity().getY() <= 0 && ev.getAction() == Action.RIGHT_CLICK_BLOCK) {

                if (ev.getClickedBlock().getRelative(BlockFace.UP).getType() == Material.AIR) {
                    return;
                }
                if (ev.getClickedBlock().getRelative(BlockFace.UP).getRelative(BlockFace.UP).getType() == Material.AIR) {
                    return;
                }

                if (Stamina.spendStamina(ev.getPlayer(), 8)) {
                    Vector velo = ev.getPlayer().getVelocity();
                    velo.setY(0.75);
                    ev.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "* cleck *");
                    ev.getPlayer().setVelocity(velo);
                    PlayEffect.play(VisualEffect.FIREWORKS_SPARK, ev.getClickedBlock().getLocation(), "num:5");
                    ItemStack mao = ev.getPlayer().getItemInHand();
                    mao.setDurability((short) (mao.getDurability() + 1));
                    //EntityPlayer p = ((CraftPlayer)ev.getPlayer()).getHandle();
                    //p.playerConnection.sendPacket(new PacketPlayInArmAnimation());
                    if (mao.getDurability() >= mao.getType().getMaxDurability()) {
                        ev.getPlayer().setItemInHand(null);
                    }
                }
            }
        }
    }

    public void tentaDerrubarArma(EntityDamageByEntityEvent ev) {
        Player p = (Player) ev.getDamager();

        if (!p.hasMetadata("disarmar")) {
            return;
        }

        String type = ClanLand.getTypeAt(p.getLocation());
        if (type.equalsIgnoreCase("SAFE") || p.getLocation().getWorld().getName().equals("dungeon")) {
            p.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Minerador") + " " + ChatColor.RED + "Voce nao pode desarmar aqui !");
            return;
        }

        if (!p.getInventory().contains(Material.GRAVEL)) {
            p.sendMessage(ChatColor.RED + "Voce precisa de cascalho para desarmar oponentes.");
            return;
        }

        KoM.gastaItem(p, p.getInventory(), Material.GRAVEL, (byte) 0);

        final Player inimigo = (Player) ev.getEntity();
        if (inimigo.getItemInHand() == null) {
            return;
        }

        if (Jobs.getJobLevel("Minerador", p) != 1) {
            return;
        }

        int custo = 35;
        int tempo = 20 * 6;
        if (PlayerSpec.temSpec(p, PlayerSpec.Desarmador)) {
            custo = 20;
            tempo = 20 * 8;
        } else if (PlayerSpec.temSpec(p, PlayerSpec.Desarmador)) {
            tempo = 20 * 2;
            custo = 50;
        }
        if (p.isSneaking() && Stamina.spendStamina(p, custo)) {

            if (Thief.taInvisivel(p)) {
                Thief.revela(p);
            }

            ItemStack namao = inimigo.getItemInHand();
            int slot = inimigo.getInventory().firstEmpty();
            inimigo.setItemInHand(null);

            MetaShit.setMetaObject("disarm", inimigo, "1");
            Runnable r = new Runnable() {
                public void run() {
                    inimigo.removeMetadata("disarm", KoM._instance);
                    inimigo.sendMessage(ChatColor.GREEN + "Sua mao se recupera");
                }
            };
            Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r, tempo);
            Bukkit.getScheduler().scheduleSyncDelayedTask(nativelevel.KoM._instance, new rearma(namao, inimigo, slot));
            EquipManager.checkEquips(inimigo);
            inimigo.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Minerador") + " " + ChatColor.GOLD + "Voce foi desarmado pela picaretada");
            p.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Minerador") + " " + ChatColor.GOLD + "Voce desarmou seu inimigo com sua picareta!");
        }
    }
}
