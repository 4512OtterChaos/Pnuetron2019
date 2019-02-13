package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Constants{
    //PID
    public static double kP = 0.5;
    public static double kI = 0.0;
    public static double kD = 40.0;
    public static final double kF = 1023.0/6800.0;
    public static final int kTimeout = 30;
    public static final double kPeakClosed = 0.7;
    public static final double kRamp = 0.55;
    public static final double kMaxRPM = 1000;
    public static final int kAllowableClosed = 0;
    public static final int kIdx = 0;

    public static void init(){
        SmartDashboard.putNumber("P Gain", kP);
        SmartDashboard.putNumber("I Gain", kI);
        SmartDashboard.putNumber("D Gain", kD);
    }

    public static void update(){
        double p = SmartDashboard.getNumber("P Gain", 0);
        double i = SmartDashboard.getNumber("I Gain", 0);
        double d = SmartDashboard.getNumber("D Gain", 0);
        if(p!=kP || i!=kI || d!=kD){
            kP=p;
            kI=i;
            kD=d;
            MotorBase.configPID(kP, kI, kD, kF);
        }
    }
}