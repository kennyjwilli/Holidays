package net.arcanerealm.holidays.listeners;
/*
* @author Kenny Williams
*/

import net.arcanerealm.holidays.HolidaysAPI;
import net.arcanerealm.holidays.framework.Holiday;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener
{
    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player p = event.getPlayer();
        for(Holiday h : HolidaysAPI.getEnabledHolidays())
        {
            h.onJoin(p);
        }
    }
}
