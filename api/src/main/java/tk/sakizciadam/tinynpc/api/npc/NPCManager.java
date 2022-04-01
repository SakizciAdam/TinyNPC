package tk.sakizciadam.tinynpc.api.npc;

import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import tk.sakizciadam.tinynpc.api.TinyNPCLib;
import tk.sakizciadam.tinynpc.api.utils.CommonUtils;
import tk.sakizciadam.tinynpc.api.utils.INPCUtils;
import tk.sakizciadam.tinynpc.api.utils.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;

public class NPCManager {
    private final List<NPC> npcs=new ArrayList<>();
    private final String classTemplate="tk.sakizciadam.tinynpc.nms.%s.%s";


    public NPCPacketListener listener;
    public final String version;
    private INPCUtils npcUtils;
    public final TinyNPCLib lib;

    public NPCManager(TinyNPCLib tinyNPCLib, String version){
        this.lib=tinyNPCLib;
        this.version=version;
        this.listener=new NPCPacketListener(this);
        Class<?> utilsClass= ReflectionUtils.getClassFromString(String.format(classTemplate,version,"NPCUtils"));

        if(utilsClass!=null){
            try {
                npcUtils= (INPCUtils) utilsClass.getConstructor().newInstance();


                if(npcUtils!=null){
                    CommonUtils.info("Utils class found!");
                } else {
                    CommonUtils.error("Utils class not found but class found!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            CommonUtils.error("Utils class not found!");
        }
    }

    public NPC getNPC(World world, int entityID){

        for(NPC npc:npcs){

            if(npc==null) continue;
            if(!npc.isSpawned()) continue;
            if(!npc.getEntity().getWorld().equals(world)) continue;

            if(npc.getEntity().getEntityId()==entityID) return npc;
        }
        return null;

    }

    public NPC getNPC(int entityID){

        for(NPC npc:npcs){

            if(npc==null) continue;
            if(!npc.isSpawned()) continue;

            if(npc.getEntity().getEntityId()==entityID) return npc;
        }
        return null;

    }

    public INPCUtils getUtils(){

        return npcUtils;

    }

    public List<NPC> getNPCList() {
        return npcs;
    }

    public NPC createNPC(NPCType npcType){
        Class<?> npcClass= ReflectionUtils.getClassFromString(String.format(classTemplate,version,npcType.getClassName()));

        if(npcClass==null){
            CommonUtils.error("NPC Class is null!");
            return null;
        }

        try {
            Object o=npcClass.getConstructor().newInstance();
            NPC npc=(NPC)o;
            npc.setLib(lib);
            this.npcs.add(npc);
            return (NPC)o;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public enum NPCType {
        PLAYER_NPC("PlayerNPC"),ZOMBIE_NPC("ZombieNPC")

        ;

        private String className;

        NPCType(String b){
            this.className=b;
        }

        public String getClassName() {
            return className;
        }
    }

}
