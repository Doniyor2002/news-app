package com.example.news_app.controller;

import com.example.news_app.dto.LoginDto;
import com.example.news_app.dto.RegistrDto;
import com.example.news_app.entity.Role;
import com.example.news_app.entity.User;
import com.example.news_app.exception.ResourceNotFoundException;
import com.example.news_app.repository.RoleRepository;
import com.example.news_app.repository.UserRepository;
import com.example.news_app.response.Apiresponse;
import com.example.news_app.security.JwtProvider;
import com.example.news_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public ResponseEntity<?> createToken(@Valid @RequestBody LoginDto loginDto){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        String token = jwtProvider.generateToken(loginDto.getUsername());
        return ResponseEntity.ok(token);
    }
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrDto registrDto){
        Apiresponse apiresponse = userService.registr(registrDto);
        return  ResponseEntity.status(apiresponse.isSucces()?201:404).body(apiresponse);
    }
    @PreAuthorize(value = "hasAuthority('MODERATOR')")
    @PostMapping("/addRoletoUser")
    public ResponseEntity<?> addRoletoUser(@RequestParam UUID id,@RequestBody List<Long> roleId){
        Apiresponse apiresponse = userService.addRole(id,roleId);
        return  ResponseEntity.status(apiresponse.isSucces()?201:404).body(apiresponse);
    }

    //validation ishlashi un metod
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
