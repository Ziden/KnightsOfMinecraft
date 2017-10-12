package nativelevel.Auras.Lista;

import me.fromgate.playeffect.PlayEffect;
import nativelevel.Auras.Aura;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Tontura extends Aura {

    @Override
    public String getNome() {
        return "Brilhos";    
    }

    @Override
    public Material getIcone() {
        return Material.GHAST_TEAR;
    }

    @Override
    public String frase() {
        return "@ começa a brilhar como um astro";
    }

    @Override
    public void tick(Player p, boolean newTick) {
        PlayEffect.play("instantspell", "loc:" + p.getLocation().getWorld().getName() + "," + p.getLocation().getBlockX() + "," + p.getLocation().getBlockY() + "," + p.getLocation().getBlockZ());
    }
    
   
}
