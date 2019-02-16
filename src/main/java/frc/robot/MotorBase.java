package frc.robot;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.*;
public class MotorBase{
	/** Hardware */
	public static TalonSRX dRightF = new TalonSRX(1);
	public static TalonSRX dRightB = new TalonSRX(2);
	public static TalonSRX dLeftF = new TalonSRX(3);
	public static TalonSRX dLeftB = new TalonSRX(4);
	public static TalonSRX liftF = new TalonSRX(5);
	public static TalonSRX liftB = new TalonSRX(6);

	/*
	dRightF.
	dRightB.
	dLeftF.
	dLeftB.
	*/

    /* Constants */
	private static float dSpeed = 0.3f;//overall speed affecting robots actions
	private static double rTarget;
	private static double lTarget;

    public static void init(){
		//*Constants
		//*Config
		dRightF.configFactoryDefault();
		dRightB.configFactoryDefault();
		dLeftF.configFactoryDefault();
		dLeftB.configFactoryDefault();
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
		dRightF.configPeakOutputReverse(-1);
		dRightB.configPeakOutputReverse(-1);
		dLeftF.configPeakOutputReverse(-1);
		dLeftB.configPeakOutputReverse(-1);
		//define sensor
		dRightF.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kIdx, Constants.kTimeout);
		dRightF.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, Constants.kTimeout);

		dLeftF.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kIdx, Constants.kTimeout);
		dLeftF.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, Constants.kTimeout);
		//pid
		configPID(Constants.kP, Constants.kI, Constants.kD, Constants.kF);
		//behavior
		dRightF.configClosedLoopPeakOutput(Constants.kIdx, Constants.kPeakClosed, Constants.kTimeout);
		dRightF.configAllowableClosedloopError(Constants.kIdx, Constants.kAllowableClosed, Constants.kTimeout);
		dRightF.configClosedloopRamp(Constants.kRamp);
		dRightF.setInverted(true);
		dRightB.setInverted(true);
		dRightF.setSensorPhase(true);

		dLeftF.configClosedLoopPeakOutput(Constants.kIdx, Constants.kPeakClosed, Constants.kTimeout);
		dLeftF.configAllowableClosedloopError(Constants.kIdx, Constants.kAllowableClosed, Constants.kTimeout);
		dLeftF.configClosedloopRamp(Constants.kRamp);
		dLeftF.setInverted(false);
		dLeftB.setInverted(false);
		dLeftF.setSensorPhase(true);

		dRightB.follow(dRightF);
		dLeftB.follow(dLeftF);
		//*Routine
		tankDrive(0,0);
		System.out.println("--Feed Forward Teleop--");
    }

    public static void periodic(){
		//*pid tuning values
		//*arcade drive
		double forward = Input.getLeftY();
		double turn = Input.getRightX();
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
		rTarget = calc100ms(right, Constants.kMaxRPM);
		lTarget = calc100ms(left, Constants.kMaxRPM);
		dRightF.set(ControlMode.Velocity, rTarget);
		dLeftF.set(ControlMode.Velocity, lTarget);
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
		turn*=dSpeed*0.8;
		if(right) return Input.limit(forward-turn);
		else return Input.limit(forward+turn);
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

	public static void displayStats(){
		SmartDashboard.putNumber("Right Encoder Counts", dRightF.getSelectedSensorPosition());
		SmartDashboard.putNumber("Right Encoder RPM", toRPM(dRightF.getSelectedSensorVelocity()));
		SmartDashboard.putNumber("Right Encoder NativeV", dRightF.getSelectedSensorVelocity());
		SmartDashboard.putNumber("Left Encoder Counts", dLeftF.getSelectedSensorPosition());
		SmartDashboard.putNumber("Left Encoder RPM", toRPM(dLeftF.getSelectedSensorVelocity()));
		SmartDashboard.putNumber("Left Encoder NativeV", dLeftF.getSelectedSensorVelocity());
		double[] PIDMap = {toRPM(rTarget), toRPM(dRightF.getSelectedSensorVelocity()), toRPM(lTarget), toRPM(dLeftF.getSelectedSensorVelocity())};
		SmartDashboard.putNumberArray("PID Map", PIDMap);
	}
}
