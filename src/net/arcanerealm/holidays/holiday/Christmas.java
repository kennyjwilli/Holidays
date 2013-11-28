package net.arcanerealm.holidays.holiday;

import info.jeppes.ZoneCore.ZoneLocation;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import net.arcanerealm.holidays.HolidaysAPI;
import net.arcanerealm.holidays.framework.Holiday;
import net.arcanerealm.holidays.users.HolidayUser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Christmas extends Holiday implements Listener{

    private ArrayList<ItemStack> gifts = new ArrayList();
    private ZoneLocation christmasTreeLocation = new ZoneLocation("spawn_christmas_2013",-942,65,2013);
    private Color[] colors = new Color[]{
        Color.AQUA,
        Color.BLACK,
        Color.BLUE,
        Color.FUCHSIA,
        Color.GRAY,
        Color.GREEN,
        Color.LIME,
        Color.MAROON,
        Color.NAVY,
        Color.OLIVE,
        Color.ORANGE,
        Color.PURPLE,
        Color.RED,
        Color.SILVER,
        Color.TEAL,
        Color.WHITE,
        Color.YELLOW
    };
    
    public Christmas() {
        super("Christmas", Bukkit.getWorld("world"));
        initGifts();
        Bukkit.getServer().getPluginManager().registerEvents(this, HolidaysAPI.getPlugin());
    }
    
    private void initGifts(){
        for(byte i = 0; i < 16; i++){
            ItemStack itemStack = new ItemStack(Material.WOOL, 64, i);
            itemStack.setAmount(64);
            gifts.add(itemStack);
        }
        gifts.add(new ItemStack(Material.GOLDEN_APPLE,5));
        gifts.add(new ItemStack(Material.GOLDEN_APPLE,1,(short)1));
        gifts.add(new ItemStack(Material.COOKIE,64));
        gifts.add(new ItemStack(Material.CAKE,24));
        gifts.add(new ItemStack(Material.DIAMOND,3));
        gifts.add(new ItemStack(Material.GOLD_INGOT,10));
        gifts.add(new ItemStack(Material.IRON_INGOT,10));
        gifts.add(new ItemStack(Material.DRAGON_EGG,1));
        gifts.add(new ItemStack(Material.EMERALD,10));
    }
    
    public static boolean isChristmas(){
        Calendar currentCalendar = new GregorianCalendar();
        int currentDayOfMonth = currentCalendar.get(Calendar.DAY_OF_MONTH);
        if(currentCalendar.get(Calendar.MONTH) == 10){
            if(currentDayOfMonth <= 24){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void enable()
    {
        super.enable();
    }

    @Override
    public void disable()
    {
        super.disable();
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerMove(PlayerMoveEvent event){
        if(event.getTo().getWorld().getName().equalsIgnoreCase(christmasTreeLocation.getWorldName())){
            if(christmasTreeLocation.distance(event.getTo()) <= 10){
                HolidayUser user = HolidaysAPI.getUserManager().getUser(event.getPlayer());
                long lastGiftRecieved = user.getConfig().getLong("christmas.last-gift-recieved");
                Calendar calendar = new GregorianCalendar();
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                Calendar calendar2 = new GregorianCalendar();
                calendar2.setTime(new Date(lastGiftRecieved));
                int dayOfMonth2 = calendar2.get(Calendar.DAY_OF_MONTH);
                if(dayOfMonth != dayOfMonth2){
                    giveGift(user);
                }
            }
        }
        
        
//        Player p = event.getPlayer();
//        if(isChristmas()){
//            HolidayUser user = HolidaysAPI.getUserManager().getUser(p);
//            if(user.getConfig().contains("christmas.last-gift-recieved")){
//                long lastGiftRecieved = user.getConfig().getLong("christmas.last-gift-recieved");
//                Calendar calendar = new GregorianCalendar();
//                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
//                Calendar calendar2 = new GregorianCalendar();
//                calendar2.setTime(new Date(lastGiftRecieved));
//                int dayOfMonth2 = calendar2.get(Calendar.DAY_OF_MONTH);
//                if(dayOfMonth != dayOfMonth2){
//                    giveGift(user);
//                }
//            } else {
//                giveGift(user);
//            }
//        }
    }
    
    @EventHandler
     public void onBlockForm(EntityBlockFormEvent event){
        if(event.getEntity().getType() == EntityType.SNOWMAN){
            event.setCancelled(true);
//            final Location location = event.getBlock().getLocation();
//            Bukkit.getScheduler().runTaskLater(HolidaysAPI.getPlugin(), new Runnable(){
//                @Override
//                public void run() {
//                    location.getBlock().setType(Material.SNOW);
//                    location.getBlock().setType(Material.AIR);
//                }
//            },3);
        }
    }
    
    public void giveGift(Player player){
        giveGift(HolidaysAPI.getUserManager().getUser(player));
    }
    public void giveGift(HolidayUser user){
        if(user.getPlayer().getWorld().getPVP()){
            return;
        }
        user.getConfig().set("christmas.last-gift-recieved", System.currentTimeMillis());
        HolidaysAPI.getUserManager().getUsersConfig().save();
        
        ItemStack gift = getRandomGift();
        
        user.getPlayer().getInventory().addItem(gift);
        user.sendMessage(ChatColor.GREEN+"You found a present under the tree with your name on!");
        user.sendMessage(ChatColor.GREEN+"Inside the present, you find "+gift.getAmount()+" "+gift.getType().name().toLowerCase()+"");
    }

    public ItemStack getRandomGift(){
        int n = (int)(Math.random() * (double)(gifts.size() + 5d));
        if(n < gifts.size()){
            return gifts.get(n);
        } else {
            return getRandomFirework();
        }
    }
    
    public ItemStack getRandomFirework(){
        ItemStack firework = new ItemStack(Material.FIREWORK);
        FireworkMeta fm = (FireworkMeta) firework.getItemMeta();

        FireworkEffect.Builder builder = FireworkEffect.builder();
        //Trail
        builder.trail(Math.random() < 0.5);
        //Flicker
        builder.flicker(Math.random() < 0.5);
        //Explosion type
        FireworkEffect.Type type = FireworkEffect.Type.values()[(int)(Math.random() * FireworkEffect.Type.values().length)];
        if(type.equals(FireworkEffect.Type.CREEPER)){
            type = FireworkEffect.Type.BURST;
        }
        builder.with(type);
        //Color
        int amountOfColors = (int)(Math.random() * 10d) - 5;
        if(amountOfColors <= 0){
            amountOfColors = 1;
        }
        for(int i = 0; i < amountOfColors; i++){
            builder.withColor(colors[(int)(Math.random() * colors.length)]);
        }
        //Fade
        amountOfColors = (int)(Math.random() * 10d) - 5;
        if(amountOfColors <= 0){
            amountOfColors = 1;
        }
        for(int i = 0; i < amountOfColors; i++){
            builder.withFade(colors[(int)(Math.random() * colors.length)]);
        }
        FireworkEffect effect = builder.build();   
        int amountOfEffects = (int)(Math.random() * 7d) - 5;
        if(amountOfEffects <= 0){
            amountOfEffects = 1;
        }
        for(int i = 0 ; i < amountOfEffects; i++){
            fm.addEffect(effect);
        }
        fm.setPower((int)(Math.random() * 2d) + 2);
        firework.setItemMeta(fm);

        return firework;
    }
    
    @Override
    public void onJoin(Player p) {}
    @Override
    public void onQuit(Player p) {}

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event){
        if(isChristmas()){
            if(event.getEntity().getWorld().getPVP()){
                return;
            }
            if(Math.random()*15 < 1){
                List<Entity> nearbyEntities = event.getEntity().getNearbyEntities(10, 10, 10);
                boolean spawn = true;
                for(Entity entity : nearbyEntities){
                    if(entity.getType() == EntityType.SNOWMAN){
                        spawn = false;
                        break;
                    }
                }
                if(spawn){
                    Entity snowman = event.getLocation().getWorld().spawnEntity(event.getLocation().add(0.2,0,0.2), EntityType.SNOWMAN);
                }
            }
            if(event.getEntityType() == EntityType.ZOMBIE){
                equipEntityAsSanta(event.getEntity());
            }
        }
    }
    
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
        if(event.getEntity().getWorld().getPVP()){
            return;
        }
        if (event instanceof EntityDamageByEntityEvent) {
            Entity attacker = ((EntityDamageByEntityEvent) event).getDamager();
            if (attacker instanceof Projectile) {
                if (attacker instanceof Snowball) {
                    if (
                        event.getEntity().getType() != EntityType.PLAYER && 
                        ((Snowball)attacker).getShooter().getType() == EntityType.SNOWMAN
                    ){
                        event.setDamage(4);
//                        if(event.getEntity() instanceof Creature){
//                            Creature defender = (Creature)event.getEntity();
//                            defender.setTarget(((Snowball)attacker).getShooter());
//                        }
                    }
                }

            }
        }
    }
//    @EventHandler
//    public void onPlayerInteract(PlayerInteractEvent event) {
//        if(!event.getPlayer().getWorld().getPVP()){
//            return;
//        }
//        if(event.getPlayer().getGameMode() != GameMode.CREATIVE){
//            if(event.getPlayer().getItemInHand().getType() == Material.SNOW_BALL){
//                event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount() + 1);
//            }
//        }
//    }
    
    public void equipEntityAsSanta(LivingEntity entity){
        EntityEquipment equipment = entity.getEquipment();
        
        boolean useHelmet = true;
        if(entity instanceof Zombie){
            if(((Zombie)entity).isBaby()){
                useHelmet = false;
            }
        }
        if(useHelmet){
            equipment.setHelmet(getSantaHead());
            equipment.setHelmetDropChance(0f);
        }
        
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS,1);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta)boots.getItemMeta();
        bootsMeta.setColor(Color.fromBGR(0, 0, 0));
        boots.setItemMeta(bootsMeta);
        equipment.setBoots(boots);
        
        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS,1);
        LeatherArmorMeta legsMeta = (LeatherArmorMeta)legs.getItemMeta();
        legsMeta.setColor(Color.fromBGR(0, 0, 255));
        legs.setItemMeta(legsMeta);
        equipment.setLeggings(legs);
        
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE,1);
        LeatherArmorMeta chestMeta = (LeatherArmorMeta)chest.getItemMeta();
        chestMeta.setColor(Color.fromBGR(0, 0, 255));
        chest.setItemMeta(chestMeta);
        equipment.setChestplate(chest);
        
        equipment.setItemInHand(getMagicSnowball());
        equipment.setItemInHandDropChance(0.1f);
    }
    
    public ItemStack getSantaHead(){
        ItemStack skull = new ItemStack(Material.SKULL_ITEM,1,(short)3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner("Santa314");
        meta.setDisplayName(ChatColor.RED+"Santa Claus's Head");
        ArrayList<String> lore = new ArrayList();
        lore.add("This head could be obtained by");
        lore.add("the players during the christmas event");
        meta.setLore(lore);
        skull.setItemMeta(meta);
        return skull;
    }
    public ItemStack getMagicSnowball(){
        ItemStack snowball = new ItemStack(Material.SNOW_BALL,1);
        snowball.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 10);
        ItemMeta meta = snowball.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE+"Magic Snowball");
        ArrayList<String> lore = new ArrayList();
        lore.add(ChatColor.GREEN+ChatColor.ITALIC.toString()+"Unlimited snowball throws");
        lore.add("");
        lore.add(ChatColor.GREEN+ChatColor.ITALIC.toString()+"This snowball could be obtained by");
        lore.add(ChatColor.GREEN+ChatColor.ITALIC.toString()+"players during the christmas event");
        meta.setLore(lore);
        snowball.setItemMeta(meta);
        return snowball;
    }
    
    //Code is not needed, as snowmen melt on their own in rain, so they should despawn on their own
    //after the holiday is over
//    @EventHandler
//    public void onChunkLoad(ChunkLoadEvent event){
//        
//    }
//    @EventHandler
//    public void onChunkUnload(ChunkUnloadEvent event){
//        Entity[] entities = event.getChunk().getEntities();
//        for(Entity entity : entities){
//            if(entity.getType() == EntityType.SNOWMAN){
//                entity.remove();
//            }
//        }
//    }
    
}
