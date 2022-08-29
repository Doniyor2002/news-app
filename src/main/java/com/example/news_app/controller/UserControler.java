package com.example.news_app.controller;

import com.example.news_app.dto.UserDto;
import com.example.news_app.response.Apiresponse;
import com.example.news_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserControler {
    private final UserService userService;

    @PreAuthorize(value = "hasAuthority('MODERATOR')")
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody UserDto userDto){

        Apiresponse apiresponse = userService.add(userDto);
        return ResponseEntity.status(apiresponse.isSucces()?201:404).body(apiresponse);
    }

    @PreAuthorize(value = "hasAuthority('MODERATOR')")
   @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) throws ParseException {
        Apiresponse apiresponse=userService.getAll(page,size);
        return ResponseEntity.ok().body(apiresponse);
    }

}
