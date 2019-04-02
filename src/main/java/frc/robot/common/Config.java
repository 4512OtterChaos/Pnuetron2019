package frc.robot.common;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.RobotMap;

public class Config{

    public static final int kTimeout = 30;

    public static void configAllStart(BaseMotorController motor){   
        configFactory(motor);
        configNeutral(NeutralMode.Brake, motor);
        configPeak(RobotMap.MOTOR_PEAK, motor);
        configNominal(RobotMap.MOTOR_NOMINAL, motor);
        configSensor(motor);
    }

    public static void configFactory(BaseMotorController motor){
        motor.configFactoryDefault();
	}
    public static void configNeutral(NeutralMode neutral, BaseMotorController motor){
        motor.setNeutralMode(neutral);
    }
    public static void configNominal(double nominal, BaseMotorController motor){
		motor.configNominalOutputForward(nominal, kTimeout);
		motor.configNominalOutputReverse(-nominal, kTimeout);
	}
    public static void configPeak(double peak, BaseMotorController motor){
        motor.configPeakOutputForward(peak, kTimeout);
        motor.configPeakOutputReverse(-peak, kTimeout);
    }
    public static void configRamp(double ramp, BaseMotorController motor){
        motor.configOpenloopRamp(ramp, kTimeout);
    }
    public static void configSensor(BaseMotorController motor){
        motor.setSelectedSensorPosition(0, RobotMap.P_IDX, kTimeout);
    }
    public static void configSensor(BaseMotorController motor, int pos){
        motor.setSelectedSensorPosition(pos, RobotMap.P_IDX, kTimeout);
    }
    public static void configPID(TalonSRX motor, double p, double i, double d, double f){
		motor.config_kP(RobotMap.P_IDX, p, kTimeout);
		motor.config_kI(RobotMap.P_IDX, i, kTimeout);
		motor.config_kD(RobotMap.P_IDX, d, kTimeout);
		motor.config_kF(RobotMap.P_IDX, f, kTimeout);
    }
    public static void configCruise(int uPer100ms, BaseMotorController motor){
		motor.configMotionCruiseVelocity(uPer100ms, kTimeout);
	}
	public static void configAccel(int uPer100msPer1s, BaseMotorController motor){
		motor.configMotionAcceleration(uPer100msPer1s, kTimeout);
    }
    public static void configClosed(TalonSRX motor, double p, double i, double d, double f, double peak, double ramp){
		motor.configClosedloopRamp(ramp);
		motor.configClosedLoopPeakOutput(RobotMap.P_IDX, peak);
		motor.configAllowableClosedloopError(RobotMap.P_IDX, RobotMap.P_ALLOWABLE_CLOSED, kTimeout);
		configPID(motor, p, i, d, f);
	}
}