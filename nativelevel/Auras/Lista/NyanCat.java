package nativelevel.Auras.Lista;

import me.fromgate.playeffect.PlayEffect;
import nativelevel.Auras.Aura;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class NyanCat extends Aura {

    @Override
    public String getNome() {
        return "NyanCat";    
    }

    @Override
    public Material getIcone() {
        return Material.REDSTONE;
    }

    @Override
    public String frase() {
        return "brilhos arco-iris começam a seguir @";
    }

    @Override
    public void tick(Player p, boolean newTick) {
        PlayEffect.play("reddust", "speed:100 draw:circle radius:1 loc:" + p.getLocation().getWorld().getName() + "," + p.getLocation().getBlockX() + "," + p.getLocation().getBlockY() + "," + p.getLocation().getBlockZ());
    }
    
   
}
