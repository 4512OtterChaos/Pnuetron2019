/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.common.Convert;

public class RobotMap {
    //motor config
    public static final int P_IDX = 0;
    public static final int P_ALLOWABLE_CLOSED = 2;
    public static final int MOTOR_PEAK = 1;
    public static final int MOTOR_NOMINAL = 0;

    //---------- Encoder Positions
    public static final int COUNTS_PER_ROTATION = 4096;
    //drive
    public static final int GYRO_COUNTS_PER_ROTATION = 8192;//actual units per rotation on the gyro
    public static final int GYRO_TURN_UNITS_PER_ROTATION = 3600;//used for scaling gyro units(10 units per degree)
    public static final double GYRO_SCALED_UNIT_RATIO = GYRO_TURN_UNITS_PER_ROTATION/(double)GYRO_COUNTS_PER_ROTATION;
    //elevator
    public static final int ELEV_ERROR = 400;//allowable error for logic
    public static final int ELEV_BOTTOM = 0;
    public static final int ELEV_HATCH1 = 4750;
    public static final int ELEV_CARGO = 6000;
    public static final int ELEV_HATCH2 = 24600;
    public static final int ELEV_HATCH3 = 47100;

    //arm
    public static final int ARM_ERROR = 40;
    public static final int ARM_FAR_FORWARD = Convert.getCounts(120);
    public static final int ARM_FAR_BACKWARD = Convert.getCounts(-85);
    public static final int ARM_CLOSE_FORWARD = Convert.getCounts(23);
    public static final int ARM_CLOSE_BACKWARD = Convert.getCounts(-75);
    public static final int ARM_HATCH_OUT = Convert.getCounts(80);
    public static final int ARM_HATCH_IN = Convert.getCounts(85);
    //----------

    //---------- Dimensions (inches)
    //drivebase
    public static final double WHEEL_DIAMETER = 8;
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
