package com.alerts.Factories;

import com.alerts.Alerts.Alert;

public abstract class AlertFactory {
    public abstract Alert createAlert(String patientID, String condition, long timestamp);
}
