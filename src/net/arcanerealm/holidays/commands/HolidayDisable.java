package net.arcanerealm.holidays.commands;
/*
* @author Kenny Williams
*/

import net.arcanerealm.holidays.HolidaysAPI;
import net.arcanerealm.holidays.framework.Holiday;
import net.vectorgaming.vcore.framework.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HolidayDisable extends SubCommand
{
    public HolidayDisable()
    {
        super("disable", HolidaysAPI.getPlugin());
    }
    @Override
    public void run(CommandSender cs, String[] args)
    {
        if(!HolidaysAPI.holidayExists(args[0]))
        {
            sendErrorMessage(cs, "Holiday "+ ChatColor.YELLOW+args[0]+ChatColor.RED+" does not exist.");
            return;
        }
        Holiday h = HolidaysAPI.getHoliday(args[0]);
        h.disable();
        cs.sendMessage(ChatColor.RED+"Disabled holiday "+ChatColor.YELLOW+h.getName()+ChatColor.RED+" in world "+ChatColor.YELLOW+h.getWorld().getName());
    }

    @Override
    public String getDescription()
    {
        return "Disables a holiday";
    }

    @Override
    public String getUsage()
    {
        return "/h disable <holiday>";
    }

    @Override
    public String getPermission()
    {
        return "holidays.disable";
    }

    @Override
    public Integer getMinArgsLength()
    {
        return 1;
    }

    @Override
    public Integer getMaxArgsLength()
    {
        return 1;
    }

    @Override
    public boolean isPlayerOnlyCommand()
    {
        return false;
    }
}
