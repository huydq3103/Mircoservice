package com.example.apigateway.services.implement;


import com.example.apigateway.entity.PermissionEntity;
import com.example.apigateway.repository.PermissionRepository;
import com.example.apigateway.services.IPermissionService;
import com.example.apigateway.until.validate.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    private PermissionRepository permissionRepository;


    @Override
    public boolean existsByName(String name) {
        ValidationUtils.validateString(name);
        if (permissionRepository.existsByName(name)) {
            return true;
        }
        return false;
    }

    @Override
    public Optional<PermissionEntity> findByName(String name) {
        ValidationUtils.validateString(name);

        Optional<PermissionEntity> permission = permissionRepository.findByName(name);

        if (Objects.isNull(permission)) {
            new IllegalArgumentException("Khong tim thay quyen");
        }

        return permission;
    }

    @Override
    public List<PermissionEntity> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @Override
    public void savePermissions(PermissionEntity permissions) {
        permissionRepository.save(permissions);
    }
}
