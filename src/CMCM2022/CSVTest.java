package CMCM2022;

import java.io.*;
import java.util.HashMap;

public class CSVTest {
    public static void main(String[] args) {
        ToCSV csv = new ToCSV();
        try {
            File octo = csv.toCSV(new File("NewSheriffsOfficeLogsOctober.txt"),"OctoberCSV");
            //File octo = csv.aadtToCSV(new File("TompkinsTrafficVolume.txt"),"VolumeCSV");
            //File octo = csv.toCSV(new File("NewSheriffsOfficeLogsSeptember.txt"),"SeptemberCSV");
            FileReader rdr = new FileReader(octo);
            BufferedReader reader = new BufferedReader(rdr);
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.err.println("File not found " + e);
        }
    }
    public static double findHashAvg(HashMap<String,Integer> map) {
        int sum = 0;
        int elems = 0;
        for (String s : map.keySet()) {
            sum += map.get(s);
            elems++;
        }
        return (double)sum / elems;
    }
}
