package me.eggme.classh.utils;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Component;

import java.io.File;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

@Component
public class FileUploader {

    private Map<String, String> cloudinaryConfig;
    private Cloudinary cloudinary;

    public FileUploader(){
        cloudinaryConfig = new HashMap();
        cloudinaryConfig.put("cloud_name", "dg8tebwjm");
        cloudinaryConfig.put("api_key", "426656436794364");
        cloudinaryConfig.put("api_secret", "FiitdYM2xtv9Oa-gyaauEIpzSoY");
        cloudinary = new Cloudinary(cloudinaryConfig);
    }

    public String saveFile(File file, ResourceType resourceType){
        String fileURL = null;
        try {
            cloudinary.uploader().upload(file, Cloudinary.asMap(
                    "public_id", encryptoSHA256(file.getName()),
                            "resource_type", resourceType.getValue()
            ));
            String cloudinaryUrl = cloudinary.url().generate(encryptoSHA256(file.getName()));
            Map<String, String> resource = cloudinary.api().resource(encryptoSHA256(file.getName()), Cloudinary.asMap(
                    "resource_type", resourceType.getValue()
            ));
            fileURL = resource.get("url");
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileURL;
    }

    private String encryptoSHA256(String text){
        StringBuffer sb = new StringBuffer();
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(text.getBytes());
            for(int i=0;i<hash.length;i++){
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) sb.append('0');
                sb.append(hex);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
