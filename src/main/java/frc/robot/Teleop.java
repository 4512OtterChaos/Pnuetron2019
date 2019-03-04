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
	private static int liftState = 0;
	private static boolean liftSticky = false;
	private static int wristState = 1;
	private static double wristCoefficient = 1;
	public static double rightTarget;
	public static double leftTarget;
	public static boolean driveFlip = false;
	public static double liftTarget;
	public static double wristTarget=280;
	public static boolean wristOver = false;
	public static boolean wristWantOver = false;
	public static boolean wristHasHatch = false;
	public static boolean wristHasCargo = false;
	public static boolean wristTest = false;
	private static boolean wristTestDe = false;
	public static boolean intakeBackdrive = false;
	public static double liftRumble = 0;
	public static double driveRumble = 0;
	public static double matchRumble = 0;

    public static void init(){
		//*
		liftTarget=0;
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
		if(matchRumble!=0) {
			Input.setRumble(matchRumble, Input.lifter);
			Input.setRumble(matchRumble, Input.driver);
		}
		else{
			Input.setRumble(liftRumble, Input.lifter);
			Input.setRumble(driveRumble, Input.driver, false);
		}
	}

	private static void drive(){
		//*arcade drive
		double forward = Input.getLeftY(Input.driver);
		double turn = Input.getRightX(Input.driver);
		forward*=(driveFlip)? -1:1;
		turn*=(driveFlip)? -1:1;

		if(Input.dUpPOV.get()) dSpeed=(dSpeed>=0.75f)? 0.75f:dSpeed+0.25f;
		else if(Input.dDownPOV.get()) dSpeed=(dSpeed<=0.25f)? 0.25f:dSpeed-0.25f;
		else dSpeed=(float)Input.limit(0.25, 0.75, (double)dSpeed);

		if(Input.driver.getYButtonPressed()){
			intakeBackdrive=(intakeBackdrive)? false:true;
		}

		/*
		if(Input.getRightStick(Input.driver)){
			Input.backLime.lightOn();
			Input.backLime.camTarget();
		}
		else{
			Input.backLime.lightOff();
			Input.backLime.camDriver();
			Input.frontLime.lightOff();
			Input.frontLime.camDriver();
		}*/
		if(Input.getRightStick(Input.driver)) dSpeed=1f;
		if(Input.getLeftStickReleased(Input.driver)){
			driveFlip=(driveFlip)? false:true;
			driveRumble=0.5;
		}
		else{
			driveRumble=0;
		}

		dVelPID(forward, turn);
		//arcadeDrive(forward, turn);
		//setDrive(forward, forward);
	}

	private static void pnuematics(){
		//*Pnuematics
		if(Input.getLeftBumper(Input.driver)) setSolenoid(RobotMap.intakeFlip, DoubleSolenoid.Value.kReverse);//down
		else if(Input.getRightBumper(Input.driver)) setSolenoid(RobotMap.intakeFlip, DoubleSolenoid.Value.kForward);//up

		if(!Input.getLeftBumper(Input.lifter)&&!Input.getRightBumper(Input.lifter)){
			if(Input.getAButton(Input.lifter)||Input.getAButton(Input.driver)){
				setSolenoid(RobotMap.crabber, DoubleSolenoid.Value.kReverse);//open
				wristHasHatch=true;
			}
			else if(Input.getBButton(Input.lifter)||Input.getBButton(Input.driver)){
				setSolenoid(RobotMap.crabber, DoubleSolenoid.Value.kForward);//closed
				wristHasHatch=false;
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

		if(isLift && Input.getAButton(Input.lifter)){
			liftState = 1;
			liftSticky = true;
		}
		else if(isLift && Input.getBButton(Input.lifter)){
			liftState = 2;
			liftSticky = true;
		}
		else if(isLift && Input.getXButton(Input.lifter)){
			liftState = 3;
			liftSticky = true;
		}
		else if(isLift && Input.getYButton(Input.lifter)){
			liftState = 5;
			liftSticky = true;
		}
		else if(isLift && Input.getRightTrigger(Input.lifter)>0){
			liftState = 4;
			liftSticky = true;
		}

		if((wristOver&&wristTarget>0-Constants.kAllowableBehavior)||(!wristOver && wristTarget<0+Constants.kAllowableBehavior)){
			if(!carrTop){
				if(liftPos<Constants.lkHatch2) liftState=3;
				else liftState = 5;
				liftSticky=false;
			}
		}
		else if((!wristOver || !wristWantOver) && !liftSticky){
			liftState=-1;
		}
		
		if(liftState==1){
			liftTarget = Constants.lkBottom;
			wristTarget = RobotMap.getCounts(16);
		}
		else if(liftState==2) {
			liftTarget = Constants.lkHatch1;
			wristTarget = RobotMap.getCounts(95);
		}
		else if(liftState==3) liftTarget = Constants.lkHatch2;
		else if(liftState==4) liftTarget = Constants.lkCargoIn;
		else if(liftState==5) liftTarget = Constants.lkHatch3;

		if(joy!=0){
			liftTarget=calcLiftManual(joy);
			liftState=-1;
			liftSticky=false;
		}

		double targetAdjusted = liftTarget;

		if((wristDeg<=10 && wristDeg>=-66) && targetAdjusted<liftPos-Constants.kAllowableBehavior){//null zone
			if(liftPos<Constants.lkHatch2+2500)
				targetAdjusted=Constants.lkHatch2;
			else if(liftPos<Constants.lkHatch3+500)
				targetAdjusted=Constants.lkHatch3;
		}
		
		if(wristOver) targetAdjusted=Math.max(Constants.lkLowOver, targetAdjusted);

		targetAdjusted = Input.limit(Constants.lkBottom, Constants.lkHatch3, targetAdjusted);

		if(liftPos<=2000 && (!stageBot||!carrBot) && targetAdjusted<=liftPos+Constants.kAllowableBehavior){
			targetAdjusted=-1000;
		}
		else if(liftPos<=2000 && targetAdjusted<=liftPos+Constants.kAllowableBehavior){
			liftState=0;
		}

		if(liftPos<=Constants.lkHatch1){
			RobotMap.configAccel((int)(Constants.lkCruise/(Constants.lkAccelTime*1.5)), liftF);
		}
		else {
			RobotMap.configAccel((int)(Constants.lkCruise/(Constants.lkAccelTime)), liftF);
		}


		//double antiGrav = (liftF.getClosedLoopTarget()-liftF.getSelectedSensorPosition()<-Constants.kAllowableBehavior)? RobotMap.toRPM(liftF.getSelectedSensorVelocity()):0;
		//double antiGrav = (liftF.getClosedLoopTarget()-liftF.getSelectedSensorPosition()<-Constants.kAllowableBehavior)? 0.1:0;
		SmartDashboard.putNumber("Lift Target", targetAdjusted);
		if(liftState!=0) lMotionPID(targetAdjusted, 0.05);
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
		double wristMinB = RobotMap.getCounts(Constants.wkMinB);
		double wristMaxB = RobotMap.getCounts(Constants.wkMaxB);
		double wristMinF = RobotMap.getCounts(Constants.wkMinF);
		double wristMaxF = RobotMap.getCounts(Constants.wkMaxF);

		if(Input.getRightStickReleased(Input.lifter)){
			wristHasCargo=(wristHasCargo)? false:true;
		}
		if(Input.getLeftStickReleased(Input.lifter)){
			wristTest=(wristTest)? false:true;
			wristTestDe=true;
		}

		if(wristHasHatch || wristHasCargo){
			//liftRumble=0.5;
			wristCoefficient = 2.2;
			Constants.wkMinF=25;
		}
		else{
			liftRumble=0;
			wristCoefficient = 1;
			Constants.wkMinF=13;
		}

		wristOver = wristDeg<1;
		wristWantOver = ((wristOver&&wristTarget>0-Constants.kAllowableBehavior)||(!wristOver && wristTarget<0+Constants.kAllowableBehavior));

		if(bump){
			if(Input.getAButton(Input.lifter)) wristTarget=wristMinB;
			else if(Input.getBButton(Input.lifter)||Input.getXButton(Input.lifter)) wristTarget=0;
			else if(Input.getYButton(Input.lifter)) wristTarget=RobotMap.getCounts(80);
		}
		else{
			wristTarget = (joyY!=0)? calcWristManual(joyY):wristTarget;
		}
		//if(Input.getRightTrigger(Input.lifter)>0) wristTarget = calcWristIntake();
		if(Input.getRightTrigger(Input.lifter)>0){
			wristTarget = wristMaxF;
			liftTarget=Constants.lkCargoIn;
		}
		
		double targetAdjusted = wristTarget;
		/*
		if(liftPos<=Constants.lkCargoIn && !wristOver && !wristWantOver)//avoid chassis
			targetAdjusted=Math.min(targetAdjusted,
			((wristMaxF-wristMinF)*((liftPos-100)/Constants.lkCargoIn))+wristMinF);
			*/
		if(wristOver && !carrTop)//avoid elevator
			targetAdjusted=Math.min(wristMaxB, targetAdjusted);
		else if(!wristOver && !carrTop)//avoid elevator
			targetAdjusted=Math.max(wristMinF, targetAdjusted);
		
		targetAdjusted = Input.limit(wristMinB, wristMaxF, targetAdjusted);


		if(wristTestDe && wristTest){
			RobotMap.configPID(wrist, Constants.tkP, Constants.tkI, Constants.tkD, Constants.tkF);
			RobotMap.configCruise(Constants.tkCruise, wrist);
			RobotMap.configAccel(Constants.tkAccel, wrist);
			wristTestDe=false;
		}
		else if(wristTestDe){
			RobotMap.configPID(wrist, Constants.wkP, Constants.wkI, Constants.tkD, Constants.tkF);
			RobotMap.configCruise(Constants.wkCruise, wrist);
			RobotMap.configAccel(Constants.wkAccel, wrist);
			wristTestDe=false;
		}
		Network.put("Wrist Test", wristTest);
		Network.put("Wrist ATarDeg", RobotMap.getDegrees(targetAdjusted));
		if(liftState==0) wMotionPID((intakeBackdrive)? -50:300);
		else if(wristState==1) wMotionPID(targetAdjusted, feed);//go limp
		//wPosPID(targetAdjusted, feed);
		//setWrist(test);
	}

	private static void intake(){
		//* Intake
		double lTrigger = Input.getLeftTrigger(Input.driver);
		double rTrigger = Input.getRightTrigger(Input.driver);
		if(lTrigger != 0) setIntake(lTrigger);//positive out
		else if(rTrigger != 0) setIntake(-rTrigger);//negative in
		else if(intakeBackdrive) setIntake(-0.25);
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
		turn*=dSpeed*1.75;
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
		return RobotMap.getPos(liftF)+(1000*joy);
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
		return RobotMap.getPos(wrist)+(250*joy);
	}

	public static double calcWristFF(){
		//i want wind speed accounted for !!
		double gravity = -Math.sin(Math.toRadians(RobotMap.getDegrees(wrist)));//how much gravity affects (0-1)
		double stall = Constants.wkAntiGrav*wristCoefficient;//how much power to counter max gravity
		double liftInertia = liftF.getMotorOutputPercent()/6.0;//how much power to counter lift movement
		double driveInertia = (dRightF.getMotorOutputPercent()+dLeftF.getMotorOutputPercent())/2.0;//forward heading
		driveInertia*=Math.cos(Math.toRadians(RobotMap.getDegrees(wrist)));//how much power to counter drive movement
		double counterHatch = (wristOver)? Constants.wkAntiHatch:-Constants.wkAntiHatch;
		counterHatch = (wristHasHatch && !(liftState==0))? counterHatch:0;
		double counterVector = (gravity*(stall)+counterHatch);//total power to keep arm stable
		return Input.limit(counterVector);
	}
	public static double calcWristIntake(){
		double liftPos = RobotMap.getPos(liftF);
		double liftTop = Constants.lkCargoIn;//maximum lift counts to optimal pos
		double liftBot = 5000;//minimum lift to 90 degrees
		double degTop = 170;
		double degBot = 90;
		liftPos = Input.limit(liftBot, liftTop, liftPos);
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
		x*=(x<0)? 0.3:1;
		intakeR.set(ControlMode.PercentOutput, x);
		intakeL.set(ControlMode.PercentOutput, (x)+((x!=0)? ((x<0)?-0.15:0.15):0));
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
		String[] tabs = {"Drive","PID"};
		Network.put("Wrist Target", wristTarget, tabs);
		Network.put("Wrist TarDeg", RobotMap.getDegrees(wristTarget), tabs);
		Network.put("Lift  Target", liftTarget);
	}
}
