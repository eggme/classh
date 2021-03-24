package me.eggme.classh.utils;

import com.cloudinary.Cloudinary;

import java.io.File;
import java.util.Map;

public class PdfFileUploader extends FileUploader{

    @Override
    public String saveFile(File file, ResourceType resourceType) {
        String fileURL = null;
        try {
            cloudinary.uploader().upload(file, Cloudinary.asMap(
                    "public_id", encryptoSHA256(file.getName())
            ));
            String cloudinaryUrl = cloudinary.url().generate(encryptoSHA256(file.getName()));
            Map<String, String> resource = cloudinary.api().resource(encryptoSHA256(file.getName()), Cloudinary.asMap(
                    "pages", true
            ));
            fileURL = resource.get("url");
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileURL;
    }
}
