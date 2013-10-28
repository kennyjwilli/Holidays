package net.arcanerealm.holidays.framework;
/*
* @author Kenny Williams
*/

import org.bukkit.World;
import org.bukkit.entity.Player;

public abstract class Holiday
{
    private boolean enabled = false;
    private World world;
    private String name;

    public Holiday(String name, World world)
    {
        this.name = name;
        this.world = world;
    }

    public String getName()
    {
        return name;
    }

    public World getWorld()
    {
        return world;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void enable()
    {
        enabled = true;
    }
    public void disable()
    {
        enabled = false;
    }

    public abstract void onJoin(Player p);

    public abstract void onQuit(Player p);
}
