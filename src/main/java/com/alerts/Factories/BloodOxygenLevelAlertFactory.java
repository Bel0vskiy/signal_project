package com.alerts.Factories;

import com.alerts.Alert;
import com.alerts.AlertFactory;
import com.alerts.Alerts.BloodOxygenAlert;

public class BloodOxygenLevelAlertFactory extends AlertFactory {
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new BloodOxygenAlert(patientId, "sp02 Alert " + condition , timestamp);
    }

}
