package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.can.*;

public class Constants{
    //*motor behavior
    public static final double kPeakForward = 1;
    public static final double kPeakReverse = -1;
    public static final double kNominalForward = 0;
    public static final double kNominalReverse = 0;
    public static final int kTimeout = 30;
    public static final double ikRamp = 0;
    public static final double dkMaxRPM = 400;
    public static final int kAllowableClosed = 0;
    public static final int kAllowableLift = 250;
    public static final int kAllowableWrist = 50;
    public static final int kIdx = 0;
    public static final double kRotCounts = 4096;//1 rotation is 4096 counts(4x encoder)
    //*PID
    public static double dkP = 0.5;
    public static double dkI = 0;
    public static double dkD = 40;
    public static double dkF = 1023/2600.0;
    public static double dkPeak = 1;
    public static double dkRamp = 0.175;

    /*
    wrist flat = 4000(lift)
    wrist flat = 1000(wrist)
    lift top = 47000(lift)
    lift cargo intake = 29000(lift)
    wrist other flat = 6000(lift)
    wrist other flat = -1000(wrist)
    carriage top = 22000(lift)
    */
    public static double lkP = 0.7;
    public static double lkI = 0;
    public static double lkD = 30;
    public static double lkF = 1023.0/5000.0;
    public static double lkPeak = 0.75;
    public static double lkRamp = 0.15;
    public static int lkAllowableClosed = 2;
    public static int lkCruise = 3200;
    public static double lkAccelTime = 0.75;//seconds
    public static int lkAccel = (int)(lkCruise/lkAccelTime);//encoder counts per 100 ms per second
    public static final double lkAntiGrav = 0.15;
    public static final double lkBottom=0;
    public static final double lkHatch1=4750;
    public static final double lkLowOver=6000;
    public static final double lkHatch2=24500;
    public static final double lkHatch3=47700;

    //no game piece pid
    public static double wkP = 3;
    public static double wkI = 0;
    public static double wkD = 60;
    public static double wkF = 1023.0/225.0;
    public static double wkPeak = 1;
    public static double wkRamp = 0.075;
    public static int wkCruise = 110;//250
    public static double wkAccelTime = 0.9;//seconds (1.1)
    public static int wkAccel = (int)(wkCruise/wkAccelTime);
    public static final double wkHatchOutF = 85;
    public static final double wkHatchOutB = -85;
    //behavior
    public static double wkAntiGrav = 0.08;//How much PercentOutput is required for the motor to stall while horizontal
    public static double wkAntiHatch = 0.11;
    public static int wkMinB = -85;
    public static int wkMaxB = -75;
    public static int wkMinF = 18;
    public static int wkMaxF = 130;

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