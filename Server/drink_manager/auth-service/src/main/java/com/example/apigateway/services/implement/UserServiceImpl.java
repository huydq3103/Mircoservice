package com.example.apigateway.services.implement;


import com.example.apigateway.dto.UserDTO;
import com.example.apigateway.entity.RoleEntity;
import com.example.apigateway.entity.UserEntity;
import com.example.apigateway.jwt.JwtUtil;
import com.example.apigateway.ldap.LdapAuthenticationService;
import com.example.apigateway.payload.request.LoginRequest;
import com.example.apigateway.payload.request.SignUpRequest;
import com.example.apigateway.payload.response.JwtResponse;
import com.example.apigateway.repository.RoleRepository;
import com.example.apigateway.repository.UserRepository;
import com.example.apigateway.services.IUserService;
import com.example.apigateway.until.mapper.UserMapper;
import com.example.apigateway.until.validate.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Class: UserServiceImpl
 * Author: ACER
 * Date: 7/23/2024
 * Description: [Your description here]
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


    /**
     * Đăng ký người dùng mới.
     *
     * @param signUpRequest yêu cầu đăng ký người dùng.
     * @return thông tin người dùng sau khi đăng ký.
     */
    @Override
    public UserDTO register(SignUpRequest signUpRequest) {

        ValidationUtils.validateObject(signUpRequest);

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        UserEntity user = new UserEntity();

        // Gán role mặc định cho user mới
        RoleEntity userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));


        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);

        return UserMapper.ConvertEntityToDTO(user);
    }

    /**
     * Xác thực người dùng và tạo token JWT.
     *
     * @param loginRequest yêu cầu đăng nhập của người dùng.
     * @return phản hồi chứa token JWT.
     */

    @Autowired
    private LdapAuthenticationService ldapAuthenticationService;

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        ValidationUtils.validateObject(loginRequest);
        boolean isLdap = false;
        UserDetails userDetails;

        // them moi
        if (loginRequest.isLdap()) {
            // Xác thực LDAP
            userDetails = ldapAuthenticationService.authenticateLdapUser(
                    loginRequest.getUsername(), loginRequest.getPassword());
            isLdap = true;
        } else {
            // Xác thực thông thường
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            userDetails = (UserDetails) authentication.getPrincipal();
        }

        // Tạo JWT token
        String jwt = jwtUtil.generateToken(userDetails, isLdap);

        // Đặt xác thực vào SecurityContext
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        return new JwtResponse(jwt);
    }
}
