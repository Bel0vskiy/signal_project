package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.data_management.Reader;

import java.util.ArrayList;
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
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert}
     * method. This method should define the specific conditions under which an
     * alert
     * will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     *
     *                this method will pass the patient's data through the methods
     */
    public void evaluateData(Patient patient) {
        // Implementation goes here
        List<PatientRecord> records = patient.getRecords(0, Long.MAX_VALUE);

        bloodPressureMonitor(records, "systolic",patient.getPatientId());
        bloodPressureMonitor(records, "diastolic",patient.getPatientId());
        checkCriticalThresholdAlert(records, "systolic", patient.getPatientId());
        checkCriticalThresholdAlert(records, "diastolic", patient.getPatientId());
        bloodSaturationMonitor(records, patient.getPatientId());
        combinedAlertMonitor(records, patient.getPatientId());
        ECGMonitor(records, patient.getPatientId());
        manualAlert(records, patient.getPatientId());
        }

    /**
     * @param records list of patient records
     * @param type the type will be displayed on the alert
     * @param patientId the patient ID of the patient whose records are being fed into the monitor
     * The method will create two different records at diff1 and diff2, they will be subtracted to
     *                  probe for an increasing or decreasing blood pressure trend
     */
    public boolean bloodPressureMonitor(List<PatientRecord> records, String type, int patientId) {
        if (records.size() < 3) {
            return false;
        }
        for (int i = 2; i < records.size(); i++) {
            double difference = records.get(i).getMeasurementValue() - records.get(i-1).getMeasurementValue();
            System.out.println(difference);
            double difference2 = records.get(i-1).getMeasurementValue() - records.get(i-2).getMeasurementValue();
            System.out.println(difference2);

            if (difference > 10 && difference2 > 10) {
                triggerAlert(new Alert(String.valueOf(patientId), type + " Increasing Trend Alert", records.get(i).getTimestamp()));
                return true;
            } else if (difference < -10 && difference2 < -10) {
                triggerAlert(new Alert(String.valueOf(patientId), type + " Decreasing Trend Alert", records.get(i).getTimestamp()));
                return true;
            }
        }
        return false;
    }
    /**
     * @param records list of patient records
     * @param type the type will be displayed on the alert
     * @param patientId the patient ID of the patient whose records are being fed into the monitor
     * The method will check if the systolic and diastolic pressure meet dangerous threshold conditions
     */

    public boolean checkCriticalThresholdAlert(List<PatientRecord> records, String type, int patientId) {
        for (PatientRecord record : records) {
            double value = record.getMeasurementValue();
            if ("SystolicPressure".equals(type)) {
                if (value > 180 || value < 90) {
                    triggerAlert(new Alert(String.valueOf(patientId), type + " Critical Threshold Alert", record.getTimestamp()));
                    return true;
                }
            } else if ("DiastolicPressure".equals(type)) {
                if (value > 120 || value < 60) {
                    triggerAlert(new Alert(String.valueOf(patientId), type + " Critical Threshold Alert", record.getTimestamp()));
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * @param records list of patient records
     * @param patientId the patient ID of the patient whose records are being fed into the monitor
     * The method will check if the blood saturation is low or rapidly dropping which will trigger an alert
     */

    public boolean bloodSaturationMonitor(List<PatientRecord> records, int patientId) {
        List<PatientRecord> saturationRecords = new ArrayList<>();

        for (PatientRecord record : records) {
            if ("Saturation".equals(record.getRecordType())) {
                saturationRecords.add(record);
            }
        }

        for (int i = 0; i < saturationRecords.size(); i++) {
            double value = saturationRecords.get(i).getMeasurementValue();
            if (value < 92) {
                triggerAlert(new Alert(String.valueOf(patientId), "Low Saturation Alert", saturationRecords.get(i).getTimestamp()));
                return true;
            }

            if (i > 0) {
                double prevValue = saturationRecords.get(i-1).getMeasurementValue();
                if (prevValue - value >= 5 && (saturationRecords.get(i).getTimestamp() - saturationRecords.get(i-1).getTimestamp() <= 600000)) {
                    triggerAlert(new Alert(String.valueOf(patientId), "Rapid Drop Alert", saturationRecords.get(i).getTimestamp()));
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param records list of patient records
     * @param patientId the patient ID of the patient whose records are being fed into the monitor
     * This method monitors the systolic pressure and the blood oxygen saturation
     *                  if both meet a threshold a combined alert is triggered
     */


    public boolean combinedAlertMonitor(List<PatientRecord> records, int patientId) {
        boolean lowSystolic = false;
        boolean lowSaturation = false;

        for (PatientRecord record : records) {
            if ("SystolicPressure".equals(record.getRecordType()) && record.getMeasurementValue() < 90) {
                lowSystolic = true;
            } else if ("Saturation".equals(record.getRecordType()) && record.getMeasurementValue() < 92) {
                lowSaturation = true;
            }

            if (lowSystolic && lowSaturation) {
                triggerAlert(new Alert(String.valueOf(patientId), "Hypotensive Hypoxemia Alert", record.getTimestamp()));
                return true;
            }
        }
        return false;
    }
    /**
     * @param records list of patient records
     * @param patientId the patient ID of the patient whose records are being fed into the monitor
     * The method will monitor the patient's ECG readings, it will trigger an alert if the readings meet a certain condition
     */

    public boolean ECGMonitor(List<PatientRecord> records, int patientId) {
        List<PatientRecord> ecgRecords = new ArrayList<>();

        for (PatientRecord record : records) {
            if ("ECG".equals(record.getRecordType())) {
                ecgRecords.add(record);
            }
        }

        if (ecgRecords.size() < 5) {
            return false;
        }

        double sum = 0;
        for (int i = 0; i < 5; i++) {
            sum += ecgRecords.get(i).getMeasurementValue();
        }
        double avg = sum / 5;

        for (int i = 5; i < ecgRecords.size(); i++) {
            double value = ecgRecords.get(i).getMeasurementValue();
            if (value > avg * 1.5) {
                triggerAlert(new Alert(String.valueOf(patientId), "ECG Abnormal Data Alert", ecgRecords.get(i).getTimestamp()));
                return true;
            }

            sum -= ecgRecords.get(i-5).getMeasurementValue();
            sum += value;
            avg = sum / 5;
        }
        return false;
    }

    /**
     * @param records list of patient records
     * @param patientId the patient ID of the patient whose records are being fed into the monitor
     * The method will check if hospital staff triggered an alert manually
     */

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

    public static void main(String[] args) {
        Reader reader = new Reader("test.txt");
        DataStorage storage = new DataStorage();

        AlertGenerator generator = new AlertGenerator(storage);

        storage.addPatientData(100, 59.0, "DiastolicPressure", 1714376789050L);
        storage.addPatientData(100, 71.0, "DiastolicPressure", 1714376789051L);
        storage.addPatientData(100, 83.0, "DiastolicPressure", 1714376789052L);

        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
        boolean result = generator.bloodPressureMonitor(recordsTest, "diastolic", 100);
        System.out.println(result);
    }
}
