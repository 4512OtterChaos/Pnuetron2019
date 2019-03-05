package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.can.*;

public class Constants{
    //*motor behavior
    public static final double kPeakForward = 1;
    public static final double kPeakReverse = -1;
    public static final double kNominalForward = 0.05;
    public static final double kNominalReverse = -0.05;
    public static final int kTimeout = 30;
    public static final double ikRamp = 0.01;
    public static final double dkMaxRPM = 300;
    public static final int kAllowableClosed = 0;
    public static final int kAllowableBehavior = 100;
    public static final int kIdx = 0;
    public static final double kRotCounts = 4096;//1 rotation is 4096 counts(4x encoder)
    //*PID
    public static double dkP = 0.5;
    public static double dkI = 0;
    public static double dkD = 40;
    public static double dkF = 1023/2600.0;
    public static double dkPeak = 0.9;
    public static double dkRamp = 0.35;

    /*
    wrist flat = 4000(lift)
    wrist flat = 1000(wrist)
    lift top = 47000(lift)
    lift cargo intake = 29000(lift)
    wrist other flat = 6000(lift)
    wrist other flat = -1000(wrist)
    carriage top = 22000(lift)
    */
    public static double lkP = 0.4;
    public static double lkI = 0;
    public static double lkD = 25;
    public static double lkF = 1023.0/5000.0;
    public static double lkPeak = 0.75;
    public static double lkRamp = 0.2;
    public static int lkAllowableClosed = 2;
    public static int lkCruise = 3200;
    public static double lkAccelTime = 0.75;//seconds
    public static int lkAccel = (int)(lkCruise/lkAccelTime);//encoder counts per 100 ms per second
    public static final double lkAntiGrav = 0.15;
    public static final double lkBottom=0;
    public static final double lkHatch1=4750;
    public static final double lkLowOver=6000;
    public static final double lkHatch2=24500;
    public static final double lkCargoIn=28200;
    public static final double lkCargoOut=40000;
    public static final double lkHatch3=47700;

    //no game piece pid
    public static double wkP = 1.4;
    public static double wkI = 0;
    public static double wkD = 48;
    public static double wkF = 1023.0/1100.0;
    public static double wkPeak = 1;
    public static double wkRamp = 0.1;
    public static int wkCruise = 100;//250
    public static double wkAccelTime = 0.8;//seconds (1.1)
    public static int wkAccel = (int)(wkCruise/wkAccelTime);
    public static final double wkHatchOutF = 80;
    public static final double wkHatchOutB = -85;
    //behavior
    public static double wkAntiGrav = 0.25;//How much PercentOutput is required for the motor to stall while horizontal
    public static double wkAntiHatch = 0.00;
    public static int wkMinB = -85;
    public static int wkMaxB = -75;
    public static int wkMinF = 13;
    public static int wkMaxF = 170;

    //testing pid
    public static double tkP = 2;
    public static double tkI = 0.0;
    public static double tkD = 50;
    public static double tkF = 1023.0/500.0;
    public static double tkPeak = 1;
    public static double tkRamp = 0.1;
    public static int tkCruise = 90;//250
    public static double tkAccelTime = 1.2;//seconds (1.1)
    public static int tkAccel = (int)(wkCruise/wkAccelTime);
    
    /* 
    PIDConfig pid = new PIDConfig(motors[], subsystem, 
    p, i, d, f, 
    peak, ramp);
    */
    /*
    public static PIDConfig drivePID = new PIDConfig(RobotMap.driveMotors, "Drive", 
    0.5, 0, 40, 1023/2600.0, 
    0.7, 0.55);

    public static PIDConfig liftPID = new PIDConfig(RobotMap.liftMotors, "Lift", 
    0, 0, 0, 0, 
    0.7, 0.55);

    public static PIDConfig wristPID = new PIDConfig(RobotMap.wristMotors, "Wrist", 
    0, 0, 0, 0, 
    0.7, 0.55);
    */

    public static void init(){
        /*
        drivePID.init();
        liftPID.init();
        wristPID.init();
        */
    }
    public static void update(){
        /*
        drivePID.update();
        liftPID.update();
        wristPID.update();
        */
    }
}