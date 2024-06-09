package com.alerts.Factories;

import com.alerts.Alert;
import com.alerts.AlertFactory;
import com.alerts.Alerts.ECGAlert;

public class ECGAlertFactory extends AlertFactory {

    @Override
    public Alert createAlert(String patientID, String condition, long timestamp) {
        return new ECGAlert(patientID, "ECG Data Alert" + " " + condition, timestamp);
    }
}
