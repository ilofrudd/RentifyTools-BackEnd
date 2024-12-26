package org.rentifytools.service;

import com.google.cloud.storage.*;
import org.rentifytools.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CloudStorageService {
    private final Storage storage = StorageOptions.getDefaultInstance().getService();
    private final String bucketName = "rentify_tool";

    public String uploadImage(MultipartFile file) {
        Long userId = SecurityUtils.getCurrentUserId();
        String fileName = userId + "-" + file.getOriginalFilename();
        String contentType = file.getContentType();
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        Blob blob = storage.get(BlobId.of(bucketName, fileName));
        if (blob != null && blob.exists()) {
            return "https://storage.googleapis.com/" + bucketName + "/" + fileName;
        }

        try {
            BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileName)
                    .setContentType(contentType)
                    .build();
            storage.create(blobInfo, file.getBytes());
            return "https://storage.googleapis.com/" + bucketName + "/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    public boolean deleteFileByUrl(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        BlobId blobId = BlobId.of(bucketName, fileName);

        return storage.delete(blobId);
    }
}
