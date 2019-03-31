package frc.robot.control;

import frc.robot.common.XboxButton;
import frc.robot.common.XboxJoystick;
import frc.robot.subsystems.driveCommands.*;
import frc.robot.subsystems.flipperCommands.*;
import frc.robot.subsystems.intakeCommands.*;
import frc.robot.subsystems.liftgroupCommands.*;
import frc.robot.subsystems.manipulatorCommands.*;

public class SoloConfig{
    
    public SoloConfig(XboxJoystick controller){
        XboxButton[] liftRequires = {controller.rightBumper};//control lift when pressing these
        //drive
        controller.leftBumper.whileActive(new DriveShiftNeutral());
        controller.leftTrigger.whileActive(new DriveVroom());
        controller.rightTrigger.whileActive(new DriveVision());
        //Manipulator
        controller.aButton.whenActive(new OpenClaw(), liftRequires, true);
        controller.bButton.whenActive(new CloseClaw(), liftRequires, true);
        controller.yButton.whenActive(new OpenPusher(), liftRequires, true);
        controller.yButton.whenInactive(new ClosePusher(), liftRequires, true);
        //Lift
        controller.aButton.whenActive(new LiftSetStart(), liftRequires, false);
        controller.bButton.whenActive(new LiftSetHatch1(), liftRequires, false);
        controller.xButton.whenActive(new LiftSetHatch2(), liftRequires, false);
        controller.yButton.whenActive(new LiftSetHatch3(), liftRequires, false);
        controller.leftStickButton.whenActive(new LiftSetCargo(), liftRequires, false);
        //intake
        controller.xButton.whenActive(new FlipIntake(), liftRequires, true);
    }
}