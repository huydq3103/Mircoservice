package com.example.apigateway.services.implement;

import com.example.apigateway.entity.PermissionEntity;
import com.example.apigateway.entity.RoleEntity;
import com.example.apigateway.exception.NotFoundException;
import com.example.apigateway.repository.RoleRepository;
import com.example.apigateway.services.IRoleService;
import com.example.apigateway.until.validate.ValidationUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionServiceImpl permissionService;

    @Override
    public boolean existsByName(String name) {
        ValidationUtils.validateString(name);

        if (roleRepository.existsByName(name)) {
            return true;
        }

        return false;
    }

    @Override
    public RoleEntity saveRole(RoleEntity roleEntity) {
       return roleRepository.save(roleEntity);
    }

    @Override
    public Optional<RoleEntity> findByName(String name) {
        ValidationUtils.validateString(name);
        return roleRepository.findByName(name);
    }

    @Override
    @Transactional
    public void addPermissionToRole(String roleName, String permissionName) {
        ValidationUtils.validateString(roleName);
        ValidationUtils.validateString(permissionName);

        RoleEntity roleEntity = roleRepository.findByName(roleName)
                .orElseThrow(() -> new NotFoundException("Role not found: " + roleName));

        PermissionEntity permissionEntity = permissionService.findByName(permissionName)
                .orElseThrow(() -> new NotFoundException("Permission not found: " + permissionName));

        roleEntity.getPermissionss().add(permissionEntity);
        roleRepository.save(roleEntity);
    }

    @Override
    @Transactional
    public void removePermissionFromRole(String roleName, String permissionName) {
        ValidationUtils.validateString(roleName);
        ValidationUtils.validateString(permissionName);
        RoleEntity roleEntity = roleRepository.findByName(roleName)
                .orElseThrow(() -> new NotFoundException("Role not found: " + roleName));

        PermissionEntity permissionEntity = permissionService.findByName(permissionName)
                .orElseThrow(() -> new NotFoundException("Permission not found: " + permissionName));

        roleEntity.getPermissionss().remove(permissionEntity);
        roleRepository.save(roleEntity);
    }

    @Override
    public List<RoleEntity> findALl() {
        return roleRepository.findAll();
    }
}
