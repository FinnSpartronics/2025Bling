package frc.robot.subsystems.Bling;

import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;

public class BlingSolid extends BlingLEDPattern {
    public BlingSolid(Color color, int length) {
        super(LEDPattern.solid(color), length);
    }
}
