package net.arcanerealm.holidays.holiday;
/*
* @author Kenny Williams
*/

import net.arcanerealm.holidays.HolidaysAPI;
import net.arcanerealm.holidays.framework.Holiday;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftBat;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Halloween extends Holiday
{
    private HashMap<Player, Integer> randomSounds = new HashMap<>();
    private HashMap<Player, Integer> musicLoop = new HashMap<>();
    private int timeToRepeat = 0;
    private int id_storm;
    private ArrayList<Integer> taskIds = new ArrayList<>();

    public Halloween()
    {
        super("halloween", Bukkit.getWorld("world"));
    }

    @Override
    public void enable()
    {
        super.enable();
        beginLightningStrikes();
    }

    public void disable()
    {
        super.disable();
        for(int task : taskIds)
        {
            Bukkit.getScheduler().cancelTask(task);
        }
    }

    @Override
    public void onJoin(final Player p)
    {
        //if(!p.getWorld() == getWorld()) return;

        if(!isEnabled()) return;

        //play some initial scary sounds
        p.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 30, -20);
        p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 25, -20);
        p.playSound(p.getLocation(), Sound.AMBIENCE_CAVE, 100, 1);

        //Plays a random scary disk after the beginning sounds are over
        int music_id = Bukkit.getScheduler().scheduleSyncRepeatingTask(HolidaysAPI.getPlugin(), new Runnable()
        {
            @Override
            public void run()
            {
                p.playEffect(p.getLocation(), Effect.RECORD_PLAY, getRandomScaryDisk());
            }
        }, 200L, 3600L);
        musicLoop.put(p, music_id);
        taskIds.add(music_id);

        //Spawns bats
        CraftPlayer cp = (CraftPlayer) p;
        for(int i = 0; i < 10; i++)
        {
            double x = (Math.random() * 5) + p.getLocation().getBlockX();
            double y = (Math.random() * 5) + p.getLocation().getBlockY();
            double z = (Math.random() * 5) + p.getLocation().getBlockZ();
            CraftBat bat = (CraftBat) getWorld().spawnCreature(new Location(getWorld(), x, y, z), EntityType.BAT);
            bat.getHandle().setGoalTarget(cp.getHandle());
        }

        //Loop for 50% probability of playing a scary sound
        int task_id = Bukkit.getScheduler().scheduleSyncRepeatingTask(HolidaysAPI.getPlugin(), new Runnable()
        {
            @Override
            public void run()
            {
                if(Math.random() > 0.5D)
                {
                    p.playSound(p.getLocation(), getRandomScarySound(), (int) (Math.random() * 30), 1);
                }
            }
        }, 600L, 1200L);
        randomSounds.put(p, task_id);
        taskIds.add(task_id);
    }

    @Override
    public void onQuit(Player p)
    {
        if(randomSounds.containsKey(p)) Bukkit.getScheduler().cancelTask(randomSounds.get(p));
    }

    private Sound getRandomScarySound()
    {
        return getScarySounds().get( (int)( Math.random() * getScarySounds().size() ) );
    }

    private ArrayList<Sound> getScarySounds()
    {
        ArrayList<Sound> result = new ArrayList<>();

        result.add(Sound.AMBIENCE_CAVE);
        result.add(Sound.ENDERMAN_SCREAM);
        result.add(Sound.AMBIENCE_THUNDER);
        result.add(Sound.ENDERDRAGON_GROWL);
        result.add(Sound.GHAST_SCREAM);
        result.add(Sound.GHAST_SCREAM2);
        result.add(Sound.WITHER_DEATH);
        result.add(Sound.WITHER_IDLE);
        result.add(Sound.WITHER_SPAWN);
        result.add(Sound.WITHER_IDLE);

        return result;
    }

    private int getRandomScaryDisk()
    {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(2256);
        list.add(2262);
        list.add(2266);
        list.add(2265);
        list.add(2264);
        int i = (int) (Math.random() * list.size());
        return list.get(i);
    }

    private int createLightning(final Location loc, long delay, long repeat)
    {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(HolidaysAPI.getPlugin(), new Runnable()
        {
            @Override
            public void run()
            {
                int x = loc.getBlockX();
                int z = loc.getBlockZ();

                if(Math.random() > 0.5D)
                {
                    x += (100 * Math.random());
                }else
                {
                    x -= (100 * Math.random());
                }

                if(Math.random() > 0.5D)
                {
                    z += (100 * Math.random());
                }else
                {
                    z -= (100 * Math.random());
                }

                loc.getWorld().strikeLightningEffect(new Location(loc.getWorld(), x, loc.getBlockY(), z));
            }
        }, delay, repeat);
    }

    private void beginLightningStrikes()
    {
        taskIds.add(createLightning(new Location(getWorld(), -942, 65, 1976), 20L, 1200L));
        Bukkit.getScheduler().scheduleSyncRepeatingTask(HolidaysAPI.getPlugin(), new Runnable()
        {
            @Override
            public void run()
            {
                if(timeToRepeat == 0)
                {
                    taskIds.add(id_storm = createLightning(new Location(getWorld(), -942, 65, 1976), 0L, 10L));
                }
                if(timeToRepeat == 1)
                {
                    Bukkit.getScheduler().cancelTask(id_storm);
                }
                timeToRepeat += 1;
                if(timeToRepeat == 8) timeToRepeat = 0;
            }
        }, 1200L, 2400L);
    }
}