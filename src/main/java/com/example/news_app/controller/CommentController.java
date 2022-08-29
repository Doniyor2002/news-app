package com.example.news_app.controller;

import com.example.news_app.dto.CommentDto;
import com.example.news_app.response.Apiresponse;
import com.example.news_app.service.CommentService;
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
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PreAuthorize(value = "hasAuthority('USER')")
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody CommentDto commentDto){

        Apiresponse apiresponse = commentService.add(commentDto);
        return ResponseEntity.status(apiresponse.isSucces()?201:404).body(apiresponse);
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MODERATOR')")
    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "") String name,
                                    @RequestParam(defaultValue = "") String date) throws ParseException {
        Apiresponse apiresponse=commentService.getAll(page,size,name,date);
        return ResponseEntity.ok().body(apiresponse);
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable UUID id){
        Apiresponse apiresponse=commentService.getOne(id);
        return ResponseEntity.ok().body(apiresponse);
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id,@Valid @RequestBody CommentDto commentDto){
        Apiresponse apiresponse = commentService.update(commentDto,id);
        return ResponseEntity.status(apiresponse.isSucces()?201:404).body(apiresponse);
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN','USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        Apiresponse apiresponse=commentService.delete(id);
        return ResponseEntity.status(apiresponse.isSucces()?200:404).body(apiresponse);
    }

    @GetMapping("/getUserComment")
    public ResponseEntity<?> getAllUserComment(HttpServletRequest request,@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) throws ParseException {
        Apiresponse apiresponse=commentService.getUserComment(request,page,size);
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
