package com.vitali.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static com.vitali.constants.Constants.IMAGE_BASE_PATH;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

@Service
@RequiredArgsConstructor
public class ImageService {

//    @Value("${app.image.bucket:/resources/static/images}")
    @Value("${app.image.bucket:C:\\Work\\IdeaProjects\\spring-shop\\src\\main\\resources\\static\\images}")
    private String bucket;

//    private final String basePath = IMAGE_BASE_PATH;

    // save data
    @SneakyThrows
    public void upload(String imagePath, InputStream imageContent) {
//        Path imageFullPath = Path.of(IMAGE_BASE_PATH, imagePath);
        Path imageFullPath = Path.of(bucket, imagePath);

        try (imageContent) {
            Files.createDirectories(imageFullPath.getParent());
            Files.write(imageFullPath, imageContent.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        }
    }

//    @SneakyThrows
//    public Optional<byte[]> get(String imagePath) {
//        Path fullImagePath = Path.of(IMAGE_BASE_PATH, imagePath);
//
//        return Files.exists(fullImagePath)
//                ? Optional.of(Files.readAllBytes(fullImagePath))
//                : Optional.empty();
//    }

}
