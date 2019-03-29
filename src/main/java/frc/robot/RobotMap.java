/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.common.MConstants;

public class RobotMap {
    //drivebase
    public static final double WHEEL_DIAMETER = 8;//inches
    public static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER*Math.PI;
    public static final double INCHES_PER_COUNT = WHEEL_CIRCUMFERENCE / MConstants.kRotCounts;
    public static final double COUNTS_PER_100MS_TO_INCHES_PER_SEC = INCHES_PER_COUNT * 10;//100ms is 1/10th of 1 second
    //limelight
    public static final double FRONTLIME_HEIGHT=38.5;//all inches
    public static final double BACKLIME_HEIGHT=0;
    public static final double FRONTLIME_ANGLE=11;
    public static final double BACKLIME_ANGLE=0;
    public static final double TAPE_HEIGHT=28.5;
    public static final double TAPE_DISTANCE=0;
}
