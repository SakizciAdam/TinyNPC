package tk.sakizciadam.tinynpc.api;

import org.bukkit.plugin.java.JavaPlugin;
import tk.sakizciadam.tinynpc.api.listeners.ChunkListener;
import tk.sakizciadam.tinynpc.api.listeners.MoveListener;
import tk.sakizciadam.tinynpc.api.listeners.PlayerListener;
import tk.sakizciadam.tinynpc.api.npc.NPCManager;

public class TinyNPCLib {
    private final JavaPlugin javaPlugin;
    private NPCManager npcManager;
    public final String version;

    public TinyNPCLib(JavaPlugin javaPlugin){
        this.javaPlugin=javaPlugin;
        String a = javaPlugin.getServer().getClass().getPackage().getName();
        version = a.substring(a.lastIndexOf('.') + 1);


    }

    public JavaPlugin getJavaPlugin() {
        return javaPlugin;
    }

    public void init(){
        this.npcManager=new NPCManager(this,version);
        new ChunkListener(this);
        new MoveListener(this);
        new PlayerListener(this);
    }

    public NPCManager getNpcManager() {
        return npcManager;
    }
}
