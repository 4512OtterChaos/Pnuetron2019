/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.driveCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.control.controlCommands.ControllerRumble;
import frc.robot.subsystems.liftgroupCommands.LiftSetHatch1;
import frc.robot.subsystems.manipulatorCommands.*;

public class DriveCycle extends CommandGroup {
  /**
   * Add your docs here.
   */
  public DriveCycle() {
    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.

    // To run multiple commands at the same time,
    // use addParallel()
    // e.g. addParallel(new Command1());
    // addSequential(new Command2());
    // Command1 and Command2 will run in parallel.

    // A command group will require all of the subsystems that each member
    // would require.
    // e.g. if Command1 requires chassis, and Command2 requires arm,
    // a CommandGroup containing them would require both the chassis and the
    // arm.
    addParallel(new ControllerRumble(0.15));
    addParallel(new ConditionalCommand(new LiftSetHatch1()){
      @Override
      protected boolean condition(){
        return !Robot.arm.getHasItem();
      }
    });
    addSequential(new VisionAlign());
    addSequential(new ConditionalCommand(new PlaceHatch(), new TakeHatch()){
      @Override
      protected boolean condition(){
        return Robot.arm.getHasItem();
      }
    });
    addParallel(new ControllerRumble(0));
  }
}
