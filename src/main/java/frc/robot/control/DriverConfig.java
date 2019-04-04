package frc.robot.control;

import frc.robot.subsystems.driveCommands.*;
import frc.robot.subsystems.flipperCommands.*;
import frc.robot.subsystems.intakeCommands.*;

class DriverConfig extends ControllerConfig{

    public void assign(XboxJoystick controller){
        //drive
        controller.leftBumper.whileActive(new DriveShiftAlternate());
        controller.leftTrigger.whileActive(new DriveVroom());
        controller.rightTrigger.whileActive(new DriveVision());
        controller.rightBumper.whileActive(new VisionAlign());
        //intake
        controller.aButton.whenActive(new IntakeBackdrive());
        controller.xButton.whenActive(new ShootCargo());
        //flipper
        controller.bButton.whenActive(new FlipIntake());
    }
}