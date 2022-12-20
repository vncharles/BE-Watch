package com.charles.website.model.dto;

import com.charles.website.domain.User;
//import com.charles.website.model.AbstractDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
public class RegisterDTO{
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String phoneNumber;

    private Set<String> role;

    public RegisterDTO(User user) {
        super();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.phoneNumber = user.getPhoneNumber();
    }

    public RegisterDTO() {
        super();
    }
}
