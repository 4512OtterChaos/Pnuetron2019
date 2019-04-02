package frc.robot.control;

import frc.robot.subsystems.driveCommands.*;
import frc.robot.subsystems.flipperCommands.*;
import frc.robot.subsystems.intakeCommands.*;

class DriverConfig extends ControllerConfig{

    public void assign(XboxJoystick controller){
        //drive
        controller.leftBumper.whileActive(new DriveShiftAlternate());
        controller.leftTrigger.whileActive(new DriveVroom());
        controller.rightTrigger.whileActive(new VisionAlign());
        //intake
        controller.aButton.whileActive(new IntakeIn());
        controller.bButton.whileActive(new IntakeOut());
        //flipper
        controller.xButton.whenActive(new FlipIntake());
    }
}