package frc.robot.control;

import frc.robot.subsystems.intakeCommands.*;
import frc.robot.subsystems.liftgroupCommands.*;
import frc.robot.subsystems.manipulatorCommands.*;

public class OperatorConfig extends ControllerConfig{

    public void assign(XboxJoystick controller){
        conditions = new XboxButton[]{controller.leftBumper};//control lift when pressing these
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
        controller.xButton.whenActive(new IntakeBackdrive(), conditions, true);
    }
}