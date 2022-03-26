package tk.sakizciadam.tinynpc.api.npc;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import tk.sakizciadam.tinynpc.api.npc.NPC;
import tk.sakizciadam.tinynpc.api.npc.gameprofile.WrappedGameProfile;

import java.util.List;
import java.util.UUID;

public interface PlayerNPC extends NPC {
    void spawnFor(Player player);

    List<UUID> getShowList();

    void despawnFor(Player player);

    void setGamemode(GameMode gamemode);

    GameMode getGamemode();

    void showEverybody();

    void setWrappedGameProfile(WrappedGameProfile wrappedGameProfile);

    WrappedGameProfile getWrappedGameProfile();

    void setShowOnJoin(boolean b);

    boolean showOnJoin();

    boolean canSee(Player player);

}
