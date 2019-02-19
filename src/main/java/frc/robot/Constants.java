package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.can.*;

public class Constants{
    public static final double kPeakForward = 1;
    public static final double kPeakReverse = -1;
    public static final double kNominalForward = 0.05;
    public static final double kNominalReverse = -0.05;
    public static final int kTimeout = 30;
    public static final double ikRamp = 0.3;
    public static final double dkMaxRPM = 1000;
    public static final double lkMaxRPM = 1000;
    public static final int kAllowableClosed = 0;
    public static final int kIdx = 0;
    //*PID

    /* 
    PIDConfig pid = new PIDConfig(motors[], subsystem, 
    p, i, d, f, 
    peak, ramp);
    */
    public static PIDConfig drivePID = new PIDConfig(RobotMap.driveMotors, "Drive", 
    0.5, 0, 40, 1023/6800, 
    0.7, 0.55);

    public static PIDConfig liftPID = new PIDConfig(RobotMap.liftMotors, "Lift", 
    0, 0, 0, 0, 
    0.7, 0.55);

    public static PIDConfig wristPID = new PIDConfig(RobotMap.wristMotors, "Wrist", 
    0, 0, 0, 0, 
    0.7, 0.55);

    public static void init(){
        drivePID.init();
        liftPID.init();
        wristPID.init();
    }
    public static void update(){
        drivePID.update();
        liftPID.update();
        wristPID.update();
    }
}