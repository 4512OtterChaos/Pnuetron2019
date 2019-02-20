package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.shuffleboard.*;
public class Network{

    /**
     * Given a title and object, automatically determine
     * SmartDashboard method for outputting.
     * @param key String title
     * @param value Java Object
     */
    private static void checkSmart(String key, Object value){
        if(value instanceof String)
            SmartDashboard.putString(key, (String)value);
        else if(value instanceof Boolean)
            SmartDashboard.putBoolean(key, (Boolean)value);
        else if(value instanceof Double || value instanceof Integer)
            SmartDashboard.putNumber(key, (double)value);
        else if(value instanceof Double[] || value instanceof Integer[])
            SmartDashboard.putNumberArray(key, (double[])value);
    }
    private static void checkShuffle(String key, Object value, String tab){
        Shuffleboard.getTab(tab).add(key, value);
    }
    private static void checkShuffle(String key, Object value, String tab, WidgetType widget){
        Shuffleboard.getTab(tab).add(key, value).withWidget(widget);
    }
    
    public static void put(String key, Object value){
        checkSmart(key, value);
    }
    public static void put(String key, Object value, String tab){
        checkSmart(key, value);
        //checkShuffle(key, value, tab);
    }
    public static void put(String key, Object value, String[] tabs){
        for(String tab:tabs){
            checkSmart(key, value);
            //checkShuffle(key, value, tab);
        }
    }
    public static void put(String key, Object value, String tab, WidgetType widget){
        checkSmart(key, value);
        //checkShuffle(key, value, tab, widget);
    }
    public static void put(String key, Object value, String[] tabs, WidgetType widget){
        for(String tab:tabs){
            checkSmart(key, value);
            //checkShuffle(key, value, tab, widget);
        }
    }
    public static void putArr(String key, Object value){
        checkSmart(key, value);
    }
    public static void putArr(String key, Object value, String tab){
        checkSmart(key, value);
        //checkShuffle(key, value, tab);
    }
    public static void putArr(String key, Object value, String[] tabs){
        for(String tab:tabs){
            checkSmart(key, value);
            //checkShuffle(key, value, tab);
        }
    }
}