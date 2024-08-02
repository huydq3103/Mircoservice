package com.example.apigateway.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Phản hồi chứa thông tin JWT (JSON Web Token) để trả về cho client sau khi xác thực thành công.
 *
 * Đây là lớp DTO dùng để gửi thông tin về token JWT cho client.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    /**
     * Token JWT được cấp cho người dùng sau khi xác thực thành công.
     */
    private String token;

    /**
     * Loại token, mặc định là "Bearer".
     */
    private String type = "Bearer";

    /**
     * Constructor dùng để khởi tạo {@link JwtResponse} với token JWT.
     *
     * @param accessToken Token JWT được cấp cho người dùng.
     */
    public JwtResponse(String accessToken) {
        this.token = accessToken;
    }
}
