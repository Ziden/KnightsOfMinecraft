package nativelevel.komquista.managers;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import nativelevel.KoM;
import nativelevel.komquista.DB;
import nativelevel.komquista.KomQuista;
import nativelevel.komquista.utils.ChatUtils;
import nativelevel.phatloots.PhatLootsAPI;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.managers.ClanManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitScheduler;

public class KomQuistaManager {

    public static boolean isAbrindo() {
        return taabrindo;
    }

    public static boolean isTreino() {
        return modotreino;
    }

    public static boolean isAberto() {
        return aberto;
    }

    private static boolean aberto = false;
    private static boolean taabrindo = false;
    private static boolean modotreino = false;
    private static int task = -1;
    private static long tempoinicial;

    public static void domina(String tag, Location signloc) {
        if (signloc != null) {
            for (int x = 0; x < 8; x += 2) {
                for (int z = 0; z < 8; z += 2) {
                    int y = signloc.getWorld().getHighestBlockAt(signloc).getY();
                    signloc.getWorld().strikeLightningEffect(new Location(signloc.getWorld(), signloc.getX() + x, y, signloc.getZ() + z));
                }
            }
        }

        Bukkit.broadcastMessage("§9• §eA guilda §c" + tag + "§e dominou o castelo!");
        ControleTempos.domina(tag);
    }

    public static void abre(final boolean valepremio) {
        if ((!aberto) && (!taabrindo)) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                ChatUtils.SendPreffix(p);
                p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 99.0F, 1.0F);
            }
            modotreino = !valepremio;
            taabrindo = true;
            Bukkit.broadcastMessage("§9• §eSendo aberto em 2 minutos!");

            Bukkit.getScheduler().scheduleSyncDelayedTask( KoM._instance, new Runnable() {

                public void run() {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        ChatUtils.SendPreffix(p);
                        p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_LARGE_BLAST, 99.0F, 1.0F);
                    }
                    Bukkit.broadcastMessage("§9• §e§lEvento Aberto!");
                    Bukkit.broadcastMessage("§9• §ePrepare sua guilda e venha dominar o castelo!");

                    if (valepremio) {
                        Bukkit.broadcastMessage("§9• §c§lValendo premios!");
                    }
                    Bukkit.broadcastMessage("§9• §e§nEvento acaba em 30 minutos!");

                    String last = DB.getLast();
                    if ((last != null) && (!KomQuistaManager.modotreino)) {
                        KomQuistaManager.domina(last, null);
                    }

                    for (Player p : Bukkit.getOnlinePlayers()) {
                        ChatUtils.SendSuffix(p);
                    }
                    KomQuistaManager.aberto = true;
                    
                    KomQuistaManager.taabrindo = false;

                    KomQuistaManager.tempoinicial = System.currentTimeMillis();
                    
                    KomQuistaManager.startMessages();

                    Runnable r = new Runnable() {
                        public void run() {
                            if ((KomQuistaManager.aberto) || (KomQuistaManager.taabrindo)) {
                                KomQuistaManager.task -= 1;
                                KomQuistaManager.fecha();
                            }
                        }
                    };
                    
                    KomQuistaManager.task = Bukkit.getScheduler().scheduleSyncDelayedTask( KoM._instance,r , 36000L);
                    
                    
                }
            }, 2400L);

            for (Player p : Bukkit.getOnlinePlayers()) {
                ChatUtils.SendSuffix(p);
            }
        }
    }

    public static void cancelMessage() {
        if (messagetask != -1) {
            Bukkit.getScheduler().cancelTask(messagetask);
            messagetask = -1;
        }
    }

    public static int messagetask = -1;

    public static int getTempoRestante() {
        int minutos = 30 - (int) ((System.currentTimeMillis() - tempoinicial) / 1000L) / 60;
        return minutos;
    }

    public static void expulsadoCastelo(Clan c) {
        for (Player p : Bukkit.getWorld("woe").getPlayers()) {
            if (p.isOp()) {
                return;
            }
            ClanPlayer cp = KomQuista.getClanManager().getClanPlayer(p);
            if (cp == null) {
                p.teleport(Bukkit.getWorld("WoE").getSpawnLocation());
                ChatUtils.sendMessage(p, "§cVocê foi expulso do KomQuista pela guilda " + c.getName() + " !");
            } else if (cp.getClan() != null) {
                if (cp.getClan().getTag().equalsIgnoreCase(c.getTag())) {
                    p.setHealth(p.getMaxHealth());
                    p.getInventory().addItem(new ItemStack[]{new ItemStack(Material.BREAD, 10)});
                    ChatUtils.sendMessage(p, "§aVocê foi curado!");
                    p.updateInventory();
                } else {
                    p.teleport(Bukkit.getWorld("woe").getSpawnLocation());
                    ChatUtils.sendMessage(p, "§cVocê foi expulso do KomQuista pela guilda " + c.getName() + " !");
                }
            }
        }
    }

    public static void startMessages() {
        messagetask = Bukkit.getScheduler().scheduleSyncRepeatingTask( KoM._instance, new Runnable() {
            public void run() {
                int minutos = KomQuistaManager.getTempoRestante();
                String modo = KomQuistaManager.modotreino ? "treino" : "recompensa";
                ChatUtils.broadcast("§aEstá aberto em modo §c" + modo + "§a ainda falta §c" + minutos + "§a minutos !");
            }
        }, 2400L, 2400L);
    }

    public static ChatColor getRandomColor(ChatColor c) {
        ChatColor escolhida = ChatColor.values()[new java.util.Random().nextInt(ChatColor.values().length)];
        while ((escolhida == c) || (escolhida == ChatColor.BOLD) || (escolhida == ChatColor.ITALIC) || (escolhida == ChatColor.MAGIC) || (escolhida == ChatColor.UNDERLINE) || (escolhida == ChatColor.STRIKETHROUGH) || (escolhida == ChatColor.BLACK) || (escolhida == ChatColor.DARK_GRAY) || (escolhida == ChatColor.RESET)) {
            escolhida = ChatColor.values()[new java.util.Random().nextInt(ChatColor.values().length)];
        }
        return escolhida;
    }

    public static void fecha() {
        ControleTempos.termina();
        cancelMessage();
        for (Player p : Bukkit.getOnlinePlayers()) {
            ChatUtils.SendPreffix(p);
        }
        Bukkit.broadcastMessage("§9• §9§lEvento foi finalizado!");
        Object guildas = ControleTempos.getGuildasByTime();
        ChatColor last = ChatColor.GOLD;
        if (task != -1) {
            Bukkit.getScheduler().cancelTask(task);
            task = -1;
        }
        nativelevel.komquista.listeners.EventosKomQuista.vidanova = false;
        int x;
        if (((List) guildas).size() > 0) {
            for (x = 0; x < ((List) guildas).size(); x++) {
                Guilda g = (Guilda) ((List) guildas).get(x);
                final String tag = g.getTag();
                Bukkit.broadcastMessage("§9• §r" + last + "" + ChatUtils.getNumber(x + 1) + " - A guilda §l" + g.getTag() + ChatColor.RESET + "" + last + " conseguiu ficar com o castelo por §n" + g.getTotalTime() + ChatColor.RESET + "" + last + " segundos!");
                if (x == 0) {
                    Clan c = KomQuista.getClanManager().getClan(tag);
                    if (!modotreino) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask( KoM._instance, new Runnable() {
                            public void run() {
                                Clan cl = KomQuista.getClanManager().getClan(tag);
                                if (cl != null) {
                                    for (ClanPlayer cp : cl.getOnlineMembers()) {
                                        ChatUtils.SendPreffix(cp.toPlayer());
                                    }
                                    KomQuistaManager.daPremio(tag);
                                    DB.addWin(tag);
                                    KomQuistaManager.sendClanMessage(tag, "§9• §bNo proximo KomQuista(Valendo Premios) sua guilda começara com o castelo!");
                                    DB.setLast(tag);

                                    for (ClanPlayer cp : cl.getOnlineMembers()) {
                                        ChatUtils.SendSuffix(cp.toPlayer());
                                    }
                                }
                            }
                        }, 60L);
                    }
                }

                last = ChatColor.GREEN;
            }

        } else {
            Bukkit.broadcastMessage("§9• §cE não teve vencedor!");
        }

        aberto = false;
        Guilda.clear();

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getWorld().getName().equalsIgnoreCase("WoE")) {
                ChatUtils.SendPreffix(p);
                p.teleport(Bukkit.getWorld("vila").getSpawnLocation());
            }
        }
    }

    public static void sendClanMessage(Clan c, String message) {
        for (ClanPlayer cp : c.getAllMembers()) {
            Player p = cp.toPlayer();
            if (p != null) {
                p.sendMessage(message);
            }
        }
    }

    public static void sendClanMessage(String tag, String message) {
        Clan c = KomQuista.getClanManager().getClan(tag);
        if (c != null) {
            for (ClanPlayer cp : c.getAllMembers()) {
                Player p = cp.toPlayer();
                if (p != null) {
                    p.sendMessage(message);
                }
            }
        }
    }

    public static HashMap<Location, String> baus = new HashMap();

    public static void daPremio(String tag) {
        final Clan cl = KomQuista.getClanManager().getClan(tag);
        if (cl != null) {
            for (ClanPlayer cp : cl.getOnlineMembers()) {
                cp.toPlayer().sendMessage("§9• §aVocê ganhou o premio do KomQuista individual!");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "loot give " + cp.getName() + " komquistai");
            }
            if (cl.getHomeLocation() != null) {
                Block b = cl.getHomeLocation().getWorld().getHighestBlockAt(cl.getHomeLocation().getBlockX(), cl.getHomeLocation().getBlockZ());
                b.setType(Material.CHEST);
                if (b.getType() == Material.CHEST) {
                    b.getWorld().strikeLightningEffect(b.getLocation());
                    Chest c = (Chest) b.getState();
                    List<ItemStack> itemsl = PhatLootsAPI.rollForLoot("komquista").getItemList();
                    for (int s = 0; s < itemsl.size(); s++) {
                        c.getInventory().setItem(s, (ItemStack) itemsl.get(s));
                    }
                    baus.put(b.getLocation(), cl.getTag());
                    sendClanMessage(cl, "§9• §6Verifique na casa da sua guilda o premio!");
                    sendClanMessage(cl, "§9• §6Somente lideres podem abrir o bau");
                    sendClanMessage(cl, "§9• §6Dentro de 5 minutos ele abre para todos!");

                    final Location bauloc = b.getLocation();
                    Bukkit.getScheduler().scheduleSyncDelayedTask( KoM._instance, new Runnable() {
                        public void run() {
                            if (KomQuistaManager.baus.containsKey(bauloc)) {
                                for (ClanPlayer cp : cl.getOnlineMembers()) {
                                    ChatUtils.SendPreffix(cp.toPlayer());
                                }
                                KomQuistaManager.sendClanMessage(cl, "§9• §6Bau livre para todos!");
                                for (ClanPlayer cp : cl.getOnlineMembers()) {
                                    ChatUtils.SendSuffix(cp.toPlayer());
                                }
                                KomQuistaManager.baus.remove(bauloc);
                            }
                        }
                    }, 6000L);
                }
            }
        }
    }

    public static boolean tacaPlayernoKomQ(Player p) {
        if (isAbrindo()) {
            ChatUtils.sendMessage(p, "§aKomQuista está sendo aberto aguarde!");
            return false;
        }
        if (!isAberto()) {
            ChatUtils.sendMessage(p, "§aKomQuista está fechado!");
            return false;
        }
        ClanPlayer cp = KomQuista.getClanManager().getClanPlayer(p);
        if (cp == null) {
            ChatUtils.sendMessage(p, "§aVocê precisa de uma guilda para entrar no KomQuista!");
            return false;
        }
        if (cp.getClan() == null) {
            ChatUtils.sendMessage(p, "§aVocê precisa de uma guilda para entrar no KomQuista!");
            return false;
        }
        if (cp.getClan().getHomeLocation() == null) {
            ChatUtils.sendMessage(p, "§aVocê precisa de uma casa setada para entrar no KomQuista!");
            return false;
        }
        ChatUtils.sendMessage(p, "§6Você entrou no KoMQuista, escolha um portal para ir a luta!");
        p.teleport(Bukkit.getWorld("WoE").getSpawnLocation());

        return true;
    }
}
