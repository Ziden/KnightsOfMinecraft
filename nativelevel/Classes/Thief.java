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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import me.asofold.bpl.simplyvanish.SimplyVanish;
import me.asofold.bpl.simplyvanish.config.Flag;
import me.asofold.bpl.simplyvanish.config.VanishConfig;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.CFG;
import nativelevel.Dano;
import nativelevel.Listeners.GeneralListener;
import nativelevel.Jobs;
import nativelevel.Menu.Menu;
import nativelevel.utils.MetaUtils;
import nativelevel.MetaShit;
import nativelevel.KoM;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Lang.L;
import nativelevel.Attributes.AttributeInfo;
import nativelevel.Attributes.Mana;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.Items.Lock;
import nativelevel.sisteminhas.ChaveCadiado;
import nativelevel.sisteminhas.KomSystem;
import nativelevel.spec.PlayerSpec;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Thief extends KomSystem {

    public static void pegaItemAleatorio(Player p, Block bau) {
        Chest c = (Chest) bau.getState();
        if (c.getBlockInventory().getSize() == 0) {
            p.sendMessage(ChatColor.RED + L.m("Este bau está vazio !"));
            return;
        }

        if (!bau.hasMetadata("aberto")) {
            p.sendMessage(ChatColor.RED + L.m("Um ladino precisa abrir o bau com uma lockpick antes de rouba-lo !"));
            return;
        }

        if (Jobs.getJobLevel("Ladino", p) != 1) {
            p.sendMessage(ChatColor.RED + L.m("Apenas ladinos sabem furtar baus !"));
            return;
        }

        Clan donoDoBau = ClanLand.getClanAt(bau.getLocation());
        Clan meu = ClanLand.manager.getClanByPlayerUniqueId(p.getUniqueId());
        if (meu != null && donoDoBau != null) {
            int pontosPilhagem = ClanLand.getPtosPilagem(meu.getTag(), donoDoBau.getTag());
            if (pontosPilhagem < CFG.custoPegarItemRandom) {
                p.sendMessage(ChatColor.RED + L.m("Sua guilda precisa de % PPs para roubar o bau !", CFG.custoPegarItemRandom + ""));
                p.sendMessage(ChatColor.RED + L.m("Mate inimigos para ganhar pilhagem sobre eles !"));
                return;
            }
            int random = Jobs.rnd.nextInt(c.getBlockInventory().getSize());
            ItemStack sorteado = c.getBlockInventory().getItem(random);

            pontosPilhagem -= CFG.custoPegarItemRandom;
            int ct = 0;
            while (sorteado == null) {
                random = Jobs.rnd.nextInt(c.getBlockInventory().getSize());
                sorteado = c.getBlockInventory().getItem(random);
                ct++;
                if (ct > 25) {
                    break;
                }
            }

            if (sorteado == null) {
                p.sendMessage(ChatColor.RED + L.m("Voce nao conseguiu roubar nada !"));
                return;
            }

            p.sendMessage(ChatColor.GREEN + L.m("Voce enfiou a mão no bau e puxou um item ! "));
            //p.getInventory().addItem(sorteado);
            c.getWorld().dropItemNaturally(c.getLocation().add(0, 1, 0), sorteado);
            c.getBlockInventory().remove(sorteado);
            ClanLand.setPtosPilhagem(meu.getTag(), donoDoBau.getTag(), pontosPilhagem);

            ItemStack nota = new ItemStack(Material.PAPER, 1);
            ItemMeta meta = nota.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + L.m("Nota de Furto"));
            meta.setLore(Arrays.asList(new String[]{ChatColor.GREEN + L.m("Bau roubado por:"), ChatColor.YELLOW + p.getName(), ChatColor.GREEN + "Guilda:", ChatColor.YELLOW + meu.getTag()}));
            nota.setItemMeta(meta);
            if (!c.getBlockInventory().contains(nota)) {
                c.getBlockInventory().addItem(nota);
            }

        }
    }

    public static void stealFullChest(Player p, Block bau) {
        Chest c = (Chest) bau.getState();
        if (c.getBlockInventory().getSize() == 0) {
            p.sendMessage(ChatColor.RED + L.m("Este bau está vazio !"));
            return;
        }

        if (!bau.hasMetadata("aberto")) {
            p.sendMessage(ChatColor.RED + L.m("Um ladino precisa abrir o bau com uma lockpick antes de rouba-lo !"));
            return;
        }

        if (Jobs.getJobLevel("Ladino", p) != 1) {
            p.sendMessage(ChatColor.RED + L.m("Apenas ladinos sabem furtar baus !"));
            return;
        }

        Clan donoDoBau = ClanLand.getClanAt(bau.getLocation());
        Clan meu = ClanLand.manager.getClanByPlayerUniqueId(p.getUniqueId());
        if (meu != null && donoDoBau != null) {
            int pontosPilhagem = ClanLand.getPtosPilagem(meu.getTag(), donoDoBau.getTag());
            if (pontosPilhagem < CFG.custoPegarBau) {
                p.sendMessage(ChatColor.RED + L.m("Sua guilda precisa de % PPs para roubar o bau !", CFG.custoPegarBau + ""));
                p.sendMessage(ChatColor.RED + L.m("Mate inimigos para ganhar pilhagem sobre eles !"));
                return;
            }
            int random = Jobs.rnd.nextInt(c.getBlockInventory().getSize());
            ItemStack sorteado = c.getBlockInventory().getItem(random);

            pontosPilhagem -= CFG.custoPegarBau;
            int ct = 0;

            p.sendMessage(ChatColor.GREEN + L.m("Voce sugou os items do bau ! ! "));

            for (ItemStack ss : c.getBlockInventory().getContents()) {
                if (ss != null) {
                    c.getWorld().dropItemNaturally(c.getLocation().add(0, 1, 0), ss);
                }
            }
            c.getBlockInventory().clear();

            ClanLand.setPtosPilhagem(meu.getTag(), donoDoBau.getTag(), pontosPilhagem);
            ItemStack nota = Thief.getNota(p);
            if (!c.getBlockInventory().contains(nota)) {
                c.getBlockInventory().addItem(nota);
            }
            p.getItemInHand().setDurability((short) (p.getItemInHand().getDurability() + 80));
        }
    }

    private static class Lockpicker implements Runnable {

        public UUID player;
        public Chest chest;
        public int step;
        public int id;
        public int dif;

        @Override
        public void run() {

            Player p = Bukkit.getPlayer(player);
            if (p == null) {
                cancel();
            } else {

                if (!p.getWorld().getName().equalsIgnoreCase(chest.getWorld().getName()) || p.getLocation().distance(chest.getLocation()) > 3) {
                    cancel();
                    p.sendMessage(ChatColor.RED + "Voce ficou muito longe do baú");
                }

                if (step > 6) {

                    int sucesso = Jobs.hasSuccess(dif, "Ladino", p);
                    if (sucesso == Jobs.success) {

                        MetaShit.setMetaString("aberto", chest, "sim");

                        boolean quebrou = false;
                        ItemStack[] coisas = chest.getBlockInventory().getContents();
                        for (int x = 0; x < coisas.length; x++) {
                            ItemStack ss = chest.getBlockInventory().getContents()[x];
                            if (ss != null) {
                                if (ss.getType() == Material.OBSERVER) {
                                    CustomItem item = CustomItem.getItem(ss);
                                    // it has a lock !
                                    if (item != null && item instanceof Lock) {
                                        coisas[x] = new ItemStack(Material.AIR);
                                        quebrou = true;
                                    }
                                }
                            }
                        }
                        p.sendMessage(Menu.getSimbolo("Ladino") + ChatColor.GREEN + L.m("Voce conseguiu quebrar o trinco do baú !"));

                        ItemStack nota = Thief.getNota(p);

                        chest.getBlockInventory().setContents(coisas);

                        if (!chest.getBlockInventory().contains(nota)) {
                            chest.getBlockInventory().addItem(nota);
                        }
                         cancel();
                    } else {
                        p.sendMessage(Menu.getSimbolo("Ladino") + ChatColor.RED + L.m("Voce nao conseguiu abrir o baú !"));
                    }
                    cancel();
                } else {
                    step++;
                    p.sendMessage(ChatColor.GREEN + "Voce está tentando abrir o baú...");
                    p.getWorld().playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
                    KoM.efeitoBlocos(chest.getBlock(), Material.CHEST);
                }

            }

        }

        public void cancel() {
            Bukkit.getScheduler().cancelTask(id);
            Player p = Bukkit.getPlayer(player);
            if (p != null) {
                p.removeMetadata("arrombando", KoM._instance);
            }
        }

    }

    public static void zoiudo(Player p, Chest chest) {

        int lockLevel = ChaveCadiado.getLockLevel(chest.getBlock());

        if (lockLevel > 0) {
            p.sendMessage(ChatColor.RED + "Este baú possui uma tranca.");
            if (Jobs.getJobLevel(Jobs.Classe.Ladino, p) == Jobs.TipoClasse.PRIMARIA) {
                p.sendMessage(ChatColor.RED + L.m("Voce pode tentar arrombar baús com lockpicks !!"));
            }
            return;
        }

        Inventory bauIlusorio = Bukkit.createInventory(p, chest.getBlockInventory().getSize(), L.m("Zoiudo !"));
        for (int i = 0; i < chest.getBlockInventory().getSize(); i++) {
            if (chest.getBlockInventory().getItem(i) == null) {
                continue;
            }
            ItemStack item = chest.getBlockInventory().getItem(i).clone();
            if (item != null) {
                MetaUtils.appendLore(item, ChatColor.RED + "!");
                bauIlusorio.setItem(i, item);
            }
        }
        p.openInventory(bauIlusorio);
    }

    public static void bisbilhota(Player p, Block b) {

        if (p.hasMetadata("arrombando")) {
            p.sendMessage(ChatColor.RED + "Voce ja está arrombando o baú, aguarde terminar.");
            return;
        }

        if (b.getType() == Material.CHEST) {
            Chest c = (Chest) b.getState();
            int dif = 5;
            boolean gasta = true;
            int lvl = Jobs.getJobLevel("Ladino", p);
            if (ClanLand.permission.has(p, "kom.verbaus")) {
                lvl = 1;
                dif = 0;
                gasta = false;
            }
            if (lvl == 0) {
                p.sendMessage(Menu.getSimbolo("Ladino") + ChatColor.RED + L.m("Apenas ladinos podem usar isto !"));
                return;
            }

            if (!Mana.spendMana(p, 40)) {
                return;
            }

            int lockLevel = ChaveCadiado.getLockLevel(b);
            if (lockLevel != 0) {
                dif = lockLevel;
            }

            if (p.getItemInHand().getAmount() > 1) {
                p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
            } else {
                p.setItemInHand(null);
            }

            Lockpicker lock = new Lockpicker();
            MetaShit.setMetaObject("arrombando", p, 1);
            lock.chest = c;
            lock.dif = dif;
            lock.player = p.getUniqueId();
            lock.step = 0;
            lock.id = Bukkit.getScheduler().scheduleSyncRepeatingTask(KoM._instance, lock, 20 * 2, 20 * 2);
            p.sendMessage(ChatColor.GREEN + "Voce começou a tentar arrombar o baú.");

        }
    }

    public static ItemStack getNota(Player p) {
        ItemStack nota = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = nota.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + L.m("Nota de Furto"));
        meta.setLore(Arrays.asList(new String[]{ChatColor.GREEN + L.m("Bau roubado por:"), ChatColor.YELLOW + p.getName()}));
        nota.setItemMeta(meta);
        return nota;
    }

    public static String getLadrao(ItemStack nota) {
        if (nota == null) {
            return null;
        }
        ItemMeta meta = nota.getItemMeta();
        if (meta.getDisplayName() != null && ChatColor.stripColor(meta.getDisplayName()).equalsIgnoreCase("Nota de Furto")) {
            if (meta != null) {
                List<String> lore = meta.getLore();
                if (lore != null && lore.size() >= 2) {
                    return ChatColor.stripColor(lore.get(1));
                }
            }
        }
        return null;
    }

    @EventHandler
    public void invClick(InventoryClickEvent ev) {
        if (ev.getCurrentItem() != null && ev.getCurrentItem().getType() == Material.PAPER) {
            String ladrao = getLadrao(ev.getCurrentItem());
            if (ladrao != null && ladrao.equalsIgnoreCase(ev.getWhoClicked().getName())) {
                ev.setCancelled(true);
            }
        }
    }

    public static void desviaTiro(Entity p, int qtd) {
        Vector destino = p.getVelocity();
        if (Jobs.rnd.nextBoolean()) {
            destino.add(new Vector(Jobs.rnd.nextInt(qtd) - (qtd / 2) / 10, Jobs.rnd.nextInt(qtd) - (qtd / 2) / 10, Jobs.rnd.nextInt(qtd) - (qtd / 2) / 10));
        } else {
            destino.subtract(new Vector(Jobs.rnd.nextInt(qtd) - (qtd / 2) / 10, Jobs.rnd.nextInt(qtd) - (qtd / 2) / 10, Jobs.rnd.nextInt(qtd) - (qtd / 2) / 10));
        }
        p.setVelocity(destino);
    }

    public static boolean taInvisivel(Player p) {
        VanishConfig cfg = SimplyVanish.getVanishConfig(p.getName(), true);
        // KnightsOfMinecraft.log.info("VANISHED: "+p.getName()+" "+cfg.vanished.state);
        return (SimplyVanish.isVanished(p) || cfg.vanished.state);

    }

    public static void ficaInvisivel(final Player p, int tempo) {
        PlayEffect.play(VisualEffect.SMOKE, p.getLocation(), "num:1");
        int idRevela = Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, new Runnable() {
            public void run() {
                if (SimplyVanish.isVanished(p) && !p.isOp()) {
                    SimplyVanish.setVanished(p, false);
                    p.removePotionEffect(PotionEffectType.INVISIBILITY);
                    p.sendMessage(ChatColor.RED + L.m("Voce se revelou !"));
                }
            }
        }, tempo);
        SimplyVanish.setVanished(p, true);
        MetaShit.setMetaObject("idRevela", p, idRevela);
        VanishConfig cfg = SimplyVanish.getVanishConfig(p.getName(), true);
        cfg.damage.state = true;
        cfg.notify.state = false;
        cfg.interact.state = true;
        cfg.attack.state = true;
        cfg.target.state = true;
        cfg.vanished.state = true;
        SimplyVanish.setVanishConfig(p.getName(), cfg, true);
        //SimplyVanish.setVanishConfig(null, null, true);
        //p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, tempo, 1));
    }

    public static void revela(Player p) {

        if (!taInvisivel(p)) {
            return;
        }

        try {
            if (ClanLand.permission.has(p, "vanish.vanish")) {
                return;
            }
            if (!p.isOp()) {

                if (p.hasMetadata("idRevela")) {

                    int task = (int) MetaShit.getMetaObject("idRevela", p);
                    Bukkit.getScheduler().cancelTask(task);
                }
                if (Thief.taInvisivel(p)) {
                    PlayEffect.play(VisualEffect.SPELL, p.getLocation(), "num:1");
                    p.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Ladino") + " " + ChatColor.RED + L.m("Voce foi revelado !"));
                }
                SimplyVanish.setVanished(p, false);
                VanishConfig cfg = SimplyVanish.getVanishConfig(p.getName(), true);
                cfg.notify.state = false;
                cfg.vanished.state = false;
                SimplyVanish.setVanished(p, false);
                SimplyVanish.setVanishConfig(p.getName(), cfg, true);
                // SimplyVanish.setVanished(p, false);
            }
            p.removeMetadata("idRevela", KoM._instance);
        } catch (Exception e) {
            KoM.log.info("ERRO NO VANISH: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void atiraFlecha(Player p, Projectile flecha) {
        if (!(flecha.getShooter() instanceof Player)) {
            return;
        }

        if (taInvisivel(p)) {
            PlayEffect.play(VisualEffect.SMOKE, p.getLocation(), "");
        }

        //AttributeInfo info = KnightsOfMania.database.getAtributos(p);
        //MetaShit.setMetaObject("modDano", flecha, (Attributes.calcArcheryDamage(info.attributes.get(Attr.dexterity))));
        if (Jobs.getJobLevel("Ladino", p) != 1) {
            int sucesso = Jobs.hasSuccess(65, "Ladino", (Player) p);
            if (sucesso == Jobs.fail) {
                Thief.desviaTiro(flecha, 2);
            }
        }

        if (p.getLevel() < 10) {
            GeneralListener.givePlayerExperience(1, p);
        }
        //p.getWorld().spawn(p.getLocation(), ExperienceOrb.class).setExperience(1);
    }

    public static void throwEnderPearl(PlayerInteractEvent ev) {
        if (!Mana.spendMana(ev.getPlayer(), 35)) {
            ev.setCancelled(true);
            return;
        }
        if (ev.getPlayer().getWorld().getName().equalsIgnoreCase("dungeon")) {
            ev.getPlayer().sendMessage(ChatColor.AQUA + Menu.getSimbolo("Ladino") + " " + ChatColor.GOLD + L.m("Aqui nao"));
            ev.setCancelled(true);
            return;
        }
        int arc = Jobs.getJobLevel("Ladino", ev.getPlayer());
        if (arc != 1) {
            ev.getPlayer().sendMessage(ChatColor.AQUA + Menu.getSimbolo("Ladino") + " " + ChatColor.GOLD + L.m("Voce falhou em usar a Ender Pearl !"));
            ev.setCancelled(true);
            return;
        }
    }

    public static void bonusDanoDeLonge(final EntityDamageByEntityEvent event) {
        if (!(((Projectile) event.getDamager()).getShooter() instanceof Player)) {
            return;
        }
        if (event.getDamager().getType() != EntityType.ARROW) {
            return;
        }
        Player p = ((Player) ((Projectile) event.getDamager()).getShooter());

        if (event.getEntity() instanceof Player) {
            int nivel = Jobs.getJobLevel("Ladino", p);
            if (p.getLocation().getWorld().getName().equalsIgnoreCase("dungeon") || p.getLocation().getWorld().getEnvironment() != Environment.NORMAL) {
                return;
            }
            double distancia = ((Entity) ((Projectile) event.getDamager()).getShooter()).getLocation().distance(event.getEntity().getLocation());
            if (distancia >= 18 && nivel == 1) {
                event.setDamage(event.getDamage() + 15);
                p.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Ladino") + " " + ChatColor.BLUE + "Sniper Shot !");
                event.getEntity().getWorld().playEffect(event.getEntity().getLocation(), Effect.SMOKE, 20);
                event.getEntity().getWorld().playEffect(event.getEntity().getLocation(), Effect.SMOKE, 20);
                GeneralListener.givePlayerExperience(5, p);
            }
        }
        if (PlayerSpec.temSpec(p, PlayerSpec.Assassino)) {
            event.setDamage(event.getDamage() * 0.8);
        } else if (PlayerSpec.temSpec(p, PlayerSpec.Ranger)) {
            event.setDamage(event.getDamage() * 1.2);
        }
        KoM.dano.mostraDano(p, event.getDamage(), Dano.BATI);
    }
}
