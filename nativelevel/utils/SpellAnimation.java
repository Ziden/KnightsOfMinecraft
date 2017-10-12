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

import nativelevel.KoM;
import org.bukkit.Bukkit;

// THIS CLASS WAS TAKEN FROM MAGICSPELLS BY NISOVIN
public abstract class SpellAnimation
        implements Runnable {

    private int taskId;
    private long delay;
    private long interval;
    private int tick;

    public SpellAnimation(long interval) {
        this(0L, interval, false);
    }

    public SpellAnimation(long interval, boolean autoStart) {
        this(0L, interval, autoStart);
    }

    public SpellAnimation(long delay, long interval) {
        this(delay, interval, false);
    }

    public SpellAnimation(long delay, long interval, boolean autoStart) {
        this.delay = delay;
        this.interval = interval;
        this.tick = -1;
        if (autoStart) {
            play();
        }
    }

    public void play() {
        this.taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(KoM._instance, this, this.delay, this.interval);
    }

    protected void stop() {
        Bukkit.getServer().getScheduler().cancelTask(this.taskId);
    }

    protected abstract void onTick(int paramInt);

    public final void run() {
        onTick(++this.tick);
    }
}
