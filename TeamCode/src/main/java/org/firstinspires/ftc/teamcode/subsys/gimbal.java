package org.firstinspires.ftc.teamcode.subsys;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.CRServo;

public class Gimbal {
    CRServo YServo = hardwareMap.get(CRServo.class, "Servo1");
    CRServo ZServo = hardwareMap.get(CRServo.class, "Servo2");

    static public final float map(double value,
                                  double istart,
                                  double istop,
                                  double ostart,
                                  double ostop) {
        return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
    }

    public void trackTag(double bearing, double elevation){
        boolean negativeBearing = (bearing < 0);
        boolean negativeElevation = (elevation < 0);

        YServo.setPower(map(bearing, 0, 360.0, 0, 1.0));
        ZServo.setPower(map(elevation, 0, 360.0, 0, 1.0));

        // add failsafes if... fail duh. maybe
    }
}
