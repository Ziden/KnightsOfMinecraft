package nativelevel.Listeners;

import nativelevel.KoM;

/*
import com.comphenix.protocol.Packets;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.ListeningWhitelist;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.injector.GamePhase;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import nativelevel.Language.LNG;
import nativelevel.Language.TRL;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Nosliw
 * 
 */

// commented as it bugged chat a little
public class ProtocolListener /* implements PacketListener */ {
    
    //@Override
    public Plugin getPlugin() {
        return KoM._instance;
    }

    
/*
    private Collection<Integer> serverPacketList, clientPacketList;
    private static final int CUSTOM_NAME_INDEX = 10; // Location of 'CustomName' within packet
    private static final int CUSTOM_STACK_INDEX = 2; // Location of 'ItemStack' within packet for ItemFrames

    public ProtocolListener() {

        serverPacketList = Arrays.asList(Packets.Server.MOB_SPAWN, Packets.Server.ENTITY_METADATA,
                Packets.Server.WINDOW_ITEMS, Packets.Server.SET_SLOT);
        clientPacketList = new ArrayList<>();
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        PacketContainer packet = event.getPacket();
        kPlayer player = KnightsOfMinecraft._instance.getPlayer(event.getPlayer());

        if (event.getPacketID() == Packets.Server.SET_SLOT) {
            translateStack(player.getLanguage(), packet.getItemModifier().read(0));
        } else if (event.getPacketID() == Packets.Server.WINDOW_ITEMS) {
            ItemStack[] elements = packet.getItemArrayModifier().read(0);

            for (int i = 0; i < elements.length; i++) {
                if (elements[i] != null) {
                    translateStack(player.getLanguage(), elements[i]);
                }
            }
        } else {
            final Entity entity = packet.getEntityModifier(event.getPlayer().getWorld()).read(0);
            if (entity instanceof LivingEntity) {
                final LivingEntity target = (LivingEntity) entity;
                final String name = TRL.translateOrNull(player.getLanguage(), target.getCustomName());

                if (name != null) {
                    event.setPacket(packet = packet.deepClone());

                    if (event.getPacketID() == Packets.Server.ENTITY_METADATA) {
                        WrappedDataWatcher watcher = new WrappedDataWatcher(packet.getWatchableCollectionModifier().read(0));

                        processDataWatcher(watcher, name);
                        packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());
                    } else
                        processDataWatcher(packet.getDataWatcherModifier().read(0), name);
                }
            } else if (entity instanceof ItemFrame) {
                if (event.getPacketID() == Packets.Server.ENTITY_METADATA) {
                    WrappedDataWatcher watcher = new WrappedDataWatcher(packet.getWatchableCollectionModifier().read(0));

                    if (watcher.getObject(CUSTOM_STACK_INDEX) != null)
                        watcher.setObject(CUSTOM_STACK_INDEX, translateStack(player.getLanguage(), (ItemStack) watcher.getObject(CUSTOM_STACK_INDEX)));
                }
            }
        }
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
    }

    @Override
    public ListeningWhitelist getSendingWhitelist() {
        return ListeningWhitelist.newBuilder().priority(ListenerPriority.HIGHEST).whitelist(serverPacketList).gamePhase(GamePhase.BOTH).options(new ListenerOptions[0]).build();
    }

    @Override
    public ListeningWhitelist getReceivingWhitelist() {
        return ListeningWhitelist.newBuilder().priority(ListenerPriority.HIGHEST).whitelist(clientPacketList).gamePhase(GamePhase.BOTH).options(new ListenerOptions[0]).build();
    }

    
    private void processDataWatcher(WrappedDataWatcher watcher, String name) {
        // debugWatcher(watcher);
        if (watcher.getObject(CUSTOM_NAME_INDEX) != null && watcher.getObject(CUSTOM_NAME_INDEX) instanceof String)
            watcher.setObject(CUSTOM_NAME_INDEX, name);
//        else {
//            System.out.println("Checking for String Variables failed (" + CUSTOM_NAME_INDEX + "), suggestions:");
//            for (int i = 0; i < 50; i++) {
//                if (watcher.getObject(i) != null && watcher.getObject(i) instanceof String)
//                    System.out.println(i + ") " + watcher.getObject(i).toString());
//            }
//        }
    }

    private void debugWatcher(WrappedDataWatcher watcher) {
        System.out.println("START! (" + (watcher.getObject(CUSTOM_NAME_INDEX) == null ? "NULL" : watcher.getObject(CUSTOM_NAME_INDEX).toString()) + ")");
        for (WrappedWatchableObject obj : watcher.getWatchableObjects()) {
            System.out.println("   -> Type: " + obj.getType().getSimpleName() + ", " + obj.getValue().toString());
        }
        System.out.println("END!");
    }

    private ItemStack translateStack(LNG language, ItemStack stack) {
        if (stack == null || !stack.hasItemMeta())
            return stack;

        ItemMeta meta = stack.getItemMeta();

        if (meta.hasDisplayName())
            meta.setDisplayName(TRL.translate(language, meta.getDisplayName()));

        if (meta.hasLore()) {
            List<String> lore = meta.getLore();
            for (int i = 0; i < lore.size(); i++) {
                lore.set(i, TRL.translate(language, lore.get(i)));
            }
            meta.setLore(lore);
        }

        stack.setItemMeta(meta);
        return stack;
    }
    */

}
