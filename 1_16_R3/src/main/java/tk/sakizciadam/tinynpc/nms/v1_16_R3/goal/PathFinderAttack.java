package tk.sakizciadam.tinynpc.nms.v1_16_R3.goal;

import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PathfinderGoal;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityTargetEvent;
import tk.sakizciadam.tinynpc.api.npc.goal.AttackGoal;
import tk.sakizciadam.tinynpc.api.npc.impl.AbstractNPC;

public class PathFinderAttack extends PathfinderGoal implements AttackGoal {

    private AbstractNPC abstractNPC;
    private LivingEntity entity;
    private double speed;
    private EntityInsentient entityInsentient;


    public PathFinderAttack(AbstractNPC abstractNPC, EntityInsentient entityInsentient, LivingEntity entity, double speed){
        this.abstractNPC=abstractNPC;
        this.entityInsentient=entityInsentient;
        this.speed=speed;
        this.entity=entity;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public boolean a() {

        if(abstractNPC==null) return false;

        if(this.entity==null) return false;

        if(this.entity.isDead()) return false;

        return abstractNPC.isSpawned()&&entityInsentient!=null&&speed>0;
    }

    public boolean b() {


        return !entityInsentient.getNavigation().m();
    }

    public void c() {

        entityInsentient.getNavigation().a(this.entity.getLocation().getX(), this.entity.getLocation().getY(), this.entity.getLocation().getZ(), (float)this.speed);

        if(abstractNPC.getLivingEntity().getLocation().distance(this.entity.getLocation())<=3){
            entityInsentient.attackEntity(((CraftEntity)entity).getHandle());
        }
    }

    public AbstractNPC getAbstractNPC() {
        return abstractNPC;
    }

    public void setParent(AbstractNPC abstractNPC) {
        this.abstractNPC = abstractNPC;
    }

    public void setTarget(LivingEntity target) {
        this.entity = target;
    }

    public LivingEntity getTarget() {
        return entity;
    }
}
