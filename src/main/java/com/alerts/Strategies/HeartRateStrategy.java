package com.alerts.Strategies;

import com.alerts.Factories.AlertFactory;
import com.alerts.Factories.HeartRateAlertFactory;
import com.data_management.PatientRecord;

import java.util.List;

public class HeartRateStrategy implements AlertStrategy {
    private AlertFactory alertFactory;

    public HeartRateStrategy(AlertFactory alertFactory) {
        this.alertFactory = alertFactory;
    }

    @Override
    public boolean checkAlert(List<PatientRecord> records, int patientId) {
        for (PatientRecord record : records) {
            double value = record.getMeasurementValue();
            if ("HeartRate".equals(record.getRecordType()) && (value < 60 || value > 100)) {
                new HeartRateAlertFactory().createAlert(String.valueOf(patientId), "Abnormal Heart Rate", record.getTimestamp());
                System.out.println("Heart rate alert for patient " + patientId + " at time" + record.getTimestamp());
                return true;
            }
        }
        return false;
    }


}

