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
package nativelevel.Lang;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import nativelevel.KoM;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * An enum for requesting strings from the language file.
 *
 * @author gomeow
 */
public enum Lang {

    TITLE("title-name", "&4[&fAWSMPLUGIN&4]:"),
    PLAYER_IS_COOL("player-is-cool", "&f%p is cool."),
    INVALID_ARGS("invalid-args", "&cInvalid args!"),
    PLAYER_ONLY("player-only", "Sorry but that can only be run by a player!"),
    MUST_BE_NUMBER("must-be-number", "&cYou need to specify a number, not a word."),
    NO_PERMS("no-permissions", "&cYou don''t have permission for that!");
    public static YamlConfiguration LANG ;
    public static File LANG_FILE;
    private String path;
    private String def;

    /**
     * Lang enum constructor.
     *
     * @param path The string path.
     * @param start The default string.
     */
    Lang(String path, String start) {
        this.path = path;
        this.def = start;
    }

    /**
     * Set the {@code YamlConfiguration} to use.
     *
     * @param config The config to set.
     */
    public static void setFile(YamlConfiguration config) {
        LANG = config;
    }

    public static void loadLang() {
        File lang = new File(KoM._instance.getDataFolder(), "lang.yml");
        if (!lang.exists()) {
            
            try {
                
                KoM._instance.getDataFolder().mkdir();
                lang.createNewFile();
                
                //InputStream defConfigStream = KoM._instance.getResource("lang.yml");
                //if (defConfigStream != null) {
                //    YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                //    defConfig.save(lang);
                //    Lang.setFile(defConfig);
                //}
            } catch (IOException e) {
                e.printStackTrace(); // So they notice
                KoM._instance.log.severe("[Kom] Falha ao criar linguas.");
                KoM._instance.log.severe("[Kom] Desabilitando");
                //  NativeLevel.instanciaDoPlugin.setEnabled(false); // Without it loaded, we can't send them messages
            }
        }
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
        for (Lang item : Lang.values()) {
            if (conf.getString(item.getPath()) == null) {
                conf.set(item.getPath(), item.getDefault());
            }
        }
        Lang.setFile(conf);
        Lang.LANG = conf;
        Lang.LANG_FILE = lang;
        try {
            conf.save(Lang.getLangFile());
        } catch (IOException e) {
            KoM._instance.log.log(Level.WARNING, "PluginName: Failed to save lang.yml.");
            KoM._instance.log.log(Level.WARNING, "PluginName: Report this stack trace to <your name>.");
            e.printStackTrace();
        }

    }

    /**
     * Gets the lang.yml config.
     *
     * @return The lang.yml config.
     */
    public static YamlConfiguration getLang() {
        return LANG;
    }

    /**
     * Get the lang.yml file.
     *
     * @return The lang.yml file.
     */
    public static File getLangFile() {
        return LANG_FILE;
    }

    @Override
    public String toString() {
        if (this == TITLE) {
            return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def)) + " ";
        }
        return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def));
    }

    /**
     * Get the default value of the path.
     *
     * @return The default value of the path.
     */
    public String getDefault() {
        return this.def;
    }

    /**
     * Get the path to the string.
     *
     * @return The path to the string.
     */
    public String getPath() {
        return this.path;
    }
}
