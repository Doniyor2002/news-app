package com.example.news_app.controller;

import com.example.news_app.dto.NewsDto;
import com.example.news_app.response.Apiresponse;
import com.example.news_app.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;
    @PreAuthorize(value = "hasAuthority('USER')")
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody NewsDto newsDto){

        Apiresponse apiresponse = newsService.add(newsDto);
        return ResponseEntity.status(apiresponse.isSucces()?201:404).body(apiresponse);
    }
    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page,
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
    public ResponseEntity<?> update(@PathVariable UUID id,@Valid @RequestBody NewsDto newsDto){
        Apiresponse apiresponse = newsService.update(newsDto,id);
        return ResponseEntity.status(apiresponse.isSucces()?201:404).body(apiresponse);
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN','USER')")

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        Apiresponse apiresponse=newsService.delete(id);
        return ResponseEntity.status(apiresponse.isSucces()?200:404).body(apiresponse);
    }


    @PreAuthorize(value = "hasAuthority('USER')")
    @GetMapping("/getNewsUser")
    public ResponseEntity<?> getAllNewsUser(HttpServletRequest request,@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) throws ParseException {
        Apiresponse apiresponse=newsService.getUserNews(request,page,size);
        return ResponseEntity.ok().body(apiresponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
