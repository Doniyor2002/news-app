package com.example.news_app.controller;

import com.example.news_app.response.Apiresponse;
import com.example.news_app.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attachment")
public class AttachmentController {
    private final AttachmentService attachmentService;
    @PostMapping
    public ResponseEntity<?> upload(MultipartHttpServletRequest request) throws IOException {
        Apiresponse apiresponse=attachmentService.uploadFile(request);
        return ResponseEntity.ok().body(apiresponse);
    }


}
