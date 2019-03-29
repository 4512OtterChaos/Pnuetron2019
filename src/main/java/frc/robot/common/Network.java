package frc.robot.common;

import edu.wpi.first.wpilibj.smartdashboard.*;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.shuffleboard.*;
public class Network{
    private static NetworkTable table;

    public static void put(String key, Object value){
        table = NetworkTableInstance.getDefault().getTable("LiveWindow");
        NetworkTableEntry entry = table.getEntry(key);
        /*
        if(value instanceof String)
            if(!entry.getString("").equals((String)value))
                entry.setString((String)value);
        else if(value instanceof Boolean)
            if(entry.getBoolean(false)!=(boolean)value)
                entry.setBoolean((boolean)value);
        else if(value instanceof Double || value instanceof Integer)
            if(entry.getNumber(0)!=(Number)value)
                entry.setNumber((Number)value);
        else if(value instanceof Double[] || value instanceof Integer[])
            if(entry.getNumberArray(new Number[0])!=(Number[])value)
                entry.setNumberArray((Number[])value);
        else{
            entry.setValue(value);
        }*/
        entry.setValue(value);
    }
}