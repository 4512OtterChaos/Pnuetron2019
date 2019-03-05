/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
  private static double mTime = -1;
  private static double rumbleTime = 0.1;
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
    Network.put("Auto choices", autoChoose);
    //---------------------------------//
    Network.init();
    Constants.init();
    OI.init();
    RobotMap.config();
  }

  @Override
  public void robotPeriodic() {
    //Constants.update();
    OI.displayStats();
    Teleop.displayStats();
    RobotMap.displayStats();
    mTime = Timer.getMatchTime();
    Network.put("Robot Elapsed", Timer.getFPGATimestamp());
    Network.put("Game Time", mTime);
  }

  @Override
  public void disabledInit() {
    Teleop.disable();
  }

  @Override
  public void disabledPeriodic() {
    OI.backLime.lightOff();
    OI.frontLime.lightOff();
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
    if(mTime<5+rumbleTime && mTime>=5){
      Teleop.matchRumble=1;
    }
    else{
      Teleop.matchRumble=0;
    }
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
    if(mTime<30+rumbleTime && mTime>=30){
      Teleop.matchRumble=0.5;
    }
    else if(mTime<15+rumbleTime && mTime>=15){
      Teleop.matchRumble=1;
    }
    else{
      Teleop.matchRumble=0;
    }
    Teleop.periodic();
  }
}
