package com.data_management;

import java.io.IOException;

public interface DataReader {
    /**
     * Reads data from a specified source and stores it in the data storage.
     * 
     * @param dataStorage the storage where data will be stored
     * @throws IOException if there is an error reading the data
     */
    void readData(DataStorage dataStorage) throws IOException;

    public static  PatientRecord lineToRecord(String line){
        String[] parts = line.split(",");

        int patientId = Integer.parseInt(parts[0].split(":")[1].trim());
        long timeStamp = Long.parseLong(parts[1].split(":")[1].trim());
        String label = parts[2].split(":")[1].trim();
        String measurementValueString = "";
        double mesurementValue= 999; // default value which would indicate Error Value

        switch (label){

            default:
                System.out.println("Label not found  -  "+label);
                break;
            case "Alert":
                // records alert triggered as 1 and alert resolved as 0;
                measurementValueString = parts[3].split(":")[1].trim();
                if (measurementValueString.equals("triggered")){mesurementValue = 1;}
                if (measurementValueString.equals("resolved")){mesurementValue = 0;}
                break;
            case "Cholesterol":
                mesurementValue= Double.parseDouble(parts[1].split(":")[1].trim());
                break;
            case "DiastolicPressure":
                mesurementValue= Double.parseDouble(parts[1].split(":")[1].trim());
                break;
            case "ECG":
                mesurementValue= Double.parseDouble(parts[1].split(":")[1].trim());
                break;
            case "RedBloodCells":
                mesurementValue= Double.parseDouble(parts[1].split(":")[1].trim());
                break;
            case "Saturation":
                measurementValueString = parts[3].split(":")[1].split("%")[0].trim();
                mesurementValue = Double.parseDouble(measurementValueString);
                break;
            case "SystolicPressure":
                mesurementValue= Double.parseDouble(parts[1].split(":")[1].trim());
                break;
            case "WhiteBloodCells":
                mesurementValue= Double.parseDouble(parts[1].split(":")[1].trim());
                break;

        }
        PatientRecord record = new PatientRecord(patientId, mesurementValue, label, timeStamp, 1714376789050L);
        return record;
    }
}
