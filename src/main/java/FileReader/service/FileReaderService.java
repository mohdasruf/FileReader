package FileReader.service;

import FileReader.pojo.FileDetails;
import FileReader.pojo.VehicleDetails;

import java.util.List;

public interface FileReaderService {

    public List<FileDetails> getFileContents(String path);

    public FileDetails getFileDetailsFromFile(String filename);

    public VehicleDetails getVehicleDetailsFromFile(String filename);

}
