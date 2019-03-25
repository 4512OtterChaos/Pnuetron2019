package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Config{

    public static final int kTimeout = 30;

    public static void configAllStart(BaseMotorController motor){   
        configFactory(motor);
        configNeutral(NeutralMode.Brake, motor);
        configPeak(MConstants.kPeak, motor);
        configNominal(MConstants.kNominal, motor);
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
        motor.setSelectedSensorPosition(0, PIDConstants.kIdx, kTimeout);
    }
    public static void configSensor(BaseMotorController motor, int pos){
        motor.setSelectedSensorPosition(pos, PIDConstants.kIdx, kTimeout);
    }
    public static void configPID(TalonSRX motor, double p, double i, double d, double f){
		motor.config_kP(PIDConstants.kIdx, p, kTimeout);
		motor.config_kI(PIDConstants.kIdx, i, kTimeout);
		motor.config_kD(PIDConstants.kIdx, d, kTimeout);
		motor.config_kF(PIDConstants.kIdx, f, kTimeout);
    }
    public static void configCruise(int uPer100ms, BaseMotorController motor){
		motor.configMotionCruiseVelocity(uPer100ms, kTimeout);
	}
	public static void configAccel(int uPer100msPer1s, BaseMotorController motor){
		motor.configMotionAcceleration(uPer100msPer1s, kTimeout);
    }
    public static void configClosed(TalonSRX motor, double p, double i, double d, double f, double peak, double ramp){
		motor.configClosedloopRamp(ramp);
		motor.configClosedLoopPeakOutput(PIDConstants.kIdx, peak);
		motor.configAllowableClosedloopError(PIDConstants.kIdx, PIDConstants.kAllowableClosed, kTimeout);
		configPID(motor, p, i, d, f);
	}
}