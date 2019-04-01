/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.control.controlCommands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.control.XboxJoystick;

public class ControllerRumble extends InstantCommand {
  
  private double rumble;
  private XboxJoystick controller1;
  private XboxJoystick controller2;//multi-controller drifting !

  public ControllerRumble(double rumble) {
    super();
    this.rumble=rumble;
    controller1=Robot.oi.driverXbox;
    controller2=Robot.oi.operatorXbox;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }
  public ControllerRumble(XboxJoystick controller, double rumble) {
    this(rumble);
    controller1=controller;
    controller2=null;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    controller1.setRumble(rumble);
    if(controller2!=null) controller2.setRumble(rumble);
  }

}
