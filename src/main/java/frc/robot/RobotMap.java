package frc.robot;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class RobotMap{
    /*
	dRightF.
	dRightB.
	dLeftF.
	dLeftB.
	liftF.
	liftB.
	wrist.
	intakeR.
	intakeL.
    */
    public static TalonSRX dRightF = new TalonSRX(1);
	public static TalonSRX dRightB = new TalonSRX(2);
	public static TalonSRX dLeftF = new TalonSRX(3);
	public static TalonSRX dLeftB = new TalonSRX(4);
	public static TalonSRX liftF = new TalonSRX(5);
	public static TalonSRX liftB = new TalonSRX(6);
	public static TalonSRX wrist = new TalonSRX(7);
	public static TalonSRX intakeR = new TalonSRX(8);
    public static TalonSRX intakeL = new TalonSRX(9);

    public static TalonSRX[] driveMotors = {dRightF, dRightB, dLeftF, dLeftB};
    public static TalonSRX[] liftMotors = {liftF, liftB};
    public static TalonSRX[] wristMotors = {wrist};
    public static TalonSRX[] intakeMotors = {intakeR, intakeL};
    public static TalonSRX[] allMotors = {dRightF, dRightB, dLeftF, dLeftB, liftF, liftB, wrist, intakeR, intakeL};
    
    /**
     * Configure the behavior of the electrical components(motor controllers, pnuematics, etc.)
     */
    public static void config(){
        configFactory(allMotors);
		//idle
		configNeutral(NeutralMode.Brake, allMotors);
		//limits
		configPeak(-1, 1, allMotors);
		//define sensor
		dRightF.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kIdx, Constants.kTimeout);
		dRightF.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, Constants.kTimeout);

		dLeftF.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kIdx, Constants.kTimeout);
		dLeftF.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, Constants.kTimeout);

		liftF.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kIdx, Constants.kTimeout);
		liftF.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, Constants.kTimeout);
        //behavior
        dRightB.follow(dRightF);
		dLeftB.follow(dLeftF);
        liftB.follow(liftF);
        intakeL.follow(intakeR);

		dRightF.setInverted(true);
		dRightB.setInverted(InvertType.FollowMaster);
		dRightF.setSensorPhase(true);

		dLeftF.setInverted(false);
		dLeftB.setInverted(InvertType.FollowMaster);
		dLeftF.setSensorPhase(true);

		liftF.setInverted(true);
		liftB.setInverted(InvertType.FollowMaster);
        liftF.setSensorPhase(true);

		wrist.setInverted(false);
        wrist.setSensorPhase(true);

        intakeR.configOpenloopRamp(Constants.ikRamp);
        intakeR.setInverted(false);
        intakeL.configOpenloopRamp(Constants.ikRamp);
        intakeL.setInverted(InvertType.OpposeMaster);
    }
    
    public static void configPeak(double negative, double positive, TalonSRX[] motors){
        for(TalonSRX motor:motors){
            motor.configPeakOutputForward(positive);
            motor.configPeakOutputReverse(negative);
        }
    }
    public static void configNeutral(NeutralMode neutral, TalonSRX[] motors){
        for(TalonSRX motor:motors){
            motor.setNeutralMode(neutral);
        }
    }
    public static void configFactory(TalonSRX[] motors){
        for(TalonSRX motor:motors){
            motor.configFactoryDefault();
        }
    }

    public static double toNative(double rpm){//convert rpm to native talon units
		return rpm*4096.0/600.0f;
	}
	public static double toRPM(double nativeU){//convert native talon units to rpm
		return nativeU/4096.0*600f;
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
		//pid
		double[] dPIDMap = {toRPM(Teleop.rightTarget), toRPM(dRightF.getSelectedSensorVelocity()), toRPM(Teleop.leftTarget), toRPM(dLeftF.getSelectedSensorVelocity())};
		SmartDashboard.putNumberArray("Drive PID Map", dPIDMap);
		double[] lPIDMap = {toRPM(Teleop.liftTarget), toRPM(liftF.getSelectedSensorVelocity())};
		SmartDashboard.putNumberArray("Lift PID Map", lPIDMap);
    }
}