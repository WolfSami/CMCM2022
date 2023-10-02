package CMCM2022;

import java.time.LocalTime;
public class Incident {
    public String address;
    public String town;
    public LocalTime time;
    public String type;

    public Incident(String addy, String town, String time, String type){
        address = addy;
        this.town = town;
        this.type = type;
        String[] parts = new String[5];
        parts = time.split(":");
        LocalTime lt = LocalTime.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2])); //hours minutes seconds
    }

}
