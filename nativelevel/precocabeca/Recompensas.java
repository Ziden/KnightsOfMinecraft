
package nativelevel.precocabeca;

import java.util.HashMap;
import java.util.UUID;
import nativelevel.KoM;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Recompensas {

    public double getRecompensa(UUID uid) {
        return 1;//KnightsOfMania.database.getPrecoCabeca(uid.toString());
    }

    public boolean isProcurado(UUID uid) {
        return false;// KnightsOfMania.database.getPrecoCabeca(uid.toString()) > 0;
    }

    public void botaRecompensa(UUID uid, double qt) {
        double jata;
        if (isProcurado(uid)) {
            jata = getRecompensa(uid);
            double recompensa = jata + qt;
            KoM.database.setPrecoCabeca(uid.toString(), (int)recompensa);
        } else {
            KoM.database.setPrecoCabeca(uid.toString(), (int)qt);

        }
    }

    public void removeRecompensa(UUID uid) {
        if (isProcurado(uid)) {
             KoM.database.setPrecoCabeca(uid.toString(), 0);
        }
    }

}
