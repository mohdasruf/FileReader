package FileReader.service;

import FileReader.pojo.FileDetails;
import FileReader.pojo.VehicleDetails;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FileReaderServiceImplTest {

    private FileReaderService fileReaderService;
    private static String DIRECTORY = System.getProperty("user.dir") + "/testfiles";
    private static String EMPTY_DIRECTORY = System.getProperty("user.dir") + "/testfiles2";

    @Before
    public void setUp() throws Exception {

        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void testFileCount() throws Exception {
        List<FileDetails> fileDetailsList = fileReaderService.getFileContents(DIRECTORY);
        assertEquals(10, fileDetailsList.size());
    }

    @Test
    public void testEmptyDirectory() throws Exception {
        List<FileDetails> fileDetailsList = fileReaderService.getFileContents(EMPTY_DIRECTORY);
        assertEquals(0, fileDetailsList.size());
    }

    @Test
    public void testMimeTypeCSV () throws Exception {
        List<FileDetails> fileDetailsList = fileReaderService.getFileContents(DIRECTORY);
        FileDetails fileDetails = fileReaderService.getFileDetailsFromFile("file1.csv");
        assertNull(fileDetails.getMimeType());
    }

    @Test
    public void testVehicleDetails() throws Exception {
        List<FileDetails> fileDetailsList = fileReaderService.getFileContents(DIRECTORY);
        VehicleDetails vehicleDetails = fileReaderService.getVehicleDetailsFromFile("file1.csv");
        assertEquals("YS59ABZ", vehicleDetails.getRegNo());
        assertEquals("BMW", vehicleDetails.getMake());
        assertEquals("BLUE", vehicleDetails.getColour());
    }

}
