package com.example.mymovieapp.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import com.example.mymovieapp.service.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile multipartFile) throws IOException {
            File file = File
                    .createTempFile("temp-file", multipartFile.getOriginalFilename());
            multipartFile.transferTo(file);

            return this.cloudinary.uploader()
                    .upload(file, ObjectUtils.emptyMap())
                    .get("url").toString();
    }

    @Override
    public void deleteImage(String url){
        String publicId = urlToPublicId(url);
        try {
            this.cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private String urlToPublicId(String imageUrl) {
        String[] splits = imageUrl.split("/");

        if (splits.length == 0) {
            throw new IllegalStateException("Invalid image url");
        }

        return splits[splits.length - 1].split("\\.")[0];
    }

    @Override
    public boolean containUrl(String url) {
        try {
            return cloudinary.api().resource(url, ObjectUtils.emptyMap()).containsKey("url");
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> findAll(){
        List<String> fileUrls = new ArrayList<>();

        try {
            // List all resources in Cloudinary
            ApiResponse result = cloudinary.api().resources(ObjectUtils.emptyMap());

            // Extract the 'resources' key from the result
            Object resources = result.get("resources");

            // Add the URLs of all resources to the list
            if (resources instanceof Iterable) {
                for (Object resource : (Iterable<?>) resources) {
                    if (resource instanceof Map) {
                        Map<String, Object> resourceMap = (Map<String, Object>) resource;
                        Object url = resourceMap.get("url");
                        if (url != null) {
                            fileUrls.add(url.toString());
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., network issues, API errors)
            e.printStackTrace();
        }

        return fileUrls;
    }
}
