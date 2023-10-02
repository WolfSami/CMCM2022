package CMCM2022;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalTime;

public class Goodness {
    /**
     * Calculates the goodness of a road, or -1 if road not found.
     * road: Name of a road
     */
    public static final double volumeWeight = 1;
    public static final double incidentWeight = 0.75;
    public static final double accidentWeight = 4;
    public static final double volumeAvg = 2402.714;
    public static final double incidentAvg = 2.21064;
    public static final double accidentAvg = 2.53368;

    public static double calculate(String road, HashMap<String, Integer> aadtMap,
                                   HashMap<String, Integer> incMap, HashMap<String, Integer> accMap) {
        int aadt = 0;
        int incis = 0;
        int accis = 0;
        if (!(aadtMap.get(road) == null))
            aadt = aadtMap.get(road);
        if (!(incMap.get(road.toUpperCase()) == null))
            incis = incMap.get(road.toUpperCase());
        if (!(accMap.get(road.toUpperCase()) == null))
            accis = accMap.get(road.toUpperCase());
        return Math.pow(((incidentAvg / volumeAvg) * (volumeWeight) * aadt + (incidentWeight) * incis
                + (accidentAvg / incidentAvg) * (accidentWeight) * accis),2);
    }

    public static double calculate(String road, HashMap<String, Integer> aadtMap, HashMap<String, Integer> incMap,
                                   HashMap<String, Integer> accMap, HashMap<String, ArrayList<LocalTime>> acciTimeMap, LocalTime time) {
            int aadt = 0;
            int incis = 0;
            int accis = 0;
            if (!(aadtMap.get(road) == null))
                aadt = aadtMap.get(road);
            if (!(incMap.get(road.toUpperCase()) == null))
                incis = incMap.get(road.toUpperCase());
            if (!(accMap.get(road.toUpperCase()) == null))
                accis = accMap.get(road.toUpperCase());
            double goodness = (incidentAvg / volumeAvg) * (volumeWeight) * aadt + (incidentWeight) * incis
                    + (accidentAvg / incidentAvg) * (accidentWeight) * accis;
            if (acciTimeMap.get(road) == null) return Math.pow(goodness,2);
            for (LocalTime ltime : acciTimeMap.get(road)) {
                if (time.isAfter(ltime)) {
                    if (time.minusHours(1).isBefore(ltime))
                        goodness *= 1.25;
                } else if (time.plusHours(1).isAfter(ltime))
                    goodness *= 1.25;
            }
            return Math.pow(goodness,2);
    }

    //public static void
}

