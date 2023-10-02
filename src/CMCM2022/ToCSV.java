package CMCM2022;

import java.io.*;
public class ToCSV {
    //static String txtFileName = "SheriffsOfficeLogsOctober.txt";
//    public static void main(String[] args) {
//        try {
//            String fileName = args[0];
//            File txtFile = new File(fileName);
//
//        }
//        catch (ArrayIndexOutOfBoundsException e) {
//            System.err.println("Array index out of bounds. " + e);
//        }
//    }

    public ToCSV() {

    }

    /**
     * Returns a csv-format text file given a sheriff's officer log text file.
     * Format: {}
     */
    public File toCSV(File txtFile, String csvFileName) throws IOException {
        File file = new File(csvFileName);
        file.createNewFile();
        FileWriter writer = new FileWriter(csvFileName);
        Reader reader = new FileReader(txtFile);
        BufferedReader rdr = new BufferedReader(reader);
        String line = rdr.readLine();
        //while (line != null) {
        while(line != null) {
            if (line.length() > 15 && line.substring(0,16).equals("Incident Address")) {
                int semiColonIndex = line.indexOf(';');
                if (line.indexOf("&") != -1 && line.indexOf("&") < semiColonIndex) semiColonIndex = line.indexOf("&") - 1;
                writer.write(line.substring(18, semiColonIndex) + ","); //add address
                line = rdr.readLine();
                if (line.length() <= 3 || !line.substring(0, 6).equals("Public")) {
                    int spaceIndex = line.indexOf(" ");
                    if (spaceIndex < 0) writer.write(line.substring(0)); //add town if = ckf
                    else writer.write(line.substring(0, spaceIndex) + ","); //add town
                } else {
                    line = rdr.readLine();
                    line = rdr.readLine();
                    line = rdr.readLine();
                    int spaceIndex = line.indexOf(" ");
                    writer.write(line.substring(0, spaceIndex) + ",");
                }
                line = rdr.readLine(); //skip a line (reported time)

                line = rdr.readLine();
                if (!line.substring(0,6).equals("Public")) {
                    int dashIndex = line.indexOf("-");
                    writer.write(timetoCSV(line) + ","); //add time
                }
                else {
                    line = rdr.readLine();
                    line = rdr.readLine();
                    line = rdr.readLine();
                    int dashIndex = line.indexOf("-");
                    writer.write(timetoCSV(line) + ",");
                }
                line = rdr.readLine();
                if (line.length() <= 5 || !line.substring(0,6).equals("Public")) {
                    writer.write(line); //add primary cause
                }
                else {
                    line = rdr.readLine();
                    line = rdr.readLine();
                    line = rdr.readLine();
                    writer.write(line);
                }
                line = rdr.readLine();
                if (line == null) break;
//                if (line.length() <= 5 || !(line.substring(0,6).equals("Public") || (line.length() > 15) && line.substring(0,16).equals("Incident Address")))
//                    writer.write(line);
                writer.write('\n');
                line = rdr.readLine();
            }
            else
                line = rdr.readLine();
        }
        writer.close();
        reader.close();
        rdr.close();
        return file;
    }

    public String timetoCSV(String timeLine) {
        String time = timeLine.substring(24);
        String[] arr = time.split(" ");
        String str = "";
        str = str + arr[0];
        str = str + ",";
        String date = arr[1];
        str += date.substring(0,2) + ":" + date.substring(3,5) + ":" + date.substring(6,8);
        return str;
    }

    public File aadtToCSV(File txtFile, String fileName) throws IOException {
        File file = new File(fileName);
        file.createNewFile();
        FileWriter writer = new FileWriter(fileName);
        Reader reader = new FileReader(txtFile);
        BufferedReader rdr = new BufferedReader(reader);
        String line = rdr.readLine();
        while (line != null) {
            if (line.substring(0,3).equals("Ith")) {
                for (int i = 0; i < 4; i++) {
                    if (line == null) break;
                    line = rdr.readLine();
                }
            }
            else {
                String str = line.substring(5);
                int spaceIndex = str.indexOf(" ");
                int slashIndex = str.indexOf("/");
                int dashIndex = str.indexOf("-");
                if (dashIndex != -1 && dashIndex < slashIndex) slashIndex = dashIndex + 1;
                writer.write(str.substring(spaceIndex + 1,slashIndex) + ","); //Write st
                int stateIndex = findStateIndex(str);
                int secondSpaceAfterStateIndex = str.indexOf(" ",stateIndex + 6);
                writer.write(str.substring(stateIndex + 6,secondSpaceAfterStateIndex)); //Write aadt
                writer.write('\n'); //Write newline
            }
            line = rdr.readLine();
        }
        writer.close();
        reader.close();
        rdr.close();
        return file;
    }

    private int findStateIndex(String str) {
        int index = str.indexOf("State");
        while (str.substring(index,index + 8).equalsIgnoreCase("State St")) {
            index = str.indexOf("State", index + 1);
        }
        return index;
    }
}
