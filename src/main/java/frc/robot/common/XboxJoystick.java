package frc.robot.common;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.*;

public class XboxJoystick extends Joystick {
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
		return this.getRawAxis(0);
	}
	public double getLeftY(){
		return -this.getRawAxis(1);
	}
	public double getRightX(){
		return this.getRawAxis(4);
	}
	public double getRightY(){
		return -this.getRawAxis(5);
    }
    public double getLeftTrigger(){
        return this.getRawAxis(3);
    }
    public double getRightTrigger(){
        return this.getRawAxis(2);
    }
}