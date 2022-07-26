package com.example.news_app.entity;

import com.example.news_app.entity.enums.Roles;
import com.example.news_app.entity.template.AbsEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.xml.catalog.Catalog;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User extends AbsEntity implements UserDetails {
    private String fullName;
    private String phone;
    private Double salary;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE,mappedBy = "user")
    private List<Comment> comments;
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "user",cascade = CascadeType.REMOVE)
    private List<News> news;


    private String password;
    private boolean accountNonExpired=true;//account vaqti o'tmaganmi
    private boolean accountNonLocked=true;//bloklanmaganmi
    private boolean credentialsNonExpired=true;//parol o'zinikimi
    private boolean enabled=true;//tizimda kimdir kirsa foydalanish huquqi

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list=new ArrayList<>();
        for (Role role1 : this.roles) {
            list.add(new SimpleGrantedAuthority(role1.getName()));
        }
        return list;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public User(String fullName, String phone, Set<Role> roles, String password) {
        this.fullName = fullName;
        this.phone = phone;
        this.roles = roles;
        this.password = password;
    }
}
