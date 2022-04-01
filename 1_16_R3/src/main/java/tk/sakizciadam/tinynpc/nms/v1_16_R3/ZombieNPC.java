package tk.sakizciadam.tinynpc.nms.v1_16_R3;

import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_16_R3.*;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import tk.sakizciadam.tinynpc.api.utils.ReflectionUtils;

import java.util.Set;
import java.util.UUID;

public class ZombieNPC extends WrapperNPC{
    private ZombieEntityNPC entity;




    @Override
    public void spawn(Location location) {
        this.entity=new ZombieEntityNPC(this,EntityTypes.ZOMBIE,location.getWorld());
        setNMSEntity(entity);
        this.entity.teleport(location);

        this.entity.getWorld().addEntity(entity);
        entity.path.setWrapperNPC(this);


    }

    public void goTo(Location location){


        if(entity.path!=null){
            entity.path.setTarget(location);

        }



    }

    @Override
    public void update() {

    }

    @Override
    public float getNavigationSpeed() {
        return entity.path.getSpeed();
    }

    @Override
    public void setNavigationSpeed(float s) {
        entity.path.setSpeed(s);
    }


    @Override
    public void setCollision(boolean v) {
        this.entity.setCollides(v);
    }

    @Override
    public void clearAI() {
        if(isSpawned()) entity.clearAI();
    }

    public static class ZombieEntityNPC extends EntityZombie {

        private final ZombieNPC parent;
        public PathFinderGoToLocation path;

        public ZombieEntityNPC(ZombieNPC parent,EntityTypes<? extends EntityZombie> entitytypes, org.bukkit.World world) {
            super(entitytypes, ((CraftWorld)world).getHandle());
            this.parent=parent;
        }

        public void clearAI(){

            ReflectionUtils.set(PathfinderGoalSelector.class,this.goalSelector,"d",Sets.newLinkedHashSet());
        }

        public void defaultAI(){
            super.initPathfinder();
        }

        public void tick(){
            super.tick();

            if(parent==null) return;

            if(parent.isSpawned()){
                parent.lastLoc=getBukkitEntity().getLocation();
            }
        }

        public void teleport(Location location){


            this.setPositionRotation(location.getX(),location.getY(),location.getZ(),location.getYaw(),location.getPitch());

        }

        public void setCollides(boolean v){
            collides=v;
        }

        @Override
        public void initPathfinder(){
            path=new PathFinderGoToLocation(null, this,1);
            this.goalSelector.a(0,path);


        }


    }
}
