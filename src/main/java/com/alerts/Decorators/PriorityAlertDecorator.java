package com.alerts.Decorators;

import com.alerts.Alerts.Alert;

public class PriorityAlertDecorator extends AlertDecorator {
    public PriorityAlertDecorator(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }

    //in this class, if an alert out of a class of alerts is of higher priority, for example if one patient's
    //hear rate is dropping faster than another one's, this class is used

    @Override
    public void addFunction(String patientId, String condition, long timestamp) {
        new Alert(patientId,"HIGH PRIORITY " +  condition, timestamp);
    }
}
