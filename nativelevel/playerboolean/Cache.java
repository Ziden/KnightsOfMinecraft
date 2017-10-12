package nativelevel.playerboolean;

import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author vntgasl
 */
public class Cache<Type> {

    private HashMap<Object, Type> cache = new HashMap<Object, Type>();

    public void set(Object u, Type obj) {
        cache.put(u, obj);
    }

    public boolean hasCache(Object u) {
        return cache.containsKey(u);
    }

    public int size() {
        return cache.size();
    }

    public void remove(Object u) {
        cache.remove(u);
    }

    public Collection<Type> getCached() {
        return cache.values();
    }

    public Type getCached(Object u) {
        if (cache.containsKey(u)) {
            return cache.get(u);
        } else {
            return null;
        }
    }

}
