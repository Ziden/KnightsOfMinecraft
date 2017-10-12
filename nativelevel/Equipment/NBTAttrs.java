/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Equipment;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.server.v1_12_R1.Item;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.MerchantRecipe;


/**
 *
 * @author User
 *
 */
public class NBTAttrs {

    private Field cC;

    public static int getPassedTicks(Player player) {
        LivingEntity livingEntity = (LivingEntity) player;
        CraftLivingEntity craftLivingEntity = (CraftLivingEntity) livingEntity;
        return craftLivingEntity.getHandle().ad;
    }

    public static void main(String[] args) {
        System.out.println(30 / 40D);
        System.out.println(0.5D + (30 / 40D));
        System.out.println(0.5 + (30 / 40D));
    }

    public static double converteMeuAtkSpeedPraGenericAttackSpeed(Player p, int attackSpeed) {

        //T = 1 / attackSpeed * 20
        double atkSpeedBukkit = (attackSpeed / 50D);
        if (atkSpeedBukkit == 0) {
            atkSpeedBukkit = 0.5;
        }
        p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(atkSpeedBukkit);
        return atkSpeedBukkit;
        // 2k - 25*20 = 1500 MS cooldown
        // 1500 / 1000 * 20 = 1.5 * 20 = 30 ticks
        // meuTempoTicks = 30; // 1.5 sec

        // 20 = 
    }

    public void AttributeRemover() {
        /*
         try {
         this.cC = EntityPlayer.class.getDeclaredField("containerCounter");
         this.cC.setAccessible(true);
         } catch (NoSuchFieldException | SecurityException e) {
         e.printStackTrace();
         }
         Set<PacketType> packets = new HashSet();
         packets.add(PacketType.Play.Server.SET_SLOT);
         packets.add(PacketType.Play.Server.WINDOW_ITEMS);
         packets.add(PacketType.Play.Server.CUSTOM_PAYLOAD);
         ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(RPG.i, packets) {
         private Object AttributeHider;

         public void onPacketSending(PacketEvent event) {
         PacketContainer packet = event.getPacket();
         PacketType type = packet.getType();
         if (type == PacketType.Play.Server.WINDOW_ITEMS) {
         try {
         org.bukkit.inventory.ItemStack[] read = (org.bukkit.inventory.ItemStack[]) packet.getItemArrayModifier().read(0);
         for (int i = 0; i < read.length; i++) {
         read[i] = removeAttributes1(read[i]);
         }
         packet.getItemArrayModifier().write(0, read);
         } catch (FieldAccessException e) {
         e.printStackTrace();
         }
         } else if (type == PacketType.Play.Server.CUSTOM_PAYLOAD) {
         if (!((String) packet.getStrings().read(0)).equalsIgnoreCase("MC|TrList")) {
         return;
         }
         try {
         EntityPlayer p = ((CraftPlayer) event.getPlayer()).getHandle();
         ContainerMerchant cM = (ContainerMerchant) p.activeContainer;
         Field fieldMerchant = cM.getClass().getDeclaredField("merchant");
         fieldMerchant.setAccessible(true);
         IMerchant imerchant = (IMerchant) fieldMerchant.get(cM);
         MerchantRecipeList merchantrecipelist = imerchant.getOffers(p);
         MerchantRecipeList nlist = new MerchantRecipeList();
         /*
         for (Object orecipe : merchantrecipelist) {
         MerchantRecipe recipe = (MerchantRecipe) orecipe;
         int uses = recipe.k().getInt("uses");
         int maxUses = recipe.k().getInt("maxUses");
         MerchantRecipe nrecipe = new MerchantRecipe(AttributeHider.removeAttributes2(recipe.getResult()), AttributeHider.removeAttributes(recipe.)), AttributeHider.removeAttributes(recipe.getBuyItem3()));
         nrecipe.a(maxUses - 7);
         for (int i = 0; i < uses; i++) {
         nrecipe.f();
         }
         nlist.add(nrecipe);
         }
                       

         PacketDataSerializer packetdataserializer = new PacketDataSerializer(Unpooled.buffer());
         packetdataserializer.writeInt(cC.getInt(p));
         nlist.a(packetdataserializer);
         byte[] b = packetdataserializer.array();
         packet.getByteArrays().write(0, b);
         packet.getIntegers().write(0, Integer.valueOf(b.length));
         } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | FieldAccessException e) {
         e.printStackTrace();
         }
         } else {
         try {
         packet.getItemModifier().write(0, NBTAttrs.removeAttributes1((org.bukkit.inventory.ItemStack) packet.getItemModifier().read(0)));
         } catch (FieldAccessException e) {
         e.printStackTrace();
         }
         }
         }
         });
         */

    }

    public static void debug(org.bukkit.inventory.ItemStack i) {
        org.bukkit.inventory.ItemStack item = i.clone();
        net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        if (nmsStack.hasTag()) {
            System.out.println("TAG: " + nmsStack.getTag().toString());
            for (String s : nmsStack.getTag().c()) {
                System.out.println("----KEY: " + s);
            }
        } else {
            System.out.println("Nao tem tag");
        }
    }

    public static org.bukkit.inventory.ItemStack removeAttr(org.bukkit.inventory.ItemStack i) {
        if (i == null) {
            return i;
        }
        if (i.getType() == Material.BOOK_AND_QUILL) {
            return i;
        }
        org.bukkit.inventory.ItemStack item = i.clone();
        net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag;
        if (!nmsStack.hasTag()) {
            tag = new NBTTagCompound();
            nmsStack.setTag(tag);
        } else {
            tag = nmsStack.getTag();
        }
        NBTTagList am = new NBTTagList();
        NBTTagCompound damage = new NBTTagCompound();

        /*
         NBTTagCompound speed = new NBTTagCompound();
         speed.set("AttributeName", new NBTTagString("generic.attackSpeed"));
         speed.set("Name", new NBTTagString("generic.attackSpeed"));
         speed.set("Amount", new NBTTagDouble(99));
         speed.set("Operation", new NBTTagInt(0));
         speed.set("UUIDLeast", new NBTTagInt(894654));
         speed.set("UUIDMost", new NBTTagInt(2872));
         speed.set("Slot", new NBTTagString("mainhand"));
         am.add(speed);
         */
        tag.set("AttributeModifiers", am);
        nmsStack.setTag(tag);
        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    public static org.bukkit.inventory.ItemStack removeAttributes1(org.bukkit.inventory.ItemStack i) {
        if (i == null) {
            return i;
        }
        if (i.getType() == Material.BOOK_AND_QUILL) {
            return i;
        }
        org.bukkit.inventory.ItemStack item = i.clone();
        net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag;
        if (!nmsStack.hasTag()) {
            tag = new NBTTagCompound();
            nmsStack.setTag(tag);
        } else {
            tag = nmsStack.getTag();
        }
        NBTTagList am = new NBTTagList();
        tag.set("AttributeModifiers", am);
        nmsStack.setTag(tag);
        return CraftItemStack.asCraftMirror(nmsStack);
    }

    public static net.minecraft.server.v1_12_R1.ItemStack removeAttributes2(net.minecraft.server.v1_12_R1.ItemStack i) {
        if (i == null) {
            return i;
        }
        if (Item.getId(i.getItem()) == 386) {
            return i;
        }
        net.minecraft.server.v1_12_R1.ItemStack item = i.cloneItemStack();
        NBTTagCompound tag;
        if (!item.hasTag()) {
            tag = new NBTTagCompound();
            item.setTag(tag);
        } else {
            tag = item.getTag();
        }
        NBTTagList am = new NBTTagList();
        tag.set("AttributeModifiers", am);
        item.setTag(tag);
        return item;
    }

}
