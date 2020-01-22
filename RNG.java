import java.io.*;
import java.util.Random;

public class RNG {

    int minimumValue;
    int maximumValue;

    public RNG(int minimumValue, int maximumValue) {
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
    }

    public RNG() {

    }


    public int getMultiple(String line) {
        String[] muls = line.split(",");
        int rnd = genRandom(muls.length);
        return Integer.parseInt(muls[rnd-1].trim());
    }


    public int genRandom(int size) {
        Random ran = new Random();
        int range = size;
        return ran.nextInt(range) + 1;
    }


    public String[] readMultiplesFile() {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader("multiples.txt"))) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                String everything = sb.toString();
                return everything.split("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String selectMultipleLine() {
        String[] multiples = readMultiplesFile();
        int rnd = genRandom(multiples.length);
        return multiples[rnd-1];
    }


}
