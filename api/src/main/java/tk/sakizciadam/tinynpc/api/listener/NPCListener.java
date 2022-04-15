package tk.sakizciadam.tinynpc.api.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tk.sakizciadam.tinynpc.api.TinyNPC;
import tk.sakizciadam.tinynpc.api.npc.NPC;
import tk.sakizciadam.tinynpc.api.npc.impl.AbstractNPC;
import tk.sakizciadam.tinynpc.api.npc.impl.AbstractPlayerNPC;
import tk.sakizciadam.tinynpc.api.utils.CommonUtils;

public class NPCListener implements Listener {
    private final TinyNPC tinyNPC;

    public NPCListener(TinyNPC tinyNPC){
        this.tinyNPC=tinyNPC;
        Bukkit.getPluginManager().registerEvents(this,tinyNPC.getJavaPlugin());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        for (NPC n : tinyNPC.getRegistry().getNPCList()) {
            if (!n.isSpawned() || n instanceof AbstractPlayerNPC==false)
                continue;

            AbstractPlayerNPC npc=(AbstractPlayerNPC)n;

            if(npc.show.contains(event.getPlayer().getUniqueId())){
                npc.despawnFor(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event){


        LivingEntity entity= (LivingEntity) event.getEntity();

        AbstractNPC npc=(AbstractNPC) tinyNPC.getRegistry().getNPC(entity.getEntityId(),entity.getWorld());

        if(npc!=null){

            npc.onDeath();

        }
    }
}
