package tk.sakizciadam.tinynpc.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EquipmentSlot;
import tk.sakizciadam.tinynpc.api.npc.NPC;

public class NPCInteractEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private final Player player;
    private final NPC npc;
    private final InteractType interactType;
    private boolean cancelled;
    private final EquipmentSlot equipmentSlot;

    public NPCInteractEvent(Player player, NPC npc, InteractType interactType, EquipmentSlot equipmentSlot){
        this.player=player;
        this.npc=npc;
        this.interactType=interactType;
        this.equipmentSlot=equipmentSlot;
    }

    public EquipmentSlot getEquipmentSlot() {
        return equipmentSlot;
    }

    public InteractType getInteractType() {
        return interactType;
    }

    public Player getPlayer() {
        return player;
    }

    public NPC getNpc() {
        return npc;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled=b;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }




    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
