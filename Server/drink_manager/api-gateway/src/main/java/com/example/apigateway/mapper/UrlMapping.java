package com.example.apigateway.mapper;

import com.example.apigateway.constant.UrlMappingConstants;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum UrlMapping {

    // Ánh xạ cho drink-service
    DRINK_CREATE(
            UrlMappingConstants.DRINK_SERVICE,
            UrlMappingConstants.CREATE_DRINK_ORIGINAL,
            UrlMappingConstants.CREATE_DRINK_MAPPED,
            UrlMappingConstants.PERMISSION_CREATE_DRINK
    ),

    DRINK_GET_ALL(
            UrlMappingConstants.DRINK_SERVICE,
            UrlMappingConstants.GET_ALL_DRINK_ORIGINAL,
            UrlMappingConstants.GET_ALL_DRINK_MAPPED,
            UrlMappingConstants.PERMISSION_READ_DRINK
    ),

    TOPPING_GET_ALL(
            UrlMappingConstants.TOPPING_SERVICE,
            UrlMappingConstants.GET_ALL_TOPPINGS_ORIGINAL,
            UrlMappingConstants.GET_ALL_TOPPINGS_MAPPED,
            UrlMappingConstants.PERMISSION_VIEW_ALL_TOPPINGS
    ),

    AUTH_LOGIN(
            UrlMappingConstants.AUTH_SERVICE,
            UrlMappingConstants.AUTH_LOGIN_ORIGINAL,
            UrlMappingConstants.AUTH_LOGIN_MAPPED,
            "" // AUTH_LOGIN không cần quyền cụ thể
    );

    private final String serviceName;
    private final String originalUrl;
    private final String mappedUrl;
    private final String permission;

    UrlMapping(String serviceName, String originalUrl, String mappedUrl, String permission) {
        this.serviceName = serviceName;
        this.originalUrl = originalUrl;
        this.mappedUrl = mappedUrl;
        this.permission = permission;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getMappedUrl() {
        return mappedUrl;
    }

    public String getPermission() {
        return permission;
    }

    public static String getOriginalUrlByMappedUrl(String mappedUrl) {
        for (UrlMapping mapping : values()) {
            if (mapping.getMappedUrl().equals(mappedUrl)) {
                return mapping.getOriginalUrl();
            }
        }
        return null;
    }

    public static List<UrlMapping> getAllMappings() {
        return Arrays.asList(values());
    }

    public static List<String> getAllOriginalUrls() {
        return Arrays.stream(values())
                .map(UrlMapping::getOriginalUrl)
                .collect(Collectors.toList());
    }

    public static List<String> getAllMappedUrls() {
        return Arrays.stream(values())
                .map(UrlMapping::getMappedUrl)
                .collect(Collectors.toList());
    }

    public static List<String> getAllPermissions() {
        return Arrays.stream(values())
                .map(UrlMapping::getPermission)
                .collect(Collectors.toList());
    }
}
