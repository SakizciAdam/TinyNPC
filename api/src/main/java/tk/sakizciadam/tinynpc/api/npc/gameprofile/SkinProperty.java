package tk.sakizciadam.tinynpc.api.npc.gameprofile;

import org.apache.commons.lang.Validate;
import tk.sakizciadam.tinynpc.api.utils.ReflectionUtils;

import java.lang.reflect.Constructor;

public class SkinProperty implements Property {

    private final String value,signature;




    public SkinProperty(String value,String signature){
        this.value=value;
        this.signature=signature;
    }

    public String getSignature() {
        return signature;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String getName() {
        return "SkinProperty";
    }


}
