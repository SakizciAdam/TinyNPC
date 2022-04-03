package tk.sakizciadam.tinynpc.nms.v1_16_R3.goal;

import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.PathfinderGoal;
import org.bukkit.Location;
import tk.sakizciadam.tinynpc.api.npc.goal.LocationGoal;
import tk.sakizciadam.tinynpc.api.npc.impl.AbstractNPC;
import tk.sakizciadam.tinynpc.api.utils.CommonUtils;

public class PathFinderGoToLocation extends PathfinderGoal implements LocationGoal {

    private AbstractNPC abstractNPC;
    private Location target;
    private double speed;
    private EntityInsentient entityInsentient;

    public PathFinderGoToLocation(AbstractNPC abstractNPC, EntityInsentient entityInsentient, double speed){
        this.abstractNPC=abstractNPC;
        this.entityInsentient=entityInsentient;
        this.speed=speed;
    }


    @Override
    public boolean a() {

        if(abstractNPC==null) return false;

        return abstractNPC.isSpawned()&&target!=null&&entityInsentient!=null&&speed>0;
    }

    public boolean b() {


        return !entityInsentient.getNavigation().m();
    }

    public void c() {


        entityInsentient.getNavigation().a(this.target.getX(), this.target.getY(), this.target.getZ(), this.speed);
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }



    public AbstractNPC getAbstractNPC() {
        return abstractNPC;
    }

    public void setParent(AbstractNPC abstractNPC) {
        this.abstractNPC = abstractNPC;
    }

    public void setTarget(Location target) {
        this.target = target;
    }

    public Location getTarget() {
        return target;
    }

    @Override
    public void setSpeed(double speed) {
        this.speed=speed;
    }

    @Override
    public double getSpeed() {
        return speed;
    }
}
