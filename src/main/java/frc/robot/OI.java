// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package frc.robot;

import frc.robot.commands.CloseClaw;
import frc.robot.commands.ClosePusher;
import frc.robot.commands.DriveShiftDown;
import frc.robot.commands.DriveShiftUp;
import frc.robot.commands.DriveVision;
import frc.robot.commands.DriveVroom;
import frc.robot.commands.FlipIntakeDown;
import frc.robot.commands.FlipIntakeUp;
import frc.robot.commands.IntakeBackdrive;
import frc.robot.commands.IntakeIn;
import frc.robot.commands.IntakeOut;
import frc.robot.commands.LiftSetCargo;
import frc.robot.commands.LiftSetHatch1;
import frc.robot.commands.LiftSetHatch2;
import frc.robot.commands.LiftSetHatch3;
import frc.robot.commands.LiftSetStart;
import frc.robot.commands.OpenClaw;
import frc.robot.commands.OpenPusher;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());


    public XboxJoystick driver;
    public XboxJoystick coDriver;

    public OI() {

        //read class file for info
        driver = new XboxJoystick(0);
        coDriver = new XboxJoystick(1);
        
        //Interface buttons with commands to control robot

        //Drive
        driver.leftBumper.whileActive(new DriveShiftDown());
        driver.rightBumper.whileActive(new DriveShiftUp());
        driver.rightTrigger.whileActive(new DriveVroom());//full mast
        driver.leftTrigger.whileActive(new DriveVision());//target with ll
        
        //Manipulator
        coDriver.aButton.whenActive(new OpenClaw(), coDriver.leftBumper, true);//manipulator
        coDriver.bButton.whenActive(new CloseClaw(), coDriver.leftBumper, true);//bumper disables buttons
        coDriver.yButton.whenActive(new OpenPusher(), coDriver.leftBumper, true);
        coDriver.yButton.whenInactive(new ClosePusher(), coDriver.leftBumper, true);
        
        //Lift
        coDriver.aButton.whenActive(new LiftSetStart(), coDriver.leftBumper, false);//lift
        coDriver.bButton.whenActive(new LiftSetHatch1(), coDriver.leftBumper, false);//bumper enables buttons
        coDriver.xButton.whenActive(new LiftSetHatch2(), coDriver.leftBumper, false);
        coDriver.yButton.whenActive(new LiftSetHatch3(), coDriver.leftBumper, false);
        coDriver.leftStickButton.whenActive(new LiftSetCargo(), coDriver.leftBumper, false);

        //Intake
        driver.aButton.whileActive(new IntakeIn());
        driver.bButton.whileActive(new IntakeOut());
        coDriver.xButton.whenActive(new IntakeBackdrive(), coDriver.leftBumper, true);

        //Flipper
        driver.xButton.whenActive(new FlipIntakeUp());
        driver.yButton.whenActive(new FlipIntakeDown());

    }
}

