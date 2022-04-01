package tk.sakizciadam.tinynpc.api.listeners;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tk.sakizciadam.tinynpc.api.TinyNPCLib;
import tk.sakizciadam.tinynpc.api.events.InteractType;
import tk.sakizciadam.tinynpc.api.events.NPCInteractEvent;
import tk.sakizciadam.tinynpc.api.npc.NPC;
import tk.sakizciadam.tinynpc.api.npc.PlayerNPC;

public class PlayerListener implements Listener {

    private final TinyNPCLib lib;

    public PlayerListener(TinyNPCLib tinyNPCLib){
        this.lib=tinyNPCLib;
        Bukkit.getPluginManager().registerEvents(this,lib.getJavaPlugin());

    }




    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent event){
        Entity entity=event.getRightClicked();
        Player p=event.getPlayer();

        if(this.lib.getNpcManager().getNPC(p.getWorld(),entity.getEntityId())!=null){


            NPC npc=this.lib.getNpcManager().getNPC(p.getWorld(),entity.getEntityId());

            if(npc instanceof PlayerNPC&& !((PlayerNPC) npc).canSee(event.getPlayer())){
               return;
            }

            NPCInteractEvent interactEvent=new NPCInteractEvent(p,npc, InteractType.RIGHT_CLICK,event.getHand());

            Bukkit.getPluginManager().callEvent(interactEvent);

            if(interactEvent.isCancelled()){
                event.setCancelled(true);
            }

        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player p=event.getPlayer();

        for(NPC npc:lib.getNpcManager().getNPCList()){
            if(npc==null) continue;
            if(npc instanceof PlayerNPC==false) continue;
            if(!npc.isSpawned()) continue;

            PlayerNPC playerNPC=(PlayerNPC)npc;

            if(!playerNPC.canSee(p)) continue;

            playerNPC.getShowList().remove(p.getUniqueId());
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player p=event.getPlayer();

        for(NPC npc:lib.getNpcManager().getNPCList()){
            if(npc==null) continue;
            if(npc instanceof PlayerNPC==false) continue;
            if(!npc.isSpawned()) continue;

            PlayerNPC playerNPC=(PlayerNPC)npc;

            if(!playerNPC.showOnJoin()) continue;

            if(playerNPC.canSee(p)) continue;

            playerNPC.spawnFor(p);

            if(playerNPC.getShowDelay()<=0){

                return;
            }

            Bukkit.getScheduler().runTaskLater(lib.getJavaPlugin(), () -> {

                playerNPC.update();
            },playerNPC.getShowDelay());
        }
    }

}
