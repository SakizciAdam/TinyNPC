package tk.sakizciadam.tinynpc.api.hologram;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EquipmentSlot;

public class Hologram {

    private String text;
    private ArmorStand armorStand;

    public Hologram(String text){
        this.text=text;
    }

    public void teleport(Location location){
        if(armorStand!=null) armorStand.teleport(location);
    }

    public void spawn(Location location){
        Validate.notNull(location,"Location is null!");
        Validate.notNull(location.getWorld(),"World is null!");
        this.armorStand= (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        this.armorStand.setGravity(false);
        this.armorStand.setVisible(false);

        for(EquipmentSlot equipmentSlot:EquipmentSlot.values()){
            this.armorStand.addEquipmentLock(equipmentSlot, ArmorStand.LockType.ADDING_OR_CHANGING);
        }

        this.armorStand.setCustomNameVisible(true);
        this.armorStand.setCustomName(getText());
        this.armorStand.setRemoveWhenFarAway(false);



    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public void setArmorStand(ArmorStand armorStand) {
        this.armorStand = armorStand;
    }

    public void setColorText(String text){
        setText(ChatColor.translateAlternateColorCodes('&',text));
    }

    public void setText(String text) {
        this.text = text;

        if(armorStand!=null){
            this.armorStand.setCustomName(getText());
        }
    }

    public String getText() {
        return text;
    }
}
