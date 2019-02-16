/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
  private static final String kDefault = "Default";
  private static final String kTest = "Test";
  private String autoSelected;
  private final SendableChooser<String> autoChoose = new SendableChooser<>();

  @Override
  public void robotInit() {
    autoChoose.addOption("Default Auto", kDefault);
    autoChoose.addOption("Test Auto", kTest);
    SmartDashboard.putData("Auto choices", autoChoose);
    //---------------------------------//
    Constants.init();
    Input.init();
  }

  @Override
  public void robotPeriodic() {
    Constants.update();
    Input.displayStats();
    Motorbase.displayStats();
  }

  @Override
  public void disabledInit() {
    Motorbase.disable();
  }
  
  @Override
  public void autonomousInit() {
    Motorbase.init();
  }

  @Override
  public void autonomousPeriodic() {
    Motorbase.periodic();
  }

  @Override
  public void teleopInit() {
    Motorbase.init();
  }

  @Override
  public void teleopPeriodic() {
    Motorbase.periodic();
  }
}
