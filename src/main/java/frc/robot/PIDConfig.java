package frc.robot;

import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class PIDConfig{
    public TalonSRX[] masterMotors;
    public String subsystemName;
    public double kP,kI,kD,kF;
    public int kTimeout;
    public double kPeakClosed;
    public double kRampClosed;
    private ShuffleboardTab tab = Shuffleboard.getTab("PID");
    private NetworkTableEntry pGain;
    private NetworkTableEntry iGain;
    private NetworkTableEntry dGain;

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
        this.pGain = tab.add(subsystemName+" P Gain", kP).withWidget(BuiltInWidgets.kTextView).getEntry();
        this.iGain = tab.add(subsystemName+" I Gain", kI).withWidget(BuiltInWidgets.kTextView).getEntry();
        this.dGain = tab.add(subsystemName+" D Gain", kD).withWidget(BuiltInWidgets.kTextView).getEntry();
    }

    public void init(){
        tab.add(subsystemName+" P Gain", kP).withWidget(BuiltInWidgets.kTextView);
        tab.add(subsystemName+" I Gain", kI).withWidget(BuiltInWidgets.kTextView);
        tab.add(subsystemName+" D Gain", kD).withWidget(BuiltInWidgets.kTextView);
    }
    public void update(){
        double p = pGain.getDouble(0);
        double i = iGain.getDouble(0);
        double d = dGain.getDouble(0);
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