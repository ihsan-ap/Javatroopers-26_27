package org.firstinspires.ftc.teamcode.subsys;//
//package org.firstinspires.ftc.teamcode.subsys;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.CRServo;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.util.ElapsedTime;
//import com.qualcomm.robotcore.util.Range;
//
//
//
//@TeleOp
//public class testgimbal extends LinearOpMode {
//
//    // Declare OpMode members for each of the 4 motors.
//    private ElapsedTime runtime = new ElapsedTime();
//    private DcMotor frontLeftDrive,backLeftDrive,frontRightDrive,backRightDrive;
//    private CRServo servox;
//    private Servo servoy;
//
//    @Override
//    public void runOpMode() {
//
//        frontLeftDrive = hardwareMap.get(DcMotor.class, "flmotor");
//        backLeftDrive = hardwareMap.get(DcMotor.class, "blmotor");
//        frontRightDrive = hardwareMap.get(DcMotor.class, "frmotor");
//        backRightDrive = hardwareMap.get(DcMotor.class, "brmotor");
//        servox = hardwareMap.get(CRServo.class, "servox");
//        servoy = hardwareMap.get(Servo.class, "servoy");
//        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
//        backRightDrive.setDirection(DcMotor.Direction.REVERSE);
//        servoy.setPosition(0);
//
//        // Wait for the game to start (driver presses START)
//        telemetry.addData("Status", "Initialized");
//        telemetry.update();
//
//        waitForStart();
//        runtime.reset();
//
//        while (opModeIsActive()) {
//            double max;
//
//            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
//            double axial   = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
//            double lateral =  gamepad1.left_stick_x;
//            double yaw     =  gamepad1.right_stick_x;
//
//            servox.setPower(gamepad2.left_stick_x);
//            servoy.setPosition(gamepad2.left_stick_y);
//
//
//
//
//            // Combine the joystick requests for each axis-motion to determine each wheel's power.
//            // Set up a variable for each drive wheel to save the power level for telemetry.
//            double frontLeftPower  = axial + lateral + yaw;
//            double frontRightPower = axial - lateral - yaw;
//            double backLeftPower   = axial - lateral + yaw;
//            double backRightPower  = axial + lateral - yaw;
//
//            // Normalize the values so no wheel power exceeds 100%
//            // This ensures that the robot maintains the desired motion.
//            max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
//            max = Math.max(max, Math.abs(backLeftPower));
//            max = Math.max(max, Math.abs(backRightPower));
//
//            if (max > 1.0) {
//                frontLeftPower  /= max;
//                frontRightPower /= max;
//                backLeftPower   /= max;
//                backRightPower  /= max;
//            }
//
//            // Send calculated power to wheels
//            frontLeftDrive.setPower(frontLeftPower);
//            frontRightDrive.setPower(frontRightPower);
//            backLeftDrive.setPower(backLeftPower);
//            backRightDrive.setPower(backRightPower);
//
//            // Show the elapsed game time and wheel power.
//            telemetry.addData("Status", "Run Time: " + runtime.toString());
//            telemetry.addData("Front left/Right", "%4.2f, %4.2f", frontLeftPower, frontRightPower);
//            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", backLeftPower, backRightPower);
//            telemetry.update();
//        }
//    }}

import android.graphics.Canvas;
import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.opencv.core.Mat;


@TeleOp
public class testgimbal extends OpMode {

    Gimbal gimbal = new Gimbal();

    AprilTagDetection detection;

    AprilTagProcessor aPro = new AprilTagProcessor.Builder()
//            .setLensIntrinsics()
            .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
//            .setCameraPose(new Position(DistanceUnit.MM, ));
            .build();

    VisionPortal vp = new VisionPortal.Builder()
            .setStreamFormat(VisionPortal.StreamFormat.YUY2)
            .setCameraResolution(new Size(640,480))
            .addProcessor(aPro)
            .build();

    @Override
    public void init() {

    }

    @Override
    public void loop() {
        gimbal.trackTag(detection.ftcPose.bearing, detection.ftcPose.elevation);
    }
}
