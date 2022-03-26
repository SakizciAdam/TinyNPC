package tk.sakizciadam.tinynpc.api.utils;

import org.bukkit.Bukkit;

public class CommonUtils {
    public static void error(String msg,Object... o){
        String v=String.format(msg,o);

        Bukkit.getLogger().severe("[TinyNPC] "+v);
    }

    public static void info(String msg,Object... o){
        String v=String.format(msg,o);

        Bukkit.getLogger().info("[TinyNPC] "+v);
    }

    public static void warn(String msg,Object... o){
        String v=String.format(msg,o);

        Bukkit.getLogger().warning("[TinyNPC] "+v);
    }
}
