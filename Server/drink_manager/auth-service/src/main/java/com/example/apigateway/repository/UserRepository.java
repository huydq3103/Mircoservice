package com.example.apigateway.repository;

import com.example.apigateway.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository quản lý các thao tác với thực thể {@link UserEntity}.
 * Lớp này mở rộng {@link JpaRepository} và cung cấp các phương thức
 * để truy xuất và kiểm tra sự tồn tại của người dùng trong cơ sở dữ liệu.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Tìm người dùng theo tên người dùng với thông tin về vai trò và quyền hạn.
     * Sử dụng {@link EntityGraph} để nạp cùng lúc thông tin vai trò và quyền hạn
     * của người dùng để tránh các truy vấn N+1.
     *
     * @param username tên người dùng của người dùng cần tìm
     * @return một {@link Optional} chứa {@link UserEntity} nếu tìm thấy người dùng,
     * ngược lại là {@link Optional#empty()}
     */
    @Transactional
    Optional<UserEntity> findByUsername(String username);

    /**
     * Kiểm tra sự tồn tại của người dùng theo tên người dùng.
     *
     * @param username tên người dùng cần kiểm tra
     * @return true nếu người dùng tồn tại, false nếu không
     */
    boolean existsByUsername(String username);
}
