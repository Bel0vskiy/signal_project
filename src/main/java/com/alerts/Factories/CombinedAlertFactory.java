package com.alerts.Factories;

import com.alerts.Alerts.Alert;
import com.alerts.Alerts.CombinedAlert;

public class CombinedAlertFactory extends AlertFactory {
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new CombinedAlert(patientId, "Combined Alert " + condition, timestamp);
    }
}
