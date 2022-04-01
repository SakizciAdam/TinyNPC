package tk.sakizciadam.tinynpc.nms.v1_16_R3;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import tk.sakizciadam.tinynpc.api.TinyNPCLib;
import tk.sakizciadam.tinynpc.api.hologram.Hologram;
import tk.sakizciadam.tinynpc.api.npc.NPC;

public abstract class WrapperNPC implements NPC {
    private net.minecraft.server.v1_16_R3.Entity nmsEntity;
    public Location lastLoc;
    private Hologram hologram;

    public net.minecraft.server.v1_16_R3.Entity getNMSEntity() {
        return nmsEntity;
    }

    public Entity getEntity() {
        return nmsEntity.getBukkitEntity();
    }

    private boolean ai;
    private TinyNPCLib lib;

    @Override
    public void clearAI() {
        //for creatures
    }

    public void teleportHologram(){
        if(isSpawned()&&hologram!=null){
            hologram.teleport(getEntity().getLocation().subtract(0,0.1,0));
        }
    }

    public Hologram getHologram() {
        return hologram;
    }

    public void setHologram(String str){
        this.hologram=new Hologram(str);


    }

    public TinyNPCLib getLib() {
        return lib;
    }

    public void setLib(TinyNPCLib lib) {
        this.lib = lib;
    }

    public void remove(){
        if(isSpawned()){
            getEntity().remove();
            setNMSEntity(null);
            lib.getNpcManager().getNPCList().remove(this);

        }
    }

    public Location getLastLoc(){
        return lastLoc;
    }

    @Override
    public void setInvulnerable(boolean v) {
        if(isSpawned()){
            nmsEntity.setInvulnerable(v);
        }
    }

    @Override
    public boolean isInvulnerable() {
        return nmsEntity!=null ? nmsEntity.isInvulnerable() : false;
    }

    @Override
    public boolean canCollide() {
        return nmsEntity!=null ? nmsEntity.isCollidable() : false;
    }

    public void despawn(){
        this.nmsEntity.getBukkitEntity().remove();
        this.nmsEntity=null;
    }

    public void setAi(boolean ai) {
        this.ai = ai;
    }

    public boolean useAI() {
        return ai;
    }

    @Override
    public boolean isSpawned() {
        return nmsEntity!=null&&nmsEntity.getBukkitEntity()!=null&&nmsEntity.getBukkitEntity().getLocation()!=null;
    }

    public void setNMSEntity(net.minecraft.server.v1_16_R3.Entity entity) {
        this.nmsEntity = entity;
    }
}
