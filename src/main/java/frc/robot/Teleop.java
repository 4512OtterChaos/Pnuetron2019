package frc.robot;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class Teleop{
	private static TalonSRX dRightF = RobotMap.dRightF;
	private static TalonSRX dLeftF = RobotMap.dLeftF;
	private static TalonSRX liftF = RobotMap.liftF;
    /* Targets */
	private static float dSpeed = 0.3f;//overall speed affecting robots actions
	public static double rightTarget;
	public static double leftTarget;
	public static double liftTarget;
	public static double wristTarget;

    public static void init(){
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
		RobotMap.configNeutral(NeutralMode.Coast, RobotMap.driveMotors);
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
		return RobotMap.toNative(tRPM);//talons use sensor units per 100ms(native units)
	}
	

	public static void displayStats(){
		
	}
}
