package frc.robot;

public class Autonomous{
    public static String command;
    
    public static void init(String _command){
        command = _command;
    }

    public static void periodic(){
        switch(command){
            case "Test":
                break;
            default:
                break;
        }
    }
}