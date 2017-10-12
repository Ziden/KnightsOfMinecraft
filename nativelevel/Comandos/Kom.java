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
import nativelevel.Auras.Aura;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Classes.Farmer;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.Items.TeleportScroll;
import nativelevel.Custom.Items.ValeSorteio;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Menu.netMenu;
import nativelevel.MetaShit;
import nativelevel.Attributes.AttributeInfo;
import nativelevel.Attributes.Mana;
import nativelevel.Attributes.MenuAtributos;
import nativelevel.Equipment.Atributo;
import nativelevel.Equipment.EquipManager;
import nativelevel.Equipment.EquipMeta;
import nativelevel.Jobs;
import nativelevel.bencoes.TipoBless;
import nativelevel.integration.BungeeCordKom;
import nativelevel.lojaagricola.ConfigLoja;
import nativelevel.lojaagricola.LojaAgricola;
import nativelevel.lojaagricola.Vendavel;
import nativelevel.rankings.Estatistica;
import nativelevel.rankings.RankDB;
import nativelevel.rankings.RankedPlayer;
import nativelevel.skills.SkillMaster;
import nativelevel.spec.PlayerSpec;
import nativelevel.sisteminhas.BookPortal;
import nativelevel.utils.BungLocation;
import nativelevel.sisteminhas.Html;
import nativelevel.sisteminhas.IronGolem;
import nativelevel.sisteminhas.Reseter;
import nativelevel.sisteminhas.XP;
import nativelevel.utils.ChatBlock;
import nativelevel.utils.MetaUtils;
import nativelevel.utils.TableGenerator;
import nativelevel.utils.TableGenerator.Alignment;
import nativelevel.utils.TableGenerator.Receiver;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Kom implements CommandExecutor {

//    HashMap<String, String> auras = new HashMap<String, String>();
    public Kom() {
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        if (cs instanceof Player) {
            Player p = (Player) cs;
            try {

                if (args.length > 1 && args[0].equalsIgnoreCase("criaaura") && p.isOp()) {

                    String nomeFinal = "";
                    for (int x = 1; x < args.length; x++) {
                        nomeFinal += args[x] + " ";
                    }

                    nomeFinal = nomeFinal.trim();

                    Aura ar = Aura.getAura(nomeFinal);
                    ChatColor cor = ChatColor.GREEN;
                    KoM.debug("Procurando aura " + nomeFinal + "!");
                    if (ar == null) {
                        String auras = "";
                        for (Aura a : Aura.auras) {
                            auras += cor + a.getNome() + " ";
                            if (cor == ChatColor.GREEN) {
                                cor = ChatColor.YELLOW;
                            } else {
                                cor = ChatColor.GREEN;
                            }
                        }
                        p.sendMessage("Auras Validas:");
                        p.sendMessage(auras);
                        return true;
                    } else {
                        ItemStack itemAura = Aura.criaItem(nomeFinal);
                        p.getInventory().addItem(itemAura);
                        p.sendMessage(ChatColor.GREEN + "Aura criada :)");

                    }
                }

                if (args.length > 0 && args[0].equalsIgnoreCase("acao")) {
                    String acao = "";
                    for (int x = 1; x < args.length; x++) {
                        acao += args[x] + " ";
                    }
                    for (Entity e : p.getNearbyEntities(8, 8, 4)) {
                        if (e.getType() == EntityType.PLAYER) {
                            ((Player) e).sendMessage(ChatColor.AQUA + "* " + p.getName() + " " + acao + " *");
                        }
                    }
                    p.sendMessage(ChatColor.AQUA + "* " + p.getName() + " " + acao + " *");
                } else if (args.length > 1 && args[0].equals("nomeitem") && p.isOp()) {
                    String nome = "";
                    for (int x = 1; x < args.length; x++) {
                        nome += args[x] + " ";
                    }
                    ItemStack item = p.getItemInHand();
                    if (item != null) {
                        ItemMeta meta = item.getItemMeta();
                        if (meta != null) {
                            meta.setDisplayName(nome);
                            item.setItemMeta(meta);
                            p.sendMessage("Alterado !");
                        }
                    } else {
                        p.sendMessage(L.m("Poe um item na mao rapaiz !"));
                    }
                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("items") && p.isOp()) {
                        int pagina = Integer.valueOf(args[1]);
                        CustomItem.openCustomItemInventory(p, pagina);
                    }
                    if (args[0].equals("mundo") && p.isOp()) {
                        World w = Bukkit.getWorld(args[1]);
                        if (w != null) {
                            p.teleport(w.getSpawnLocation());
                        } else {
                            BungLocation l = new BungLocation(args[1], 0, 0, 0, 0, 0);
                            BungeeCordKom.tp(p, l);
                            //p.sendMessage("este universo ainda nao foi criado em meus reinos binarios !");
                        }
                    } else if (args[0].equals("settype") && p.isOp()) {
                        String type = args[1];
                        ItemStack naMao = p.getItemInHand();
                        naMao.setType(Material.getMaterial(type));
                    } else if (args[0].equals("garrafa") && p.isOp()) {
                        int exp = Integer.valueOf(args[1]);
                        ItemStack garrafa = new ItemStack(Material.EXP_BOTTLE, 1);
                        ItemMeta meta = garrafa.getItemMeta();
                        List<String> lore = new ArrayList<String>();
                        meta.setDisplayName("§a♦ §6Garrafa de Experiencia");
                        lore.add("§7-Uma garrafa contendo §a§n" + exp + "§7 de experiência");
                        lore.add(ChatColor.BLACK + "" + exp);
                        meta.setLore(lore);
                        garrafa.setItemMeta(meta);
                        p.getInventory().addItem(garrafa);
                        p.sendMessage("Vai descendo na boquinha da garrafa");
                    } else if (args[0].equals("nomesorteio") && p.isOp()) {
                        ValeSorteio.nomeSorteio = args[1];
                        p.sendMessage("Nome do sorteio setado para " + args[1]);
                    } else if (args[0].equals("itemsorteio") && p.isOp()) {
                        ItemStack item = ValeSorteio.cria(args[1]);
                        p.getInventory().addItem(item);
                        p.updateInventory();
                        p.sendMessage("Tomae o item fih");
                    } else if (args[0].equals("ajd") && p.isOp()) {
                        String nick = args[1];
                        Player nego = Bukkit.getPlayer(nick);
                        if (nego == null) {
                            p.sendMessage("nao ta online..");
                            return true;
                        }
                        PermissionsEx pex = (PermissionsEx) Bukkit.getPluginManager().getPlugin("PermissionsEx");
                        PermissionUser user = pex.getPermissionsManager().getUser(nego);
                        if (user.getGroupNames().length == 0) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + nick + " group set ajudante");
                        } else {
                            for (String g : user.getGroupNames()) {
                                if (g.equalsIgnoreCase("lord")) {
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + nick + " group set ajudantelord");
                                } else if (g.equalsIgnoreCase("ajudantelord")) {
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + nick + " group set lord");
                                } else if (g.equalsIgnoreCase("ajudante")) {
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + nick + " group set default");
                                } else if (g.equalsIgnoreCase("default") || g.equalsIgnoreCase("player")) {
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + nick + " group set ajudante");
                                }
                            }
                        }
                    } else if (args[0].equals("closeinv") && p.isOp()) {
                        Player nego = Bukkit.getPlayer(args[1]);
                        if (nego == null) {
                            p.sendMessage("Achei o nego naum");
                        } else {
                            if (nego.getOpenInventory() == null) {
                                p.sendMessage("Ele tem nd aberto naum..");
                            } else {
                                nego.closeInventory();
                                nego.sendMessage(ChatColor.RED + "Voce se distrariu...");
                            }
                        }
                    } else if (args[0].equals("checkip") && p.isOp()) {
                        Player preto = Bukkit.getPlayer(args[1]);
                        if (preto == null) {
                            p.sendMessage("Porra... esse cara nem ta online como vou pegar o IP dele ?");
                            return true;
                        } else {
                            preto.getAddress();
                        }
                    } else if (args[0].equals("resetaclan") && p.isOp()) {
                        Player p2 = Bukkit.getPlayer(args[1]);
                        if (p2 == null) {
                            p.sendMessage(ChatColor.GREEN + L.m("Nao encontrei o manolo..."));
                            return true;
                        } else {
                            ClanPlayer cp = ClanLand.manager.getAnyClanPlayer(p2.getUniqueId());
                            if (cp == null) {
                                cp = ClanLand.manager.getClanPlayerName(p2.getName());
                            }
                            if (cp == null) {
                                p.sendMessage(ChatColor.GREEN + L.m("Nao achei o ClanPlayer !"));
                                return true;
                            }

                            ClanLand.manager.deleteClanPlayer(cp);
                            ClanLand.manager.deleteClanPlayerFromMemory(p2.getUniqueId());
                            ClanLand.storage.deleteClanPlayer(cp);
                            Clan c = cp.getClan();
                            c.removeMember(p.getUniqueId().toString());
                            c.removeMember(p.getName());
                            p.sendMessage(ChatColor.GREEN + L.m("Limpei este manolo !"));
                        }
                    } else if (args[0].equals("tiracava") && p.isOp()) {
                        Player p2 = Bukkit.getPlayer(args[1]);
                        if (p2 == null) {
                            p.sendMessage(ChatColor.GREEN + L.m("Nao encontrei o manolo..."));
                            return true;
                        } else {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + p2.getName() + " group remove cavaleiro");
                            p2.sendMessage(ChatColor.RED + L.m("Seu cavaleiro esquecido venceu ! Obrigado por voltar a jogar no nosso mundinho medieval virtual amado :D"));
                            cs.sendMessage(L.m("Tirei o manolo de cavaleiro !"));
                        }
                    } else if (args[0].equalsIgnoreCase("aura")) {

                        /*
                         if (!p.hasPermission("kom.vip")) {
                         p.sendMessage(ChatColor.RED + L.m("Apenas cavaleiros podem colocar efeitos em suas auras !"));
                         return true;
                         }
                         String aura = args[1];

                         if (!auras.containsKey(aura)) {
                         p.sendMessage(ChatColor.RED + L.m("Use /kom aura para ver as auras disponiveis !"));
                         return true;
                         }
                         if (aura.equalsIgnoreCase("off")) {
                         aurasJogadores.remove(p.getUniqueId());
                         } else {
                         aurasJogadores.put(p.getUniqueId(), auras.get(aura));
                         }
                         p.sendMessage(ChatColor.GREEN + L.m("Aura Setada !"));
                         */
                    } else if (args[0].equalsIgnoreCase("deletaconta")) {
                        if (!p.isOp()) {
                            return true;
                        }
                        String conta = args[1];
                        KoM.database.deletaConta(conta);
                        p.sendMessage(ChatColor.GREEN + L.m("Conta deletada !"));
                    } else if (args[0].equals("resetaspec") && p.isOp()) {
                        Player w = Bukkit.getPlayer(args[1]);
                        if (w != null) {
                            KoM.database.atualizaSpecs(w.getUniqueId().toString(), new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0});
                            w.sendMessage(ChatColor.GREEN + L.m("Voce esqueceu sua especializacao !"));
                            p.sendMessage(L.m("resetei"));
                        }
                    } else if (args[0].equals("stats")) {
                        p.sendMessage(ChatColor.RED + "Já se foi este tempo, onde homens se mediam por seus atributos");

                        if (!ClanLand.permission.has(p, "kom.vip")) {
                            p.sendMessage(ChatColor.RED + L.m("Apenas bons cavaleiros sabem fazer isto !"));
                            return true;
                        }
                        Player w = Bukkit.getPlayer(args[1]);
                        if (w == null) {
                            p.sendMessage(ChatColor.RED + L.m("Jogador nao encontrado !"));
                            return true;
                        }
                        mostraStats(w, p);
                    } else if (args[0].equals("nomeitem") && p.isOp()) {
                        String nome = args[1];
                        ItemStack item = p.getItemInHand();
                        if (item != null) {
                            ItemMeta meta = item.getItemMeta();
                            if (meta != null) {
                                meta.setDisplayName(nome);
                                item.setItemMeta(meta);
                                p.sendMessage(L.m("Alterado !"));
                            }
                        } else {
                            p.sendMessage(L.m("Poe um item na mao rapaiz !"));
                        }
                    } else if (args[0].equals("addlore") && p.isOp()) {
                        String nome = args[1];
                        ItemStack item = p.getItemInHand();
                        if (item != null) {
                            ItemMeta meta = item.getItemMeta();
                            if (meta != null) {
                                List<String> lore = meta.getLore();
                                if (lore == null) {
                                    lore = new ArrayList<String>();
                                }
                                lore.add(nome);
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                p.sendMessage(L.m("Alterado !"));
                            }
                        } else {
                            p.sendMessage(L.m("Poe um item na mao rapaiz !"));
                        }
                    } else if (args[0].equals("setlevel") && p.isOp()) {
                        int lvl = -1;
                        try {
                            lvl = Integer.valueOf(args[1]);
                        } catch (Exception e) {
                        }
                        if (lvl != -1) {
                            p.sendMessage(L.m("Seu level agora eh " + lvl));
                            //p.setLevel(lvl);
                            XP.setaLevel(p, lvl);
                        }
                    } else if (args[0].equals("chunks") && p.isOp()) {
                        if (args[1].equalsIgnoreCase("safezone")) {
                            Selection sel = KoM.worldEdit.getSelection(p);
                            if (sel == null) {
                                p.sendMessage(ChatColor.RED + L.m("Seleciona um bagui com //wand primeiro cara..."));
                                return true;
                            } else {
                                World w = p.getWorld();
                                Chunk inicial = sel.getMinimumPoint().getChunk();
                                Chunk fim = sel.getMaximumPoint().getChunk();

                                if (KoM.debugMode) {
                                    p.sendMessage(L.m("inix :" + inicial.getX() + " iniz: " + inicial.getZ()));
                                    p.sendMessage(L.m("fimx :" + fim.getX() + " fim: " + fim.getZ()));
                                }

                                int x = inicial.getX();
                                int y = inicial.getZ();
                                while (x != fim.getX()) {
                                    while (y != fim.getZ()) {
                                        if (KoM.debugMode) {
                                            p.sendMessage(L.m("lendo x :" + x + " y: " + y));
                                        }
                                        Chunk c = p.getWorld().getChunkAt(x, y);
                                        ClanLand.removeClanAt(c.getBlock(1, 1, 1).getLocation());
                                        ClanLand.setClanAt(c.getBlock(1, 1, 1).getLocation(), "SAFE");
                                        y++;
                                    }
                                    y = inicial.getZ();
                                    x++;
                                }
                                p.sendMessage(L.m("Transformou em safezone !"));
                            }
                        } else if (args[1].equalsIgnoreCase("warzone")) {
                            Selection sel = KoM.worldEdit.getSelection(p);
                            if (sel == null) {
                                p.sendMessage(ChatColor.RED + L.m("Seleciona um bagui com //wand primeiro cara..."));
                                return true;
                            } else {
                                World w = p.getWorld();
                                Chunk inicial = sel.getMinimumPoint().getChunk();
                                Chunk fim = sel.getMaximumPoint().getChunk();

                                int x = inicial.getX();
                                int y = inicial.getZ();
                                while (x != fim.getX()) {
                                    while (y != fim.getZ()) {
                                        Chunk c = p.getWorld().getChunkAt(x, y);
                                        ClanLand.setClanAt(c.getBlock(0, 0, 0).getLocation(), "WARZ");
                                        y++;
                                    }
                                    y = inicial.getZ();
                                    x++;
                                }
                                p.sendMessage(L.m("Transformou em warzone !"));
                            }
                        } else if (args[1].equalsIgnoreCase("wild")) {
                            Selection sel = KoM.worldEdit.getSelection(p);
                            if (sel == null) {
                                p.sendMessage(ChatColor.RED + L.m("Seleciona um bagui com //wand primeiro cara..."));
                                return true;
                            } else {
                                World w = p.getWorld();
                                Chunk inicial = sel.getMinimumPoint().getChunk();
                                Chunk fim = sel.getMaximumPoint().getChunk();

                                int x = inicial.getX();
                                int y = inicial.getZ();
                                while (x != fim.getX()) {
                                    while (y != fim.getZ()) {
                                        Chunk c = p.getWorld().getChunkAt(x, y);
                                        ClanLand.setClanAt(c.getBlock(0, 0, 0).getLocation(), "WILD");
                                        y++;
                                    }
                                    y = inicial.getZ();
                                    x++;
                                }
                                p.sendMessage(L.m("Transformou em terras sem dono !"));
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + L.m("Nao encontrei seu comando em meus vastos livros de comandos..."));
                        }
                    }
                } else if (args.length == 6) {
                    KoM.log.info("5 params");
                    if (args[0].equalsIgnoreCase("lojaagricola")) {
                        try {

                            // 
                            String item = args[1];
                            int preco = Integer.valueOf(args[2]);
                            int qtdAlta = Integer.valueOf(args[3]);
                            int qtdNormal = Integer.valueOf(args[4]);
                            int qtdBaixa = Integer.valueOf(args[5]);
                            if (!ConfigLoja.cfg.getConfig().contains(item)) {
                                String valores = "";
                                for (Vendavel v : LojaAgricola.vendaveis) {
                                    valores += v.getNomeTecnico() + " ";
                                }
                                p.sendMessage("Use um dos abaixo");
                                p.sendMessage(valores);
                            } else {
                                ConfigLoja.setPreco(item, preco);
                                ConfigLoja.setQtdAlta(item, qtdAlta);
                                ConfigLoja.setQtdBaixo(item, qtdBaixa);
                                ConfigLoja.setQtdNormal(item, qtdNormal);
                                p.sendMessage("Preços novos setados");
                                p.sendMessage("Baixa: " + qtdBaixa);
                                p.sendMessage("Alta: " + qtdAlta);
                                p.sendMessage("Normal: " + qtdNormal);
                                p.sendMessage("Preço: " + preco);
                            }
                        } catch (Exception e) {
                            p.sendMessage("numero invalido");
                            e.printStackTrace();
                        }
                    }
                } else if (args.length == 4) {
                    if (args[0].equalsIgnoreCase("cavalo") && p.isOp()) {
                        //"/kom cavalo <cor> <tipo> <variant>"
                        String cor = args[1];
                        String tipo = args[2];
                        String variant = args[3];
                        Horse h = (Horse) p.getWorld().spawnEntity(p.getLocation(), EntityType.HORSE);
                        h.setColor(Farmer.pegaCor(cor));
                        h.setStyle(Farmer.pegaTipo(tipo));
                        //h.setVariant(Farmer.pegaVariant(variant));
                        h.setOwner(p);
                        h.setLeashHolder(p);
                        h.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
                    } else {
                        p.sendMessage(ChatColor.RED + L.m("Nao encontrei seu comando em meus vastos livros de comandos..."));
                    }
                } else if (args.length == 3) {
                    if (args[0].equals("livro") && p.isOp()) {
                        if (args[1].equalsIgnoreCase("titulo")) {
                            if (p.getItemInHand().getType() == Material.BOOK_AND_QUILL || p.getItemInHand().getType() == Material.WRITTEN_BOOK) {
                                BookMeta m = (BookMeta) p.getItemInHand().getItemMeta();
                                m.setTitle(args[2]);
                                p.sendMessage(ChatColor.GREEN + L.m("Titulo alterado !"));
                                p.getItemInHand().setItemMeta(m);
                            } else {
                                p.sendMessage(ChatColor.RED + L.m("Bota o livro na mao rapaiz..."));
                            }
                        } else if (args[1].equalsIgnoreCase("autor")) {
                            if (p.getItemInHand().getType() == Material.BOOK_AND_QUILL || p.getItemInHand().getType() == Material.WRITTEN_BOOK) {
                                BookMeta m = (BookMeta) p.getItemInHand().getItemMeta();
                                m.setAuthor(args[2]);
                                p.sendMessage(ChatColor.GREEN + L.m("Autor alterado !"));
                                p.getItemInHand().setItemMeta(m);
                            } else {
                                p.sendMessage(ChatColor.RED + L.m("Bota o livro na mao rapaiz..."));
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("setlevel") && p.isOp()) {

                        String nick = args[1];
                        Player p2 = Bukkit.getPlayer(nick);
                        if (p2 == null) {
                            p.sendMessage(L.m("nao achei esse kra"));
                            return true;
                        }
                        int lvl = Integer.valueOf(args[2]);
                        XP.setaLevel(p2, lvl);
                        KoM.database.resetPlayer(p2);
                        KoM.database.changeMaxLevel(p2, p2.getLevel());
                        int resets = KoM.database.getResets(p);
                        p2.sendMessage(ChatColor.GREEN + L.m("Seu nivel foi alterado !"));
                        p.sendMessage(ChatColor.GREEN + L.m("Nivel alterado !"));
                    } else if (args[0].equalsIgnoreCase("stat") && p.isOp()) {
                        /*
                         String stat = args[1];
                         String qto = args[2];
                         String atrs = "";
                         for (Attr a : Attr.values()) {
                         if (a.name.equalsIgnoreCase(stat)) {
                         KnightsOfMania.database.mudaAtributo(p, a, Integer.valueOf(qto));
                         p.sendMessage(L.m("alterado sua " + a.name + " para " + qto));
                         atrs += a.name + " ";
                         return true;
                         }
                         }
                         p.sendMessage(L.m("Nao achei seu stat, oia os q tem :"));
                         p.sendMessage(atrs);
                         */
                    } else if (args[0].equalsIgnoreCase("tpscroll")) {
                        if (args[1].equalsIgnoreCase("criar")) {
                            int cargas = Integer.valueOf(args[2]);
                            ItemStack scroll = TeleportScroll.createTeleportScroll(p.getLocation(), false, cargas);
                            p.getInventory().addItem(scroll);
                            p.updateInventory();
                        } else {
                            int cargas = Integer.valueOf(args[2]);
                            ItemStack scroll = TeleportScroll.createTeleportScroll(p.getLocation(), true, cargas);
                            p.getInventory().addItem(scroll);
                            p.updateInventory();
                        }

                    }
                } else if (args.length == 0) {
                    p.sendMessage(ChatColor.GREEN + L.m("_oOo____________KoM___________oOo_"));
                    // p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/kom verclasses"));
                    //p.sendMessage(ChatColor.GREEN + "| " + ChatColor.YELLOW + "/kom sethome");
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/guilda, ou /f"));
                    // p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/kom veralmas "));
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/kom stats"));
                    // p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/quests  /quest"));
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/kom suicidio "));
                    // p.sendMessage(ChatColor.GREEN + "| " + ChatColor.YELLOW + "/recompensa ");
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/terreno"));
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/arena"));
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/doar"));
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/kom mostrachance "));
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/kom mostradano "));
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/kom mostraxp "));
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/kom cavaleiro"));
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/kom nivelaqui"));
                    //p.sendMessage(ChatColor.GREEN L.m(+ "| " + ChatColor.YELLOW + "/reinicio");
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/kom acao <descrever acao>"));
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/kom bencao"));
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/kom skills"));
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "" + ChatColor.BOLD + "/shop"));
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "" + ChatColor.BOLD + "/cash"));
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom silencio"));
                    //p.sendMessage(ChatColor.GREEN + "| " + ChatColor.YELLOW + "/ch");
                    // if(Terrenos.permission.has(p, "kom.vip")) {
                    p.sendMessage(ChatColor.GREEN + L.m("| ------- Comandos de VIP -------"));
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom resetgeral"));
                    // p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/fakes"));
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom aura"));
                    // p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom stats <nick>"));
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/foguete"));

                    // }
                    if (p.isOp()) {
                        p.sendMessage(ChatColor.GREEN + L.m("| ------- Comandos de OP -------"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom garrafa <exp>"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom livro"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom tpscroll"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom criaaura"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom resetaclan <nick>"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom setlevel <level>"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom items"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom deletaconta <nick>"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom dupe"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom ajd <nick>"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom cavalo"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom mudaclasses"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom mudaspecs"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom resetaspec <player>"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom nivelaqui"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom checkip <nome>"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom tper"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom sorteio"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom nomesorteio <nome>"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom itemsorteio <nome>"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom debugmode"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom mundo <nome>"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom bencoes"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.GOLD + "/kom lojaagricola <item> <preco>"));

                    }
                    p.sendMessage(ChatColor.GREEN + L.m("oOo__________________________oOo"));
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("tpscroll")) {
                        p.sendMessage(ChatColor.GREEN + L.m("oOo____________KoM___________oOo"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/kom tpscroll criar <cargas>"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/kom tpscroll criarmultiworld <cargas>"));
                        p.sendMessage(ChatColor.GREEN + L.m("oOo__________________________oOo"));
                    } else if (args[0].equalsIgnoreCase("livro") && p.isOp()) {
                        p.sendMessage(ChatColor.GREEN + L.m("oOo____________KoM___________oOo"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/kom livro titulo <titulo>"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/kom livro autor <autor>"));
                        p.sendMessage(ChatColor.GREEN + L.m("oOo__________________________oOo"));
                    } else if (args[0].equalsIgnoreCase("cavalo") && p.isOp() && p.isOp()) {
                        p.sendMessage(ChatColor.GREEN + L.m("oOo____________KoM___________oOo"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/kom cavalo <cor> <tipo> <variant>"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "Cores: CREAMY, BLACK,WHITE, CHESTNUT, DARK_BROWN, BROWN, GRAY"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "Tipos: BLACK_DOTS, WHITEFIELD,WHITE,WHITE_DOTS"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "Variants: HORSE, DONKEY, MULE"));
                        p.sendMessage(ChatColor.GREEN + L.m("oOo__________________________oOo"));
                    } else if (args[0].equalsIgnoreCase("chunks") && p.isOp()) {
                        p.sendMessage(ChatColor.GREEN + L.m("oOo____________KoM___________oOo"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "!! TA MEI BUGADIN mas da pra usar.. !! "));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/kom chunks safezone "));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/kom chunks warzone"));
                        p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/kom chunks wild"));
                        p.sendMessage(ChatColor.GREEN + L.m("oOo__________________________oOo"));
                    } else if (args[0].equalsIgnoreCase("items") && p.isOp()) {
                        p.sendMessage("Comando migrou pra /kom items <1 ou 2>");
                    } else if (args[0].equalsIgnoreCase("wipe") && p.isOp()) {
                        Reseter.limpaGuildas();
                    } else if (args[0].equalsIgnoreCase("mostradano")) {
                        if (!p.hasMetadata("mostradano")) {
                            MetaShit.setMetaObject("mostradano", p, true);
                            p.sendMessage(ChatColor.GREEN + L.m("Mostrando os danos..."));
                        } else {
                            p.removeMetadata("mostradano", KoM._instance);
                            p.sendMessage(ChatColor.GREEN + L.m("Nao mostrando os danos..."));
                        }
                    } else if (args[0].equalsIgnoreCase("mostrachance")) {

                        if (p.hasMetadata("mostra%")) {
                            p.sendMessage("§a§uEscondendo§2 chances de sucesso");
                            p.removeMetadata("mostra%", KoM._instance);
                        } else {
                            p.sendMessage("§a§uMostrando§2 chances de sucesso");
                            MetaShit.setMetaString("mostra%", p, "");
                        }

                    } else if (args[0].equalsIgnoreCase("mostraxp")) {
                        if (!p.hasMetadata("mostraXp")) {
                            MetaShit.setMetaObject("mostraXp", p, true);
                            p.sendMessage(ChatColor.GREEN + L.m("Mostrando xp..."));
                        } else {
                            p.removeMetadata("mostraXp", KoM._instance);
                            p.sendMessage(ChatColor.GREEN + L.m("Nao mostrando xp..."));
                        }
                    } else if (args[0].equalsIgnoreCase("regenmundao") && p.isOp()) {
                        Reseter.regenMundo();
                    } else if (args[0].equals("bencao")) {
                        int tempo = TipoBless.save.tem(p);
                        TipoBless tipo = TipoBless.save.getTipo(p);
                        if (tipo == null) {
                            p.sendMessage(ChatColor.RED + "Nenhuma benção ativa. Encontre benções em locais secretos ou compre no /shop");
                        } else {
                            p.sendMessage(ChatColor.GREEN + "Benção ativa: " + tipo.name());
                            p.sendMessage(ChatColor.GREEN + "Segundos Restantes: " + tempo);
                        }
                    } else if (args[0].equals("tirabencao")) {
                        TipoBless.save.remove(p);
                    } else if (args[0].equals("bencaos") && p.isOp()) {
                        TipoBless.mostraTodos(p);
                    } else if (args[0].equalsIgnoreCase("wipegeralzao") && p.isOp()) {
                        Reseter.wipeGeral();
                    } else if (args[0].equalsIgnoreCase("aura")) {
                        Aura.abreAuras(p);
                    } else if (args[0].equalsIgnoreCase("regen") && ClanLand.permission.has(p, "kom.mod")) {
                        if (p.getWorld().getName().equalsIgnoreCase("dungeon") || p.getWorld().getName().equalsIgnoreCase("vila") || p.getWorld().getName().equalsIgnoreCase("woe") || p.getWorld().getName().equalsIgnoreCase("Arena")) {
                            return true;
                        }
                        String tipo = ClanLand.getTypeAt(p.getLocation());
                        if (tipo.equalsIgnoreCase("WILD")) {
                            Chunk c = p.getLocation().getChunk();
                            c.getWorld().regenerateChunk(c.getX(), c.getZ());
                            p.sendMessage(L.m("Regenerado"));
                        }
                    } else if (args[0].equalsIgnoreCase("itemnamao") && p.isOp()) {
                        ItemStack ss = p.getItemInHand();
                        p.sendMessage("DATA:" + ss.getData().getData());
                        p.sendMessage("typeid:" + ss.getTypeId());
                        p.sendMessage("DUR:" + ss.getDurability());
                        p.sendMessage("StackSize:" + ss.getMaxStackSize());
                        p.sendMessage("DataString:" + ss.getData().toString());
                        p.sendMessage("MetaString:" + ss.getItemMeta().toString());
                        p.sendMessage("ItemString:" + ss.toString());
                        p.sendMessage("Qtd:" + ss.getAmount());
                    } else if (args[0].equalsIgnoreCase("mudaclasses") && p.isOp()) {
                        netMenu.escolheClasse(p);
                    } else if (args[0].equalsIgnoreCase("mudaspecs") && p.isOp()) {
                        PlayerSpec.abreSpecSelect(p);
                    } else if (args[0].equalsIgnoreCase("wipepirata") && p.isOp()) {
                        KoM.database.wipePirata();
                    } else if (args[0].equalsIgnoreCase("gerahtmlzao") && p.isOp()) {
                        Html.gera();
                    } else if (args[0].equalsIgnoreCase("resetstats")) {
                        /*
                         if (!ClanLand.permission.has(p, "kom.vip")) {
                         p.sendMessage(L.m("Apenas cavaleiros sabem resetar seus stats assim !"));
                         return true;
                         }
                         if (!ClanLand.econ.has(p.getName(), 200)) {
                         p.sendMessage(ChatColor.RED + L.m("Voce precisa de 200 esmeraldas para resetar seus stats !"));
                         return true;
                         }
                         ClanLand.econ.withdrawPlayer(p.getName(), 200);
                         KnightsOfMania.database.resetPlayer(p);
                         KnightsOfMania.database.changeMaxLevel(p, p.getLevel());
                         int resets = KnightsOfMania.database.getResets(p);
                         //int pontos = Attributes.calcSkillPoints(p.getLevel(), resets);
                         // KnightsOfMania.database.changePoints(p, pontos);
                         p.sendMessage(ChatColor.GREEN + L.m("Voce esqueceu tudo que sabia !"));
                         */
                        p.sendMessage(ChatColor.RED + "Ja se foram os tempos em que os homens se mediam por seus atributos");
                    } else if (args[0].equalsIgnoreCase("resetgeral")) {
                        if (!ClanLand.permission.has(p, "kom.vip")) {
                            p.sendMessage(L.m("Apenas cavaeiros sabem resetar assim !"));
                            return true;
                        }
                        if (!ClanLand.econ.has(p.getName(), 200)) {
                            p.sendMessage(ChatColor.RED + L.m("Voce precisa de 200 Esmeraldas para resetar seus stats !"));
                            return true;
                        }
                        ClanLand.econ.withdrawPlayer(p.getName(), 200);
                        netMenu.escolheClasse(p);
                    } else if (args[0].equalsIgnoreCase("vida")) {
                        p.sendMessage("Vida: " + p.getMaxHealth());
                    } else if (args[0].equalsIgnoreCase("setavida40") && p.isOp()) {
                        p.setMaxHealth(40D);
                        MetaShit.setMetaObject("itemattributes.basehealth", p, 40, KoM.itemAttributes);
                    } else if (args[0].equalsIgnoreCase("setavida10") && p.isOp()) {
                        p.setMaxHealth(10D);
                        MetaShit.setMetaObject("itemattributes.basehealth", p, 10, KoM.itemAttributes);
                    } else if (args[0].equalsIgnoreCase("comoupar")) {
                        p.setMaxHealth(10D);
                        MetaShit.setMetaObject("itemattributes.basehealth", p, 10, KoM.itemAttributes);
                    } else if (args[0].equalsIgnoreCase("stats")) {
                        mostraStats(p, p);
                        //b.sendBlock(p);
                        //p.sendMessage(ChatColor.GREEN + "---------- " + p.getName() + " ----------");
                        //p.sendMessage(ChatColor.GREEN + "Karma: " + ChatColor.YELLOW + KoM.database.getKarma(p.getUniqueId())+ "       "+ChatColor.GREEN + "Fama: " + ChatColor.YELLOW + KoM.database.getFama(p.getUniqueId()));
                        //p.sendMessage(ChatColor.GREEN + "XP: " +ChatColor.YELLOW+ p.getTotalExperience()+"        "+ChatColor.GREEN+"Próximo Lvl: "+XP.getExpProximoNivel(p.getLevel()));
                    } else if (args[0].equalsIgnoreCase("skills")) {
                        SkillMaster.abreSkills(p);
                    } else if (args[0].equalsIgnoreCase("testegolem")) {
                        IronGolem.spawn(p);
                    } else if (args[0].equalsIgnoreCase("cavaleiro")) {
                        if (ClanLand.permission.has(p, "kom.vip")) {
                            p.sendMessage(ChatColor.GREEN + L.m("Hummm aventureiro, voce já é um nobre cavaleiro ! Obrigado por contribuir com o servidor !"));
                            if (!p.hasMetadata("kitcava")) {
                                p.sendMessage(ChatColor.GREEN + L.m("Aqui , uma ajuda... tome aqui algumas Esmeraldas"));
                                p.getInventory().addItem(new ItemStack(Material.EMERALD, 10));
                                MetaShit.setMetaObject("kitcava", p, 0);
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + L.m("Hummm aventureiro, vejo que ainda não é um nobre cavaleiro !"));
                            p.sendMessage(ChatColor.RED + L.m("Por que não te tornas um ? Irá ajudar o servidor !"));
                        }

                    } else if (args[0].equalsIgnoreCase("dupe") && p.isOp()) {
                        ItemStack item = p.getItemInHand();
                        if (item != null) {
                            p.getInventory().addItem(item);
                        }
                        p.sendMessage("Dupado");
                    } else if (args[0].equalsIgnoreCase("limpanpcs") && p.isOp()) {
                        NPCRegistry registry = CitizensAPI.getNPCRegistry();
                        Iterator<NPC> npcs = registry.iterator();
                        while (npcs.hasNext()) {
                            NPC npc = npcs.next();
                            Location loc = npc.getStoredLocation();
                            if (loc.getWorld().getName().equalsIgnoreCase("dungeon")) {
                                continue;
                            }
                            KoM.log.info("DESTRUIDO: " + npc.getFullName());
                            npc.destroy();
                        }

                        p.sendMessage("Dupado");
                    } else if (args[0].equalsIgnoreCase("limpanpcs") && p.isOp()) {
                        NPCRegistry registry = CitizensAPI.getNPCRegistry();
                        Iterator<NPC> npcs = registry.iterator();
                        while (npcs.hasNext()) {
                            NPC npc = npcs.next();
                            Location loc = npc.getStoredLocation();
                            if (loc.getWorld().getName().equalsIgnoreCase("dungeon")) {
                                continue;
                            }
                            KoM.log.info("DESTRUIDO: " + npc.getFullName());
                            npc.destroy();
                        }

                        p.sendMessage("Dupado");
                    } else if (args[0].equalsIgnoreCase("esponjas") && p.isOp()) {
                        ItemStack esponjas = KoM.geraEs(64);
                        p.getInventory().addItem(esponjas);
                        p.sendMessage(L.m("foi"));
                    } else if (args[0].equalsIgnoreCase("removemeta") && p.isOp()) {
                        p.removeMetadata("atributo", KoM._instance);
                    } else if (args[0].equalsIgnoreCase("mana")) {
                        Mana m = Mana.getMana(p);
                        p.sendMessage("Mana : " + m.mana + "/" + m.maxMana);
                    } else if (args[0].equalsIgnoreCase("reseta")) {
                        Mana m = Mana.getMana(p);
                        p.sendMessage("Mana : " + m.mana + "/" + m.maxMana);
                    } else if (args[0].equalsIgnoreCase("suicidio")) {
                        p.sendMessage(ChatColor.GREEN + L.m("Voce bateu sua cabeça com tudo no chao !"));
                        p.setHealth(1D);
                        p.damage(30);
                    } else if (args[0].equalsIgnoreCase("veralmas")) {
                        int almas = KoM.database.getAlmas(p.getUniqueId().toString());
                        p.sendMessage(ChatColor.GREEN + L.m("Voce tem % almas !", almas));
                    } else if (args[0].equalsIgnoreCase("tirasafezones")) {
                        World w = p.getWorld();
                        int ct = 0;
                        for (Chunk c : w.getLoadedChunks()) {
                            String type = ClanLand.getTypeAt(c.getBlock(1, 1, 1).getLocation());
                            if (type.equalsIgnoreCase("warz")) {
                                ClanLand.setClanAt(c.getBlock(1, 1, 1).getLocation(), "WILD");
                                ct++;
                            }
                        }
                        p.sendMessage(L.m("transformei " + ct + " de war pra wild"));
                    } else if (args[0].equalsIgnoreCase("sincroniza") && p.isOp()) {

                    } else if (args[0].equalsIgnoreCase("tper") && p.isOp()) {

                        if (p.getItemInHand().getType() == Material.BOOK_AND_QUILL) {
                            BungLocation local = BookPortal.getLocationFromBook(p.getItemInHand());
                            if (local == null) {
                                p.sendMessage(ChatColor.RED + L.m("ou ce fica cum livro de portal ou cas mao vazia mero mortal !"));
                            } else {

                                ApplicableRegionSet set = KoM.worldGuard.getRegionManager(p.getWorld()).getApplicableRegions(p.getLocation());
                                if (set.size() > 0) {

                                    Iterator<ProtectedRegion> i = set.iterator();
                                    while (i.hasNext()) {
                                        ProtectedRegion regiao = i.next();
                                        if (regiao.getId().contains("barco-") || regiao.getId().contains("balao-")) {

                                            com.sk89q.worldedit.Location localWE = BukkitUtil.toLocation(local.toLocation());
                                            regiao.setFlag(DefaultFlag.SPAWN_LOC, localWE);

                                            p.sendMessage("Local destino do transporte setado !");
                                            return true;
                                        }
                                        break;
                                    }
                                }

                                Block ondeTaONego = p.getLocation().getBlock();
                                ondeTaONego.setType(Material.WOOD_PLATE);
                                Block bau = ondeTaONego.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN);
                                bau.setType(Material.CHEST);
                                Chest c = (Chest) bau.getState();
                                c.getBlockInventory().addItem(p.getItemInHand());
                                p.sendMessage(ChatColor.GREEN + L.m("Teleporter criado !"));
                            }
                        } else if (p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR) {
                            p.sendMessage(ChatColor.RED + L.m("ou ce fica cum livro de portal ou cas mao vazia mero mortal !"));
                        } else {
                            ItemStack livro = BookPortal.criaLivroPortal(p);
                            p.setItemInHand(livro);
                            p.sendMessage(ChatColor.GREEN + L.m("O poderoso livro do portal foi conjurado ! Use o poder do comando novamente para criar o teleporter !"));
                        }

                    } else if (args[0].equalsIgnoreCase("silencio")) {

                        if (p.hasMetadata("silencio")) {
                            p.removeMetadata("silencio", KoM._instance);
                            p.sendMessage(ChatColor.GREEN + "Voce está lendo mensagens do chat Global novamente.");
                        } else {
                            MetaShit.setMetaObject("silencio", p, true);
                            p.sendMessage(ChatColor.GREEN + "Você não está mais lendo mensagens do chat Global.");

                        }

                    } else if (args[0].equalsIgnoreCase("sorteio") && p.isOp()) {
                        if (ValeSorteio.players.size() == 0) {
                            p.sendMessage("Primeiro de o documento de sorteio do /kom items pra galera");
                            return true;
                        }
                        ValeSorteio.fazSorteio();
                    } else if (args[0].equalsIgnoreCase("verclasses")) {
                        p.sendMessage(ChatColor.GREEN + L.m("Vendo suas classes !"));
                        netMenu.mostraClasses(p);
                    } else if (args[0].equalsIgnoreCase("sethome")) {
                        ClanPlayer cp = ClanLand.getPlayer(p.getName());
                        if (cp == null) {
                            p.sendMessage(ChatColor.RED + L.m("Voce precisa de uma guilda !"));
                            return true;
                        }
                        if (!cp.isLeader()) {
                            p.sendMessage(ChatColor.RED + L.m("Voce precisa ser líder da guilda !"));
                            return true;
                        }
                        Clan c = cp.getClan();
                        Clan aqui = ClanLand.getClanAt(p.getLocation());
                        if (aqui == null || c == null || !c.getTag().equalsIgnoreCase(aqui.getTag())) {
                            p.sendMessage(L.m("Voce so pode setar a home em terreno da sua guilda !"));
                            return true;
                        }
                        c.setHomeLocation(p.getLocation());
                        p.sendMessage(ChatColor.GREEN + "Home setada !");
                    } else if (args[0].equalsIgnoreCase("debugmode") && p.isOp()) {
                        KoM.debugMode = !KoM.debugMode;
                        if (KoM.debugMode) {
                            p.sendMessage(ChatColor.GREEN + L.m("Modo Debug Ligado !"));
                        } else {
                            p.sendMessage(ChatColor.GREEN + L.m("Modo Debug Desligado !"));
                        }

                    } else if (args[0].equalsIgnoreCase("paramusica")) {
                        // Music.paraMusica(p);
                        // p.sendMessage(ChatColor.GREEN + L.m("Musica parada !!"));

                    } else if (args[0].equalsIgnoreCase("nivelaqui")) {
                        int lvl = ClanLand.getMobLevel(p.getLocation());
                        int lvlMobs = lvl * 5;
                        p.sendMessage(L.m(ChatColor.GREEN + "Aqui os mobs são nivel " + ChatColor.YELLOW + lvlMobs));
                    } else if (args[0].equalsIgnoreCase("bigorna") && p.isOp()) {
                        AnvilInventory i = (AnvilInventory) Bukkit.createInventory(p, InventoryType.ANVIL);
                        p.openInventory(i);
                    } else {
                        p.sendMessage(ChatColor.RED + L.m("Nao encontrei seu comando em meus vastos livros de comandos..."));
                    }
                } else {
                    p.sendMessage(ChatColor.RED + L.m("Nao encontrei seu comando em meus vastos livros de comandos..."));
                }
            } catch (Throwable t) {
                KoM.log.info(t.getMessage());
                t.printStackTrace();
            }
        }

        return true;
    }

    public void mostraStats(Player p, Player vendo) {
        vendo.sendMessage(" ");
        vendo.sendMessage("§2§l_____________ KoM _____________");
        vendo.sendMessage("§2§l|");

        TableGenerator tabela = new TableGenerator(Alignment.RIGHT, Alignment.LEFT, Alignment.RIGHT, Alignment.LEFT);

        double xpProx = XP.getExpProximoNivel(p.getLevel());

        double xp = p.getTotalExperience();

        double pct = xp * 100d / xpProx;

        int almas = KoM.database.getAlmas(p.getUniqueId().toString());

        // b.setAlignment("l", "l", "l", "l");
        List<String> primarias = Jobs.getPrimarias(p);

        List<String> secundarias = Jobs.getSecundarias(p);
        tabela.addRow("§a§lPrimárias", "", "§2§lSecundárias", "");
        tabela.addRow("§a" + primarias.get(0), "§a" + primarias.get(1), "§2" + secundarias.get(0), "§2" + secundarias.get(1));
        tabela.addRow("", "", "", "");
        tabela.addRow("§6§lKarma", "§a" + KoM.database.getKarma(p.getUniqueId()), "§6§lFama", "§a" + KoM.database.getFama(p.getUniqueId()));
        tabela.addRow("", "", "", "");
        tabela.addRow("§6§lAlmas", "§a" + almas, "", "");
        tabela.addRow("", "", "", "");
        tabela.addRow("§6§lXP", "§a" + (int) xp + " §2(" + (int) pct + "%)", "§6§lXP Prox", "§a" + (int) xpProx);
        tabela.addRow("", "", "", "");

        EquipMeta meta = EquipManager.getPlayerEquipmentMeta(p);

        String[] linhas = new String[4];

        int n = 0;

        if (meta.getAtributos().size() > 0) {
            tabela.addRow("§6§lAtributos", "", "", "");
            tabela.addRow("", "", "", "");

            for (Atributo a : meta.getAtributos()) {

                int valor = (int) meta.getAttribute(a);

                linhas[n] = "§a§l" + valor;
                if (a.pct) {
                    linhas[n] += "%";
                }
                linhas[n + 1] = "§a" + a.getName();

                n += 2;

                if (n == 4) {
                    n = 0;
                    tabela.addRow(linhas);
                    linhas = new String[4];
                }
            }
            tabela.addRow("", "", "", "");
        }

        tabela.addRow("§6§lRankings", "", "", "");
        tabela.addRow("", "", "", "");
        linhas = new String[4];
        n = 0;
        HashMap<Estatistica, RankedPlayer> rankings = RankDB.getRankings(p);

        for (Estatistica a : rankings.keySet()) {

            RankedPlayer rp = rankings.get(a);

            linhas[n] = "§a" + a.titulo;
            linhas[n + 1] = "§eTop " + (rp.posicao == 0 ? "Noob" : rp.posicao);

            n += 2;
            if (n == 4) {
                n = 0;
                tabela.addRow(linhas);
                linhas = new String[4];
            }

        }

        for (String s : tabela.generate(Receiver.CLIENT, true, true)) {
            vendo.sendMessage("§2§l|" + s);
        }
        vendo.sendMessage("§2§l_____________________________________");
    }
}
