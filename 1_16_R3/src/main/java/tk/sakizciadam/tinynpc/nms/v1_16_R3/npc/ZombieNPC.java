package tk.sakizciadam.tinynpc.nms.v1_16_R3.npc;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Entity;
import tk.sakizciadam.tinynpc.api.npc.goal.AttackGoal;
import tk.sakizciadam.tinynpc.api.npc.goal.LocationGoal;
import tk.sakizciadam.tinynpc.api.npc.impl.AbstractZombieNPC;
import tk.sakizciadam.tinynpc.nms.v1_16_R3.goal.PathFinderAttack;
import tk.sakizciadam.tinynpc.nms.v1_16_R3.goal.PathFinderGoToLocation;

public class ZombieNPC extends AbstractZombieNPC {
    public ZombieNPC(String name) {
        super(name);
    }

    @Override
    public void spawn(Location location) {

        ZombieEnt zombieEnt=new ZombieEnt(this,location.getWorld());
        zombieEnt.teleport(location);
        zombieEnt.setBaby(this.isTempBaby());

        setNMSEntity(zombieEnt);
        zombieEnt.getWorld().addEntity(zombieEnt);


    }

    @Override
    public void despawn() {
        if(!isSpawned()){

            return;
        }

        getBukkitEntity().remove();

        setNMSEntity(null);

        super.despawn();

    }

    @Override
    public void setBaby(boolean baby) {
        super.setBaby(baby);
        ((ZombieEnt)getNMSEntity()).setBaby(baby);
    }

    @Override
    public boolean isBaby() {
        return ((ZombieEnt)getNMSEntity()).isBaby();
    }

    @Override
    public Entity getBukkitEntity() {
        return ((EntityLiving)getNMSEntity()).getBukkitEntity();
    }

    @Override
    public Location getLocation() {
        return isSpawned() ? getBukkitEntity().getLocation() : null;
    }

    @Override
    public AttackGoal getAttackGoal() {
        return ((ZombieEnt)getNMSEntity()).pathFinderAttack;
    }

    @Override
    public LocationGoal getLocationGoal() {
        return ((ZombieEnt)getNMSEntity()).pathFinderGoToLocation;
    }

    @Override
    public boolean isSpawned() {
        return getNMSEntity()!=null&&getBukkitEntity()!=null;
    }

    public EntityTypes getEntityTypes(){
        switch (getZombieType()){
            case ZOMBIE_VILLAGER:
                return EntityTypes.ZOMBIE_VILLAGER;
            case DROWNED:
                return EntityTypes.DROWNED;


            default:
            case ZOMBIE:
                return EntityTypes.ZOMBIE;

        }
    }

    @Override
    public int getID() {
        return isSpawned() ? getBukkitEntity().getEntityId() : -999;
    }

    @Override
    public org.bukkit.World getWorld(){
        return isSpawned() ? getBukkitEntity().getWorld() : null;
    }

    public static class ZombieEnt extends EntityZombie {
        protected final ZombieNPC parent;

        private PathFinderGoToLocation pathFinderGoToLocation;
        private PathFinderAttack pathFinderAttack;

        public ZombieEnt(ZombieNPC parent, org.bukkit.World world) {

            super(parent.getEntityTypes(), ((CraftWorld)world).getHandle());
            this.parent=parent;
            this.pathFinderGoToLocation.setParent(this.parent);
            this.pathFinderAttack.setParent(this.parent);
        }

        public void teleport(Location location){
            this.setPositionRotation(location.getX(),location.getY(),location.getZ(),location.getYaw(),location.getPitch());
        }

        public void playSound(SoundEffect sound,float f,float v){
            if(getParent()==null){
                return;
            }

            if(getParent().canPlaySound()){
                super.playSound(sound, f, v);
            }
        }

        public void initPathfinder(){

            this.goalSelector.a(0, pathFinderAttack= new PathFinderAttack(null,this,null,2f));
            this.goalSelector.a(1, pathFinderGoToLocation= new PathFinderGoToLocation(null,this,2f));
        }

        public void tick(){
            super.tick();
            if(getParent()!=null&&!getBukkitEntity().isDead()){
                getParent().tick();
            }
        }

        public ZombieNPC getParent() {
            return parent;
        }
    }
}
