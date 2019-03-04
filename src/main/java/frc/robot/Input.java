package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.sensors.PigeonIMU;
public class Input{
    /* Sensors */
    private static PigeonIMU pigeon = new PigeonIMU(RobotMap.dRightB);
    private static PigeonIMU.GeneralStatus pStat = new PigeonIMU.GeneralStatus();
    private static double[] ypr = new double[3];//yaw[0], pitch[1], roll[2]
    public static Limelight frontLime;
    public static Limelight backLime;
    
    /* Controls */
    public static XboxController driver; //object for controller --more buttons :)
    public static XboxController lifter;
    public static Debouncer dUpPOV;
    public static Debouncer dDownPOV;
    public static Debouncer lUpPOV;
    public static Debouncer lDownPOV;
    private static Hand KLEFT = GenericHID.Hand.kLeft; //constant referring to
    private static Hand KRIGHT = GenericHID.Hand.kRight;//the side of controller

    public static void init(){
        /* Controls' assignment*/
        driver = new XboxController(0);
        lifter = new XboxController(1);
        dUpPOV = new Debouncer(driver, 0f, 0.2);
        dDownPOV = new Debouncer(driver, 180f, 0.2);
        lUpPOV = new Debouncer(lifter, 0f, 0.2);
        lDownPOV = new Debouncer(lifter, 180f, 0.2);
        
        /* Sensor assignment *///code matches electrical
        //comment these out as necessary
        pigeon.getGeneralStatus(pStat);
        pigeon.getYawPitchRoll(ypr);
        backLime = new Limelight("back");
        frontLime = new Limelight("front","limelight-one");
    }

    /**
     * @param value What value should be constrained
     * @return Value constrained to deadband.
     */
	private static double deadband(double value) {
		double deadzone = 0.15;//smallest amount you can recognize from the controller
		if ((value >= +deadzone)||(value <= -deadzone)) {
			return value;//outside deadband
		}else{
			return 0;//inside deadband
		}
    }

    /**
     * @param value What value should be constrained.
     * @return Value constrained for motor output(-1 to 1).
     */
    public static double limit(double value){
        return Math.max(-1,Math.min(1,value));
    }
    public static double limit(double low, double high, double value){
        return Math.max(low,Math.min(high,value));
    }

    public static double interpolate(double a, double b, double x){//given x as a fraction between a and b
		double math = a+(x*(b-a));
		return math;
	}

    public static double constrainAngle(double x, double min, double max){
        while(x<min){//constrain angles 0 - 360
            x += max;
        } if(x>max){
            x = x % max;
        }
        return x;
    }

    public static void shiftPipe(Limelight lime){
        lime.shiftPipe();
    }

    public static double getLeftY(XboxController controller){
        double joy = -deadband(controller.getY(KLEFT));
        return joy;
    }
    public static double getLeftX(XboxController controller){
        double joy = deadband(controller.getX(KLEFT));
        return joy;
    }
    public static double getRightY(XboxController controller){
        double joy = -deadband(controller.getY(KRIGHT));
        return joy;
    }
    public static double getRightX(XboxController controller){
        double joy = deadband(controller.getX(KRIGHT));
        return joy;
    }
    public static boolean getRightBumper(XboxController controller){
        return controller.getBumper(KRIGHT);
    }
    public static boolean getLeftBumper(XboxController controller){
        return controller.getBumper(KLEFT);
    }
    public static boolean getAButton(XboxController controller){
        return controller.getAButton();
    }
    public static boolean getXButton(XboxController controller){
        return controller.getXButton();
    }
    public static boolean getYButton(XboxController controller){
        return controller.getYButton();
    }
    public static boolean getBButton(XboxController controller){
        return controller.getBButton();
    }
    public static boolean getStart(XboxController controller){
        backLime.shiftPipe();
        return controller.getStartButtonPressed();
    }
    public static boolean getBack(XboxController controller){
        backLime.toggleLight();
        return controller.getBackButtonPressed();
    }
    public static double getRightTrigger(XboxController controller){
        return controller.getTriggerAxis(KRIGHT);
    }
    public static double getLeftTrigger(XboxController controller){
        return controller.getTriggerAxis(KLEFT);
    }
    public static boolean getLeftStick(XboxController controller){
        return controller.getStickButton(KLEFT);
    }
    public static boolean getLeftStickReleased(XboxController controller){
        return controller.getStickButtonReleased(KLEFT);
    }
    public static boolean getRightStick(XboxController controller){
        return controller.getStickButton(KRIGHT);
    }
    public static boolean getRightStickReleased(XboxController controller){
        return controller.getStickButtonReleased(KRIGHT);
    }
    public static void setRumble(double value, XboxController controller){
        controller.setRumble(RumbleType.kLeftRumble, value);
        controller.setRumble(RumbleType.kRightRumble, value);
    }
    public static void setRumble(double value, XboxController controller, boolean rightSide){
        if(rightSide) controller.setRumble(RumbleType.kRightRumble, value);
        else controller.setRumble(RumbleType.kLeftRumble, value);
    }

    public static void displayStats(){
        SmartDashboard.putNumber("Drive Turn", getRightX(driver));
        //SmartDashboard.putNumber("RJoyY", getRightY(driver));
        //SmartDashboard.putNumber("LJoyX", getLeftX(driver));
        SmartDashboard.putNumber("Drive Forward", getLeftY(driver));
        SmartDashboard.putNumber("Lift Y", getLeftY(lifter));
        SmartDashboard.putNumber("Wrist Y", getRightY(lifter));
        SmartDashboard.putNumberArray("YPR", ypr);
        //frontLime.displayStats();
        backLime.displayStats();
    }
}