package nativelevel.Auras.Lista;

import me.fromgate.playeffect.PlayEffect;
import nativelevel.Auras.Aura;
import nativelevel.Jobs;
import nativelevel.utils.Particles;
import nativelevel.utils.UtilParticles;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class LordGelado extends Aura {

    @Override
    public String getNome() {
        return "Gelo";
    }

    @Override
    public Material getIcone() {
        return Material.ICE;
    }

    @Override
    public String frase() {
        return "@ emite uma aura geladona";
    }

    @Override
    public void tick(Player p, boolean newTick) {
        for (int i = 0; i < 6; i++) {
            Location location = p.getLocation();
            double inc = (2 * Math.PI) / 100;
            double angle = step * inc + stepY + i;
            Vector v = new Vector();
            v.setX(Math.cos(angle) * radius);
            v.setZ(Math.sin(angle) * radius);
            UtilParticles.display(Particles.SNOW_SHOVEL, location.add(v).add(0, stepY, 0));
            location.subtract(v).subtract(0, stepY, 0);
            if (stepY < 3) {
                if (newTick) {
                    radius -= 0.022;
                    stepY += 0.045;
                }
            } else {
                if (newTick) {
                    stepY = 0;
                    step = 0;
                    radius = 1.5f;
                }
                //SoundUtil.playSound(getPlayer(), Sounds.DIG_SNOW, .5f, 1.5f);
                UtilParticles.display(Particles.SNOW_SHOVEL, location.clone().add(0, 3, 0), 48, 0.3f);
            }
        }
    }

    int step = 0;
    float stepY = 0;
    float radius = 1.5f;

}
