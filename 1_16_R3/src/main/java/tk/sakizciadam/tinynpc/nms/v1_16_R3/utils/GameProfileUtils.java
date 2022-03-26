package tk.sakizciadam.tinynpc.nms.v1_16_R3.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import tk.sakizciadam.tinynpc.api.npc.gameprofile.SkinProperty;
import tk.sakizciadam.tinynpc.api.npc.gameprofile.WrappedGameProfile;
import tk.sakizciadam.tinynpc.api.utils.CommonUtils;

public class GameProfileUtils {

    public static GameProfile get(WrappedGameProfile wrappedGameProfile){
        GameProfile gameProfile=new GameProfile(wrappedGameProfile.getUuid(), wrappedGameProfile.getName());

        if(wrappedGameProfile.getProperty("SkinProperty")!=null){
            SkinProperty skinProperty=(SkinProperty)wrappedGameProfile.getProperty("SkinProperty");

            gameProfile.getProperties().get("textures").clear();

            Property property=new Property("textures", skinProperty.getValue(), skinProperty.getSignature());

            gameProfile.getProperties().put("textures",property);

        }

        return gameProfile;
    }

}
