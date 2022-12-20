package com.charles.website.service;

import com.charles.website.domain.User;
import com.charles.website.model.dto.JwtResponse;
import com.charles.website.model.dto.RegisterDTO;
import com.charles.website.model.dto.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserResponse> getListUser();

    User getUserDetail(Long id);

    JwtResponse loginAccount(String username, String password);

    void createAccount(RegisterDTO registerDTO);
}
