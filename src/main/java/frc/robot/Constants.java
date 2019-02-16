package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Constants{
    //*PID
    public static double dkP = 0.5;
    public static double dkI = 0.0;
    public static double dkD = 40.0;
    public static final double dkF = 1023.0/6800.0;
    public static double lkP = 0.0;
    public static double lkI = 0.0;
    public static double lkD = 0.0;
    public static final double lkF = 1023.0/6800.0;
    public static final int kTimeout = 30;
    public static final double dkPeakClosed = 0.7;
    public static final double lkPeakClosed = 0.7;
    public static final double dkRamp = 0.55;
    public static final double lkRamp = 0.55;
    public static final double dkMaxRPM = 1000;
    public static final double lkMaxRPM = 1000;
    public static final int kAllowableClosed = 0;
    public static final int kIdx = 0;

    public static void init(){
        SmartDashboard.putNumber("Drive P Gain", dkP);
        SmartDashboard.putNumber("Drive I Gain", dkI);
        SmartDashboard.putNumber("Drive D Gain", dkD);
        SmartDashboard.putNumber("Lift P Gain", lkP);
        SmartDashboard.putNumber("Lift I Gain", lkI);
        SmartDashboard.putNumber("Lift D Gain", lkD);
    }

    public static void update(){
        double dp = SmartDashboard.getNumber("Drive P Gain", 0);
        double di = SmartDashboard.getNumber("Drive I Gain", 0);
        double dd = SmartDashboard.getNumber("Drive D Gain", 0);
        if(dp!=dkP || di!=dkI || dd!=dkD){
            dkP=dp;
            dkI=di;
            dkD=dd;
            Motorbase.configPID(dkP, dkI, dkD, dkF);
        }
        double lp = SmartDashboard.getNumber("Lift P Gain", 0);
        double li = SmartDashboard.getNumber("Lift I Gain", 0);
        double ld = SmartDashboard.getNumber("Lift D Gain", 0);
        if(lp!=lkP || li!=lkI || ld!=lkD){
            lkP=lp;
            lkI=li;
            lkD=ld;
            Motorbase.configPID(Motorbase.liftF, lkP, lkI, lkD, lkF);
            Motorbase.configPID(Motorbase.liftB, lkP, lkI, lkD, lkF);
        }
    }
}