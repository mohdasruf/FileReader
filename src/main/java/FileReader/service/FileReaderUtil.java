package FileReader.service;

import FileReader.pojo.FileDetails;
import FileReader.pojo.VehicleDetails;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public final class FileReaderUtil {

    public static FileDetails setFileDetails(File file) throws IOException {

        FileDetails fileDetails = new FileDetails();
        fileDetails.setName(file.getName());
        fileDetails.setFileSize(file.length());

        String mimeType = Files.probeContentType(file.toPath());

        //set the file extension
        String extension = "";
        int i = file.getName().lastIndexOf('.');
        if (i > 0) {
            extension = file.getName().substring(i+1);
        }
        fileDetails.setFileExtension(extension);

        //set the vehicle details
        VehicleDetails vehicleInfo = setVehicleDetals(file);
        if(vehicleInfo == null ) {
            return null;
        }
        fileDetails.setVehicleDetails(vehicleInfo);
        return fileDetails;
    }


    /*
     Used to fill the vehicleDetails bean
     */
    private static VehicleDetails setVehicleDetals(File file) throws IOException{
        Scanner scanner = new Scanner(file);
        String line = "";
        String[] info;
        if(scanner.hasNext()) {
            line = scanner.nextLine();
        }
        scanner.close();
        //the separator is , for csv else it is space for xls files
        if(line.contains(",")) {
            info = line.split(",");
        } else {
            info = line.split("\\s+");
        }
        VehicleDetails vehicleDetails = new VehicleDetails();
        //if the line cannot be split into 3 tokens then we return null as we don't know
        //where to split the tokens otherwise
        if(info.length != 3) {
            return null;
        }
        //Store the details as uppercase with no white spaces
        vehicleDetails.setRegNo(info[0].toUpperCase().trim());
        vehicleDetails.setMake(info[1].toUpperCase().trim());
        vehicleDetails.setColour(info[2].toUpperCase().trim());

        return vehicleDetails;
    }
}
