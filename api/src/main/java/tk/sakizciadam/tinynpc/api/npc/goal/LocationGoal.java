package tk.sakizciadam.tinynpc.api.npc.goal;

import org.bukkit.Location;
import tk.sakizciadam.tinynpc.api.npc.impl.AbstractNPC;

public interface LocationGoal extends NPCGoal{
    AbstractNPC getAbstractNPC();

    void setParent(AbstractNPC abstractNPC);

    void setTarget(Location target);

    Location getTarget();
}
