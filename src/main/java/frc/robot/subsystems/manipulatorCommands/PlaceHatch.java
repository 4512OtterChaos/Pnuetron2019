/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.manipulatorCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.subsystems.driveCommands.DriveManual;
import frc.robot.subsystems.liftgroupCommands.LiftSetHatch1;
import frc.robot.subsystems.liftgroupCommands.LiftSetStart;

public class PlaceHatch extends CommandGroup {
  /**
   * Add your docs here.
   */
  public PlaceHatch() {
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
    addSequential(new CloseClaw());
    addSequential(new WaitCommand(0.05));
    addSequential(new OpenPusher());
    addSequential(new WaitCommand(0.05));
    addParallel(new DriveManual(-0.6, 0));
    addSequential(new WaitCommand(0.45));
    addParallel(new LiftSetStart());
    addSequential(new WaitCommand(0.75));
    addParallel(new DriveManual(0,0));
    addSequential(new ClosePusher());
  }

  @Override
  public void interrupted(){
    new ClosePusher().start();
  }
}
