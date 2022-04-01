package tk.sakizciadam.tinynpc.api.utils;

import tk.sakizciadam.tinynpc.api.npc.NPCManager;

public interface INPCUtils {
    NPCManager.NPCType getNPCType(org.bukkit.entity.Entity entity);
    boolean isNavigatorEntity(org.bukkit.entity.Entity entity);

}
