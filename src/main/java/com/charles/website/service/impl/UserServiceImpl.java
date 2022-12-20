package com.charles.website.service.impl;

import com.charles.website.domain.ERole;
import com.charles.website.domain.Role;
import com.charles.website.domain.User;
import com.charles.website.exception.BadRequestException;
import com.charles.website.exception.NotFoundException;
import com.charles.website.model.dto.JwtResponse;
import com.charles.website.model.dto.RegisterDTO;
import com.charles.website.model.dto.UserResponse;
import com.charles.website.repository.RoleRepository;
import com.charles.website.repository.UserRepository;
import com.charles.website.security.impl.UserDetailsImpl;
import com.charles.website.security.jwt.JwtUtils;
import com.charles.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public List<UserResponse> getListUser() {
        List<User> listUser = userRepository.findAll();
        List<UserResponse> listUserResponse = new ArrayList<>();
        for(User user: listUser){
            listUserResponse.add(new UserResponse(user));
        }
        return listUserResponse;
    }

    @Override
    public User getUserDetail(Long id) {
        User user = null;
        try {
            user = userRepository.findById(id).get();
        } catch (Exception ex) {ex.printStackTrace();}
        if(user==null) throw new NotFoundException(404, "User is not found");

        return null;
    }

    @Override
    public JwtResponse loginAccount(String username, String password) {
        if (!userRepository.existsByUsername(username)) {
            throw new NotFoundException(404, "User has not existed");
        }

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
        } catch (Exception ex) {
            throw new BadRequestException(400, "wrong password");
        }
    }

    @Override
    public void createAccount(RegisterDTO registerDTO) {
        if(userRepository.existsByUsername(registerDTO.getUsername())){
            throw new NotFoundException(404, "Error: Username is already in use!");
        }

        if(userRepository.existsByEmail(registerDTO.getEmail())){
            throw new NotFoundException(404, "Error: Email is already in use!");
        }

        User user = new User(registerDTO.getEmail(), registerDTO.getPhoneNumber(), registerDTO.getUsername(), encoder.encode(registerDTO.getPassword()),
                registerDTO.getFullName());

        Set<String> strRoles = registerDTO.getRole();
        Set<Role> roles = new HashSet<>();

        // Nếu user bth không có set role thì set thành ROLE_USER
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new NotFoundException(404, "Error: Role is not found"));
            roles.add(userRole);
//            Cart cart = new Cart();
//            cart.setUser(user);
//            user.setCart(cart);
        } else { // Ngược lại thì mình set role có thể là admin hoặc là seller cho họ
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new NotFoundException(404, "Error: Role is not found"));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new NotFoundException(404, "Error: Role is not found"));
                        roles.add(userRole);
//                        Cart cart = new Cart();
//                        cart.setUser(user);
//                        user.setCart(cart);
                        break;
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
    }
}
