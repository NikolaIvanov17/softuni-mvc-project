package org.softuni.learningportal.service.impl;

import com.cloudinary.Cloudinary;
import org.softuni.learningportal.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(String image) throws IOException {
        File file = File
                .createTempFile("temp-file", image);

        return this.cloudinary.uploader()
                .upload(file, new HashMap())
                .get("url").toString();
    }
}
