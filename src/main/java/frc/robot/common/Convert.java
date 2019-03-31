package frc.robot.common;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Convert {

	/**
	 * 
	 * @param nativeU Native talon units per 100ms
	 * @return Rotations per minute
	 */
    public static double getRPM(double nativeU){
        return nativeU/4096.0*600.0f;
	}
	/**
	 * 
	 * @param motor CTRE motor controller
	 * @return Rotations per minute
	 */
    public static double getRPM(BaseMotorController motor){
		return getRPM(motor.getSelectedSensorVelocity());
	}
	/**
	 * 
	 * @param rpm Rotations per minute
	 * @return Native talon units per 100ms
	 */
    public static double getNative(double rpm){
        return rpm*4096.0/600.0f;
	}
	/**
	 * 
	 * @param motor CTRE motor controller
	 * @return Native talon units per 100ms
	 */
	public static double getNative(BaseMotorController motor){
		return motor.getSelectedSensorVelocity();
	}

	/**
	 * 
	 * @param counts Encoder counts(4096/rot)
	 * @return Degrees 
	 */
	public static double getDegrees(double counts){
		double percent = counts/MConstants.kRotCounts;
		return percent*360;
	}
	/**
	 * 
	 * @param motor CTRE motor controller
	 * @return Encoder counts(4096/rot)
	 */
	public static int getCounts(BaseMotorController motor){
		return motor.getSelectedSensorPosition();
	}
	/**
	 * 
	 * @param degree Degrees
	 * @return Encoder counts(4096/rot)
	 */
	public static int getCounts(double degree){
		double percent = degree/360.0;
		return (int)(percent*MConstants.kRotCounts);
	}

	public static double limit(double value){
		return limit(-1, 1, value);
	}
	/**
	 * 
	 * @param low Minimum value
	 * @param high Maximum value
	 * @param value Actual value
	 * @return Constrained value
	 */
    public static double limit(double low, double high, double value){
        return Math.max(low,Math.min(high,value));
	}
	/**
	 * 
	 * @param low Minimum value
	 * @param high Maximum value
	 * @param value Actual value
	 * @return Constrained value
	 */
    public static int limit(int low, int high, int value){
        return Math.max(low,Math.min(high,value));
	}
	
	/**
	 * 
	 * @param a Value 1
	 * @param b Value 2
	 * @param x Percentage(0-1) 
	 * @return Returns value between a and b, with 0 equivalent to a and 1 equivalent to b,
	 * linearly transitioning between the two based on x.
	 */
	public static double interpolate(double a, double b, double x){//given x as a fraction between a and b
		x=limit(0,1,x);
		double math = a+(x*(b-a));
		return math;
	}
}