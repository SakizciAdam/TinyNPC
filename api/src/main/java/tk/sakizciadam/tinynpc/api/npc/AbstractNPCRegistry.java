package tk.sakizciadam.tinynpc.api.npc;

import org.bukkit.World;
import tk.sakizciadam.tinynpc.api.TinyNPC;
import tk.sakizciadam.tinynpc.api.npc.impl.AbstractNPC;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNPCRegistry {
    private final List<NPC> npcList=new ArrayList<>();
    private final TinyNPC lib;

    public AbstractNPCRegistry(TinyNPC lib){
        this.lib=lib;
    }

    public TinyNPC getLib() {
        return lib;
    }

    public abstract NPC createNPC(Class<? extends AbstractNPC> clazz,String name);

    public NPC createUnsafeNPC(Class<? extends NPC> clazz,String name){
        try {
            NPC npc=clazz.getConstructor(String.class).newInstance(name);
            npcList.add(npc);
            return npc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public NPC getNPC(int entityID, World world){
        return getNPCList().stream().filter(npc -> npc.getWorld()!=null).filter(npc -> npc.getWorld().equals(world)).filter(npc -> npc.getID()==entityID).findFirst().orElse(null);
    }

    public List<NPC> getNPCList() {
        return npcList;
    }
}
