package com.alerts.Strategies;

import com.alerts.Factories.ECGAlertFactory;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class ECGAlertStrategy implements AlertStrategy {

    @Override
    public boolean checkAlert(List<PatientRecord> records, int patientId) {
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
               new ECGAlertFactory().createAlert(String.valueOf(patientId), "ECG", ecgRecords.get(i).getTimestamp());
                System.out.println("ECG Alert for patient " + patientId + " at time " + ecgRecords.get(i).getTimestamp());
                return true;
            }

            sum -= ecgRecords.get(i-5).getMeasurementValue();
            sum += value;
            avg = sum / 5;
        }
        return false;
    }
}
