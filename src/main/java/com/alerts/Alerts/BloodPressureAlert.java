package com.alerts.Alerts;

import com.alerts.Alert;

public class BloodPressureAlert extends Alert {
    public BloodPressureAlert(String patientId, String condition, Long timestamp){
        super(patientId, condition, timestamp);
    }
}
