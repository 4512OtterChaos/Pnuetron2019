package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.*;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;
public class Network{

    private static ArrayList<String> keys = new ArrayList<String>();;
    private static ArrayList<NetworkTableEntry> objects = new ArrayList<NetworkTableEntry>();

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
            SmartDashboard.putBoolean(key, (boolean)value);
        else if(value instanceof Double || value instanceof Integer)
            SmartDashboard.putNumber(key, (double)value);
        else if(value instanceof Double[] || value instanceof Integer[])
            SmartDashboard.putNumberArray(key, (double[])value);
    }
    private static void checkShuffle(String key, Object value, String tab){
        boolean repeat = false;
        int id = 0;
        NetworkTableEntry entry;
        for(String s:keys){
            if(s.equals(key+tab)){
                repeat=true;
                id=keys.indexOf(s);
            }
        }
        if(repeat){
            entry = objects.get(id);
            entry.setValue(value);
            /*
            if(value instanceof String)
                entry.setString((String)value);
            else if(value instanceof Boolean)
                entry.setBoolean((boolean)value);
            else if(value instanceof Double || value instanceof Integer)
                entry.setDouble((double)value);
            else if(value instanceof Double[] || value instanceof Integer[])
                entry.setDoubleArray((double[])value);
            */
        }else{
            entry = Shuffleboard.getTab(tab).add(key, value).getEntry();
            keys.add(key+tab);
            objects.add(entry);
        }
    }
    private static void checkShuffle(String key, Object value, String tab, WidgetType widget){
        boolean repeat = false;
        int id = 0;
        NetworkTableEntry entry;
        for(String s:keys){
            if(s.equals(key+tab)){
                repeat=true;
                id=keys.indexOf(s);
            }
        }
        if(repeat){
            entry = objects.get(id);
            entry.setValue(value);
            /*
            if(value instanceof String)
                entry.setString((String)value);
            else if(value instanceof Boolean)
                entry.setBoolean((boolean)value);
            else if(value instanceof Double || value instanceof Integer)
                entry.setDouble((double)value);
            else if(value instanceof Double[] || value instanceof Integer[])
                entry.setDoubleArray((double[])value);
            */
        }else{
            entry = Shuffleboard.getTab(tab).add(key, value).withWidget(widget).getEntry();
            keys.add(key+tab);
            objects.add(entry);
        }
    }
    
    public static void put(String key, Object value){
        checkSmart(key, value);
    }
    public static void put(String key, Object value, String tab){
        checkSmart(key, value);
        checkShuffle(key, value, tab);
    }
    public static void put(String key, Object value, String[] tabs){
        for(String tab:tabs){
            checkSmart(key, value);
            checkShuffle(key, value, tab);
        }
    }
    public static void put(String key, Object value, String tab, WidgetType widget){
        checkSmart(key, value);
        checkShuffle(key, value, tab, widget);
    }
    public static void put(String key, Object value, String[] tabs, WidgetType widget){
        for(String tab:tabs){
            checkSmart(key, value);
            checkShuffle(key, value, tab, widget);
        }
    }
    public static void putArr(String key, Object value){
        checkSmart(key, value);
    }
    public static void putArr(String key, Object value, String tab){
        checkSmart(key, value);
        checkShuffle(key, value, tab);
    }
    public static void putArr(String key, Object value, String[] tabs){
        for(String tab:tabs){
            checkSmart(key, value);
            checkShuffle(key, value, tab);
        }
    }
}