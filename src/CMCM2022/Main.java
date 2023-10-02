package CMCM2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;
import java.time.LocalTime;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Here are the top three roads to visit: ");
        try {
            TreeMap<Double,String> map = getGoodnessMap();
            for (int i = 0; i < 3; i++) {
                System.out.print(map.pollLastEntry() + ", ");
            }
            map = getGoodnessMap();
            System.out.println("Here is a randomly chosen road: ");
            while (!map.isEmpty()) {
                if (Math.random() * 100 < 7.5) {
                    System.out.println(map.pollLastEntry());
                    break;
                }
                else
                    map.pollLastEntry();
            }
            System.out.println("Please enter a time in format hr:mi");
            String time = sc.next();
            LocalTime ltime = LocalTime.of(Integer.parseInt(time.substring(0,2)),Integer.parseInt(time.substring(3,5)));
            map = getGoodnessMap(ltime);
            map = getGoodnessMap(ltime);
            System.out.println("Here is a randomly chosen road for the time " + time);
            while (!map.isEmpty()) {
                if (Math.random() * 100 < 7.5) {
                    System.out.println(map.pollLastEntry());
                    break;
                }
                else
                    map.pollLastEntry();
            }
        }
        catch (IOException e) {
            System.err.println("File not found. " + e);
        }
    }

    private static TreeMap<Double,String> getGoodnessMap() throws IOException {
        HashMap<String,Integer> volMap = CSVReader.readVolumes("VolumeCSV");
        HashMap<String,Integer> inciMap = CSVReader.readIncidentsToMap("OctoAndSeptIncisCSV");
        HashMap<String,Integer> acciMap = CSVReader.readAccidents("AccidentList");
        TreeMap<Double,String> goodMap = new TreeMap<>();
        for (String road : volMap.keySet()) {
            goodMap.put(Goodness.calculate(road,volMap,inciMap,acciMap),road);
        }
        return goodMap;
    }
    private static TreeMap<Double,String> getGoodnessMap(LocalTime time) throws IOException {
        HashMap<String,Integer> volMap = CSVReader.readVolumes("VolumeCSV");
        HashMap<String,Integer> inciMap = CSVReader.readIncidentsToMap("OctoAndSeptIncisCSV");
        HashMap<String,Integer> acciMap = CSVReader.readAccidents("AccidentList");
        HashMap<String,ArrayList<LocalTime>> timeMap = CSVReader.readAccidentTimes("AccidentList","AccidentTimeList");
        TreeMap<Double,String> goodMap = new TreeMap<>();
        for (String road : volMap.keySet()) {
            goodMap.put(Goodness.calculate(road,volMap,inciMap,acciMap,timeMap,time),road);
        }
        return goodMap;
    }

    private static HashMap<String,Integer> getFairnessMap() throws IOException {
        HashMap<String,String> roadToTown = new HashMap<>();
        FileReader reader = new FileReader("TompkinsTrafficVolume.txt");
        BufferedReader rdr = new BufferedReader(reader);
        while (rdr.ready()) {
            String line = rdr.readLine();
            int secondSpaceIndex = line.indexOf(" ");
            secondSpaceIndex = line.substring(secondSpaceIndex + 1).indexOf(" ");
            int slashIndex = line.indexOf("/");
            roadToTown.put(line.substring(secondSpaceIndex + 1, slashIndex - 2),line.substring(slashIndex+1,line.substring(slashIndex).indexOf(" ")));
        }
        System.out.println(roadToTown);
        throw new IOException();
    }

}
