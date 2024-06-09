package com.alerts.Strategies;

import com.alerts.Factories.CombinedAlertFactory;
import com.data_management.PatientRecord;

import java.util.List;

    public class CombinedAlertStrategy implements AlertStrategy {

        public CombinedAlertStrategy() {

        }

        @Override
        public boolean checkAlert(List<PatientRecord> records, int patientId) {
            boolean lowSystolic = false;
            boolean lowSaturation = false;

            for (PatientRecord record : records) {
                if ("SystolicPressure".equals(record.getRecordType()) && record.getMeasurementValue() < 90) {
                    lowSystolic = true;
                } else if ("Saturation".equals(record.getRecordType()) && record.getMeasurementValue() < 92) {
                    lowSaturation = true;
                }

                if (lowSystolic && lowSaturation) {
                    new CombinedAlertFactory().createAlert(String.valueOf(patientId), "Combined Alert", record.getTimestamp());
                    System.out.println("New Combined Alert " + "for patient " + patientId + " at time " + record.getTimestamp());
                    return true;
                }
            }
            return false;
        }
    }


