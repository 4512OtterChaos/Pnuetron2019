/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.control.controlCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.control.XboxJoystick;

public class RumbleEvent extends CommandGroup {

  public RumbleEvent(double rumble){
    addSequential(new ControllerRumble(rumble));
    addSequential(new WaitCommand(0.5));
    addSequential(new ControllerRumble(0));
  }
  public RumbleEvent(double rumble, double time){
    addSequential(new ControllerRumble(rumble));
    addSequential(new WaitCommand(time));
    addSequential(new ControllerRumble(0));
  }
  public RumbleEvent(XboxJoystick controller, double rumble){
    addSequential(new ControllerRumble(controller, rumble));
    addSequential(new WaitCommand(0.5));
    addSequential(new ControllerRumble(controller, 0));
  }
  public RumbleEvent(XboxJoystick controller, double rumble, double time){
    addSequential(new ControllerRumble(controller, rumble));
    addSequential(new WaitCommand(time));
    addSequential(new ControllerRumble(controller, 0));
  }
}
