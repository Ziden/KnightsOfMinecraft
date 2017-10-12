package nativelevel.Auras.Lista;

import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Auras.Aura;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Amor extends Aura {

    @Override
    public String getNome() {
        return "Amor";    
    }

    @Override
    public Material getIcone() {
        return Material.APPLE;
    }

    @Override
    public String frase() {
        return "@ se enche de amor !";
    }

    @Override
    public void tick(Player p, boolean newTick) {
        PlayEffect.play(VisualEffect.HEART, "loc:" + p.getLocation().getWorld().getName() + "," + p.getLocation().getBlockX() + "," + p.getLocation().getBlockY() + "," + p.getLocation().getBlockZ());
    }
    
   
}
