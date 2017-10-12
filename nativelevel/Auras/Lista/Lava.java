package nativelevel.Auras.Lista;

import me.fromgate.playeffect.PlayEffect;
import nativelevel.Auras.Aura;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Lava extends Aura {

    @Override
    public String getNome() {
        return "Lava";    
    }

    @Override
    public Material getIcone() {
        return Material.LAVA_BUCKET;
    }

    @Override
    public String frase() {
        return "@ começa pipocar de lava";
    }

    @Override
    public void tick(Player p, boolean newTick) {
        PlayEffect.play("lava", "loc:" + p.getLocation().getWorld().getName() + "," + p.getLocation().getBlockX() + "," + p.getLocation().getBlockY() + "," + p.getLocation().getBlockZ());
    }
    
   
}
