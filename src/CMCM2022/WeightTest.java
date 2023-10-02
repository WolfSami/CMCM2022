package CMCM2022;

import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
public class WeightTest {
    // Return averages of aadt, incident, and accident data
    public static void main(String[] args) {
        // Find volume avg
        try {
        HashMap<String,Integer> volMap = CSVReader.readVolumes("VolumeCSV");
        System.out.println("Volume average: " + findHashAvg(volMap));
        HashMap<String,Integer> accMap = CSVReader.readAccidents("AccidentList");
        System.out.println("Accident average: " + findHashAvg(accMap));
        HashMap<String,Integer> incMapOcto = CSVReader.readIncidentsToMap("OctoberCSV");
        HashMap<String,Integer> incMapSept = CSVReader.readIncidentsToMap("SeptemberCSV");
        System.out.println("Incident Average: " + (findHashAvg(incMapOcto) + findHashAvg(incMapSept)) / 2.0);
        System.out.println("Cobb St Goodness: " + Goodness.calculate("Cobb St",volMap,incMapOcto,accMap));
        }
        catch (IOException e) {
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
