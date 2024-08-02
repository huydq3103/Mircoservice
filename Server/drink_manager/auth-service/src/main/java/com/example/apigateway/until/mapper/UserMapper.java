package com.example.apigateway.until.mapper;


import com.example.apigateway.dto.PermissionDTO;
import com.example.apigateway.dto.RoleDTO;
import com.example.apigateway.dto.UserDTO;
import com.example.apigateway.entity.PermissionEntity;
import com.example.apigateway.entity.RoleEntity;
import com.example.apigateway.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    /**
     * Chuyển đổi từ UserEntity thành UserDTO.
     *
     * @param user Entity cần chuyển đổi.
     * @return DTO kết quả.
     */
    public static UserDTO ConvertEntityToDTO(UserEntity user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // Có thể loại bỏ nếu không cần hiển thị mật khẩu.
                .permissions(user.getRoles().stream()
                        .flatMap(role -> role.getPermissionss().stream())
                        .map(UserMapper::convertPermissionEntityToDTO)
                        .collect(Collectors.toSet()))
                .roles(user.getRoles().stream()
                        .map(UserMapper::convertRoleEntityToDTO)
                        .collect(Collectors.toSet()))
                .build();
    }

    /**
     * Chuyển đổi từ UserDTO thành UserEntity.
     *
     * @param userDTO DTO cần chuyển đổi.
     * @return Entity kết quả.
     */
    public static UserEntity ConvertDTOToEntity(UserDTO userDTO) {
        return UserEntity.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .roles(userDTO.getRoles().stream()
                        .map(UserMapper::convertRoleDTOToEntity)
                        .collect(Collectors.toSet()))
                .build();
    }

    private static PermissionDTO convertPermissionEntityToDTO(PermissionEntity permissionEntity) {
        return PermissionDTO.builder()
                .id(permissionEntity.getId())
                .name(permissionEntity.getName())
                .url(permissionEntity.getUrl())
                .method(permissionEntity.getMethod())
                .build();
    }

    private static PermissionEntity convertPermissionDTOToEntity(PermissionDTO permissionDTO) {
        return PermissionEntity.builder()
                .id(permissionDTO.getId())
                .name(permissionDTO.getName())
                .url(permissionDTO.getUrl())
                .method(permissionDTO.getMethod())
                .build();
    }

    private static RoleDTO convertRoleEntityToDTO(RoleEntity roleEntity) {
        return RoleDTO.builder()
                .id(roleEntity.getId())
                .name(roleEntity.getName())
                .permissions(roleEntity.getPermissionss().stream()
                        .map(UserMapper::convertPermissionEntityToDTO)
                        .collect(Collectors.toSet()))
                .build();
    }

    private static RoleEntity convertRoleDTOToEntity(RoleDTO roleDTO) {
        return RoleEntity.builder()
                .id(roleDTO.getId())
                .name(roleDTO.getName())
                .permissionss(roleDTO.getPermissions().stream()
                        .map(UserMapper::convertPermissionDTOToEntity)
                        .collect(Collectors.toSet()))
                .build();
    }
}
