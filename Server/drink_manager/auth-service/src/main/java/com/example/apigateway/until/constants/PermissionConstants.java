package com.example.apigateway.until.constants;

public class PermissionConstants {
    // Constants for permissions
    public static final String PERMISSION_READ_DRINK = "PERMISSION_READ_DRINK";
    public static final String PERMISSION_WRITE_DRINK = "PERMISSION_WRITE_DRINK";
    public static final String PERMISSION_UPDATE_DRINK = "PERMISSION_UPDATE_DRINK";
    public static final String PERMISSION_DELETE_DRINK = "PERMISSION_DELETE_DRINK";

    public static final String PERMISSION_VIEW_ROLES = "PERMISSION_VIEW_ROLES";
    public static final String PERMISSION_VIEW_PERMISSIONS = "PERMISSION_VIEW_PERMISSIONS";
    public static final String PERMISSION_DELETE_PERMISSIONS_FROM_ROLES = "PERMISSION_DELETE_PERMISSIONS_FROM_ROLES";
    public static final String PERMISSION_ADD_PERMISSIONS_TO_ROLE = "PERMISSION_ADD_PERMISSIONS_TO_ROLE";

    // Constants for URLs
    public static final String URL_READ_DRINK = "/api/get-all-drink";
    public static final String URL_WRITE_DRINK = "/api/create-drink";
    public static final String URL_UPDATE_DRINK = "/api/update-drink";
    public static final String URL_DELETE_DRINK = "/api/delete-drink";
    public static final String URL_GET_ALL_ROLES = "/api/roles/get-all-roles";
    public static final String URL_GET_ALL_PERMISSIONS = "/api/roles/get-all-permissions";

    public static final String URL_ADD_PERMISSION_TO_ROLE = "/api/roles/{roleName}/permissions";
    public static final String URL_DELETE_PERMISSION_FROM_ROLE = "/api/roles/{roleName}/permissions/{permissionName}";


    // Constants for HTTP methods
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";





}
