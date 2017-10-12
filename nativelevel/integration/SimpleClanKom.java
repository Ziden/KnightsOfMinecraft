/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.integration;

import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import nativelevel.CFG;
import nativelevel.Classes.Thief;
import nativelevel.Comandos.Terreno;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Custom.Buildings.Construcao;
import nativelevel.Lang.L;
import nativelevel.Listeners.GeneralListener;
import nativelevel.MetaShit;
import nativelevel.karma.Criminoso;
import nativelevel.utils.BookUtil;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SimpleClanKom {

    public static boolean canDamage(EntityDamageEvent ev) {

        //if(ev.getEntity() instanceof Monster) return;
        if (ev.getEntity().getWorld().getName().equalsIgnoreCase("dungeon")) {
            return true;
        }

        String type = ClanLand.getTypeAt(ev.getEntity().getLocation());
        if (ev.getEntity() instanceof Monster && !(ev.getEntity() instanceof Tameable) && type.equalsIgnoreCase("SAFE")) {
            ev.getEntity().getWorld().playEffect(ev.getEntity().getLocation(), Effect.SMOKE, 0);
            ev.getEntity().remove();
            ev.setCancelled(true);
            return false;
        }
        if (ev.getEntity() instanceof Horse && ev.getEntity().getPassenger() != null && type.equalsIgnoreCase("SAFE")) {
            ev.setCancelled(true);
            return false;
        }
        return true;
    }

    public static boolean canPvp(Player atacante, Player defensor) {

        KoM.debug("CAN PVP ?");

        if (atacante.getWorld().getName().equalsIgnoreCase("dungeon")) {
            // if (!Criminoso.isCriminoso(defensor)) {
            KoM.debug("NO CAN PVP");
            return false;
            // }
        }

        ClanPlayer atk = ClanLand.manager.getClanPlayer(atacante);
        ClanPlayer def = ClanLand.manager.getClanPlayer(defensor);

        if (WorldGuardKom.ehSafeZone(defensor.getLocation())) {
            // se quem ta tomando não é criminoso nao pode pvp
            if (!Criminoso.isCriminoso(defensor)) {
                KoM.debug("NO CAN PVP CRIM");
                return false;
            } else {
                // criminoso tomando
                if (Criminoso.isCriminoso(atacante)) {
                    KoM.debug("NO CAN PVP");
                    return false;
                }
            }
        }
        return true;
    }

    //public static HashSet<Location> verificados = new HashSet<Location>();
    public List<Material> permitidosMineradorRetirar = Arrays.asList(
            new Material[]{
                Material.COBBLESTONE,
                Material.STONE,
                Material.BRICK,
                Material.BRICK_STAIRS,
                Material.COBBLESTONE_STAIRS,
                Material.WOOD,
                Material.LOG,
                Material.LOG_2,}
    );

    // metodo principal de controle de permissões
    public static boolean podeMexer(Player p, Location l, Block b) {
        if (l.getY() < 40) {
            return true;
        }
        if (p.isOp() || ClanLand.permission.has(p, "kom.interact")) {
            return true;
        }
        String type = ClanLand.getTypeAt(l);

        if (type.equalsIgnoreCase("CLAN")) {
            Clan aqui = ClanLand.getClanAt(l);
            Clan meu = ClanLand.manager.getClanByPlayerName(p.getName());
            if (b != null && b.getType() == Material.CHEST) {
                Location origem = b.getLocation().getChunk().getBlock(0, 0, 0).getLocation();
                //Terrenos.getClanAt(l);    
            }

            //  if(Terrenos.  ev.getPlayer().)
            if ((aqui != null) && (meu == null || !meu.getTag().equalsIgnoreCase(aqui.getTag()))) {

                //// TENTANDO MEXER EM TERRA DE INIMIGO
                if (meu != null && meu.isRival(aqui.getTag())) {
                    //    if(Jobs.getJobLevel("Minerador",p)==1) {
                    //        
                    //    }
                }

                if (p.getItemInHand().getType() == Material.WATER_BUCKET) {
                    p.sendMessage(ChatColor.RED + L.m("Ninguem pediu pra voce lavar a guilda dos outros !"));
                    return false;
                }
                if (b.getType() == Material.STONE_BUTTON
                        || b.getType() == Material.WOOD_BUTTON
                        || b.getType() == Material.WOOD_PLATE
                        || b.getType() == Material.STONE_PLATE
                        || b.getType() == Material.STONE_PLATE
                        || b.getType() == Material.WOODEN_DOOR
                        || b.getType() == Material.ENDER_CHEST
                        || b.getType() == Material.TRAP_DOOR
                        || b.getType() == Material.FURNACE
                        || b.getType() == Material.BURNING_FURNACE
                        || b.getType() == Material.BREWING_STAND
                        || b.getType() == Material.GRASS
                        || b.getType() == Material.LEVER
                        || b.getType() == Material.WOODEN_DOOR
                        || b.getType() == Material.ACACIA_FENCE_GATE
                        || b.getType() == Material.DARK_OAK_DOOR
                        || b.getType().name().contains("DOOR")
                        || b.getType().name().contains("FENCE_GATE")
                        || b.getType().name().contains("TRAP_DOOR")
                        || b.getType() == Material.ACACIA_DOOR) {
                    p.sendMessage(ChatColor.RED + L.m("Voce nao pode fazer isto em terras dos outros"));
                    if (b.getType() == Material.WOODEN_DOOR || b.getType() == Material.TRAP_DOOR) {
                        p.damage(5D);
                    }
                    return false;
                } else if (b.getType() == Material.CHEST) {
                    if (p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR) {
                        String customItem = CustomItem.getCustomItem(p.getItemInHand());
                        if (customItem != null && customItem.equalsIgnoreCase(L.m("Lockpick"))) {
                            Thief.bisbilhota(p, b);
                        }
                        if (customItem != null && customItem.equalsIgnoreCase(L.m("Pe de Cabra"))) {
                            Thief.stealFullChest(p, b);
                        }
                    } else {
                        if (!p.isSneaking()) {
                            Thief.zoiudo(p, (Chest) b.getState());
                            if (p.getOpenInventory() != null) {
                                p.sendMessage(ChatColor.GREEN + "Voce está espiando o baú. Voce pode usar shift + click sem nada na mão para roubar um item aleatório. Custa " + CFG.custoPegarItemRandom + " PPS");
                            }
                        } else {
                            Thief.pegaItemAleatorio(p, b);
                        }
                    }
                    return false;
                }
                /////// SE ELE EH DO CLAN
            } else if (meu != null && aqui != null && meu.getTag().equalsIgnoreCase(aqui.getTag())) {
                ClanPlayer cp = ClanLand.getPlayer(p.getName());
                String owner = ClanLand.getOwnerAt(l);
                if (owner != null && owner.equalsIgnoreCase("none")) {
                    return cp.isTrusted(); // terra publica
                }
                List<String> members = ClanLand.getMembersAt(l);
                if (members != null && members.contains(p.getUniqueId().toString()) || owner.equalsIgnoreCase(p.getUniqueId().toString())) {
                    return true; // tem permissão
                }
                if (!cp.isLeader()) {
                    // se nao eh membro confiável só mexe em terra q eh dele
                    if (!cp.isTrusted()) {

                        if (owner != null && owner.equalsIgnoreCase(p.getUniqueId().toString())) {
                            return true;
                        }
                        if (members != null && members.contains(p.getUniqueId().toString())) {
                            return true;
                        }
                        // nao mexe em chest se nao for trusted
                        if (b.getType() == Material.CHEST) {
                            return false;
                        }
                        if (b.getType() == Material.WOODEN_DOOR || b.getType() == Material.FURNACE || b.getType() == Material.TRAP_DOOR || b.getType() == Material.WORKBENCH) {
                            return false;
                        }
                        return false;
                        // ele eh confiável mexe em tudo menos onde tem dono
                    } else {
                        if (owner != null && owner.equalsIgnoreCase(p.getUniqueId().toString())) {
                            return true;
                        }
                        if (members != null && members.contains(p.getUniqueId().toString())) {
                            return true;
                        }
                        // se ele nao for o dono
                        if (owner != null && !owner.equalsIgnoreCase("none")) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            }
        }
        return true;
    }

    public static void interact(PlayerInteractEvent ev) {
        if (ev.getPlayer().getWorld().getName().equalsIgnoreCase("dungeon")) {
            return;
        }
        if (ev.getPlayer().getItemInHand() != null && ev.getPlayer().getItemInHand().getType() != Material.AIR) {
            if (ev.getPlayer().getItemInHand().getType() == Material.FLINT_AND_STEEL
                    && (ev.getClickedBlock() != null && ev.getClickedBlock().getType() != Material.NETHERRACK && ev.getBlockFace() == BlockFace.UP)) {
                ev.setCancelled(true);
                return;
            }
        }
        if (!ev.getPlayer().isOp() && !ClanLand.permission.has(ev.getPlayer(), "kom.build") && ev.getClickedBlock() != null && ev.getClickedBlock().getType() != Material.AIR) {
            if (!podeMexer(ev.getPlayer(), ev.getClickedBlock().getLocation(), ev.getClickedBlock())) {
                ev.setCancelled(true);
                if (KoM.debugMode) {
                    KoM.log.info("parei o interact com simpleclans");
                }
            } else {
                if (ev.getClickedBlock().getType() == Material.BEACON) {
                    ev.getClickedBlock().breakNaturally();
                }
            }
        }
        if (ev.getClickedBlock() != null) {
            String type = ClanLand.getTypeAt(ev.getClickedBlock().getLocation());
            if (type.equalsIgnoreCase("WARZ")) {
                if (ev.getPlayer().getItemInHand() != null) {
                    if (ev.getPlayer().getItemInHand().getType() == Material.BUCKET || ev.getPlayer().getItemInHand().getType() == Material.WATER_BUCKET) {
                        ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce nao pode fazer isto !"));
                        ev.setCancelled(true);
                    }
                }
            }
        }

    }
    public static List<Material> liberados = Arrays.asList(new Material[]{Material.SUGAR_CANE_BLOCK, Material.PUMPKIN, Material.DEAD_BUSH, Material.LONG_GRASS, Material.CACTUS, Material.RED_MUSHROOM, Material.BROWN_MUSHROOM, Material.SUGAR_CANE, Material.RED_ROSE, Material.YELLOW_FLOWER, Material.LOG, Material.LEAVES, Material.SAPLING, Material.IRON_ORE, Material.LEAVES, Material.LOG_2, Material.LEAVES_2, Material.PUMPKIN, Material.COCOA, Material.DOUBLE_PLANT});

    public static boolean canBuild(Player p, Location l, Block b, boolean placing) {
        if (l.getWorld().getEnvironment() == Environment.THE_END) {
            return true;
        }
        if (p.getWorld().getName().equalsIgnoreCase("dungeon")) {
            if (placing && b.getType() == Material.TORCH && l.getBlock().getType() != Material.STATIONARY_WATER) {
                return true;
            }
            ApplicableRegionSet set = KoM.worldGuard.getRegionManager(Bukkit.getWorld("dungeon")).getApplicableRegions(b.getLocation());
            if (set.size() >= 1) {
                Iterator<ProtectedRegion> i = set.iterator();
                while (i.hasNext()) {
                    ProtectedRegion regiao = i.next();
                    //if (regiao.getId().contains("tutorial")) {
                    // return true;
                    if (!p.isOp() && !ClanLand.permission.has(p, "kom.build")) {
                        p.damage(5D);
                        p.sendMessage(ChatColor.RED + L.m("Voce apenas pode mecher em dungeons em locais específicos !!"));
                        return false;
                    }
                }
            }
            if ((!p.isOp() && !ClanLand.permission.has(p, "kom.build")) && b.getType() != Material.TORCH) {
                p.damage(5D);
                p.sendMessage(ChatColor.RED + L.m("Voce apenas pode mecher em dungeons em locais específicos !!"));
                return false;
            }
            return p.isOp() || ClanLand.permission.has(p, "kom.build");
        } else if (p.getWorld().getName().equalsIgnoreCase("woe")) {
            ApplicableRegionSet set = KoM.worldGuard.getRegionManager(l.getWorld()).getApplicableRegions(l);
            if (set.size() > 0) {
                return true;
            }
            if (p.isOp() && ClanLand.permission.has(p, "kom.build")) {
                return true;
            }
            p.sendMessage(ChatColor.RED + L.m("Voce apenas pode construir/destruir as areas marcadas !"));
            return false;
        }
        String type = ClanLand.getTypeAt(l);
        if (KoM.debugMode) {
            KoM.log.info("tentando construir em " + type);
        }
        // building in wilderness
        if (type.equalsIgnoreCase("WILD")) {

            if (Construcao.chunkConstruido(l.getChunk())) {
                p.sendMessage(ChatColor.RED + "Você não pode construir aqui");
                return false;
            }

            Block gambs = l.getChunk().getBlock(5, 0, 5);
            if (gambs.getType() == GeneralListener.gambiarra) {
                l.getChunk().getBlock(5, 0, 5).setType(Material.GLOWSTONE);
                MetaShit.setMetaObject("temporegen", l.getChunk().getBlock(5, 0, 5), (System.currentTimeMillis() / 1000) + (60 * 30));
                if (!p.hasMetadata("msgwild")) {
                    p.sendMessage(ChatColor.RED + "Voce esta em terras sem dono !");
                    p.sendMessage(ChatColor.RED + "Este terreno será regenerado em breve e tudo que for construido vai ser perdido !");
                    p.sendMessage(ChatColor.RED + "Para construir voce precisa de uma " + ChatColor.GREEN + "guilda " + ChatColor.RED + " !");
                    p.sendMessage(ChatColor.RED + "Use o comando " + ChatColor.GREEN + "/f criar <tag> <nome> " + ChatColor.RED + " !");
                    MetaShit.setMetaString("msgwild", p, "");
                }
            }
            if (l.getBlockY() < 50) {
                return true;
            }
            ClanPlayer cp = ClanLand.manager.getClanPlayer(p);
            if (Terreno.temGuildaPerto(p, cp, b.getLocation())) {
                p.sendMessage(L.m("Voce está muito próximo de outra guilda para mexer nesta terra !"));
                return false;
            }
            return true;
            /*
             if (l.getBlockY() > 50) { 
             if (b != null && !placing && liberados.contains(b.getType()) || b.getType() == Material.SAPLING) {
             if (placing && b != null && b.getType() == Material.SAPLING) {
             if (p.hasPotionEffect(PotionEffectType.WATER_BREATHING)) {
             return false;
             }
             p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 20 * 10, 0));
             ClanPlayer cp = ClanLand.manager.getClanPlayer(p);
             if (cp == null) {
             p.sendMessage(ChatColor.RED + L.m("Voce precisa de uma guilda para poder colocar arvores !"));
             return false;
             } else {
             if (Terreno.temGuildaPerto(p, cp, b.getLocation())) {
             p.sendMessage(L.m("Voce nao pode plantar arvores colada em outras guildas que nao sejam a sua !"));
             return false;
             }
             }
             }
             return true;
             }
             if (!p.isOp() && !ClanLand.permission.has(p, "kom.build")) {
             p.sendMessage(ChatColor.RED + L.m("Voce nao sabe mecher em terras sem dono !"));
             return false;
             }
             }
             if (b.getType() == Material.LONG_GRASS && Jobs.rnd.nextInt(2000) == 1) {
             b.getWorld().dropItemNaturally(l, new ItemStack(Material.EMERALD, 1));
             p.playSound(l, Sound.ITEM_PICKUP, 10, 10);
             }
             */
        } else if (!p.isOp() && !ClanLand.permission.has(p, "kom.build") && type.equalsIgnoreCase("SAFE")) {
            // regions have privileges!!!
            ApplicableRegionSet set = KoM.worldGuard.getRegionManager(l.getWorld()).getApplicableRegions(l);
            if (set.size() > 0 && set.canBuild(KoM.worldGuard.wrapPlayer(p))) {
                return true;
            }
            p.sendMessage(ChatColor.RED + L.m("Voce nao pode fazer isto em vilas !"));
            return false;
        } else if (type.equalsIgnoreCase("WARZ")) {
            p.sendMessage(ChatColor.RED + L.m("Voce nao pode mecher em uma zona de guerra !"));
            return p.isOp() || ClanLand.permission.has(p, "kom.build");
        } else if (type.equalsIgnoreCase("CLAN") && !p.isOp()) {
            Clan aqui = ClanLand.getClanAt(l);
            if (KoM.debugMode) {
                KoM.log.info("tentando construir nas terras de " + aqui.getTag());
            }
            if (aqui.isMember(p)) {
                if (KoM.debugMode) {
                    KoM.log.info("sou membro de " + aqui.getTag());
                }
                if (aqui.isLeader(p)) {
                    return true; // lider constroi no clan inteiro
                }
                String owner = ClanLand.getOwnerAt(l);
                if (owner == null || owner.equalsIgnoreCase("none")) {
                    if (KoM.debugMode && p.isOp()) {
                        KoM.log.info("to em terra publica");
                    }
                    ClanPlayer cp = ClanLand.getPlayer(p.getName());
                    if (cp.isTrusted()) {
                        return true; // terra publica
                    } else {
                        return false;
                    }
                }
                List<String> members = ClanLand.getMembersAt(l);
                if (members != null && members.contains(p.getUniqueId().toString()) || owner.equalsIgnoreCase(p.getUniqueId().toString())) {
                    return true; // tem permissão
                }
            }
            p.sendMessage(ChatColor.RED + L.m("Estas terras nao sao suas para voce poder construir aqui !"));
            return false;
        }
        return true;
    }

    public static void setupFactionForBeginner(Player p) {
        for (Player pOnline : Bukkit.getOnlinePlayers()) {
            Clan c = ClanLand.manager.getClanByPlayerName(p.getName());
            if (c.getHomeLocation() == null) {
                continue;
            }
            if (c.getOnlineMembers().size() > 2 && ClanLand.getPoder(c.getTag()) < ClanLand.getQtdTerrenos(c.getTag())) {
                p.sendMessage(ChatColor.GREEN + "Voce entrou para a guilda " + c.getName());
                p.sendMessage(ChatColor.GREEN + "Lembre-se, este e um jogo cooperativo !");
                p.sendMessage(ChatColor.GREEN + "Tente " + ChatColor.YELLOW + "fazer bons amigos" + ChatColor.GREEN + " !!");
                //c.addBb("Jabu", p.getName() + " eh um iniciante, e entrou em sua guilda !");
                //c.addBb("Jabu", "Ele se encontra na home de sua faccao ! Tente ajuda-lo !");
                //c.addBb("Jabu", "Assim" + ChatColor.YELLOW + "faccao " + ChatColor.GREEN + "pode ficar " + ChatColor.YELLOW + "maior e mais forte" + ChatColor.GREEN + " !");
                //BungeeCordKom.tp(p, c.getHomeLocation());
                // p.teleport(c.getHomeLocation());
                return;
            }
        }
        p.sendMessage(ChatColor.RED + "Infelizmente nenhuma guilda disponivel " + ChatColor.YELLOW + ":(");
        p.sendMessage(ChatColor.RED + "Tente ir a " + ChatColor.YELLOW + "Rhodes" + ChatColor.RED + " e ache uma ou " + ChatColor.YELLOW + "faca um dinheiro e crie uma" + ChatColor.RED + " !");
    }
}
