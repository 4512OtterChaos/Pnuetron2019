package frc.robot.control;

import frc.robot.subsystems.driveCommands.*;
import frc.robot.subsystems.flipperCommands.*;
import frc.robot.subsystems.intakeCommands.*;
import frc.robot.subsystems.liftgroupCommands.*;
import frc.robot.subsystems.manipulatorCommands.*;

public class SoloConfig extends ControllerConfig{
    
    public void assign(XboxJoystick controller){
        conditions = new XboxButton[]{controller.rightBumper};//control lift when pressing these
        //drive
        controller.leftBumper.whileActive(new DriveShiftAlternate());
        controller.leftTrigger.whileActive(new DriveVroom());
        controller.rightTrigger.whileActive(new DriveVision());
        //Manipulator
        controller.aButton.whenActive(new OpenClaw(), conditions, true);
        controller.bButton.whenActive(new CloseClaw(), conditions, true);
        controller.yButton.whenActive(new OpenPusher(), conditions, true);
        controller.yButton.whenInactive(new ClosePusher(), conditions, true);
        //Lift
        controller.aButton.whenActive(new LiftSetStart(), conditions, false);
        controller.bButton.whenActive(new LiftSetHatch1(), conditions, false);
        controller.xButton.whenActive(new LiftSetHatch2(), conditions, false);
        controller.yButton.whenActive(new LiftSetHatch3(), conditions, false);
        controller.leftStickButton.whenActive(new LiftSetCargo(), conditions, false);
        //intake
        controller.xButton.whenActive(new FlipIntake(), conditions, true);
    }
}