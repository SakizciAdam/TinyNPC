package tk.sakizciadam.tinynpc.api.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import tk.sakizciadam.tinynpc.api.npc.NPC;
import tk.sakizciadam.tinynpc.api.TinyNPCLib;

public class ChunkListener implements Listener {
    private final TinyNPCLib lib;

    public ChunkListener(TinyNPCLib tinyNPCLib){
        this.lib=tinyNPCLib;
        Bukkit.getPluginManager().registerEvents(this,lib.getJavaPlugin());

    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        Chunk chunk = event.getChunk();

        for (NPC npc : lib.getNpcManager().getNPCList()) {
            if (!npc.isSpawned() || !isSameChunk(npc.getEntity().getLocation(), chunk))
                continue;

            npc.despawn();
        }
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        Chunk chunk = event.getChunk();

        for (NPC npc : lib.getNpcManager().getNPCList()) {
            if (!npc.isSpawned() || !isSameChunk(npc.getEntity().getLocation(), chunk))
                continue;

            if(npc.getLastLoc()==null) continue;

            npc.spawn(npc.getLastLoc());
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
