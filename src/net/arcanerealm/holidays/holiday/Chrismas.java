package net.arcanerealm.holidays.holiday;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import net.arcanerealm.holidays.HolidaysAPI;
import net.arcanerealm.holidays.framework.Holiday;
import net.arcanerealm.holidays.users.HolidayUser;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

public class Chrismas extends Holiday implements Listener{

    private ArrayList<ItemStack> gifts = new ArrayList();
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
    
    public Chrismas() {
        super("Chrismas", null);
        initGifts();
    }
    
    private void initGifts(){
        for(byte i = 0; i < 16; i++){
            ItemStack itemStack = new ItemStack(Material.WOOL, 64, i);
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
    
    @Override
    public void onJoin(Player p) {
        Calendar currentCalendar = new GregorianCalendar();
        int currentDayOfMonth = currentCalendar.get(Calendar.DAY_OF_MONTH);
        if(currentCalendar.get(Calendar.MONTH) == 11){
            if(currentDayOfMonth <= 24){
                HolidayUser user = HolidaysAPI.getUserManager().getUser(p);
                ConfigurationSection config = user.getConfig().getConfigurationSection("chrismas");
                if(config != null){
                    if(config.contains("last-gift-recieved")){
                        long lastGiftRecieved = config.getLong("last-gift-recieved");
                        Calendar calendar = new GregorianCalendar();
                        calendar.setTime(new Date(lastGiftRecieved));
                        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                        if(dayOfMonth != currentDayOfMonth){
                            giveGift(user);
                        }
                    } else {
                        giveGift(user);
                    }
                }
            }
        }
    }
    
    public void giveGift(Player player){
        giveGift(HolidaysAPI.getUserManager().getUser(player));
    }
    public void giveGift(HolidayUser user){
        ConfigurationSection config = user.getConfig().getConfigurationSection("chrismas");
        config.set("last-gift-recieved", System.currentTimeMillis());
        
        ItemStack gift = getRandomGift();
        
        user.giveItemWhenOnline(gift);
        user.sendMesssageWhenOnline(ChatColor.GREEN+"You have recieved "+gift.getAmount()+""+gift.getType().name().toLowerCase()+" in your daily present!");
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
    public void onQuit(Player p) {
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event){
        List<Entity> nearbyEntities = event.getEntity().getNearbyEntities(10, 10, 10);
        for(Entity entity : nearbyEntities){
            if(entity.getType() == EntityType.SNOWMAN){
                return;
            }
        }
        if(Math.random()*5 < 1){
            Entity snowman = event.getLocation().getWorld().spawnEntity(event.getLocation(), EntityType.SNOWMAN);
        }
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
