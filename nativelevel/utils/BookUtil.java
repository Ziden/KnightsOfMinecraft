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
package nativelevel.utils;

import java.util.List;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.Items.RecipeBook;
import nativelevel.MetaShit;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.integration.BungeeCordKom;
import nativelevel.sisteminhas.BookPortal;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class BookUtil {

    public static boolean hasBookMeta(ItemStack item) {
        if (item == null) {
            return false;
        }
        Material type = item.getType();
        if (type == Material.WRITTEN_BOOK) {
            return true;
        }
        return type == Material.BOOK_AND_QUILL;
    }
    
    public static boolean ehLivroDeQuest(ItemStack otroLivro) {
        if(otroLivro.getType()==Material.WRITTEN_BOOK) {
            
             CustomItem cu = CustomItem.getItem(otroLivro);
             if(cu!=null && cu instanceof RecipeBook) {
                 return true;
             }
            
              BookMeta m = (BookMeta) otroLivro.getItemMeta();
                if((m != null && m.getTitle()!=null && m.getTitle().contains("Quest")) || (m.getDisplayName()!=null && m.getDisplayName().contains("Quest"))) {
                    return true;
                }
        } else if(otroLivro.getType()==Material.TRIPWIRE_HOOK) {
            ItemMeta m = otroLivro.getItemMeta();
            if(m!=null && m.getDisplayName()!=null && m.getDisplayName().contains("[Chave]"))
                return true;
        }
        return false;
    }

    public static void getBookOnBookshelf(PlayerInteractEvent ev) {
        Block possivel = ev.getClickedBlock().getRelative(BlockFace.DOWN);
        for (int x = 0; x < 3; x++) {
            possivel = possivel.getRelative(BlockFace.DOWN);
            if (possivel.getType() == Material.CHEST) {
                Chest c = (Chest) possivel.getState();
                for (ItemStack coisa : c.getBlockInventory().getContents()) {
                    if (coisa != null && (coisa.getType() == Material.WRITTEN_BOOK)) {
                        ItemStack otroLivro = coisa.clone();
                        BookMeta m = (BookMeta) otroLivro.getItemMeta();
                        if(m != null && m.getTitle().contains("Quest") || (m.getDisplayName()!=null && m.getDisplayName().contains("Quest"))) {
                            return;
                        }
                        if(m!=null && m.getAuthor()!=null && m.getAuthor().equalsIgnoreCase("Jabu"))
                            return;
                        if (ev.getPlayer().hasMetadata(m.getTitle())) {
                            ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce ja tem este livro !"));
                            return;
                        }
                        
                        ev.getPlayer().getInventory().addItem(otroLivro);
                        ev.getPlayer().updateInventory();
                        ev.getPlayer().sendMessage(ChatColor.AQUA + L.m("Voce pegou o livro !"));
                        ev.getPlayer().setMetadata(m.getTitle(), new MetaShit(1));
                        break;
                    }
                }
                break;
            }
        }
    }

    public static ItemStack getBookAt(Block chest) {
        Chest c = (Chest) chest.getState();
        for (ItemStack coisa : c.getBlockInventory().getContents()) {
            if (coisa != null && coisa.getType() == Material.WRITTEN_BOOK || coisa.getType() == Material.BOOK_AND_QUILL) {
                return coisa;
            }
        }
        return null;
    }

    public static void acaoLivroEmPressurePlate(PlayerInteractEvent ev) {
        Block possivel = ev.getClickedBlock().getRelative(BlockFace.DOWN);
        if (KoM.debugMode) {
            KoM.log.info(" tentando ativar pressureplate !");
        }
        for (int x = 0; x < 3; x++) {
            possivel = possivel.getRelative(BlockFace.DOWN);
            if (possivel.getType() == Material.CHEST) {
                Chest c = (Chest) possivel.getState();
                for (ItemStack coisa : c.getBlockInventory().getContents()) {
                    if (coisa != null && (coisa.getType() == Material.WRITTEN_BOOK || coisa.getType() == Material.BOOK_AND_QUILL)) {
                        BookMeta m = (BookMeta) coisa.getItemMeta();
                        if (KoM.debugMode) {
                            KoM.log.info(" achei o livro !");
                        }
                        if (m.getTitle() != null && m.getTitle().equalsIgnoreCase("musica")) {
                            //Music.tocaMusica(ev.getPlayer(), m.getPage(1));
                            break;

                        } else if (m.getTitle() != null && m.getTitle().equalsIgnoreCase("TP")) {
                            BungLocation l = BookPortal.getLocationFromBook(coisa);
                            if (l != null) {
                                if (ev.getPlayer().isOp() && !ev.getPlayer().isSneaking()) {
                                    return;
                                }
                                BungeeCordKom.tp(ev.getPlayer(), l);
                                ev.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "* poof *");
                                //PlayEffect.play(VisualEffect.FIREWORK, l, "color:purple type:burst");
                            } else {
                                ev.getPlayer().sendMessage(ChatColor.RED + L.m("Este teleporter...esta...velho..."));
                            }
                        } else {
                            if (!ev.getPlayer().isSneaking()) {
                                String jaLeu = MetaShit.getMetaString(c.getLocation().toString(), ev.getPlayer());
                                if (jaLeu == null) {
                                    MetaShit.setMetaString(c.getLocation().toString(), ev.getPlayer(), "wololo");
                                } else {
                                    return;
                                }
                            }
                            for (String msg : m.getPages()) {
                                ev.getPlayer().sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + ChatColor.BOLD + (m.getTitle() == null ? "Jabu" : L.m(m.getTitle())) + ChatColor.DARK_AQUA + "]" + ChatColor.GOLD + " " + L.m(msg.replace("\n", " ")));
                            }
                            break;
                        }

                    }
                }
                break;
            }
        }
    }

    public static BookMeta getBookMeta(ItemStack item) {
        if (item == null) {
            return null;
        }
        ItemMeta meta = item.getItemMeta();
        if (!(meta instanceof BookMeta)) {
            return null;
        }
        return (BookMeta) meta;
    }

    public static boolean isBookMetaEmpty(ItemStack item) {
        if (item == null) {
            return true;
        }
        BookMeta meta = getBookMeta(item);
        return isBookMetaEmpty(meta);
    }

    public static boolean isBookMetaEmpty(BookMeta meta) {
        if (meta == null) {
            return true;
        }
        if (meta.hasTitle()) {
            return false;
        }
        if (meta.hasAuthor()) {
            return false;
        }
        return !meta.hasPages();
    }

    public static boolean setDisplayName(ItemStack item, String targetDisplayName) {
        if (item == null) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        String currentDisplayName = meta.getDisplayName();
        //if (MUtil.equals(currentDisplayName, targetDisplayName)) {
        //    return false;
        //}
        meta.setDisplayName(targetDisplayName);
        return item.setItemMeta(meta);
    }

    public static String getTitle(ItemStack item) {
        BookMeta meta = getBookMeta(item);
        if (meta == null) {
            return null;
        }
        if (!meta.hasTitle()) {
            return null;
        }
        return meta.getTitle();
    }

    public static boolean setTitle(ItemStack item, String title) {
        BookMeta meta = getBookMeta(item);
        if (meta == null) {
            return false;
        }
        meta.setTitle(title);
        if (!item.setItemMeta(meta)) {
            return false;
        }
        return true;
    }

    public static boolean isTitleEquals(ItemStack item, String title) {
        String actualTitle = getTitle(item);
        if (actualTitle == null) {
            return title == null;
        }
        return actualTitle.equals(title);
    }

    public static String getAuthor(ItemStack item) {
        BookMeta meta = getBookMeta(item);
        if (meta == null) {
            return null;
        }
        if (!meta.hasAuthor()) {
            return null;
        }
        return meta.getAuthor();
    }

    public static boolean setAuthor(ItemStack item, String author) {
        BookMeta meta = getBookMeta(item);
        if (meta == null) {
            return false;
        }
        meta.setAuthor(author);
        if (!item.setItemMeta(meta)) {
            return false;
        }
        return true;
    }

    public static boolean isAuthorEqualsId(ItemStack item, String author) {
        String actualAuthor = getAuthor(item);
        if (actualAuthor == null) {
            return author == null;
        }
        return actualAuthor.equalsIgnoreCase(author);
    }

    //public static boolean isAuthorEquals(ItemStack item, CommandSender author) {
    //return isAuthorEqualsId(item, SenderUtil.getSenderId(author));
    //}
    public static List<String> getPages(ItemStack item) {
        BookMeta meta = getBookMeta(item);
        if (meta == null) {
            return null;
        }
        if (!meta.hasPages()) {
            return null;
        }
        return meta.getPages();
    }

    public static boolean setPages(ItemStack item, List<String> pages) {
        BookMeta meta = getBookMeta(item);
        if (meta == null) {
            return false;
        }
        meta.setPages(pages);
        if (!item.setItemMeta(meta)) {
            return false;
        }
        return true;
    }

    public static boolean isPagesEquals(ItemStack item, List<String> pages) {
        List actualPages = getPages(item);
        if (actualPages == null) {
            return pages == null;
        }
        return actualPages.equals(pages);
    }

    public static boolean unlock(ItemStack item) {
        if (item == null) {
            return false;
        }
        if (item.getType() == Material.BOOK_AND_QUILL) {
            return true;
        }
        item.setType(Material.BOOK_AND_QUILL);
        return true;
    }

    public static boolean lock(ItemStack item) {
        if (item == null) {
            return false;
        }
        if (item.getType() == Material.WRITTEN_BOOK) {
            return true;
        }
        item.setType(Material.WRITTEN_BOOK);
        return true;
    }

    public static boolean isLocked(ItemStack item) {
        if (item == null) {
            return false;
        }
        return item.getType() == Material.WRITTEN_BOOK;
    }

    public static boolean isUnlocked(ItemStack item) {
        if (item == null) {
            return false;
        }
        return item.getType() == Material.BOOK_AND_QUILL;
    }

    public static boolean clear(ItemStack item) {
        item.setDurability((short) 0);
        item.setType(Material.BOOK_AND_QUILL);
        item.setItemMeta(null);
        return true;
    }

    public static boolean isCleared(ItemStack item) {
        return (item.getDurability() == 0) && (item.getType() == Material.BOOK_AND_QUILL) && (!item.hasItemMeta());
    }

    public static boolean containsFlag(ItemStack item, String flag) {
        if (flag == null) {
            return false;
        }
        if (!item.hasItemMeta()) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) {
            return false;
        }
        List lore = meta.getLore();
        return lore.contains(flag);
    }

    public static boolean addFlag(ItemStack item, String flag) {
        if (flag == null) {
            return false;
        }
        if (containsFlag(item, flag)) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta.hasLore()) {
            List lore = meta.getLore();
            lore.add(flag);
            meta.setLore(lore);
        } else {
            //meta.setLore(MUtil.list(new String[]{flag}));
        }
        if (!item.setItemMeta(meta)) {
            return false;
        }
        return true;
    }

    public static boolean removeFlag(ItemStack item, String flag) {
        if (flag == null) {
            return false;
        }
        if (!containsFlag(item, flag)) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) {
            return false;
        }
        List lore = meta.getLore();
        lore.remove(flag);
        if (lore.size() == 0) {
            meta.setLore(null);
        } else {
            meta.setLore(lore);
        }
        if (!item.setItemMeta(meta)) {
            return false;
        }
        return true;
    }
}
