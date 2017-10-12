package nativelevel.sisteminhas;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import nativelevel.KoM;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 *
 * @author Ben Sergent V
 */
public class Correio {

    // TODO Add letter delivery queue to deliver at a specific time option
    private final String version = "0.3.2";
    private org.bukkit.configuration.file.FileConfiguration mailboxesConfig = null;
    private java.io.File mailboxesFile = null;
    private org.bukkit.configuration.file.FileConfiguration packagesConfig = null;
    private java.io.File packagesFile = null;
    private org.bukkit.configuration.file.FileConfiguration languageConfig = null;
    private java.io.File languageFile = null;
    private ItemMeta mailboxRecipeMeta = null;
    private org.bukkit.inventory.meta.BookMeta stationeryMeta = null;
    private String prefix = ChatColor.WHITE + "[" + ChatColor.GOLD + "Mail" + ChatColor.WHITE + "]";

    /* Mailbox Textures */
    private final String mailboxTextureBlue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjZhNDllZmFhYWI1MzI1NTlmZmY5YWY3NWRhNmFjNGRkNzlkMTk5ZGNmMmZkNDk3Yzg1NDM4MDM4NTY0In19fQ==";
    private final String mailboxIdBlue = "48614330-6c44-47be-85ec-33ed037cf48c";
    private final String mailboxTextureWhite = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTM5ZTE5NzFjYmMzYzZmZWFhYjlkMWY4NWZjOWQ5YmYwODY3NjgzZjQxMjk1NWI5NjExMTdmZTY2ZTIifX19";
    private final String mailboxIdWhite = "480bff09-ed89-4214-a2bd-dab19fa5177d";
    private final String mailboxTextureRed = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGZhODljZTg1OTMyYmVjMWExYzNmMzFjYjdjMDg1YTViZmIyYWM3ZTQwNDA5NDIwOGMzYWQxMjM4NzlkYTZkYSJ9fX0=";
    private final String mailboxIdRed = "6a71ad04-2422-41f3-a501-6ea5707aaef3";
    private final String mailboxTextureGreen = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzJiY2NiNTI0MDg4NWNhNjRlNDI0YTBjMTY4YTc4YzY3NmI4Yzg0N2QxODdmNmZiZjYwMjdhMWZlODZlZSJ9fX0=";
    private final String mailboxIdGreen = "60621c0e-cb3e-471b-a237-4dec155f4889";

    public void onEnable() {
        // KnightsOfMania.getConfig().options().copyDefaults(true);
        // saveConfig();

        ItemStack blueMailboxCoupon = new ItemStack(Material.PAPER, 1);
        mailboxRecipeMeta = blueMailboxCoupon.getItemMeta();
        mailboxRecipeMeta.setDisplayName("§rMailbox Recipe");
        mailboxRecipeMeta.setLore(Arrays.asList("§r§7Right-click with this coupon", "§r§7to get a mailbox"));
        blueMailboxCoupon.setItemMeta(mailboxRecipeMeta);
        ShapedRecipe blueMailboxRecipe = new ShapedRecipe(blueMailboxCoupon);
        blueMailboxRecipe.shape("  w", "iii", "ici");
        blueMailboxRecipe.setIngredient('w', org.bukkit.Material.WOOL, -1);
        blueMailboxRecipe.setIngredient('i', org.bukkit.Material.IRON_INGOT);
        blueMailboxRecipe.setIngredient('c', org.bukkit.Material.CHEST);
        KoM._instance.getServer().addRecipe(blueMailboxRecipe);

        ItemStack stationery = new ItemStack(Material.BOOK_AND_QUILL, 1);
        stationeryMeta = (org.bukkit.inventory.meta.BookMeta) stationery.getItemMeta();
        stationeryMeta.setDisplayName("§rStationery");
        stationeryMeta.setLore(Arrays.asList("§r§7Right-click a mailbox to send after signing", "§r§7Use the name of the recipient as the title"));
        stationeryMeta.addPage("");
        stationery.setItemMeta(stationeryMeta);
        ShapelessRecipe stationeryRecipe = new ShapelessRecipe(stationery);
        stationeryRecipe.addIngredient(Material.PAPER);
        stationeryRecipe.addIngredient(Material.FEATHER);
        KoM._instance.getServer().addRecipe(stationeryRecipe);

        if (KoM._instance.getConfig().getString("prefix") != null) {
            prefix = KoM._instance.getConfig().getString("prefix").replaceAll("&", "§");
        }
        prefix = prefix + " ";

        if (mailboxesFile == null) {
            mailboxesFile = new java.io.File(KoM._instance.getDataFolder(), "mailboxes.yml");
        }
        mailboxesConfig = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(mailboxesFile);

        if (packagesFile == null) {
            packagesFile = new java.io.File(KoM._instance.getDataFolder(), "packages.yml");
        }
        packagesConfig = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(packagesFile);

        if (languageFile == null) {
            languageFile = new java.io.File(KoM._instance.getDataFolder(), "language.yml");
            if (!languageFile.exists()) {
                try {
                    InputStream in = KoM._instance.getResource("language.yml");
                    OutputStream out = new FileOutputStream(languageFile);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    out.close();
                    in.close();
                } catch (IOException ex) {
                    if (KoM._instance.getConfig().getBoolean("verbose_errors", false)) {
                        KoM._instance.getLogger().log(Level.WARNING, "Could not create a default languages.yml file.");
                    }
                }
            }

        }
        languageConfig = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(languageFile);

        KoM._instance.getServer().getPluginManager().registerEvents(new MailListener(), KoM._instance);
        KoM._instance.getServer().getPluginManager().registerEvents(new LoginListener(), KoM._instance);

        KoM._instance.getLogger().log(Level.INFO, "RealMail v{0} enabled.", version);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("realmail")) { // TODO Make the commands more organized when dealing w/ console vs player
            //<editor-fold defaultstate="collapsed" desc="Intruction Commands">
            if (args.length == 0 || (args.length < 2 && args[0].equals("1"))) { // Show crafting
                sender.sendMessage(new String[]{
                    ChatColor.GOLD + "" + ChatColor.BOLD + "RealMail - Crafting Recipes",
                    ChatColor.GOLD + "Mailbox:",
                    ChatColor.DARK_GRAY + "  --" + ChatColor.WHITE + "w   w" + ChatColor.WHITE + " = wool (1x)",
                    ChatColor.GRAY + "  i i i   i" + ChatColor.WHITE + " = iron ingot (5x)",
                    ChatColor.GRAY + "  i " + ChatColor.DARK_RED + "c" + ChatColor.GRAY + "i   " + ChatColor.DARK_RED + "c" + ChatColor.WHITE + " = chest (1x)",
                    ChatColor.GOLD + "Stationery:",
                    ChatColor.WHITE + "  1x paper and 1x feather",
                    ChatColor.WHITE + "Use /mail 2 for usage"
                });
            } else if (args.length < 2) {
                if (args[0].equals("2")) { // Show usage
                    sender.sendMessage(new String[]{
                        ChatColor.GOLD + "" + ChatColor.BOLD + "RealMail - Usage information",
                        ChatColor.GOLD + "Sending a letter:",
                        ChatColor.WHITE + "  1. Craft some stationery" + (KoM._instance.getConfig().getBoolean("let_players_spawn_stationary", false) ? " or use /mail new" : ""),
                        ChatColor.WHITE + "  2. Type your letter",
                        ChatColor.WHITE + "    - Type [Subject:mySubject] on first line for subject",
                        ChatColor.WHITE + "  3. Attach items if you wish (see /mail 3)",
                        ChatColor.WHITE + "  4. Sign the book/stationery as the recipient's username",
                        ChatColor.WHITE + "  5. Right-click a mailbox with the letter",
                        ChatColor.WHITE + "Use /mail 3 for packaging"
                    });
                } else if (args[0].equals("3")) { // Show attachments
                    sender.sendMessage(new String[]{
                        ChatColor.GOLD + "" + ChatColor.BOLD + "RealMail - Packaging",
                        ChatColor.GOLD + "Attach:",
                        ChatColor.WHITE + "  1. Pick up the item to be attached with your cursor",
                        ChatColor.WHITE + "  2. Drop it/left-click again on the letter (" + KoM._instance.getConfig().getInt("max_attachments", 4) + " stacks max)",
                        ChatColor.GOLD + "Detach:",
                        ChatColor.WHITE + "  1. Pick up the package with your cursor",
                        ChatColor.WHITE + "  2. Right-click empty slots with the package",
                        ChatColor.WHITE + "    Example: http://bit.ly/1Cijgbl",
                        sender.hasPermission("realmail.admin.seeAdminHelp") ? ChatColor.WHITE + "Use /mail 4 for administration" : ChatColor.WHITE + "Use /mail 1 for crafting"
                    });
                } else if (args[0].equals("4")) { // Show adminministration
                    if (sender.hasPermission("realmail.admin.seeAdminHelp")) {
                        sender.sendMessage(new String[]{
                            ChatColor.GOLD + "" + ChatColor.BOLD + "RealMail - Administration",
                            ChatColor.GOLD + "/mail send " + ChatColor.WHITE + " Send the letter in your hand to the addressed player",
                            ChatColor.GOLD + "/mail bulksend " + ChatColor.WHITE + " Send the letter in your hand to all players with mailboxes",
                            ChatColor.GOLD + "/mail spawn <mailbox|stationery> " + ChatColor.WHITE + " Spawn in a mailbox or some stationery",
                            ChatColor.GOLD + "/mail open [player] " + ChatColor.WHITE + " Open your mailbox or that of another player",
                            ChatColor.WHITE + "Use /mail 1 for crafting"
                        });
                    } else {
                        sender.sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("noperm.seeAdminHelp", "You do not have permission to see the admin commands."));
                    }
                }
            } //</editor-fold>

            if (args.length >= 1 && args[0].equals("version")) {
                //  sender.sendMessage(new String[] {ChatColor.GOLD+"RealMail v"+version, "Go to http://dev.bukkit.org/bukkit-plugins/realmail/ for updates."});
            } else {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(prefix + ChatColor.WHITE + "This command can only be run by a player.");
                } else {
                    //<editor-fold defaultstate="collapsed" desc="NonConsole Commands">
                    Player player = (Player) sender;
                    if (args.length >= 1) {
                        if (args[0].equals("send")) {
                            if (player.hasPermission("realmail.admin.sendmailAnywhere")) {
                                ItemStack itemHand = player.getItemInHand();
                                if (itemHand.getType() == Material.WRITTEN_BOOK && itemHand.hasItemMeta() && itemHand.getItemMeta().hasDisplayName() && (itemHand.getItemMeta().getDisplayName().contains("Letter") || itemHand.getItemMeta().getDisplayName().contains("Package"))) {
                                    BookMeta bookMeta = (BookMeta) itemHand.getItemMeta();
                                    OfflinePlayer recipient = Bukkit.getOfflinePlayer(bookMeta.getTitle());
                                    sendMail(itemHand, player, recipient, true);
                                } else {
                                    sender.sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.onlyLettersAndPackages", "You may only send letters and packages."));
                                }
                            } else {
                                player.sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("noperm.sendFromAnywhere", "You do not have permission to send mail from anywhere."));
                            }
                        } else if (args[0].equals("bulksend")) {
                            if (player.hasPermission("realmail.admin.bulkmail")) {
                                ItemStack itemHand = player.getItemInHand();
                                if (itemHand.getType() == Material.WRITTEN_BOOK && itemHand.hasItemMeta() && itemHand.getItemMeta().hasDisplayName() && (itemHand.getItemMeta().getDisplayName().contains("Letter") || itemHand.getItemMeta().getDisplayName().contains("Package"))) {
                                    List<String> players = (List<String>) mailboxesConfig.getList("players", new LinkedList<String>());
                                    for (String p : players) {
                                        sendMail(itemHand, player, UUID.fromString(p), false);
                                    }
                                    sender.sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.bulkSent", "Letter sent to all players on the server."));
                                } else {
                                    sender.sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.onlyLettersAndPackages", "You may only send letters and packages."));
                                }
                            } else {
                                player.sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("noperm.bulkSend", "You do not have permission to mail players in bulk."));
                            }
                        } else if (args[0].equals("spawn")) {
                            if (args.length != 2) {
                                sender.sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("commandSyntax.spawn", "Command syntax: /realmail spawn <mailbox|stationery>"));
                            } else {
                                if (args[1].equals("mailbox")) {
                                    if (player.hasPermission("realmail.admin.spawn.mailbox")) {
                                        giveMailbox(player);
                                    } else {
                                        player.sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("noperm.spawnMailbox", "You do not have permission to spawn mailboxes."));
                                    }
                                } else if (args[1].equals("stationary") || args[1].equals("stationery")) {
                                    if (player.hasPermission("realmail.admin.spawn.stationary")) {
                                        giveStationery(player);
                                    } else {
                                        player.sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("noperm.spawnStationary", "You do not have permission to spawn stationery."));
                                    }
                                } else {
                                    sender.sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("commandSyntax.spawn", "Command syntax: /realmail spawn <mailbox|stationery>"));
                                }
                            }
                        } else if (args[0].equals("open")) {
                            if (args.length >= 2) {
                                if (player.hasPermission("realmail.admin.openMailboxAnywhere.others")) {
                                    if (Bukkit.getOfflinePlayer(args[1]) != null) {
                                        openMailbox(Bukkit.getOfflinePlayer(args[1]), player);
                                    } else {
                                        player.sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.doesntHaveMailbox", "{0} does not have a mailbox.").replaceAll("\\{0}", args[1]));
                                    }
                                } else {
                                    player.sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("noperm.viewOtherMailbox", "You do not have permission to view other players' mailboxes."));
                                }
                            } else {
                                if (player.hasPermission("realmail.admin.openMailboxAnywhere")) {
                                    openMailbox(player, player);
                                } else {
                                    player.sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("noperm.openCommand", "You do not have permission to view your mailbox via command."));
                                }
                            }
                        } else if (args[0].equals("new")) {
                            if (player.hasPermission("realmail.admin.spawn.stationary") || KoM._instance.getConfig().getBoolean("let_players_spawn_stationary", false)) {
                                giveStationery(player);
                            } else {
                                player.sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("noperm.spawnStationary", "You do not have permission to spawn stationery.")); // TODO Start replaces with language compatible here
                            }
                        }
                    }
                    //</editor-fold>
                }
            }

        }

        return true;
    }

    public void giveMailbox(Player ply) {
        KoM._instance.getServer().dispatchCommand(KoM._instance.getServer().getConsoleSender(), "minecraft:give " + ply.getName() + " minecraft:skull 1 3 {display:{Name:\"§rMailbox\",Lore:[\"§r§7Blue\",\"§r§7Punch to change texture\"]},SkullOwner:{Id:\"" + mailboxIdBlue + "\",Name:\"ha1fBit\",Properties:{textures:[{Value:\"" + mailboxTextureBlue + "\"}]}}}");
    }

    public void giveStationery(Player ply) {
        ItemStack stationery = new ItemStack(Material.BOOK_AND_QUILL, 1);
        stationery.setItemMeta(stationeryMeta);
        ply.getInventory().addItem(stationery);
    }

    public boolean openMailbox(OfflinePlayer owner, Player viewer) {
        String ownerName = owner.getName();
        if (ownerName == null) {
            ownerName = "Someone";
        }
        String title = ownerName + "'s Mailbox";
        if (title.length() > 32) {
            title = title.replace("'s Mailbox", "");
        }
        if (title.length() > 32) {
            title = "Mailbox";
            viewer.sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.openedMailbox", "Opened {0}'s mailbox.").replaceAll("\\{0}", ownerName));
        }
        Inventory mailInv = Bukkit.createInventory(viewer, KoM._instance.getConfig().getInt("mailbox_rows", 4) * 9, title);
        List<org.bukkit.inventory.meta.BookMeta> letters = (List<org.bukkit.inventory.meta.BookMeta>) mailboxesConfig.getList(owner.getUniqueId() + ".letters", new LinkedList<org.bukkit.inventory.meta.BookMeta>());
        for (org.bukkit.inventory.meta.BookMeta letterMeta : letters) {
            ItemStack newBook;
            if (letterMeta.getDisplayName().contains("Stationary") || letterMeta.getDisplayName().contains("Stationery")) {
                newBook = new ItemStack(Material.BOOK_AND_QUILL, 1);
            } else {
                newBook = new ItemStack(Material.WRITTEN_BOOK, 1);
            }
            newBook.setItemMeta(letterMeta);
            HashMap leftover = mailInv.addItem(newBook);
            if (!leftover.isEmpty()) {
                viewer.sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.notAllShown", "Not all letters could be shown. Please empty your mailbox."));
                break;
            }
        }
        viewer.openInventory(mailInv);
        return true;
    }

    public boolean sendMail(ItemStack mailItem, Player fromPlayer, OfflinePlayer toPlayer, boolean sendMessages) {
        if (mailItem.getType() != Material.WRITTEN_BOOK) {
            return false;
        }
        BookMeta mailMeta = (BookMeta) mailItem.getItemMeta();
        if (mailboxesConfig.getList(toPlayer.getUniqueId() + ".letters", new LinkedList<ItemStack>()).size() < (KoM._instance.getConfig().getInt("mailbox_rows", 4) * 9)) {
            java.util.Date dateRaw = java.util.Calendar.getInstance().getTime();
            java.text.SimpleDateFormat format = new java.text.SimpleDateFormat();
            format.applyPattern(KoM._instance.getConfig().getString("dateformat"));
            String dateString = format.format(dateRaw);

            List<String> oldLore = (List<String>) mailMeta.getLore();
            List<String> lore = (List<String>) new LinkedList(Arrays.asList("§r§7Date: " + dateString));
            for (String oldLoreLine : oldLore) {
                if (oldLoreLine.contains("ID")) {
                    lore.add(oldLoreLine);
                } else if (oldLoreLine.contains("To")) {
                    lore.add(oldLoreLine);
                }
            }
            mailMeta.setLore(lore);

            List<org.bukkit.inventory.meta.BookMeta> letters = (List<org.bukkit.inventory.meta.BookMeta>) mailboxesConfig.getList(toPlayer.getUniqueId() + ".letters", new LinkedList<org.bukkit.inventory.meta.BookMeta>());
            letters.add(mailMeta);
            mailboxesConfig.set(toPlayer.getUniqueId() + ".letters", letters);
            mailboxesConfig.set(toPlayer.getUniqueId() + ".unread", true);
            try {
                mailboxesConfig.save(mailboxesFile);
                fromPlayer.getInventory().remove(mailItem);
                if (sendMessages) {
                    fromPlayer.sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.letterSent", "Letter sent to {0}.").replaceAll("\\{0}", toPlayer.getName()));
                }
                udpateMailboxFlags(toPlayer.getPlayer());
                if (toPlayer.getPlayer() != null) {
                    toPlayer.getPlayer().sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.gotMail", "You've got mail! Check your mailbox. Use /mail to learn how to craft one."));
                }
            } catch (Exception ex) {
                fromPlayer.sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.failedToSend", "Failed to send the letter."));
                if (KoM._instance.getConfig().getBoolean("verbose_errors", false)) {
                    ex.printStackTrace();
                }
            }
        } else {
            if (sendMessages) {
                fromPlayer.sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.mailboxFull", "Recipient's mailbox was full. Please try again later."));
            }
            if (toPlayer.getPlayer() != null) {
                toPlayer.getPlayer().sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.mailboxFullReceiver", "{0} tried to send you mail, but you mailbox was full. Consider emptying it out.").replaceAll("\\{0}", fromPlayer.getName()));
            }
        }
        return true;
    }

    public boolean sendMail(ItemStack mailItem, Player fromPlayer, UUID toUUID, boolean sendMessages) {
        if (mailItem.getType() != Material.WRITTEN_BOOK) {
            return false;
        }
        BookMeta mailMeta = (BookMeta) mailItem.getItemMeta();
        if (mailboxesConfig.getList(toUUID + ".letters", new LinkedList<ItemStack>()).size() < (KoM._instance.getConfig().getInt("mailbox_rows", 4) * 9)) {
            java.util.Date dateRaw = java.util.Calendar.getInstance().getTime();
            java.text.SimpleDateFormat format = new java.text.SimpleDateFormat();
            format.applyPattern(KoM._instance.getConfig().getString("dateformat"));
            String dateString = format.format(dateRaw);

            List<String> oldLore = (List<String>) mailMeta.getLore();
            List<String> lore = (List<String>) new LinkedList(Arrays.asList("§r§7To: " + mailMeta.getTitle(), "§r§7Date: " + dateString));
            for (String oldLoreLine : oldLore) {
                if (oldLoreLine.contains("ID")) {
                    lore.add(oldLoreLine);
                    break;
                }
            }
            mailMeta.setLore(lore);

            List<org.bukkit.inventory.meta.BookMeta> letters = (List<org.bukkit.inventory.meta.BookMeta>) mailboxesConfig.getList(toUUID + ".letters", new LinkedList<org.bukkit.inventory.meta.BookMeta>());
            letters.add(mailMeta);
            mailboxesConfig.set(toUUID + ".letters", letters);
            mailboxesConfig.set(toUUID + ".unread", true);
            try {
                mailboxesConfig.save(mailboxesFile);
                fromPlayer.getInventory().remove(mailItem);
                if (sendMessages) {
                    fromPlayer.sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.letterSent", "Letter sent to {0}.").replaceAll("\\{0}", Bukkit.getOfflinePlayer(toUUID).getName()));
                }
                udpateMailboxFlags(Bukkit.getOfflinePlayer(toUUID));
                if (Bukkit.getPlayer(toUUID) != null) {
                    Bukkit.getPlayer(toUUID).sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.gotMail", "You've got mail! Check your mailbox. Use /mail to learn how to craft one."));
                }
            } catch (Exception ex) {
                fromPlayer.sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.failedToSend", "Failed to send the letter."));
                if (KoM._instance.getConfig().getBoolean("verbose_errors", false)) {
                    ex.printStackTrace();
                }
            }
        } else {
            if (sendMessages) {
                fromPlayer.sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.mailboxFull", "Recipient's mailbox was full. Please try again later."));
            }
            if (Bukkit.getPlayer(toUUID) != null) {
                Bukkit.getPlayer(toUUID).sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.mailboxFullReceiver", "{0} tried to send you mail, but you mailbox was full. Consider emptying it out.").replaceAll("\\{0}", fromPlayer.getName()));
            }
        }
        return true;
    }

    public boolean udpateMailboxFlags(OfflinePlayer owner) {
        return true;
    }

    //<editor-fold defaultstate="collapsed" desc="Old Methods">
    private boolean sendBook(org.bukkit.inventory.ItemStack bookStack, Player fromPlayer, String toString, boolean bulk) {
        /*Player target  = org.bukkit.Bukkit.getServer().getPlayer(toString);
         try {
         java.util.Date dateRaw = java.util.Calendar.getInstance().getTime();
         java.text.SimpleDateFormat format = new java.text.SimpleDateFormat();
         format.applyPattern(getConfig().getString("dateformat"));
         String dateString = format.format(dateRaw);
            
         org.bukkit.inventory.meta.BookMeta bookMeta = (org.bukkit.inventory.meta.BookMeta) bookStack.getItemMeta();
         java.util.List<String> oldPages = bookMeta.getPages();
         java.util.List<String> newPages = new java.util.LinkedList<String>();
         newPages.add("§0From: "+fromPlayer.getDisplayName()+"\n§0To: "+toString+"\n§0Subject: "+ bookMeta.getTitle() +"\n§0Date: "+dateString+"\n§0\n§0\n§0\n§0\n§0\n§0\n§0\n§0\n§l§1--Real Mail--§r");
         if (oldPages.size()>0) {
         for (int i = 0; i < oldPages.size(); i++) {
         newPages.add(oldPages.get(i));
         }
         }
         bookMeta.setPages(newPages);
            
         java.util.List<String> lore = new java.util.LinkedList<String>();
         lore.add("§7to  "+toString);
         lore.add("§7on "+dateString);
         bookMeta.setLore(lore);
            
         bookStack.setItemMeta(bookMeta);

         Send Book 
         //target.getInventory().addItem((org.bukkit.inventory.ItemStack) bookCraftStack);
         //fromPlayer.setItemInHand(new org.bukkit.inventory.ItemStack(0));
         if (mailboxesConfig.contains(toString)) {
         org.bukkit.World world = Bukkit.getWorld((String) mailboxesConfig.get(toString+".world"));
         org.bukkit.block.Block block = world.getBlockAt(mailboxesConfig.getInt(toString+".x"), mailboxesConfig.getInt(toString+".y"), mailboxesConfig.getInt(toString+".z"));
         if (block.getTypeId() == 54) {
         org.bukkit.block.Chest chest = (org.bukkit.block.Chest) block.getState();
         org.bukkit.inventory.Inventory chestInv = chest.getBlockInventory();
         chestInv.addItem(bookStack);
         setSignStatus(true, chest.getBlock(), toString);
         if (!bulk) {
         fromPlayer.sendMessage(prefix+ChatColor.WHITE+"Mail Sent!");
         fromPlayer.setItemInHand(new org.bukkit.inventory.ItemStack(0));
         }
         if (target != null) {
         target.sendMessage(prefix+ChatColor.WHITE+"You've got mail!");
         }
         // If there's a sign, mark as unread
         } else {
         if (target != null) {
         target.sendMessage(prefix+ChatColor.WHITE+fromPlayer.getDisplayName()+" tried to send you a message, but your mailbox is missing!");
         target.sendMessage(prefix+ChatColor.WHITE+"Use "+org.bukkit.ChatColor.ITALIC+"/rm setmailbox"+org.bukkit.ChatColor.RESET+" on a chest.");
         }
         if (!bulk) {
         fromPlayer.sendMessage(prefix+ChatColor.WHITE+"Failed to send.");
         fromPlayer.sendMessage(prefix+ChatColor.WHITE+"They don't have a mailbox!");
         }
         }
         } else {
         if (target != null) {
         target.sendMessage(prefix+ChatColor.WHITE+fromPlayer.getDisplayName()+" tried to send you a message, but you don't have a mailbox!");
         target.sendMessage(prefix+ChatColor.WHITE+"Use "+org.bukkit.ChatColor.ITALIC+"/rm setmailbox"+org.bukkit.ChatColor.RESET+" on a chest.");
         }
         if (!bulk) {
         fromPlayer.sendMessage(prefix+ChatColor.WHITE+"Failed to send.");
         fromPlayer.sendMessage(prefix+ChatColor.WHITE+"They don't have a mailbox!");
         }
         }

         } catch (Exception ex) {
         ex.printStackTrace();
         if (!bulk) {
         fromPlayer.sendMessage(prefix+ChatColor.WHITE+"Failed to mail the book.");
         }
         }*/
        return true;
    }

    public boolean setSignStatus(boolean unread, org.bukkit.block.Block chestBlock, String ownerName) {
        /*for (int x = chestBlock.getX()-getConfig().getInt("signradius"); x <= chestBlock.getX()+getConfig().getInt("signradius"); x++) {
         for (int y = chestBlock.getY()-getConfig().getInt("signradius"); y <= chestBlock.getY()+getConfig().getInt("signradius"); y++) {
         for (int z = chestBlock.getZ()-getConfig().getInt("signradius"); z <= chestBlock.getZ()+getConfig().getInt("signradius"); z++) {
         org.bukkit.block.Block block = chestBlock.getWorld().getBlockAt(x, y, z);
         //if (block.getType() == org.bukkit.Material.SIGN || block.getType() == org.bukkit.Material.SIGN_POST) {
         if (block.getTypeId() == 63 || block.getTypeId() == 68) {
         org.bukkit.block.Sign sign = (org.bukkit.block.Sign) block.getState();
         if (sign.getLine(0).equals("[Mailbox]")) {
         sign.setLine(1, ownerName);
         if (unread) {
         sign.setLine(2, "  §a"+getConfig().getString("unreadmailsigntext"));
         mailboxesConfig.set(ownerName+".unread", true);
         } else {
         sign.setLine(2, getConfig().getString("readmailsigntext"));
         mailboxesConfig.set(ownerName+".unread", false);
         }
         sign.update();
         try {
         mailboxesConfig.save(mailboxesFile);
         } catch (Exception ex) {
         ex.printStackTrace();
         }
         }
         }
         }
         }
         }*/
        return false;
    }
    //</editor-fold>

    public final class MailListener implements org.bukkit.event.Listener {

        @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.NORMAL)
        public void onUseItemEvent(org.bukkit.event.player.PlayerInteractEvent e) {
            if (e.getItem() != null) {
                ItemStack is = e.getItem();
                ItemStack toBeRemoved = is.clone();
                toBeRemoved.setAmount(1);
                /* Exchange Coupon */
                if (is.getType() == Material.PAPER && is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().contains("Mailbox Recipe")) {
                    e.getPlayer().getInventory().removeItem(toBeRemoved);
                    giveMailbox(e.getPlayer());
                    e.getPlayer().sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.exchangedRecipe", "You exchanged your recipe for a mailbox."));
                } /* Cycle texture */ else if (is.getType() == Material.SKULL_ITEM && (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) && is.getItemMeta().hasLore() && is.getItemMeta().getLore().get(1).contains("Punch to change texture")) {
                    e.getPlayer().getInventory().removeItem(toBeRemoved);
                    if (is.getItemMeta().getLore().get(0).contains("Blue")) {
                        KoM._instance.getServer().dispatchCommand(KoM._instance.getServer().getConsoleSender(), "minecraft:give " + e.getPlayer().getName() + " minecraft:skull 1 3 {display:{Name:\"§rMailbox\",Lore:[\"§r§7White\",\"§r§7Punch to change texture\"]},SkullOwner:{Id:\"" + mailboxIdWhite + "\",Name:\"ha1fBit\",Properties:{textures:[{Value:\"" + mailboxTextureWhite + "\"}]}}}");
                    } else if (is.getItemMeta().getLore().get(0).contains("White")) {
                        KoM._instance.getServer().dispatchCommand(KoM._instance.getServer().getConsoleSender(), "minecraft:give " + e.getPlayer().getName() + " minecraft:skull 1 3 {display:{Name:\"§rMailbox\",Lore:[\"§r§7Red\",\"§r§7Punch to change texture\"]},SkullOwner:{Id:\"" + mailboxIdRed + "\",Name:\"ha1fBit\",Properties:{textures:[{Value:\"" + mailboxTextureRed + "\"}]}}}");
                    } else if (is.getItemMeta().getLore().get(0).contains("Red")) {
                        KoM._instance.getServer().dispatchCommand(KoM._instance.getServer().getConsoleSender(), "minecraft:give " + e.getPlayer().getName() + " minecraft:skull 1 3 {display:{Name:\"§rMailbox\",Lore:[\"§r§7Green\",\"§r§7Punch to change texture\"]},SkullOwner:{Id:\"" + mailboxIdGreen + "\",Name:\"ha1fBit\",Properties:{textures:[{Value:\"" + mailboxTextureGreen + "\"}]}}}");
                    } else if (is.getItemMeta().getLore().get(0).contains("Green")) {
                        KoM._instance.getServer().dispatchCommand(KoM._instance.getServer().getConsoleSender(), "minecraft:give " + e.getPlayer().getName() + " minecraft:skull 1 3 {display:{Name:\"§rMailbox\",Lore:[\"§r§7Blue\",\"§r§7Punch to change texture\"]},SkullOwner:{Id:\"" + mailboxIdBlue + "\",Name:\"ha1fBit\",Properties:{textures:[{Value:\"" + mailboxTextureBlue + "\"}]}}}");
                    }
                    e.getPlayer().sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.textureChange", "You changed your mailbox's texture."));
                } /* Stationery Stuff */ else if (is.getType() == Material.WRITTEN_BOOK && is.hasItemMeta() && is.getItemMeta().hasLore() && is.getItemMeta().hasDisplayName() && (is.getItemMeta().getDisplayName().contains("§rLetter") || is.getItemMeta().getDisplayName().contains("§rPackage"))) {
                    if (e.getClickedBlock() != null && e.getClickedBlock().getType().equals(Material.SKULL)) {

                        List<String> players = (List<String>) mailboxesConfig.getList("players", new LinkedList<String>());

                        playersLoop:
                        for (String p : players) {
                            List<Location> playersMailboxLocations = (List<Location>) mailboxesConfig.getList(p + ".mailboxes", new LinkedList<Location>());
                            for (Location loc : playersMailboxLocations) {
                                if (e.getClickedBlock().getLocation().equals(loc)) {
                                    org.bukkit.inventory.meta.BookMeta newLetter = (org.bukkit.inventory.meta.BookMeta) is.getItemMeta();
                                    OfflinePlayer recipient = Bukkit.getOfflinePlayer(newLetter.getTitle());
                                    if (e.getPlayer().hasPermission("realmail.user.sendmail")) {
                                        if (KoM._instance.getConfig().getBoolean("universal_mailboxes", false) || (!KoM._instance.getConfig().getBoolean("universal_mailboxes", false) && p.equals(e.getPlayer().getUniqueId() + "")) || e.getPlayer().hasPermission("realmail.admin.sendmailAnywhere")) {
                                            ItemStack newLetterItem = new ItemStack(Material.WRITTEN_BOOK);
                                            newLetterItem.setItemMeta(newLetter);
                                            Correio.this.sendMail(newLetterItem, e.getPlayer(), recipient, true);
                                        } else {
                                            e.getPlayer().sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.notYourMailbox", "That's not your mailbox. Use /mail to find out how to craft your own."));
                                        }
                                    } else {
                                        e.getPlayer().sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("noperm.sendMail", "You do not have permission to send mail."));
                                    }
                                    break playersLoop;
                                }
                            }
                        }

                    }
                }
            } // End empty hand detection

            /* Open Mailbox */
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null && e.getClickedBlock().getType().equals(Material.SKULL) && e.getPlayer().getItemInHand().getType() != Material.WRITTEN_BOOK) {
                List<String> players = (List<String>) mailboxesConfig.getList("players", new LinkedList<String>());
                OfflinePlayer mailboxOwner = null;
                for (String p : players) {
                    List<Location> locations = (List<Location>) mailboxesConfig.getList(p + ".mailboxes", new LinkedList<Location>());
                    for (Location loc : locations) {
                        if (e.getClickedBlock().getLocation().equals(loc)) {
                            mailboxOwner = Bukkit.getOfflinePlayer(UUID.fromString(p));
                        }
                    }
                }
                if (mailboxOwner != null) {
                    if (KoM._instance.getConfig().getBoolean("universal_mailboxes", false)) {
                        openMailbox(e.getPlayer(), e.getPlayer());
                    } else {
                        if (KoM._instance.getConfig().getBoolean("lock_mailboxes", true)) {
                            if (mailboxOwner.getUniqueId().equals(e.getPlayer().getUniqueId()) || e.getPlayer().hasPermission("realmail.admin.openMailboxAnywhere.others")) {
                                openMailbox(mailboxOwner, e.getPlayer());
                            } else {
                                e.getPlayer().sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.notYourMailbox", "That's not your mailbox. Use /mail to find out how to craft your own."));
                            }
                        } else {
                            openMailbox(mailboxOwner, e.getPlayer());
                        }
                    }
                }
            }
        }

        //<editor-fold defaultstate="collapsed" desc="Signing Letters">
        @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.NORMAL)
        public void onEditBook(org.bukkit.event.player.PlayerEditBookEvent e) {
            if (e.getPreviousBookMeta() != null && e.getPreviousBookMeta().hasDisplayName() && (e.getPreviousBookMeta().getDisplayName().contains("Stationary") || e.getPreviousBookMeta().getDisplayName().contains("Stationery") || e.getPreviousBookMeta().getDisplayName().contains("Package"))) {

                BookMeta newBM = e.getNewBookMeta();
                if (e.getPreviousBookMeta().getDisplayName().contains("Package")) {
                    newBM.setDisplayName("§rPackage");
                    newBM.setLore(e.getPreviousBookMeta().getLore());
                } else {
                    newBM.setDisplayName("§rStationery");
                    newBM.setLore(Arrays.asList("§r§7Right-click a mailbox to send after signing", "§r§7Use the name of the recipient as the title"));
                }
                e.setNewBookMeta(newBM);

                if (e.isSigning()) {
                    if (newBM.getDisplayName().contains("Stationary") || newBM.getDisplayName().contains("Stationery")) {
                        newBM.setDisplayName("§rLetter");
                    }

                    List<String> bookLore = newBM.getLore();
                    bookLore.add("§r§7To: " + newBM.getTitle());
                    newBM.setLore(bookLore);

                    if (newBM.getPageCount() >= 1) {
                        String firstPage = newBM.getPages().get(0); // [subject|subj|s:Test Subject;moon|moonrune|rune;burn|burnonread|selfdestruct|destruct]
                        firstPage = firstPage.split("\n")[0];
                        if (firstPage.matches("^(.*)\\[Subject:(.*)\\](.*)$")) { // [Subject:Test Subject]

                            firstPage = firstPage.replaceFirst("^(.*)\\[Subject:", "");
                            firstPage = firstPage.replaceFirst("\\](.*)", "");
                            newBM.setDisplayName(newBM.getDisplayName() + " - " + firstPage);
                        }
                    }

                    // Check if the recipient exists before signing
                    if (mailboxesConfig.getList("players", new LinkedList<String>()).contains(Bukkit.getOfflinePlayer(newBM.getTitle()).getUniqueId() + "")) {
                        e.setNewBookMeta(newBM);
                    } else { // TODO Make sure to still save the book like when you hit "Done" even when the signing fails
                        e.getPlayer().sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.unknownRecipient", "Could not sign. {0} is not a known user on this server.").replaceAll("\\{0}", newBM.getTitle()));
                        e.setSigning(false);
                    }
                }
            }
        }
        //</editor-fold>

        @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.NORMAL)
        public void onInventoryClick(org.bukkit.event.inventory.InventoryClickEvent e) {
            //<editor-fold defaultstate="collapsed" desc="Only letters and attachments in mailboxes and no villagers">
            if (e.getInventory().getName().contains("Mailbox")) {
                ItemStack cursor = e.getCursor();
                ItemStack current = e.getCurrentItem();

                boolean allowCursor = false;
                boolean allowCurrent = false;

                if (cursor == null || cursor.getType() == Material.AIR) {
                    allowCursor = true;
                } else {
                    if (cursor.hasItemMeta()) {
                        if (cursor.getItemMeta().hasDisplayName()) {
                            if (cursor.getItemMeta().getDisplayName().contains("Stationary") || cursor.getItemMeta().getDisplayName().contains("Stationery") || cursor.getItemMeta().getDisplayName().contains("Letter") || cursor.getItemMeta().getDisplayName().contains("Package")) {
                                allowCursor = true;
                            }
                        }
                    }
                }
                if (current == null || current.getType() == Material.AIR) {
                    allowCurrent = true;
                } else {
                    if (current.hasItemMeta()) {
                        if (current.getItemMeta().hasDisplayName()) {
                            if (current.getItemMeta().getDisplayName().contains("Stationary") || current.getItemMeta().getDisplayName().contains("Stationery") || current.getItemMeta().getDisplayName().contains("Letter") || current.getItemMeta().getDisplayName().contains("Package")) {
                                allowCurrent = true;
                            }
                        }
                    }
                }
                if ((!allowCursor) || (!allowCurrent)) {
                    e.setCancelled(true);
                }
            } else if (e.getInventory().getType() == InventoryType.MERCHANT) {
                ItemStack cursor = e.getCursor();
                ItemStack current = e.getCurrentItem();

                boolean disallowCursor = false;
                boolean disallowCurrent = false;

                if (cursor != null && cursor.hasItemMeta()) {
                    if (cursor.getItemMeta().hasDisplayName()) {
                        if (cursor.getItemMeta().getDisplayName().contains("Stationary") || cursor.getItemMeta().getDisplayName().contains("Stationery") || cursor.getItemMeta().getDisplayName().contains("Letter") || cursor.getItemMeta().getDisplayName().contains("Package")) {
                            disallowCursor = true;
                        }
                    }
                }
                if (current != null && current.hasItemMeta()) {
                    if (current.getItemMeta().hasDisplayName()) {
                        if (current.getItemMeta().getDisplayName().contains("Stationary") || current.getItemMeta().getDisplayName().contains("Stationery") || current.getItemMeta().getDisplayName().contains("Letter") || current.getItemMeta().getDisplayName().contains("Package")) {
                            disallowCurrent = true;
                        }
                    }
                }
                if (disallowCursor || disallowCurrent) {
                    e.setCancelled(true);
                }
            }
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="Attach items">
            if (e.isLeftClick()) { // TODO Fix all these creative bugs
                ItemStack current = e.getCurrentItem();
                ItemStack cursor = e.getCursor();

                if ((current == null || current.getType() == Material.BOOK_AND_QUILL) && cursor != null && cursor.getType() != Material.AIR) {
                    if (current != null && current.hasItemMeta()) {
                        if (current.getItemMeta().hasDisplayName()) {
                            if (current.getItemMeta().getDisplayName().contains("Stationary") || current.getItemMeta().getDisplayName().contains("Stationery") || current.getItemMeta().getDisplayName().contains("Package")) {
                                if (cursor != null && cursor.hasItemMeta() && cursor.getItemMeta().hasDisplayName() && cursor.getItemMeta().getDisplayName().contains("Package")) {
                                    e.getWhoClicked().sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.packageInPackage", "You can't put packages inside of packages. You'll create package-ception."));
                                    e.setResult(Event.Result.DENY);
                                } else {
                                    if (e.getWhoClicked().hasPermission("realmail.user.attach")) {
                                        if (e.getClick() != ClickType.CREATIVE) {
                                            List<ItemStack> attachments = new LinkedList<ItemStack>();
                                            String code = "";
                                            ItemMeta im = current.getItemMeta();
                                            if (im.hasLore()) {
                                                for (String loreLine : im.getLore()) {
                                                    if (loreLine.contains("ID")) {
                                                        code = loreLine.replace("§r§7ID: ", "");
                                                        attachments = (List<ItemStack>) packagesConfig.getList(code, new LinkedList<ItemStack>());
                                                        break;
                                                    }
                                                }
                                            }
                                            if (attachments.size() < KoM._instance.getConfig().getInt("max_attachments", 4) || e.getWhoClicked().hasPermission("realmail.admin.bypassAttachmentLimits")) {
                                                if (code.equals("")) {
                                                    code = UUID.randomUUID().toString();
                                                    List<String> lore = im.getLore();

                                                    boolean hasDetachInstr = false;
                                                    for (String detachLoreLine : lore) {
                                                        if (detachLoreLine.contains("to detach")) {
                                                            hasDetachInstr = true;
                                                        }
                                                    }

                                                    if (!hasDetachInstr) {
                                                        lore.add("§r§7Right-click empty slot with package to detach");
                                                    }

                                                    lore.add("§r§7ID: " + code);
                                                    im.setLore(lore);
                                                }
                                                attachments.add(cursor.clone());
                                                packagesConfig.set(code, attachments);
                                                try {
                                                    packagesConfig.save(packagesFile);
                                                    e.getWhoClicked().sendMessage(prefix + ChatColor.WHITE + cursor.getType().name() + " x" + cursor.getAmount() + " attached.");
                                                    im.setDisplayName("§rPackage");
                                                    current.setItemMeta(im);
                                                    e.setCursor(new ItemStack(Material.AIR));
                                                    //cursor.setType(Material.AIR);
                                                    //cursor.setAmount(0);
                                                    e.setResult(Event.Result.DENY);
                                                } catch (Exception ex) {
                                                    e.getWhoClicked().sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.couldNotAttach", "Could not attach the item."));
                                                }
                                            } else {
                                                e.getWhoClicked().sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.maxAlreadyAttached", "Max items already attached. ({0})").replaceAll("\\{0}", KoM._instance.getConfig().getInt("max_attachments", 4) + ""));
                                                e.setResult(Event.Result.DENY);
                                            }
                                        } else {
                                            e.getWhoClicked().sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.attachInCreative", "Attaching and detaching items in creative is currently disabled due to bugs."));
                                        }
                                    } else {
                                        e.getWhoClicked().sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("noperm.attachments", "You do not have permission to attach items."));
                                        e.setResult(Event.Result.DENY);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="Detach items">
            if ((e.isRightClick() || e.getClick() == ClickType.CREATIVE) && e.getCursor() != null && e.getCursor().getType() != Material.AIR && (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR)) {
                if (e.getCursor().getType() == Material.WRITTEN_BOOK || e.getCursor().getType() == Material.BOOK_AND_QUILL) {
                    if (e.getCursor().hasItemMeta() && e.getCursor().getItemMeta().hasDisplayName() && e.getCursor().getItemMeta().getDisplayName().contains("Package") && e.getCursor().getItemMeta().hasLore()) {
                        for (String loreLine : e.getCursor().getItemMeta().getLore()) {
                            if (loreLine.contains("ID")) {
                                String code = loreLine.replace("§r§7ID: ", "");
                                if (packagesConfig.contains(code)) {

                                    List<ItemStack> attachments = (List<ItemStack>) packagesConfig.getList(code, new LinkedList<ItemStack>());

                                    e.setCurrentItem(attachments.get(0));
                                    attachments.remove(0);

                                    if (attachments.size() <= 0) {
                                        ItemMeta im = e.getCursor().getItemMeta();
                                        ArrayList<String> lore2 = (ArrayList<String>) im.getLore();
                                        for (String loreLine2 : (ArrayList<String>) lore2.clone()) {
                                            if (loreLine2.contains("ID")) {
                                                lore2.remove(loreLine2);
                                                break;
                                            }
                                        }
                                        im.setLore(lore2);
                                        e.getCursor().setItemMeta(im);
                                        attachments = null;
                                    }

                                    packagesConfig.set(code, attachments);

                                    try {
                                        packagesConfig.save(packagesFile);
                                        e.setResult(Event.Result.DENY);
                                    } catch (Exception ex) {
                                        e.getWhoClicked().sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.couldNotDetach", "Could not detach item."));
                                        if (KoM._instance.getConfig().getBoolean("verbose_errors", false)) {
                                            ex.printStackTrace();
                                        }
                                    }

                                } else {
                                    e.getWhoClicked().sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.couldNotDetachCode", "Cound not detach item, unknown code."));
                                }
                                break;
                            }
                        }
                    }
                }
            }
            //</editor-fold>
        }

        //<editor-fold defaultstate="collapsed" desc="Save mailbox on close">
        @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.NORMAL)
        public void onInventoryClose(org.bukkit.event.inventory.InventoryCloseEvent e) {
            if (e.getInventory().getName().contains("Mailbox")) {
                List<BookMeta> letters = new LinkedList<BookMeta>();
                for (ItemStack is : e.getInventory().getContents()) { // TODO Should do anything about extra letters?
                    if (is != null && is.hasItemMeta()) {
                        letters.add((org.bukkit.inventory.meta.BookMeta) is.getItemMeta());
                    }
                }
                String ownerName = e.getInventory().getName();
                ownerName = ownerName.replace("'s Mailbox", "");
                mailboxesConfig.set(Bukkit.getOfflinePlayer(ownerName).getUniqueId() + ".letters", letters);
                mailboxesConfig.set(Bukkit.getOfflinePlayer(ownerName).getUniqueId() + ".unread", false);
                Correio.this.udpateMailboxFlags(Bukkit.getOfflinePlayer(ownerName));
                try {
                    mailboxesConfig.save(mailboxesFile);
                } catch (Exception ex) {
                    KoM._instance.getLogger().log(Level.INFO, "Failed to save {0}''s mailbox.", e.getPlayer().getName());
                    if (KoM._instance.getConfig().getBoolean("verbose_errors", false)) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Detect mailbox placing">
        @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.NORMAL)
        public void onBlockPlace(org.bukkit.event.block.BlockPlaceEvent e) {
            if (e.getItemInHand() != null) {
                ItemStack is = e.getItemInHand();

                if (is.getType() == Material.SKULL_ITEM && is.getItemMeta().hasLore() && is.getItemMeta().getLore().size() >= 2 && is.getItemMeta().getLore().get(1).contains("Punch to change texture")) {

                    List<Location> locations = (List<Location>) mailboxesConfig.getList(e.getPlayer().getUniqueId() + ".mailboxes", new LinkedList<Location>());
                    locations.add(e.getBlock().getLocation());
                    mailboxesConfig.set(e.getPlayer().getUniqueId() + ".mailboxes", locations);

                    List<String> players = (List<String>) mailboxesConfig.getList("players", new LinkedList<String>());
                    if (!players.contains(e.getPlayer().getUniqueId().toString())) {
                        players.add(e.getPlayer().getUniqueId().toString());
                    }
                    mailboxesConfig.set("players", players);

                    try {
                        mailboxesConfig.save(mailboxesFile);
                        e.getPlayer().sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.mailboxPlaced", "Mailbox placed."));
                    } catch (Exception ex) {
                        e.getPlayer().sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.failedToPlaceMailbox", "Failed to place mailbox."));
                        if (KoM._instance.getConfig().getBoolean("verbose_errors", false)) {
                            ex.printStackTrace();
                        }
                    }
                }

            }
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Detect mailbox breaking">
        @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.NORMAL)
        public void onBlockBreak(org.bukkit.event.block.BlockBreakEvent e) {
            List<String> players = (List<String>) mailboxesConfig.getList("players", new LinkedList<String>());
            for (String p : players) {
                List<Location> locations = (List<Location>) mailboxesConfig.getList(e.getPlayer().getUniqueId() + ".mailboxes", new LinkedList<Location>());
                for (Location loc : locations) {
                    if (e.getBlock().getLocation().equals(loc)) {

                        locations.remove(e.getBlock().getLocation());
                        mailboxesConfig.set(e.getPlayer().getUniqueId() + ".mailboxes", locations);

                        try {
                            mailboxesConfig.save(mailboxesFile);
                            e.setCancelled(true);
                            e.getBlock().setType(Material.AIR);
                            KoM._instance.getServer().dispatchCommand(KoM._instance.getServer().getConsoleSender(), "summon Item " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ() + " {Item:{id:minecraft:skull, Count:1, Damage: 3, tag:{display:{Name:\"§rMailbox\",Lore:[\"§r§7Blue\",\"§r§7Punch to change texture\"]},SkullOwner:{Id:\"" + mailboxIdBlue + "\",Name:\"ha1fBit\",Properties:{textures:[{Value:\"" + mailboxTextureBlue + "\"}]}}}}}");
                        } catch (Exception ex) {
                            e.getPlayer().sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.failedToRemoveMailbox", "Failed to remove mailbox."));
                            if (KoM._instance.getConfig().getBoolean("verbose_errors", false)) {
                                ex.printStackTrace();
                            }
                        }
                        return;
                    }
                }
            }
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Crafting">
        @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.NORMAL)
        public void onCraft(org.bukkit.event.inventory.CraftItemEvent e) {
            if (e.getRecipe().getResult().hasItemMeta() && e.getRecipe().getResult().getItemMeta().hasLore() && e.getRecipe().getResult().getItemMeta().getDisplayName().contains(stationeryMeta.getDisplayName())) { // Stationery
                if (!e.getWhoClicked().hasPermission("realmail.user.craft.stationary")) {
                    e.getWhoClicked().sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("noperm.craftStationary", "You do not have permission to craft stationery."));
                    e.setResult(Event.Result.DENY);
                }
            } else if (e.getRecipe().getResult().hasItemMeta() && e.getRecipe().getResult().getItemMeta().hasLore() && e.getRecipe().getResult().getItemMeta().getDisplayName().contains(mailboxRecipeMeta.getDisplayName())) { // Mailbox
                if (!e.getWhoClicked().hasPermission("realmail.user.craft.mailbox")) {
                    e.getWhoClicked().sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("noperm.craftMailbox", "You do not have permission to craft a mailbox."));
                    e.setResult(Event.Result.DENY);
                }
            }
        }
        //</editor-fold>
    }

    //<editor-fold defaultstate="collapsed" desc="Login notifications">
    public final class LoginListener implements org.bukkit.event.Listener {

        @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.MONITOR)
        public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent e) {
            if (mailboxesConfig.getList("players", new LinkedList<String>()).contains(e.getPlayer().getUniqueId().toString())) {
                if (KoM._instance.getConfig().getBoolean("login_notification")) {
                    if (mailboxesConfig.getBoolean(e.getPlayer().getUniqueId() + ".unread", false)) {
                        try {
                            Bukkit.getScheduler().runTaskLater(KoM._instance, new LoginRunnable(e), 20 * 10);
                        } catch (IllegalArgumentException ex) {
                            e.getPlayer().sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.gotMailLogin", "You've got mail! Check your mailbox."));
                        }
                    }
                }
            } else {
                List<String> knownPlayers = (List<String>) mailboxesConfig.getList("players", new LinkedList<String>());
                knownPlayers.add(e.getPlayer().getUniqueId().toString());
                mailboxesConfig.set("players", knownPlayers);
                try {
                    mailboxesConfig.save(mailboxesFile);
                } catch (IOException ex) {
                    KoM._instance.getLogger().log(Level.WARNING, "Failed to add {0} to the mail list.", e.getPlayer().getName());
                }
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Login runnable">
    public final class LoginRunnable implements Runnable {

        private org.bukkit.event.player.PlayerJoinEvent event;

        public LoginRunnable(org.bukkit.event.player.PlayerJoinEvent e) {
            this.event = e;
        }

        @Override
        public void run() {
            event.getPlayer().sendMessage(prefix + ChatColor.WHITE + languageConfig.getString("mail.gotMailLogin", "You've got mail! Check your mailbox."));
        }

    }
    //</editor-fold>

}
