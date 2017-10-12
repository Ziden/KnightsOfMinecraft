package nativelevel.Listeners;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import java.util.ArrayList;
import java.util.List;
import nativelevel.Classes.Mage.spelllist.Paralyze;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Classes.Paladin;
import nativelevel.Classes.Thief;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.Items.ItemRaroAleatorio;
import nativelevel.Custom.Items.SeguroDeItems;
import nativelevel.sisteminhas.Deuses;
import nativelevel.sisteminhas.Mobs;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Language.MSG;
import nativelevel.MetaShit;
import nativelevel.karma.Criminoso;
import nativelevel.karma.Karma;
import nativelevel.sisteminhas.IronGolem;
import nativelevel.sisteminhas.XP;
import nativelevel.spec.PlayerSpec;
import nativelevel.utils.BookUtil;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PacketPlayInClientCommand;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Ziden
 */
public class DeathEvents implements Listener {

    public void morreJogador(final PlayerDeathEvent event, int perdaDeLvl) {

        event.setKeepLevel(true);
        event.setDeathMessage(null);
        event.setDroppedExp(0);

        if(Criminoso.isCriminoso(event.getEntity()))
            Criminoso.setCriminoso(event.getEntity());
        
        if(Paralyze.isParalizado(event.getEntity())) {
            Paralyze.removeParalize(event.getEntity());
        }
        
        int level = ((Player) event.getEntity()).getLevel();
        int prox = level - perdaDeLvl;
        if (prox < 1) {
            prox = 1;
        }
        
        event.setNewLevel(event.getEntity().getLevel());
        int soulPoints = KoM.database.getAlmas(event.getEntity().getUniqueId().toString());
        if (soulPoints > 0) {
            if (perdaDeLvl > 0) {
                soulPoints--;
                KoM.database.setAlmas(event.getEntity().getUniqueId().toString(), soulPoints);
                event.getEntity().sendMessage(ChatColor.GREEN + "Voce nao perdeu nenhum level ao morrer, mas perdeu 1 ponto de alma !");
                event.getEntity().sendMessage(ChatColor.GREEN + "Recupere Alma ficando Online !");
                event.getEntity().sendMessage(ChatColor.RED + "Voce ainda tem " + soulPoints + " Almas");
            }
        } else {
            XP.setaLevel((Player) event.getEntity(), prox);
            event.setNewLevel(prox);

            if (perdaDeLvl > 0) {
                event.getEntity().sendMessage("Voce ficou sem pontos de alma e perdeu " + perdaDeLvl + " niveis");
            }
        }
        //((Player) event.getEntity()).setLevel(prox);

        MetaShit.setMetaObject("tempomorte", event.getEntity(), System.currentTimeMillis() / 1000);
        Respawna(event.getEntity());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void morreAlguem(final EntityDeathEvent ev) {
        if (ev.getEntity() instanceof Player) {
            ev.setDroppedExp(0);
        } else if (ev.getEntity().getType() == EntityType.CHICKEN || ev.getEntity().getType() == EntityType.PIG || ev.getEntity().getType() == EntityType.COW) {
            ev.setDroppedExp(0);
        }

        IronGolem.morre(ev);
        Mobs.morreMob(ev);
        if (ev.getEntity() instanceof EnderDragon) {
            ev.getEntity().getWorld().dropItemNaturally(ev.getEntity().getLocation(), new ItemStack(Material.IRON_INGOT, 60));
            ev.getEntity().getWorld().dropItemNaturally(ev.getEntity().getLocation(), new ItemStack(Material.DIAMOND, 2));
            ev.getEntity().getWorld().dropItemNaturally(ev.getEntity().getLocation(), new ItemStack(Material.DIAMOND, 2));
            ev.getEntity().getWorld().dropItemNaturally(ev.getEntity().getLocation(), new ItemStack(Material.DIAMOND, 2));
            ev.getEntity().getWorld().dropItemNaturally(ev.getEntity().getLocation(), new ItemStack(Material.EXP_BOTTLE, 10));
            ev.getEntity().getWorld().dropItemNaturally(ev.getEntity().getLocation(), new ItemStack(Material.EXP_BOTTLE, 10));
            ev.getEntity().getWorld().dropItemNaturally(ev.getEntity().getLocation(), new ItemStack(Material.EXP_BOTTLE, 10));
            ev.getEntity().getWorld().dropItemNaturally(ev.getEntity().getLocation(), new ItemStack(Material.GLOWSTONE, 10));
            ev.getEntity().getWorld().dropItemNaturally(ev.getEntity().getLocation(), KoM.geraEs(5));
            ev.getEntity().getWorld().dropItemNaturally(ev.getEntity().getLocation(), CustomItem.getItem(ItemRaroAleatorio.class).generateItem());
            ev.getEntity().getWorld().dropItemNaturally(ev.getEntity().getLocation(), CustomItem.getItem(ItemRaroAleatorio.class).generateItem());
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(ChatColor.DARK_PURPLE + L.m("[EPIC]" + ChatColor.GREEN + "O Dragao do Fim foi morto %", (ev.getEntity().getKiller() == null ? "" : " por " + ev.getEntity().getKiller().getName())));
            }
        }

        EntityDamageEvent.DamageCause causaDaMorte = null;
        if (ev.getEntity().getLastDamageCause() != null) {
            causaDaMorte = ev.getEntity().getLastDamageCause().getCause();
        }

        if (ev instanceof PlayerDeathEvent) {
            final Player p = (Player) ev.getEntity();

            KoM.debug(p.getName() + " morrendo");

            // arrumando bug de jogar enderpearl pra cima antes de morrer
            if (GeneralListener.pearls.containsKey(p.getUniqueId())) {
                GeneralListener.pearls.get(p.getUniqueId()).remove();
                GeneralListener.pearls.remove(p.getUniqueId());
            }

            Player pmorto = p;
            int fama = KoM.database.getFama(pmorto.getUniqueId());
            int famaPerdida = fama / 10;
            if (!pmorto.hasMetadata("msgfama")) {
                MetaShit.setMetaString("msgfama", pmorto, "");
                pmorto.sendMessage(ChatColor.GREEN + "Fama: " + fama + " " + ChatColor.RED + "-" + famaPerdida);
            } else {
                pmorto.sendMessage(ChatColor.GREEN + "Fama: " + ChatColor.YELLOW + "-" + famaPerdida);
            }
            KoM.database.setFama(pmorto.getUniqueId(), fama - famaPerdida);

            // bug de voltar qnd morre no cavalo
            if (p.getVehicle() != null) {
                p.getVehicle().eject();
            }

            boolean naoPerdeItem = false;
            boolean naoPerdeLevel = false;

            Thief.revela(p);

            if (Deuses.odio) {
                p.sendMessage(ChatColor.RED + "Voce morreu perante o ódio de Ubaj");
                Deuses.matou++;
            }

            String tipo = ClanLand.getTypeAt(ev.getEntity().getLocation());
            boolean seguro = false;

            if (ClanLand.permission.has(p, "kom.vip")) {
                if (p.getWorld().getName().equalsIgnoreCase("dungeon")) {
                    naoPerdeItem = true;
                    KoM.debug("vip em dungeon nao eprde item");
                }
                naoPerdeLevel = true;
            }
            
            
            if(ev.getEntity().getLastDamageCause() != null && ev.getEntity().getLastDamageCause().getCause()==DamageCause.SUFFOCATION) {
                naoPerdeLevel = true;
                naoPerdeItem = true;
            }
            
            if (tipo.equalsIgnoreCase("WARZ")) {
                naoPerdeLevel = true;
            }
            if (ev.getEntity().getWorld().getName().equalsIgnoreCase("woe") || ev.getEntity().getWorld().getName().equalsIgnoreCase("arena")) {
                //naoPerdeItem = true;
                //   if(ev.getEntity().getWorld().getName().equalsIgnoreCase("woe")) {
                this.morreJogador((PlayerDeathEvent) ev, 0);
                return;
                //   }
            }
            List<PlayerSpec> spec = PlayerSpec.getSpecs(p);
            if (p.getLevel() < 10 && KoM.database.getResets(p) == 0 && (spec == null || spec.size() == 0)) {
                seguro = true;
                naoPerdeLevel = true;
            }
            if (p.getWorld().getName().equalsIgnoreCase("dungeon")) {
                ApplicableRegionSet set = KoM.worldGuard.getRegionManager(p.getWorld()).getApplicableRegions(p.getLocation());
                if (set != null && set.size() > 0) {
                    if (set.getFlag(DefaultFlag.ITEM_DROP) == StateFlag.State.DENY) {
                        naoPerdeItem = true;
                    }
                }
            }
            if (SeguroDeItems.segurou(p)) {
                seguro = true;
                if (KoM.debugMode) {
                    KoM.log.info("segurando item");
                }
            }

            int perdaDeLvl = 0;
            try {
                Object tempoMorte = MetaShit.getMetaObject("tempomorte", p);
                if (tempoMorte != null) {
                    long tempo = (long) tempoMorte;
                    long tempoAgora = System.currentTimeMillis() / 1000;
                    if (tempo + 2 > tempoAgora) {
                        p.removeMetadata("tempomorte", KoM._instance);
                        this.morreJogador((PlayerDeathEvent) ev, 0);
                        return; // só morre 1x a cada 2 sec pra evitar bug

                    }
                }
                if ((ev.getEntity().getWorld().getName().equalsIgnoreCase("dungeon") && naoPerdeItem) || seguro || ev.getEntity().getWorld().getName().equalsIgnoreCase("dungeon") || ClanLand.isWarZone(ev.getEntity().getLocation())) {
                    ArrayList<ItemStack> listaItems = new ArrayList<ItemStack>();
                    for (ItemStack ss : p.getInventory().getContents()) {
                        if (ss != null && (Jobs.rnd.nextInt(8) != 1
                                || naoPerdeItem
                                || seguro
                                || BookUtil.ehLivroDeQuest(ss)
                                || SeguroDeItems.isItemSeguro(ss))) {
                            listaItems.add(ss);
                        }
                    }
                    GeneralListener.loots.put(p.getUniqueId(), listaItems);
                    ev.getDrops().removeAll(listaItems);
                    if (ev.getDrops().size() > 0) {
                        p.sendMessage(ChatColor.RED + L.m("Voce dropou % item(s) !", ev.getDrops().size()));
                    }
                    perdaDeLvl = 1;
                } else {
                    ArrayList<ItemStack> listaItems = new ArrayList<ItemStack>();
                    for (ItemStack ss : p.getInventory().getContents()) {
                        if (ss != null && (BookUtil.ehLivroDeQuest(ss) || SeguroDeItems.isItemSeguro(ss))) {
                            KoM.debug("Salvando item " + ss.getType().name());
                            listaItems.add(ss);
                        }
                    }
                    GeneralListener.loots.put(p.getUniqueId(), listaItems);
                    ev.getDrops().removeAll(listaItems);
                    perdaDeLvl = 1;
                    KoM.debug("Ainda ficaram " + ev.getDrops() + " items");
                }
                if (ev.getEntity().getLastDamageCause() == null || ev.getEntity().getLastDamageCause().getCause() == null) {
                    perdaDeLvl = 0;
                } else if (ev.getEntity().getKiller() != null) {
                    perdaDeLvl = 0;
                } else if (causaDaMorte == EntityDamageEvent.DamageCause.DROWNING || causaDaMorte == EntityDamageEvent.DamageCause.LAVA || causaDaMorte == EntityDamageEvent.DamageCause.STARVATION) {
                    perdaDeLvl = 1;
                }
                if (naoPerdeLevel) {
                    perdaDeLvl = 0;
                }
                // se um jogador matou otro jogador
                if (ev.getEntity() instanceof Player && (ev.getEntity().getKiller() != null || GeneralListener.ultimoDano.containsKey(ev.getEntity().getUniqueId()))) {
                    String local = ClanLand.getTypeAt(ev.getEntity().getLocation());

                    if (!local.equalsIgnoreCase("WARZ") && !ev.getEntity().getLocation().getWorld().getName().equalsIgnoreCase("WoE")) {

                        Player pMorto = (Player) ev.getEntity();
                        Player pMatou = ev.getEntity().getKiller();
                        if (pMatou == null) {
                            pMatou = Bukkit.getPlayer(GeneralListener.ultimoDano.get(ev.getEntity().getUniqueId()));
                        }
                        if (pMorto != null && pMatou != null) {
                            Karma.manoloMata(pMatou, pMorto);
                        }

                        ClanPlayer morto = ClanLand.manager.getClanPlayer((Player) ev.getEntity());
                        ClanPlayer matou = null;
                        if (ev.getEntity().getKiller() != null) {
                            matou = ClanLand.manager.getClanPlayer(ev.getEntity().getKiller());
                        } else {
                            matou = ClanLand.manager.getClanPlayer(Bukkit.getPlayer(GeneralListener.ultimoDano.get(ev.getEntity().getUniqueId())));
                        }
                        // se o kra tinha um clan
                        if (morto != null && morto.getClan() != null) {
                            // quem matou tem clan ?
                            if (matou != null && matou.getClan() != null) {
                                // se sao clans inimigos
                                if (matou.isRival((Player) ev.getEntity())) {
                                    if (matou.isTrusted() && morto.isTrusted()) {
                                        // o ganhador ganha ponto de pilhagem
                                        int qtdTerrenos = ClanLand.getQtdTerrenos(morto.getClan().getTag());
                                        int pontosGanhos = 1;
                                        if (matou.isLeader()) {
                                            pontosGanhos += 3;
                                        }
                                        if (morto.isLeader()) {
                                            pontosGanhos += 3;
                                        }

                                        pontosGanhos += (qtdTerrenos / 3);

                                        int ptosPilhagemGanhador = ClanLand.getPtosPilagem(matou.getTag(), morto.getTag());
                                        int ptosPilhagemDefensor = ClanLand.getPtosPilagem(morto.getTag(), matou.getTag());
                                        if (ptosPilhagemDefensor - pontosGanhos < 0) {
                                            ptosPilhagemDefensor = 0;
                                        }

                                        ClanLand.setPtosPilhagem(morto.getTag(), matou.getTag(), (ptosPilhagemDefensor - pontosGanhos) < 0 ? 0 : (ptosPilhagemDefensor - pontosGanhos));
                                        ClanLand.setPtosPilhagem(matou.getTag(), morto.getTag(), (ptosPilhagemGanhador + pontosGanhos < 0 ? 0 : (ptosPilhagemGanhador + pontosGanhos)));
                                        ((Player) ev.getEntity()).sendMessage(ChatColor.RED + L.m("Sua guilda perdeu % PPs para a guilda ", pontosGanhos) + matou.getName());
                                        ev.getEntity().getKiller().sendMessage(ChatColor.GREEN + L.m("Sua guilda ganhou % PPs da guilda ", pontosGanhos) + morto.getName());

                                    }
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                this.morreJogador((PlayerDeathEvent) ev, 0);
                KoM.log.info("ERRO NO PLAYERDEATH EVENT " + e.getMessage());
                e.printStackTrace();
            }
            this.morreJogador((PlayerDeathEvent) ev, perdaDeLvl);
        }
        if (ev.getEntity() instanceof Creature) {
            if (!(ev.getEntity() instanceof Player)) {
                if (ev.getEntity().getKiller() != null && ev.getEntity().getKiller().getType() == EntityType.PLAYER) {
                    Player quemMatou = ev.getEntity().getKiller();
                    Paladin.pegaDropsExtrasDeMobs(ev.getEntity(), quemMatou);
                    if (Jobs.getJobLevel(L.get("Classes.Alchemist"), quemMatou) == 1) {
                        int sorte = Jobs.rnd.nextInt(50);
                        if (sorte >= 5 && sorte < 8) {
                            ev.getEntity().getWorld().dropItemNaturally(ev.getEntity().getLocation(), new ItemStack(Material.SPIDER_EYE, 1));
                        } else if (sorte == 2 || sorte == 3) {
                            ev.getEntity().getWorld().dropItemNaturally(ev.getEntity().getLocation(), new ItemStack(Material.NETHER_WARTS, 3));
                        }
                    }
                }
            }
        }
        if (ev.getEntity().getKiller() != null && ev.getEntity().getKiller() instanceof Player) {
            int lvl = Jobs.getJobLevel("Fazendeiro", (Player) ev.getEntity().getKiller());
            if (lvl == 1) {
                if (ev.getEntity().getType() == EntityType.SHEEP || ev.getEntity().getType() == EntityType.COW || ev.getEntity().getType() == EntityType.PIG) {
                    ev.getDrops().add(new ItemStack(Material.RAW_BEEF, 3));
                    ev.getDrops().add(new ItemStack(Material.LEATHER, 3));
                    ((Player) ev.getEntity().getKiller()).sendMessage(ChatColor.GREEN + L.m("Voce coletou carne extra do animal !"));
                } else if (ev.getEntity().getType() == EntityType.CHICKEN) {
                    ev.getDrops().add(new ItemStack(Material.RAW_CHICKEN, 3));
                    ((Player) ev.getEntity().getKiller()).sendMessage(ChatColor.GREEN + L.m("Voce coletou carne extra do animal !"));
                }
            }
        }

    }

    public static void Respawna(final Player pl) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, new Runnable() {

            @Override
            public void run() {
                PacketPlayInClientCommand in = new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN); // Gets the packet class
                EntityPlayer cPlayer = ((CraftPlayer) pl).getHandle(); // Gets the EntityPlayer class
                cPlayer.playerConnection.a(in);
            }
        }, 5L);
    }

}
