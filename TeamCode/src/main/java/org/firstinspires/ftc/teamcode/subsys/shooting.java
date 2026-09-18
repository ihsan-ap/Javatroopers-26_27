package org.firstinspires.ftc.teamcode.subsys;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class shooting {
    DcMotorEx feeder = hardwareMap.get(DcMotorEx.class, "feeder");
    DcMotorEx shooter = hardwareMap.get(DcMotorEx.class, "shoot");
}
