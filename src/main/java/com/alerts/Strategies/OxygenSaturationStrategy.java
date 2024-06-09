package com.alerts.Strategies;

import com.alerts.AlertFactory;
import com.alerts.AlertStrategy;
import com.alerts.Alerts.BloodOxygenAlert;
import com.data_management.PatientRecord;

import java.util.List;

public class OxygenSaturationStrategy implements AlertStrategy {
    private AlertFactory alertFactory;

    public OxygenSaturationStrategy(AlertFactory alertFactory) {
        this.alertFactory = alertFactory;
    }

    @Override
    public boolean checkAlert(List<PatientRecord> records, int patientId) {
        for (PatientRecord record : records) {
            double value = record.getMeasurementValue();
            if ("Saturation".equals(record.getRecordType()) && value < 92) {
                BloodOxygenAlert alert = (BloodOxygenAlert) alertFactory.createAlert(String.valueOf(patientId), "Low Oxygen Saturation", record.getTimestamp());
                System.out.println("Alert created: " + alert.getCondition() + " for patient " + alert.getPatientId() + " at " + alert.getTimestamp());
                return true;
            }

        }
        return false;
    }
}
