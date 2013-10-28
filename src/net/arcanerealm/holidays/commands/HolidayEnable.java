package net.arcanerealm.holidays.commands;
/*
* @author Kenny Williams
*/

import net.arcanerealm.holidays.HolidaysAPI;
import net.arcanerealm.holidays.framework.Holiday;
import net.vectorgaming.vcore.framework.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HolidayEnable extends SubCommand
{
    public HolidayEnable()
    {
        super("enable", HolidaysAPI.getPlugin());
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
        h.enable();
        cs.sendMessage(ChatColor.GREEN+"Enabled holiday "+ChatColor.YELLOW+h.getName()+ChatColor.GREEN+" in world "+ChatColor.YELLOW+h.getWorld().getName());
    }

    @Override
    public String getDescription()
    {
        return "Enables a holiday";
    }

    @Override
    public String getUsage()
    {
        return "/h enable <holiday>";
    }

    @Override
    public String getPermission()
    {
        return "holidays.enable";
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
