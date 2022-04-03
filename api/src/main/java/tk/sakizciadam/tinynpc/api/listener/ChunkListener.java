package tk.sakizciadam.tinynpc.api.listener;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import tk.sakizciadam.tinynpc.api.TinyNPC;
import tk.sakizciadam.tinynpc.api.npc.NPC;
import tk.sakizciadam.tinynpc.api.npc.impl.AbstractPlayerNPC;

public class ChunkListener implements Listener {
    private final TinyNPC lib;

    public ChunkListener(TinyNPC tinyNPCLib){
        this.lib=tinyNPCLib;
        Bukkit.getPluginManager().registerEvents(this,lib.getJavaPlugin());

    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        Chunk chunk = event.getChunk();

        for (NPC npc : lib.getRegistry().getNPCList()) {
            if (!npc.isSpawned() || !isSameChunk(npc.getBukkitEntity().getLocation(), chunk))
                continue;

            if(npc instanceof AbstractPlayerNPC){
                AbstractPlayerNPC playerNPC=(AbstractPlayerNPC)npc;

                playerNPC.despawn();
            }
        }
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        Chunk chunk = event.getChunk();

        for (NPC npc : lib.getRegistry().getNPCList()) {
            if (!npc.isSpawned() || !isSameChunk(npc.getBukkitEntity().getLocation(), chunk))
                continue;

            if(npc.getLocation()==null) continue;

            npc.spawn(npc.getLocation());
        }
    }

    private int getChunkCoordinate(int coordinate) {
        return coordinate >> 4;
    }

    private boolean isSameChunk(Location loc, Chunk chunk) {
        return getChunkCoordinate(loc.getBlockX()) == chunk.getX()
                && getChunkCoordinate(loc.getBlockZ()) == chunk.getZ();
    }
}
