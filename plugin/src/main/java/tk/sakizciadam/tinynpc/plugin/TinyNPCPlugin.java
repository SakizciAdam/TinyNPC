package tk.sakizciadam.tinynpc.plugin;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;
import tk.sakizciadam.tinynpc.api.TinyNPCLib;
import tk.sakizciadam.tinynpc.api.events.InteractType;
import tk.sakizciadam.tinynpc.api.events.NPCInteractEvent;
import tk.sakizciadam.tinynpc.plugin.command.TestCommand;

public class TinyNPCPlugin extends JavaPlugin implements Listener {

    private static TinyNPCPlugin tinyNPC;
    private TinyNPCLib lib;

    /*


    This plugin is completely for test
    And should never be used
    You can learn how to use the API in this plugin though


     */


    @Override
    public void onEnable() {
        tinyNPC=this;
        Bukkit.getPluginManager().registerEvents(this,this);
        lib=new TinyNPCLib(this);
        lib.init();



        getCommand("spawnnpc").setExecutor(new TestCommand());
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onNPCInteractEvent(NPCInteractEvent event){


    }

    public TinyNPCLib getLib(){
        return lib;
    }

    public static TinyNPCPlugin getTinyNPC() {
        return tinyNPC;
    }
}
