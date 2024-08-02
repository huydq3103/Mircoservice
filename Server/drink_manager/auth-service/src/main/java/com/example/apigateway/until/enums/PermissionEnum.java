package com.example.apigateway.until.enums;

import com.example.apigateway.until.constants.PermissionConstants;
import lombok.Getter;

/**
 * Enumeration representing various permissions in the application.
 * Each permission includes a name, a URL, and an HTTP method associated with it.
 */
@Getter
public enum PermissionEnum {


    READ_DRINK(PermissionConstants.PERMISSION_READ_DRINK, PermissionConstants.URL_READ_DRINK, PermissionConstants.METHOD_GET),
    WRITE_DRINK(PermissionConstants.PERMISSION_WRITE_DRINK, PermissionConstants.URL_WRITE_DRINK, PermissionConstants.METHOD_POST),
    UPDATE_DRINK(PermissionConstants.PERMISSION_UPDATE_DRINK, PermissionConstants.URL_UPDATE_DRINK, PermissionConstants.METHOD_PUT),
    DELETE_DRINK(PermissionConstants.PERMISSION_DELETE_DRINK, PermissionConstants.URL_DELETE_DRINK, PermissionConstants.METHOD_DELETE),

    VIEW_ROLES(PermissionConstants.PERMISSION_VIEW_ROLES, PermissionConstants.URL_GET_ALL_ROLES, PermissionConstants.METHOD_GET),
    VIEW_PERMISSIONS(PermissionConstants.PERMISSION_VIEW_PERMISSIONS,PermissionConstants.URL_GET_ALL_PERMISSIONS, PermissionConstants.METHOD_GET),
    DELETE_PERMISSIONS_FROM_ROLE(PermissionConstants.PERMISSION_DELETE_PERMISSIONS_FROM_ROLES,PermissionConstants.URL_DELETE_PERMISSION_FROM_ROLE,PermissionConstants.METHOD_DELETE),
    ADD_PERMISSIONS_FROM_ROLE(PermissionConstants.PERMISSION_ADD_PERMISSIONS_TO_ROLE,PermissionConstants.URL_ADD_PERMISSION_TO_ROLE,PermissionConstants.METHOD_POST)
    ;


    private final String name;
    private final String url;
    private final String method;

    /**
     * Constructor to initialize a permission with a name, URL, and method.
     *
     * @param name   the name of the permission
     * @param url    the URL associated with the permission
     * @param method the HTTP method associated with the permission
     */
    PermissionEnum(String name, String url, String method) {
        this.name = name;
        this.url = url;
        this.method = method;
    }


}