package tk.sakizciadam.tinynpc.nms.v1_16_R3.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import tk.sakizciadam.tinynpc.api.npc.goal.AttackGoal;
import tk.sakizciadam.tinynpc.api.npc.goal.LocationGoal;
import tk.sakizciadam.tinynpc.api.npc.impl.AbstractPlayerNPC;
import tk.sakizciadam.tinynpc.api.utils.CommonUtils;
import tk.sakizciadam.tinynpc.nms.v1_16_R3.goal.PathFinderAttack;
import tk.sakizciadam.tinynpc.nms.v1_16_R3.goal.PathFinderGoToLocation;
import tk.sakizciadam.tinynpc.nms.v1_16_R3.utils.NPCUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerNPC extends AbstractPlayerNPC {
    private NavigationEntity navigationEntity;
    private Location lastLoc;


    public PlayerNPC(String name) {
        super(name);
    }

    @Override
    public void despawn() {
        for(Player player:Bukkit.getOnlinePlayers()){

            if(!show.contains(player.getUniqueId())){
                continue;
            }

            despawnFor(player);

        }
    }

    public void setGravity(boolean v){
        this.navigationEntity.getBukkitEntity().setGravity(v);
    }

    @Override
    public Entity getBukkitEntity() {
        return ((EntityPlayer)getNMSEntity()).getBukkitEntity();
    }

    @Override
    public AttackGoal getAttackGoal() {
        return navigationEntity.pathFinderAttack;
    }

    @Override
    public LocationGoal getLocationGoal() {
        return navigationEntity.pathFinderGoToLocation;
    }

    public void despawnFor(Player player){

        PlayerConnection playerConnection=((CraftPlayer)player).getHandle().playerConnection;
        playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, (FakePlayer)getNMSEntity()));
        playerConnection.sendPacket(new PacketPlayOutEntityDestroy(getBukkitEntity().getEntityId()));

        show.remove(player.getUniqueId());
    }

    public void tick(){
        super.tick();

        for(UUID uuid:show){
            if(!Bukkit.getPlayer(uuid).isOnline()){
                show.remove(uuid);
            }
        }

        getBukkitEntity().teleport(navigationEntity.getBukkitEntity().getLocation());
        lastLoc=getBukkitEntity().getLocation();
    }

    public Location getLocation() {
        if(isSpawned()&&getBukkitEntity()!=null){
            if(getBukkitEntity().getLocation()!=null){
                return getBukkitEntity().getLocation();
            }
        }
        return lastLoc;
    }

    public void spawnFor(Player player){
        if(show.contains(player.getUniqueId())){
            CommonUtils.warn("%s is already in the show list",player.getUniqueId());
        }

        PlayerConnection playerConnection=((CraftPlayer)player).getHandle().playerConnection;
        FakePlayer entity=(FakePlayer)getNMSEntity();
        entity.getSpawnPackets().forEach(i -> playerConnection.sendPacket(i));

        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(this.navigationEntity.getBukkitEntity().getEntityId());
        playerConnection.sendPacket(packet);


        show.add(player.getUniqueId());
    }

    @Override
    public void spawn(Location location) {
        FakePlayer entity=new FakePlayer(this,location.getWorld(), getWrappedGameProfile()!=null ? NPCUtils.getGameProfile(getWrappedGameProfile()) : new GameProfile(UUID.randomUUID(),"Steve"));

        entity.playerConnection = new PlayerConnection(entity.server, new NetworkManager(EnumProtocolDirection.CLIENTBOUND), entity);
        setNMSEntity(entity);
        entity.teleport(location);
        spawnHologram();
        entity.getWorld().addEntity(entity);

        this.navigationEntity=new NavigationEntity(this,location.getWorld());
        this.navigationEntity.teleport(entity.getBukkitEntity().getLocation());

        this.navigationEntity.getWorld().addEntity(navigationEntity);
        lastLoc=location;



    }

    @Override
    public World getWorld() {
        return getBukkitEntity().getWorld();
    }

    @Override
    public int getID() {
        return getBukkitEntity().getEntityId();
    }

    public static class FakePlayer extends EntityPlayer {

        private final PlayerNPC parent;

        public FakePlayer(PlayerNPC parent, org.bukkit.World world, GameProfile gameProfile) {
            super(((CraftServer) Bukkit.getServer()).getServer(),((CraftWorld)world).getHandle(),gameProfile,new PlayerInteractManager(((CraftWorld)world).getHandle()));

            this.parent=parent;
        }

        public void tick(){
            super.tick();

        }

        public void teleport(Location location){


            if(this.getBukkitEntity()!=null){
                this.getBukkitEntity().teleport(location);
            } else {
                this.setPositionRotation(location.getX(),location.getY(),location.getZ(),location.getYaw(),location.getPitch());
            }


        }

        public List<Packet> getSpawnPackets() {
            List<Packet> packets=new ArrayList<>();
            this.getDataWatcher().set( DataWatcherRegistry.a.a(16), (byte)127);

            packets.add(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this));

            packets.add(new PacketPlayOutNamedEntitySpawn(this));

            packets.add(new PacketPlayOutEntityHeadRotation(this, (byte) ((this.yaw * 256f) / 360f)));

            packets.add(new PacketPlayOutEntityMetadata(this.getId(),this.getDataWatcher(),true));

            packets.add(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, this));

            List<Pair<EnumItemSlot, ItemStack>> pairs=new ArrayList<>();

            if(getBukkitEntity().getInventory().getBoots()!=null){
                Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack> pair = new Pair<>(EnumItemSlot.FEET, CraftItemStack.asNMSCopy(getBukkitEntity().getInventory().getBoots()));
                pairs.add(pair);
            }

            if(getBukkitEntity().getInventory().getLeggings()!=null){
                Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack> pair = new Pair<>(EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(getBukkitEntity().getInventory().getLeggings()));
                pairs.add(pair);
            }

            if(getBukkitEntity().getInventory().getChestplate()!=null){
                Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack> pair = new Pair<>(EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(getBukkitEntity().getInventory().getChestplate()));
                pairs.add(pair);
            }

            if(getBukkitEntity().getInventory().getHelmet()!=null){
                Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack> pair = new Pair<>(EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(getBukkitEntity().getInventory().getHelmet()));
                pairs.add(pair);
            }

            if(getBukkitEntity().getInventory().getItem(EquipmentSlot.HAND)!=null){
                Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack> pair = new Pair<>(EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(getBukkitEntity().getInventory().getItem(EquipmentSlot.HAND)));
                pairs.add(pair);
            }

            if(getBukkitEntity().getInventory().getItem(EquipmentSlot.OFF_HAND)!=null){
                Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack> pair = new Pair<>(EnumItemSlot.OFFHAND, CraftItemStack.asNMSCopy(getBukkitEntity().getInventory().getItem(EquipmentSlot.OFF_HAND)));
                pairs.add(pair);
            }

            if(!pairs.isEmpty()){
                PacketPlayOutEntityEquipment item = new PacketPlayOutEntityEquipment(this.getBukkitEntity().getEntityId(), pairs);
                packets.add(item);
            }

            return packets;
        }


    }

    public static class NavigationEntity extends EntityZombie {
        protected final PlayerNPC parent;

        private PathFinderGoToLocation pathFinderGoToLocation;
        private PathFinderAttack pathFinderAttack;

        public NavigationEntity(PlayerNPC parent, org.bukkit.World world) {

            super(EntityTypes.ZOMBIE, ((CraftWorld)world).getHandle());
            this.parent=parent;
            this.pathFinderGoToLocation.setParent(this.parent);
            this.pathFinderAttack.setParent(this.parent);
        }

        public void collide(net.minecraft.server.v1_16_R3.Entity entity) {


        }

        public void teleport(Location location){
            this.setPositionRotation(location.getX(),location.getY(),location.getZ(),location.getYaw(),location.getPitch());
        }

        public void playSound(SoundEffect sound, float f, float v){

        }

        public void initPathfinder(){

            this.goalSelector.a(0, pathFinderAttack= new PathFinderAttack(null,this,null,2f));
            this.goalSelector.a(1, pathFinderGoToLocation= new PathFinderGoToLocation(null,this,2f));
        }

        public void tick(){
            super.tick();
            if(getParent()!=null&&!getBukkitEntity().isDead()){
                getParent().tick();
            }
        }

        public PlayerNPC getParent() {
            return parent;
        }
    }
}
