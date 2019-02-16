package frc.robot;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.*;
public class Motorbase{
	/** Hardware */
	public static TalonSRX dRightF = new TalonSRX(1);
	public static TalonSRX dRightB = new TalonSRX(2);
	public static TalonSRX dLeftF = new TalonSRX(3);
	public static TalonSRX dLeftB = new TalonSRX(4);
	public static TalonSRX liftF = new TalonSRX(5);
	public static TalonSRX liftB = new TalonSRX(6);
	public static TalonSRX wrist = new TalonSRX(7);
	public static TalonSRX intakeL = new TalonSRX(8);
	public static TalonSRX intakeR = new TalonSRX(9);

	/*
	dRightF.
	dRightB.
	dLeftF.
	dLeftB.
	liftF.
	liftB.
	wrist.
	intakeL.
	intakeR.
	*/

    /* Targets */
	private static float dSpeed = 0.3f;//overall speed affecting robots actions
	private static double rightTarget;
	private static double leftTarget;
	private static double liftTarget;
	private static double wristTarget;

    public static void init(){
		//*Constants
		//*Config
		dRightF.configFactoryDefault();
		dRightB.configFactoryDefault();
		dLeftF.configFactoryDefault();
		dLeftB.configFactoryDefault();
		liftF.configFactoryDefault();
		liftB.configFactoryDefault();
		//idle
		dRightF.setNeutralMode(NeutralMode.Brake);
		dRightB.setNeutralMode(NeutralMode.Brake);
		dLeftF.setNeutralMode(NeutralMode.Brake);
		dLeftB.setNeutralMode(NeutralMode.Brake);
		liftF.setNeutralMode(NeutralMode.Brake);
		liftB.setNeutralMode(NeutralMode.Brake);
		//limits
		dRightF.configPeakOutputForward(1);
		dRightB.configPeakOutputForward(1);
		dLeftF.configPeakOutputForward(1);
		dLeftB.configPeakOutputForward(1);
		liftF.configPeakOutputForward(1);
		liftB.configPeakOutputForward(1);
		dRightF.configPeakOutputReverse(-1);
		dRightB.configPeakOutputReverse(-1);
		dLeftF.configPeakOutputReverse(-1);
		dLeftB.configPeakOutputReverse(-1);
		liftF.configPeakOutputReverse(-1);
		liftB.configPeakOutputReverse(-1);
		//define sensor
		dRightF.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kIdx, Constants.kTimeout);
		dRightF.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, Constants.kTimeout);

		dLeftF.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kIdx, Constants.kTimeout);
		dLeftF.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, Constants.kTimeout);

		liftF.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kIdx, Constants.kTimeout);
		liftF.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, Constants.kTimeout);
		//pid
		configPID(Constants.dkP, Constants.dkI, Constants.dkD, Constants.dkF);
		configPID(liftF, Constants.lkP, Constants.lkI, Constants.lkD, Constants.lkF);
		//behavior
		dRightF.configClosedLoopPeakOutput(Constants.kIdx, Constants.dkPeakClosed, Constants.kTimeout);
		dRightF.configAllowableClosedloopError(Constants.kIdx, Constants.kAllowableClosed, Constants.kTimeout);
		dRightF.configClosedloopRamp(Constants.dkRamp);
		dRightF.setInverted(true);
		dRightB.setInverted(true);
		dRightF.setSensorPhase(true);

		dLeftF.configClosedLoopPeakOutput(Constants.kIdx, Constants.dkPeakClosed, Constants.kTimeout);
		dLeftF.configAllowableClosedloopError(Constants.kIdx, Constants.kAllowableClosed, Constants.kTimeout);
		dLeftF.configClosedloopRamp(Constants.dkRamp);
		dLeftF.setInverted(false);
		dLeftB.setInverted(false);
		dLeftF.setSensorPhase(true);

		liftF.configClosedLoopPeakOutput(Constants.kIdx, Constants.lkPeakClosed, Constants.kTimeout);
		liftF.configAllowableClosedloopError(Constants.kIdx, Constants.kAllowableClosed, Constants.kTimeout);
		liftF.configClosedloopRamp(Constants.lkRamp);
		liftF.setInverted(true);
		liftB.setInverted(true);
		liftF.setSensorPhase(true);

		dRightB.follow(dRightF);
		dLeftB.follow(dLeftF);
		liftB.follow(liftF);
		//*Routine
		tankDrive(0,0);
		System.out.println("--Feed Forward Teleop--");
    }

    public static void periodic(){
		//*pid tuning values
		//*arcade drive
		double forward = Input.getLeftY(Input.xbox);
		double turn = Input.getRightX(Input.xbox);
		velPID(forward, turn);
		//arcadeDrive(forward, turn);
	}

	public static void disable(){
		tankDrive(0, 0);
		dRightF.setNeutralMode(NeutralMode.Coast);
		dRightB.setNeutralMode(NeutralMode.Coast);
		dLeftF.setNeutralMode(NeutralMode.Coast);
		dLeftB.setNeutralMode(NeutralMode.Coast);
		liftF.setNeutralMode(NeutralMode.Brake);
		liftB.setNeutralMode(NeutralMode.Brake);
	}

	public static void velPID(double forward, double turn){
		double right = arcadeMath(forward, turn, true);
		double left = arcadeMath(forward, turn, false);
		rightTarget = calc100ms(right, Constants.dkMaxRPM);
		leftTarget = calc100ms(left, Constants.dkMaxRPM);
		dRightF.set(ControlMode.Velocity, rightTarget);
		dLeftF.set(ControlMode.Velocity, leftTarget);
	}
	public static void arcadeDrive(double forward, double turn){
		double right = arcadeMath(forward, turn, true);
		double left = arcadeMath(forward, turn, false);
		dRightF.set(ControlMode.PercentOutput, right);
		dLeftF.set(ControlMode.PercentOutput, left);
		//dRightF.set(ControlMode.PercentOutput, forward, DemandType.ArbitraryFeedForward, -turn);
		//dLeftF.set(ControlMode.PercentOutput, forward, DemandType.ArbitraryFeedForward, +turn);
	}

    public static void tankDrive(double left, double right){
		left*=dSpeed;
		right*=dSpeed;
		dRightF.set(ControlMode.PercentOutput, right);
		dLeftF.set(ControlMode.PercentOutput, left);
	}

	private static double arcadeMath(double forward, double turn, boolean right){
		forward*=dSpeed;
		turn*=dSpeed*0.75;
		if(right) return Input.limit(forward-turn);
		else return Input.limit(forward+turn);
	}

	public static void setLift(double x){
		liftF.set(ControlMode.PercentOutput, x);
	}

	private static double calc100ms(double percentOutput, double range){//percentage rpm as native units
		double tRPM = percentOutput*range;
		return toNative(tRPM);//talons use sensor units per 100ms(native units)
	}
	private static double toNative(double rpm){//convert rpm to native talon units
		return rpm*4096.0/600.0f;
	}
	private static double toRPM(double nativeU){//convert native talon units to rpm
		return nativeU/4096.0*600f;
	}

	public static void configPID(double p, double i, double d, double f){
		dRightF.config_kP(Constants.kIdx, p, Constants.kTimeout);
		dRightF.config_kI(Constants.kIdx, i, Constants.kTimeout);
		dRightF.config_kD(Constants.kIdx, d, Constants.kTimeout);
		dRightF.config_kF(Constants.kIdx, f, Constants.kTimeout);
		dLeftF.config_kP(Constants.kIdx, p, Constants.kTimeout);
		dLeftF.config_kI(Constants.kIdx, i, Constants.kTimeout);
		dLeftF.config_kD(Constants.kIdx, d, Constants.kTimeout);
		dLeftF.config_kF(Constants.kIdx, f, Constants.kTimeout);
	}

	public static void configPID(TalonSRX motor, double p, double i, double d, double f){
		motor.config_kP(Constants.kIdx, p, Constants.kTimeout);
		motor.config_kI(Constants.kIdx, i, Constants.kTimeout);
		motor.config_kD(Constants.kIdx, d, Constants.kTimeout);
		motor.config_kF(Constants.kIdx, f, Constants.kTimeout);
		motor.config_kP(Constants.kIdx, p, Constants.kTimeout);
		motor.config_kI(Constants.kIdx, i, Constants.kTimeout);
		motor.config_kD(Constants.kIdx, d, Constants.kTimeout);
		motor.config_kF(Constants.kIdx, f, Constants.kTimeout);
	}

	public static void displayStats(){
		SmartDashboard.putNumber("Right Encoder Counts", dRightF.getSelectedSensorPosition());
		SmartDashboard.putNumber("Right Encoder RPM", toRPM(dRightF.getSelectedSensorVelocity()));
		SmartDashboard.putNumber("Right Encoder NativeV", dRightF.getSelectedSensorVelocity());
		SmartDashboard.putNumber("Left Encoder Counts", dLeftF.getSelectedSensorPosition());
		SmartDashboard.putNumber("Left Encoder RPM", toRPM(dLeftF.getSelectedSensorVelocity()));
		SmartDashboard.putNumber("Left Encoder NativeV", dLeftF.getSelectedSensorVelocity());
		SmartDashboard.putNumber("Lift Encoder Counts", liftF.getSelectedSensorPosition());
		SmartDashboard.putNumber("Lift Encoder RPM", toRPM(liftF.getSelectedSensorVelocity()));
		SmartDashboard.putNumber("Lift Encoder NativeV", liftF.getSelectedSensorVelocity());
		//
		double[] dPIDMap = {toRPM(rightTarget), toRPM(dRightF.getSelectedSensorVelocity()), toRPM(leftTarget), toRPM(dLeftF.getSelectedSensorVelocity())};
		SmartDashboard.putNumberArray("Drive PID Map", dPIDMap);
		double[] lPIDMap = {toRPM(liftTarget), toRPM(liftF.getSelectedSensorVelocity())};
		SmartDashboard.putNumberArray("Lift PID Map", lPIDMap);
	}
}
