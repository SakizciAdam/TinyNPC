package tk.sakizciadam.tinynpc.plugin;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;
import tk.sakizciadam.tinynpc.api.TinyNPC;
import tk.sakizciadam.tinynpc.plugin.command.TestCommand;

public class TinyNPCPlugin extends JavaPlugin implements Listener {

    private static TinyNPCPlugin tinyNPC;
    public TinyNPC lib;

    /*
    THIS PLUGIN IS FOR EXAMPLE
    AND TESTING THE CAPABILITIES
     */

    @Override
    public void onEnable() {
        tinyNPC=this;
        Bukkit.getPluginManager().registerEvents(this,this);

        this.lib=new TinyNPC(this);


        getCommand("spawnnpc").setExecutor(new TestCommand());
    }

    @Override
    public void onDisable() {

    }


    public static TinyNPCPlugin getTinyNPC() {
        return tinyNPC;
    }
}
