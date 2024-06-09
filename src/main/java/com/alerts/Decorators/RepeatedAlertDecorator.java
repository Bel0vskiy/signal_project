package com.alerts.Decorators;

import com.alerts.Alerts.Alert;

public class RepeatedAlertDecorator extends AlertDecorator {
    private int repeatNumber;
    public RepeatedAlertDecorator(String patientId, String condition, long timestamp, int repeatNumber) {
        super(patientId, condition, timestamp);
        this.repeatNumber = repeatNumber;
    }

    //if an alert is determined to require a repeated execution, this method will repeat this alert a given number of times

    @Override
    public void addFunction(String patientId, String condition, long timestamp) {
        for(int i = 0; i < repeatNumber; i++){
            new Alert(patientId, condition, timestamp);
            i--;
        }
    }
}
