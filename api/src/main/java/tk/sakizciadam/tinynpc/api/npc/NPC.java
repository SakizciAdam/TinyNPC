package tk.sakizciadam.tinynpc.api.npc;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import tk.sakizciadam.tinynpc.api.TinyNPCLib;
import tk.sakizciadam.tinynpc.api.hologram.Hologram;

public interface NPC {

    void spawn(Location location);

    Entity getEntity();

    boolean isSpawned();

    void despawn();

    void clearAI();

    void setInvulnerable(boolean v);

    boolean isInvulnerable();

    void setCollision(boolean v);

    boolean canCollide();

    void goTo(Location location);

    Location getLastLoc();

    void update();

    float getNavigationSpeed();

    void setNavigationSpeed(float s);

    void remove();

    TinyNPCLib getLib();

    void setLib(TinyNPCLib lib);

    Hologram getHologram();

    void setHologram(String str);

}
