/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel
 */
public class ConfigProperties {

    private File dbFile;
    Properties prop = new Properties();

    public ConfigProperties(String arquivo) throws IOException, FileNotFoundException {
        dbFile = new File(arquivo);
        if (!dbFile.exists()) {
            dbFile.getParentFile().mkdirs();
            prop.store(new FileOutputStream(arquivo), null);
        } else {
            prop.load(new FileInputStream(arquivo));
        }
    }

    public void saveConfig() {
        try {
            prop.store(new FileOutputStream(dbFile.getAbsolutePath()), null);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConfigProperties.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConfigProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Properties getConfig() {
        return this.prop;
    }
}
