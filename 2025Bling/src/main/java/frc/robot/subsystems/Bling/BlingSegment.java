package frc.robot.subsystems.Bling;

import edu.wpi.first.wpilibj.AddressableLEDBufferView;

public abstract class BlingSegment {
    protected int frame = 0;
    protected int ledLength;
    protected int maxLength = -1;
    AddressableLEDBufferView buffer;

    final public int getStripLength() {
        return this.ledLength;
    }

    final private void incrementFrame() {
        frame++;
        if (maxLength > -1)
            if (frame > maxLength) frame = 0;
    }

    final public void update() {
        updateLights();
        incrementFrame();
    }
    abstract protected void updateLights();
}
