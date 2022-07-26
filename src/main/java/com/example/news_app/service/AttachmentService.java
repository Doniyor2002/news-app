package com.example.news_app.service;

import com.example.news_app.entity.Attachment;
import com.example.news_app.exception.ResourceNotFoundException;
import com.example.news_app.repository.AttachmentRepository;
import com.example.news_app.response.Apiresponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

@Service
@RequiredArgsConstructor
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final Path root = Paths.get("C:\\Users\\Doniyor\\IdeaProjects\\News_app\\src\\main\\resources\\upload");

    public Apiresponse uploadFile(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileNames = request.getFileNames();
        Attachment save=null;
        while (fileNames.hasNext()) {
            Attachment attachment=new Attachment();
            MultipartFile file = request.getFile(fileNames.next());
            attachment.setName(file.getOriginalFilename());
            attachment.setContentType(file.getContentType());
            attachment.setSize(file.getSize());
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
            attachment.setUrl(this.root + file.getOriginalFilename());
            save = attachmentRepository.save(attachment);

        }
        return Apiresponse.builder().succes(true).message(save.getName() + " nomli fayl saqlandi").build();
    }
    public ResponseEntity<?> getFile(Long id) throws MalformedURLException {
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("file", "id", id));
        Path file = root.resolve(attachment.getName());
        Resource resource = new UrlResource(file.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(attachment.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getName() + "\"")
                .body(resource);
    }
}
