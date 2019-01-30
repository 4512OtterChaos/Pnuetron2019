package frc.robot;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.*;
public class MotorBase{
	/** Hardware */
	static CANSparkMax dRightF = new CANSparkMax(2, MotorType.kBrushless);
	static CANSparkMax dRightB = new CANSparkMax(1, MotorType.kBrushless);
	static CANSparkMax dLeftF = new CANSparkMax(3, MotorType.kBrushless);
    static CANSparkMax dLeftB = new CANSparkMax(4, MotorType.kBrushless);

    /* Constants */
	public static double dSpeed = 0.4;//overall speed affecting robots actions
	public static double dForwardH;//last non-zero FORWARD value
	public static double dTurnH;//last non-zero TURN value
	public static double driveK;//value affecting the slew of acceleration

    public static void init(){
		driveK = 0.2;
		dForwardH = 0;
		
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
		dRightF.setRampRate(1);
		dLeftF.setRampRate(1);
		dRightF.setInverted(true);
		setDrive(0,0);
		System.out.println("--Feed Forward Teleop--");
    }

    public static void periodic(){
		setDrive(Input.getLeftY(), Input.getRightX());
    }

    public static void setDrive(double forward, double turn){
		/*
		forward = driveK*forward + (1-driveK)*dForwardH;
		dForwardH = forward;
		turn = driveK*turn + (1-driveK)*dTurnH;
		dTurnH = turn;
		*/
		SmartDashboard.putNumber("Forward", forward);
		SmartDashboard.putNumber("Turn", turn);
		double rightMath = Input.limit(forward-turn)*dSpeed;
		double leftMath = Input.limit(forward+turn)*dSpeed;
		dRightF.set(rightMath);
		dLeftF.set(leftMath);
	}
}