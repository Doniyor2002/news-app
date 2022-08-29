package com.example.news_app.service;

import com.example.news_app.component.DateFormatter;
import com.example.news_app.dto.*;
import com.example.news_app.entity.Category;
import com.example.news_app.entity.News;
import com.example.news_app.entity.Role;
import com.example.news_app.entity.User;
import com.example.news_app.entity.enums.Roles;
import com.example.news_app.exception.ResourceNotFoundException;
import com.example.news_app.repository.CategoryRepository;
import com.example.news_app.repository.NewsRepository;
import com.example.news_app.repository.RoleRepository;
import com.example.news_app.repository.UserRepository;
import com.example.news_app.response.Apiresponse;
import com.example.news_app.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    public Apiresponse add(UserDto userDto) {
        User user=new User();
        user.setFullName(userDto.getFullName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhone(userDto.getUserName());
        user.setSalary(userDto.getSalary());
        List<Role> roles = roleRepository.findAllById(userDto.getRoleId());
        //////
        Set<Role> roleSet = new HashSet<>(roles);
        user.setRoles(roleSet);
        ///
        User save = userRepository.save(user);
        if (save!=null){
            return Apiresponse.builder().message("Added").succes(true).data(toDto(save)).build();
        }
        return Apiresponse.builder().message("Xatolik yuz berdi").succes(false).build();
    }

    public Apiresponse getAll(int page, int size) throws ParseException {
        Pageable pageable=PageRequest.of(page, size);
        Page<User> all=userRepository.findAll(pageable);
        return Apiresponse.builder().succes(true).message("News list").data(toDTOPage(all)).build();
    }
   public Apiresponse registr(RegistrDto registrDto) {
    User user=new User();
    Role role = roleRepository.findById(2L).orElseThrow(() -> new ResourceNotFoundException("Role", "id", 2));
    user.setPassword(passwordEncoder.encode(registrDto.getPassword()));
    user.setFullName(registrDto.getFullName());
    user.setPhone(registrDto.getPhone());
    user.setEmail(registrDto.getEmail());
    Set<Role> roleSet=new HashSet<>();
    roleSet.add(role);
    user.setRoles(roleSet);
    User save = userRepository.save(user);
    if (save!=null){
        return Apiresponse.builder().message("Added").succes(true).data(toDto(save)).build();
    }
    return Apiresponse.builder().message("Xatolik yuz berdi").succes(false).build();
}
    public Apiresponse addRole(UUID id, List<Long> roleId) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        List<Role> roles = roleRepository.findAllById(roleId);
        Set<Role> roleSet = user.getRoles();
        roleSet.addAll(roles);
        user.setRoles(roleSet);
        User save = userRepository.save(user);
        if (save!=null){
            return Apiresponse.builder().data(toDto(save)).message("Added role").succes(true).build();
        }
        return Apiresponse.builder().succes(false).message("Xatolik yuz berdi").build();
    }

    public String getPhonetoUser(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        return jwtProvider.getUsernameFromToken(token);
    }
    public ResUserDto toDto(User user){
        Set<String> set=new HashSet<>();
        for (Role role : user.getRoles()) {
            set.add(role.getName());
        }
        ResUserDto userDto=new ResUserDto();
        userDto.setPhone(user.getPhone());
        userDto.setSalary(user.getSalary());
        userDto.setFullName(user.getFullName());
        userDto.setRoles(set);
        return userDto;
    }
    public Page<ResUserDto> toDTOPage(Page<User> userPage) {
        List<ResUserDto> collect = userPage.stream().map(this::toDto).collect(Collectors.toList());
        return new PageImpl<>(collect);
    }
}
