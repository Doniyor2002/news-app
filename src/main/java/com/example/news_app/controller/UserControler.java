package com.example.news_app.controller;

import com.example.news_app.dto.UserDto;
import com.example.news_app.response.Apiresponse;
import com.example.news_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserControler {
    private final UserService userService;

    @PreAuthorize(value = "hasAuthority('MANAGER')")
    @PostMapping
    public ResponseEntity<?> add( @RequestBody UserDto userDto){

        Apiresponse apiresponse = userService.add(userDto);
        return ResponseEntity.status(apiresponse.isSucces()?201:404).body(apiresponse);
    }

//    @PreAuthorize(value = "hasAuthority('ADMIN')")
//   @GetMapping()
//    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page,
//                                     @RequestParam(defaultValue = "10") int size,
//                                     @RequestParam(defaultValue = "") String name,
//                                     @RequestParam(defaultValue = "") String categoryName,
//                                     @RequestParam(defaultValue = "") String date) throws ParseException {
//        Apiresponse apiresponse=newsService.getAll(page,size,name,categoryName,date);
//        return ResponseEntity.ok().body(apiresponse);
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getOne(@PathVariable UUID id){
//        Apiresponse apiresponse=newsService.getOne(id);
//        return ResponseEntity.ok().body(apiresponse);
//    }

}
