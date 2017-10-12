/*     */ package nativelevel.anuncios;
/*     */
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Random;
import nativelevel.KoM;
/*     */ import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.PluginCommand;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */
/*     */ public class Announcer /*     */ {
    /*     */ private static PluginDescriptionFile pdfFile;
    /*  16 */ private static final String DIR = "plugins" + File.separator + "Kom" + File.separator;
    /*     */ private static final String CONFIG_FILE = "anuncios.yml";
    /*     */ private static YamlConfiguration Settings;
    /*     */ private static String Tag;
    /*     */ private static int Interval;
    public static Announcer instancia;
    /*  21 */ private static int taskId = -1;
    private static int counter = 0;
    /*     */ public boolean isScheduling;
    /*     */ public boolean isRandom;
    /*     */ public boolean InSeconds;
    /*     */ public boolean permission;
    /*     */ public boolean toGroups;
    /*     */ private static List<String> strings;
    /*     */ private static List<String> Groups;
    /*     */ protected AnnouncerPerm perm;
    /*     */
    /*     */ public Announcer() /*     */ {
        /*  22 */ this.isScheduling = false;
        this.InSeconds = false;
        /*     */
        /*  24 */ this.perm = null;
        /*     */    }
    /*     *//*     *//*     *//*     *//*     *//*     *//*     *//*     */
    /*     */ public void onEnable() {
        instancia = this;
        /*  28 */ pdfFile = KoM._instance.getDescription();
        /*  29 */ File fDir = new File(DIR);
        /*  30 */ if (!fDir.exists()) /*  31 */ {
            fDir.mkdir();
        }
        /*     */ try {
            /*  33 */ File configFile = new File(DIR + "anuncios.yml");
            /*  34 */ if (!configFile.exists()) {
                /*  35 */ configFile.getParentFile().mkdirs();
                /*  36 */ AnnouncerUtils.copy(KoM._instance.getResource("anuncios.yml"), configFile);
                /*     */            }
            /*     */        } catch (Exception e) {
            /*  39 */ AnnouncerLog.severe("Failed to copy default config!", e);
            /*     */        }
        /*  41 */ loadSettings();
        /*  42 */ this.perm = new AnnouncerPerm(this);
        /*     */
        /*  44 */ if (this.permission) /*  45 */ {
            this.perm.enablePermissions();
        } /*     */ else {
            /*  47 */ AnnouncerLog.warning("No permission system enabled!");
            /*     */        }
        /*     */
        /*     */
        /*  52 */ AnnouncerLog.info("Settings Loaded (" + strings.size() + " announces).");
        /*  53 */ this.isScheduling = scheduleOn(null);
        /*  54 */ AnnouncerLog.info("v" + pdfFile.getVersion() + " is enabled!");
        /*     */    }
    /*     */
    /*     */ public void onDisable() /*     */ {
        /*  60 */ scheduleOff(true, null);
        /*  61 */ AnnouncerLog.info("v" + pdfFile.getVersion() + " is disabled!.");
        /*     */    }
    /*     *//*     *//*     *//*     *//*     *//*     *//*     *//*     */
    /*     */ public void scheduleOff(boolean Disabling, CommandSender sender) {
        /*  65 */ if (this.isScheduling) {
            /*  66 */ KoM._instance.getServer().getScheduler().cancelTask(taskId);
            /*  67 */ if (sender != null) {
                sender.sendMessage(ChatColor.DARK_GREEN + "Scheduling finished!");
            }
            /*  68 */ AnnouncerLog.info("Scheduling finished!");
            /*  69 */ this.isScheduling = false;
            /*     */        } /*  71 */ else if (!Disabling) {
            /*  72 */ if (sender != null) {
                sender.sendMessage(ChatColor.DARK_RED + "No schedule running!");
            }
            /*  73 */ AnnouncerLog.info("No schedule running!");
            /*     */        }
        /*     */    }
    /*     *//*     *//*     *//*     *//*     *//*     *//*     *//*     */
    /*     */ public boolean scheduleOn(CommandSender sender) /*     */ {
        /*  79 */ if (!this.isScheduling) {
            /*  80 */ if (strings.size() > 0) {
                /*  81 */ int TimeToTicks = this.InSeconds ? 20 : 1200;
                /*  82 */ taskId = KoM._instance.getServer().getScheduler().scheduleAsyncRepeatingTask(KoM._instance, new printAnnounce(), Interval * TimeToTicks, Interval * TimeToTicks);
                /*  83 */ if (taskId == -1) {
                    /*  84 */ if (sender != null) {
                        sender.sendMessage(ChatColor.DARK_RED + "Scheduling failed!");
                    }
                    /*  85 */ AnnouncerLog.warning("Scheduling failed!");
                    /*  86 */ return false;
                    /*     */                }
                /*  88 */ counter = 0;
                /*  89 */ if (sender != null) {
                    sender.sendMessage(ChatColor.DARK_GREEN + "Scheduled every " + Interval + (this.InSeconds ? " seconds!" : " minutes!"));
                }
                /*  90 */ AnnouncerLog.info("Scheduled every " + Interval + (this.InSeconds ? " seconds!" : " minutes!"));
                /*  91 */ return true;
                /*     */            }
            /*     */
            /*  94 */ if (sender != null) {
                sender.sendMessage(ChatColor.DARK_RED + "Scheduling failed! There are no announcements to do.");
            }
            /*  95 */ AnnouncerLog.warning("Scheduling failed! There are no announcements to do.");
            /*  96 */ return false;
            /*     */        }
        /*     */
        /*  99 */ if (sender != null) {
            sender.sendMessage(ChatColor.DARK_RED + "Scheduler already running.");
        }
        /* 100 */ AnnouncerLog.info("Scheduler already running.");
        /* 101 */ return true;
        /*     */    }
    /*     */
    /*     */ public void scheduleRestart(CommandSender sender) /*     */ {
        /* 106 */ if (this.isScheduling) {
            /* 107 */ scheduleOff(false, null);
            /* 108 */ loadSettings();
            /* 109 */ sender.sendMessage(ChatColor.DARK_GREEN + "Settings Loaded (" + strings.size() + " announces).");
            /* 110 */ this.isScheduling = scheduleOn(sender);
            /*     */        } else {
            /* 112 */ sender.sendMessage(ChatColor.DARK_RED + "No schedule running!");
            /*     */        }
        /*     */    }
    /*     */
    /*     */ public void setInterval(String[] args, CommandSender sender) {
        /* 117 */ if (args.length == 2) /*     */ {
            try {
                /* 119 */ int interval = Integer.parseInt(args[1], 10);
                /* 120 */ Settings.set("Settings.Interval", Integer.valueOf(interval));
                /* 121 */ saveSettings();
                /* 122 */ sender.sendMessage(ChatColor.DARK_GREEN + "Interval changed successfully to " + args[1] + (this.InSeconds ? " seconds." : " minutes."));
                /* 123 */ if (this.isScheduling) /* 124 */ {
                    scheduleRestart(sender);
                }
                /*     */            } catch (NumberFormatException err) {
                /* 126 */ sender.sendMessage(ChatColor.DARK_RED + "Error! Usage: /announcer interval 5");
                /*     */            }
        } /*     */ else /* 129 */ {
            sender.sendMessage(ChatColor.DARK_RED + "Error! Usage: /announcer interval 5");
        }
        /*     */    }
    /*     */
    /*     */ public void setRandom(String[] args, CommandSender sender) /*     */ {
        /* 134 */ if (args.length == 2) {
            /* 135 */ if (args[1].equals("on")) {
                /* 136 */ Settings.set("Settings.Random", Boolean.valueOf(true));
                /* 137 */ saveSettings();
                /* 138 */ sender.sendMessage(ChatColor.DARK_GREEN + "Changed to random transition.");
                /* 139 */ if (this.isScheduling) /* 140 */ {
                    scheduleRestart(sender);
                }
                /* 141 */            } else if (args[1].equals("off")) {
                /* 142 */ Settings.set("Settings.Random", Boolean.valueOf(false));
                /* 143 */ saveSettings();
                /* 144 */ sender.sendMessage(ChatColor.DARK_GREEN + "Changed to consecutive transition.");
                /* 145 */ if (this.isScheduling) /* 146 */ {
                    scheduleRestart(sender);
                }
                /*     */            } else {
                /* 148 */ sender.sendMessage(ChatColor.DARK_RED + "Error! Usage: /announcer random off");
                /*     */            }
            /*     */        } /* 151 */ else {
            sender.sendMessage(ChatColor.DARK_RED + "Error! Usage: /announcer random off");
        }
        /*     */    }
    /*     */
    /*     */ public void addAnnounce(String[] args, CommandSender sender) /*     */ {
        /* 156 */ if (args.length > 1) {
            /* 157 */ String com = StringUtils.join(args, " ", 1, args.length);
            /* 158 */ strings.add(com);
            /* 159 */ Settings.set("Announcer.Strings", strings);
            /* 160 */ saveSettings();
            /* 161 */ sender.sendMessage(ChatColor.DARK_GREEN + "New announce added!");
            /*     */        } else {
            /* 163 */ sender.sendMessage(ChatColor.DARK_RED + "Error! Usage: /announcer add [announce here]");
            /*     */        }
        /*     */    }
    /*     */
    /*     */ public void listAnnounces(CommandSender sender) /*     */ {
        /* 169 */ sender.sendMessage(ChatColor.DARK_GREEN + "List of announces with ids: (Total: " + strings.size() + ")");
        /* 170 */ int i = 0;
        int j = 0;
        /* 171 */ for (String announce : strings) {
            /* 172 */ j++;
            /* 173 */ for (String line : announce.split("&NEW_LINE;")) {
                /* 174 */ i++;
                /* 175 */ if (i == 1) /* 176 */ {
                    sender.sendMessage(ChatColor.GOLD + "[" + j + "] " + ChatColor.RESET + AnnouncerUtils.colorize(line));
                } /*     */ else /* 178 */ {
                    sender.sendMessage(AnnouncerUtils.colorize(line));
                }
                /*     */            }
            /* 180 */ i = 0;
            /*     */        }
        /*     */    }
    /*     */
    /*     */ public void removeAnnounce(String[] args, CommandSender sender) {
        /* 185 */ if (args.length == 2) /*     */ {
            try {
                /* 187 */ int announceid = Integer.parseInt(args[1]);
                /* 188 */ if ((announceid < 1) || (announceid > strings.size())) {
                    /* 189 */ sender.sendMessage(ChatColor.DARK_RED + "Error! Usage: /announcer remove [announce id]");
                    /*     */                } else {
                    /* 191 */ strings.remove(announceid - 1);
                    /* 192 */ Settings.set("Announcer.Strings", strings);
                    /* 193 */ saveSettings();
                    /* 194 */ sender.sendMessage(ChatColor.DARK_GREEN + "Announce deleted!");
                    /* 195 */ if (this.isScheduling) /* 196 */ {
                        scheduleRestart(sender);
                    }
                    /*     */                }
                /*     */            } catch (NumberFormatException e) {
                /* 199 */ sender.sendMessage(ChatColor.DARK_RED + "Error! Usage: /announcer remove [announce id]");
                /*     */            }
        } /*     */ else /* 202 */ {
            sender.sendMessage(ChatColor.DARK_RED + "Error! Usage: /announcer remove [announce id]");
        }
        /*     */    }
    /*     */
    /*     */ public void announcerHelp(CommandSender sender) /*     */ {
        /* 208 */ String or = ChatColor.WHITE + " | ";
        /* 209 */ String auctionStatusColor = ChatColor.DARK_GREEN.toString();
        /* 210 */ String helpMainColor = ChatColor.GOLD.toString();
        /* 211 */ String helpCommandColor = ChatColor.AQUA.toString();
        /* 212 */ String helpObligatoryColor = ChatColor.DARK_RED.toString();
        /* 213 */ sender.sendMessage(helpMainColor + " -----[ " + auctionStatusColor + "Help for AutoAnnouncer" + helpMainColor + " ]----- ");
        /* 214 */ sender.sendMessage(helpCommandColor + "/announcer help" + or + helpCommandColor + "?" + helpMainColor + " - Show this message.");
        /* 215 */ sender.sendMessage(helpCommandColor + "/announcer on" + helpMainColor + " - Start AutoAnnouncer.");
        /* 216 */ sender.sendMessage(helpCommandColor + "/announcer off" + helpMainColor + " - Stop AutoAnnouncer.");
        /* 217 */ sender.sendMessage(helpCommandColor + "/announcer restart" + helpMainColor + " - Restart AutoAnnouncer.");
        /* 218 */ sender.sendMessage(helpCommandColor + "/announcer interval" + or + helpCommandColor + "i" + helpObligatoryColor + " <minutes|seconds>" + helpMainColor + " - Set the interval time.");
        /* 219 */ sender.sendMessage(helpCommandColor + "/announcer random" + or + helpCommandColor + "r" + helpObligatoryColor + " <on|off>" + helpMainColor + " - Set random or consecutive.");
        /*     */    }
    /*     */
    /*     */ private void loadSettings() {
        /* 223 */ Settings = YamlConfiguration.loadConfiguration(new File(DIR + "anuncios.yml"));
        /* 224 */ Interval = Settings.getInt("Settings.Interval", 5);
        /* 225 */ this.InSeconds = Settings.getBoolean("Settings.InSeconds", false);
        /* 226 */ this.isRandom = Settings.getBoolean("Settings.Random", false);
        /* 227 */ this.permission = Settings.getBoolean("Settings.Permission", true);
        /* 228 */ strings = Settings.getStringList("Announcer.Strings");
        /* 229 */ Tag = AnnouncerUtils.colorize(Settings.getString("Announcer.Tag", "&GOLD;[AutoAnnouncer]"));
        /* 230 */ this.toGroups = Settings.getBoolean("Announcer.ToGroups", true);
        /* 231 */ Groups = Settings.getStringList("Announcer.Groups");
        /*     */    }
    /*     */ private void saveSettings() {
        /*     */ try {
            /* 235 */ Settings.save(new File(DIR + "anuncios.yml"));
            /*     */        } catch (IOException e) {
            /* 237 */ AnnouncerLog.warning("Failed to save config!");
            /*     */        }
        /*     */    }
    /*     */ class printAnnounce implements Runnable {
        /*     */ printAnnounce() {
            /*     */        }
        /*     */ public void run() {
            /* 243 */ String announce = "";
            /*     */
            /* 246 */ Random randomise = new Random();
            /* 247 */ int selection = randomise.nextInt(Announcer.strings.size());
            /* 248 */ announce = (String) Announcer.strings.get(selection);
            /* 256 */ if ((Announcer.this.permission) && (Announcer.this.toGroups)) {
                /* 258 */ for (Player p : Bukkit.getServer().getOnlinePlayers()) /* 259 */ {
                    for (String group : Announcer.Groups) /* 260 */ {
                        if (Announcer.this.perm.group(p, group)) {
                            /* 261 */ for (String line : announce.split("&NEW_LINE;")) /* 262 */ {
                                p.sendMessage(Announcer.Tag + " " + AnnouncerUtils.colorize(line));
                            }
                            /* 263 */ break;
                            /*     */                        }
                    }
                }
                /*     */            } /*     */ else /*     */ {
                /* 268 */ for (String line : announce.split("&NEW_LINE;")) /* 269 */ {
                    KoM._instance.getServer().broadcastMessage(Announcer.Tag + " " + AnnouncerUtils.colorize(line));
                }
                /*     */            }
            /*     */        }
        /*     */    }
    /*     */ }
