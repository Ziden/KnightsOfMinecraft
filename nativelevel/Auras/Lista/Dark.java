package nativelevel.Auras.Lista;

import me.fromgate.playeffect.PlayEffect;
import nativelevel.Auras.Aura;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Dark extends Aura {

    @Override
    public String getNome() {
        return "Sombria";    
    }

    @Override
    public Material getIcone() {
        return Material.OBSIDIAN;
    }

    @Override
    public String frase() {
        return "@ começa a se enevoar em sombras";
    }

    @Override
    public void tick(Player p, boolean newTick) {
        PlayEffect.play("mobspell","draw:circle radius:1 loc:" + p.getLocation().getWorld().getName() + "," + p.getLocation().getBlockX() + "," + p.getLocation().getBlockY() + "," + p.getLocation().getBlockZ());
    }
    
   // VO COPIAR UNS EFEITOS MERMO =X
}
