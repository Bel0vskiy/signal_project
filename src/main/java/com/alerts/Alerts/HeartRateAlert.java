package com.alerts.Alerts;

import com.alerts.Alert;

public class HeartRateAlert extends Alert {
    public HeartRateAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }
}

