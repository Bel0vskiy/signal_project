package data_management;

import static org.junit.jupiter.api.Assertions.*;


import com.alerts.*;
import com.alerts.Factories.*;
import com.alerts.Strategies.*;
import com.data_management.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AlertFactoryTests {

    private DataStorage storage;
    private Patient patient;

    @BeforeEach
    public void setUp() {
        storage = DataStorage.getInstance();
    }

    @Test
    public void testBloodPressureMonitorIncreasingTrendSystolic() {
        Reader reader = new Reader("test.txt");
        DataStorage storage = DataStorage.getInstance();

        AlertGenerator generator = new AlertGenerator(storage);

        storage.addPatientData(100, 0, "SystolicPressure", 1714376789050L);
        storage.addPatientData(100, 0, "Saturation", 1714376789051L);


        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
        boolean e = new OxygenSaturationStrategy(new BloodOxygenLevelAlertFactory()).checkAlert(recordsTest, 214);
        assertTrue(e, "The blood pressure monitor should detect an increasing trend");
    }

    @Test
    public void testBloodPressureMonitorDecreasingTrendSystolic() {
        storage.addPatientData(100, 300.0, "SystolicPressure", 1714376789050L);
        storage.addPatientData(100, 200.0, "SystolicPressure", 1714376789051L);
        storage.addPatientData(100, 100.0, "SystolicPressure", 1714376789052L);

        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
        boolean result = new TrendAlertStrategy().checkAlert(recordsTest, 100);
        assertTrue(result, "The blood pressure monitor should detect a decreasing trend");
    }

    @Test
    public void testBloodPressureMonitorNoSignificantTrendSystolic() {
        storage.addPatientData(100, 100.0, "SystolicPressure", 1714376789050L);
        storage.addPatientData(100, 105.0, "SystolicPressure", 1714376789051L);
        storage.addPatientData(100, 110.0, "SystolicPressure", 1714376789052L);

        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
        boolean result = new TrendAlertStrategy().checkAlert(recordsTest, 100);
        assertFalse(result, "The blood pressure monitor should not detect any significant trend");
    }

    @Test
    public void testBloodPressureMonitorInsufficientRecordsSystolic() {
        storage.addPatientData(100, 100.0, "DiastolicPressure", 1714376789050L);
        storage.addPatientData(100, 200.0, "DiastolicPressure", 1714376789051L);

        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
        boolean result = new TrendAlertStrategy().checkAlert(recordsTest, 100);
        assertFalse(result, "The blood pressure monitor should not detect any trend with less than 3 records");
    }

    @Test
    public void testBloodPressureMonitorIncreasingTrendDiastolic() {
        storage.addPatientData(100, 59.0, "DiastolicPressure", 1714376789050L);
        storage.addPatientData(100, 70.0, "DiastolicPressure", 1714376789051L);
        storage.addPatientData(100, 82.0, "DiastolicPressure", 1714376789052L);

        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
        boolean result = new TrendAlertStrategy().checkAlert(recordsTest, 100);
        assertTrue(result, "The blood pressure monitor should detect an increasing trend");
    }

    @Test
    public void testBloodPressureMonitorDecreasingTrendDiastolic() {
        storage.addPatientData(100, 82.0, "DiastolicPressure", 1714376789050L);
        storage.addPatientData(100, 70.0, "DiastolicPressure", 1714376789051L);
        storage.addPatientData(100, 59.0, "DiastolicPressure", 1714376789052L);

        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
        boolean result = new TrendAlertStrategy().checkAlert(recordsTest, 100);
        assertTrue(result, "The blood pressure monitor should detect a decreasing trend");
    }

    @Test
    public void testBloodPressureMonitorNoSignificantTrendDiastolic() {
        storage.addPatientData(100, 60.0, "DiastolicPressure", 1714376789050L);
        storage.addPatientData(100, 65.0, "DiastolicPressure", 1714376789051L);
        storage.addPatientData(100, 70.0, "DiastolicPressure", 1714376789052L);

        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
        boolean result = new TrendAlertStrategy().checkAlert(recordsTest, 100);
        assertFalse(result, "The blood pressure monitor should not detect any significant trend");
    }

    @Test
    public void testBloodPressureMonitorInsufficientRecordsDiastolic() {
        storage.addPatientData(100, 60.0, "DiastolicPressure", 1714376789050L);
        storage.addPatientData(100, 70.0, "DiastolicPressure", 1714376789051L);

        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
        boolean result = new TrendAlertStrategy().checkAlert(recordsTest, 100);
        assertFalse(result, "The blood pressure monitor should not detect any trend with less than 3 records");
    }

    @Test
    public void testCheckCriticalThresholdAlertSystolic() {
        DataStorage storage = DataStorage.getInstance();

        storage.addPatientData(100, 130.0, "SystolicPressure", 1714376789050L);
        storage.addPatientData(100, 110.0, "SystolicPressure", 1714376789051L);
        storage.addPatientData(100, 50.0, "SystolicPressure", 1714376789052L);



        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
        boolean e = new TrendAlertStrategy().checkAlert(recordsTest, 100);
        assertTrue(e, "Critical Threshold Systolic Pressure");
    }

    @Test
    public void testCheckCriticalThresholdAlertDiastolic() {
        storage.addPatientData(100, 130.0, "DiastolicPressure", 1714376789050L);
        storage.addPatientData(100, 110.0, "DiastolicPressure", 1714376789051L);
        storage.addPatientData(100, 50.0, "DiastolicPressure", 1714376789052L);

        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
        boolean result = new BloodPressureStrategy(new BloodPressureAlertFactory()).checkAlert(recordsTest, recordsTest.get(0).getPatientId());
        assertTrue(result, "Critical Threshold Diastolic Pressure");
    }

    @Test
    public void testBloodSaturationMonitorLowSaturation() {
        storage.addPatientData(100, 91.0, "Saturation", 1714376789050L);

        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
        boolean result = new OxygenSaturationStrategy(new BloodOxygenLevelAlertFactory()).checkAlert(recordsTest, 100);
        assertTrue(result, "The blood saturation monitor should detect low saturation");
    }

    @Test
    public void testBloodSaturationMonitorRapidDrop() {
        storage.addPatientData(100, 96.0, "Saturation", 1714376789050L);
        storage.addPatientData(100, 90.0, "Saturation", 1714377389050L);

        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
        boolean result = new OxygenSaturationStrategy(new BloodOxygenLevelAlertFactory()).checkAlert(recordsTest, 100);
        assertTrue(result, "The blood saturation monitor should detect a rapid drop in saturation");
    }

    @Test
    public void testBloodSaturationMonitorNoAlert() {
        storage.addPatientData(100, 95.0, "Saturation", 1714376789050L);
        storage.addPatientData(100, 94.0, "Saturation", 1714377389050L);

        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
        boolean result = new OxygenSaturationStrategy(new BloodOxygenLevelAlertFactory()).checkAlert(recordsTest, 100);
        assertFalse(result, "The blood saturation monitor should not detect any alert");
    }

    @Test
    public void testECGMonitorAbnormalData() {
        storage.addPatientData(100, 1.0, "ECG", 1714376789050L);
        storage.addPatientData(100, 1.2, "ECG", 1714376789051L);
        storage.addPatientData(100, 1.1, "ECG", 1714376789052L);
        storage.addPatientData(100, 1.3, "ECG", 1714376789053L);
        storage.addPatientData(100, 1.0, "ECG", 1714376789054L);
        storage.addPatientData(100, 2.5, "ECG", 1714376789055L);


        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
        boolean result = new ECGAlertStrategy().checkAlert(recordsTest, 100);
        assertTrue(result, "The ECG monitor should detect abnormal data");
    }

    @Test
    public void testECGMonitorNormalData() {
        storage.addPatientData(100, 1.0, "ECG", 1714376789050L);
        storage.addPatientData(100, 1.2, "ECG", 1714376789051L);
        storage.addPatientData(100, 1.1, "ECG", 1714376789052L);
        storage.addPatientData(100, 1.3, "ECG", 1714376789053L);
        storage.addPatientData(100, 1.0, "ECG", 1714376789054L);
        storage.addPatientData(100, 1.2, "ECG", 1714376789055L);

        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
        boolean result = new ECGAlertStrategy().checkAlert(recordsTest, 100);
        assertFalse(result, "The ECG monitor should not detect any abnormal data");
    }

    @Test
    public void testECGMonitorInsufficientData() {
        storage.addPatientData(100, 1.0, "ECG", 1714376789050L);
        storage.addPatientData(100, 1.2, "ECG", 1714376789051L);
        storage.addPatientData(100, 1.1, "ECG", 1714376789052L);
        storage.addPatientData(100, 1.3, "ECG", 1714376789053L);

        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
        boolean result = new ECGAlertStrategy().checkAlert(recordsTest, 100);
        assertFalse(result, "The ECG monitor should not detect any abnormal data with less than 5 records");
    }

    @Test
    public void testCombinedAlertMonitor() {
        storage.addPatientData(100, 85.0, "SystolicPressure", 1714376789050L); // Low systolic pressure
        storage.addPatientData(100, 85.0, "Saturation", 1714376789051L); // Low saturation

        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
        boolean result = new CombinedAlertStrategy().checkAlert(recordsTest, 100);
        assertTrue(result, "The combined alert monitor should detect both low systolic pressure and low saturation");
    }

    @Test
    public void hearRateCriticalLow() {
        storage.addPatientData(100, 59, "HeartRate", 1714376789050L); // Low systolic pressure

        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
        boolean result = new HeartRateStrategy(new HeartRateAlertFactory()).checkAlert(recordsTest, 100);
        assertTrue(result, "Low heart rate");
    }
    @Test
    public void hearRateCriticalHigh() {
        storage.addPatientData(100, 199, "HeartRate", 1714376789050L); // Low systolic pressure

        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
        boolean result = new HeartRateStrategy(new HeartRateAlertFactory()).checkAlert(recordsTest, 100);
        assertTrue(result, "Low heart rate");
    }
    @Test
    public void hearRateCriticalNormal() {
        storage.addPatientData(100, 0, "HeartRate", 1714376789050L); // Low systolic pressure

        List<PatientRecord> recordsTest = storage.getRecords(100, 0, Long.MAX_VALUE);
        boolean result = new HeartRateStrategy(new HeartRateAlertFactory()).checkAlert(recordsTest, 100);
        assertTrue(result, "Normal Heart Rate");
    }



}

