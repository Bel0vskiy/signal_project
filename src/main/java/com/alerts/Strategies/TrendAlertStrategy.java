package com.alerts.Strategies;

import com.alerts.Factories.BloodPressureAlertFactory;
import com.data_management.PatientRecord;

import java.util.List;

public class TrendAlertStrategy implements AlertStrategy {

    @Override
    public boolean checkAlert(List<PatientRecord> records, int patientId) {
        if (records.size() < 3) {
            return false;
        }

        for (int i = 2; i < records.size(); i++) {
            double difference = records.get(i).getMeasurementValue() - records.get(i-1).getMeasurementValue();
            double difference2 = records.get(i-1).getMeasurementValue() - records.get(i-2).getMeasurementValue();

            if (difference > 10 && difference2 > 10) {
               new BloodPressureAlertFactory().createAlert(String.valueOf(patientId), records.get(i).getRecordType() + " Increasing Trend Alert", records.get(i).getTimestamp());
                System.out.println( "Patient " + String.valueOf(patientId)+ " " + records.get(i).getRecordType() +   " Increasing Trend Alert at time " + records.get(i).getTimestamp());
                return true;
            } else if (difference < -10 && difference2 < -10) {
                new BloodPressureAlertFactory().createAlert(String.valueOf(patientId), records.get(i).getRecordType() + " Decreasing Trend Alert", records.get(i).getTimestamp());
                System.out.println( "Patient " + String.valueOf(patientId)+ " " + records.get(i).getRecordType() +   "Decreasing Trend Alert" + records.get(i).getTimestamp());
                return true;
            }
        }
        return false;
    }
}
