
package org.firstinspires.ftc.teamcode.subsys;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp
public class testdb extends LinearOpMode {

    // Declare OpMode members for each of the 4 motors.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotorEx frontLeftDrive,backLeftDrive,frontRightDrive,backRightDrive;
    private CRServo servox;
    private Servo servoy;

    @Override
    public void runOpMode() {

        frontLeftDrive = hardwareMap.get(DcMotorEx.class, "flmotor");
        backLeftDrive = hardwareMap.get(DcMotorEx.class, "blmotor");
        frontRightDrive = hardwareMap.get(DcMotorEx.class, "frmotor");
        backRightDrive = hardwareMap.get(DcMotorEx.class, "brmotor");

        servox = hardwareMap.get(CRServo.class, "servox");
        servoy = hardwareMap.get(Servo.class, "servoy");

        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.REVERSE);
        servoy.setPosition(0);

        // Wait for the game to start (driver presses START)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            double max;

            //delta of pairs
            double f;
            double b;
            double r;
            double l;

            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
            double axial   = 2000 * -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
            double lateral = 2000 * gamepad1.left_stick_x;
            double yaw     = 2000 * gamepad1.right_stick_x;

            servox.setPower(gamepad2.left_stick_x);
            servoy.setPosition(gamepad2.left_stick_y);




            // Combine the joystick requests for each axis-motion to determine each wheel's power.
            // Set up a variable for each drive wheel to save the power level for telemetry.
            double frontLeftPower  = axial + lateral + yaw;
            double frontRightPower = axial - lateral - yaw;
            double backLeftPower   = axial - lateral + yaw;
            double backRightPower  = axial + lateral - yaw;

            // Normalize the values so no wheel power exceeds 100%
            // This ensures that the robot maintains the desired motion.
            max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
            max = Math.max(max, Math.abs(backLeftPower));
            max = Math.max(max, Math.abs(backRightPower));

            if (max > 2000.0) {
                frontLeftPower  /= max;
                frontRightPower /= max;
                backLeftPower   /= max;
                backRightPower  /= max;
            }

            // Send calculated power to wheels
            frontLeftDrive.setVelocity(frontLeftPower);
            frontRightDrive.setVelocity(frontRightPower);
            backLeftDrive.setVelocity(backLeftPower);
            backRightDrive.setVelocity(backRightPower);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Front left/Right", "%4.2f, %4.2f", frontLeftPower, frontRightPower);
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", backLeftPower, backRightPower);
            telemetry.update();
        }
    }}
