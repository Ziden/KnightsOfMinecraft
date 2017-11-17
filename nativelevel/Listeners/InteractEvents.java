package nativelevel.Listeners;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import nativelevel.Auras.Aura;
import nativelevel.CFG;
import nativelevel.Classes.Alchemy.Alchemist;
import nativelevel.Classes.Blacksmithy.Blacksmith;
import nativelevel.Classes.Engineer;
import nativelevel.Classes.Farmer;
import nativelevel.Classes.Lumberjack;
import nativelevel.Classes.Minerador;
import nativelevel.Classes.Thief;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.ItemListener;
import nativelevel.Custom.Items.Ponte;
import nativelevel.Custom.Items.SuperBomba;
import nativelevel.Harvesting.HarvestEvents;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import static nativelevel.Listeners.GeneralListener.taEmCombate;
import nativelevel.Classes.Mage.spelllist.Paralyze;
import nativelevel.Menu.Menu;
import nativelevel.Menu.netMenu;
import nativelevel.MetaShit;
import nativelevel.Attributes.Mana;
import nativelevel.Attributes.Stamina;
import nativelevel.bencoes.TipoBless;
import nativelevel.ComandosNovos.list.KomSubs.CmdOE;
import nativelevel.Jobs.TipoClasse;
import nativelevel.integration.BungeeCordKom;
import nativelevel.integration.SimpleClanKom;
import nativelevel.integration.WorldGuardKom;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.sisteminhas.Dungeon;
import nativelevel.sisteminhas.Horses;
import nativelevel.sisteminhas.Lobo;
import nativelevel.sisteminhas.XP;
import nativelevel.sisteminhas.Mobs;
import nativelevel.lojaagricola.LojaAgricola;
import nativelevel.mercadinho.MenuMercado;
import nativelevel.sisteminhas.QuestsIntegracao;
import nativelevel.spec.PlayerSpec;
import nativelevel.utils.BookUtil;
import nativelevel.utils.Cooldown;
import nativelevel.utils.itemattributes.Slot;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.material.Button;
import org.bukkit.material.Openable;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import tw.kid7.BannerMaker.util.InventoryMenuUtil;

/**
 *
 * @author Ziden
 *
 */
public class InteractEvents implements Listener {

    public static final String NPC_CLEAN_ITEM = "Ronald";
    public static final String NPC_FARM_SHOP = "Rey do Gado";
    public static final String NPC_TAILOR = "Zara";
    public static final String NPC_SHOP = "Narigudo do Mercado";

    @EventHandler(priority = EventPriority.LOW)
    public void interageEntidade(PlayerInteractEntityEvent ev) {

        KoM.debug("interage low inicio");

        if (ev.getRightClicked() != null && ev.getRightClicked().getType() == EntityType.MINECART_HOPPER) {
            ev.setCancelled(true);
        }
        
        if(ev.getRightClicked() != null && ev.getRightClicked().getType()==EntityType.ARMOR_STAND) {
            if(!ev.getPlayer().isOp() && WorldGuardKom.ehSafeZone(ev.getRightClicked().getLocation())) {
                ev.getPlayer().sendMessage(ChatColor.RED+"Voce nao pode fazer isto aqui");
                ev.setCancelled(true);
                return;
            }
        }

        if (ev.getPlayer().getItemInHand() != null && (ev.getPlayer().getItemInHand().getType() == Material.WATER_BUCKET || ev.getPlayer().getItemInHand().getType() == Material.LAVA_BUCKET)) {
            ev.setCancelled(true);
            ev.getPlayer().setVelocity(new Vector(0, -0.2, 0));
            return;
        }

        if (ev.getRightClicked().getType() == EntityType.ITEM_FRAME) {
            String tipo = ClanLand.getTypeAt(ev.getPlayer().getLocation());
            if (ev.getPlayer().getWorld().getName().equalsIgnoreCase("dungeon") || tipo.equalsIgnoreCase("SAFE") || tipo.equalsIgnoreCase("WARZ")) {
                if (!ev.getPlayer().isOp()) {
                    ev.setCancelled(true);
                }
            }
        }

        if (!ev.getPlayer().getWorld().getName().equalsIgnoreCase("dungeon") && ev.getRightClicked() != null && (ev.getRightClicked().getType() == EntityType.MINECART_CHEST
                || ev.getRightClicked().getType() == EntityType.MINECART_FURNACE
                || ev.getRightClicked().getType() == EntityType.MINECART_HOPPER
                || ev.getRightClicked().getType() == EntityType.MINECART_MOB_SPAWNER
                || ev.getRightClicked().getType() == EntityType.MINECART_TNT)) {
            if (!ev.getPlayer().isOp()) {
                ev.setCancelled(true);
                return;
            }
        }

        if (ev.getPlayer().getItemInHand() != null && ev.getPlayer().getItemInHand().getType() == Material.SULPHUR) {
            if (ev.getPlayer().getItemInHand().getEnchantments().size() > 0) {
                ev.getPlayer().sendMessage(ChatColor.RED + L.m("A polvora encantada se desfez entre seus dedos..."));
                ev.getPlayer().setItemInHand(null);
                ev.setCancelled(true);
                return;
            }
        }

        if (ev.getRightClicked().getType() == EntityType.VILLAGER) {
            Villager bixo = (Villager) ev.getRightClicked();
            if (bixo.getCustomName() != null && bixo.getCustomName().equals(NPC_CLEAN_ITEM)) {
                if (ev.getPlayer().isSneaking() == true) {
                    if (ev.getPlayer().getItemInHand().getItemMeta().getLore() != null || ev.getPlayer().getItemInHand().getItemMeta().getDisplayName() != null) {
                        for (String s : ev.getPlayer().getItemInHand().getItemMeta().getLore()) {
                            if (s.contains("Classe:") || s.contains("!")) {
                                return;
                            }
                        }
                        ev.setCancelled(true);
                        ItemMeta meta = ev.getPlayer().getItemInHand().getItemMeta();
                        meta.setLore(new ArrayList<String>());
                        meta.setDisplayName(null);
                        ev.getPlayer().getItemInHand().setItemMeta(meta);
                        ev.getPlayer().sendMessage("§2O Item foi limpado");
                    } else {
                        ev.setCancelled(true);
                        ev.getPlayer().sendMessage("§7O Item já está limpo");
                    }
                } else if (ev.getPlayer().getItemInHand() == null || ev.getPlayer().getItemInHand().getType() == Material.AIR) {
                    ev.setCancelled(true);
                    ev.getPlayer().sendMessage("§7Coloque um item na mão para reseta-lo");
                } else {
                    ev.setCancelled(true);
                    ev.getPlayer().sendMessage("§eClique com botão direito e o SHIFT ao mesmo tempo para limpar o item que estiver na sua mão");
                    ev.getPlayer().sendMessage("§cIsso removera tudo do seu item, nome e lore");
                }
            } else if (bixo.getCustomName() != null && bixo.getCustomName().equals(NPC_SHOP)) {
                MenuMercado.abre(ev.getPlayer());
            }
        }

        Lobo.interage(ev);

        KoM.debug("Interage low medio");

        Engineer.prende(ev);

        if (ev.getRightClicked() instanceof LivingEntity && ev.getPlayer().isOp() && ev.getPlayer().getItemInHand() != null && ev.getPlayer().getItemInHand().getType() == Material.BONE) {
            if (ev.getPlayer().isSneaking()) {
                ev.getPlayer().sendMessage("vidaCustom " + ((LivingEntity) ev.getRightClicked()).getMaxHealth());
                ev.getPlayer().sendMessage("bonusDano " + MetaShit.getMetaObject("bonusDano", ev.getRightClicked()));
                ev.getPlayer().sendMessage("nivel " + MetaShit.getMetaObject("nivel", ev.getRightClicked()));
                HashSet<Mobs.EfeitoMobs> efeitos = (HashSet<Mobs.EfeitoMobs>) MetaShit.getMetaObject("efeitos", ev.getRightClicked());
                ev.getPlayer().sendMessage("efeitos " + (efeitos == null ? "nenhum" : efeitos.toString()));
            } else {
                ((LivingEntity) ev.getRightClicked()).damage(9999999D);
            }
        } else {
            if (ev.getRightClicked() != null && ev.getRightClicked().getType() == EntityType.VILLAGER) {
                LivingEntity e = (LivingEntity) ev.getRightClicked();
                if (e.getCustomName() != null) {
                    if (ChatColor.stripColor(e.getCustomName()).equalsIgnoreCase(NPC_FARM_SHOP)) {
                        ev.setCancelled(true);
                        LojaAgricola.abreMenu(ev.getPlayer());
                    } else if (ChatColor.stripColor(e.getCustomName()).equalsIgnoreCase((NPC_TAILOR))) {
                        ev.setCancelled(true);
                        InventoryMenuUtil.openMenu(ev.getPlayer());
                    }
                }
            } else {
                Horses.interactHorse(ev);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void interageHigh(final PlayerInteractEvent ev) {

        if (ev.getItem() != null && (ev.getItem().getType() == Material.MONSTER_EGG || ev.getItem().getType() == Material.MONSTER_EGGS)) {
            Farmer.handleEgg(ev);
        }
        
        if(ev.getClickedBlock() != null && ev.getItem() != null && ev.getItem().getType()==Material.ARMOR_STAND) {
            if(!ev.getPlayer().isOp() && WorldGuardKom.ehSafeZone(ev.getClickedBlock().getLocation())) {
                ev.getPlayer().sendMessage(ChatColor.RED+"Voce nao pode usar isto aqui");
                ev.setCancelled(true);
                return;
            }
        }

        Aura.interage(ev);
        if (ev.isCancelled()) {
            KoM.debug("Cancelando antes do paladino");
        }

        if (ev.getPlayer().getLocation().getPitch() < -88) {
            if (Jobs.getJobLevel("Paladino", ev.getPlayer()) == 1) {
                String aqui = ClanLand.getTypeAt(ev.getPlayer().getLocation());

                if (!Cooldown.isCooldown(ev.getPlayer(), "grito")) {
                    Cooldown.setMetaCooldown(ev.getPlayer(), "grito", 5000);
                    if (aqui == null || !aqui.equalsIgnoreCase("safe")) {
                        if (ev.getItem() != null && ev.getItem().getType().name().contains("SWORD")) {
                            if (Mana.spendMana(ev.getPlayer(), 40)) {
                                ev.getPlayer().sendMessage(ChatColor.GREEN + "Voce da um grito para chamar a atenção !");
                                for (Entity e : ev.getPlayer().getNearbyEntities(5, 5, 5)) {
                                    if (e.getType() == EntityType.PLAYER) {
                                        Player alvo = (Player) e;
                                        if (Thief.taInvisivel(alvo)) {
                                            Thief.revela(alvo);
                                        }
                                        alvo.sendMessage(ChatColor.AQUA + ev.getPlayer().getName() + " da um grito muito alto");
                                        if (e.getWorld().getName().equalsIgnoreCase(CFG.mundoGuilda)) {
                                            Vector ve = e.getLocation().toVector();
                                            Vector v = ve.subtract(ev.getPlayer().getLocation().toVector()).normalize().multiply(2);
                                            v.setY(0.6);
                                            e.setVelocity(v);
                                        }
                                    } else if (e instanceof Monster) {
                                        ((Monster) e).setTarget(ev.getPlayer());
                                        Vector ve = e.getLocation().toVector();
                                        Vector v = ve.subtract(ev.getPlayer().getLocation().toVector()).normalize().multiply(2);
                                        v.setY(0.6);
                                        e.setVelocity(v);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    ev.getPlayer().sendMessage(ChatColor.RED+"Aguarde para poder fazer isto novamente.");
                }
            }
        }
        if (ev.getItem() != null && ev.getPlayer().isSneaking() && ev.getItem().getType().name().contains("PICKAXE") && Jobs.getJobLevel("Minerador", ev.getPlayer()) == 1) {
            if (ev != null && ev.getAction() != null && (ev.getAction() == Action.RIGHT_CLICK_AIR || ev.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                if (ev.getPlayer().hasMetadata("disarmar")) {
                    ev.getPlayer().removeMetadata("disarmar", KoM._instance);
                    ev.getPlayer().sendMessage(ChatColor.GREEN + "Voce nao esta mais em modo de desarmamento.");
                } else {
                    MetaShit.setMetaObject("disarmar", ev.getPlayer(), true);
                    ev.getPlayer().sendMessage(ChatColor.GREEN + "Voce entrou no modo disarmamento.");
                }
            } else if (ev.getAction() == Action.LEFT_CLICK_AIR || ev.getAction() == Action.LEFT_CLICK_BLOCK) {
                if (ev.getPlayer().hasMetadata("disarmar")) {
                    ev.getPlayer().sendMessage(ChatColor.GREEN + "Nao desarmou nada.");
                    Stamina.spendStamina(ev.getPlayer(), 35);
                    if (!ev.getPlayer().hasMetadata("disarmmsg")) {
                        MetaShit.setMetaObject("disarmmsg", ev.getPlayer(), "");
                        ev.getPlayer().sendMessage(ChatColor.GOLD + "[Dica] " + ChatColor.GREEN + " Use Shift+Click direito para sair do modo desarmador.");
                    }
                }
            }
        }

        if (ev.getItem() != null && ev.getItem().getType() == Material.HOPPER_MINECART) {
            if (!ev.getPlayer().isOp()) {
                ev.setCancelled(true);
                return;
            }
        }

        TipoBless.interage(ev);

        Lobo.interactOsso(ev);

        HarvestEvents.interage(ev);

        if (Thief.taInvisivel(ev.getPlayer())) {
            if (ev.getAction() == Action.RIGHT_CLICK_BLOCK && ev.getPlayer().getLocation().getPitch() > 88) {
                ev.getPlayer().sendMessage(ChatColor.GREEN + "Luminosidade : " + ev.getPlayer().getLocation().getBlock().getLightLevel());
            }
        }

        if (ev.getClickedBlock() != null && ev.getClickedBlock().getType() == Material.CAKE_BLOCK) {
            double vidaGanha = ev.getPlayer().getMaxHealth();
            vidaGanha += ev.getPlayer().getHealth();
            if (vidaGanha >= ev.getPlayer().getMaxHealth()) {
                vidaGanha = ev.getPlayer().getMaxHealth();
            }

            if (taEmCombate(ev.getPlayer()) <= 0) {
                ev.getPlayer().setHealth(vidaGanha);
                Mana.changeMana(ev.getPlayer(), 400);
                Stamina.changeStamina(ev.getPlayer(), 400);
                ev.getPlayer().sendMessage(ChatColor.AQUA + "* om nom nom *");
            } else {
                ev.getPlayer().sendMessage(ChatColor.RED + "Se preocupe com a luta e não com a comida !");
            }
            ev.setCancelled(true);
            if (Jobs.rnd.nextInt(3) == 1) {
                byte data = ev.getClickedBlock().getData();
                data++;
                if (data == 7) {
                    ev.getClickedBlock().setType(Material.AIR);
                } else {
                    ev.getClickedBlock().setData(data);
                    ev.getClickedBlock().getState().update();
                }
            }
        }
    }

    @EventHandler
    public void interageDenovo(PlayerInteractEvent ev) {

        interageLow(ev);

        //  KoM.debug("inter normal com item " + ev.getItem().getType().name());
        if (ev.getAction() != Action.PHYSICAL && ev.getItem() != null && ev.getItem().getType() == Material.COMPASS && !ev.getPlayer().isSneaking()) {
            KoM.debug("Abrindo inv");
            QuestsIntegracao.abreInventarioQuests(ev.getPlayer());
            if (ev.getPlayer().isOp()) {
                ev.getPlayer().sendMessage("Pra usar o compass do WorldEdit segura shift");
            }
            ev.setCancelled(true);
            return;
        }

        if (ev.getClickedBlock() != null && ev.getClickedBlock().getType() == Material.BREWING_STAND && ev.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (Jobs.getJobLevel(Jobs.Classe.Alquimista, ev.getPlayer()) == TipoClasse.NADA) {
                ev.setCancelled(true);
                ev.getPlayer().sendMessage(ChatColor.RED + "Voce não tem a menor idéia de como usar isso, melhor não mexer pois pode quebrar, e isso deixaria o dono pistola.");
                return;
            }
        }

        if (ev.getClickedBlock() != null && ev.getClickedBlock().getType() == Material.ENDER_CHEST && ev.getAction() == Action.RIGHT_CLICK_BLOCK) {

            ev.setCancelled(true);

            if (CmdOE.xeretando.contains(ev.getPlayer().getUniqueId())) {
                return;
            }

            ev.getPlayer().sendMessage(ChatColor.GREEN + "Voce abriu seu Banco");

            ItemStack[] items = KoM.database.getBanco(ev.getPlayer().getUniqueId());
            int slots = KoM.database.getSlotsBanco(ev.getPlayer().getUniqueId().toString());
            int linhas = slots + 1;
            if (linhas > 5) {
                linhas = 5;
            }

            Inventory banco = Bukkit.createInventory(ev.getPlayer(), 9 * linhas, "Banco");
            if (items != null) {
                try {
                    banco.setContents(items);
                } catch (Exception e) {
                    ev.getPlayer().sendMessage(ChatColor.RED + "Seu banco foi corrompido. Chame um staff.");
                }
            }
            ev.getPlayer().openInventory(banco);
        } else if (ev.getClickedBlock() != null) {
            if (ev.getItem() != null && ev.getItem().getType().name().contains("PICKAXE")) {
                Minerador.escala(ev);
            }
        }
        KoM.debug("fim inter normal");
    }

    public void interageLow(PlayerInteractEvent ev) {

        KoM.debug("Interage lowest " + ev.isCancelled());

        // if(ev.isCancelled() && ev.getAction()==Action.RIGHT_CLICK_AIR)
        //     ev.setCancelled(false);
        if (Paralyze.isParalizado(ev.getPlayer())) {
            ev.setCancelled(true);
            KoM.debug("paralizado");
            return;
        }
        
        if(Engineer.validaPrisao(ev))
            return;

        // CUSTOM ITEMS
        ItemListener.interage(ev);
        //   if (ev.isCancelled()) {
        //      KoM.debug("custom item cancel");
        //  return;
        //   }

        if (ev.getItem() != null && !CustomItem.podeUsar(ev.getPlayer(), ev.getItem())) {
            ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce nao sabe usar isto !"));
            ev.setCancelled(true);
            return;
        }

        if (ev.getItem() != null && ev.getItem().getType() == Material.SPONGE) {
            ev.setCancelled(true);
            KoM.debug("cancelado sponge");
            return;
        }

       

        KoM.debug("Entrando bloco hidraulico");

        if (ev.getItem() != null && ev.getItem().getType() == Material.GOLD_BLOCK && ev.getAction() == Action.RIGHT_CLICK_BLOCK && Jobs.getJobLevel("Engenheiro", ev.getPlayer()) == 1) {
            String ci = CustomItem.getCustomItem(ev.getItem());
            if (ci != null && ci.equalsIgnoreCase("Bloco Hidraulico")) {
                if (ClanLand.getTypeAt(ev.getClickedBlock().getLocation()).equalsIgnoreCase("SAFE") || ev.getPlayer().getWorld().getName().equalsIgnoreCase("dungeon") || ev.getPlayer().getWorld().getName().equalsIgnoreCase("vila") || ev.getPlayer().getWorld().getName().equalsIgnoreCase("barco") || ev.getPlayer().getWorld().getName().equalsIgnoreCase("arena")) {
                    ev.getPlayer().sendMessage(ChatColor.RED + "Isso nao funciona aqui");
                } else {
                    if (!KoM.gastaItem(ev.getPlayer(), ev.getPlayer().getInventory(), Material.GOLD_INGOT, (byte) 0)) {
                        ev.getPlayer().sendMessage(ChatColor.RED + "Voce precisa de um ouro para fazer isto !");
                    } else {
                        Block alvo = ev.getClickedBlock().getRelative(ev.getBlockFace());
                        alvo.setType(Material.GOLD_BLOCK);
                        KoM.rewind.put(alvo, Material.AIR);
                        Ponte.bloqueios.add(alvo);
                        ev.getPlayer().sendMessage(ChatColor.GREEN + "Voce colocou um bloco hidraulico");
                        final Block b = ev.getClickedBlock();
                        final BlockFace f = ev.getBlockFace();
                        Runnable r = new Runnable() {
                            public void run() {
                                KoM.rewind.remove(b.getRelative(f));
                                Ponte.bloqueios.remove(b.getRelative(f));
                                b.getRelative(f).setType(Material.AIR);
                            }
                        };
                        Bukkit.getScheduler().scheduleSyncRepeatingTask(KoM._instance, r, 20 * 60, 20 * 60);
                    }
                }
            }
            KoM.debug("cancelado bloco H");
            ev.setCancelled(true);
            return;
        }

        KoM.debug("Antes da dungeon");

        Dungeon.empurraBloco(ev);
        Dungeon.itemAtivaRedstone(ev);

        KoM.debug("Pre Right com action " + ev.getAction());

        if (ev.getAction() == Action.RIGHT_CLICK_AIR || ev.getAction() == Action.RIGHT_CLICK_BLOCK) {
            // evitando bug de dupar items
            KoM.debug("RIGHT CLICK INTERACT");
            if (ev.getClickedBlock() != null && ev.getClickedBlock().getType() == Material.CHEST) {
                if (ev.getPlayer().getVehicle() != null) {
                    ev.setCancelled(true);
                }
            }

            ItemStack item = ev.getItem();
            if (item != null) {
                if (ev.getPlayer().getWorld().getName().equalsIgnoreCase("dungeon")) {
                    if (item.getType() == Material.BUCKET) {
                        ev.setCancelled(true);
                        return;
                    }
                }
                ItemMeta meta = item.getItemMeta();
                if (meta != null && meta.getLore() != null && meta.getLore().size() > 0) {
                    for (String l : meta.getLore()) {
                        if (l.contains("!")) {
                            ev.getPlayer().sendMessage(ChatColor.RED + L.m("O item ilusorio se desfaz"));
                            ev.getPlayer().setItemInHand(null);
                            ev.setCancelled(true);
                        }
                    }
                }
            }

            if (item != null && (item.getType() == Material.POTION && item.getType().getId() == 373)) {
                try {
                    KoM.debug("Pocoes");
                    Potion p = Potion.fromItemStack(item);
                    if (p.isSplash()) {
                        if (!ev.getPlayer().hasPotionEffect(PotionEffectType.SLOW)) {
                            if (Jobs.getJobLevel(L.get("Classes.Alchemist"), ev.getPlayer()) != 1 || ev.getPlayer().getLevel() < 15) {
                                ev.getPlayer().sendMessage(ChatColor.RED + L.m("Apenas bons alquimistas sabem arremeças poções !"));
                                ev.setCancelled(true);
                                return;
                            } else {
                                if (!Mana.spendMana(ev.getPlayer(), 15)) {
                                    ev.setCancelled(true);
                                    return;
                                }
                                ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 1, 0));
                                if (ev.getItem().getAmount() < 1) {
                                    ev.getItem().setAmount(1);
                                    ev.getPlayer().setItemInHand(null);
                                }
                            }
                        } else {
                            ev.setCancelled(true);
                        }

                    }
                } catch (Exception e) {
                    //NativeLevel.log.info(e.getMessage());
                    e.printStackTrace();
                    // isso é o erro do waterbottle... qnd faz Potion.fromItemStack no waterbottle..
                }
            }
            // They right clicked
            KoM.debug("Interage ITEM");
            if (ev.getItem() != null) {

                // They were holding an item when they did it, (all spells are items)
                int slot = ev.getPlayer().getInventory().getHeldItemSlot();

                if (item != null && item.getType() == Material.APPLE && ev.getPlayer().isOp()) {
                    if (ev.getClickedBlock() != null) {
                        ev.getPlayer().sendMessage(L.m("Material: %", ev.getClickedBlock().getType().toString()));
                        ev.getPlayer().sendMessage(L.m("Data: %", ev.getClickedBlock().getData()));
                        ev.getPlayer().sendMessage(L.m("State Data: %", ev.getClickedBlock().getState().getData().getData()));
                    }
                }

                Material armor = Blacksmith.getArmorType(item.getType());
                if (armor != null) {
                    if (armor == Material.LEATHER) {
                        Farmer.fixLeatherArmor(ev);
                    }
                    if (!ev.getPlayer().isOp()) {
                        ev.setCancelled(true);
                        ev.getPlayer().updateInventory();
                        ev.getPlayer().sendMessage(ChatColor.RED + L.m("Você não pode colocar equipamentos desta maneira !"));
                    }
                    // nao pode equipar clicando assim tem q ir la no inventario e por...
                    return;
                }
                if (ev.getItem().getType() == Material.FISHING_ROD) {
                    //String customItem = CustomItem.getCustomItem(ev.getItem());
                    //if (customItem == null) {
                    if (Jobs.getJobLevel("Fazendeiro", ev.getPlayer()) != 1) { // só fazendeiro primario pode
                        ev.getPlayer().sendMessage(ChatColor.AQUA + Menu.getSimbolo("Fazendeiro") + " " + ChatColor.RED + "Apenas bons fazendeiros sabem usar isto !");
                        ev.setCancelled(true);
                    }
                    // }
                }
                if (ev.getItem() != null && ev.getItem().getType() == Material.MINECART) {
                    if (ev.getClickedBlock() != null && ev.getClickedBlock().getType() != Material.RAILS) {
                        ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce nao pode colocar o minecart aí !"));
                        ev.setCancelled(true);
                        return;
                    }
                    if (ev.getClickedBlock().getRelative(BlockFace.DOWN).getType() != Material.GOLD_BLOCK) {
                        ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce nao pode colocar o minecart aí !"));
                        ev.setCancelled(true);
                        return;
                    }
                    Minecart cart = (Minecart) ev.getPlayer().getLocation().getWorld().spawnEntity(ev.getClickedBlock().getLocation(), EntityType.MINECART);
                    Location carrin = cart.getLocation().clone();
                    carrin.setX(carrin.getX() + 0.5);
                    carrin.setZ(carrin.getZ() + 0.5);
                    carrin.setY(carrin.getY() + 0.5);
                    ev.getPlayer().teleport(carrin);
                    cart.setPassenger(ev.getPlayer());
                    ev.getPlayer().setItemInHand(null);
                    ev.setCancelled(true);
                }
            }
        }

        KoM.debug("pre salto");
        if (!ev.getPlayer().hasPotionEffect(PotionEffectType.WATER_BREATHING) && !ev.getPlayer().getWorld().getName().equalsIgnoreCase("dungeon") && ev.getAction() == Action.RIGHT_CLICK_AIR) {
            if (Jobs.getJobLevel("Lenhador", ev.getPlayer()) == 1) {
                Lumberjack.salta(ev);
                ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 60, 1));
            }
        } else if (ev.getClickedBlock() != null && ev.getClickedBlock().getType() == Material.DISPENSER) {
            if (ev.getPlayer().getWorld().getName().equalsIgnoreCase("dungeon") && !ev.getPlayer().isOp()) {
                ev.setCancelled(true);
            }
        } else if (ev.getClickedBlock() != null && ev.getClickedBlock().getType() == Material.STONE_BUTTON) {

            Button button = (Button) ev.getClickedBlock().getState().getData();
            Block attached = ev.getClickedBlock().getRelative(button.getAttachedFace());

            // circle stone fazendo firula com toxa pra facilitar fazer dungeon
            // qnd clica no botao na circle stone ele gera uma toxa atraz dps tira.
            if (attached.getType().getId() == 98 && attached.getData() == 3) {

                if (!ev.getPlayer().getWorld().getName().equalsIgnoreCase("dungeon") && !ev.getPlayer().getWorld().getName().equalsIgnoreCase("vila")) {
                    ev.getClickedBlock().setType(Material.AIR);
                    ev.setCancelled(true);
                    ev.getPlayer().sendMessage(ChatColor.RED + L.m("O bug foi arrumado,% espertinho ! Jabu ta de olho !", ev.getPlayer().getName()));
                    return;
                }

                final Block toxa = attached.getRelative(button.getAttachedFace()).getRelative(button.getAttachedFace());
                if (!toxa.hasMetadata("redstone") && toxa.getType() == Material.REDSTONE_BLOCK) {
                    toxa.setType(Material.AIR);
                    ev.setCancelled(true);
                    return;
                }
                if (toxa.hasMetadata("redstone")) {
                    ev.setCancelled(true);
                    return;
                }
                KoM.rewind.put(toxa, Material.AIR);
                toxa.setType(Material.REDSTONE_BLOCK);

                MetaShit.setMetaString("redstone", toxa, "1");
                int task = Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance,
                        new Runnable() {

                            @Override
                            public void run() {
                                toxa.setType(Material.AIR);
                                toxa.removeMetadata("redstone", KoM._instance);
                            }
                        }, 20 * 10);
            }
            return;
        } else if (ev.getClickedBlock() != null && ev.getClickedBlock().getType() == Material.BOAT) {
            ev.setCancelled(true);
            return;
        } else if (ev.getClickedBlock() != null && ev.getClickedBlock().getType() == Material.WOODEN_DOOR) {
            Material m = ev.getPlayer().getLocation().getBlock().getType();
            Material m2 = ev.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType();
            if (m == Material.STATIONARY_WATER || m == Material.WATER || m2 == Material.STATIONARY_WATER || m2 == Material.WATER) {
                ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce nao pode abrir portas estando na agua !"));
                ev.setCancelled(true);
                return;
            }
            if (ev.getPlayer().getWorld().getName().equalsIgnoreCase("dungeon")) {
            }
        } else if (ev.getClickedBlock() != null && ev.getClickedBlock().getType() == Material.IRON_DOOR_BLOCK) {
            if (KoM.debugMode && ev.getPlayer().isOp()) {
                ev.getPlayer().sendMessage(L.m("testando porta"));
            }
            Block testado = ev.getClickedBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN);
            final Openable d = (Openable) ev.getClickedBlock().getState().getData();

            if (!d.isOpen()) {
                if (KoM.debugMode && ev.getPlayer().isOp()) {
                    ev.getPlayer().sendMessage(L.m("tentando chave"));
                }
                boolean podeAbrir = false;
                String nomeChave = null;
                for (int x = 0; x < 3; x++) {
                    if (KoM.debugMode && ev.getPlayer().isOp()) {
                        ev.getPlayer().sendMessage(L.m("verificando %", testado.getType().name()));
                    }
                    if (testado.getType() == Material.CHEST) {
                        if (KoM.debugMode && ev.getPlayer().isOp()) {
                            ev.getPlayer().sendMessage(L.m("achei o bau"));
                        }
                        Chest c = (Chest) testado.getState();
                        for (ItemStack i : c.getBlockInventory().getContents()) {
                            if (i == null) {
                                continue;
                            }
                            // se essa porta tem uma chave
                            if (i.getType() == Material.TRIPWIRE_HOOK) {
                                if (KoM.debugMode && ev.getPlayer().isOp()) {
                                    ev.getPlayer().sendMessage(L.m("achei uma possivel chave"));
                                }
                                
                                ItemStack chave = ev.getItem();
                                if(chave==null)
                                    continue;

                                ItemMeta minha = chave.getItemMeta();
                                ItemMeta porta = i.getItemMeta();
                                // eu to com uma chave na mao ?
                                nomeChave = porta.getDisplayName();
                                if (chave.getType() == Material.TRIPWIRE_HOOK) {
                                    if (KoM.debugMode && ev.getPlayer().isOp()) {
                                        ev.getPlayer().sendMessage(L.m("to cuma chave na mao %", minha.getDisplayName()));
                                        ev.getPlayer().sendMessage(L.m("pra abrir %", porta.getDisplayName()));
                                    }
                                    // se as chaves tem nome custom
                                    if (minha.getDisplayName() != null && porta.getDisplayName() != null) {
                                        // se tem o mesmo nome
                                        if (minha.getDisplayName().trim().equalsIgnoreCase(porta.getDisplayName().trim())) {
                                            // se existe lore

                                            if (porta.getLore() != null && porta.getLore().size() > 0) {
                                                if (minha.getLore() == null || minha.getLore().size() != porta.getLore().size()) {
                                                    podeAbrir = false;
                                                } else // se a primeira linha da lore eh igual 
                                                if (minha.getLore().get(0).trim().equalsIgnoreCase(porta.getLore().get(0).trim())) {
                                                    podeAbrir = true;
                                                }
                                            } else {
                                                // se tem o mermo nome e nao tem lore, abre 
                                                podeAbrir = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (!podeAbrir) {
                            ev.getPlayer().sendMessage(ChatColor.RED + L.m("Esta porta possui uma fechadura"));
                        } else {
                            ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Voce usou a chave para abrir a porta !"));
                            if (!nomeChave.contains("[Quest]")) {
                                if (ev.getItem().getAmount() <= 1) {
                                    ev.getPlayer().setItemInHand(null);
                                } else {
                                    ev.getItem().setAmount(ev.getItem().getAmount() - 1);
                                }
                            }

                            Block block2 = ev.getClickedBlock();
                            if (block2.getData() >= 8) {
                                block2 = block2.getRelative(BlockFace.DOWN);
                            }
                            final Block block = block2;
                            if (block.getType() == Material.IRON_DOOR_BLOCK) {
                                if (block.getData() < 4) {
                                    block.setData((byte) (block.getData() + 4));
                                    block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 0);
                                } else {
                                    block.setData((byte) (block.getData() - 4));
                                    block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 0);
                                }
                            }

                            Runnable run = new Runnable() {
                                public void run() {

                                    if (!block.getChunk().isLoaded()) {
                                        block.getChunk().load();
                                    }
                                    if (block.getType() == Material.IRON_DOOR_BLOCK) {
                                        if (block.getData() < 4) {
                                            block.setData((byte) (block.getData() + 4));
                                            block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 0);
                                        } else {
                                            block.setData((byte) (block.getData() - 4));
                                            block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 0);
                                        }
                                    }
                                }
                            };
                            Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, run, 20 * 10);
                        }
                        break;
                    }
                    testado = testado.getRelative(BlockFace.DOWN);
                }
            }

        } else if (ev.getClickedBlock() != null && ev.getClickedBlock().getType() == Material.IRON_DOOR_BLOCK) {
        } else if (ev.getClickedBlock() != null && ev.getClickedBlock().getType() == Material.TNT) {

            if (SuperBomba.explosivos.containsKey(ev.getClickedBlock())) {
                if (ev.getPlayer().hasPotionEffect(PotionEffectType.SLOW)) {
                    return;
                }
                int jobLevel = Jobs.getJobLevel("Engenheiro", ev.getPlayer());
                if (jobLevel != 0) {
                    if (Jobs.rnd.nextInt(10) == 1) {
                        ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Voce desarmou a bomba !"));
                        Clan c = ClanLand.manager.getClanByPlayerUniqueId(ev.getPlayer().getUniqueId());
                        if (c != null) {
                            for (ClanPlayer cp : c.getOnlineMembers()) {
                                cp.toPlayer().sendMessage(ChatColor.GREEN + ev.getPlayer().getName() + L.m(" desarmou a bomba !!!"));
                            }
                        }
                        int idTask = SuperBomba.explosivos.get(ev.getClickedBlock());
                        Bukkit.getScheduler().cancelTask(idTask);
                        SuperBomba.explosivos.remove(ev.getClickedBlock());
                        ev.getClickedBlock().getLocation().getWorld().dropItemNaturally(ev.getClickedBlock().getLocation(), CustomItem.getItem(SuperBomba.class).generateItem());
                        ev.getClickedBlock().setType(Material.AIR);
                        return;
                    } else {
                        ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce nao conseguiu desarmar a bomba !"));
                        ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 0));
                    }
                }
            }
        } else if (ev.getClickedBlock() != null && ev.getClickedBlock().getType() == Material.CHEST) {

        } else if (ev.getClickedBlock() != null && KoM.debugMode && ev.getPlayer().isOp()) {
            ev.getPlayer().sendMessage("Cliquei sem evento em " + ev.getClickedBlock().getType().name());
        }

        if (ev.getPlayer().isSneaking() && (ev.getAction() == Action.RIGHT_CLICK_AIR || ev.getAction() == Action.RIGHT_CLICK_BLOCK) && ev.getItem() != null && (ev.getItem().getType() == Material.WOOD_AXE || ev.getItem().getType() == Material.IRON_AXE || ev.getItem().getType() == Material.GOLD_AXE || ev.getItem().getType() == Material.STONE_AXE || ev.getItem().getType() == Material.DIAMOND_AXE)) {
            Lumberjack.preparaMachadadaEpica(ev.getPlayer());
        }

        if (KoM.debugMode && ev.getPlayer().isOp()) {
            ev.getPlayer().sendMessage("Acao: " + ev.getAction().name());
        }

        SimpleClanKom.interact(ev);
        if (ev.isCancelled()) {
            return;
        }

        if (ev.getItem() != null && ev.getItem().getType().equals(Material.LAVA_BUCKET)) {
            ev.setCancelled(true);
        }

        if (ev.getPlayer().isOp()) {
            if (ev.getClickedBlock() != null && ev.getClickedBlock().getState() instanceof CreatureSpawner) {
                if (ev.getItem() != null && (ev.getItem().getType() == Material.MONSTER_EGG || ev.getItem().getType() == Material.MONSTER_EGGS)) {
                    SpawnEggMeta meta = (SpawnEggMeta) ev.getItem().getItemMeta();
                    CreatureSpawner spawner = (CreatureSpawner) ev.getClickedBlock().getState();
                    spawner.setSpawnedType(meta.getSpawnedType());

                    ev.getPlayer().sendMessage("Setou pra spawnar " + meta.getSpawnedType().toString());
                    ev.setCancelled(true);
                    return;
                }
            }
        }

        if (ev.getPlayer().getWorld().getName().equalsIgnoreCase("dungeon")) {
            Dungeon.interact(ev);
        }

        if (ev.getAction().equals(Action.PHYSICAL)) {
            if (ev.getClickedBlock().getType() == Material.WOOD_PLATE) {
                BookUtil.acaoLivroEmPressurePlate(ev);
            }
        }

        if (ev.getClickedBlock() != null && ev.getClickedBlock().getType() == Material.BOOKSHELF) {
            if (KoM.debugMode) {
                KoM.log.info(" bookshelvando !");
            }
            BookUtil.getBookOnBookshelf(ev);
        }

        // SIGNS
        if (ev.getClickedBlock() != null) {
            if (ev.getClickedBlock().getState() instanceof Sign) {
                Sign s = (Sign) ev.getClickedBlock().getState();
                if (s.getLine(0).equalsIgnoreCase("[Server]")) {
                    Signs.signClick(ev, s);
                }
            }
        }

        if (ev.getItem() != null && ev.getItem().getType() == Material.BOOK) {
            if (!WorldGuardKom.ehSafeZone(ev.getPlayer().getLocation())) {
                //Wizard.cast(ev);
            } else {
                ev.getPlayer().sendMessage(ChatColor.RED + L.m("Jabu impede conjuracao de magias nesta area !"));
            }
        } else if (ev.getItem() != null && ev.getItem().getType() == Material.MONSTER_EGG) {

        } else if (ev.getItem() != null && ev.getItem().getType() == Material.TNT) {
            Alchemist.tossTnt(ev);
        } else if (ev.getItem() != null && ev.getItem().getType() == Material.GOLDEN_APPLE) {
            if (Jobs.getJobLevel("Paladino", ev.getPlayer()) == 0) {
                ev.getPlayer().sendMessage(ChatColor.GOLD + L.m("Apenas guerreiros sagrados podem comer esta maca"));
                ev.setCancelled(true);
            }
        } else if (ev.getItem() != null && ev.getAction() != Action.PHYSICAL && ev.getItem().getType() == Material.ENDER_PEARL) {
            Thief.throwEnderPearl(ev);
        } else if (ev.getClickedBlock() != null && ev.getClickedBlock().getType() == Material.SPONGE) {

            Block dbaxo = ev.getClickedBlock().getRelative(0, -1, 0);
            // esponja de cadastro free do spawn
            if (dbaxo.getType() == Material.GOLD_BLOCK) {
                boolean cadastrado = KoM.database.hasRegisteredClass(ev.getPlayer().getUniqueId().toString());
                if (KoM.debugMode) {
                    KoM.log.info("CADASTRANDO PLAYER " + ev.getPlayer().getName());
                }
                if (!cadastrado) {
                    //IconMenu.escolheClasse(ev.getPlayer(), false);
                    if (KoM.database.jaTemNome(ev.getPlayer().getName().toLowerCase())) {
                        ev.getPlayer().sendMessage(L.m("Seu nick já está cadastrado... por favor, mude seu nick !"));
                        ev.setCancelled(true);
                        return;
                    }
                    MetaShit.setMetaObject("primeiraEscolha", ev.getPlayer(), "true");
                    netMenu.escolheClasse(ev.getPlayer());
                } else {
                    ev.getPlayer().sendMessage(ChatColor.GOLD + L.m("Você já escolheu suas classes ! Procure pela esponja sagrada de Bob."));

                }
                // templo do reset
            } else if (dbaxo.getType() == Material.DIAMOND_BLOCK) {
                boolean pagou = false;
                boolean cadastrado = KoM.database.hasRegisteredClass(ev.getPlayer().getUniqueId().toString());
                if (KoM.debugMode) {
                    KoM.log.info("CADASTRANDO PLAYER " + ev.getPlayer().getName());
                }
                if (cadastrado) {
                    netMenu.escolheClasse(ev.getPlayer());
                    ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Escolha suas classes com sabedoria !"));
                }
            } else if (dbaxo.getType() == Material.IRON_BLOCK) {
                KoM.database.resetPlayer(ev.getPlayer());
                KoM.database.changeMaxLevel(ev.getPlayer(), ev.getPlayer().getLevel());
                // KnightsOfMinecraft.database.setResets(ev.getPlayer(), 0);
                int resets = KoM.database.getResets(ev.getPlayer());
                //int pontos = Attributes.calcSkillPoints(ev.getPlayer().getLevel(), resets);
                //KnightsOfMania.database.changePoints(ev.getPlayer(), pontos);
                ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Voce esqueceu tudo que sabia !"));
                BungeeCordKom.tp(ev.getPlayer(), CFG.spawnTree);
            } else if (dbaxo.getType() == Material.BEDROCK) {
                if (ev.getPlayer().getLevel() != 100) {
                    ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce precisa estar no nivel 100 !"));
                    return;
                }
                int resets = KoM.database.getResets(ev.getPlayer());
                if (resets == 13) {
                    ev.getPlayer().sendMessage(ChatColor.RED + L.m("O Máximo de Reborns é 13 !"));
                    return;
                }
                XP.setaLevel(ev.getPlayer(), 1);
                KoM.database.resetPlayer(ev.getPlayer());
                KoM.database.changeMaxLevel(ev.getPlayer(), ev.getPlayer().getLevel());

                resets++;
                KoM.database.setResets(ev.getPlayer(), resets);
                // int pontos = Attributes.calcSkillPoints(ev.getPlayer().getLevel(), resets);
                // KnightsOfMania.database.changePoints(ev.getPlayer(), pontos);
                ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Voce esqueceu tudo que sabia e transcendeu !"));
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    if (pl != null && pl != ev.getPlayer()) {
                        pl.sendMessage(ChatColor.GREEN + ev.getPlayer().getName() + L.m(" transcendeu seu conhecimento e renasceu das cinzas !"));
                    }
                }
                ItemStack esponjas = KoM.geraEs(5 * resets);
                ev.getPlayer().getInventory().addItem(esponjas);
                BungeeCordKom.tp(ev.getPlayer(), CFG.spawnTree);
            } else if (dbaxo.getType() == Material.LAPIS_BLOCK) {
                if (ev.getPlayer().getLevel() != 100) {
                    ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce precisa estar no nivel 100 !"));
                    return;
                }
                List<PlayerSpec> specs = PlayerSpec.getSpecs(ev.getPlayer());
                if (specs.size() == 0) {
                    PlayerSpec.abreSpecSelect(ev.getPlayer());
                } else if (specs.size() == 1) {
                    int reborns = KoM.database.getResets(ev.getPlayer());
                    if (reborns < 2) {
                        ev.getPlayer().sendMessage(ChatColor.RED + "Voce precisa de 2 reborns para conseguir pegar sua segunda especializacao !");
                    } else {
                        PlayerSpec.abreSpecSelect(ev.getPlayer());
                    }
                } else {
                    ev.getPlayer().sendMessage(ChatColor.RED + "Voce ja tem suas 2 especializacoes !");
                    ev.getPlayer().sendMessage(ChatColor.RED + "Voce pode reseta-las no templo do esquecimento resetando suas classes");
                    ev.getPlayer().sendMessage(ChatColor.RED + "Ou com um pergaminho de re especializacao");
                }
            }
        }

        KoM._instance.abrePassagem(ev);

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
        Clan aqui = ClanLand.getClanAt(bp);
        ClanPlayer cp = ClanLand.manager.getClanPlayer(ev.getPlayer());
        if (type.equalsIgnoreCase("CLAN") && aqui != null) {
            if (cp == null || !cp.getTag().equalsIgnoreCase(aqui.getTag())) {
                ev.setCancelled(true);
                return;
            }
        }
    }

}
