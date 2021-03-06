/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Utilities;
import frc.robot.commands.DriverControls;

/**
 * VROOM VROOM
 */
public class DriveTrain extends Subsystem {

  private final TalonSRX leftMotor1 = new TalonSRX(RobotMap.MOTORS.LEFT_MOTOR_1.ordinal()); // drive train motors
  private VictorSPX leftMotor2 = null;
  private TalonSRX leftMotor3 = null;
  private final TalonSRX rightMotor1 = new TalonSRX(RobotMap.MOTORS.RIGHT_MOTOR_1.ordinal());
  private  VictorSPX rightMotor2 = null;
  private  TalonSRX rightMotor3 = null;

  private final double distancePerPulse = (2.0 * RobotMap.WHEEL_DIAMETER * RobotMap.ENCODER_GEAR_RATIO) / 
  (RobotMap.ENCODER_PULSES_PER_REVOLUTION);

  private int leftOffset = 0;
  private int rightOffset = 0;

  public DriveTrain() {

    if(Robot.jankMode){
      this.leftMotor3 = new TalonSRX(RobotMap.MOTORS.LEFT_MOTOR_2.ordinal());
      this.rightMotor3 = new TalonSRX(RobotMap.MOTORS.RIGHT_MOTOR_2.ordinal());
      this.leftMotor3.follow(this.leftMotor1);
      this.rightMotor3.follow(this.rightMotor1);
      this.rightMotor3.setInverted(true);
    }else{
      this.leftMotor2 = new VictorSPX(RobotMap.MOTORS.LEFT_MOTOR_2.ordinal());
      this.rightMotor2 =new VictorSPX(RobotMap.MOTORS.RIGHT_MOTOR_2.ordinal());
      this.leftMotor2.follow(this.leftMotor1);
      this.rightMotor2.follow(this.rightMotor1);
      this.rightMotor2.setInverted(true);
    }

    
    // configure the time it takes for the motors to reach max speed
    this.leftMotor1.configOpenloopRamp(RobotMap.RAMP_RATE, 0);
    this.rightMotor1.configOpenloopRamp(RobotMap.RAMP_RATE, 0);

    // configure peak outputs for both the driver and the PID
    this.leftMotor1.configPeakOutputForward(1.0);
    this.rightMotor1.configPeakOutputForward(1.0);

    this.leftMotor1.configPeakOutputReverse(-1.0);
    this.rightMotor1.configPeakOutputReverse(-1.0);

    this.leftMotor1.setInverted(false);
    this.rightMotor1.setInverted(true); // the right side is mounted backwards

    //current limiting 
    this.leftMotor1.configPeakCurrentLimit(RobotMap.CURRENT_LIMIT, 0); 
    this.leftMotor1.configPeakCurrentDuration(RobotMap.CURRENT_LIMIT_DURATION, 0);
    this.leftMotor1.configContinuousCurrentLimit(RobotMap.CONTINUOUS_CURRENT_LIMIT, 0); 
    this.leftMotor1.enableCurrentLimit(true);

    this.rightMotor1.configPeakCurrentLimit(RobotMap.CURRENT_LIMIT, 0); 
    this.rightMotor1.configPeakCurrentDuration(RobotMap.CURRENT_LIMIT_DURATION, 0);
    this.rightMotor1.configContinuousCurrentLimit(RobotMap.CONTINUOUS_CURRENT_LIMIT, 0); 
    this.rightMotor1.enableCurrentLimit(true);
    
  }

  public void initDefaultCommand() {
     super.setDefaultCommand(new DriverControls());
  }

  public void setLeftMotor(double motorSetting) {
    motorSetting = Utilities.scale(motorSetting, RobotMap.MAX_SPEED);
    this.leftMotor1.set(ControlMode.PercentOutput, motorSetting); // 2 is following 1

    // check current and ensure safe limits
    //if (this.leftMotor1.getOutputCurrent() > RobotMap.CURRENT_LIMIT) {
      //this.leftMotor1.set(ControlMode.Current, RobotMap.CURRENT_LIMIT);
    //}
  }

  public void setRightMotor(double motorSetting) {
    motorSetting = Utilities.scale(motorSetting, RobotMap.MAX_SPEED);
    this.rightMotor1.set(ControlMode.PercentOutput, motorSetting); //2 is following 1

    // check current and ensure safe limits
    //if (this.rightMotor1.getOutputCurrent() > RobotMap.CURRENT_LIMIT) {
      //this.rightMotor1.set(ControlMode.Current, RobotMap.CURRENT_LIMIT);
    //}
  }

  public double getDistance(){
    return distancePerPulse * (this.getLeftTicks() + this.getRightTicks()) / 2d; 
  }

  public int getLeftTicks(){
    return this.leftMotor1.getSelectedSensorPosition() - this.leftOffset;
  }

  public int getRightTicks(){
    return this.rightMotor1.getSelectedSensorPosition() - this.rightOffset;
  }

  public void resetEncoders(){
    this.leftOffset = this.leftMotor1.getSelectedSensorPosition();
    this.rightOffset = this.rightMotor1.getSelectedSensorPosition();
  }
  
}
