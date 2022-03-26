package tk.sakizciadam.tinynpc.plugin.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import tk.sakizciadam.tinynpc.api.npc.NPCManager;
import tk.sakizciadam.tinynpc.api.npc.PlayerNPC;
import tk.sakizciadam.tinynpc.api.npc.gameprofile.SkinProperty;
import tk.sakizciadam.tinynpc.api.npc.gameprofile.WrappedGameProfile;
import tk.sakizciadam.tinynpc.plugin.TinyNPCPlugin;

import java.util.UUID;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player){
            Player p=(Player)commandSender;
            PlayerNPC npc= (PlayerNPC) TinyNPCPlugin.getTinyNPC().getLib().getNpcManager().createNPC(NPCManager.NPCType.PLAYER_NPC);
            npc.setInvulnerable(true);

            SkinProperty skinProperty=new SkinProperty("ewogICJ0aW1lc3RhbXAiIDogMTY0MTAxNzkxNjM0NSwKICAicHJvZmlsZUlkIiA6ICI5MWYwNGZlOTBmMzY0M2I1OGYyMGUzMzc1Zjg2ZDM5ZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJTdG9ybVN0b3JteSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9hN2VjMzlmYmU4OWZhNDIwZGE0MjUzOTY5ZTkxMTBlN2JlZTJkY2NiMzVkNTI4ZmMxZjk0OGY4Mzk1NmMwZjcwIgogICAgfQogIH0KfQ==","Sqatq7WDc4Wh2aqqfm5XesNjjhzNEjaGjDbMdHK/tE40tkQ6kb/apjBy2LjNM7YKi9WK8GGGPvpaAjyEr3wVltzpJzx6OFx4z+NYrfIUmr+I8P6JUvth8Tnfy5pH/q40cwkdmYQVBAP5yzo26GtuZoy4C7JM7nwaiLplOk/xk6dP/Qp/0EyObJoocp1/EWyYS1lbsuUZcAwHH81HYLdby6fMo1J+hkCqeKiyekK1Otp5N5g7lTI4Fs9p2SHG7ZD3wCbJiG9IbATQ1Tx9kwsoegzFUVXNXIwmWWHEJ1m4P+3V7ZTM0ofBAfCbnPiDg0tlovpwIZ6UMLUmz/j25KRaW0KRmsJaky8YX2PClYtpzmVycOlgy8NQ50hsjxyZBoZ+KJXYokpC1tZ4dylUyTPkyIkK/+ABhBZMJMBQ1JdwtabMhDXxkQ8H4dnJUUF500nLbRZihyPJE2O0WnGW7l0h+rI1Idrr0Fligue90IH43V/vqI4JK96oV8Vcbm+1ucFst6kjFeSXHsxFO74mmJyobxPl/nOWSTt6kdFoG2gy7Xoj+94wS6/IWwPnEkrLxy/VTG9CCEqPtBJHCRWjGgFxYzh3TPwN8vsK86Iay3g9XHGOSHbLi048wG72SzNuv1GLIXUaZmv3jJHfSPdtIyK1+76RL70AJCnKz0R++jj1ANY=");
            WrappedGameProfile wrappedGameProfile=new WrappedGameProfile(ChatColor.translateAlternateColorCodes('&',"&8"), UUID.randomUUID());

            wrappedGameProfile.addProperty(skinProperty);

            npc.setWrappedGameProfile(wrappedGameProfile);
            npc.setHologram(ChatColor.translateAlternateColorCodes('&',"&aNPC Shop"));

            npc.spawn(p.getLocation());
            ((Player)npc.getEntity()).getInventory().setItem(EquipmentSlot.HAND,new ItemStack(Material.NETHERITE_HOE));
            npc.setCollision(false);


            npc.setGamemode(GameMode.CREATIVE);

            npc.setNavigationSpeed(3);
            npc.showEverybody();
            npc.setShowOnJoin(true);


            //for pathfinder testing
            Bukkit.getScheduler().runTaskTimer(TinyNPCPlugin.getTinyNPC(),() -> {

                if(p.isOnline()&&p.isSneaking()){

                    npc.goTo(p.getLocation());

                }

            },20L,10L);
        }

        return true;
    }
}
