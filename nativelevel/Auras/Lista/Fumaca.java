package nativelevel.Auras.Lista;

import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Auras.Aura;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Fumaca extends Aura {

    @Override
    public String getNome() {
        return "Fumacento";    
    }

    @Override
    public Material getIcone() {
        return Material.COAL;
    }

    @Override
    public String frase() {
        return "@ começa soltar fumaça !";
    }

    @Override
    public void tick(Player p, boolean newTick) {
        PlayEffect.play(VisualEffect.CLOUD, p.getLocation().add(0, 1, 0), "");
    }
    
   
}
