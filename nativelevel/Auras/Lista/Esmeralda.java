package nativelevel.Auras.Lista;

import me.fromgate.playeffect.PlayEffect;
import nativelevel.Auras.Aura;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Esmeralda extends Aura {

    @Override
    public String getNome() {
        return "Esmeralda";    
    }

    @Override
    public Material getIcone() {
        return Material.EMERALD;
    }

    @Override
    public String frase() {
        return "@ começa a brilhar esmeraldas";
    }

    @Override
    public void tick(Player p, boolean newTick) {
        p.playEffect(p.getLocation().add(0.0D, 1.0D, 0.0D), Effect.STEP_SOUND, Material.EMERALD.getId());
        //PlayEffect.play("blockcrack", "item:emerald_block loc:" + p.getLocation().getWorld().getName() + "," + p.getLocation().getBlockX() + "," + p.getLocation().getBlockY() + "," + p.getLocation().getBlockZ());
    }
    
   
}
