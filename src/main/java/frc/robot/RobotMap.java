/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.common.Convert;
import frc.robot.common.MConstants;

public class RobotMap {
    //---------- Dimensions (inches)
    //drivebase
    public static final double WHEEL_DIAMETER = 8;
    public static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER*Math.PI;
    public static final double INCHES_PER_COUNT = WHEEL_CIRCUMFERENCE / MConstants.kRotCounts;
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

    //---------- Encoder Positions
    //elevator
    public static final int ELEV_ERROR = 400;//allowable error for logic
    public static final int ELEV_BOTTOM = 0;
    public static final int ELEV_SUPPLY = 4800;
    public static final int ELEV_HATCH1 = 5300;
    public static final int ELEV_CARGO = 6000;
    public static final int ELEV_HATCH2 = 24600;
    public static final int ELEV_HATCH3 = 47100;
    /*
    public final int akMinB = Convert.getCounts(-85);//0 degrees straight up, positive forward
    public final int akMaxB = Convert.getCounts(-75);
    public final int akMinF = Convert.getCounts(23);
    public final int akMaxF = Convert.getCounts(120);
    public int akHatchOutF = Convert.getCounts(80);
    */
    //arm
    public static final int ARM_ERROR = 40;
    public static final int ARM_FAR_FORWARD = Convert.getCounts(120);
    public static final int ARM_FAR_BACKWARD = Convert.getCounts(-85);
    public static final int ARM_CLOSE_FORWARD = Convert.getCounts(23);
    public static final int ARM_CLOSE_BACKWARD = Convert.getCounts(-75);
    public static final int ARM_HATCH_OUT = Convert.getCounts(80);
    public static final int ARM_HATCH_IN = Convert.getCounts(85);
}
