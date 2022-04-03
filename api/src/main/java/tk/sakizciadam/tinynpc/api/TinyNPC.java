package tk.sakizciadam.tinynpc.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import tk.sakizciadam.tinynpc.api.listener.ChunkListener;
import tk.sakizciadam.tinynpc.api.listener.MoveListener;
import tk.sakizciadam.tinynpc.api.listener.NPCListener;
import tk.sakizciadam.tinynpc.api.npc.AbstractNPCRegistry;
import tk.sakizciadam.tinynpc.api.utils.CommonUtils;
import tk.sakizciadam.tinynpc.api.utils.ReflectionUtils;

public class TinyNPC {
    private final JavaPlugin javaPlugin;

    private AbstractNPCRegistry npcRegistry;
    public final String version;
    private final String classTemplate="tk.sakizciadam.tinynpc.nms.%s.%s";

    public TinyNPC(JavaPlugin javaPlugin){
        this.javaPlugin=javaPlugin;
        String a = javaPlugin.getServer().getClass().getPackage().getName();
        version = a.substring(a.lastIndexOf('.') + 1);
        Class registryClass=ReflectionUtils.getClassFromString(String.format(classTemplate,version,"NPCRegistry"));

        if(registryClass==null){
            CommonUtils.error("Registry class is null. Unsupported version? %s",version);
            Bukkit.getPluginManager().disablePlugin(javaPlugin);
        }

        try {
            this.npcRegistry=(AbstractNPCRegistry) registryClass.getConstructor(TinyNPC.class).newInstance(this);
        } catch (Exception e){
            CommonUtils.error("Could not create NPCRegistry.");
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(javaPlugin);

        }

        new NPCListener(this);
        new MoveListener(this);
        new ChunkListener(this);
    }

    public AbstractNPCRegistry getRegistry() {
        return npcRegistry;
    }

    public JavaPlugin getJavaPlugin() {
        return javaPlugin;
    }
}
