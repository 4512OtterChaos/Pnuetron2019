package frc.robot.common;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

public class XboxJoystick extends Joystick {
	public enum Axes{
		LEFT_Y(1),
		LEFT_X(0),
		RIGHT_Y(5),
		RIGHT_X(4),
		LEFT_TRIGGER(3),
		RIGHT_TRIGGER(2);
		int kAxis;
		Axes(int axis) {
			kAxis=axis;
		}
	}
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
	public XboxButton leftTrigger = new XboxButton(this, 3);
	public XboxButton rightTrigger = new XboxButton(this, 2);
	
	//XboxController does not have command functionality, so we imitate it as a Joystick.
    public XboxJoystick(final int port){
		super(port);
		leftTrigger.setAxis(true);//these buttons activate on trigger presses
		rightTrigger.setAxis(true);
    }
    
    public double getLeftX(){
		return this.getRawAxis(Axes.LEFT_X.kAxis);
	}
	public double getLeftY(){
		return -this.getRawAxis(Axes.LEFT_Y.kAxis);
	}
	public double getRightX(){
		return this.getRawAxis(Axes.RIGHT_X.kAxis);
	}
	public double getRightY(){
		return -this.getRawAxis(Axes.RIGHT_Y.kAxis);
    }
    public double getLeftTrigger(){
        return this.getRawAxis(Axes.LEFT_TRIGGER.kAxis);
    }
    public double getRightTrigger(){
        return this.getRawAxis(Axes.RIGHT_TRIGGER.kAxis);
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
}