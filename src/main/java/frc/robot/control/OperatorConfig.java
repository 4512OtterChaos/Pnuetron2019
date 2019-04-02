package frc.robot.control;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import frc.robot.Robot;
import frc.robot.subsystems.flipperCommands.FlipIntake;
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
        controller.bButton.whenActive(new ConditionalCommand(new LiftSetHatch1(), new LiftSetHatchIn()){
            @Override
            protected boolean condition() {
                return Robot.elevator.getIsSupply();
            }
        }, conditions, false);
        controller.xButton.whenActive(new LiftSetHatch2(), conditions, false);
        controller.yButton.whenActive(new LiftSetHatch3(), conditions, false);
    }
}