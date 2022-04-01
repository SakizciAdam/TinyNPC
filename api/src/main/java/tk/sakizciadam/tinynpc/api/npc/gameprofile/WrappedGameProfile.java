package tk.sakizciadam.tinynpc.api.npc.gameprofile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import tk.sakizciadam.tinynpc.api.utils.CommonUtils;
import tk.sakizciadam.tinynpc.api.utils.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class WrappedGameProfile {

    private String name;
    private UUID uuid;
    private List<Property> propertyList=new ArrayList<>();



    public WrappedGameProfile(String name, UUID uuid){
        this.name=name;
        this.uuid=uuid;

        if(StringUtils.isBlank(this.name)){
            CommonUtils.warn("Invalid name for GameProfile setting it to Steve");
            this.name="Steve";

        }
        if(this.uuid==null){
            CommonUtils.warn("Invalid UUID for GameProfile setting it randomly");
            this.uuid=UUID.randomUUID();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }


    public String toString(){
        return String.format("GameProfile{%s:%s}",uuid.toString(),name);
    }

    public Property getProperty(String name){
        return propertyList.stream().filter(i -> i.getName().contentEquals(name)).findFirst().orElse(null);
    }

    public void addProperty(Property property){
        Validate.notNull(property,"Invalid property!");

        if(getProperty(property.getName())!=null){
            CommonUtils.warn("%s already has a property named %s",this.toString(),property.getName());
            return;
        }

        propertyList.add(property);

    }
}
