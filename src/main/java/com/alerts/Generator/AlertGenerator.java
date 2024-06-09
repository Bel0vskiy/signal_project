package com.alerts.Generator;

import com.alerts.Alerts.Alert;
import com.alerts.Factories.*;
import com.alerts.Strategies.*;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.data_management.Reader;

import java.util.List;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the corresponding
     * strategy.
     *
     * @param patient the patient data to evaluate for alert condition
     */
    public void evaluateData(Patient patient) {
        List<PatientRecord> records = patient.getRecords(0, Long.MAX_VALUE);
        //initialize the alerts
        AlertStrategy bloodPressureStrategy = new BloodPressureStrategy(new BloodPressureAlertFactory());
        AlertStrategy oxygenSaturationStrategy = new OxygenSaturationStrategy(new BloodOxygenLevelAlertFactory());
        AlertStrategy combinedAlertStrategy = new CombinedAlertStrategy();
        AlertStrategy heartRateStrategy = new HeartRateStrategy(new HeartRateAlertFactory());
        AlertStrategy trend = new TrendAlertStrategy();
        //run the data through the alerts
        trend.checkAlert(records, patient.getPatientId());
        bloodPressureStrategy.checkAlert(records, patient.getPatientId());
        oxygenSaturationStrategy.checkAlert(records, patient.getPatientId());
        combinedAlertStrategy.checkAlert(records, patient.getPatientId());
        heartRateStrategy.checkAlert(records, patient.getPatientId());
        manualAlert(records, patient.getPatientId());
        }

    public static void main(String[] args) {
        Reader reader = new Reader("test.txt");
        DataStorage storage = DataStorage.getInstance();

        AlertGenerator generator = new AlertGenerator(storage);

        storage.addPatientData(100, 130.0, "DiastolicPressure", 1714376789050L);
        storage.addPatientData(100, 110.0, "DiastolicPressure", 1714376789051L);
        storage.addPatientData(100, 50.0, "DiastolicPressure", 1714376789052L);



        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
        boolean e = new TrendAlertStrategy().checkAlert(recordsTest, 2929);
        boolean f = new BloodPressureStrategy(new BloodPressureAlertFactory()).checkAlert(recordsTest, 214);
        System.out.println(e);
    }

    public boolean manualAlert(List<PatientRecord> records, int patientId) {
        for (PatientRecord record : records) {
            if ("Alert".equals(record.getRecordType()) && record.getMeasurementValue() == 1) {
                triggerAlert(new Alert(String.valueOf(patientId), "Manual Triggered Alert", record.getTimestamp()));
                return true;
            }
        }
        return false;
    }

    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    private void triggerAlert(Alert alert) {
        // Implementation might involve logging the alert or notifying staff
        System.out.println("Alert triggered: " + alert.getCondition() + " for patient " + alert.getPatientId() + " at " + alert.getTimestamp());
    }


}
