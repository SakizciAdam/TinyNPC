package tk.sakizciadam.tinynpc.api.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import tk.sakizciadam.tinynpc.api.npc.NPC;
import tk.sakizciadam.tinynpc.api.npc.PlayerNPC;
import tk.sakizciadam.tinynpc.api.TinyNPCLib;

public class MoveListener implements Listener {

    private final TinyNPCLib lib;

    public MoveListener(TinyNPCLib tinyNPCLib){
        this.lib=tinyNPCLib;
        Bukkit.getPluginManager().registerEvents(this,lib.getJavaPlugin());
    }



    @EventHandler
    public void moveEvent(PlayerMoveEvent event){
        Player p=event.getPlayer();

        Location loc=event.getTo();

        if(event.isCancelled()) return;

        for (NPC npc : lib.getNpcManager().getNPCList()) {
            if(!npc.isSpawned()) continue;
            if(npc instanceof PlayerNPC==false){
                continue;
            }

            PlayerNPC pNPC=(PlayerNPC)npc;

            if(npc.getEntity().getWorld()!=p.getWorld()) continue;



            if(npc.getEntity().getLocation().distance(p.getLocation())>=Bukkit.getViewDistance()){
                if(pNPC.getShowList().contains(p.getUniqueId())){
                    pNPC.despawnFor(p);
                }


            } else if(!pNPC.getShowList().contains(p.getUniqueId())){
                pNPC.spawnFor(p);
            }


        }
    }

}
