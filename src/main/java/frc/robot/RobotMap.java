package frc.robot;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class RobotMap{
    /*
	dRightF.
	dRightB.
	dLeftF.
	dLeftB.
	liftF.
	liftB.
	wrist.
	intakeR.
	intakeL.
	*/
    public static TalonSRX dRightF = new TalonSRX(1);
	public static TalonSRX dRightB = new TalonSRX(2);//pigeon
	public static TalonSRX dLeftF = new TalonSRX(3);
	public static TalonSRX dLeftB = new TalonSRX(4);
	public static TalonSRX liftF = new TalonSRX(5);
	public static TalonSRX liftB = new TalonSRX(6);
	public static TalonSRX wrist = new TalonSRX(7);
	public static VictorSPX intakeR = new VictorSPX(8);
    public static VictorSPX intakeL = new VictorSPX(9);

    public static TalonSRX[] driveMotors = {dRightF, dRightB, dLeftF, dLeftB};
    public static TalonSRX[] liftMotors = {liftF, liftB};
    public static TalonSRX[] wristMotors = {wrist};
    public static BaseMotorController[] intakeMotors = {intakeR, intakeL};
	public static BaseMotorController[] allMotors = {dRightF, dRightB, dLeftF, dLeftB, liftF, liftB, wrist, intakeR, intakeL};
	
	public static Compressor compressor = new Compressor(0);
	public static DoubleSolenoid crabber = new DoubleSolenoid(4,5);
	public static DoubleSolenoid crabPop = new DoubleSolenoid(6,7);
	public static DoubleSolenoid intakeFlip = new DoubleSolenoid(0,1);
	public static DoubleSolenoid liftStop = new DoubleSolenoid(2,3);

	public static DigitalInput stage1Bot = new DigitalInput(2);
	public static DigitalInput stage1Top = new DigitalInput(0);
	public static DigitalInput carriageTop = new DigitalInput(1);
	public static DigitalInput carriageBot = new DigitalInput(3);

    
    /**
     * Configure the behavior of the electrical components(motor controllers, pnuematics, etc.)
     */
    public static void config(){
		configFactory(allMotors);
		//idle
		configNeutral(NeutralMode.Brake, allMotors);
		configNeutral(NeutralMode.Coast, liftMotors);
		//limits
		configPeak(Constants.kPeakReverse, Constants.kPeakForward, allMotors);
		configNominal(Constants.kNominalReverse, Constants.kNominalReverse, allMotors);
		//define sensor
		dRightF.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kIdx, Constants.kTimeout);
		dRightF.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, Constants.kTimeout);

		dLeftF.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kIdx, Constants.kTimeout);
		dLeftF.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, Constants.kTimeout);

		liftF.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, Constants.kIdx, Constants.kTimeout);
		liftF.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, Constants.kTimeout);

		wrist.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, Constants.kIdx, Constants.kTimeout);
		wrist.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, Constants.kTimeout);
        //behavior
        dRightB.follow(dRightF);
		dLeftB.follow(dLeftF);
        liftB.follow(liftF);

		dRightF.setInverted(true);
		dRightB.setInverted(InvertType.FollowMaster);
		dRightF.setSensorPhase(true);

		dLeftF.setInverted(false);
		dLeftB.setInverted(InvertType.FollowMaster);
		dLeftF.setSensorPhase(true);

		liftF.setInverted(true);
		liftB.setInverted(InvertType.FollowMaster);
        liftF.setSensorPhase(true);

		wrist.setInverted(false);
        wrist.setSensorPhase(true);

        intakeR.configOpenloopRamp(Constants.ikRamp);
        intakeR.setInverted(false);
        intakeL.configOpenloopRamp(Constants.ikRamp);
		intakeL.setInverted(true);
		//pid
		zeroSensor(allMotors);
		configClosed(driveMotors, Constants.dkP, Constants.dkI, Constants.dkD, Constants.dkF, Constants.dkPeak, Constants.dkRamp);
		configClosed(liftMotors, Constants.lkP, Constants.lkI, Constants.lkD, Constants.lkF, Constants.lkPeak, Constants.lkRamp);
	}
	
	public static void zeroSensor(BaseMotorController motor){
		motor.setSelectedSensorPosition(0, Constants.kIdx, Constants.kTimeout);
	}
	public static void zeroSensor(BaseMotorController[] motors){
		for(BaseMotorController motor:motors){
			motor.setSelectedSensorPosition(0, Constants.kIdx, Constants.kTimeout);
		}
	}
    
    public static void configPeak(double negative, double positive, BaseMotorController[] motors){
        for(BaseMotorController motor:motors){
            motor.configPeakOutputForward(positive, Constants.kTimeout);
            motor.configPeakOutputReverse(negative, Constants.kTimeout);
        }
	}
	public static void configNominal(double negative, double positive, BaseMotorController[] motors){
		for(BaseMotorController motor:motors){
			motor.configNominalOutputForward(positive, Constants.kTimeout);
			motor.configNominalOutputReverse(negative, Constants.kTimeout);
		}
	}
    public static void configNeutral(NeutralMode neutral, BaseMotorController[] motors){
        for(BaseMotorController motor:motors){
            motor.setNeutralMode(neutral);
        }
    }
    public static void configFactory(BaseMotorController[] motors){
        for(BaseMotorController motor:motors){
            motor.configFactoryDefault();
        }
	}
	public static void configPID(TalonSRX motor, double p, double i, double d, double f){
		motor.config_kP(Constants.kIdx, p, Constants.kTimeout);
		motor.config_kI(Constants.kIdx, i, Constants.kTimeout);
		motor.config_kD(Constants.kIdx, d, Constants.kTimeout);
		motor.config_kF(Constants.kIdx, f, Constants.kTimeout);
    }
	public static void configClosed(TalonSRX[] motors, double p, double i, double d, double f, double peak, double ramp){
		for(TalonSRX motor:motors){
			motor.configClosedloopRamp(ramp);
			motor.configClosedLoopPeakOutput(Constants.kIdx, peak);
			motor.configAllowableClosedloopError(Constants.kIdx, Constants.kAllowableClosed, Constants.kTimeout);
			configPID(motor, p, i, d, f);
		}
	}

    public static double toNative(double rpm){//convert rpm to native talon units
		return rpm*4096.0/600.0f;
	}
	public static double toRPM(double nativeU){//convert native talon units to rpm
		return nativeU/4096.0*600f;
	}

	public static double getRPM(BaseMotorController motor){
		return toRPM(motor.getSelectedSensorVelocity());
	}
	public static double getNative(BaseMotorController motor){
		return motor.getSelectedSensorVelocity();
	}
	public static double getPos(BaseMotorController motor){
		return motor.getSelectedSensorPosition();
	}

	public static double getArmDegrees(TalonSRX arm){//straight up is 0 degrees, negative forward
		double percent = getPos(arm)/Constants.wkRotationCounts;
		return percent*360;
	}
	public static double calculateArmFF(TalonSRX arm){
		double math = (1-Math.cos(2*Input.toRadians(getArmDegrees(arm))))/2.0;
		return math;
	}

	public static boolean getSwitch(DigitalInput dio){
		return !dio.get();
	}

    public static void displayStats(){
		//pid/electrical systems
		String[] tabs = {"PID","Electrical"};
		Network.put("Right Drive Counts", getPos(dRightF), tabs);
		Network.put("Right Drive RPM", getRPM(dRightF), tabs);
		Network.put("Right Drive NativeV", getNative(dRightF), tabs);
		Network.put("Left Drive Counts", getPos(dLeftF), tabs);
		Network.put("Left Drive RPM", getRPM(dLeftF), tabs);
		Network.put("Left Drive NativeV", getNative(dLeftF), tabs);
		Network.put("Lift Counts", getPos(liftF), tabs);
		Network.put("Lift RPM", getRPM(liftF), tabs);
		Network.put("Lift NativeV", getNative(liftF), tabs);
		Network.put("Wrist Counts", getPos(wrist), tabs);
		Network.put("Wrist RPM", getRPM(wrist), tabs);
		Network.put("Wrist NativeV", getNative(wrist), tabs);
		//pid
		double[] dPIDMap = {toRPM(Teleop.rightTarget), getRPM(dRightF), toRPM(Teleop.leftTarget), getRPM(dLeftF)};
		double[] lPIDMap = {toRPM(Teleop.liftTarget), getRPM(liftF)};
		double[] wPIDMap = {toRPM(Teleop.wristTarget), getRPM(wrist)};
		SmartDashboard.putNumberArray("Drive PID Map", dPIDMap);
		SmartDashboard.putNumberArray("Lift PID Map", lPIDMap);
		SmartDashboard.putNumberArray("Wrist PID Map", wPIDMap);
		//Network.putArr("Drive PID Map", dPIDMap, "PID");
		//Network.putArr("Lift PID Map", lPIDMap, "PID");
		//Network.putArr("Wrist PID Map", wPIDMap, "PID");
		//electrical
		Network.put("Compressor Current", compressor.getCompressorCurrent(), "Electrical");
		Network.put("Compressor Switch", compressor.getPressureSwitchValue(), "Electrical");

		Network.put("Stage 1 Bot", getSwitch(stage1Bot), "Electrical");
		Network.put("Stage 1 Top", getSwitch(stage1Top), "Electrical");
		Network.put("Carriage Top", getSwitch(carriageTop), "Electrical");
		Network.put("Carriage Bot", getSwitch(carriageBot), "Electrical");
	}
}