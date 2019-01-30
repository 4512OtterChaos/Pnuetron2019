package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
public class Input{
    /* Sensors */
    public static BuiltInAccelerometer accel;
	public static DigitalInput button;
    public static Encoder encoder;
    
    /* Controls */
	public static XboxController xbox; //object for controller --more buttons :)
    private static Hand KLEFT = GenericHID.Hand.kLeft; //constant referring to
    private static Hand KRIGHT = GenericHID.Hand.kRight;//the side of controller

    public static void init(){
        /* Controls' assignment*/
		xbox = new XboxController(0);
        
        /* Sensor assignment *///code matches electrical
        //comment these out as necessary
        accel = new BuiltInAccelerometer();
        //gyro = new ADXRS450_Gyro(Port.kOnboardCS0);
    }

    public static void reset(){
        encoder.reset();
    }

    /** deadband ? percent, used on the gamepad */
	private static double deadband(double value) {
		double deadzone = 0.04;//smallest amount you can recognize from the controller
		if ((value >= +deadzone)||(value <= -deadzone)) {
			return value;//outside deadband
		}else{
			return 0;//inside deadband
		}
    }

    //constrain input to motor ranges
    public static double limit(double value){
        return Math.max(-1,Math.min(1,value));
    }

    public static double constrainAngle(double x, double min, double max){
        while(x<min){//constrain angles 0 - 360
            x += max;
        } if(x>max){
            x = x % max;
        }
        return x;
    }

    /*
    public static double getAngleRate(){
        return gyro.getRate();
    }
    public static double getAngle(){
        return constrainAngle(gyro.getAngle(),0,360);
    }
    */
    public static double getLeftY(){
        double joy = -deadband(xbox.getY(KLEFT));
        return joy;
    }
    public static double getLeftX(){
        double joy = deadband(xbox.getX(KLEFT));
        return joy;
    }
    public static double getRightY(){
        double joy = -deadband(xbox.getY(KRIGHT));
        return joy;
    }
    public static double getRightX(){
        double joy = deadband(xbox.getX(KRIGHT));
        return joy;
    }
    public static boolean getRightBumper(){
        return xbox.getBumper(KRIGHT);
    }
    public static boolean getLeftBumper(){
        return xbox.getBumper(KLEFT);
    }
    public static boolean getAButton(){
        return xbox.getAButton();
    }
    public static boolean getXButton(){
        return xbox.getXButton();
    }
    public static boolean getYButton(){
        return xbox.getYButton();
    }
    public static boolean getBButton(){
        return xbox.getBButton();
    }
    public static double getRightTrigger(){
        return xbox.getTriggerAxis(KRIGHT);
    }
    public static double getLeftTrigger(){
        return xbox.getTriggerAxis(KLEFT);
    }
    public static int getCount(){
        return encoder.get();
    }

    public static void displayStats(){
        SmartDashboard.putNumber("RJoyX", getRightX());
        SmartDashboard.putNumber("RJoyY", getRightY());
        SmartDashboard.putNumber("LJoyX", getLeftX());
        SmartDashboard.putNumber("LJoyY", getLeftY());
    }
}