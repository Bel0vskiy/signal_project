package com.alerts;

public abstract class AlertFactory {
    public abstract Alert createAlert(String patientID, String condition, long timestamp);
}
