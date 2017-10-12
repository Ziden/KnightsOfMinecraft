package nativelevel.phatloots.regions;

import java.util.List;
import org.bukkit.Location;

/**
 *
 * @author Codisimus
 */
public interface RegionHook {
    public String getPluginName();
    public List<String> getRegionNames(Location loc);
}
