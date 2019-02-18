package frc.robot;

import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class PIDConfig{
    public TalonSRX[] masterMotors;
    public String subsystemName;
    public double kP,kI,kD,kF;
    public int kTimeout;
    public double kPeakClosed;
    public double kRampClosed;

    /**
     * Creates a new PID configuration for a set of motors.
     * This automatically configures the motors and updates smartdashboard with values.
     * @param motors Array of TalonSRX motors(to be configured).
     * @param subsystem Name of the subsystem to be displayed on smartdashboard.
     * @param p Proportional constant.
     * @param i Integral constant.
     * @param d Derivative constant.
     * @param f Feed-forward constant.
     * @param peak Closed loop peak output(0 to 1).
     * @param ramp Seconds from neutral to full throttle(0 to 10 seconds).
     */
    public PIDConfig(TalonSRX[] motors, String subsystem, double p, double i, double d, double f, double peak, double ramp){
        this.masterMotors = motors;
        this.subsystemName = subsystem;
        this.kP = p;
        this.kI = i;
        this.kD = d;
        this.kF = f;
        this.kPeakClosed = peak;
        this.kRampClosed = ramp;
        for(TalonSRX motor:masterMotors){
            motor.configClosedloopRamp(ramp);
            motor.configClosedLoopPeakOutput(Constants.kIdx, peak);
            motor.configAllowableClosedloopError(Constants.kIdx, Constants.kAllowableClosed, Constants.kTimeout);
            configPID(motor, kP, kI, kD, kF);
        }
    }

    public void init(){
        SmartDashboard.putNumber(subsystemName+" P Gain", kP);
        SmartDashboard.putNumber(subsystemName+" I Gain", kI);
        SmartDashboard.putNumber(subsystemName+" D Gain", kD);
    }
    public void update(){
        double p = SmartDashboard.getNumber(subsystemName+" P Gain", 0);
        double i = SmartDashboard.getNumber(subsystemName+" I Gain", 0);
        double d = SmartDashboard.getNumber(subsystemName+" D Gain", 0);
        if(p!=kP || i!=kI || d!=kD){
            kP=p;
            kI=i;
            kD=d;
            for(TalonSRX motor:masterMotors){
                configPID(motor, kP, kI, kD, kF);
            }
        }
    }

    public static void configPID(TalonSRX motor, double p, double i, double d, double f){
		motor.config_kP(Constants.kIdx, p, Constants.kTimeout);
		motor.config_kI(Constants.kIdx, i, Constants.kTimeout);
		motor.config_kD(Constants.kIdx, d, Constants.kTimeout);
		motor.config_kF(Constants.kIdx, f, Constants.kTimeout);
    }
}