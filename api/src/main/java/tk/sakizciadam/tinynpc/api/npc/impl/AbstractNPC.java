package tk.sakizciadam.tinynpc.api.npc.impl;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import tk.sakizciadam.tinynpc.api.hologram.Hologram;
import tk.sakizciadam.tinynpc.api.npc.NPC;
import tk.sakizciadam.tinynpc.api.npc.goal.AttackGoal;
import tk.sakizciadam.tinynpc.api.npc.goal.LocationGoal;

public abstract class AbstractNPC implements NPC {

    private String name;
    private Object nmsEntity;
    private Hologram hologram;
    private boolean playSound;

    public AbstractNPC(String name){
        this.name=name;
    }

    public Hologram getHologram() {
        return hologram;
    }

    public void setHologram(String name) {
        this.hologram = new Hologram(ChatColor.translateAlternateColorCodes('&',name));
    }

    public void setMaxHealth(double d){
        AttributeInstance attribute = getLivingEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH);
        attribute.setBaseValue(d);
    }



    public void setGravity(boolean v){
        getLivingEntity().setGravity(v);
    }

    public abstract void despawn();

    public boolean canPlaySound() {
        return playSound;
    }

    public void setPlaySound(boolean playSound) {
        this.playSound = playSound;
    }

    public abstract Entity getBukkitEntity();

    public LivingEntity getLivingEntity(){
        return (LivingEntity)getBukkitEntity();
    }

    public Object getNMSEntity() {
        return nmsEntity;
    }

    public void tick(){
        if(getHologram()!=null){
            if(getHologram().getArmorStand()==null){
                return;
            }

            getHologram().teleport(getBukkitEntity().getLocation());

        }
    }
    public void spawnHologram(){
        if(hologram!=null) getHologram().spawn(getBukkitEntity().getLocation());
    }
    public abstract AttackGoal getAttackGoal();
    public abstract LocationGoal getLocationGoal();

    public void setNMSEntity(Object nmsEntity) {
        this.nmsEntity = nmsEntity;
    }

    @Override
    public boolean isSpawned() {
        return nmsEntity!=null;
    }
}
