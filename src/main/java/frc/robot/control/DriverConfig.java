package frc.robot.control;

import frc.robot.common.XboxJoystick;
import frc.robot.subsystems.driveCommands.*;
import frc.robot.subsystems.flipperCommands.*;
import frc.robot.subsystems.intakeCommands.*;

class DriverConfig{

    public DriverConfig(XboxJoystick controller){
        //drive
        controller.leftBumper.whileActive(new DriveShiftNeutral());
        controller.leftTrigger.whileActive(new DriveVroom());
        controller.rightTrigger.whileActive(new DriveVision());
        //intake
        controller.aButton.whileActive(new IntakeIn());
        controller.bButton.whileActive(new IntakeOut());
        //flipper
        controller.xButton.whenActive(new FlipIntake());
    }
}