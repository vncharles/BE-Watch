package com.charles.website.model.dto;

import com.charles.website.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginDTO {
    private String username;
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
