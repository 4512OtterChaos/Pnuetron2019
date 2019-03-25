package frc.robot;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Convert {

	
    public static double getRPM(double nativeU){
        return nativeU/4096.0*600.0f;
    }
    public static double getRPM(BaseMotorController motor){
		return getRPM(motor.getSelectedSensorVelocity());
    }
    public static double getNative(double rpm){
        return rpm*4096.0/600.0f;
    }
	public static double getNative(BaseMotorController motor){
		return motor.getSelectedSensorVelocity();
	}

	public static double getDegrees(TalonSRX arm){//straight up is 0 degrees, negative forward
		return getDegrees(getCounts(arm));
	}
	public static double getDegrees(double counts){
		double percent = counts/MConstants.kRotCounts;
		return percent*360;
	}
	public static int getCounts(BaseMotorController motor){
		return motor.getSelectedSensorPosition();
	}
	public static int getCounts(double degree){
		double percent = degree/360.0;
		return (int)(percent*MConstants.kRotCounts);
	}

	public static double limit(double value){
        return Math.max(-1,Math.min(1,value));
    }
    public static double limit(double low, double high, double value){
        return Math.max(low,Math.min(high,value));
	}
	
	public static double interpolate(double a, double b, double x){//given x as a fraction between a and b
		x=limit(0,1,x);
		double math = a+(x*(b-a));
		return math;
	}
}