package net.arcanerealm.holidays.users;

import info.jeppes.ZoneCore.Users.ZoneUserData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class HolidayUser extends ZoneUserData{
    public HolidayUser(Player player, ConfigurationSection configurationSection) {
        super(player, configurationSection);
    }
    
    public HolidayUser(String userName, ConfigurationSection configurationSection) {
        super(userName, configurationSection);
    }
    
}
