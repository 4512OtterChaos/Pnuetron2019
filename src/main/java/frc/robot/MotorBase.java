package frc.robot;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.*;
public class MotorBase{
	/** Hardware */
	public static CANSparkMax dRightF = new CANSparkMax(2, MotorType.kBrushless);
	public static CANSparkMax dRightB = new CANSparkMax(1, MotorType.kBrushless);
	public static CANSparkMax dLeftF = new CANSparkMax(3, MotorType.kBrushless);
	public static CANSparkMax dLeftB = new CANSparkMax(4, MotorType.kBrushless);
	public static CANEncoder eRightF = new CANEncoder(dRightF);
	public static CANEncoder eRightB = new CANEncoder(dRightB);
	public static CANEncoder eLeftF = new CANEncoder(dLeftF);
	public static CANEncoder eLeftB = new CANEncoder(dLeftB);
	public static CANPIDController dRight = new CANPIDController(dRightF);
	public static CANPIDController dLeft = new CANPIDController(dLeftF);
	public static double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;//pid constants

    /* Constants */
	private static double dSpeed = 0.4;//overall speed affecting robots actions

    public static void init(){
		//*Constants
		kP=1e-5;
		kI=1e-6;
		kD=0;
		kIz=0;
		kFF=0;
		kMaxOutput=1;
		kMinOutput=-1;
		maxRPM=5700;//neo motor = 5700 rpm; gearbox reduction = 10.71:1;
		SmartDashboard.putNumber("P Gain", kP);
    	SmartDashboard.putNumber("I Gain", kI);
    	SmartDashboard.putNumber("D Gain", kD);
    	SmartDashboard.putNumber("I Zone", kIz);
    	SmartDashboard.putNumber("Feed Forward", kFF);
    	SmartDashboard.putNumber("Max Output", kMaxOutput);
    	SmartDashboard.putNumber("Min Output", kMinOutput);
		//*Hardware initialization
		dRightF.setIdleMode(IdleMode.kBrake);
		dRightB.setIdleMode(IdleMode.kBrake);
		dLeftF.setIdleMode(IdleMode.kBrake);
		dLeftB.setIdleMode(IdleMode.kBrake);
		dRightF.setSmartCurrentLimit(40);
		dRightB.setSmartCurrentLimit(40);
		dLeftF.setSmartCurrentLimit(40);
		dLeftB.setSmartCurrentLimit(40);
		dRightB.follow(dRightF);
		dLeftB.follow(dLeftF);
		dRightF.setRampRate(0.75);
		dLeftF.setRampRate(0.75);
		dRightF.setInverted(true);
		//*PID
		dRight.setP(kP);
		dRight.setI(kI);
		dRight.setD(kD);
		dRight.setIZone(kIz);
		dRight.setFF(kFF);
		dRight.setOutputRange(kMinOutput, kMaxOutput);
		dLeft.setP(kP);
		dLeft.setI(kI);
		dLeft.setD(kD);
		dLeft.setIZone(kIz);
		dLeft.setFF(kFF);
		dLeft.setOutputRange(kMinOutput, kMaxOutput);
		//*Routine
		tankDrive(0,0);
		System.out.println("--Feed Forward Teleop--");
    }

    public static void periodic(){
		//*pid tuning values
		double p = SmartDashboard.getNumber("P Gain", 0);
		double i = SmartDashboard.getNumber("I Gain", 0);
		double d = SmartDashboard.getNumber("D Gain", 0);
		double iz = SmartDashboard.getNumber("I Zone", 0);
		double ff = SmartDashboard.getNumber("Feed Forward", 0);
		double max = SmartDashboard.getNumber("Max Output", 0);
		double min = SmartDashboard.getNumber("Min Output", 0);
		if((p != kP)) { dRight.setP(p);dLeft.setP(p); kP = p; }
		if((i != kI)) { dRight.setI(i);dLeft.setI(i); kI = i; }
		if((d != kD)) { dRight.setD(d);dLeft.setD(d); kD = d; }
		if((iz != kIz)) { dRight.setIZone(iz);dLeft.setIZone(iz); kIz = iz; }
		if((ff != kFF)) { dRight.setFF(ff);dLeft.setFF(ff); kFF = ff; }
		if((max != kMaxOutput) || (min != kMinOutput)) { 
		  dRight.setOutputRange(min, max);
		  dLeft.setOutputRange(min, max); 
		  kMinOutput = min; kMaxOutput = max; 
		}
		//*arcade drive
		double forward = Input.getLeftY();
		double turn = Input.getRightX();
		double leftMath = ((int)((Input.limit(forward+turn)*dSpeed)*100.0)/100.0)*maxRPM;
		double rightMath = ((int)((Input.limit(forward-turn)*dSpeed)*100.0)/100.0)*maxRPM;
		SmartDashboard.putNumber("LeftMath", leftMath);
		SmartDashboard.putNumber("RightMath", rightMath);
		dLeft.setReference(leftMath, ControlType.kVelocity);
		dRight.setReference(rightMath, ControlType.kVelocity);
		//tankDrive(leftMath, rightMath);
	}

	public static void disable(){
		dRightF.setIdleMode(IdleMode.kCoast);
		dRightB.setIdleMode(IdleMode.kCoast);
		dLeftF.setIdleMode(IdleMode.kCoast);
		dLeftB.setIdleMode(IdleMode.kCoast);
	}

	public static void arcadeDrive(double forward, double turn){
		double leftMath = (int)((Input.limit(forward+turn)*dSpeed)*100.0)/100.0;
		double rightMath = (int)((Input.limit(forward-turn)*dSpeed)*100.0)/100.0;
		tankDrive(leftMath, rightMath);
	}

    public static void tankDrive(double left, double right){
		dRightF.set(right);
		dLeftF.set(left);
	}

	public static void displayStats(){
		SmartDashboard.putNumber("dLeftF-E", eLeftF.getPosition());
		SmartDashboard.putNumber("dLeftB-E", eLeftB.getPosition());
		SmartDashboard.putNumber("dRightF-E", eRightF.getPosition());
		SmartDashboard.putNumber("dRightB-E", eRightB.getPosition());
		SmartDashboard.putNumber("LeftVelocity", eLeftF.getVelocity());
		SmartDashboard.putNumber("RightVelocity", eRightF.getVelocity());
	}
}