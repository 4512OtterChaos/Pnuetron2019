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
	private static float dSpeed = 0.5f;//coefficient of drivespeed
	private static int liftState = -1;
	private static double wristCoefficient = 1;
	public static double rightTarget;
	public static double leftTarget;
	public static double liftTarget;
	public static double wristTarget;
	public static boolean wristOver = false;

    public static void init(){
		//*
		liftTarget=0;
		wristTarget=280;
		rightTarget=0;
		leftTarget=0;
		//*Routine
		disableMotors();
		System.out.println("--Feed Forward Teleop--");
		setIntake(-1);
		//Input.backLime.lightOn();
    }

    public static void periodic(){
		drive();
		pnuematics();
		lift();
		wrist();
		intake();
	}

	private static void drive(){
		//*arcade drive
		double forward = Input.getLeftY(Input.driver);
		double turn = Input.getRightX(Input.driver);

		if(Input.dUpPOV.get()) dSpeed=(dSpeed==0.75f)? 0.75f:dSpeed+0.25f;
		else if(Input.dDownPOV.get()) dSpeed=(dSpeed==0.25f)? 0.25f:dSpeed-0.25f;

		dVelPID(forward, turn);
		//arcadeDrive(forward, turn);
		//setDrive(forward, forward);
	}

	private static void pnuematics(){
		//*Pnuematics
		if(Input.getLeftBumper(Input.driver)) setSolenoid(RobotMap.intakeFlip, DoubleSolenoid.Value.kReverse);//down
		else if(Input.getRightBumper(Input.driver)) setSolenoid(RobotMap.intakeFlip, DoubleSolenoid.Value.kForward);//up

		if(!Input.getLeftBumper(Input.lifter)){
			if(Input.getAButton(Input.lifter)||Input.getAButton(Input.driver)){
				setSolenoid(RobotMap.crabber, DoubleSolenoid.Value.kReverse);//open
				wristCoefficient = 2;
			}
			else if(Input.getBButton(Input.lifter)||Input.getBButton(Input.driver)){
				setSolenoid(RobotMap.crabber, DoubleSolenoid.Value.kForward);//closed
				wristCoefficient = 1;
			}
	
			if(Input.getXButton(Input.lifter)||Input.getXButton(Input.driver)) setSolenoid(RobotMap.crabPop, DoubleSolenoid.Value.kForward);
			else setSolenoid(RobotMap.crabPop, DoubleSolenoid.Value.kReverse);
		}
	}

	private static void lift(){
		//* Lift
		double liftPos = RobotMap.getPos(liftF);
		double wristPos = RobotMap.getPos(wrist);
		double wristDeg = RobotMap.getDegrees(wrist);

		boolean stageBot = RobotMap.getSwitch(stage1Bot);
		boolean carrTop = RobotMap.getSwitch(carriageTop);
		boolean carrBot = RobotMap.getSwitch(carriageBot);

		boolean isLift = Input.getLeftBumper(Input.lifter);
		double joy = Input.getLeftY(Input.lifter);

		if(isLift && Input.getAButton(Input.lifter)) liftState = 1;
		else if(isLift && Input.getBButton(Input.lifter)) liftState = 2;
		else if(isLift && Input.getXButton(Input.lifter)) liftState = 3;
		else if(isLift && Input.getYButton(Input.lifter)) liftState = 4;

		if((wristOver&&wristTarget>0-Constants.kAllowableBehavior)||(!wristOver && wristTarget<0+Constants.kAllowableBehavior)){
			if(!carrTop){
				if(liftPos<Constants.lkHatch2) liftState=3;
				else liftState = 4;
			}
		}
		
		if(liftState==1){
			liftTarget = Constants.lkBottom;
			wristTarget = RobotMap.getCounts(15);
		}
		else if(liftState==2) liftTarget = Constants.lkHatch1;
		else if(liftState==3) liftTarget = Constants.lkHatch2;
		else if(liftState==4) liftTarget = Constants.lkHatch3;

		if(joy!=0){
			liftTarget=calcLiftManual(joy);
			liftState=-1;
		}

		double targetAdjusted = liftTarget;

		if((wristDeg<=10 && wristDeg>=-66) && targetAdjusted<liftPos-Constants.kAllowableBehavior) targetAdjusted=liftPos;
		
		if(wristOver) Input.limit(7000, Constants.lkHatch3, targetAdjusted);

		targetAdjusted = Input.limit(Constants.lkBottom, Constants.lkHatch3, targetAdjusted);

		if(liftPos<=2000 && (!stageBot||!carrBot) && targetAdjusted<=liftPos+Constants.kAllowableBehavior){
			targetAdjusted=-1000;
		}
		else if(liftPos<=2000 && targetAdjusted<=liftPos+Constants.kAllowableBehavior){
			liftState=0;
		}

		//double antiGrav = (liftF.getClosedLoopTarget()-liftF.getSelectedSensorPosition()<-Constants.kAllowableBehavior)? RobotMap.toRPM(liftF.getSelectedSensorVelocity()):0;
		//double antiGrav = (liftF.getClosedLoopTarget()-liftF.getSelectedSensorPosition()<-Constants.kAllowableBehavior)? 0.1:0;
		SmartDashboard.putNumber("Lift Target", targetAdjusted);
		if(liftState!=0) lMotionPID(targetAdjusted);//go limp
		else{
			setLift(0);
			liftTarget=0;
		}
		//lPosPID(targetAdjusted, antiGrav);
	}

	public static void wrist(){
		boolean carrTop = RobotMap.getSwitch(carriageTop);
		boolean bump = Input.getRightBumper(Input.lifter);

		double joyY = Input.getRightY(Input.lifter);
		double joyX = Input.getRightX(Input.lifter);
		double feed = calcWristFF();
		double wristDeg = RobotMap.getDegrees(wrist);
		double liftPos = RobotMap.getPos(liftF);
		double wristMinB = -95;
		double wristMaxB = -70;
		double wristMinF = 13;
		double wristMaxF = 170;

		wristOver = wristDeg<-1;

		if(bump){
			if(joyY==1) wristTarget = RobotMap.getCounts(90);
			else if(joyY==-1) wristTarget = RobotMap.getCounts(-90);
			else if(Math.abs(joyX)==1) wristTarget = RobotMap.getCounts(0);
		}else{
			wristTarget = (joyY!=0)? calcWristManual(joyY):wristTarget;
		}
		if(Input.getRightTrigger(Input.lifter)>0) wristTarget = calcWristIntake();
		
		double targetAdjusted = wristTarget;

		if(wristOver && !carrTop)
			targetAdjusted=Math.min(RobotMap.getCounts(wristMaxB), targetAdjusted);
		else if(!wristOver && !carrTop)
			targetAdjusted=Math.max(RobotMap.getCounts(wristMinF), targetAdjusted);
		if(liftPos<=29000 && !wristOver)
			targetAdjusted=Input.limit(RobotMap.getCounts(wristMinF), Input.interpolate(280, RobotMap.getCounts(wristMaxF), liftPos/29000.0), targetAdjusted);
		
		targetAdjusted = Input.limit(RobotMap.getCounts(wristMinB), RobotMap.getCounts(wristMaxF), targetAdjusted);
		
		SmartDashboard.putNumber("Wrist Target", wristTarget);
		SmartDashboard.putNumber("Wrist TargetA Degrees", RobotMap.getDegrees(targetAdjusted));
		SmartDashboard.putNumber("Wrist Target Degrees", RobotMap.getDegrees(wristTarget));
		if(liftState!=0) wMotionPID(targetAdjusted, feed);//go limp
		else{
			setWrist(0);
			wristTarget = 280;
		}
		//wPosPID(targetAdjusted, feed);
		//setWrist(test);
	}

	private static void intake(){
		//* Intake
		double lTrigger = Input.getLeftTrigger(Input.driver);
		double rTrigger = Input.getRightTrigger(Input.driver);
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
		turn*=dSpeed*0.95;
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

	public static void lMotionPID(double pos){
		liftF.set(ControlMode.MotionMagic, pos);
	}
	public static void lMotionPID(double pos, double feed){
		liftF.set(ControlMode.MotionMagic, pos, DemandType.ArbitraryFeedForward, feed);
	}
	public static void lPosPID(double pos){
		liftF.set(ControlMode.Position, pos);
	}
	public static void lPosPID(double pos, double feed){
		liftF.set(ControlMode.Position, pos, DemandType.ArbitraryFeedForward, feed);
	}

	public static double calcLiftManual(double joy){
		return RobotMap.getPos(liftF)+(2000*joy);
	}
	
	public static void wMotionPID(double pos){
		wrist.set(ControlMode.MotionMagic, pos);
	}
	public static void wMotionPID(double pos, double feed){
		wrist.set(ControlMode.MotionMagic, pos, DemandType.ArbitraryFeedForward, feed);
	}
	public static void wPosPID(double pos){
		wrist.set(ControlMode.Position, pos);
	}
	public static void wPosPID(double pos, double feed){
		wrist.set(ControlMode.Position, pos, DemandType.ArbitraryFeedForward, feed);
	}

	public static double calcWristManual(double joy){
		return RobotMap.getPos(wrist)+(500*joy);
	}

	public static double calcWristFF(){
		//i want wind speed accounted for !!
		double gravity = Math.sin(Math.toRadians(RobotMap.getDegrees(wrist)));//how much gravity affects (0-1)
		double stall = Constants.wkAntiGrav*wristCoefficient;//how much power to counter max gravity
		double liftInertia = liftF.getMotorOutputPercent()/6.0;//how much power to counter lift movement
		double driveInertia = (dRightF.getMotorOutputPercent()+dLeftF.getMotorOutputPercent())/2.0;//forward heading
		driveInertia*=Math.cos(Math.toRadians(RobotMap.getDegrees(wrist)));//how much power to counter drive movement
		double counterVector = -gravity*(stall+liftInertia);//total power to keep arm stable
		return Input.limit(counterVector);
	}
	public static double calcWristIntake(){
		double liftPos = RobotMap.getPos(liftF);
		double liftTop = 40000;//maximum lift counts to optimal pos
		double liftBot = 5000;//minimum lift to 90 degrees
		double degTop = 170;
		double degBot = 90;
		liftPos = Input.limit(5000, 40000, liftPos);
		liftPos-=liftBot;
		liftTop-=liftBot;
		liftBot=0;
		double angle = degTop-(((liftTop-liftPos)/liftTop)*(degTop-degBot));
		return angle;
	}

	//arbitrary motor control
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
		setWrist(0);
		setIntake(0);
	}

	public static void displayStats(){
	}
}
