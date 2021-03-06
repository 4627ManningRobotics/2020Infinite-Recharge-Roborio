/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.DriveForward;
import frc.robot.commands.GoToStrip;
import frc.robot.commands.IncrementElevator;
import frc.robot.commands.SetElevator;
import frc.robot.commands.SetElevatorSmartDashboard;
import frc.robot.commands.SetLight;
import frc.robot.commands.ToggleClaw;
import frc.robot.commands.TurnToAngle;
import frc.robot.commands.TurnToBall;
import frc.robot.commands.ZeroBothClimbers;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  	XboxController driverController = new XboxController(RobotMap.DRIVER_CONTROLLER);
	XboxController operatorController = new XboxController(RobotMap.OPERATOR_CONTROLLER);
	
	Button dButtonA = new JoystickButton(this.driverController, RobotMap.BUTTON_A);
	Button dButtonB = new JoystickButton(this.driverController, RobotMap.BUTTON_B);
	Button dButtonX = new JoystickButton(this.driverController, RobotMap.BUTTON_X);
	Button dButtonY = new JoystickButton(this.driverController, RobotMap.BUTTON_Y);
	Button dButtonBack = new JoystickButton(this.driverController, RobotMap.BACK_BUTTON);
	Button dButtonStart = new JoystickButton(this.driverController, RobotMap.START_BUTTON);
	
	Button oButtonA = new JoystickButton(this.operatorController, RobotMap.BUTTON_A);
	Button oButtonB = new JoystickButton(this.operatorController, RobotMap.BUTTON_B);
	Button oButtonY = new JoystickButton(this.operatorController, RobotMap.BUTTON_Y);
	Button oButtonX = new JoystickButton(this.operatorController, RobotMap.BUTTON_X);
	Button oButtonBack = new JoystickButton(this.operatorController, RobotMap.BACK_BUTTON);
	Button oButtonStart = new JoystickButton(this.operatorController, RobotMap.START_BUTTON);
  	Button oButtonRightStick = new JoystickButton(this.operatorController, RobotMap.RIGHT_STICK_BUTTON);
	Button oButtonRightBumper = new JoystickButton(this.operatorController, RobotMap.RIGHT_BUMPER);
	Button oButtonLeftBumper = new JoystickButton(this.operatorController, RobotMap.LEFT_BUMPER);

  	public boolean getOperatorButton(int axis) {
		return this.operatorController.getRawButton(axis);
	}
	
	public boolean getDriverButton(int axis) {
		return this.driverController.getRawButton(axis);
	}
	
	public double getOperatorRawAxis(int axis) {
		return this.operatorController.getRawAxis(axis);
	}
	
	public double getDriverRawAxis(int axis) {
		return this.driverController.getRawAxis(axis);
	}
	
	public int getOperatorPOV(){
		return this.operatorController.getPOV();
	}

	public OI () {
		this.driverController.setRumble(RumbleType.kRightRumble, 1);
		this.driverController.setRumble(RumbleType.kLeftRumble, 1);
		this.operatorController.setRumble(RumbleType.kRightRumble, 1);
		this.operatorController.setRumble(RumbleType.kLeftRumble, 1);

		this.oButtonA.whenPressed(new SetElevator(RobotMap.ELEVATOR_LOW));
		//this.oButtonA.whenPressed(new SetElevator(RobotMap.ELEVATOR_GROUND)); 
		this.oButtonX.whenPressed(new SetElevator(RobotMap.ELEVATOR_GROUND)); 
		this.oButtonY.whenPressed(new SetElevator(RobotMap.ELEVATOR_HIGH)); 
		this.oButtonB.whenPressed(new SetElevator(RobotMap.ELEVATOR_MED));
		//this.oButtonStart.whenPressed(new TurnToAngle(-45));
		//this.oButtonStart.whenPressed(new SetLight(true));
		//this.oButtonBack.whenPressed(new SetLight(false));
		//this.oButtonX.whenPressed(new TurnToAngle(Sensors.ballReqester.getAngle()));
		//this.oButtonBack.whileHeld(new IncrementElevator(-RobotMap.ELEVATOR_INCREMENT));
		//this.oButtonStart.whileHeld(new IncrementElevator(RobotMap.ELEVATOR_INCREMENT));
		//this.oButtonBack.whenPressed(new SetElevatorSmartDashboard());

		this.oButtonLeftBumper.whenPressed(new ToggleClaw());
		this.oButtonRightBumper.whenPressed(new ToggleClaw());

		this.dButtonA.whenPressed(new GoToStrip());
		this.dButtonX.whenPressed(new DriveForward(30));
		//this.dButtonA.whenPressed(new SetFrontClimber(RobotMap.CLIMBER_GROUND, RobotMap.CLIMBER_GROUND_SLOT));
		//this.dButtonX.whenPressed(new SetBackClimber(RobotMap.CLIMBER_GROUND, RobotMap.CLIMBER_GROUND_SLOT));
		//this.dButtonB.whenPressed(new SetFrontClimber(RobotMap.CLIMBER_LIFT, RobotMap.CLIMBER_GROUND_SLOT));
		//this.dButtonY.whenPressed(new SetBackClimber(RobotMap.CLIMBER_LIFT, RobotMap.CLIMBER_GROUND_SLOT));
		//this.dButtonBack.whenPressed(new ZeroClimber(Dart.FRONT));
		this.dButtonBack.whenPressed(new ZeroBothClimbers());
	}
}
