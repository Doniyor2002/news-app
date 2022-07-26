package com.example.news_app.controller;

import com.example.news_app.dto.NewsDto;
import com.example.news_app.response.Apiresponse;
import com.example.news_app.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.UUID;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;
    @PreAuthorize(value = "hasAuthority('USER')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody NewsDto newsDto){

        Apiresponse apiresponse = newsService.add(newsDto);
        return ResponseEntity.status(apiresponse.isSucces()?201:404).body(apiresponse);
    }
    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam String phone,@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "") String name,
                                    @RequestParam(defaultValue = "") String categoryName,
                                    @RequestParam(defaultValue = "") String date) throws ParseException {


        Apiresponse apiresponse=newsService.getAll(page,size,name,categoryName,date);
        return ResponseEntity.ok().body(apiresponse);
    }
    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable UUID id){
        Apiresponse apiresponse=newsService.getOne(id);
        return ResponseEntity.ok().body(apiresponse);
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id,@RequestBody NewsDto newsDto){
        Apiresponse apiresponse = newsService.update(newsDto,id);
        return ResponseEntity.status(apiresponse.isSucces()?201:404).body(apiresponse);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        Apiresponse apiresponse=newsService.delete(id);
        return ResponseEntity.status(apiresponse.isSucces()?200:404).body(apiresponse);
    }



    @GetMapping("/myArticle")
    public ResponseEntity<?> getAll() throws ParseException {


        Apiresponse apiresponse=newsService.getUserNews();
        return ResponseEntity.ok().body(apiresponse);
    }
}
