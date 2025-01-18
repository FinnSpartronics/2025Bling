package frc.robot.subsystems.Bling;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BlingSubsystem extends SubsystemBase {

    private AddressableLED strip;
    private AddressableLEDBuffer buffer;
    private BlingSegment[] shows;

    public void updateShows(BlingSegment... shows) {
        this.shows = shows;

        int ledLength = 0;
        for (BlingSegment x : shows) {
            ledLength += x.getStripLength();
        }

        strip.setLength(ledLength);
        buffer = new AddressableLEDBuffer(ledLength);

        int index = 0;
        for (BlingSegment x : shows) {
            x.buffer = buffer.createView(index, index + x.ledLength - 1);
            index += x.ledLength;
        }
    }

    public BlingSubsystem(int port, BlingSegment... shows) {
        strip = new AddressableLED(port);
        strip.start();
        updateShows(shows);
    }

    @Override
    public void periodic() {
        for (BlingSegment show : shows) {
            show.update();
        }
        strip.setData(buffer);
    }

}
