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
	/*
	dRightF.
	dRightB.
	dLeftF.
	dLeftB.
	*/

    /* Constants */
	private static double dSpeed = 0.3;//overall speed affecting robots actions

    public static void init(){
		//*Constants
		//*Config
		dRightF.configFactoryDefault();
		dRightB.configFactoryDefault();
		dLeftF.configFactoryDefault();
		dLeftB.configFactoryDefault();

		dRightF.setNeutralMode(NeutralMode.Brake);
		dRightB.setNeutralMode(NeutralMode.Brake);
		dLeftF.setNeutralMode(NeutralMode.Brake);
		dLeftB.setNeutralMode(NeutralMode.Brake);

		dRightF.configPeakOutputForward(1);
		dRightB.configPeakOutputForward(1);
		dLeftF.configPeakOutputForward(1);
		dLeftB.configPeakOutputForward(1);
		dRightF.configPeakOutputReverse(-1);
		dRightB.configPeakOutputReverse(-1);
		dLeftF.configPeakOutputReverse(-1);
		dLeftB.configPeakOutputReverse(-1);

		dRightF.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);
		dRightF.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, 30);

		dLeftF.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);
		dLeftF.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, 30);

		dRightF.config_kP(0, 0.6, 30);//0.1
		dRightF.config_kI(0, 0.0, 30);
		dRightF.config_kD(0, 20.0, 30);//20
		dRightF.config_kF(0, 1023.0/6800.0, 30);
		
		dLeftF.config_kP(0, 0.6, 30);//0.1
		dLeftF.config_kI(0, 0.0, 30);
		dLeftF.config_kD(0, 20.0, 30);//20
		dLeftF.config_kF(0, 1023.0/6800.0, 30);

		dRightF.configClosedLoopPeakOutput(0, 0.75, 30);
		dRightF.configAllowableClosedloopError(0, 0, 30);
		dRightF.configClosedloopRamp(0.55);
		dRightF.setInverted(true);
		dRightB.setInverted(true);
		dRightF.setSensorPhase(true);

		dLeftF.configClosedLoopPeakOutput(0, 0.75, 30);
		dLeftF.configAllowableClosedloopError(0, 0, 30);
		dLeftF.configClosedloopRamp(0.55);
		dLeftF.setInverted(false);
		dLeftB.setInverted(false);
		dLeftF.setSensorPhase(false);

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
		testPID(forward, turn);
		//arcadeDrive(forward, turn);
	}

	public static void disable(){
		tankDrive(0, 0);
		dRightF.setNeutralMode(NeutralMode.Coast);
		dRightB.setNeutralMode(NeutralMode.Coast);
		dLeftF.setNeutralMode(NeutralMode.Coast);
		dLeftB.setNeutralMode(NeutralMode.Coast);
	}

	public static void testPID(double forward, double turn){
		double right = Input.limit(forward-turn);
		double left = Input.limit(forward+turn);
		double maxRPM = 1000*dSpeed;
		double r100ms = calc100ms(right, maxRPM);
		double l100ms = calc100ms(left, maxRPM);
		dRightF.set(ControlMode.Velocity, r100ms);
		dLeftF.set(ControlMode.Velocity, l100ms);
	}
	public static void arcadeDrive(double forward, double turn){
		forward*=dSpeed;
		turn*=dSpeed;
		double right = Input.limit(forward-turn);
		double left = Input.limit(forward+turn);
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
	public static void displayStats(){
		SmartDashboard.putNumber("Right Encoder Counts", dRightF.getSelectedSensorPosition());
		SmartDashboard.putNumber("Right Encoder RPM", toRPM(dRightF.getSelectedSensorVelocity()));
		SmartDashboard.putNumber("Left Encoder NativeV", dRightF.getSelectedSensorVelocity());
		SmartDashboard.putNumber("Left Encoder Counts", dLeftF.getSelectedSensorPosition());
		SmartDashboard.putNumber("Left Encoder RPM", toRPM(dLeftF.getSelectedSensorVelocity()));
		SmartDashboard.putNumber("Left Encoder NativeV", dLeftF.getSelectedSensorVelocity());
	}
}
