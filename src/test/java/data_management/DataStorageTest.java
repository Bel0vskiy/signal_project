package data_management;

import static org.junit.jupiter.api.Assertions.*;

import com.data_management.Reader;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.PatientRecord;

import java.util.List;

class DataStorageTest {

    @Test
    void testAddAndGetRecords() throws Exception {
        // TODO Perhaps you can implement a mock data reader to mock the test data?
        // DataReader reader
        Reader reader = new Reader("/Users/vd/IdeaProjects/signal_project/test.txt");
        DataStorage storage = new DataStorage();
        reader.readData(storage);


        storage.addPatientData(100, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(100, 200.0, "WhiteBloodCells", 1714376789051L);
        List<PatientRecord> recordsTest = storage.getRecords(100,0,Long.MAX_VALUE);
        assertEquals(2, recordsTest.size(), "Two PatientRecords were not successfuly added, or less/more PatintRecords were added"); // Check if two records are retrieved


        List<PatientRecord> records = storage.getRecords(20, 0, Long.MAX_VALUE);

        List<PatientRecord> recordsAlert = storage.getRecords(10, 0, Long.MAX_VALUE, "Alert");
        for (PatientRecord record : recordsAlert) {
            assertEquals("Alert", record.getRecordType(), "Alert getter does not work correctly"); // Validate each record type
        }

        List<PatientRecord> recordsCholesterol = storage.getRecords(20, 0, Long.MAX_VALUE, "Cholesterol");
        for (PatientRecord record : recordsCholesterol) {
            assertEquals("Cholesterol", record.getRecordType(), "Cholesterol getter does not work correctly"); // Validate each record type
        }

        List<PatientRecord> recordsDiastolicPressure = storage.getRecords(20, 0, Long.MAX_VALUE, "DiastolicPressure");
        for (PatientRecord record : recordsDiastolicPressure) {
            assertEquals("DiastolicPressure", record.getRecordType(), "DiastolicPressure getter does not work correctly"); // Validate each record type
        }

        List<PatientRecord> recordsECG = storage.getRecords(20, 0, Long.MAX_VALUE, "ECG");
        for (PatientRecord record : recordsECG) {
            assertEquals("ECG", record.getRecordType(), "ECG getter does not work correctly"); // Validate each record type
        }

        List<PatientRecord> recordsRedBloodCells = storage.getRecords(20, 0, Long.MAX_VALUE, "RedBloodCells");
        for (PatientRecord record : recordsRedBloodCells) {
            assertEquals("RedBloodCells", record.getRecordType(), "RedBloodCells getter does not work correctly"); // Validate each record type
        }

        List<PatientRecord> recordsSaturation = storage.getRecords(20, 0, Long.MAX_VALUE, "Saturation");
        for (PatientRecord record : recordsSaturation) {
            assertEquals("Saturation", record.getRecordType(), "Saturation getter does not work correctly"); // Validate each record type
        }

        List<PatientRecord> recordsSystolicPressure = storage.getRecords(20, 0, Long.MAX_VALUE, "SystolicPressure");
        for (PatientRecord record : recordsSystolicPressure) {
            assertEquals("SystolicPressure", record.getRecordType(), "SystolicPressure getter does not work correctly"); // Validate each record type
        }
        List<PatientRecord> recordsWhiteBloodCells = storage.getRecords(20, 0, Long.MAX_VALUE, "WhiteBloodCells");
        for (PatientRecord record : recordsWhiteBloodCells) {
            assertEquals("WhiteBloodCells", record.getRecordType(), "WhiteBloodCells getter does not work correctly"); // Validate each record type
        }
    }
}
