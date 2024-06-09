package com.alerts.Factories;

import com.alerts.Alert;
import com.alerts.AlertFactory;
import com.alerts.Alerts.BloodPressureAlert;

public class BloodPressureAlertFactory extends AlertFactory {
    @Override
    public Alert createAlert(String patientID, String condition, long timestamp) {
        return new BloodPressureAlert(patientID, "Blood Pressure Alert " + condition, timestamp);
    }
}
