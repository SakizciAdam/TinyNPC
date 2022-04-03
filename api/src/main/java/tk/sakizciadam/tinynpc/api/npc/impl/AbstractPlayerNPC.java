package tk.sakizciadam.tinynpc.api.npc.impl;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import tk.sakizciadam.tinynpc.api.npc.gameprofile.WrappedGameProfile;
import tk.sakizciadam.tinynpc.api.npc.goal.AttackGoal;
import tk.sakizciadam.tinynpc.api.npc.goal.LocationGoal;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AbstractPlayerNPC extends AbstractNPC{
    private WrappedGameProfile wrappedGameProfile;
    public List<UUID> show=new ArrayList<>();

    public AbstractPlayerNPC(String name) {
        super(name);
        this.wrappedGameProfile=new WrappedGameProfile("Steve", UUID.randomUUID());
    }

    public void updateNPC(){
        for(UUID uuid:show){
            if(Bukkit.getPlayer(uuid).isOnline()){
                Player player=Bukkit.getPlayer(uuid);

                despawnFor(player);
                spawnFor(player);
            }
        }
    }

    public abstract void spawnFor(Player player);

    public abstract void despawnFor(Player player);

    public void setWrappedGameProfile(WrappedGameProfile wrappedGameProfile) {
        this.wrappedGameProfile = wrappedGameProfile;
    }

    public WrappedGameProfile getWrappedGameProfile(){
        return this.wrappedGameProfile;
    }
}
