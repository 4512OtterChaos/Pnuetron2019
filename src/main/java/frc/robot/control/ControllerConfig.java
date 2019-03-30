package frc.robot.control;

import frc.robot.common.XboxJoystick;
import frc.robot.subsystems.driveCommands.*;
import frc.robot.subsystems.flipperCommands.*;
import frc.robot.subsystems.intakeCommands.*;

public abstract class ControllerConfig{

    private XboxJoystick controller;

    public ControllerConfig(XboxJoystick controller){
        this.controller=controller;
        assign();
    }

    protected abstract void assign();
}