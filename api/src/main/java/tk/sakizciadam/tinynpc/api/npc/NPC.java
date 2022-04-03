package tk.sakizciadam.tinynpc.api.npc;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public interface NPC {
    boolean isSpawned();

    void spawn(Location location);

    World getWorld();

    int getID();

    Entity getBukkitEntity();

    Location getLocation();
}
