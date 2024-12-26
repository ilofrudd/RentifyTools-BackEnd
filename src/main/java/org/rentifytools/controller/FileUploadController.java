package org.rentifytools.controller;

import org.rentifytools.service.CloudStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    @Autowired
    private CloudStorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadImages(@RequestParam("images") List<MultipartFile> files) {
        List<String> imageUrls = files.stream()
                .map(storageService::uploadImage)
                .collect(Collectors.toList());

        return ResponseEntity.ok(imageUrls);
    }
}
