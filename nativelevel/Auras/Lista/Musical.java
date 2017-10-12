package nativelevel.Auras.Lista;

import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Auras.Aura;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Musical extends Aura {

    @Override
    public String getNome() {
        return "Musical";    
    }

    @Override
    public Material getIcone() {
        return Material.APPLE;
    }

    @Override
    public String frase() {
        return "@ começa tocar uma musica !";
    }

    @Override
    public void tick(Player p, boolean newTick) {
        PlayEffect.play(VisualEffect.NOTE, p.getLocation().add(0, 1.8, 0), "");
    }
    
   
}
