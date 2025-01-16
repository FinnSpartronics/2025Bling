package frc.robot.subsystems.Bling;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BlingSubsystem extends SubsystemBase {

    private short[][] show;
    private AddressableLED strip;
    private AddressableLEDBuffer buffer;
    private int frame = 0;
    private int ledLength;

    public BlingSubsystem(int port) {
        try {
            show = loadFromDeploy("export.bling");
        } catch(Exception e) {
            e.printStackTrace();
        }

        strip = new AddressableLED(port); 
        ledLength = show[0].length / 3;
        System.out.println(ledLength);
        strip.setLength(ledLength);
        strip.start();
        buffer = new AddressableLEDBuffer(ledLength);
    }

    @Override
    public void periodic() {
        if (frame % 2 == 0) {
            for (int i = 0; i < ledLength; i++)
                buffer.setRGB(i, show[frame][i * 3], show[frame][i * 3 + 1], show[frame][i * 3 + 2]);
            strip.setData(buffer);
        }
        frame++;
        if (frame > show.length - 1) frame = 0;
    }

    public short[][] loadFromDeploy(String filename) throws FileNotFoundException, IOException {
        String path = Filesystem.getDeployDirectory().getPath() + "/" + filename;
        System.out.println("Attempting to load " + path);
        BufferedReader reader = new BufferedReader(new FileReader(path));

        String line = reader.readLine();

        ArrayList<ArrayList<Short>> arr = new ArrayList<ArrayList<Short>>();
        
        for (String x : line.split(" ")) {
            ArrayList<Short> tmp = new ArrayList<Short>();
            for (String num : x.split(",")) {
                tmp.add(Short.parseShort(num));
            }
            arr.add(tmp);
        }

        short[][] array = new short[arr.size()][arr.get(0).size()];
        for (int x = 0; x < arr.size(); x++) {
            for (int y = 0; y < arr.get(x).size(); y++) {
                array[x][y] = arr.get(x).get(y);
            }
        }

        reader.close();
        return array;
    }
    
}
