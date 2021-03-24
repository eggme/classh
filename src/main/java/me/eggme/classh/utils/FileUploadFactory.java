package me.eggme.classh.utils;

public class FileUploadFactory {

    private static FileUploader fileUploader;

    public static FileUploader getFileUploader(ResourceType resourceType){
        switch (resourceType){
            case PDF:
                fileUploader = new PdfFileUploader();
                break;
            case IMAGE:
            case VIDEO:
                fileUploader = new MediaFileUploader();
                break;
        }
        return fileUploader;
    }
}
