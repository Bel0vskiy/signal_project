package com.data_management;

import com.alerts.Generator.AlertGenerator;
import com.cardio_generator.HealthDataSimulator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;

/**
 * The WebSocketClient class is responsible for establishing a WebSocket connection to a specified server URI,
 * receiving messages, and handling the data storage.
 */
public class WebSocketClient {
    private WebSocket webSocket;
    private final String serverUri;
    private final CountDownLatch messageLatch = new CountDownLatch(1);
    private DataStorage dataStorage;

    /**
     * Constructs a WebSocketClient with the specified server URI.
     *
     * @param serverUri the URI of the WebSocket server
     */
    public WebSocketClient(String serverUri) {
        this.serverUri = serverUri;
    }

    /**
     * The main method to run the WebSocket client.
     * It initializes the HealthDataSimulator, connects to the WebSocket server,
     * waits for a message, and processes the data.
     *
     * @param args command line arguments
     * @throws URISyntaxException if the URI syntax is incorrect
     * @throws IOException if an I/O error occurs
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        // This part is not fully complete, as integrating is not finished.
        // There is an issue related with receiving messages, because the server only listen them
        // and does not start further processing
        String[] arg = new String[] {"--output", "websocket:8080"};
        HealthDataSimulator.main(arg);
        WebSocketClient client = new WebSocketClient("ws://localhost:8080");
        // Connect the WebSocket client
        client.connect(DataStorage.getInstance());
        client.awaitMessage();
        client.wait(1000);
        List<PatientRecord> records = DataStorage.getInstance().getRecords(1, 1609459200000L, 1609459200000L);
        for (PatientRecord record : records) {
            System.out.println("ID is:" + record.getPatientId());
            System.out.println("MeasurementValue is:" + record.getMeasurementValue());
            System.out.println("RecordType is:" + record.getRecordType());
            System.out.println("TimeStamp is:" + record.getTimestamp());
        }
        // Initialize the AlertGenerator with the storage
        AlertGenerator alertGenerator = new AlertGenerator(DataStorage.getInstance());

        // Evaluate all patients' data to check for conditions that may trigger alerts
        for (Patient patient : DataStorage.getInstance().getAllPatients()) {
            alertGenerator.evaluateData(patient);
        }
    }

    /**
     * Connects to the WebSocket server and sets up the WebSocket listener.
     *
     * @param dataStorage the DataStorage instance to be used for storing received messages
     * @throws URISyntaxException if the URI syntax is incorrect
     */
    public void connect(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        try {
            HttpClient client = HttpClient.newHttpClient();
            webSocket = client.newWebSocketBuilder()
                    .buildAsync(new URI(serverUri), new WebSocketListener())
                    .join();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Awaits a message from the WebSocket server.
     *
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    public void awaitMessage() throws InterruptedException {
        messageLatch.await();
    }


    /**
     * A WebSocket listener to handle incoming messages and WebSocket events.
     */
    private class WebSocketListener implements Listener {
        @Override
        public void onOpen(WebSocket webSocket) {
            System.out.println("Connected to WebSocket server");
            webSocket.request(1);
        }

        @Override
        public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
            String message = data.toString();
            System.out.println("Message received: " + message);
            processMessage(message);
            messageLatch.countDown();
            return Listener.super.onText(webSocket, data, last);
        }

        @Override
        public void onError(WebSocket webSocket, Throwable error) {
            error.printStackTrace();
        }

        private void processMessage(String message) {
            String[] parts = message.split(",");
            if (parts.length == 4) {
                try {
                    int patientId = Integer.parseInt(parts[0]);
                    long timeStamp = Long.parseLong(parts[1]);
                    String label = parts[2];
                    String measurementValueString = "";
                    double mesurementValue= 999; // default value which would indicate Error Value

                    switch (label){

                        default:
                            System.out.println("Label not found  -  "+label);
                            break;
                        case "Alert":
                            // records alert triggered as 1 and alert resolved as 0;
                            measurementValueString = parts[3];
                            if (measurementValueString.equals("triggered")){mesurementValue = 1;}
                            if (measurementValueString.equals("resolved")){mesurementValue = 0;}
                            break;
                        case "Cholesterol":
                            mesurementValue= Double.parseDouble(parts[1]);
                            break;
                        case "DiastolicPressure":
                            mesurementValue= Double.parseDouble(parts[1]);
                            break;
                        case "ECG":
                            mesurementValue= Double.parseDouble(parts[1]);
                            break;
                        case "RedBloodCells":
                            mesurementValue= Double.parseDouble(parts[1]);
                            break;
                        case "Saturation":
                            measurementValueString = parts[3].split("%")[0].trim();
                            mesurementValue = Double.parseDouble(measurementValueString);
                            break;
                        case "SystolicPressure":
                            mesurementValue= Double.parseDouble(parts[1]);
                            break;
                        case "WhiteBloodCells":
                            mesurementValue= Double.parseDouble(parts[1]);
                            break;

                    }

                    PatientRecord record = new PatientRecord(patientId, mesurementValue, label, timeStamp, 1714376789050L);
                    DataStorage.getInstance().AddPatientRecord(record);
                }
                catch (NumberFormatException e) {
                    System.err.println("Invalid data format: " + message);
                }
            } else {
                System.err.println("Incorrect data format: " + message);
            }
//                int patientId = Integer.parseInt(parts[0]);
//                long timeStamp = Long.parseLong(parts[1]);
//                String label = parts[2];
//                String measurementValueString = "";
//                double mesurementValue= 999; // default value which would indicate Error Value
//
//                switch (label){
//
//                    default:
//                        System.out.println("Label not found  -  "+label);
//                        break;
//                    case "Alert":
//                        // records alert triggered as 1 and alert resolved as 0;
//                        measurementValueString = parts[3];
//                        if (measurementValueString.equals("triggered")){mesurementValue = 1;}
//                        if (measurementValueString.equals("resolved")){mesurementValue = 0;}
//                        break;
//                    case "Cholesterol":
//                        mesurementValue= Double.parseDouble(parts[1]);
//                        break;
//                    case "DiastolicPressure":
//                        mesurementValue= Double.parseDouble(parts[1]);
//                        break;
//                    case "ECG":
//                        mesurementValue= Double.parseDouble(parts[1]);
//                        break;
//                    case "RedBloodCells":
//                        mesurementValue= Double.parseDouble(parts[1]);
//                        break;
//                    case "Saturation":
//                        measurementValueString = parts[3].split("%")[0].trim();
//                        mesurementValue = Double.parseDouble(measurementValueString);
//                        break;
//                    case "SystolicPressure":
//                        mesurementValue= Double.parseDouble(parts[1]);
//                        break;
//                    case "WhiteBloodCells":
//                        mesurementValue= Double.parseDouble(parts[1]);
//                        break;
//
//                }
//
//                PatientRecord record = new PatientRecord(patientId, mesurementValue, label, timeStamp, 1714376789050L);
//                DataStorage.getInstance().AddPatientRecord(record);
            }
        }




//            if (message.split(",").length ==4){
//                try {
//                    System.out.println(message);
//                    dataStorage.AddPatientRecord(DataReader.lineToRecord(message));
//                }
//                catch (NumberFormatException e) {
//                    System.err.println("Invalid data format: " + message);
//                }
//            }
//            dataStorage = DataStorage.getInstance();
//            String[] parts = message.split(",");
//            if (parts.length == 4) {
//                try {
//                    System.out.println(Arrays.toString(parts));
//                    System.out.println(parts[0]);
//                    System.out.println(parts[1]);
//                    System.out.println(parts[2]);
//                    System.out.println(parts[3]);
//                    int patientId = Integer.parseInt(parts[0]);
//                    double measurementValue = Double.parseDouble(parts[3]);
//                    String recordType = parts[2];
//                    long timestamp = Long.parseLong(parts[1]);
//                    dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
//                } catch (NumberFormatException e) {
//                    System.err.println("Invalid data format: " + message);
//                }
//            } else {
//                System.err.println("Incorrect data format: " + message);
//            }
    }