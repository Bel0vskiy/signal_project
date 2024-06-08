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


//        Testing that addPatinetData works correctly, manually add 2 PatientRecords,
//        Such patientID is chosen because Generators simulate data only for ids in range 1-50;
        storage.addPatientData(1000, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1000, 200.0, "WhiteBloodCells", 1714376789051L);
        List<PatientRecord> recordsTest = storage.getRecords(1000,0,Long.MAX_VALUE);
        assertEquals(2, recordsTest.size(), "Two PatientRecords were not successfuly added, or less/more PatintRecords were added"); // Check if two records are retrieved


//        Tests Reader.readData(DataStorage) method and
//        newly implemented getRecords() with RecordType as the parameter
        List<PatientRecord> records = storage.getRecords(20, 0, Long.MAX_VALUE);

//        Tests that getRecords with RecordType == "Alert" works correctly
        List<PatientRecord> recordsAlert = storage.getRecords(10, 0, Long.MAX_VALUE, "Alert");
        for (PatientRecord record : recordsAlert) {
            assertEquals("Alert", record.getRecordType(), "Alert getter does not work correctly"); // Validate each record type
        }

//        Tests that getRecords with RecordType == "Cholesterol" works correctly
        List<PatientRecord> recordsCholesterol = storage.getRecords(20, 0, Long.MAX_VALUE, "Cholesterol");
        for (PatientRecord record : recordsCholesterol) {
            assertEquals("Cholesterol", record.getRecordType(), "Cholesterol getter does not work correctly"); // Validate each record type
        }
//        Tests that getRecords with RecordType == "DiastolicPressure" works correctly
        List<PatientRecord> recordsDiastolicPressure = storage.getRecords(20, 0, Long.MAX_VALUE, "DiastolicPressure");
        for (PatientRecord record : recordsDiastolicPressure) {
            assertEquals("DiastolicPressure", record.getRecordType(), "DiastolicPressure getter does not work correctly"); // Validate each record type
        }

//        Tests that getRecords with RecordType == "ECG" works correctly
        List<PatientRecord> recordsECG = storage.getRecords(20, 0, Long.MAX_VALUE, "ECG");
        for (PatientRecord record : recordsECG) {
            assertEquals("ECG", record.getRecordType(), "ECG getter does not work correctly"); // Validate each record type
        }

//        Tests that getRecords with RecordType == "RedBloodCells" works correctly
        List<PatientRecord> recordsRedBloodCells = storage.getRecords(20, 0, Long.MAX_VALUE, "RedBloodCells");
        for (PatientRecord record : recordsRedBloodCells) {
            assertEquals("RedBloodCells", record.getRecordType(), "RedBloodCells getter does not work correctly"); // Validate each record type
        }

//        Tests that getRecords with RecordType == "Saturation" works correctly
        List<PatientRecord> recordsSaturation = storage.getRecords(20, 0, Long.MAX_VALUE, "Saturation");
        for (PatientRecord record : recordsSaturation) {
            assertEquals("Saturation", record.getRecordType(), "Saturation getter does not work correctly"); // Validate each record type
        }

//        Tests that getRecords with RecordType == "SystolicPressure" works correctly
        List<PatientRecord> recordsSystolicPressure = storage.getRecords(20, 0, Long.MAX_VALUE, "SystolicPressure");
        for (PatientRecord record : recordsSystolicPressure) {
            assertEquals("SystolicPressure", record.getRecordType(), "SystolicPressure getter does not work correctly"); // Validate each record type
        }

//        Tests that getRecords with RecordType == "WhiteBloodCells" works correctly
        List<PatientRecord> recordsWhiteBloodCells = storage.getRecords(20, 0, Long.MAX_VALUE, "WhiteBloodCells");
        for (PatientRecord record : recordsWhiteBloodCells) {
            assertEquals("WhiteBloodCells", record.getRecordType(), "WhiteBloodCells getter does not work correctly"); // Validate each record type
        }
    }
}
