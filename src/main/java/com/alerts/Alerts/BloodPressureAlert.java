package com.alerts.Alerts;

public class BloodPressureAlert extends Alert {
    public BloodPressureAlert(String patientId, String condition, Long timestamp){
        super(patientId, condition, timestamp);
    }
}
