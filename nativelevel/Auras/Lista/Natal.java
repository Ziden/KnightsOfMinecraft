package nativelevel.Auras.Lista;

import me.fromgate.playeffect.PlayEffect;
import nativelevel.Auras.Aura;
import nativelevel.Jobs;
import nativelevel.utils.Particles;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Natal extends Aura {

    @Override
    public String getNome() {
        return "Natal";
    }

    @Override
    public Material getIcone() {
        return Material.SAPLING;
    }

    @Override
    public String frase() {
        return "@ bota seu gorrinho de natal";
    }

    @Override
    public void tick(Player p, boolean newTick) {
        if (step > 360) {
            step = 0;
        }
        Location center = p.getEyeLocation().add(0, 0.6, 0);
        double inc = (2 * Math.PI) / 20;
        double angle = step * inc;
        double x = Math.cos(angle) * 1.1f;
        double z = Math.sin(angle) * 1.1f;
        center.add(x, 0, z);
        for (int i = 0; i < 15; i++) {
            Particles.ITEM_CRACK.display(new Particles.ItemData(Material.INK_SACK, getRandomColor()), 0.2f, 0.2f, 0.2f, 0, 1, center, 128);
        }
        if(newTick)
            step++;
    }
    private int step;

    public static byte getRandomColor() {
        float f = Jobs.rnd.nextFloat();
        if (f > 0.98) {
            return (byte) 2;
        } else if (f > 0.49) {
            return (byte) 1;
        } else {
            return (byte) 15;
        }
    }

   // VO COPIAR UNS EFEITOS MERMO =X
}
