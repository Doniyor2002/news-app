package com.example.news_app.controller;

import com.example.news_app.entity.Category;
import com.example.news_app.response.Apiresponse;
import com.example.news_app.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryControler {
    private final CategoryService categoryService;


    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody Category category){
        Apiresponse apiresponse = categoryService.add(category);
        return ResponseEntity.status(apiresponse.isSucces()?201:404).body(apiresponse);
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MODERATOR')")
   @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size
                       ,@RequestParam(defaultValue = "") String name){
        Apiresponse apiresponse=categoryService.getAll(page,size,name);
        return ResponseEntity.status(apiresponse.isSucces()?200:404).body(apiresponse);
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MODERATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        Apiresponse apiresponse=categoryService.getOne(id);
        return ResponseEntity.ok().body(apiresponse);
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MODERATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody Category category){
        Apiresponse apiresponse = categoryService.update(category,id);
        return ResponseEntity.status(apiresponse.isSucces()?201:404).body(apiresponse);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
       Apiresponse apiresponse=categoryService.delete(id);
        return ResponseEntity.status(apiresponse.isSucces()?200:404).body(apiresponse);
    }
}
