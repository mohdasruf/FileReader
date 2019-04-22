package FileReader.pojo;

import FileReader.enums.MimeType;


public class FileDetails {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public MimeType getMimeType() {
        return mimeType;
    }

    public void setMimeType(MimeType mimeType) {
        this.mimeType = mimeType;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public VehicleDetails getVehicleDetails() {
        return vehicleInfo;
    }

    public void setVehicleDetails(VehicleDetails vehicleDetails) {
        this.vehicleInfo = vehicleDetails;
    }

    private Long fileSize;
    private MimeType mimeType;
    private String fileExtension;
    private VehicleDetails vehicleInfo;

}
