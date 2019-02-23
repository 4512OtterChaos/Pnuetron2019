package frc.robot;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class Teleop{
	//reference popular components
	private static TalonSRX dRightF = RobotMap.dRightF;
	private static TalonSRX dLeftF = RobotMap.dLeftF;
	private static TalonSRX liftF = RobotMap.liftF;
	private static TalonSRX wrist = RobotMap.wrist;
	private static VictorSPX intakeR = RobotMap.intakeR;
	private static VictorSPX intakeL = RobotMap.intakeL;
	private static DigitalInput stage1Bot = RobotMap.stage1Bot;
	private static DigitalInput stage1Top = RobotMap.stage1Top;
	private static DigitalInput carriageBot = RobotMap.carriageBot;
	private static DigitalInput carriageTop = RobotMap.carriageTop;
    /* Targets */
	private static float dSpeed = 0.3f;//coefficient of drivespeed
	public static double rightTarget;
	public static double leftTarget;
	public static double liftTarget;
	public static double wristTarget;

    public static void init(){
		//*Routine
		disableMotors();
		System.out.println("--Feed Forward Teleop--");
		setIntake(-1);
    }

    public static void periodic(){
		checkInputs();
	}

	private static void checkInputs(){
		drive();
		pnuematics();
		lift();
		wrist();
		intake();
	}

	private static void drive(){
		//*arcade drive
		double forward = Input.getLeftY(Input.xbox);
		double turn = Input.getRightX(Input.xbox);
		dVelPID(forward, turn);
		//arcadeDrive(forward, turn);
		//setDrive(forward, forward);
	}

	private static void pnuematics(){
		//*Pnuematics
		if(Input.getLeftBumper(Input.xbox)) setSolenoid(RobotMap.intakeFlip, DoubleSolenoid.Value.kReverse);
		else if(Input.getRightBumper(Input.xbox)) setSolenoid(RobotMap.intakeFlip, DoubleSolenoid.Value.kForward);
		else setSolenoid(RobotMap.intakeFlip, DoubleSolenoid.Value.kOff);
		if(Input.getRightTrigger(Input.fightStick)>0) setSolenoid(RobotMap.crabber, DoubleSolenoid.Value.kForward);
		else if(Input.getRightBumper(Input.fightStick)) setSolenoid(RobotMap.crabber, DoubleSolenoid.Value.kReverse);
		if(Input.getRightStick(Input.fightStick)) setSolenoid(RobotMap.crabPop, DoubleSolenoid.Value.kForward);
		else setSolenoid(RobotMap.crabPop, DoubleSolenoid.Value.kReverse);
	}

	private static void lift(){
		//* Lift
		double joy = (Input.getLeftTrigger(Input.fightStick)!=0)? Input.getLeftY(Input.fightStick):0;
		double pos = RobotMap.getPos(liftF);
		liftTarget = calcLiftTarget(joy);
		boolean stageBot = RobotMap.getSwitch(stage1Bot);
		boolean carrTop = RobotMap.getSwitch(carriageTop);
		boolean carrBot = RobotMap.getSwitch(carriageBot);
		if(Math.abs(RobotMap.getDegrees(wrist))<15 && liftTarget<pos-100){
			liftTarget=pos;
		}
		//double target = 3900;
		//movement
		if(stageBot && !carrTop){
			liftTarget = Input.limit(liftTarget,0,22000);
			if(pos>=19000 && liftTarget>=pos+100){
				liftTarget=24000;
			}
		} else {
			if(pos<=2000 && (!stageBot&&!carrBot) && liftTarget<=pos+100 && liftTarget>=pos-100){
				liftTarget=-2000;
			}
			else if(liftTarget<0)liftTarget=Math.max(liftTarget,0);
			liftTarget = Input.limit(liftTarget, 0, 47500);
		}
		//setLift(lift*0.3);
		lPosPID(liftTarget);
	}

	public static void wrist(){
		boolean stageBot = RobotMap.getSwitch(stage1Bot);
		boolean carrTop = RobotMap.getSwitch(carriageTop);
		double feed = RobotMap.calculateArmFF(wrist); 
		if(Input.getAButton(Input.fightStick)) wristTarget = RobotMap.degreesToCounts(-90);
		else if(Input.getXButton(Input.fightStick)) wristTarget = RobotMap.degreesToCounts(90);
		else if(Input.getBButton(Input.fightStick)) wristTarget = RobotMap.degreesToCounts(0);
		//else if(Input.getYButton(Input.fightStick)) wristTarget = ;
		if(RobotMap.getDegrees(wristTarget)<20 && !carrTop) Input.limit(RobotMap.degreesToCounts(15), RobotMap.degreesToCounts(170), wristTarget);
		wristTarget = Input.limit(RobotMap.degreesToCounts(-95), RobotMap.degreesToCounts(170), wristTarget);
		SmartDashboard.putNumber("Wrist Target", wristTarget);
		SmartDashboard.putNumber("Wrist Target Degrees", RobotMap.getDegrees(wristTarget));
		wPosPID(wristTarget, feed);
		//setWrist(test);
	}

	private static void intake(){
		//* Intake
		double lTrigger = Input.getLeftTrigger(Input.xbox);
		double rTrigger = Input.getRightTrigger(Input.xbox);
		if(lTrigger != 0) setIntake(lTrigger);
		else if(rTrigger != 0) setIntake(-rTrigger);
		else setIntake(0);//-0.3
	}

	public static void disable(){
		disableMotors();
		RobotMap.configNeutral(NeutralMode.Coast, RobotMap.driveMotors);
	}
	
	/**
	 * Operates drivebase in velocity mode, using PID configuration set in Constants class.
	 * The setpoint for each PID loop(right/left) is the arcade drive percentage converted to native units.
	 * @param forward Percentage forward(-1 to 1).
	 * @param turn Percentage turn(-1 to 1).
	 */
	public static void dVelPID(double forward, double turn){
		double right = arcadeMath(forward, turn, true);
		double left = arcadeMath(forward, turn, false);
		rightTarget = calc100ms(right, Constants.dkMaxRPM);
		leftTarget = calc100ms(left, Constants.dkMaxRPM);
		dRightF.set(ControlMode.Velocity, rightTarget);
		dLeftF.set(ControlMode.Velocity, leftTarget);
	}
	/**
	 * Basic arcade drive control.
	 * @param forward Percentage forward(-1 to 1).
	 * @param turn Percentage turn(-1 to 1).
	 */
	public static void arcadeDrive(double forward, double turn){
		double right = arcadeMath(forward, turn, true);
		double left = arcadeMath(forward, turn, false);
		dRightF.set(ControlMode.PercentOutput, right);
		dLeftF.set(ControlMode.PercentOutput, left);
		//dRightF.set(ControlMode.PercentOutput, forward, DemandType.ArbitraryFeedForward, -turn);
		//dLeftF.set(ControlMode.PercentOutput, forward, DemandType.ArbitraryFeedForward, +turn);
	}
	/**
	 * 
	 * @param forward Percentage forward(-1 to 1).
	 * @param turn Percentage turn(-1 to 1).
	 * @param right If this is the right or left side.
	 * @return Desired speed for certain side to perform arcade drive.
	 */
	private static double arcadeMath(double forward, double turn, boolean right){
		forward*=dSpeed;
		turn*=dSpeed*0.9;
		if(right) return Input.limit(forward-turn);
		else return Input.limit(forward+turn);
	}
	
	/**
	 * Returns a percentage rpm as native talon units per 100 ms.
	 * @param percentOutput Percentage of range(-1 to 1).
	 * @param range Maximum range of speed(rpm).
	 * @return Native talon units per 100 ms.
	 */
	private static double calc100ms(double percentOutput, double range){//percentage rpm as native units
		double tRPM = percentOutput*range;
		return RobotMap.toNative(tRPM);//talons use sensor units per 100ms(native units)
	}

	/**
	 * Basic tank drive control.
	 * @param left Desired left speed.
	 * @param right Desired right speed.
	 */
    public static void tankDrive(double left, double right){
		left*=dSpeed;
		right*=dSpeed;
		dRightF.set(ControlMode.PercentOutput, right);
		dLeftF.set(ControlMode.PercentOutput, left);
	}

	public static void lPosPID(double pos){
		liftF.set(ControlMode.Position, pos);
	}

	public static double calcLiftTarget(double joy){
		return RobotMap.getPos(liftF)+(4000*((joy<0)? 0.66*joy:joy));
	}
	
	public static void wPosPID(double pos){
		wrist.set(ControlMode.Position, pos);
	}
	public static void wPosPID(double pos, double feedforward){
		wrist.set(ControlMode.Position, pos, DemandType.ArbitraryFeedForward, feedforward);
	}

	//basic motor control
	public static void setDrive(double left, double right){
		dRightF.set(ControlMode.PercentOutput, right);
		dLeftF.set(ControlMode.PercentOutput, left);
	}
	public static void setLift(double x){
		liftF.set(ControlMode.PercentOutput, x);
	}
	public static void setWrist(double x){
		wrist.set(ControlMode.PercentOutput, x);
	}
	public static void setIntake(double x){
		x*=0.3;
		intakeR.set(ControlMode.PercentOutput, x);
		intakeL.set(ControlMode.PercentOutput, (1.1*x)+((x<0)?-0.15:0.15));
	}
	//basic solenoid control
	public static void setSolenoid(Solenoid valve, boolean state){
		valve.set(state);
	}
	public static void setSolenoid(DoubleSolenoid valve, Value state){
		valve.set(state);
	}

	public static void disableMotors(){
		setDrive(0, 0);
		setLift(0);
		//setWrist(0);
		setIntake(0);
	}

	public static void displayStats(){
	}
}
