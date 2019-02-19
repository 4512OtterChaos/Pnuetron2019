package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.shuffleboard.*;
public class Network{
    public static void put(String key, double num){
        SmartDashboard.putNumber(key, num);
    }
    public static void put(String key, double num, String tab){
        SmartDashboard.putNumber(key, num);
        Shuffleboard.getTab(tab).add(key, num);
    }
    public static void put(String key, double num, String[] tabs){
        for(String tab:tabs){
            SmartDashboard.putNumber(key, num);
            Shuffleboard.getTab(tab).add(key, num);
        }
    }
    public static void put(String key, double num, String tab, WidgetType widget){
        SmartDashboard.putNumber(key, num);
        Shuffleboard.getTab(tab).add(key, num).withWidget(widget);
    }
    public static void put(String key, double num, String[] tabs, WidgetType widget){
        for(String tab:tabs){
            SmartDashboard.putNumber(key, num);
            Shuffleboard.getTab(tab).add(key, num).withWidget(widget);
        }
    }
    public static void putArr(String key, double[] num){
        SmartDashboard.putNumberArray(key, num);
    }
    public static void putArr(String key, double[] num, String tab){
        SmartDashboard.putNumberArray(key, num);
        Shuffleboard.getTab(tab).add(key, num);
    }
    public static void putArr(String key, double[] num, String[] tabs){
        for(String tab:tabs){
            SmartDashboard.putNumberArray(key, num);
            Shuffleboard.getTab(tab).add(key, num);
        }
    }
}