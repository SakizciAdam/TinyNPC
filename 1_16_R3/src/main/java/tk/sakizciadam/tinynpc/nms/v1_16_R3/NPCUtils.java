package tk.sakizciadam.tinynpc.nms.v1_16_R3;

import net.minecraft.server.v1_16_R3.Entity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import tk.sakizciadam.tinynpc.api.utils.INPCUtils;
import tk.sakizciadam.tinynpc.api.npc.NPCManager;
import tk.sakizciadam.tinynpc.api.utils.CommonUtils;

public class NPCUtils implements INPCUtils {

    public boolean isNavigatorEntity(org.bukkit.entity.Entity entity){
        if(entity==null) {
            CommonUtils.warn("Entity is null!");
            return false;
        }

        CraftEntity craftEntity=(CraftEntity)entity;

        if(craftEntity==null){
            CommonUtils.warn("%s(CraftEntity) is null!",entity.getEntityId());

            return false;
        }

        Entity nmsEntity=craftEntity.getHandle();

        if(nmsEntity==null){
            CommonUtils.warn("%s(NMSEntity) is null!",entity.getEntityId());

            return false;
        }

        return nmsEntity instanceof PlayerNPC.NavigationEntity;
    }

    public NPCManager.NPCType getNPCType(org.bukkit.entity.Entity entity){

        if(entity==null){
            CommonUtils.warn("Entity is null!");
            return null;
        }

        CraftEntity craftEntity=(CraftEntity)entity;

        if(craftEntity==null){
            CommonUtils.warn("%s(CraftEntity) is null!",entity.getEntityId());

            return null;
        }

        Entity nmsEntity=craftEntity.getHandle();

        if(nmsEntity==null){
            CommonUtils.warn("%s(NMSEntity) is null!",entity.getEntityId());

            return null;
        }

        if(nmsEntity instanceof PlayerNPC.FakePlayer){

            return NPCManager.NPCType.PLAYER_NPC;
        } else if(nmsEntity instanceof ZombieNPC.ZombieEntityNPC){
            return NPCManager.NPCType.ZOMBIE_NPC;
        }


        return null;
    }

}
