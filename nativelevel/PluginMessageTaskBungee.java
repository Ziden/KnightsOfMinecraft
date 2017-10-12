package nativelevel;

import java.io.ByteArrayOutputStream;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author NeT32
 */
public class PluginMessageTaskBungee extends BukkitRunnable {

    private final ByteArrayOutputStream bytes;
    private final Player pSend;

    public PluginMessageTaskBungee(ByteArrayOutputStream bytes, Player pSend)
    {
        this.bytes = bytes;
        this.pSend = pSend;
    }

    @Override
    public void run()
    {
        if ((pSend != null) && (pSend.isOnline()))
        {
            pSend.sendPluginMessage(KoM._instance, "BungeeCord", bytes.toByteArray());
        }
    }
}
