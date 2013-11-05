/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.arcanerealm.holidays.users;

import info.jeppes.ZoneCore.Users.ZoneUserManager;
import info.jeppes.ZoneCore.ZoneConfig;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

public class HolidayUserManager extends ZoneUserManager<HolidayUser>{
    public HolidayUserManager(Plugin plugin, ZoneConfig usersConfig) {
        super(plugin, usersConfig);
    }
    
    @Override
    public HolidayUser loadUser(String userName, ConfigurationSection config) {
        return new HolidayUser(userName,config);
    }
}
