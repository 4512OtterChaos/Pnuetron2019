/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.common.Convert;

public class RobotMap {
    //---------- motor config
    public static final int P_IDX = 0;
    public static final int P_ALLOWABLE_CLOSED = 2;
    public static final int MOTOR_PEAK = 1;
    public static final int MOTOR_NOMINAL = 0;
    //Motors
    public static final int DRIVE_F_RIGHT = 1;//Front
    public static final int DRIVE_B_RIGHT = 2;//Back
    public static final int DRIVE_F_LEFT = 3;
    public static final int DRIVE_B_LEFT = 4;
    public static final int ELEV_F = 5;
    public static final int ELEV_B = 6;
    public static final int WRIST = 7;
    public static final int INTAKE_R = 8;//Right
    public static final int INTAKE_L = 9;//Left
    //Solenoids
    public static final int FLIPPER_F = 0;//Forward
    public static final int FLIPPER_R = 1;//Reverse
    public static final int CLAW_F = 4;
    public static final int CLAW_R = 5;
    public static final int PUSHER_F = 6;
    public static final int PUSHER_R = 7;
    //Buttons
    public static final int STAGETOP = 0;
    public static final int CARRIAGETOP = 1;
    public static final int STAGEBOT = 2;
    public static final int CARRIAGEBOT = 3;
    public static final int HATCH = 4;

    //---------- Encoder Positions
    public static final int COUNTS_PER_ROTATION = 4096;
    //drive
    public static final int GYRO_COUNTS_PER_ROTATION = 8192;//actual units per rotation on the gyro
    public static final int GYRO_TURN_UNITS_PER_ROTATION = 3600;//used for scaling gyro units(10 units per degree)
    public static final double GYRO_SCALED_UNIT_RATIO = GYRO_TURN_UNITS_PER_ROTATION/(double)GYRO_COUNTS_PER_ROTATION;
    //elevator
    public static final int ELEV_ERROR = 400;//allowable error for logic
    public static final int ELEV_BOTTOM = 0;
    public static final int ELEV_HATCH1 = 4550;
    public static final int ELEV_CARGO = 6000;
    public static final int ELEV_HATCH2 = 24600;
    public static final int ELEV_HATCH3 = 47000;

    //arm
    public static final int ARM_ERROR = 40;
    public static final int ARM_FAR_FORWARD = Convert.getCounts(120);
    public static final int ARM_FAR_BACKWARD = Convert.getCounts(-85);
    public static final int ARM_CLOSE_FORWARD = Convert.getCounts(23);
    public static final int ARM_CLOSE_BACKDRIVE = Convert.getCounts(11);
    public static final int ARM_CLOSE_BACKWARD = Convert.getCounts(-75);
    public static final int ARM_HATCH_OUT = Convert.getCounts(78);
    public static final int ARM_HATCH_IN = Convert.getCounts(88);
    //----------

    //---------- Dimensions (inches)
    //drivebase
    public static final double WHEEL_DIAMETER = 6;
    public static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER*Math.PI;
    public static final double INCHES_PER_COUNT = WHEEL_CIRCUMFERENCE / COUNTS_PER_ROTATION;
    public static final double COUNTS_PER_100MS_TO_INCHES_PER_SEC = INCHES_PER_COUNT * 10;//100ms is 1/10th of 1 second
    public static final double WHEEL_BASE = 22.5;
    public static final double ROBOT_WIDTH = 33.5;//including bumpers
    public static final double ROBOT_LENGTH = 38.81;
    //
    public static final double FRONTLIME_FOV_WIDTH=59.6;//degrees
    public static final double FRONTLIME_FOV_HEIGHT=45.7;//degrees
    public static final double FRONTLIME_HEIGHT=38.5;
    public static final double FRONTLIME_DIST_CENTER=0;
    public static final double BACKLIME_HEIGHT=0;
    public static final double FRONTLIME_ANGLE=11;
    public static final double BACKLIME_ANGLE=0;
    public static final double TAPE_HEIGHT=28.5;
    public static final double TAPE_DISTANCE=0;
    //----------
}
