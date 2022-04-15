package tk.sakizciadam.tinynpc.plugin.command;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.loot.LootTables;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import tk.sakizciadam.tinynpc.nms.v1_16_R3.npc.GolemNPC;
import tk.sakizciadam.tinynpc.plugin.TinyNPCPlugin;


public class GolemKing extends GolemNPC implements Listener {
    private Player target;
    private int laserTick=0,spawnTick,spawned;

    public GolemKing(String name) {
        super(name);

        setHologram(ChatColor.translateAlternateColorCodes('&',getDisplayName()));
        Bukkit.getPluginManager().registerEvents(this, TinyNPCPlugin.getTinyNPC());
    }

    @Override
    public void spawn(Location location) {
        super.spawn(location);

        ((IronGolem)getLivingEntity()).setLootTable(LootTables.EMPTY.getLootTable());

        spawnHologram();
        setMaxHealth(200d);
        getLivingEntity().setHealth(200d);
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public Player getTarget() {
        return target;
    }

    public void tick(){
        super.tick();

        if(getHologram()!=null&&getHologram().getArmorStand()!=null){
            getHologram().setColorText(getDisplayName());
        }



        if(getTarget()!=null&&getTarget().isOnline()){
            lookAt(getTarget());
            laserTick++;
            spawnTick++;

            if(spawnTick>=20*4&&spawned<3){
                spawnTick=0;
                spawned++;
                Bukkit.getScheduler().runTaskLater(TinyNPCPlugin.getTinyNPC(),() -> {
                    GolemApp golemApp=(GolemApp)TinyNPCPlugin.getTinyNPC().lib.getRegistry().createUnsafeNPC(GolemApp.class,"GolemApp");

                    golemApp.setTarget(getTarget());

                    golemApp.spawn(getLocation());
                },1l);
            }

            if(laserTick>=60){
                laserTick=0;
                laser();
                if(((IronGolem)getLivingEntity()).hasLineOfSight(getTarget())){
                    getTarget().damage(10);
                }

            }



        } else {
            despawn();
        }
    }

    public void onDeath(){
        super.onDeath();
        if(getTarget()!=null&&getTarget().isOnline()){
            getTarget().playSound(getTarget().getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH,1,2);

        }
    }


    public void laser(){
        Location origin = getLivingEntity().getEyeLocation();
        Vector direction = origin.getDirection();
        direction.multiply(10);
        Location destination = origin.clone().add(direction);

        direction.normalize();
        for (int i = 0; i < 10; i++) {
            Location loc = origin.add(direction);
            loc.getWorld().spawnParticle(Particle.SMOKE_LARGE,loc,10);
        }
    }

    public String getDisplayName(){
        return String.format("&cGolem King&7[&c%s&7/&c%s&7]",isSpawned() ? (int)getLivingEntity().getHealth() : 0,200);
    }
}
