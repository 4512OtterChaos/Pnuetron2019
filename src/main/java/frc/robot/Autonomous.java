package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Timer;

public class Autonomous {
    public static String command;
    private static ArrayList<String> keys = new ArrayList<String>();
    private static ArrayList<Double[]> times = new ArrayList<Double[]>();
    private static int scheduleId = 0;
    
    public static void init(String command){
        Autonomous.command = command;
        switch(command){
            case "nothing":
                //haha nothing
                break;
            default://cross line
                schedule("line", 4);
                break;
        }
    }

    public static void periodic(){
        switch(command){
            case "nothing":
                //haha nothing
                break;
            default://cross line
                if(schedule("line")){
                    Teleop.dVelPID(1, 0);
                }else{
                    Teleop.dVelPID(1, 0);
                }
                break;
        }
    }

    private static boolean schedule(String key){
        int id = -1;
        for(String k:keys){
            if(k.equals(key)){
                id = keys.indexOf(k);
            }
        }
        if(id==-1) return false;
        double current = Timer.getFPGATimestamp() - times.get(id)[0];
        boolean expired = current>times.get(id)[1];
        if(!expired){
            return true;
        }else{
            keys.remove(id);
            times.remove(id);
            scheduleId--;
            return false;
        }
    }
    private static boolean schedule(String key, double duration){
        boolean repeat = false;
        int id = 0;
        for(String k:keys){
            if(k.equals(key)){
                repeat = true;
            }
        }
        if(!repeat){
            keys.add(scheduleId, key);
            Double[] schedule = {(Double)Timer.getFPGATimestamp(), (Double)duration};
            times.add(scheduleId, schedule);
            scheduleId++;
        }
        for(String k:keys){
            if(k.equals(key)){
                id = keys.indexOf(k);
            }
        }
        double current = Timer.getFPGATimestamp() - times.get(id)[0];
        boolean expired = current>times.get(id)[1];
        if(!expired){
            return true;
        }else{
            keys.remove(id);
            times.remove(id);
            scheduleId--;
            return false;
        }
    }
}