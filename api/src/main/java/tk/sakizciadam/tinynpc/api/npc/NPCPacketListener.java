package tk.sakizciadam.tinynpc.api.npc;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedEnumEntityUseAction;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EquipmentSlot;
import tk.sakizciadam.tinynpc.api.events.InteractType;
import tk.sakizciadam.tinynpc.api.events.NPCInteractEvent;
import tk.sakizciadam.tinynpc.api.npc.NPCManager;

public class NPCPacketListener {

    
    private NPCManager npcManager;

    public NPCPacketListener(NPCManager npcManager){
        this.npcManager=npcManager;


        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(
                new PacketAdapter(npcManager.lib.getJavaPlugin(), PacketType.Play.Client.USE_ENTITY) {
                    @Override
                    public void onPacketReceiving(PacketEvent event) {
                        PacketContainer packet = event.getPacket();
                        if(packet.getType() == PacketType.Play.Client.USE_ENTITY) {
                            Entity entity = packet.getEntityModifier(event.getPlayer().getWorld()).read(0);


                            if(entity == null) return;


                            EnumWrappers.EntityUseAction action=packet.getEntityUseActions().read(0);

                            if(action!= EnumWrappers.EntityUseAction.ATTACK) return;

                            EnumWrappers.Hand hand;

                            try {
                                hand=packet.getHands().read(0);

                            } catch (Exception e){
                                hand= EnumWrappers.Hand.MAIN_HAND;
                            }



                            NPC npc= npcManager.getNPC(entity.getWorld(),entity.getEntityId());

                            if(npc!=null){
                                if(!npc.isSpawned()) return;
                                if(npc.isInvulnerable()) event.setCancelled(true);
                                NPCInteractEvent interactEvent=null;
                                EquipmentSlot slot=hand.equals(EnumWrappers.Hand.MAIN_HAND) ? EquipmentSlot.HAND : EquipmentSlot.OFF_HAND;
                                if(npc instanceof PlayerNPC){
                                    PlayerNPC playerNPC=(PlayerNPC) npc;

                                    if(!playerNPC.canSee(event.getPlayer())){

                                        event.setCancelled(true);
                                    } else {
                                        interactEvent=new NPCInteractEvent(event.getPlayer(),playerNPC,InteractType.LEFT_CLICK,slot);
                                    }
                                } else {
                                    interactEvent=new NPCInteractEvent(event.getPlayer(),npc,InteractType.LEFT_CLICK,slot);
                                }

                                if(event.isCancelled()) return;

                                NPCInteractEvent finalInteractEvent = interactEvent;
                                Bukkit.getScheduler().runTask(npcManager.lib.getJavaPlugin(), () -> {
                                    Bukkit.getPluginManager().callEvent(finalInteractEvent);

                                    if(finalInteractEvent.isCancelled()){
                                        event.setCancelled(true);
                                    }
                                });



                            }
                        }
                    }
                }
        );
        protocolManager.addPacketListener(
                new PacketAdapter(npcManager.lib.getJavaPlugin(), PacketType.Play.Server.SPAWN_ENTITY_LIVING) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        PacketContainer packet = event.getPacket();
                        if(packet.getType() == PacketType.Play.Server.SPAWN_ENTITY_LIVING) {
                            Object entity = packet.getEntityModifier(event.getPlayer().getWorld()).read(0);

                            if(entity == null) return;
                            if(entity instanceof Entity == false) return;
                            if(entity instanceof Zombie==false) return;



                            try {
                                Validate.notNull(npcManager,"NPCManager is null!");
                                Validate.notNull(npcManager.getUtils(),"NPCUtils is null!");

                                if(npcManager.getUtils().isNavigatorEntity((Entity) entity)){
                                    event.setCancelled(true);
                                }
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
    }

}
