package net.arcanerealm.holidays;
/*
* @author Kenny Williams
*/

import info.jeppes.ZoneCore.ZoneConfig;
import java.io.File;
import net.arcanerealm.holidays.commands.HolidayBase;
import net.arcanerealm.holidays.framework.Holiday;
import net.arcanerealm.holidays.holiday.Christmas;
import net.arcanerealm.holidays.holiday.Halloween;
import net.arcanerealm.holidays.listeners.PlayerJoin;
import net.arcanerealm.holidays.listeners.PlayerQuit;
import net.arcanerealm.holidays.listeners.SnowballListener;
import net.arcanerealm.holidays.users.HolidayUserManager;
import net.vectorgaming.vcore.VCore;
import net.vectorgaming.vcore.framework.VertexAPI;
import net.vectorgaming.vcore.framework.commands.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class Holidays extends VCore
{
    HolidaysAPI api;
    private HolidayUserManager userManager;

    @Override
    public void onEnable()
    {
        api = new HolidaysAPI(this);
        setupCommands();
        setupListeners();
        registerHolidays();
        userManager = new HolidayUserManager(this,new ZoneConfig(this,new File("plugins/holidays/users.yml")));
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
        pm.registerEvents(new SnowballListener(), this);
    }

    private void registerHolidays()
    {
        Holiday halloween = new Halloween();
        HolidaysAPI.registerHoliday(halloween);
        Christmas christmas = new Christmas();
        HolidaysAPI.registerHoliday(christmas);
    }

    @Override
    public Plugin getPlugin()
    {
        return this;
    }

    public VertexAPI getAPI()
    {
        return api;
    }

    public HolidayUserManager getUserManager() {
        return userManager;
    }
    
    
}
