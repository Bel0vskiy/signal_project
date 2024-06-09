package com.alerts.Factories;

import com.alerts.Alerts.HeartRateAlert;

public class HeartRateAlertFactory extends AlertFactory {
    @Override
    public HeartRateAlert createAlert(String patientId, String condition, long timestamp) {
        return new HeartRateAlert(patientId, condition, timestamp);
    }
}

