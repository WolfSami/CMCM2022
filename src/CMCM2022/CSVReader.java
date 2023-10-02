package CMCM2022;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalTime;
    public class CSVReader
    {

        /**
         * Incidents have an address, town, time, and type
         */
        private Incident[] incidents = new Incident[2500];
        void readIncidents(String file) throws IOException {
            String fileName = file;
            Reader r = null;
            try {
                r = new FileReader(fileName);
            } catch (FileNotFoundException fnfe){
                System.out.println("File name not found");
            }
            BufferedReader br = new BufferedReader(r);
            int i = 0;
            while(br.ready()){
                String incidentString = br.readLine();
                if (incidentString == null) break;
                String[] details = incidentString.split(",");
                if (details.length >= 5) {
                    incidents[i] = new Incident(details[0], details[1], details[2],
                            details[4]);
                }
                i++;
            }
        }

        /**
         * Returns a hashmap that matches street names to their highest recorded AADT.
         */
        static HashMap<String,Integer> readVolumes(String file) throws IOException {
            String fileName = file;
            Reader r = null;
            try {
                r = new FileReader(fileName);
            } catch (FileNotFoundException fnfe){
                System.out.println("File name not found");
            }
            BufferedReader br = new BufferedReader(r);
            HashMap<String,Integer> map = new HashMap<>();
            while(br.ready()){
                String incidentString = br.readLine();
                String[] details = incidentString.split(",");
                int aadt = Integer.parseInt(details[1]);
                if (details.length > 2) {
                    aadt = Integer.parseInt(details[1]) * 1000 + Integer.parseInt(details[2]);
                }
                String roadName = details[0].substring(0,details[0].length() - 2);
                if (!map.containsKey(roadName) || map.get(roadName) < aadt)
                    map.put(roadName,aadt);
            }
            return map;
        }

        /**
         * Returns a hashmap that links each road to the number of accidents on it.
         */
        static HashMap<String,Integer> readAccidents(String file) throws IOException {
            String fileName = file;
            Reader r = null;
            try {
                r = new FileReader(fileName);
            } catch (FileNotFoundException fnfe){
                System.out.println("File name not found");
            }
            BufferedReader br = new BufferedReader(r);
            HashMap<String,Integer> map = new HashMap<>();
            while(br.ready()) {
                String incidentString = br.readLine();
                if (incidentString.contains("STREET"))
                    incidentString = incidentString.substring(0,incidentString.length() - 6)
                            + "ST";
                else if(incidentString.contains("ROAD"))
                    incidentString = incidentString.substring(0,incidentString.length() - 4)
                            + "RD";
                while (incidentString.charAt(0) < 60)
                    incidentString = incidentString.substring(1);
                if (!map.containsKey(incidentString))
                    map.put(incidentString,0);
                else
                    map.put(incidentString,map.get(incidentString) + 1);
            }
            return map;
        }

        public Incident[] getIncidents() {
            return incidents;
        }
        static HashMap<String,ArrayList<LocalTime>> readAccidentTimes(String accidentFile,String timeFile) throws IOException {
            Reader acciRdr = new FileReader(accidentFile);
            Reader timeRdr = new FileReader(timeFile);
            BufferedReader ar = new BufferedReader(acciRdr);
            BufferedReader tr = new BufferedReader(timeRdr);
            HashMap<String,ArrayList<LocalTime>> map = new HashMap<>();
            while (ar.ready() && tr.ready()) {
                String incidentString = ar.readLine();
                String time = tr.readLine();
                //Convert string to localtime
                int hr = Integer.parseInt(time.substring(0,2));
                int min = Integer.parseInt(time.substring(3,5));
                LocalTime ltime = LocalTime.of(hr,min);
                if (incidentString.contains("STREET"))
                    incidentString = incidentString.substring(0,incidentString.length() - 6)
                            + "ST";
                else if(incidentString.contains("ROAD"))
                    incidentString = incidentString.substring(0,incidentString.length() - 4)
                            + "RD";
                while (incidentString.charAt(0) < 60)
                    incidentString = incidentString.substring(1);
                if (!map.containsKey(incidentString)) {
                    ArrayList<LocalTime> list = new ArrayList<>();
                    list.add(ltime);
                    map.put(incidentString,list);
                }
                else {
                    ArrayList<LocalTime> list = map.get(incidentString);
                    list.add(ltime);
                    map.put(incidentString,list);
                }
            }
            return map;
        }
        static HashMap<String,Integer> readIncidentsToMap(String file) throws IOException {
            String fileName = file;
            Reader r = null;
            try {
                r = new FileReader(fileName);
            } catch (FileNotFoundException fnfe){
                System.out.println("File name not found");
            }
            BufferedReader br = new BufferedReader(r);
            HashMap<String,Integer> map = new HashMap<>();
            while(br.ready()) {
                String incidentString = br.readLine();
                String[] details = incidentString.split(",");
                String road = details[0].substring(1);
                if (!map.containsKey(road))
                    map.put(road,0);
                else
                    map.put(road,map.get(road) + 1);
            }
            return map;
        }

        public ArrayList<Incident> getTrafficIncidents() {
            ArrayList<Incident> traffic = new ArrayList<>();
            for (Incident i : incidents) {
                if (i == null) break;
                if (i.type.toLowerCase().indexOf("traffic") != -1)
                    traffic.add(i);
            }
            return traffic;
        }
    }
