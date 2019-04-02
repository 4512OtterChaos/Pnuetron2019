/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.control.controlCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.control.XboxJoystick;

public class DoubleRumbleEvent extends CommandGroup {

  private double previousRumble;
  public DoubleRumbleEvent(double rumble){
    previousRumble=Robot.oi.driverXbox.getRumble();
    addSequential(new ControllerRumble(rumble));
    addSequential(new WaitCommand(0.2));
    addSequential(new ControllerRumble(previousRumble));
    addSequential(new WaitCommand(0.2));
    addSequential(new ControllerRumble(rumble));
    addSequential(new WaitCommand(0.2));
    addSequential(new ControllerRumble(previousRumble));
  }
  public DoubleRumbleEvent(double rumble, double time){
    previousRumble=Robot.oi.driverXbox.getRumble();
    addSequential(new ControllerRumble(rumble));
    addSequential(new WaitCommand(time));
    addSequential(new ControllerRumble(previousRumble));
    addSequential(new WaitCommand(time));
    addSequential(new ControllerRumble(rumble));
    addSequential(new WaitCommand(time));
    addSequential(new ControllerRumble(previousRumble));
  }
  public DoubleRumbleEvent(XboxJoystick controller, double rumble){
    previousRumble=controller.getRumble();
    addSequential(new ControllerRumble(controller, rumble));
    addSequential(new WaitCommand(0.2));
    addSequential(new ControllerRumble(controller, previousRumble));
    addSequential(new WaitCommand(0.2));
    addSequential(new ControllerRumble(controller, rumble));
    addSequential(new WaitCommand(0.2));
    addSequential(new ControllerRumble(controller, previousRumble));
  }
  public DoubleRumbleEvent(XboxJoystick controller, double rumble, double time){
    previousRumble=controller.getRumble();
    addSequential(new ControllerRumble(controller, rumble));
    addSequential(new WaitCommand(time));
    addSequential(new ControllerRumble(controller, previousRumble));
    addSequential(new WaitCommand(time));
    addSequential(new ControllerRumble(controller, rumble));
    addSequential(new WaitCommand(time));
    addSequential(new ControllerRumble(controller, previousRumble));
  }
}
