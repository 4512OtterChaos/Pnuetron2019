package frc.robot.common;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

public class XboxJoystick extends XboxController {

	private final int LEFT_X_AXIS = 0;
	private final int LEFT_Y_AXIS = 1;
	private final int RIGHT_TRIGGER_AXIS = 2;
	private final int LEFT_TRIGGER_AXIS = 3;
	private final int RIGHT_X_AXIS = 4;
	private final int RIGHT_Y_AXIS = 5;
	//these button fields represent the raw buttons on the controller
    public XboxButton xButton = new XboxButton(this, 3);
	public XboxButton yButton = new XboxButton(this, 4);
	public XboxButton aButton = new XboxButton(this, 1);
	public XboxButton bButton = new XboxButton(this, 2);
	public XboxButton rightBumper = new XboxButton(this, 6);
	public XboxButton leftBumper = new XboxButton(this, 5);
	public XboxButton startButton = new XboxButton(this, 8);
	public XboxButton selectButton = new XboxButton(this, 7);
	public XboxButton leftStickButton = new XboxButton(this, 9);
	public XboxButton rightStickButton = new XboxButton(this, 10);
	public XboxButton leftTrigger = new XboxButton(this, 3, true);
	public XboxButton rightTrigger = new XboxButton(this, 2, true);
	
	//XboxController does not have command functionality, so we imitate it as a Joystick.
    public XboxJoystick(final int port){
		super(port);
    }
    
    public double getLeftX(){
		return this.getRawAxis(LEFT_X_AXIS);
	}
	public double getLeftY(){
		return -this.getRawAxis(LEFT_Y_AXIS);
	}
	public double getRightX(){
		return this.getRawAxis(RIGHT_X_AXIS);
	}
	public double getRightY(){
		return -this.getRawAxis(RIGHT_Y_AXIS);
    }
    public double getLeftTrigger(){
        return this.getRawAxis(LEFT_TRIGGER_AXIS);
    }
    public double getRightTrigger(){
        return this.getRawAxis(RIGHT_TRIGGER_AXIS);
	}
	
	public boolean getDpadUp(){
		return this.getPOV() == 0;
	}
	public boolean getDpadRight(){
		return this.getPOV() == 90;
	}
	public boolean getDpadDown(){
		return this.getPOV() == 180;
	}
	public boolean getDpadLeft(){
		return this.getPOV() == 270;
	}

	public void setRumble(double x){
		this.setRumble(RumbleType.kLeftRumble, x);
		this.setRumble(RumbleType.kRightRumble, x);
	}
}