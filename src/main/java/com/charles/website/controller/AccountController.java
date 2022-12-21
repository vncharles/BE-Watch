package com.charles.website.controller;

import com.charles.website.model.MessageResponse;
import com.charles.website.model.dto.JwtResponse;
import com.charles.website.model.dto.LoginDTO;
import com.charles.website.model.dto.RegisterDTO;
import com.charles.website.model.dto.UserResponse;
import com.charles.website.security.impl.UserDetailsImpl;
import com.charles.website.security.jwt.JwtUtils;
import com.charles.website.service.UserService;
import com.charles.website.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private UserDetailsServiceImpl userDetailService;

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@Validated @RequestBody LoginDTO loginDTO) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
//
//        if(authentication != null){
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            String jwt = jwtUtils.generateJwtToken(authentication);
//
//            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//            List<String> roles = userDetails.getAuthorities().stream()
//                    .map(item -> item.getAuthority())
//                    .collect(Collectors.toList());
//
//            return ResponseEntity.ok(new JwtResponse(jwt,
//                    userDetails.getId(),
//                    userDetails.getUsername(),
//                    userDetails.getEmail(),
//                    roles));
//        }
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error Message!");
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody Map<String, String> login) {
        String username = login.get("username");
        String password = login.get("password");
        return ResponseEntity.ok(userService.loginAccount(username, password));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody RegisterDTO registerDTO){
        userService.createAccount(registerDTO);
        return ResponseEntity.ok(new MessageResponse("Đăng kí thành công"));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> listUser(){
        List<UserResponse> listUserResponse = userService.getListUser();

        return ResponseEntity.ok(listUserResponse);
    }
}
