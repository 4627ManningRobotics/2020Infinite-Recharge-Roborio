/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.Arrays;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Utilities;

public class OperatorControls extends Command {

  public OperatorControls() {
    // Use requires() here to declare subsystem dependencies

      super.requires(Robot.claw);
    }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
      Robot.claw.setSpeed(Robot.oi.getOperatorRawAxis(RobotMap.RIGHT_TRIGGER)-Robot.oi.getOperatorRawAxis(RobotMap.LEFT_TRIGGER));
      if (Robot.oi.getOperatorPOV()==RobotMap.DPAD_UP){
        double newSetpoint=Robot.elevator.getCurrentSetpoint() + RobotMap.ELEVATOR_INCREMENT;
        newSetpoint = Utilities.constrain(newSetpoint, RobotMap.ELEVATOR_GROUND, RobotMap.ELEVATOR_MAX);
        Robot.elevator.setElevator(newSetpoint);
      }
      if (Robot.oi.getOperatorPOV() == RobotMap.DPAD_DOWN){
        double newSetpoint=Robot.elevator.getCurrentSetpoint() + -RobotMap.ELEVATOR_INCREMENT;
        newSetpoint = Utilities.constrain(newSetpoint, RobotMap.ELEVATOR_GROUND, RobotMap.ELEVATOR_MAX);
        Robot.elevator.setElevator(newSetpoint);  
      }
    }


  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {

  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
