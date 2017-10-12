package nativelevel.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

public class JsonMessage {

    private String msg;

    public JsonMessage() {
        this.msg = "[{\"text\":\"\",\"extra\":[{\"text\": \"\"}";
    }

    private static Class<?> getNmsClass(String nmsClassName)
            throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + "." + nmsClassName);
    }

    private static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().substring(23);
    }

    public void send(Player p) {
        String version = getServerVersion();
        String nmsClass = (version.startsWith("v1_8_R") ? "IChatBaseComponent$" : "") + "ChatSerializer";
        try {
            Object comp = getNmsClass(nmsClass).getMethod("a", new Class[]{String.class}).invoke(null, new Object[]{this.msg + "]}]"});
            Object packet = getNmsClass("PacketPlayOutChat").getConstructor(new Class[]{getNmsClass("IChatBaseComponent")}).newInstance(new Object[]{comp});
            Object handle = p.getClass().getMethod("getHandle", new Class[0]).invoke(p, new Object[0]);

            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", new Class[]{getNmsClass("Packet")}).invoke(playerConnection, new Object[]{packet});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JsonStringBuilder append(String text) {
        return new JsonStringBuilder(this, esc(text));
    }

    private static String esc(String s) {
        return JSONObject.escape(s);
    }

    public static class JsonStringBuilder {

        private String click = "";
        private String hover = "";
        private final String string;
        private final JsonMessage message;

        public JsonStringBuilder(JsonMessage msg, String text) {
            this.message = msg;
            this.string = (",{\"text\":\"" + text + "\"");
        }

        public JsonStringBuilder setHoverAsTooltip(String... lore) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < lore.length; i++) {
                if (i + 1 == lore.length) {
                    builder.append(lore[i]);
                } else {
                    builder.append(lore[i] + "\n");
                }
            }
            this.hover = (",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + JsonMessage.esc(builder.toString()) + "\"}");
            return this;
        }

        public JsonStringBuilder setHoverAsAchievement(String ach) {
            this.hover = (",\"hoverEvent\":{\"action\":\"show_achievement\",\"value\":\"achievement." + JsonMessage.esc(ach) + "\"}");
            return this;
        }

        public JsonStringBuilder setClickAsURL(String link) {
            this.click = (",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"" + JsonMessage.esc(link) + "\"}");
            return this;
        }

        public JsonStringBuilder setClickAsSuggestCmd(String cmd) {
            this.click = (",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"" + JsonMessage.esc(cmd) + "\"}");
            return this;
        }

        public JsonStringBuilder setClickAsExecuteCmd(String cmd) {
            this.click = (",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + JsonMessage.esc(cmd) + "\"}");
            return this;
        }

        public JsonMessage save() {
            JsonMessage tmp4_1 = this.message;
            tmp4_1.msg = (tmp4_1.msg + this.string + this.hover + this.click + "}");
            return this.message;
        }
    }
}
