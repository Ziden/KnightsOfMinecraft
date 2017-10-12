package nativelevel.phatloots.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

/**
 * The basis of a PhatLoots Event
 *
 * @author Codisimus
 */
public abstract class PhatLootsEvent extends Event implements Cancellable {
    private boolean cancelled;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean arg) {
        cancelled = arg;
    }
}
