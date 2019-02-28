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
  private static final String kHybrid = "hybrid";
  private static final String kForward = "forward";
  private static final String kNothing = "nothing";
  private String autoSelected;
  private final SendableChooser<String> autoChoose = new SendableChooser<>();

  @Override
  public void robotInit() {
    autoChoose.addOption("Auto Forward", kForward);
    autoChoose.addOption("Nothing :)", kNothing);
    autoChoose.setDefaultOption("Hybrid", kHybrid);
    SmartDashboard.putData("Auto choices", autoChoose);
    //---------------------------------//
    Constants.init();
    Input.init();
    RobotMap.config();
  }

  @Override
  public void robotPeriodic() {
    //Constants.update();
    Input.displayStats();
    Teleop.displayStats();
    RobotMap.displayStats();
  }

  @Override
  public void disabledInit() {
    Teleop.disable();
    Input.backLime.lightOff();
    Input.frontLime.lightOff();
  }
  
  @Override
  public void autonomousInit() {
    autoSelected = autoChoose.getSelected();
    if(autoSelected.equals(kHybrid)){
      Teleop.init();
    }else{
      Autonomous.init(autoSelected);
    }
  }

  @Override
  public void autonomousPeriodic() {
    if(autoSelected.equals(kHybrid)){
      Teleop.periodic();
    }else{
      Autonomous.periodic();
    }
  }

  @Override
  public void teleopInit() {
    Teleop.init();
  }

  @Override
  public void teleopPeriodic() {
    Teleop.periodic();
  }
}
