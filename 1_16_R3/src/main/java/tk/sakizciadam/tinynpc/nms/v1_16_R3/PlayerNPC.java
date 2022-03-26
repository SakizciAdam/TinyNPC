package tk.sakizciadam.tinynpc.nms.v1_16_R3;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import tk.sakizciadam.tinynpc.api.npc.gameprofile.WrappedGameProfile;
import tk.sakizciadam.tinynpc.api.utils.CommonUtils;
import tk.sakizciadam.tinynpc.nms.v1_16_R3.utils.GameProfileUtils;

import java.util.*;

public class PlayerNPC extends WrapperNPC implements tk.sakizciadam.tinynpc.api.npc.PlayerNPC {
    private FakePlayer entity;
    private NavigationEntity navigationEntity;
    private UUID uuid;
    private String name;
    private WrappedGameProfile wrappedGameProfile;
    private boolean showOnJoin;

    public List<UUID> show=new ArrayList<>();

    public PlayerNPC(){
        this.uuid=UUID.randomUUID();
        this.name="Steve";
    }

    public boolean canSee(Player player){
        return show.contains(player.getUniqueId())&&isSpawned();
    }

    public void remove(){
        if(isSpawned()){
            despawn();
            getEntity().remove();
            navigationEntity.getBukkitEntity().remove();
            setNMSEntity(null);
            navigationEntity=null;
            getLib().getNpcManager().getNPCList().remove(this);

        }
    }

    @Override
    public void spawn(Location location) {
        this.entity=new FakePlayer(this,location.getWorld(), wrappedGameProfile!=null ? GameProfileUtils.get(wrappedGameProfile) : new GameProfile(UUID.randomUUID(),"Steve"));

        this.entity.playerConnection = new PlayerConnection(this.entity.server, new NetworkManager(EnumProtocolDirection.CLIENTBOUND), this.entity);
        setNMSEntity(entity);
        this.entity.teleport(location);
        this.entity.getWorld().addEntity(entity);


        this.navigationEntity=new NavigationEntity(this,location.getWorld());
        this.navigationEntity.teleport(this.entity.getBukkitEntity().getLocation());

        this.navigationEntity.getWorld().addEntity(navigationEntity);
        this.navigationEntity.path.setWrapperNPC(this);

        if(getHologram()!=null){
            getHologram().spawn(getEntity().getLocation());
        }


    }

    @Override
    public float getNavigationSpeed() {
        return navigationEntity.path.getSpeed();
    }

    @Override
    public void setNavigationSpeed(float s) {
        navigationEntity.path.setSpeed(s);
    }

    public WrappedGameProfile getWrappedGameProfile(){
        return wrappedGameProfile;
    }

    @Override
    public void setShowOnJoin(boolean b) {
        this.showOnJoin=b;
    }

    @Override
    public boolean showOnJoin() {
        return this.showOnJoin;
    }

    public void setWrappedGameProfile(WrappedGameProfile wrappedGameProfile) {
        this.wrappedGameProfile = wrappedGameProfile;
    }

    public void showEverybody(){
        for(Player player:Bukkit.getOnlinePlayers()){
            spawnFor(player);
        }

    }

    @Override
    public void goTo(Location location) {
        if(navigationEntity.path!=null){
            navigationEntity.path.setTarget(location);

        }
    }

    @Override
    public void setCollision(boolean v) {
        this.entity.setCollides(v);
    }

    public void despawn(){
        for(Player player:Bukkit.getOnlinePlayers()){

            if(!show.contains(player.getUniqueId())){
                continue;
            }

            despawnFor(player);

        }

    }

    @Override
    public GameMode getGamemode() {
        return entity.getBukkitEntity().getGameMode();
    }

    public void setGamemode(GameMode gamemode){
        entity.getBukkitEntity().setGameMode(gamemode);
    }

    public List<UUID> getShowList() {
        return show;
    }

    public void despawnFor(Player player){

        PlayerConnection playerConnection=((CraftPlayer)player).getHandle().playerConnection;
        playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entity));
        playerConnection.sendPacket(new PacketPlayOutEntityDestroy(this.entity.getBukkitEntity().getEntityId()));

        show.remove(player.getUniqueId());
    }

    public void update(){
        for(UUID uuid:show){
            if(Bukkit.getPlayer(uuid).isOnline()){
                Player player=Bukkit.getPlayer(uuid);

                despawnFor(player);
                spawnFor(player);
            }
        }
    }

    public void spawnFor(Player player){
        if(show.contains(player.getUniqueId())){
            CommonUtils.warn("");
        }

        PlayerConnection playerConnection=((CraftPlayer)player).getHandle().playerConnection;

        this.entity.getSpawnPackets().forEach(i -> playerConnection.sendPacket(i));

        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(this.navigationEntity.getBukkitEntity().getEntityId());
        playerConnection.sendPacket(packet);
        Player npcPlayer=(Player)this.getEntity();

        List<Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack>> pairs=new ArrayList<>();

        if(npcPlayer.getInventory().getBoots()!=null){
            Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack> pair = new Pair<>(EnumItemSlot.FEET, CraftItemStack.asNMSCopy(npcPlayer.getInventory().getBoots()));
            pairs.add(pair);
        }

        if(npcPlayer.getInventory().getLeggings()!=null){
            Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack> pair = new Pair<>(EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(npcPlayer.getInventory().getLeggings()));
            pairs.add(pair);
        }

        if(npcPlayer.getInventory().getChestplate()!=null){
            Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack> pair = new Pair<>(EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(npcPlayer.getInventory().getChestplate()));
            pairs.add(pair);
        }

        if(npcPlayer.getInventory().getHelmet()!=null){
            Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack> pair = new Pair<>(EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(npcPlayer.getInventory().getHelmet()));
            pairs.add(pair);
        }

        if(npcPlayer.getInventory().getItem(EquipmentSlot.HAND)!=null){
            Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack> pair = new Pair<>(EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(npcPlayer.getInventory().getItem(EquipmentSlot.HAND)));
            pairs.add(pair);
        }

        if(npcPlayer.getInventory().getItem(EquipmentSlot.OFF_HAND)!=null){
            Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack> pair = new Pair<>(EnumItemSlot.OFFHAND, CraftItemStack.asNMSCopy(npcPlayer.getInventory().getItem(EquipmentSlot.OFF_HAND)));
            pairs.add(pair);
        }

        if(!pairs.isEmpty()){
            PacketPlayOutEntityEquipment item = new PacketPlayOutEntityEquipment(entity.getBukkitEntity().getEntityId(), pairs);
            playerConnection.sendPacket(item);
        }
        show.add(player.getUniqueId());
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public static class NavigationEntity extends EntityZombie {

        private final PlayerNPC parent;
        public PathFinderGoToLocation path;

        public NavigationEntity(PlayerNPC parent, org.bukkit.World world) {
            super(EntityTypes.ZOMBIE, ((CraftWorld)world).getHandle());
            this.parent=parent;
        }



        public void playSound(SoundEffect f,float f1,float f2){

        }

        public boolean damageEntity(DamageSource damageSource,float f){

            if(parent.entity.isInvulnerable()) return false;

            if(parent.entity.damageEntity(damageSource, f)){

            }


            return false;
        }

        public boolean canCollideWith(Entity entity) {
            return false;
        }

        public void teleport(Location location){


            this.setPositionRotation(location.getX(),location.getY(),location.getZ(),location.getYaw(),location.getPitch());

        }

        @Override
        public void initPathfinder(){

            path=new PathFinderGoToLocation(null,this, 1);
            this.goalSelector.a(0,path);


        }

        public boolean isInvisible(){
            return true;
        }

        public boolean isInvulnerable(){
            return true;
        }


    }

    public static class FakePlayer extends EntityPlayer {

        private final PlayerNPC parent;

        public FakePlayer(PlayerNPC parent, org.bukkit.World world,GameProfile gameProfile) {
            super(((CraftServer)Bukkit.getServer()).getServer(),((CraftWorld)world).getHandle(),gameProfile,new PlayerInteractManager(((CraftWorld)world).getHandle()));

            this.parent=parent;
        }

        public void setCollides(boolean v){
            collides=v;
        }


        public boolean damageEntity(DamageSource d,float f){
            if(!isInvulnerable()) return super.damageEntity(d,f);

            return false;
        }

        public void tick(){
            super.tick();

            if(parent!=null){
                if(parent.navigationEntity!=null){
                    getBukkitEntity().teleport(parent.navigationEntity.getBukkitEntity().getLocation());
                    setHeadRotation(parent.navigationEntity.getHeadRotation());

                }



                if(parent.isSpawned()){
                    parent.lastLoc=getBukkitEntity().getLocation();

                    if(parent.getHologram()!=null){
                        parent.teleportHologram();
                    }
                }
            }
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


            packets.add(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this));

            packets.add(new PacketPlayOutNamedEntitySpawn(this));

            packets.add(new PacketPlayOutEntityHeadRotation(this, (byte) ((this.yaw * 256f) / 360f)));

            packets.add(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, this));

            return packets;
        }


    }
}
