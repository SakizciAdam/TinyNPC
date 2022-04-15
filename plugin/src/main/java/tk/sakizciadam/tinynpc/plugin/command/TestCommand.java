package tk.sakizciadam.tinynpc.plugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.sakizciadam.tinynpc.plugin.TinyNPCPlugin;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player){
            Player p=(Player)commandSender;

            GolemKing golemKing=(GolemKing)TinyNPCPlugin.getTinyNPC().lib.getRegistry().createUnsafeNPC(GolemKing.class,"GolemKing");

            golemKing.setTarget(p);

            golemKing.spawn(p.getLocation());
        }

        return true;
    }
}
