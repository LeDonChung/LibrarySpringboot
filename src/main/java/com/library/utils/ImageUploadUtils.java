package com.library.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class ImageUploadUtils {
    private final String UPLOAD_FOLDER = "D:\\SpringBoot\\LibrarySpringboot\\src\\main\\resources\\static\\images\\book";

    public boolean uploadImage(MultipartFile bookCover) {
        boolean isUpload = false;
        try {
            Files.copy(bookCover.getInputStream(), Paths.get(UPLOAD_FOLDER + File.separator, bookCover.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            isUpload = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpload;
    }

    public boolean checkExisted(MultipartFile imageProduct) {
        boolean isExisted = false;
        try {
            File file = new File(UPLOAD_FOLDER + File.separator, imageProduct.getOriginalFilename());
            isExisted = file.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isExisted;
    }
}
