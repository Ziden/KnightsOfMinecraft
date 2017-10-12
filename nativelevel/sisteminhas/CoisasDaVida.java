package nativelevel.sisteminhas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import nativelevel.KoM;
import nativelevel.sisteminhas.KomSystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class CoisasDaVida extends KomSystem {

    public Random rnd = new Random();

    public void rolaCom(Player p) {
        Acontecimento rolou = (Acontecimento) this.acontecimentos.get(this.rnd.nextInt(this.acontecimentos.size()));
        rolou.acontece(p);
    }

    public void onEnable() {
        acontecemCoisasEmNossasVidas();
        Runnable r = new Runnable() {
            public void run() {
                Player random = (Player) Bukkit.getServer().getOnlinePlayers().toArray()[rnd.nextInt(Bukkit.getServer().getOnlinePlayers().toArray().length)];
                if (random != null) {
                    Acontecimento rolou = (Acontecimento) acontecimentos.get(rnd.nextInt(acontecimentos.size()));
                    rolou.acontece(random);
                }
            }
        };
        Bukkit.getScheduler().scheduleSyncRepeatingTask(KoM._instance, r, 12000L, 12000L);
    }

    public static void todosVeem(Player p, String aconteceu) {
        for (Entity e : p.getNearbyEntities(9.0D, 9.0D, 9.0D)) {
            if ((e.getType() == EntityType.PLAYER) && (e != p)) {
                ((Player) e).sendMessage(ChatColor.GREEN + aconteceu);
            }
        }
    }

    
    List<Acontecimento> acontecimentos = new ArrayList();

    public void acontecemCoisasEmNossasVidas() {
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.RED + "Você soltou um pum fedido.");
                todosVeem(p, "Você sente um cheiro podre...");
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.RED + "Você soltou um pum fedido.");
                todosVeem(p, "Você sente um cheiro podre...");
            }
        });

        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.RED + "Um inseto entrou na sua orelha.");
                todosVeem(p, p.getName() + " começou a cutucar a propria orelha...");
            }
        });

        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.RED + "Voce sente vontade de comer doce.");
                todosVeem(p, p.getName() + " começa lamber os dados...");
            }
        });

        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.RED + "Você tropeêou em uma pedra.");
                todosVeem(p, p.getName() + " Trupicou em uma pedra");
                p.getWorld().dropItemNaturally(p.getLocation(), new ItemStack(Material.STONE));
                p.setVelocity(new Vector(0, 2, 0));
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.RED + "Uma mosca caiu em seu olho.");
                todosVeem(p, "Uma mosca caiu no olho de " + p.getName());
                p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                if (p.getSaturation() < 20.0F) {
                    p.sendMessage(ChatColor.RED + "Despertou um apetite enorme em voce !");
                    todosVeem(p, "Você ouve uma barriga roncando alto...");
                    p.setSaturation(0.0F);
                }
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Você encontrou um pouco de pê dourado no chêo.");
                todosVeem(p, p.getName() + "Pegou algo rapidamente do chao escondidamente");
                p.getInventory().addItem(new ItemStack[]{new ItemStack(Material.GLOWSTONE_DUST, 1)});
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Voce encontrou uma pau com algo escrito.");
                ItemStack madeira = new ItemStack(Material.STICK, 1);
                ItemMeta m = madeira.getItemMeta();
                m.setDisplayName(ChatColor.BLUE + "Love is True, Love is Blind");
                madeira.setItemMeta(m);
                p.getInventory().addItem(new ItemStack[]{madeira});
                todosVeem(p, p.getName() + " achou algo no chao...");
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Você arrotou muito alto.");
                todosVeem(p, p.getName() + " solta um arroto muito alto");
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Voce sente uma caimbra pegando na sua perna.");
                todosVeem(p, p.getName() + " ajoelha no chao e grita de dor");
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 3));
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Um bixo picou seu pê.");
                todosVeem(p, p.getName() + " da um saltinho e um gritinho");
                p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0));
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Voce espirra muito forte.");
                p.getWorld().spawnEntity(p.getLocation(), EntityType.SLIME);
                todosVeem(p, p.getName() + " da um espirro muito forte");
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Voce trupicou em um ferro.");
                todosVeem(p, p.getName() + " trupica e cai no chao todo feliz abracando algo");
                p.getInventory().addItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 1)});
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Voce sente uma leve dor de cabeca.");
                todosVeem(p, p.getName() + " coca a cabeca e nao parece estar muito bem");
                p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0));
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Voce comeca a cantar, im blue da ba de da ba da....");
                todosVeem(p, p.getName() + " comeca a cantar im blue da ba de da ba da...");
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Voce sente uma coceira na bunda...");
                todosVeem(p, p.getName() + " comeca a cocar a bunda");
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Um zumbi puxa seu pe !");
                todosVeem(p, p.getName() + " deu um gritinho afeminado");
                p.getWorld().spawnEntity(p.getLocation(), EntityType.ZOMBIE);
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Voce tentou espirrar e segurou o espirro !");
                todosVeem(p, p.getName() + " botou um ovo");
                p.getWorld().dropItemNaturally(p.getLocation(), new ItemStack(Material.EGG, 1));
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Um pombo cagou em sua cabeca !");
                todosVeem(p, p.getName() + " da um salto de nojo, tirando caca de pombo");
                p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0));
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Voce se sente deprimido !");
                todosVeem(p, p.getName() + " esta tristonho(a) !");
                Location l = p.getLocation();
                l.setPitch(-90.0F);
                p.teleport(l);
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Voce quebrou uma unha !");
                todosVeem(p, p.getName() + " chacoalha a mao insanamente gritando de dor !");
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Voce coêa o nariz por dentro !");
                todosVeem(p, p.getName() + " esta sendo muito deselegante colocando o dedo no nariz !");
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Uma abelha picou sua bunda !");
                todosVeem(p, p.getName() + " da um salto esfregando a bunda !");
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Voce pisou num coco velho !");
                todosVeem(p, p.getName() + " esta cheirando muito mal...");
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Bateu uma alegria insana !");
                todosVeem(p, p.getName() + " esta sorrindo como besta");
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Voce comeca a filosofar sobre a vida, a morte, o minecraft, e zumbis !");
                todosVeem(p, p.getName() + " esta olhando pro ceu e falando sozinho");
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Voce encontrou uma pedra brilhante !");
                todosVeem(p, p.getName() + " pegou uma pedra brilhante do chao");
                p.getInventory().addItem(new ItemStack[]{new ItemStack(Material.EMERALD, 1)});
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Voce encontrou uma pedra brilhante !");
                todosVeem(p, p.getName() + " pegou uma pedra brilhante do chao");
                p.getInventory().addItem(new ItemStack[]{new ItemStack(Material.EMERALD, 1)});
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Voce encontrou uma caixa de pedras brilhantes !");
                todosVeem(p, p.getName() + " pegou uma caixa de pedras brilhantes do chao");
                p.getInventory().addItem(new ItemStack[]{new ItemStack(Material.EMERALD, 10)});
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Seu pe esta cocando e voce abaixa para cocar e trupica !");
                todosVeem(p, p.getName() + " da uma cambalhota ridicula");
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 6));
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Seu coracao bate forte, pode ser amor !");
                todosVeem(p, p.getName() + " suspira e faz uma cara sorridente olhando pra cima...");
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 6));
            }
        });
        this.acontecimentos.add(new Acontecimento() {
            public void acontece(Player p) {
                p.sendMessage(ChatColor.GREEN + "Saudades, bate forte, voce sente muita falta...");
                todosVeem(p, p.getName() + " olha para o chao e fica chutando pedrinhas...");
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 6));
            }
        });
    }

    private static abstract interface Acontecimento {

        public abstract void acontece(Player paramPlayer);
    }
}
