package nativelevel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Date;

/**
 *
 * @author NeT32
 * 
 */

public class AutoUpdate {

    public static String FilePluginOrigem;
    public static String FilePluginDestino;

    public static void CheckUpdade(String ArquivoJar) {
        File forigem = new File(KoM._instance.getDataFolder(), ArquivoJar);
        AutoUpdate.FilePluginOrigem = forigem.getAbsolutePath();
        File fdestino = new File(KoM._instance.getDataFolder().getParent(), ArquivoJar);
        AutoUpdate.FilePluginDestino = fdestino.getAbsolutePath();
        try {
            AutoUpdate.copy(forigem, fdestino, true);
            forigem.delete();
        } catch (IOException ex) {
            System.out.println("Updade não efetuado!");
        }
        System.out.println(AutoUpdate.FilePluginOrigem);
        System.out.println(AutoUpdate.FilePluginDestino);
    }

    /**
     * Copia arquivos de um local para o outro
     *
     * @param origem - Arquivo de origem
     * @param destino - Arquivo de destino
     * @param overwrite - Confirmação para sobrescrever os arquivos
     * @throws IOException
     */
    public static void copy(File origem, File destino, boolean overwrite) throws IOException {
        Date date = new Date();
        if (destino.exists() && !overwrite) {
            System.err.println(destino.getName() + " já existe, ignorando...");
            return;
        }
        FileInputStream fisOrigem = new FileInputStream(origem);
        FileOutputStream fisDestino = new FileOutputStream(destino);
        FileChannel fcOrigem = fisOrigem.getChannel();
        FileChannel fcDestino = fisDestino.getChannel();
        fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);
        fisOrigem.close();
        fisDestino.close();
        Long time = new Date().getTime() - date.getTime();
        System.out.println("Efetuando Updade: " + time);
    }

}