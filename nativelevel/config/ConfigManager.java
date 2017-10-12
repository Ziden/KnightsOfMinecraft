/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.config;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author Gabriel
 */
public class ConfigManager {

    private File dbFile;
    private FileConfiguration config = new YamlConfiguration();

    public ConfigManager(String arquivo) throws IOException, FileNotFoundException, InvalidConfigurationException {
        dbFile = new File(arquivo);
        if (!dbFile.exists()) {
            dbFile.getParentFile().mkdirs();
            dbFile.createNewFile();
        }
        config.load(dbFile);
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public void SaveConfig(){
        try {
            this.config.save(dbFile);
        } catch (IOException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
