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
package nativelevel.Menu;

import nativelevel.utils.MetaUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import nativelevel.Equipment.WeaponDamage;
import nativelevel.CFG;
import nativelevel.Classes.Blacksmithy.RecipeLoader;
import nativelevel.Classes.Blacksmithy.recipes.Armas.Pedra.PaDePedra;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.Items.Ank;
import nativelevel.Custom.Items.Armadilha;
import nativelevel.Custom.Items.BombaFumaca;
import nativelevel.Custom.Items.Detonador;
import nativelevel.Custom.Items.FolhaDeMana;
import nativelevel.Custom.Items.Pistola;
import nativelevel.Custom.Items.RecipeBook;
import nativelevel.Custom.Items.SlimePoison;
import nativelevel.Custom.Items.VenenoNatural;
import nativelevel.Custom.PotionLoader;
import nativelevel.Custom.Potions.Cure1;
import nativelevel.Custom.Potions.Explosion1;
import nativelevel.Custom.Potions.Mana1;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Classes.Mage.MageSpell;
import nativelevel.Classes.Mage.SpellLoader;
import nativelevel.Classes.Mage.spelllist.Firebola;
import nativelevel.RecipeBooks.BookTypes;
import nativelevel.integration.BungeeCordKom;
import nativelevel.rankings.RankDB;
import nativelevel.spec.PlayerSpec;
import nativelevel.sisteminhas.XP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

/**
 *
 * @author NeT32
 *
 */
public class netMenu implements Listener {

    public static final String NomeMenu = "Classes !";
    public static String NomeMenuDisabled = null;
    public static HashMap<String, Inventory> ListaMenus = new HashMap();
    public static HashMap<String, Inventory> ListaMenusDisabled = new HashMap();
    //LANG PT-BR
    public static String Lang_Primaria = null;
    public static String Lang_Secundaria = null;
    public static String Lang_MuitoBoa = null;
    public static String Lang_Mediana = null;
    public static String Lang_Confirmar = null;
    public static String Lang_TemCerteza = null;
    public static String Lang_Selecione = null;
    public static String Lang_WarnSelecione = null;
    public static String Lang_WarnDemonstrativo = null;
    public static String Lang_SejaEsperto = null;
    public static String Lang_Escolheu = null;

    public netMenu() {
        NomeMenuDisabled = L.m("Suas Classes!");
        Lang_Primaria = ChatColor.GREEN + "" + ChatColor.BOLD + L.m("Primária");
        Lang_Secundaria = ChatColor.YELLOW + "" + ChatColor.BOLD + L.m("Secundária");
        Lang_MuitoBoa = ChatColor.AQUA + L.m("Muito Boa!");
        Lang_Mediana = ChatColor.DARK_AQUA + L.m("Mediana!");
        Lang_Confirmar = ChatColor.GREEN + L.m("Confirmar");
        Lang_TemCerteza = ChatColor.GOLD + L.m("Tem Certeza ?");
        Lang_Selecione = ChatColor.RED + L.m("Selecione 4 Classes, Sendo 2 Primárias e 2 Secundárias!");
        Lang_WarnSelecione = ChatColor.RED + L.m("Você deve escolher 4 classes sendo 2 Primárias e 2 Secundárias!");
        Lang_WarnDemonstrativo = ChatColor.GREEN + L.m("O Diamante é apenas demonstrativo continue a escolher");
        Lang_SejaEsperto = ChatColor.GOLD + L.m("Seja Esperto!");
        Lang_Escolheu = ChatColor.GREEN + L.m("Você escolheu suas Classes !");
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();
        String title = event.getView().getTitle();
        int slot = event.getRawSlot();
        try {
            if (slot < 0 || slot >= inv.getSize() || inv.getItem(slot) == null) {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (KoM.debugMode) {
            KoM.log.info(title);
        }
        if (title.equals(NomeMenu)) {
            try {
                event.setCancelled(true);
                if (slot <= 44 && inv.getItem(slot) != null) {
                    event.setCancelled(true);
                    event.setCursor(null);
                    event.setResult(Result.DENY);
                    boolean cadastrado = KoM.database.hasRegisteredClass(player.getUniqueId().toString());
                    if (inv.getItem(slot).getType().equals(Material.DIAMOND)) {
                        player.sendMessage(Lang_WarnDemonstrativo);
                        event.setCancelled(true);
                        return;
                    }
                    if (ChatColor.stripColor(inv.getItem(slot).getItemMeta().getDisplayName()).equals(ChatColor.stripColor(Lang_Confirmar))) {
                        int primarias = 0;
                        int secundarias = 0;
                        for (int x = 0; x < 9; x++) {
                            int qtoTem = inv.getItem(x + 9) == null ? 0 : inv.getItem(x + 9).getAmount();
                            if (qtoTem == 2) {
                                primarias++;
                            }
                            if (qtoTem == 1) {
                                secundarias++;
                            }
                        }
                        if (primarias != 2 || secundarias != 2) {
                            return;
                        }
                        player.closeInventory();
                        player.sendMessage(Lang_Escolheu);
                        if (!player.hasMetadata("resetFree")) {
                            KoM.database.resetPlayer(player);
                            XP.setaLevel(player, 1);
                        } else {
                            player.removeMetadata("resetFree", KoM._instance);
                        }
                        if (!player.hasMetadata("rebornGratiz")) {
                            KoM.database.setResets(player, 0);
                        } else {
                            player.removeMetadata("rebornGratiz", KoM._instance);
                        }
                        KoM.database.atualizaSpecs(player.getUniqueId().toString(), new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0});

                        if (!cadastrado) {
                            KoM.database.cadastra(player.getUniqueId().toString(), player.getName());
                            player.getInventory().clear();
                            player.getEquipment().clear();
                            player.getEquipment().setArmorContents(null);
                            player.getInventory().setBoots(null);
                            player.getInventory().setArmorContents(null);
                            player.getInventory().setChestplate(null);
                            player.getInventory().setHelmet(null);
                            player.getInventory().setContents(CFG.starterItems);

                            player.getInventory().setItemInOffHand(WeaponDamage.checkForMods(new ItemStack(Material.SHIELD, 1)));
                            // Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + player.getName() + " map 1 1000");
                            player.setBedSpawnLocation(null);
                            BungeeCordKom.tp(player, CFG.localInicio);

                            if (!KoM.ehOriginal(player)) {
                                KoM.log.info("Transformando skin");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sync console bungee sr set " + player.getName() + " ZidenVentuinha");
                            }

                            XP.setaLevel(player, 1);
                            player.removeMetadata("tutorial", KoM._instance);
                            player.sendMessage(ChatColor.GREEN + L.m("O Portal te levou para longe. Para um novo tempo, um tempo de recomeço, um tempo, definitivo."));
                            player.sendMessage(ChatColor.GREEN + L.m("Bem vindo ao KoM !!!"));
                            player.sendMessage(ChatColor.GREEN + L.m("O comando /kom resume os comandos !"));
                            player.sendMessage(ChatColor.GREEN + L.m("Lembre-se, você precisa de aliados ! E não desista !"));
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                if (p.getUniqueId() != player.getUniqueId()) {
                                    p.sendMessage(ChatColor.GOLD + "[Jabu] " + ChatColor.GREEN + L.m("Um novo aventureiro, % acaba de chegar a Aden !", player.getName()));
                                    p.sendMessage(ChatColor.GOLD + "[Jabu] " + ChatColor.GREEN + L.m("Seja bom, ajude o novo aventureiro ! Recrute-o e ensine-o !"));
                                }
                            }
                        } else {
                            RankDB.deletePlayer(player);
                        }
                        int[] skills = new int[10];
                        for (int x = 0; x < 9; x++) {
                            int qtoTem = inv.getItem(x + 9) == null ? 0 : inv.getItem(x + 9).getAmount();
                            skills[x] = qtoTem;
                        }
                        KoM.database.atualiza(player.getUniqueId().toString(), skills);
                        if (player.hasMetadata("primeiraEscolha")) {
                            
                            player.getInventory().setItemInHand(new ItemStack(Material.COMPASS, 1));
                            player.removeMetadata("primeiraEscolha", KoM._instance);
                            if (Jobs.getJobLevel("Ferreiro", player) == 1) {
                                player.getInventory().addItem(WeaponDamage.checkForMods(new ItemStack(Material.IRON_SPADE, 3)));
                                player.getInventory().addItem(RecipeBook.createBook(BookTypes.Ferraria));
                                player.getInventory().addItem(RecipeLoader.customItemsClass.get(PaDePedra.class).generateRecipe().getItem());
                            }
                            
                            if (Jobs.getJobLevel("Fazendeiro", player) == 1) {
                                player.getInventory().addItem(new ItemStack(Material.FISHING_ROD, 1));
                                player.getInventory().addItem(new ItemStack(Material.SEEDS, 10));
                                player.getInventory().addItem(WeaponDamage.checkForMods(new ItemStack(Material.IRON_HOE, 1)));
                            }
                            
                            if (Jobs.getJobLevel("Alquimista", player) == 1) {
                                player.getInventory().addItem(new ItemStack(Material.GLASS_BOTTLE, 20));
                                player.getInventory().addItem(new ItemStack(Material.CAULDRON_ITEM, 1));
                                player.getInventory().addItem(new ItemStack(Material.NETHERRACK, 1));
                                player.getInventory().addItem(new ItemStack(Material.NETHER_WARTS, 1));
                                player.getInventory().addItem(new ItemStack(Material.SPIDER_EYE, 3));
                                player.getInventory().addItem(RecipeBook.createBook(BookTypes.Alquimia));
                                player.getInventory().addItem(PotionLoader.customItemsClass.get(Cure1.class).generateRecipe().getItem());
                                player.getInventory().addItem(PotionLoader.customItemsClass.get(Mana1.class).generateRecipe().getItem());
                            }
                            
                            if (Jobs.getJobLevel("Lenhador", player) == 1) {
                                player.getInventory().addItem(new ItemStack(Material.WOOD, 20));
                                player.getInventory().addItem(WeaponDamage.checkForMods(new ItemStack(Material.IRON_AXE, 1)));
                            }
                            
                            if (Jobs.getJobLevel("Paladino", player) == 1) {
                                //player.getInventory().addItem(CustomItem.getItem(Ank.class).generateItem(1));
                                player.getInventory().addItem(WeaponDamage.checkForMods(new ItemStack(Material.IRON_SWORD, 1)));
                            }
                            
                            if (Jobs.getJobLevel("Ladino", player) == 1) {
                                player.getInventory().addItem(new ItemStack(Material.BOW, 1));
                                player.getInventory().addItem(new ItemStack(Material.ARROW, 32));
                                player.getInventory().addItem(new ItemStack(Material.CARROT, 3));
                            }
                            
                            if (Jobs.getJobLevel("Mago", player) == 1) {
                                player.getInventory().addItem(RecipeBook.createBook(BookTypes.Magia));
                                player.getInventory().addItem(SpellLoader.spellsByClass.get(Firebola.class).generateRecipe().getItem());
                            }
                            
                            if (Jobs.getJobLevel("Engenheiro", player) == 1) {
                                player.getInventory().addItem(new ItemStack(Material.SULPHUR, 10));
                                player.getInventory().addItem(new ItemStack(Material.REDSTONE, 20));
                                player.getInventory().addItem(CustomItem.getItem(Pistola.class).generateItem(1));
                            }
                            
                            if (Jobs.getJobLevel("Minerador", player) == 1) {
                                player.getInventory().addItem(WeaponDamage.checkForMods(new ItemStack(Material.IRON_PICKAXE, 1)));
                            }
                            
                        }
                        return;
                    }
                    if (ChatColor.stripColor(inv.getItem(slot).getItemMeta().getDisplayName()).equals(ChatColor.stripColor(Lang_Selecione))) {
                        player.sendMessage(Lang_WarnSelecione);
                        event.setCancelled(true);
                    } else if (player.getOpenInventory() != null && player.getOpenInventory().getTopInventory() instanceof Inventory) {
                        int indexEscolhida = Menu.getId(inv.getItem(slot).getItemMeta().getDisplayName().split(" - ")[1]);
                        int primarias = 0;
                        int secundarias = 0;
                        for (int x = 0; x < 9; x++) {
                            int qtoTem = inv.getItem(x + 9) == null ? 0 : inv.getItem(x + 9).getAmount();
                            if (qtoTem == 2) {
                                primarias++;
                            }
                            if (qtoTem == 1) {
                                secundarias++;
                            }
                        }
                        for (int x = 0; x < 9; x++) {
                            int qtoTem = inv.getItem(x + 9) == null ? 0 : inv.getItem(x + 9).getAmount();
                            if (x == indexEscolhida) {
                                if (inv.getItem(x + 9) == null || inv.getItem(x + 9).getType() == Material.AIR) {
                                    if (secundarias == 2 && primarias < 2) {
                                        ItemStack icone = new ItemStack(Material.DIAMOND, 2);
                                        MetaUtils.setItemNameAndLore(icone, Lang_Primaria, Lang_MuitoBoa);
                                        inv.setItem(x + 9, icone);
                                    } else if (secundarias != 2) {
                                        ItemStack icone = new ItemStack(Material.DIAMOND, 1);
                                        MetaUtils.setItemNameAndLore(icone, Lang_Secundaria, Lang_Mediana);
                                        inv.setItem(x + 9, icone);
                                    } else {
                                        inv.setItem(x + 9, null);
                                    }
                                } else {
                                    qtoTem++;
                                    if (primarias == 2) {
                                        inv.setItem(x + 9, null);
                                    } else {
                                        inv.getItem(x + 9).setAmount(qtoTem);
                                        if (qtoTem == 2) {
                                            MetaUtils.setItemNameAndLore(inv.getItem(x + 9), Lang_Primaria, Lang_MuitoBoa);
                                        } else {
                                            inv.setItem(x + 9, null);
                                        }
                                    }

                                }
                            }
                        }
                        primarias = 0;
                        secundarias = 0;
                        for (int x = 0; x < 9; x++) {
                            int qtoTem = inv.getItem(x + 9) == null ? 0 : inv.getItem(x + 9).getAmount();
                            if (qtoTem == 2) {
                                primarias++;
                            }
                            if (qtoTem == 1) {
                                secundarias++;
                            }
                        }
                        if (primarias == 2 && secundarias == 2) {
                            Wool icone = new Wool(DyeColor.LIME);
                            ItemStack i = icone.toItemStack();
                            i.setAmount(1);
                            MetaUtils.setItemNameAndLore(i, Lang_Confirmar, Lang_TemCerteza);
                            inv.setItem(31, i);
                        } else {
                            Wool icone = new Wool(DyeColor.RED);
                            ItemStack i = icone.toItemStack();
                            i.setAmount(1);
                            MetaUtils.setItemNameAndLore(i, Lang_Selecione, Lang_SejaEsperto);
                            inv.setItem(31, i);
                        }
                    }
                    player.updateInventory();
                }
            } catch (Exception e) {
                e.printStackTrace();
                event.setCancelled(true);
            }
        } else if (title.equals(NomeMenuDisabled)) {
            player.closeInventory();
            event.setCancelled(true);
        }
    }

    public static void escolheClasse(Player p) {
        Inventory MenuInventory;
        if (ListaMenus.containsKey(p.getName())) {
            MenuInventory = ListaMenus.get(p.getName());
        } else {
            ListaMenus.put(p.getName(), Bukkit.getServer().createInventory(null, 9 * 4, NomeMenu));
            MenuInventory = ListaMenus.get(p.getName());
        }
        MenuInventory.clear();
        for (int x = 0; x < 9; x++) {
            MenuInventory.setItem(x, MetaUtils.setItemNameAndLore(new ItemStack(Menu.getDesenho(x), 1), ChatColor.GREEN + Menu.getSimbolo(x) + ChatColor.GOLD + " - " + Menu.getNome(x), Menu.getToolTip(x) + "!"));
        }
        Wool icone = new Wool(DyeColor.RED);
        ItemStack i = icone.toItemStack();
        MetaUtils.setItemNameAndLore(i, Lang_Selecione, Lang_SejaEsperto);
        MenuInventory.setItem(31, i);
        p.openInventory(MenuInventory);
    }

    public static void mostraClasses(Player p) {
        List<PlayerSpec> specs = PlayerSpec.getSpecs(p);
        Inventory MenuInventory;
        if (ListaMenusDisabled.containsKey(p.getName())) {
            MenuInventory = ListaMenusDisabled.get(p.getName());
        } else {
            int tamanho = 9 * 4;
            if (KoM.debugMode) {
                KoM.log.info("Tamano invi:" + tamanho);
            }
            ListaMenusDisabled.put(p.getName(), Bukkit.getServer().createInventory(null, tamanho, NomeMenuDisabled));
            MenuInventory = ListaMenusDisabled.get(p.getName());
        }
        MenuInventory.clear();
        for (int x = 0; x < 9; x++) {
            int[] skills = KoM.database.getSkills(p.getUniqueId().toString());
            MenuInventory.setItem(x, MetaUtils.setItemNameAndLore(new ItemStack(Menu.getDesenho(x), 1), ChatColor.GREEN + Menu.getSimbolo(x) + ChatColor.GOLD + " - " + Menu.getNome(x), Menu.getToolTip(x) + "!"));
            if (skills[x] != 0) {
                MenuInventory.setItem(x + 9, MetaUtils.setItemNameAndLore(new ItemStack(Material.DIAMOND, skills[x]), skills[x] == 2 ? Lang_Primaria : Lang_Secundaria, skills[x] == 2 ? Lang_MuitoBoa : Lang_Mediana));
            }
        }
        int slot = 31;
        for (PlayerSpec possivel : specs) {
            ItemStack item = new ItemStack(Material.BOOK, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + possivel.name());
            List<String> lore = new ArrayList<String>();
            lore.add(ChatColor.YELLOW + "Especializacao de " + possivel.classe);
            lore.add(ChatColor.YELLOW + possivel.desc);
            lore.add("!");
            meta.setLore(lore);
            item.setItemMeta(meta);
            MenuInventory.setItem(slot, item);
            slot++;
        }

        p.openInventory(MenuInventory);
    }
}
