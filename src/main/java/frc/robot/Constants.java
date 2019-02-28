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
    public static final double ikRamp = 0.25;
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
    public static double dkPeak = 0.7;
    public static double dkRamp = 0.4;

    /*
    wrist flat = 4000(lift)
    wrist flat = 1000(wrist)
    lift top = 47000(lift)
    lift cargo intake = 29000(lift)
    wrist other flat = 6000(lift)
    wrist other flat = -1000(wrist)
    carriage top = 22000(lift)
    */
    public static double lkP = 0.25;
    public static double lkI = 0;
    public static double lkD = 20;
    public static double lkF = 1023/5000.0;
    public static double lkPeak = 0.6;
    public static double lkRamp = 0.3;
    public static int lkAllowableClosed = 2;
    public static int lkCruise = 2000;
    public static int lkAccel = (int)(lkCruise/0.75);//encoder counts per 100 ms per second
    public static double lkAntiGrav = 0;
    private static final double lkAntiGravMax = 0.1;
    public static final double lkBottom=0;
    public static final double lkHatch1=4000;
    public static final double lkLowOver=6500;
    public static final double lkHatch2=24500;
    public static final double lkCargoIn=29000;
    public static final double lkHatch3=47700;

    public static double wkP = 0.3;
    public static double wkI = 0;
    public static double wkD = 25;
    public static double wkF = 1023/1300.0;
    public static double wkPeak = 0.9;
    public static double wkRamp = 0.3;
    public static int wkCruise = 800;
    public static int wkAccel = (int)(wkCruise/1);
    public static final double wkAntiGrav = 0.25;//How much PercentOutput is required for the motor to stall while horizontal
    public static final int wkMinB = -95;
    public static final int wkMaxB = -75;
    public static final int wkMinF = 13;
    public static final int wkMaxF = 170;
    
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
        double lift = RobotMap.liftF.getMotorOutputPercent();
        if(lift<-0.05){
            lkAntiGrav=-(lkAntiGravMax*lift);
        }else{
            lkAntiGrav=0;
        }
        /*
        drivePID.update();
        liftPID.update();
        wristPID.update();
        */
    }
}