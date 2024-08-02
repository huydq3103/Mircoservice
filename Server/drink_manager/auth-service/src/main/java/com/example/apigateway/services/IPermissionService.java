package com.example.apigateway.services;


import com.example.apigateway.entity.PermissionEntity;

import java.util.List;
import java.util.Optional;

public interface IPermissionService {

    boolean existsByName(String name);

    Optional<PermissionEntity> findByName(String name);

    List<PermissionEntity> getAllPermissions();

    void savePermissions(PermissionEntity permissions);

}

