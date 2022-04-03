package tk.sakizciadam.tinynpc.api.npc.goal;

import org.bukkit.entity.LivingEntity;
import tk.sakizciadam.tinynpc.api.npc.impl.AbstractNPC;

public interface AttackGoal extends NPCGoal {
    AbstractNPC getAbstractNPC();

    void setParent(AbstractNPC abstractNPC);

    void setTarget(LivingEntity target);

    LivingEntity getTarget();
}
