package frc.robot;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;

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
		dRightF.setInverted(true);
		dRightB.setInverted(true);
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
		arcadeDrive(forward, turn);
	}

	public static void disable(){
		dRightF.setNeutralMode(NeutralMode.Coast);
		dRightB.setNeutralMode(NeutralMode.Coast);
		dLeftF.setNeutralMode(NeutralMode.Coast);
		dLeftB.setNeutralMode(NeutralMode.Coast);
	}

	public static void arcadeDrive(double forward, double turn){
		forward*=dSpeed;
		turn*=dSpeed;
		dRightF.set(ControlMode.PercentOutput, forward, DemandType.ArbitraryFeedForward, -turn);
		dLeftF.set(ControlMode.PercentOutput, forward, DemandType.ArbitraryFeedForward, +turn);
	}

    public static void tankDrive(double left, double right){
		left*=dSpeed;
		right*=dSpeed;
		dRightF.set(ControlMode.PercentOutput, right);
		dLeftF.set(ControlMode.PercentOutput, left);
	}

	public static void displayStats(){
	}
}
