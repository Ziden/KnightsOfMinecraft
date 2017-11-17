package nativelevel.Listeners;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import nativelevel.utils.TitleAPI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import me.asofold.bpl.simplyvanish.SimplyVanish;
import me.asofold.bpl.simplyvanish.config.VanishConfig;
import me.blackvein.quests.Quest;
import nativelevel.Attributes.Health;
import nativelevel.CFG;
import nativelevel.Classes.Farmer;
import nativelevel.Crafting.CraftEvents;
import static nativelevel.Crafting.CraftEvents.addInfoItem;
import nativelevel.Custom.Buildings.Portal;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.Items.Armadilha;
import nativelevel.Custom.Items.CapaInvisvel;
import nativelevel.Custom.Items.Detonador;
import nativelevel.Custom.Items.LogoutTrap;
import nativelevel.Custom.Items.PedraDoPoder;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import static nativelevel.Listeners.GeneralListener.loots;
import static nativelevel.Listeners.GeneralListener.taPelado;
import nativelevel.Classes.Mage.spelllist.Paralyze;
import nativelevel.Custom.Items.SeguroDeItems;
import nativelevel.Equipment.EquipManager;
import nativelevel.MetaShit;
import nativelevel.bencoes.TipoBless;
import nativelevel.integration.BungeeCordKom;
import nativelevel.karma.KarmaFameTables;
import nativelevel.rankings.RankCache;
import nativelevel.rankings.RankDB;
import nativelevel.rankings.RankedPlayer;
import nativelevel.scores.SBCore;
import nativelevel.sisteminhas.BookPortal;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.sisteminhas.Horses;
import nativelevel.sisteminhas.XP;
import nativelevel.sisteminhas.Mobs;
import nativelevel.skills.Skill;
import nativelevel.skills.SkillMaster;
import nativelevel.titulos.Titulos;
import nativelevel.utils.BungLocation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

/**
 *
 * @author Ziden
 */
public class PlayerEvents implements Listener {

    @EventHandler
    public void swapa(PlayerSwapHandItemsEvent ev) {
        ev.setCancelled(true);
        ev.getPlayer().sendMessage(ChatColor.RED + "Você não aprendeu sua habilidade ultimate, ainda.");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void crafta(CraftItemEvent ev) {
        CraftEvents.craft(ev);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void negoVaza(PlayerQuitEvent ev) {

        if (ev.getPlayer().getVehicle() != null) {
            ev.getPlayer().getVehicle().eject();
        }
        if (ev.getPlayer().getItemOnCursor() != null && ev.getPlayer().getItemOnCursor().getType() != Material.AIR) {
            ev.getPlayer().setItemOnCursor(new ItemStack(Material.AIR));
        }

        if (ev.getPlayer().hasPotionEffect(PotionEffectType.BLINDNESS)) {
            ev.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
        }

        if (!ev.getPlayer().getWorld().getName().equalsIgnoreCase("arena") && !ev.getPlayer().getWorld().getName().equalsIgnoreCase("woe") && !ev.getPlayer().getWorld().getName().equalsIgnoreCase("dungeon")) {
            if (LogoutTrap.trapeados.contains(ev.getPlayer().getName())) {
                LogoutTrap.trapeados.remove(ev.getPlayer().getName());
                ev.getPlayer().setHealth(0);
            }
            /*
             if(SeguroDeItems.segurou(ev.getPlayer())) {
             ev.getPlayer().teleport(ev.getPlayer().getWorld().getSpawnLocation());
             return;
             }
             if (LogoutTrap.trapeados.contains(ev.getPlayer().getName())) {
             for (int slot = 0; slot < 40; slot++) {
             if (ev.getPlayer().getInventory().getItem(slot) != null && ev.getPlayer().getInventory().getItem(slot).getType() != Material.AIR) {
             ev.getPlayer().getLocation().getWorld().dropItemNaturally(ev.getPlayer().getLocation(), new ItemStack(ev.getPlayer().getInventory().getItem(slot)));
             ev.getPlayer().getInventory().setItem(slot, null);
             }
             }
             ev.getPlayer().teleport(ev.getPlayer().getWorld().getSpawnLocation());
             }
             */
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void lvlUp(final PlayerLevelChangeEvent ev) {

        if (ev.getPlayer().isDead()) {
            XP.setaLevel(ev.getPlayer(), ev.getOldLevel());
            return;
        }

        if (ev.getNewLevel() > CFG.maxLevel) {
            XP.setaLevel(ev.getPlayer(), CFG.maxLevel);
            return;
        }

        if (ev.getNewLevel() > ev.getOldLevel()) {
            ev.getPlayer().setMaxHealth(Health.getMaxHealth(ev.getPlayer(), ev.getNewLevel()));
            ev.getPlayer().setHealth(ev.getPlayer().getMaxHealth());
            ev.getPlayer().setFoodLevel(20);
        }

        KoM.database.updateNivel(ev.getPlayer(), ev.getNewLevel());

        SBCore.AtualizaObjetivos(ev.getPlayer());

        if (ev.getNewLevel() > ev.getOldLevel() && ev.getNewLevel() == CFG.maxLevel) {
            ev.getPlayer().sendMessage(ChatColor.YELLOW + L.m("Level Up! Voce esta no nivel maximo !"));
            ev.getPlayer().playSound(ev.getPlayer().getLocation(), Sound.ENTITY_LIGHTNING_THUNDER, 10, 0);
            ev.getPlayer().sendMessage(ChatColor.YELLOW + L.m("Procure a esponja da evolução para transcender seu nivel se quiser !"));
            ev.getPlayer().sendMessage(ChatColor.YELLOW + L.m("Voce voltará do nivel 1 mais forte !"));
            ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Antes de transcender voce poderá pegar sua especialização (Apenas lvl 100)!"));
            return;
        }

        if (ev.getNewLevel() > ev.getOldLevel()) {
            SBCore.setLevelPoints(ev.getPlayer(), ev.getPlayer().getLevel());
            ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Level Up! Voce esta no nivel " + ChatColor.YELLOW + ChatColor.BOLD + "%", ev.getNewLevel()));
            String quests = "";
            for(Quest q  : KoM.quests.quests) {
                if (q.customRequirements != null && q.customRequirements.containsKey("Nivel KoM")) {
                    Map<String, Object> mapa = q.customRequirements.get("Nivel KoM");
                    if (mapa != null) {
                        int nivel = Integer.valueOf((String) mapa.get("Level"));
                        if(nivel==ev.getNewLevel()) {
                            quests += "- "+q.name+"   ";
                        }
                    }

                }
            }
            if(quests.length() > 2) {
                ev.getPlayer().sendMessage(ChatColor.GREEN+"!!! Novas Missões Disponíveis !!!");
                ev.getPlayer().sendMessage(ChatColor.YELLOW+quests);
            }
            ev.getPlayer().sendMessage(ChatColor.GREEN + "+Dano: " + ChatColor.YELLOW + " +0.5% " + ChatColor.GREEN + "  +Vida: " + ChatColor.YELLOW + "0.2");
            List<String> primarias = Jobs.getPrimarias(ev.getPlayer());
            boolean apr = false;
            for (String primaria : primarias) {
                Skill aprendeu = SkillMaster.aprendeuSkill(ev.getPlayer(), primaria, ev.getNewLevel());
                if (aprendeu != null) {
                    apr = true;
                    ev.getPlayer().sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Nova Skill: " + ChatColor.YELLOW + aprendeu.getNome());
                    for (String desc : aprendeu.getLore()) {
                        ev.getPlayer().sendMessage(ChatColor.GREEN + " -> " + ChatColor.YELLOW + desc);
                    }
                }
            }
            if (apr) {
                ev.getPlayer().sendMessage(ChatColor.GREEN + "Digite /kom skills para ver suas skills");
            }

            ev.getPlayer().playSound(ev.getPlayer().getLocation(), Sound.ENTITY_LIGHTNING_THUNDER, 10, 0);
            if (ev.getNewLevel() == 10) {
                ev.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + L.m("[Atenção] Agora voce é um aventureiro de verdade, sua fome será normal !"));
            }
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void pressNumberHotkey(final PlayerItemHeldEvent ev) {
        if (ev.getPlayer().hasMetadata("disarm")) {
            final int slot = ev.getPreviousSlot();

            Runnable r = new Runnable() {

                public void run() {
                    ev.getPlayer().getInventory().setHeldItemSlot(slot);
                }
            };
            Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r, 0);
            ev.setCancelled(true);
            EquipManager.checkEquips(ev.getPlayer());
        }

        ItemStack selecionou = ev.getPlayer().getInventory().getItem(ev.getNewSlot());
        ItemStack tirou = ev.getPlayer().getInventory().getItem(ev.getPreviousSlot());
        if (selecionou != null) {
            String ci = CustomItem.getCustomItem(selecionou);
            if (ci != null && ci.equalsIgnoreCase("Capa da Invisibilidade")) {
                CapaInvisvel.ficaInvi(ev.getPlayer());
            }
        }
        if (tirou != null) {
            String ci = CustomItem.getCustomItem(tirou);
            if (ci != null && ci.equalsIgnoreCase("Capa da Invisibilidade")) {
                CapaInvisvel.aparece(ev.getPlayer());
            }
        }
        if (selecionou != null) {
            if (ev.getPlayer().hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
                String ci = CustomItem.getCustomItem(selecionou);
                if (ci != null && ci.equalsIgnoreCase("Cajado Elemental")) {
                    ev.getPlayer().removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                    ev.getPlayer().sendMessage(ChatColor.RED + "Ao empunhar o Cajado elemental, a força é consumida pelo item magico");
                }
            }
        }
    }

    @EventHandler
    public void playerFishEvent(PlayerFishEvent ev) {

        ev.setExpToDrop(0);

        if (ev.getCaught() != null && ev.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            Location l = ev.getCaught().getLocation();
            if (ev.getPlayer().hasMetadata("pescou")) {
                Location pescou = (Location) MetaShit.getMetaObject("pescou", ev.getPlayer());
                if (pescou.getBlockX() == l.getBlockX() && pescou.getBlockZ() == l.getBlockZ()) {
                    ev.setCancelled(true);
                    return;
                }
            }

            MetaShit.setMetaObject("pescou", ev.getPlayer(), l);

            TipoBless ativo = TipoBless.save.getTipo(ev.getPlayer());
            if (ativo == null || ativo != TipoBless.Pescaria) {

                ApplicableRegionSet set = KoM.worldGuard.getRegionManager(ev.getPlayer().getWorld()).getApplicableRegions(ev.getPlayer().getLocation());
                Iterator<ProtectedRegion> i = set.iterator();
                for (int x = 0; x < 2; x++) {
                    if (i.hasNext()) {
                        ProtectedRegion r = i.next();
                        if (!r.getId().contains("pesca")) {
                            if (Jobs.rnd.nextInt(2) != 1) {
                                ev.setExpToDrop(0);
                                ev.getCaught().remove();
                                ev.getPlayer().sendMessage(ChatColor.RED + "Voce nao conseguiu puxar a linha");
                            }
                        }
                    }
                }
            }
            int random = Jobs.rnd.nextInt(100);
            if (random == 1) {
                ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Você encontrou uma pedra brilhosa."));
                ev.getPlayer().getInventory().addItem(CustomItem.getItem(PedraDoPoder.class).generateItem(1));
            }
            if (ativo != null && ativo == TipoBless.Pescaria) {
                random = Jobs.rnd.nextInt(100);
                if (random == 1) {
                    ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Você encontrou uma esponja molhada."));
                    ItemStack esponja = new ItemStack(Material.SPONGE);
                    ItemMeta meta = esponja.getItemMeta();
                    meta.setDisplayName(ChatColor.BLUE + "Esponja Molhada");
                    List<String> lore = new ArrayList<String>();
                    lore.add(ChatColor.GREEN + "Se pelo menos pudesse secar ela..");
                    meta.setLore(lore);
                    esponja.setItemMeta(meta);
                    ev.getPlayer().getInventory().addItem(esponja);
                }
            }
            // BukkitListener.givePlayerExperience(10, ev.getPlayer());
        }
        if (ev.getCaught() != null && (ev.getCaught() instanceof Item || ev.getCaught() instanceof Entity)) {
            TipoBless ativo = TipoBless.save.getTipo(ev.getPlayer());
            int lvl = ev.getPlayer().getLevel();
            int xp = XP.getExpPorAcao(lvl) * 2;
            if (ativo != null && ativo == TipoBless.Pescaria) {
                xp *= 5;
            }
            XP.changeExp(ev.getPlayer(), xp);
        }
        Farmer.pesca(ev);
    }

    private boolean hasChangedBlockCoordinates(final Location fromLoc, final Location toLoc) {
        return !(fromLoc.getWorld().equals(toLoc.getWorld())
                && fromLoc.getBlockX() == toLoc.getBlockX()
                && fromLoc.getBlockY() == toLoc.getBlockY()
                && fromLoc.getBlockZ() == toLoc.getBlockZ());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void move(PlayerMoveEvent event) {

        Material m = event.getPlayer().getLocation().getBlock().getType();
        if (!hasChangedBlockCoordinates(event.getFrom(), event.getTo())) {
            return;
        }

        if (Paralyze.isParalizado(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "Voce está paralizado");
        }

        Material pisando = event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType();
        if (Armadilha.armadilhas.containsKey(event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN))) {
            Armadilha.armadilhas.remove(event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN));
            Detonador.explodeTrap(event.getPlayer().getLocation(), null, null);
        }

        String meta = MetaShit.getMetaString("Pergaminho", event.getPlayer());
        // se ele tava usando um pergaminho
        if (meta != null) {
            int idTask = Integer.valueOf(meta);
            Bukkit.getScheduler().cancelTask(idTask);
            event.getPlayer().removeMetadata("Pergaminho", KoM._instance);
            event.getPlayer().sendMessage(ChatColor.RED + L.m("Você perdeu o foco do teleporte !"));
        }

        //afogando o manolo
        if (m == Material.STATIONARY_WATER || m == Material.WATER && !event.getPlayer().isOp()) {
            m = event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType();
            if (m == Material.STATIONARY_WATER || m == Material.WATER) {
                Block b = event.getPlayer().getWorld().getBlockAt(event.getPlayer().getLocation().getBlockX(), 0, event.getPlayer().getLocation().getBlockZ());
                if (b.getType() == Material.SPONGE) {
                    return;
                }
                ItemStack bota = event.getPlayer().getInventory().getBoots();
                if (bota == null) {
                    Vector vel = event.getPlayer().getVelocity();
                    vel.setY(-0.18);
                    event.getPlayer().setVelocity(vel);
                } else {
                    String customItem = CustomItem.getCustomItem(bota);
                    if (customItem == null) {
                        if (event.getPlayer().getLevel() > 80) {
                            if (taPelado(event.getPlayer())) {
                                return;
                            }
                        }
                        Vector vel = event.getPlayer().getVelocity();
                        vel.setY(-0.18);
                        event.getPlayer().setVelocity(vel);
                    } else if (!customItem.equalsIgnoreCase(L.m("Pe de Pato"))) {
                        Vector vel = event.getPlayer().getVelocity();
                        vel.setY(-0.18);
                        event.getPlayer().setVelocity(vel);
                    }
                }

            }
        } else if (m == Material.VINE) {
            if (event.getPlayer().isOp()) {
                return;
            }
            boolean podeEscalar = false;
            if (event.getPlayer().getInventory().getLeggings() != null) {
                String customItem = CustomItem.getCustomItem(event.getPlayer().getInventory().getLeggings());
                if (customItem != null && customItem.equalsIgnoreCase(L.m("Calcas de Escalar"))) {
                    podeEscalar = true;
                }
            }
            if (!podeEscalar && event.getFrom().getY() < event.getTo().getY()) {
                event.setCancelled(true);
                Block aqui = event.getPlayer().getLocation().getBlock();
                while (aqui != null && (aqui.getType() == Material.AIR || aqui.getType() == Material.VINE)) {
                    aqui = aqui.getRelative(BlockFace.DOWN);
                }
                event.getPlayer().sendMessage(ChatColor.RED + L.m("Você não consegue escalar isto !"));
                Location l = aqui.getRelative(BlockFace.UP).getLocation();
                l.setX(l.getX() + 0.5);
                l.setZ(l.getZ() + 0.5);
                event.getPlayer().teleport(l);
            }

        } else if (pisando == Material.MYCEL) {
            if (!event.getPlayer().hasPotionEffect(PotionEffectType.POISON)) {
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 30, 0));
                event.getPlayer().sendMessage(ChatColor.RED + L.m("Este piso contem muitos fungos, e você não se sente bem..."));
            }
        }

        // se mudou de chunk
        if (ClanLand.isSameChunk(event.getFrom(), event.getTo())) {
            return;
        }

        if (event.getTo().getWorld().getName().equalsIgnoreCase("Vila") || event.getTo().getWorld().getName().equalsIgnoreCase("Dungeon")) {
            return;
        }

        int mobLevel1 = ClanLand.getMobLevel(event.getFrom());
        int mobLevel2 = ClanLand.getMobLevel(event.getTo());
        if (mobLevel2 > 20) {
            if (!CFG.dungeons.contains(event.getPlayer().getWorld().getName()) && !CFG.safeMaps.contains(event.getPlayer().getWorld().getName()) && !CFG.warMaps.contains(event.getPlayer().getWorld().getName())) {
                event.getPlayer().sendMessage(ChatColor.RED + L.m("Voce chegou no limite deste mundo !"));
                event.setCancelled(true);
            }
        }
        String type = ClanLand.getTypeAt(event.getTo());
        if (mobLevel1 != mobLevel2) {
            if (mobLevel2 > 20 && event.getTo().getWorld().getName().equalsIgnoreCase(CFG.mundoGuilda)) {
                if (!CFG.dungeons.contains(event.getPlayer().getWorld().getName()) && !CFG.safeMaps.contains(event.getPlayer().getWorld().getName()) && !CFG.warMaps.contains(event.getPlayer().getWorld().getName())) {
                    event.getPlayer().sendMessage(ChatColor.RED + L.m("Voce chegou no limite deste mundo !"));
                    event.setCancelled(true);
                }
                //mobLevel2 = 666;
                //event.getPlayer().sendMessage(ChatColor.AQUA + "~ Zona de nível " + mobLevel2 + ChatColor.RED + " [PERIGO NIVEL MÀXIMO!]");
            } else {
                if (type.equalsIgnoreCase("WILD")) {
                    TitleAPI.sendTitle(event.getPlayer(), 10, 10, 120, ChatColor.AQUA + "~ Zona de nivel " + (mobLevel2 * 5), "");
                }
            }
        }

        String lastType = ClanLand.getTypeAt(event.getFrom());

        if (type.equalsIgnoreCase("SAFE") && !lastType.equalsIgnoreCase("SAFE")) {
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 60 * 60, 1));
        }
        if (!type.equalsIgnoreCase("SAFE") && lastType.equalsIgnoreCase("SAFE")) {
            event.getPlayer().removePotionEffect(PotionEffectType.SPEED);
        }

        if (type.equals("WILD") && event.getPlayer().getLevel() < 3) {

            if (!lastType.equalsIgnoreCase("WILD")) {
                if (!event.getPlayer().hasMetadata("1wild")) {
                    MetaShit.setMetaObject("1wild", event.getPlayer(), true);
                    event.getPlayer().sendMessage("§e§l[Dica] §aEstas terras são 'sem dono', ou seja, livres. Você pode criar uma guilda e pegar uma terra para você de graça usando o comando §c/f criar <tag> <nome>");
                }
            }

            Chunk c = event.getTo().getChunk();

            ClanLand.marcaChunk(event.getPlayer(), c);

        }

        if (type.equals(lastType)) {
            if (type.equals("CLAN") && lastType.equals("CLAN")) {
                String tag = ClanLand.getClanAt(event.getTo()).getTag();
                String lasttag = ClanLand.getClanAt(event.getFrom()).getTag();
                if (!tag.equals(lasttag)) {
                    ClanLand.update(event.getPlayer(), event.getTo());
                } else {
                    String donoVai = ClanLand.getOwnerAt(event.getTo());
                    String dono2 = ClanLand.getOwnerAt(event.getFrom());
                    if (donoVai == null && dono2 != null) {
                        ClanLand.update(event.getPlayer(), event.getTo());
                    } else if (donoVai != null && dono2 == null) {
                        ClanLand.update(event.getPlayer(), event.getTo());
                    } else if (donoVai != null && dono2 != null && !donoVai.equalsIgnoreCase(dono2)) {
                        ClanLand.update(event.getPlayer(), event.getTo());
                    }
                }
            }
        } else {
            ClanLand.update(event.getPlayer(), event.getTo());
        }
    }

    @EventHandler
    public void playerExp(PlayerExpChangeEvent ev) {
        int qto = ev.getAmount();
        if (KoM.debugMode) {
            ev.getPlayer().sendMessage("Ganhando " + qto + " exp de forma nativa");
        }
        XP.changeExp(ev.getPlayer(), qto, 1);
        ev.setAmount(0);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void coletaItem(PlayerPickupItemEvent ev) {
        if (ev.getPlayer().getVehicle() != null) {
            ev.setCancelled(true);
        }

        addInfoItem(ev.getItem().getItemStack());

        String customItem = CustomItem.getCustomItem(ev.getItem().getItemStack());
        if (customItem != null && customItem.equalsIgnoreCase("Slimeball Envenenada")) {
            Farmer.poisonaSlimeball(ev);
        }
        KoM.chutaBola(ev);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void dropaItem(final PlayerDropItemEvent ev) {

        String dropado = CustomItem.getCustomItem(ev.getItemDrop().getItemStack());
        if (dropado != null && dropado.equalsIgnoreCase("Capacete da Visao")) {
            Runnable r = new Runnable() {

                public void run() {
                    VanishConfig cfg = SimplyVanish.getVanishConfig(ev.getPlayer().getName(), true);
                    if (cfg.see.state) {
                        String customItem = CustomItem.getCustomItem(ev.getPlayer().getInventory().getHelmet());
                        if (customItem == null || !customItem.equalsIgnoreCase("Capacete da Visao")) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + ev.getPlayer().getName() + " remove simplyvanish.see-all");
                            cfg.see.state = false;
                            ev.getPlayer().sendMessage(ChatColor.RED + "Voce tirou o capacete da visao");
                            SimplyVanish.setVanishConfig(ev.getPlayer().getName(), cfg, true);
                            SimplyVanish.updateVanishState(ev.getPlayer());
                        }
                    }
                }
            };
            Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r, 10);
        } else if (dropado != null && dropado.equalsIgnoreCase("Capa da Invisibilidade")) {
            CapaInvisvel.aparece(ev.getPlayer());
        }

        if (ev.getPlayer().getOpenInventory() != null) {
            InventoryType viewing = ev.getPlayer().getOpenInventory().getType();
            if (viewing == InventoryType.ANVIL || viewing == InventoryType.BREWING || viewing == InventoryType.FURNACE || viewing == InventoryType.WORKBENCH) {
                ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce nao conseguiu dropar o item..."));
                ev.setCancelled(true);
                return;
            }
        }

        String customItem = CustomItem.getCustomItem(ev.getItemDrop().getItemStack());
        if (customItem != null) {
            if (customItem.equalsIgnoreCase("Slimeball Envenenada")) {
                if (Jobs.getJobLevel("Fazendeiro", ev.getPlayer()) != 1) {
                    ev.getPlayer().sendMessage(ChatColor.RED + L.m("Apenas fazendeiros experientes podem fazer isto"));
                    ev.setCancelled(true);
                    return;
                } else {
                    if (ev.getPlayer().getWorld().getName().equalsIgnoreCase("dungeon") || ev.getPlayer().getWorld().getName().equalsIgnoreCase("vila")) {
                        ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce nao pode jogar isto aqui !"));
                        ev.setCancelled(true);
                        return;
                    }
                    ev.getPlayer().playSound(ev.getPlayer().getLocation(), Sound.ENTITY_MAGMACUBE_JUMP, 10, 0);
                }
            }
        }

        ItemMeta meta = ev.getItemDrop().getItemStack().getItemMeta();
        if (meta != null && meta.getLore() != null && meta.getLore().size() > 0) {
            for (String lore : meta.getLore()) {
                if (lore.contains("!")) {
                    ev.getItemDrop().remove();
                } else if (lore.contains("[Fixo]")) {
                    ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce nao pode dropar este item !"));
                    ev.setCancelled(true);
                }
            }
        }
    }

    public static void devolveLoot(Player p) {
        List<ItemStack> lista = loots.get(p.getUniqueId());
        loots.remove(p.getUniqueId());
        for (ItemStack s : lista) {
            if (s == null || s.getType() == Material.AIR) {
                continue;
            }
            if (s.getType().name().contains("CHESTPLATE") && (p.getInventory().getChestplate() == null || p.getInventory().getChestplate().getType() == Material.AIR)) {
                p.getInventory().setChestplate(s);
                continue;
            }
            if (((s.getType()==Material.PUMPKIN && s.getAmount()==1) || (s.getType()==Material.SKULL_ITEM) || s.getType().name().contains("HELMET")) && (p.getInventory().getHelmet() == null || p.getInventory().getHelmet().getType() == Material.AIR)) {
                p.getInventory().setHelmet(s);
                continue;
            }
            if (s.getType().name().contains("BOOTS") && (p.getInventory().getBoots() == null || p.getInventory().getBoots().getType() == Material.AIR)) {
                p.getInventory().setBoots(s);
                continue;
            }
            if (s.getType().name().contains("LEGGINGS") && (p.getInventory().getLeggings() == null || p.getInventory().getLeggings().getType() == Material.AIR)) {
                p.getInventory().setLeggings(s);
                continue;
            }
            if (p.getInventory().firstEmpty() != -1) {
                p.getInventory().addItem(s);
            } else {
                p.getWorld().dropItemNaturally(p.getLocation(), s);
            }
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent ev) {
        if (ev.getPlayer().hasMetadata("NPC")) {
            return;
        }

        VanishConfig cfg = SimplyVanish.getVanishConfig(ev.getPlayer().getName(), true);
        //if (cfg.see.state) {
        String customItem = CustomItem.getCustomItem(ev.getPlayer().getInventory().getHelmet());
        if (customItem != null || customItem == null) {

            if (customItem == null || !customItem.equalsIgnoreCase("Capacete da Visao")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + ev.getPlayer().getName() + " remove simplyvanish.see-all");
                cfg.see.state = false;
                SimplyVanish.setVanishConfig(ev.getPlayer().getName(), cfg, true);
                SimplyVanish.updateVanishState(ev.getPlayer());
            }
        }
        //}

        // Check if player has completed tutorial
        if (!KoM.database.hasRegisteredClass(ev.getPlayer().getUniqueId().toString())) {
            //player.message(ChatColor.RED, MSG.REQUIRE_TUTORIAL);
            ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce precisa terminar o tutorial !"));
            if (Bukkit.getWorld("dungeon") == null) {
                BungeeCordKom.tp(ev.getPlayer(), CFG.localTutorial);
            } else {
                ev.setRespawnLocation(CFG.localTutorial.toLocation());
            }
        }

        // Award extra loot?
        if (loots.containsKey(ev.getPlayer().getUniqueId())) {
            devolveLoot(ev.getPlayer());
        }
    }

    @EventHandler
    public void mudaItem(PlayerItemHeldEvent ev) {
        if (ev.getPlayer().hasMetadata("epichax") || ev.getPlayer().hasMetadata("epichaxpronta")) {
            ev.setCancelled(true);
        }
        if (Paralyze.isParalizado(ev.getPlayer())) {
            ev.setCancelled(true);
        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent ev) {
        handlePlayerJoin(ev.getPlayer());
    }

    public void handlePlayerJoin(Player p) {
        if (p.hasMetadata("NPC")) {
            return;
        }

        p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(100.0D);

        p.setMaxHealth(Health.getMaxHealth(p, p.getLevel()));

        if (KoM.safeMode && !p.isOp()) {
            p.kickPlayer("Servidor em Manutenção");
            return;
        }

        // Award Stored Loot.
        if (GeneralListener.loots.containsKey(p.getUniqueId())) {
            List<ItemStack> loots = GeneralListener.loots.get(p.getUniqueId());
            GeneralListener.loots.remove(p.getUniqueId());
            for (ItemStack s : loots) {
                p.getInventory().addItem(s);
            }

        }

        if (p.getWorld().getName().equalsIgnoreCase("eventos") || p.getWorld().getName().equalsIgnoreCase("dungeon") || p.getWorld().getName().equalsIgnoreCase("arena")) {
            if (p.getWorld().getName().equalsIgnoreCase("dungeon")) {
                long agora = System.currentTimeMillis() / 1000;
                if (agora > KoM.ENABLE_TIME + 60) {
                    BungeeCordKom.tp(p, CFG.spawnTree);
                }
            } else {
                BungeeCordKom.tp(p, CFG.spawnTree);
            }
        }

        ClanLand.update(p, p.getLocation());

        ApplicableRegionSet set = KoM.worldGuard.getRegionManager(p.getWorld()).getApplicableRegions(p.getLocation());
        if (set.queryState(null, DefaultFlag.GHAST_FIREBALL) == StateFlag.State.DENY) {
            BungeeCordKom.tp(p, CFG.spawnTree);
        }

        // Check if completed tutorial.
        if (!KoM.database.hasRegisteredClass(p.getUniqueId().toString())) {
            p.setLevel(1);
            MetaShit.setMetaObject("tutorial", p, true);
            if (p.getBedSpawnLocation() == null) {
                p.setBedSpawnLocation(CFG.localTutorial.toLocation());
                p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "[Tutorial]" + ChatColor.YELLOW + "Preste ATENÇÃO !");
            } else {
                p.sendMessage(ChatColor.GREEN + "Você continuou o tutorial de onde parou...");
                p.sendMessage(ChatColor.GREEN + "Não desista !");
                p.teleport(p.getBedSpawnLocation());
            }
        }

        if (!KarmaFameTables.cacheTitulos.containsKey(p.getUniqueId())) {
            int karma = KoM.database.getKarma(p.getUniqueId());
            int fama = KoM.database.getFama(p.getUniqueId());
            String titulo = KarmaFameTables.getTitle(karma, fama);
            KarmaFameTables.cacheTitulos.put(p.getUniqueId(), titulo);
            String tituloTrabalhado = Titulos.trabalhaTitulo(titulo, p, ChatColor.AQUA);
            Titulos.setTitulo(p, titulo, ChatColor.AQUA);
            if (!titulo.trim().equalsIgnoreCase("")) {
                p.sendMessage(KoM.tag + ChatColor.GREEN + "Sua fama e seu caráter o fizeram ser conhecido como " + tituloTrabalhado);
                p.sendMessage(KoM.tag + ChatColor.GREEN + "Use /titulo para ver seus títulos.");
            }
        }

        if (RankCache.souTopEm.containsKey(p.getUniqueId())) {
            p.sendMessage(KoM.tag + " " + ChatColor.GREEN + "Por ser um dos melhores no ranking de " + ChatColor.GOLD + RankCache.souTopEm.get(p.getUniqueId()) + ChatColor.GREEN + " voce pode usar um titulo especial. Use o comando " + ChatColor.AQUA + "/titulo");

            if (!jaAvisou.contains(p.getUniqueId())) {
                jaAvisou.add(p.getUniqueId());
                RankedPlayer rp = RankDB.getPlayer(RankCache.souTopEm.get(p.getUniqueId()), p);
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    pl.sendMessage(ChatColor.GREEN + p.getName() + " top " + rp.posicao + " no ranking de " + rp.stat.titulo + " entrou");
                }
            }

        }
    }

    public static HashSet<UUID> jaAvisou = new HashSet<UUID>();

    @EventHandler
    public void usaBalde(PlayerBucketFillEvent ev) {
        Location bp = ev.getBlockClicked().getLocation();
        if (bp.getWorld().getName().equalsIgnoreCase("dungeon")) {
            ev.setCancelled(true);
            return;
        }
        String type = ClanLand.getTypeAt(bp);
        if (type.equalsIgnoreCase("SAFE") || type.equalsIgnoreCase("WARZ")) {
            ev.setCancelled(true);
            return;
        }
        if (ev.getPlayer().isOp()) {
            return;
        }
    }

    @EventHandler
    public void usaBalde(PlayerBucketEmptyEvent ev) {
        if (ev.getPlayer().isOp()) {
            return;
        }
        Location bp = ev.getBlockClicked().getLocation();
        if (bp.getWorld().getName().equalsIgnoreCase("dungeon")) {
            ev.setCancelled(true);
            return;
        }
        String type = ClanLand.getTypeAt(bp);
        if (type.equalsIgnoreCase("SAFE") || type.equalsIgnoreCase("WARZ")) {
            ev.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onEntityPortal(PlayerPortalEvent ev) {
        Player p = ev.getPlayer();

        if (p.hasPotionEffect(PotionEffectType.CONFUSION)) {
            return;
        }

        p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 2, 0));
        Block checkBlock = ev.getFrom().getBlock().getRelative(BlockFace.DOWN);
        for (int x = 0; x < 3; x++) {
            checkBlock = checkBlock.getRelative(BlockFace.DOWN);
            if (KoM.debugMode) {
                KoM.log.info("Checking Block: " + checkBlock.getType().toString());
            }

            if (checkBlock.getType() == Material.CHEST) {
                Chest c = (Chest) checkBlock.getState();
                for (ItemStack stack : c.getBlockInventory().getContents()) {
                    if (stack != null && (stack.getType() == Material.WRITTEN_BOOK || stack.getType() == Material.BOOK_AND_QUILL)) {
                        BookMeta m = (BookMeta) stack.getItemMeta();
                        if (KoM.debugMode) {
                            KoM.log.info("Book found!" + (m.hasTitle() ? " '" + m.getTitle() + "' by " + m.getAuthor() : "Book and Quill"));
                        }
                        if (checkBlock.getRelative(BlockFace.UP).getType() == Material.DIAMOND_BLOCK) {
                            if (!p.hasPermission("kom.vip")) {
                                p.sendMessage(ChatColor.RED + L.m("Apenas nobres podem usar este portal"));
                                return;
                            }
                        }

                        if (m.getTitle() != null && m.getTitle().equalsIgnoreCase("TP")) {
                            BungLocation loc = BookPortal.getLocationFromBook(stack);
                            if (loc != null) {
                                p.sendMessage(ChatColor.LIGHT_PURPLE + "* poof *");
                                // PlayEffect.play(VisualEffect.SOUND, p.getLocation(), "type:PORTAL_TRAVEL pitch:3");
                                BungeeCordKom.tp(p, loc);
                                return;
                            } else {

                            }
                            break;
                        }
                    }
                }
                return;
            }
        }
        if (Portal.portais.containsKey(p.getLocation().getBlock())) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 3, 0));
            p.teleport(Portal.portais.get(p.getLocation().getBlock()).getLocation());
        }
    }

    @EventHandler
    public void ovo(PlayerEggThrowEvent ev) {
        ev.setHatching(false);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void cmd(PlayerCommandPreprocessEvent ev) {
        if (ev.getMessage().startsWith("/quests top")) {
            ev.getPlayer().sendMessage(ChatColor.RED + L.m("Deus Jabu não gosta deste comando..."));
            ev.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerTeleport(PlayerTeleportEvent ev) {
        if (ev.getPlayer().hasMetadata("NPC")) {
            return;
        }

        if (ev.getTo().getWorld().getName().equalsIgnoreCase(CFG.mundoGuilda)) {
            int level = ClanLand.getMobLevel(ev.getTo());
            if (level > 20) {
                ev.getPlayer().sendMessage(ChatColor.RED + "Seu teleporte ia te levar a uma zona fora do mundo...");
                ev.setCancelled(true);
                return;
            }
        }

        String type = ClanLand.getTypeAt(ev.getTo());
        String lastType = ClanLand.getTypeAt(ev.getFrom());

        if (type.equalsIgnoreCase("SAFE") && !lastType.equalsIgnoreCase("SAFE")) {
            ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 60 * 60, 1));
        }
        if (!type.equalsIgnoreCase("SAFE") && lastType.equalsIgnoreCase("SAFE")) {
            ev.getPlayer().removePotionEffect(PotionEffectType.SPEED);
        }

        if (ev.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            if (ev.getTo().getBlock().getType() == Material.WOODEN_DOOR || ev.getTo().getBlock().getType() == Material.IRON_DOOR) {
                ev.setCancelled(true);
                return;
            }

            int x = ev.getTo().getBlockX() - 2;
            int y = ev.getTo().getBlockY() - 2;
            int z = ev.getTo().getBlockZ() - 2;
            for (int xx = x; xx < x + 4; xx++) {
                for (int yy = y; yy < y + 4; yy++) {
                    for (int zz = z; zz < z + 4; zz++) {
                        Block b = ev.getTo().getWorld().getBlockAt(xx, yy, zz);
                        if (b.getType() == Material.IRON_DOOR || b.getType() == Material.IRON_DOOR_BLOCK || b.getType() == Material.WOODEN_DOOR) {
                            ev.setCancelled(true);
                            return;
                        }
                    }
                }
            }

        }

        if (ev.getTo() == null || ev.getTo().getWorld() == null || ev.getFrom() == null || ev.getFrom().getWorld() == null) {
            ev.setCancelled(true);
            return;
        }
        if (ev.getTo().getWorld().getName().equalsIgnoreCase("dungeon") || ev.getTo().getWorld().getName().equalsIgnoreCase("vila")) {
            if (ev.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
                ev.setCancelled(true);
                return;
            }

        }
        if (!ev.getTo().getWorld().getName().equalsIgnoreCase(ev.getFrom().getWorld().getName()) && !ev.getTo().getWorld().getName().equalsIgnoreCase("vila")) {
            ev.getPlayer().sendMessage(ChatColor.RED + "~ Zona Nivel " + ClanLand.getMobLevel(ev.getTo()) * 5);
        }

        if (ev.getPlayer().getVehicle() != null) {

        }

        if (ev.getTo().getWorld().getName().equalsIgnoreCase("Vila")) {
            ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 9999, 2));
        }

        if (!KoM.database.hasRegisteredClass(ev.getPlayer().getUniqueId().toString())) {
            //ev.getPlayer().teleport(CFG.localTutorial);

            if (!ev.getTo().getWorld().getName().equalsIgnoreCase("dungeon")) {

                ev.getPlayer().sendMessage(ChatColor.RED + "Você só pode sair daqui quando terminar o tutorial");

                World d = Bukkit.getWorld("dungeon");
                if (d != null) {
                    ev.setTo(CFG.localTutorial.toLocation());
                } else {
                    BungeeCordKom.tp(ev.getPlayer(), CFG.localTutorial);
                    ev.setCancelled(true);
                }
                return;
            }
        }
    }

    @EventHandler
    public void comeOuBebe(PlayerItemConsumeEvent ev) {
        if (ev.getItem().equals(ev.getPlayer().getInventory().getItemInOffHand())) {
            ev.getPlayer().sendMessage(ChatColor.RED + "Coma usando sua mao principal");
            ev.setCancelled(true);
            return;
        }
        if (ev.getItem().getType() == Material.CARROT_ITEM) {
            if (Jobs.getJobLevel("Ladino", ev.getPlayer()) == 1) {
                ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("A cenoura lhe fortalece"));
                ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 60, 0));
            }
        } else if (ev.getItem().getType() == Material.COOKED_BEEF) {
            if (Jobs.getJobLevel("Lenhador", ev.getPlayer()) == 1) {
                ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("O bife lhe fortalece"));
                ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 20, 0));
            }
        } else if (ev.getItem().getType() == Material.COOKED_CHICKEN) {
            if (Jobs.getJobLevel("Engenheiro", ev.getPlayer()) == 1) {
                ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("O frango lhe fortalece"));
                ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 60, 1));
            }
        } else if (ev.getItem().getType() == Material.COOKIE) {
            if (Jobs.getJobLevel("Mago", ev.getPlayer()) == 1) {
                ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("O biscoito lhe fortalece"));
                ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 60, 1));
                ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 10, 1));
            }
        } else if (ev.getItem().getType() == Material.GRILLED_PORK) {
            if (Jobs.getJobLevel("Paladino", ev.getPlayer()) == 1) {
                ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("O porco lhe fortalece"));
                ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 60, 4));
            }
        } else if (ev.getItem().getType() == Material.COOKED_RABBIT) {
            if (Jobs.getJobLevel("Alquimista", ev.getPlayer()) == 1) {
                ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("O coelho lhe fortalece"));
                ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 10, 0));
            }
        } else if (ev.getItem().getType() == Material.MELON) {
            if (Jobs.getJobLevel("Minerador", ev.getPlayer()) == 1) {
                ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("O melao lhe fortalece"));
                ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 10, 0));
                ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 60, 1));
                //ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 30, 0));
            }
        } else if (ev.getItem().getType() == Material.COOKED_FISH && ev.getItem().getData().getData() == 1) { // salmao
            if (Jobs.getJobLevel("Fazendeiro", ev.getPlayer()) == 1) {
                ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("O Peixe fortalece"));
                ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 10, 1));
                ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 40, 1));
                ev.getPlayer().setFoodLevel(20);
            }
        } else if (ev.getItem().getType() == Material.PUMPKIN_PIE) {
            if (Jobs.getJobLevel("Ferreiro", ev.getPlayer()) == 1) {
                ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("A torta lhe fortalece"));
                ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 10, 0));
                ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 60, 0));
            }
        } else if (ev.getItem().getType() == Material.MUSHROOM_SOUP) {
            ev.getPlayer().removePotionEffect(PotionEffectType.SLOW);
            ev.getPlayer().removePotionEffect(PotionEffectType.POISON);
            ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("A sopa lhe curou venenos e congelamentos"));
        } else if (ev.getItem().getType() == Material.RABBIT_STEW) {
            ev.getPlayer().removePotionEffect(PotionEffectType.WEAKNESS);
            ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("A sopa lhe curou de fraquezas"));
        }
    }

}
