package com.data_management;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;


public class Reader implements DataReader {
    private final String path;

    public Reader(String path) {
        if (path == null) {throw new IllegalArgumentException("Path cannot be null");}
        this.path = path;
        System.out.println(this.path);
    };

    @Override
    public void readData(DataStorage dataStorage) throws IOException {

        Path dirPath = Paths.get(this.path);

        if (!Files.isDirectory(dirPath)) {
            throw new IOException("Provided path is not a directory.");
        }

        try (Stream<Path> paths = Files.walk(dirPath)) {
            paths.filter(Files::isRegularFile).forEach(file -> {
                try (Stream<String> lines = Files.lines(file)) {
                    lines.forEach(line -> {
                        try {
                            PatientRecord record = lineToRecord(line);
                            dataStorage.addPatientData(record.getPatientId(), record.getMeasurementValue(), record.getRecordType(), record.getTimestamp());
                        } catch (Exception e) {
                            System.err.println("Failed to parse or add data to storage: " + e.getMessage());
                        }
                    });
                } catch (IOException e) {
                    System.err.println("Error reading file: " + file + " - " + e.getMessage());
                }
            });
        }
    }

    private  PatientRecord lineToRecord(String line){
            String[] parts = line.split(",");

            int patientId = Integer.parseInt(parts[0].split(":")[1].trim());
            long timeStamp = Long.parseLong(parts[1].split(":")[1].trim());
            String label = parts[2].split(":")[1].trim();
            String measurementValueString = "";
            double mesurementValue= 999;

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
            PatientRecord record = new PatientRecord(patientId, mesurementValue, label, timeStamp);
            return record;
    }

    public static void main(String[] args) throws IOException {
        Reader reader = new Reader("/Users/vd/IdeaProjects/signal_project/test.txt");
        DataStorage dataStorage = new DataStorage();
        reader.readData(dataStorage);

        Patient patient = new Patient(40);
        List<PatientRecord> recordsChol = dataStorage.getRecords(40, 0, Long.MAX_VALUE, "Cholesterol");
        for (PatientRecord record : recordsChol) {
            System.out.println(record.getPatientId());
            System.out.println(record.getRecordType());
            System.out.println(record.getTimestamp());
            System.out.println(record.getMeasurementValue());
            System.out.println("\n");
        }

    }
}
