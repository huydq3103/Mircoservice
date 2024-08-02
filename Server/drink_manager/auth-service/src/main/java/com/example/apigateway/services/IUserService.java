package com.example.apigateway.services;

import com.example.apigateway.dto.UserDTO;
import com.example.apigateway.payload.request.LoginRequest;
import com.example.apigateway.payload.request.SignUpRequest;
import com.example.apigateway.payload.response.JwtResponse;


public interface IUserService {

    /**
     * Đăng ký người dùng mới.
     *
     * @param signUpRequest yêu cầu đăng ký người dùng.
     * @return thông tin người dùng sau khi đăng ký.
     */
    UserDTO register(SignUpRequest signUpRequest);

    /**
     * Xác thực người dùng và tạo token JWT.
     *
     * @param loginRequest yêu cầu đăng nhập của người dùng.
     * @return phản hồi chứa token JWT.
     */
    JwtResponse authenticateUser(LoginRequest loginRequest);
}
