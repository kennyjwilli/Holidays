package net.arcanerealm.holidays.commands;
/*
* @author Kenny Williams
*/

import net.arcanerealm.holidays.HolidaysAPI;
import net.vectorgaming.vcore.VCoreAPI;
import net.vectorgaming.vcore.framework.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HolidayList extends SubCommand
{
    public HolidayList()
    {
        super("list", HolidaysAPI.getPlugin());
    }

    @Override
    public void run(CommandSender cs, String[] args)
    {
        String holidays = "";
        boolean first = false;
        int count = 1;
        for(String s : HolidaysAPI.getEnabledHolidayNames())
        {
            if(first)
            {
                holidays += ChatColor.WHITE+", ";
            }else
            {
                first = true;
            }
            if(count % 2 == 0)
            {
                holidays += ChatColor.AQUA+s;
            }else
            {
                holidays += ChatColor.YELLOW+s;
            }
        }

        cs.sendMessage(VCoreAPI.getColorScheme().getTitleBar("Holidays"));
        cs.sendMessage(ChatColor.BOLD+""+ChatColor.DARK_BLUE+"Available Holidays: "+ChatColor.RESET+holidays);

    }

    @Override
    public String getDescription()
    {
        return "Lists all available holidays";
    }

    @Override
    public String getUsage()
    {
        return "/h list";
    }

    @Override
    public String getPermission()
    {
        return "holidays.list";
    }

    @Override
    public Integer getMinArgsLength()
    {
        return 0;
    }

    @Override
    public Integer getMaxArgsLength()
    {
        return 0;
    }

    @Override
    public boolean isPlayerOnlyCommand()
    {
        return false;
    }
}
