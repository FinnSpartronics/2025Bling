package frc.robot.subsystems.Bling;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BlingSubsystem extends SubsystemBase {

    private AddressableLED strip;
    private AddressableLEDBuffer buffer;
    private int ledLength;
    private BlingSegment[] shows;

    public BlingSubsystem(int port, BlingSegment... shows) {

        int ledLength = 0;
        for (BlingSegment x : shows) {
            ledLength += x.getStripLength();
        }

        this.shows = shows;

        strip = new AddressableLED(port);
        strip.setLength(ledLength);
        strip.start();
        buffer = new AddressableLEDBuffer(ledLength);
        int index = 0;
        for (BlingSegment x : shows) {
            x.buffer = buffer.createView(index, index + x.ledLength - 1);
            index += x.ledLength;
        }
    }

    @Override
    public void periodic() {
        for (BlingSegment show : shows) {
            show.update();
        }
        strip.setData(buffer);
    }

}
