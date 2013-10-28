package net.arcanerealm.holidays.commands;
/*
* @author Kenny Williams
*/

import net.arcanerealm.holidays.HolidaysAPI;
import net.vectorgaming.vcore.VCoreAPI;
import net.vectorgaming.vcore.framework.commands.VCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HolidayBase extends VCommand
{
    public HolidayBase()
    {
        super("holidays", HolidaysAPI.getPlugin());
        addAlias("h");
        addAlias("holiday");
        addSubCommand(new HolidayEnable());
        addSubCommand(new HolidayDisable());
        addSubCommand(new HolidayList());
    }
    @Override
    public void run(CommandSender cs, String[] args)
    {
        cs.sendMessage(VCoreAPI.getColorScheme().getTitleBar("Holidays"));
        cs.sendMessage(ChatColor.GREEN+"Type "+VCoreAPI.getColorScheme().getArgumentColor()+"/h help "+ChatColor.GREEN+"for a list of commands.");
    }

    @Override
    public String getDescription()
    {
        return "Holidays main menu";
    }

    @Override
    public String getUsage()
    {
        return "/h <subcommand>";
    }

    @Override
    public String getPermission()
    {
        return "holidays.help";
    }

    @Override
    public Integer getMinArgsLength()
    {
        return 0;
    }

    @Override
    public Integer getMaxArgsLength()
    {
        return -1;
    }

    @Override
    public boolean isPlayerOnlyCommand()
    {
        return false;
    }
}
