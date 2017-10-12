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

import org.bukkit.DyeColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import java.util.UUID;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Classes.Thief;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.MetaShit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class Atadura extends CustomItem {

    public static final int DISTANCIA_ANDAR = 6;

    public Atadura() {
        super(Material.CARPET, L.m("Atadura"), L.m("Para simples curativos"), CustomItem.INCOMUM);
    }

    @Override
    public void posCriacao(ItemStack ss) {
        ss.getData().setData(DyeColor.YELLOW.getDyeData());
    }

    private class AtaduraRun implements Runnable {

        private UUID u;
        private int duracao;
        private int qtosFaltam;
        public double vaiCurar;
        private long inicio;
        public int taskId;
        private Location inicial;

        public AtaduraRun(int duracao, UUID u, int vaiCurar, Location l) {
            this.u = u;
            inicio = System.currentTimeMillis() / 1000;
            this.duracao = duracao;
            this.qtosFaltam = duracao;
            this.vaiCurar = vaiCurar;
            this.inicial = l;
        }

        public void run() {

            Player p = Bukkit.getPlayer(u);
            if (p == null) {
                Bukkit.getScheduler().cancelTask(taskId);
                return;
            }

            KoM.efeitoBlocos(p, Material.WOOL);

            if(!p.getLocation().getWorld().getName().equalsIgnoreCase(inicial.getWorld().getName())) {
                p.sendMessage(ChatColor.RED + "[ : : ] " + ChatColor.GREEN + "Você se movimentou muito e parou de se curar.");
                p.removeMetadata("atadura", KoM._instance);
                Bukkit.getScheduler().cancelTask(taskId);
                return;
            }
            
            double distancia = p.getLocation().distance(inicial);
            if (distancia > DISTANCIA_ANDAR) {
                p.sendMessage(ChatColor.RED + "[ : : ] " + ChatColor.GREEN + "Você se movimentou muito e parou de se curar.");
                p.removeMetadata("atadura", KoM._instance);
                Bukkit.getScheduler().cancelTask(taskId);
                return;
            }

            inicial = p.getLocation().clone();

            qtosFaltam--;
            if (qtosFaltam < 0) {

                if (p.hasPotionEffect(PotionEffectType.POISON)) {
                    p.sendMessage(ChatColor.RED + "Voce parou de se curar por causa do veneno");
                    Bukkit.getScheduler().cancelTask(taskId);
                    return;
                }
                if (p.getFireTicks() > 0) {
                    p.sendMessage(ChatColor.RED + "Voce parou de se curar por causa do fogo");
                    Bukkit.getScheduler().cancelTask(taskId);
                    return;
                }

                p.sendMessage(ChatColor.YELLOW + "[ : : ] " + ChatColor.GREEN + "Você terminou de se curar.");
                double vida = p.getHealth();
                vida += vaiCurar;
                if (vida > p.getMaxHealth()) {
                    vida = p.getMaxHealth();
                }
                p.setHealth(vida);
                p.removeMetadata("atadura", KoM._instance);
                Bukkit.getScheduler().cancelTask(taskId);
            } else {
                p.sendMessage(ChatColor.YELLOW + "[ : : ] " + ChatColor.GREEN + "Voce está se curando.");
            }
        }
    };

    public static void tomaDano(Player p, double dano) {
        AtaduraRun run = seCurando(p);
        if (run != null) {

            run.vaiCurar -= dano;
            if (run.vaiCurar <= 0) {
                para(p);
                p.sendMessage(ChatColor.RED + "[ : : ] " + ChatColor.GREEN + "Você não conseguiu se curar.");
            } else {
                p.sendMessage(ChatColor.YELLOW + "[ : : ] " + ChatColor.GREEN + "Seus dedos escorregaram.");
            }

            if (Jobs.rnd.nextInt(3) == 1) {

            }
        }
    }

    public static void para(Player p) {
        AtaduraRun run = seCurando(p);
        if (run != null) {
            Bukkit.getScheduler().cancelTask(run.taskId);
        }
    }

    public static AtaduraRun seCurando(Player p) {
        if (p.hasMetadata("atadura")) {
            AtaduraRun run = (AtaduraRun) MetaShit.getMetaObject("atadura", p);
            p.removeMetadata("atadura", KoM._instance);
            return run;
        }
        return null;
    }

    public static boolean podeInterromper(Player p) {
        if (p.hasPotionEffect(PotionEffectType.POISON) || p.hasPotionEffect(PotionEffectType.WITHER)) {
            p.sendMessage(ChatColor.RED + "Você não consegue aplicar as ataduras estando envenenado.");
            return true;
        }

        if (p.getFireTicks() > 0) {
            p.sendMessage(ChatColor.RED + "Voce nao consegue se curar pegando fogo !");
            return true;
        }

        if (p.getLocation().getBlock().getType() == Material.WATER) {
            p.sendMessage(ChatColor.RED + "Você nao consegue aplicar as ataduras na agua.");
            return true;
        }

        if (p.getLocation().getBlock().getType() == Material.WEB) {
            p.sendMessage(ChatColor.RED + "Você nao consegue aplicar as ataduras em um emaranhado de teias.");
            return true;
        }

        return false;
    }

    @Override
    public boolean onItemInteract(Player p) {
        AtaduraRun run = seCurando(p);
        if (run != null) {
            p.sendMessage(ChatColor.RED + "Você já está com ataduras.");
            return true;
        }

        if (p.getMaxHealth() == p.getHealth()) {
            p.sendMessage(ChatColor.RED + "Você não tem nenhum ferimento.");
            return true;
        }

        if (podeInterromper(p)) {
            return true;
        }

        int stack = p.getItemInHand().getAmount();
        stack--;
        if (stack == 0) {
            p.setItemInHand(new ItemStack(Material.AIR, 1));
        } else {
            p.getItemInHand().setAmount(stack);
        }

        if(Thief.taInvisivel(p))
            Thief.revela(p);
        
        for (Entity e : p.getNearbyEntities(10, 4, 10)) {
            if (e.getType() == EntityType.PLAYER) {
                ((Player) e).sendMessage(ChatColor.AQUA + "* " + p.getName() + " começou a aplicar ataduras *");
            }
        }

        run = new AtaduraRun(4, p.getUniqueId(), 20, p.getLocation().clone());
        MetaShit.setMetaObject("atadura", p, run);
        run.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(KoM._instance, run, 60, 60);
        p.sendMessage(ChatColor.YELLOW + "[ : : ] " + ChatColor.GREEN + "Aplicando Ataduras");
        return true;
    }
}
