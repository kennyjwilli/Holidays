package net.arcanerealm.holidays.listeners;
/*
* @author Kenny Williams
*/

import net.arcanerealm.holidays.HolidaysAPI;
import net.arcanerealm.holidays.framework.Holiday;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener
{
    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        for(Holiday h : HolidaysAPI.getEnabledHolidays())
        {
            if(h.isEnabled()) h.onQuit(event.getPlayer());
        }
    }
}
