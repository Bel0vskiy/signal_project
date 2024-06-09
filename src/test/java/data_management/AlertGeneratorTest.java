package data_management;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.PatientRecord;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AlertGeneratorTest {

//    private DataStorage storage;
//    private AlertGenerator generator;
//
//    @BeforeEach
//    public void setUp() {
//        storage = new DataStorage();
//        generator = new AlertGenerator(storage);
//    }
//
//
//        @Test
//        public void testBloodPressureMonitorIncreasingTrendSystolic() {
//            storage.addPatientData(100, 100.0, "SystolicPressure", 1714376789050L);
//            storage.addPatientData(100, 200.0, "SystolicPressure", 1714376789051L);
//            storage.addPatientData(100, 300.0, "SystolicPressure", 1714376789052L);
//
//            List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
//            boolean result = generator.bloodPressureMonitor(recordsTest, "systolic", 100);
//            assertTrue(result, "The blood pressure monitor should detect an increasing trend");
//        }
//
//        @Test
//        public void testBloodPressureMonitorDecreasingTrendSystolic() {
//            storage.addPatientData(100, 300.0, "SystolicPressure", 1714376789050L);
//            storage.addPatientData(100, 200.0, "SystolicPressure", 1714376789051L);
//            storage.addPatientData(100, 100.0, "SystolicPressure", 1714376789052L);
//
//            List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
//            boolean result = generator.bloodPressureMonitor(recordsTest, "systolic", 100);
//            assertTrue(result, "The blood pressure monitor should detect a decreasing trend");
//        }
//
//        @Test
//        public void testBloodPressureMonitorNoSignificantTrendSystolic() {
//            storage.addPatientData(100, 100.0, "SystolicPressure", 1714376789050L);
//            storage.addPatientData(100, 105.0, "SystolicPressure", 1714376789051L);
//            storage.addPatientData(100, 110.0, "SystolicPressure", 1714376789052L);
//
//            List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
//            boolean result = generator.bloodPressureMonitor(recordsTest, "systolic", 100);
//            assertFalse(result, "The blood pressure monitor should not detect any significant trend");
//        }
//
//        @Test
//        public void testBloodPressureMonitorInsufficientRecordsSystolic() {
//            storage.addPatientData(100, 100.0, "SystolicPressure", 1714376789050L);
//            storage.addPatientData(100, 200.0, "SystolicPressure", 1714376789051L);
//
//            List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
//            boolean result = generator.bloodPressureMonitor(recordsTest, "systolic", 100);
//            assertFalse(result, "The blood pressure monitor should not detect any trend with less than 3 records");
//        }
//
//        @Test
//        public void testBloodPressureMonitorIncreasingTrendDiastolic() {
//            storage.addPatientData(100, 59.0, "DiastolicPressure", 1714376789050L);
//            storage.addPatientData(100, 70.0, "DiastolicPressure", 1714376789051L);
//            storage.addPatientData(100, 82.0, "DiastolicPressure", 1714376789052L);
//
//            List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
//            boolean result = generator.bloodPressureMonitor(recordsTest, "diastolic", 100);
//            assertTrue(result, "The blood pressure monitor should detect an increasing trend");
//        }
//
//        @Test
//        public void testBloodPressureMonitorDecreasingTrendDiastolic() {
//            storage.addPatientData(100, 82.0, "DiastolicPressure", 1714376789050L);
//            storage.addPatientData(100, 70.0, "DiastolicPressure", 1714376789051L);
//            storage.addPatientData(100, 59.0, "DiastolicPressure", 1714376789052L);
//
//            List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
//            boolean result = generator.bloodPressureMonitor(recordsTest, "diastolic", 100);
//            assertTrue(result, "The blood pressure monitor should detect a decreasing trend");
//        }
//
//        @Test
//        public void testBloodPressureMonitorNoSignificantTrendDiastolic() {
//            storage.addPatientData(100, 60.0, "DiastolicPressure", 1714376789050L);
//            storage.addPatientData(100, 65.0, "DiastolicPressure", 1714376789051L);
//            storage.addPatientData(100, 70.0, "DiastolicPressure", 1714376789052L);
//
//            List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
//            boolean result = generator.bloodPressureMonitor(recordsTest, "diastolic", 100);
//            assertFalse(result, "The blood pressure monitor should not detect any significant trend");
//        }
//
//        @Test
//        public void testBloodPressureMonitorInsufficientRecordsDiastolic() {
//            storage.addPatientData(100, 60.0, "DiastolicPressure", 1714376789050L);
//            storage.addPatientData(100, 70.0, "DiastolicPressure", 1714376789051L);
//
//            List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
//            boolean result = generator.bloodPressureMonitor(recordsTest, "diastolic", 100);
//            assertFalse(result, "The blood pressure monitor should not detect any trend with less than 3 records");
//        }
//
//        @Test
//        public void testCheckCriticalThresholdAlertSystolic() {
//            storage.addPatientData(100, 200.0, "SystolicPressure", 1714376789050L);
//            storage.addPatientData(100, 150.0, "SystolicPressure", 1714376789051L);
//            storage.addPatientData(100, 85.0, "SystolicPressure", 1714376789052L);
//
//            List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
//            boolean result = generator.checkCriticalThresholdAlert(recordsTest, "SystolicPressure", 100);
//            assertTrue(result, "The critical threshold alert should be triggered for systolic pressure");
//        }
//
//        @Test
//        public void testCheckCriticalThresholdAlertDiastolic() {
//            storage.addPatientData(100, 130.0, "DiastolicPressure", 1714376789050L);
//            storage.addPatientData(100, 110.0, "DiastolicPressure", 1714376789051L);
//            storage.addPatientData(100, 50.0, "DiastolicPressure", 1714376789052L);
//
//            List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
//            boolean result = generator.checkCriticalThresholdAlert(recordsTest, "DiastolicPressure", 100);
//            assertTrue(result, "The critical threshold alert should be triggered for diastolic pressure");
//        }
//
//
//    @Test
//    public void testBloodSaturationMonitorLowSaturation() {
//        storage.addPatientData(100, 91.0, "Saturation", 1714376789050L);
//
//        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
//        boolean result = generator.bloodSaturationMonitor(recordsTest, 100);
//        assertTrue(result, "The blood saturation monitor should detect low saturation");
//    }
//
//    @Test
//    public void testBloodSaturationMonitorRapidDrop() {
//        storage.addPatientData(100, 96.0, "Saturation", 1714376789050L);
//        storage.addPatientData(100, 90.0, "Saturation", 1714377389050L); // Drop of 6 within 10 minutes
//
//        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
//        boolean result = generator.bloodSaturationMonitor(recordsTest, 100);
//        assertTrue(result, "The blood saturation monitor should detect a rapid drop in saturation");
//    }
//
//    @Test
//    public void testBloodSaturationMonitorNoAlert() {
//        storage.addPatientData(100, 95.0, "Saturation", 1714376789050L);
//        storage.addPatientData(100, 94.0, "Saturation", 1714377389050L);
//
//        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
//        boolean result = generator.bloodSaturationMonitor(recordsTest, 100);
//        assertFalse(result, "The blood saturation monitor should not detect any alert");
//    }
//
//    @Test
//    public void testECGMonitorAbnormalData() {
//        storage.addPatientData(100, 1.0, "ECG", 1714376789050L);
//        storage.addPatientData(100, 1.2, "ECG", 1714376789051L);
//        storage.addPatientData(100, 1.1, "ECG", 1714376789052L);
//        storage.addPatientData(100, 1.3, "ECG", 1714376789053L);
//        storage.addPatientData(100, 1.0, "ECG", 1714376789054L);
//        storage.addPatientData(100, 2.5, "ECG", 1714376789055L);
//
//        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
//        boolean result = generator.ECGMonitor(recordsTest, 100);
//        assertTrue(result, "The ECG monitor should detect abnormal data");
//    }
//
//    @Test
//    public void testECGMonitorNormalData() {
//        storage.addPatientData(100, 1.0, "ECG", 1714376789050L);
//        storage.addPatientData(100, 1.2, "ECG", 1714376789051L);
//        storage.addPatientData(100, 1.1, "ECG", 1714376789052L);
//        storage.addPatientData(100, 1.3, "ECG", 1714376789053L);
//        storage.addPatientData(100, 1.0, "ECG", 1714376789054L);
//        storage.addPatientData(100, 1.2, "ECG", 1714376789055L);
//
//        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
//        boolean result = generator.ECGMonitor(recordsTest, 100);
//        assertFalse(result, "The ECG monitor should not detect any abnormal data");
//    }
//
//    @Test
//    public void testECGMonitorInsufficientData() {
//        storage.addPatientData(100, 1.0, "ECG", 1714376789050L);
//        storage.addPatientData(100, 1.2, "ECG", 1714376789051L);
//        storage.addPatientData(100, 1.1, "ECG", 1714376789052L);
//        storage.addPatientData(100, 1.3, "ECG", 1714376789053L);
//
//        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
//        boolean result = generator.ECGMonitor(recordsTest, 100);
//        assertFalse(result, "The ECG monitor should not detect any abnormal data with less than 5 records");
//    }
//
//    @Test
//    public void testManualAlertTriggered() {
//        storage.addPatientData(100, 1.0, "Alert", 1714376789050L);
//
//        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
//        boolean result = generator.manualAlert(recordsTest, 100);
//        assertTrue(result, "The manual alert should be detected");
//    }
//
//    @Test
//    public void testManualAlertNotTriggered() {
//        storage.addPatientData(100, 0.0, "Alert", 1714376789050L);
//
//        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
//        boolean result = generator.manualAlert(recordsTest, 100);
//        assertFalse(result, "The manual alert should not be detected");
//    }
//


}