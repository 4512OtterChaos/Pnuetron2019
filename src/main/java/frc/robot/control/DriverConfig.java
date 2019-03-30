package frc.robot.control;

import frc.robot.common.XboxJoystick;
import frc.robot.subsystems.driveCommands.*;
import frc.robot.subsystems.flipperCommands.*;
import frc.robot.subsystems.intakeCommands.*;

public class DriverConfig extends ControllerConfig{

    private XboxJoystick controller;

    public DriverConfig(XboxJoystick controller){
        super(controller);
    }

    protected void assign(){
        //drive
        controller.leftBumper.whileActive(new DriveShiftDown());
        controller.rightBumper.whileActive(new DriveShiftUp());
        controller.rightTrigger.whileActive(new DriveVroom());
        controller.leftTrigger.whileActive(new DriveVision());
        //intake
        controller.aButton.whileActive(new IntakeIn());
        controller.bButton.whileActive(new IntakeOut());
        //flipper
        controller.xButton.whenActive(new FlipIntake());
    }
}