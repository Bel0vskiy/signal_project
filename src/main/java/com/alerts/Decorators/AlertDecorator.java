package com.alerts.Decorators;

import com.alerts.Alerts.Alert;

public abstract class AlertDecorator extends Alert  {
    public AlertDecorator(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }
    abstract public void addFunction(String patientId, String condition, long timestamp);

}
