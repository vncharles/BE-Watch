package com.charles.website.model.dto;

import com.charles.website.domain.User;
//import com.charles.website.model.AbstractDTO;
import lombok.*;

@Getter @Setter
public class UserResponse{
    private String username;
    private String email;
    private String phoneNumber;
    private String fullName;

    public UserResponse() {
    }

    public UserResponse(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.fullName = user.getFullName();
    }
}
