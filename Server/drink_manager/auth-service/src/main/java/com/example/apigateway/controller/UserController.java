package com.example.apigateway.controller;

import com.example.apigateway.dto.UserDTO;
import com.example.apigateway.jwt.JwtUtil;
import com.example.apigateway.ldap.LdapAuthenticationService;
import com.example.apigateway.payload.request.LoginRequest;
import com.example.apigateway.payload.request.SignUpRequest;
import com.example.apigateway.payload.response.AuthResponse;
import com.example.apigateway.services.implement.CustomUserDetailsService;
import com.example.apigateway.services.implement.UserServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Controller quản lý các API liên quan đến người dùng (đăng ký và đăng nhập).
 */
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * API để đăng ký người dùng mới.
     *
     * @param signUpRequest Đối tượng DTO chứa thông tin người dùng để đăng ký.
     * @return ResponseEntity chứa thông tin về người dùng đã đăng ký và HttpStatus.OK nếu thành công.
     */
    @Operation(summary = "Register new user", description = "API để đăng ký người dùng mới")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(
            @Parameter(description = "Đối tượng DTO chứa thông tin người dùng để đăng ký", required = true)
           @Valid @RequestBody SignUpRequest signUpRequest) {
        UserDTO userDTO = userService.register(signUpRequest);
        return ResponseEntity.ok(userDTO);
    }

    /**
     * API để xác thực người dùng và cấp token JWT.
     *
     * @param loginRequest Đối tượng DTO chứa thông tin đăng nhập của người dùng.
     * @return ResponseEntity chứa JWT token và HttpStatus.OK nếu thành công.
     */
    @Operation(summary = "Authenticate user and generate JWT", description = "API để xác thực người dùng và cấp token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful and JWT token generated"),
            @ApiResponse(responseCode = "400", description = "Username or password incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(
            @Parameter(description = "Đối tượng DTO chứa thông tin đăng nhập của người dùng", required = true)
            @Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.authenticateUser(loginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, null);
        return ResponseEntity.ok("Logout successful");
    }



    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private LdapAuthenticationService ldapAuthenticationService;

    @GetMapping("/validateToken")
    public ResponseEntity<AuthResponse> validateToken(@RequestHeader("Authorization") String token) {
        log.info("Received token for validation: " + token);
        String jwt = token.substring(7); // Bỏ tiền tố "Bearer "
        log.info("Extracted JWT: " + jwt);
        String username = jwtUtil.extractUsername(jwt);
        boolean isLdap = jwtUtil.extractLdap(jwt);

        UserDetails userDetails;

        if (isLdap) {
            userDetails = ldapAuthenticationService.loadLdapUserDetails(username);
        } else {
            userDetails = customUserDetailsService.loadUserByUsername(username);
        }

        if (jwtUtil.validateToken(jwt)) {
            AuthResponse response = new AuthResponse();
            response.setRoles(userDetails.getAuthorities().stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .collect(Collectors.toList()));
            response.setPermissions(new ArrayList<>()); // Bạn có thể thêm quyền cụ thể nếu cần

            return ResponseEntity.ok(response);
        } else {
            log.warn("Token validation failed for token: " + jwt);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }




}
