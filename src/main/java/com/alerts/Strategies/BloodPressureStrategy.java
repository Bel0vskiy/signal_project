package com.alerts.Strategies;

import com.alerts.AlertFactory;
import com.alerts.AlertStrategy;
import com.alerts.Alerts.BloodPressureAlert;
import com.data_management.PatientRecord;

import java.util.List;
public class BloodPressureStrategy implements AlertStrategy {
    private AlertFactory alertFactory;

    public BloodPressureStrategy(AlertFactory alertFactory) {
        this.alertFactory = alertFactory;
    }

    @Override
    public boolean checkAlert(List<PatientRecord> records, int patientId) {
        for (PatientRecord record : records) {
            double value = record.getMeasurementValue();
            if ("SystolicPressure".equals(record.getRecordType()) && (value > 180 || value < 90)) {
                BloodPressureAlert alert = (BloodPressureAlert) alertFactory.createAlert(String.valueOf(patientId), "Systolic Pressure Critical Threshold", record.getTimestamp());
                System.out.println("Alert created: " + alert.getCondition() + " for patient " + alert.getPatientId() + " at " + alert.getTimestamp());
                return true;
            } else if ("DiastolicPressure".equals(record.getRecordType()) && (value > 120 || value < 60)) {
                BloodPressureAlert alert = (BloodPressureAlert) alertFactory.createAlert(String.valueOf(patientId), "Diastolic Pressure Critical Threshold", record.getTimestamp());
                System.out.println("Alert created: " + alert.getCondition() + " for patient " + alert.getPatientId() + " at " + alert.getTimestamp());
                return true;
            }
        }
        return false;
    }
}
