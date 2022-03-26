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

            SkinProperty skinProperty=new SkinProperty("ewogICJ0aW1lc3RhbXAiIDogMTU5MzE5MDY3OTIwOCwKICAicHJvZmlsZUlkIiA6ICJlYTlhMTczMzQ1ZGY0NzMzYWNlNzM0OGMyM2U2ZWFhOSIsCiAgInByb2ZpbGVOYW1lIiA6ICJSb3VuZGlzaEF0b20iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjdkZWE5ZWI0NjkzZDBlMDBhZmZmZTY1YWMxNmU5ZWIyNTU3ZDVhNTc4NDU4Y2FjNWQ3MWU2ZTdkNDZhM2Y2MSIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9","k4Q/K4JtjcroE6/EZr8fIW9Od9nucExAGoI9Z45gHcMimUK2lurrkluJ+NiFfJqy2twVdde9qiZgsZ9s26DS7Imq9pHcUduQe2wNLcozuQR8pXKV81E+QdoPrhIBKj54rkB6AQQwmTNi242/oPTS01e3PLYD0VBoqTCRTevAOBGh+ZiIhfepymIbLp5tKRrXZ8iKsyWm4qX4nKR/xPZ8MI46ApH7oKO2FV2XuC5Wfav/ghIJobwAaV3jKc/M/fqA75D07ZFauMLv7quxZtYyCfK9foolIf68A4GQ/5kNKPtnPjR2LRQbP5n+N6stbtYb6nv6sb6i3DQwLbqFv93zPbt2EZw3ACGd/VPjOxkjyQj3VsZp5eLlxWuyi0Teuwwl+LdbKJQKC2q2p1QMgNMGp2AQrSLANpXq4wGqreVGb27qWfPWbwlikTuMRZfy4rL1TIdHKpOlhGKf/4CwzRKmJ2U9lLEIWemeyfhDIofBGFHnuV3686R5562Ju3ZysfwjRfyqtEe37qKZEYysX5YZwFbf6GsWPP7vuHiVUaHPRVYCIU9cCRPH6m9RxUEtDUWMujdTwCgUV8m2KsKMaTE03BBRnXFAGoPp7TPMQrXebY62Nd0DUR7N0cuhdir03oFC12vn3hD498ZuR+pwFVoEx2kc+EkiRO3g/aSnWjAUq2g=");
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
