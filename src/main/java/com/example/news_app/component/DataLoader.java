package com.example.news_app.component;

import com.example.news_app.entity.Role;
import com.example.news_app.entity.User;
import com.example.news_app.entity.enums.Roles;
import com.example.news_app.repository.RoleRepository;
import com.example.news_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor


public class DataLoader implements CommandLineRunner {
    @Value("${spring.sql.init.mode}")
    String result;
    private final UserRepository userResository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        if (result.equals("always")){
            Role admin = roleRepository.save(new Role(1L, "ADMIN",true));
            Role user = roleRepository.save(new Role(2L, "USER",true));
            Role moderator = roleRepository.save(new Role(3L, "MODERATOR",true));
            Set<Role> set=new HashSet<>();
            set.add(admin);
            Set<Role> set1=new HashSet<>();
            set1.add(user);
            Set<Role> set2=new HashSet<>();
            set2.add(moderator);

            userResository.save(new User("Doniyor", "+998936330207", set, passwordEncoder.encode("111")));
            userResository.save(new User("Ilhom","+998901112233",set1, passwordEncoder.encode("222")));
            userResository.save(new User("Bekzod","+98971306005",set1, passwordEncoder.encode("444")));
            userResository.save(new User("Azizbek","+998912020702",set2, passwordEncoder.encode("333")));


        }
    }
}
