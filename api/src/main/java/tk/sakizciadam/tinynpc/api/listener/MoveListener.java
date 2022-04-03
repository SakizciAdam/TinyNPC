package tk.sakizciadam.tinynpc.api.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import tk.sakizciadam.tinynpc.api.TinyNPC;
import tk.sakizciadam.tinynpc.api.npc.NPC;
import tk.sakizciadam.tinynpc.api.npc.impl.AbstractPlayerNPC;

public class MoveListener implements Listener {

        private final TinyNPC lib;

    public MoveListener(TinyNPC tinyNPCLib){
        this.lib=tinyNPCLib;
        Bukkit.getPluginManager().registerEvents(this,lib.getJavaPlugin());
    }



    @EventHandler
    public void moveEvent(PlayerMoveEvent event){
        Player p=event.getPlayer();

        Location loc=event.getTo();

        if(event.isCancelled()) return;

        for (NPC npc : lib.getRegistry().getNPCList()) {
            if(!npc.isSpawned()) continue;
            if(npc instanceof AbstractPlayerNPC ==false){
                continue;
            }

            AbstractPlayerNPC pNPC=(AbstractPlayerNPC) npc;

            if(npc.getBukkitEntity().getWorld()!=p.getWorld()) continue;

            if(npc.getBukkitEntity().getLocation().distance(p.getLocation())>=64){
                if(pNPC.show.contains(p.getUniqueId())){
                    pNPC.despawnFor(p);
                }


            } else if(!pNPC.show.contains(p.getUniqueId())){
                pNPC.spawnFor(p);
            }


        }
    }

}
