package tk.sakizciadam.tinynpc.nms.v1_16_R3;

import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.PathfinderGoal;
import org.bukkit.Location;
import tk.sakizciadam.tinynpc.api.utils.CommonUtils;

public class PathFinderGoToLocation extends PathfinderGoal {

    private WrapperNPC wrapperNPC;
    private Location target;
    private float speed;
    private EntityInsentient entityInsentient;

    public PathFinderGoToLocation(WrapperNPC wrapperNPC,EntityInsentient entityInsentient,float speed){
        this.wrapperNPC=wrapperNPC;
        this.entityInsentient=entityInsentient;
        this.speed=speed;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public boolean a() {

        if(wrapperNPC==null) return false;

        return wrapperNPC.isSpawned()&&target!=null&&entityInsentient!=null&&speed>0;
    }

    public boolean b() {


        return !entityInsentient.getNavigation().m();
    }

    public void c() {


        entityInsentient.getNavigation().a(this.target.getX(), this.target.getY(), this.target.getZ(), this.speed);
    }

    public WrapperNPC getWrapperNPC() {
        return wrapperNPC;
    }

    public void setWrapperNPC(WrapperNPC wrapperNPC) {
        this.wrapperNPC = wrapperNPC;
    }

    public void setTarget(Location target) {
        this.target = target;
    }

    public Location getTarget() {
        return target;
    }
}
