package com.empinventory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public interface FlatFileUploadController {
	ResponseEntity<String> uploadFile(MultipartFile file);
	ResponseEntity<String> getStatus(int taskId);
}
