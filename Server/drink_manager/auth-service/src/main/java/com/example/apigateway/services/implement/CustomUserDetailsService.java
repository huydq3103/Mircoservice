package com.example.apigateway.services.implement;

import com.example.apigateway.entity.UserEntity;
import com.example.apigateway.exception.NotFoundException;
import com.example.apigateway.repository.UserRepository;
import com.example.apigateway.until.validate.ValidationUtils;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Triển khai dịch vụ để tải thông tin người dùng.
 * Lớp này thực hiện giao diện {@link UserDetailsService}
 * và được sử dụng bởi Spring Security để truy xuất thông tin người dùng
 * trong quá trình xác thực.
 */
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Tải thông tin người dùng từ cơ sở dữ liệu theo tên người dùng.
     * Phương thức này được Spring Security sử dụng để truy xuất thông tin người dùng
     * và thực hiện xác thực cũng như phân quyền.
     *
     * @param username tên người dùng của người dùng cần tải
     * @return đối tượng {@link UserDetails} chứa thông tin người dùng
     * @throws UsernameNotFoundException nếu không tìm thấy người dùng trong cơ sở dữ liệu
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {

        ValidationUtils.validateString(username);

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng"));


        log.info("Đang tải người dùng: {}", username);
        user.getRoles()
                .forEach(role -> log.info("Vai trò: {}", role.toString()));

        return user;
    }
}
