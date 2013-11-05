package net.arcanerealm.holidays;
/*
* @author Kenny Williams
*/

import net.arcanerealm.holidays.framework.Holiday;
import net.vectorgaming.vcore.framework.VertexAPI;
import net.vectorgaming.vcore.framework.VertexPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import net.arcanerealm.holidays.users.HolidayUserManager;

public class HolidaysAPI extends VertexAPI
{
    private static HashMap<String, Holiday> holidays = new HashMap<>();

    public HolidaysAPI(VertexPlugin plugin)
    {
        super(plugin);
    }
    
    public static HolidayUserManager getUserManager(){
        return ((Holidays)getPlugin()).getUserManager();
    }
    
    public static void registerHoliday(Holiday holiday)
    {
        holidays.put(holiday.getName().toLowerCase(), holiday);
    }

    public static Holiday getHoliday(String holiday)
    {
        return holidays.get(holiday.toLowerCase());
    }

    public static ArrayList<Holiday> getEnabledHolidays()
    {
        ArrayList<Holiday> result = new ArrayList<>();
        for(Holiday h : holidays.values())
        {
            if(h.isEnabled()) result.add(h);
        }
        return result;
    }

    public static Set<String> getEnabledHolidayNames()
    {
        return holidays.keySet();
    }

    public static boolean holidayExists(String holiday)
    {
        return getEnabledHolidayNames().contains(holiday.toLowerCase());
    }
}
