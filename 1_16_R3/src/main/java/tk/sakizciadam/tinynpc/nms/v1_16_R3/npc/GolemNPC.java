package tk.sakizciadam.tinynpc.nms.v1_16_R3.npc;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import tk.sakizciadam.tinynpc.api.npc.goal.AttackGoal;
import tk.sakizciadam.tinynpc.api.npc.goal.LocationGoal;
import tk.sakizciadam.tinynpc.api.npc.impl.AbstractGolemNPC;
import tk.sakizciadam.tinynpc.nms.v1_16_R3.goal.PathFinderAttack;
import tk.sakizciadam.tinynpc.nms.v1_16_R3.goal.PathFinderGoToLocation;

public class GolemNPC extends AbstractGolemNPC {
    public GolemNPC(String name) {
        super(name);
    }

    @Override
    public void spawn(Location location) {
        GolemEnt entity=new GolemEnt(this,location.getWorld());
        entity.teleport(location);
        setNMSEntity(entity);
        entity.getWorld().addEntity(entity);

    }

    @Override
    public Entity getBukkitEntity() {
        return ((GolemEnt)getNMSEntity()).getBukkitEntity();
    }

    @Override
    public AttackGoal getAttackGoal() {
        return ((GolemEnt)getNMSEntity()).pathFinderAttack;
    }

    @Override
    public LocationGoal getLocationGoal() {
        return ((GolemEnt)getNMSEntity()).pathFinderGoToLocation;
    }

    public static class GolemEnt extends EntityIronGolem {
        protected final GolemNPC parent;

        private PathFinderGoToLocation pathFinderGoToLocation;
        private PathFinderAttack pathFinderAttack;

        public GolemEnt(GolemNPC parent, org.bukkit.World world) {
            super(EntityTypes.IRON_GOLEM,((CraftWorld)world).getHandle());

            this.parent=parent;
            this.pathFinderGoToLocation.setParent(this.parent);
            this.pathFinderAttack.setParent(this.parent);
        }

        public void teleport(Location location){
            this.setPositionRotation(location.getX(),location.getY(),location.getZ(),location.getYaw(),location.getPitch());
        }

        public void playSound(SoundEffect sound, float f, float v){
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

        public GolemNPC getParent() {
            return parent;
        }
    }
}
