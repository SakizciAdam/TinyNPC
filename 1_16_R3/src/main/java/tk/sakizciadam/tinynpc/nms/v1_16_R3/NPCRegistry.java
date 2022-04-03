package tk.sakizciadam.tinynpc.nms.v1_16_R3;

import tk.sakizciadam.tinynpc.api.TinyNPC;
import tk.sakizciadam.tinynpc.api.npc.AbstractNPCRegistry;
import tk.sakizciadam.tinynpc.api.npc.NPC;
import tk.sakizciadam.tinynpc.api.npc.impl.AbstractNPC;
import tk.sakizciadam.tinynpc.api.utils.CommonUtils;
import tk.sakizciadam.tinynpc.api.utils.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Ref;
import java.util.Set;

public class NPCRegistry extends AbstractNPCRegistry {
    public NPCRegistry(TinyNPC lib) {
        super(lib);
    }

    @Override
    public NPC createNPC(Class<? extends AbstractNPC> clazz, String name) {
        Class<? extends AbstractNPC> cl= (Class<? extends AbstractNPC>) ReflectionUtils.getClassFromString("tk.sakizciadam.tinynpc.nms.v1_16_R3.npc."+clazz.getSimpleName().replace("Abstract",""));

        if(cl==null){
            CommonUtils.warn("Could not find extended class for %s",clazz.getCanonicalName());
            return null;
        }

        return createUnsafeNPC(cl,name);
    }

}
