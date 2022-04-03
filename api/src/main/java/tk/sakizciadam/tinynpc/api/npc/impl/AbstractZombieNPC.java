package tk.sakizciadam.tinynpc.api.npc.impl;

public abstract class AbstractZombieNPC extends AbstractNPC{
    private ZombieType zombieType;
    private boolean tempBaby;

    public AbstractZombieNPC(String name) {
        super(name);
        this.zombieType=ZombieType.ZOMBIE;
    }

    public void setZombieType(ZombieType zombieType) {
        this.zombieType = zombieType;
    }

    public ZombieType getZombieType() {
        return zombieType;
    }

    public void setBaby(boolean baby){
        this.tempBaby=baby;
    }

    public boolean isTempBaby() {
        return tempBaby;
    }

    public abstract boolean isBaby();

    public void despawn(){
        if(getHologram()!=null&&getHologram().getArmorStand()!=null){
            getHologram().getArmorStand().remove();
            getHologram().setArmorStand(null);
        }
    }

    public enum ZombieType {
        DROWNED,ZOMBIE,ZOMBIE_VILLAGER
    }
}
