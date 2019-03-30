package frc.robot.control;

import frc.robot.common.XboxButton;
import frc.robot.common.XboxJoystick;
import frc.robot.subsystems.flipperCommands.FlipIntake;
import frc.robot.subsystems.intakeCommands.*;
import frc.robot.subsystems.liftgroupCommands.*;
import frc.robot.subsystems.manipulatorCommands.*;

public class SoloConfig extends ControllerConfig{

    private XboxJoystick controller;

    private XboxButton[] liftRequires = {controller.leftBumper};//control lift when pressing these

    public SoloConfig(XboxJoystick controller){
        super(controller);
    }

    protected void assign(){
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