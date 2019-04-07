// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.common.Config;
import frc.robot.common.Network;
import frc.robot.control.OI;
import frc.robot.control.controlCommands.DoubleRumbleEvent;
import frc.robot.control.controlCommands.RumbleEvent;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Flipper;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Manipulator;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

    public static OI oi;

    public static Drive drive;
    public static Elevator elevator;
    public static Arm arm;
    public static Manipulator manipulator;
    public static Intake intake;
    public static Flipper flipper;
    public static Chassis chassis;

    public static final String dualConfig = "dual";
    public static final String soloConfig = "solo";
    private static String controlConfig = dualConfig;
    private static SendableChooser<String> controlChooser = new SendableChooser<>();

    private static double gTime = -1;

    public Robot(){
        super(0.02);
    }
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        drive = new Drive();
        elevator = new Elevator();
        arm = new Arm();
        manipulator = new Manipulator();
        intake = new Intake();
        flipper = new Flipper();
        chassis = new Chassis();

        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();

        oi.setDual(true);

        controlChooser.setDefaultOption("Dual", dualConfig);
        controlChooser.addOption("Solo", soloConfig);
        SmartDashboard.putData("Controller", controlChooser);
    }
    @Override
    public void robotPeriodic() {
        gTime = Timer.getMatchTime();
        Network.put("Game Time", gTime);
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit(){
        Config.configNeutral(NeutralMode.Coast, elevator.front);//make robot moveable
        Config.configNeutral(NeutralMode.Coast, elevator.back);
        Config.configNeutral(NeutralMode.Coast, drive.frontRight);
        Config.configNeutral(NeutralMode.Coast, drive.frontLeft);
        Config.configNeutral(NeutralMode.Coast, drive.backRight);
        Config.configNeutral(NeutralMode.Coast, drive.backLeft);
        Config.configNeutral(NeutralMode.Coast, arm.wrist);

        chassis.frontLime.lightOff();
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        teleopInit();//less auto, more tele
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        if(gTime==10.0) new DoubleRumbleEvent(0.5).start();
        if(gTime==5.0) new DoubleRumbleEvent(1).start();
        
        Scheduler.getInstance().run();
        arm.setButtonDisable(true);
    }

    @Override
    public void teleopInit() {
        Config.configNeutral(NeutralMode.Brake, elevator.front);//make robot less moveable
        Config.configNeutral(NeutralMode.Brake, elevator.back);
        Config.configNeutral(NeutralMode.Brake, drive.frontRight);
        Config.configNeutral(NeutralMode.Brake, drive.frontLeft);
        Config.configNeutral(NeutralMode.Brake, drive.backRight);
        Config.configNeutral(NeutralMode.Brake, drive.backLeft);
        Config.configNeutral(NeutralMode.Brake, arm.wrist); 

        arm.setTarget(arm.wrist.getSelectedSensorPosition());
        elevator.setTarget(elevator.front.getSelectedSensorPosition());
        arm.wrist.set(ControlMode.MotionMagic, arm.wrist.getSelectedSensorPosition());
        elevator.front.set(ControlMode.MotionMagic, elevator.front.getSelectedSensorPosition());

        arm.setWrist(0);
        elevator.setElev(0);
        intake.setBackdriving(false);
        intake.setIntake(0);
        manipulator.setPusher(false);
        
        controlConfig = controlChooser.getSelected();
        if(controlConfig.equals(dualConfig)){
            oi.setDual(true);
            drive.setDefaultSpeed(drive.dkSpeedLow);
            drive.setAlternateSpeed(drive.dkSpeedNeutral);
        }
        else if(controlConfig.equals(soloConfig)){
            oi.setDual(false);
            drive.setAlternateSpeed(drive.dkSpeedLow);
            drive.setDefaultSpeed(drive.dkSpeedNeutral);
        }
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        arm.setButtonDisable(false);
        Scheduler.getInstance().run();//run scheduled commands from OI
        if(gTime==22) new DoubleRumbleEvent(0.7).start();;
        if(gTime==12) new DoubleRumbleEvent(1).start();
    }

    public static boolean getDualControl(){return controlConfig.equals(dualConfig);}
}
