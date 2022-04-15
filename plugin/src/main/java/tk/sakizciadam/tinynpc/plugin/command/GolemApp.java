package tk.sakizciadam.tinynpc.plugin.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import tk.sakizciadam.tinynpc.api.TinyNPC;
import tk.sakizciadam.tinynpc.api.npc.gameprofile.SkinProperty;
import tk.sakizciadam.tinynpc.api.npc.gameprofile.WrappedGameProfile;
import tk.sakizciadam.tinynpc.nms.v1_16_R3.npc.PlayerNPC;
import tk.sakizciadam.tinynpc.plugin.TinyNPCPlugin;

import java.util.UUID;

public class GolemApp extends PlayerNPC implements Listener {
    private Player target;

    public int max=50,hp=max;

    public GolemApp(String name) {
        super(name);

        WrappedGameProfile wrappedGameProfile=new WrappedGameProfile("-", UUID.randomUUID());
        SkinProperty skinProperty=new SkinProperty(
                "ewogICJ0aW1lc3RhbXAiIDogMTYxMzc1NDQ5NTU0MSwKICAicHJvZmlsZUlkIiA6ICI0ZWQ4MjMzNzFhMmU0YmI3YTVlYWJmY2ZmZGE4NDk1NyIsCiAgInByb2ZpbGVOYW1lIiA6ICJGaXJlYnlyZDg4IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2Q3YmY1NTAxYzdlYjJiZWFhYWU2YjhkM2M4ZDUyNTE1Yjk5NzhmZjViODMzOTYxNDQwZDBhMzgzYTQyNDk0ZWUiCiAgICB9CiAgfQp9",
                "dZtwC/WZBz8j9KSLN9Y7D27plvTbtxwHVEioYUMGvgkOJdeFE0jnovOYJ076lc2/C9mQAGfskeeJbjJMxr+y86A3UGsYV3JAg+f/oX9MI9Ntw3i9kV/KakIiRTFHMhn7guHQ4uwdGs28tiStuq45gkVY04oFBHd23jqX3juGLT0Nh/cshKNojitMPzlGexuxQZBkV8JUsmKZjlt5SpQQIn9JvgyZLX6l+zhMN6v5tdhyOB9nfXEPHioQ5uaTfCnvrv7DubOX4AGSPmJDqruhkydGkehaDPnKAvi4jNvr+pJA7W9GS+9Bs1lg66d0PV6h5xdVTbnNuTy81r4TudBW184IQSWrHvHCaX/L961WGFeQgRMDcPMzZMEiLwrsIyhIa3ErBVk2X4sWWASCOZv0qjwcUkgvtrpsZUA5/+quNvNHpbLiV4xAoesUDpG/fghwVuqzGlFHkHI6Zk+T5J4ion7fkShfhGYAUL5dyDxKNb6OX0gG9Ha1OMKuzS9u8a3w1nXX4vj1Rhc8GbIZyCSRhb9YCKwo07kdOF/SGsxLXXGNPKtS8k1qEaMoWwsqk3y7os7Mc43YLHRijd7YOu+XAlhchPRFVAUfwZnaC+I5Rrvkiqkp7akHETRrtAr7lOVyv1lLzzcH2b0zaK+tguimp2X72S9kqiMloD2026Ffis8="
        );

        wrappedGameProfile.addProperty(skinProperty);

        setWrappedGameProfile(wrappedGameProfile);

        setHologram(ChatColor.translateAlternateColorCodes('&',getDisplayName()));
        Bukkit.getPluginManager().registerEvents(this, TinyNPCPlugin.getTinyNPC());
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
        Entity entity=event.getEntity();

        if(entity.getWorld().equals(getWorld())){

            if(entity.getEntityId()==getID()||entity.getEntityId()==this.getHologramID()||entity.getEntityId()==this.getNavigationEntityID()){
                hp-=Math.max(event.getFinalDamage()/3,0.1);

                if(hp<=0){
                    event.setDamage(40);
                } else {
                    event.setCancelled(true);
                }
            }




        }
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public Player getTarget() {
        return target;
    }

    @Override
    public void spawn(Location location) {
        super.spawn(location);
        spawnHologram();
    }

    public void tick(){
        super.tick();

        if(getHologram()!=null&&getHologram().getArmorStand()!=null){
            getHologram().setColorText(getDisplayName());
        }

        if(getTarget()!=null&&getTarget().isOnline()){
            getLocationGoal().setTarget(getTarget().getLocation());

            if(getTarget().getLocation().distance(getLocation())<=2){
                getTarget().damage(10,getLivingEntity());
            }
        } else {
            despawn();
        }
    }

    public String getDisplayName(){
        return String.format("&eGolem Apprentice&7[&c%s&7/&c%s&7]",hp,max);
    }




}
