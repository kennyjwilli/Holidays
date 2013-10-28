package net.arcanerealm.holidays;
/*
* @author Kenny Williams
*/

import net.arcanerealm.holidays.commands.HolidayBase;
import net.arcanerealm.holidays.framework.Holiday;
import net.arcanerealm.holidays.holiday.Halloween;
import net.arcanerealm.holidays.listeners.PlayerJoin;
import net.arcanerealm.holidays.listeners.PlayerQuit;
import net.vectorgaming.vcore.framework.VertexAPI;
import net.vectorgaming.vcore.framework.VertexPlugin;
import net.vectorgaming.vcore.framework.commands.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class Holidays extends VertexPlugin
{
    HolidaysAPI api;

    @Override
    public void onEnable()
    {
        api = new HolidaysAPI(this);
        setupCommands();
        setupListeners();
        registerHolidays();
    }

    @Override
    public void onDisable()
    {

    }

    @Override
    public void setupCommands()
    {
        CommandManager.registerCommand(new HolidayBase());
    }

    private  void setupListeners()
    {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new PlayerJoin(), this);
        pm.registerEvents(new PlayerQuit(), this);
    }

    private void registerHolidays()
    {
        Holiday halloween = new Halloween();
        HolidaysAPI.registerHoliday(halloween);
    }

    @Override
    public Plugin getPlugin()
    {
        return this;
    }

    @Override
    public VertexAPI getAPI()
    {
        return api;
    }
}
