package com.example.apigateway.constant;

public class UrlMappingConstants {

    // Drink Service URL Mappings
    public static final String DRINK_SERVICE = "drink-service";
    public static final String CREATE_DRINK_ORIGINAL = "/api/drink/create-drink";
    public static final String CREATE_DRINK_MAPPED = "/create";
    public static final String GET_ALL_DRINK_ORIGINAL = "/api/drinks/get-all-drink";
    public static final String GET_ALL_DRINK_MAPPED = "/get-all";

    // Topping Service URL Mappings
    public static final String TOPPING_SERVICE = "topping-service";
    public static final String GET_ALL_TOPPINGS_ORIGINAL = "/api/toppings/get-all-topping";
    public static final String GET_ALL_TOPPINGS_MAPPED = "/get-all-topping";

    // Auth Service URL Mappings
    public static final String AUTH_SERVICE = "auth-service";
    public static final String AUTH_LOGIN_ORIGINAL = "/api/auth/login";
    public static final String AUTH_LOGIN_MAPPED = "/login";
    public static final String AUTH_VALIDATE_ORIGINAL = "/api/auth/validate";
    public static final String AUTH_VALIDATE_MAPPED = "/validate";

    // Load Balancer URL
    public static final String LOAD_BALANCER = "lb://";

    // Define permissions
    public static final String PERMISSION_CREATE_DRINK = "PERMISSION_CREATE_DRINK";
    public static final String PERMISSION_READ_DRINK = "PERMISSION_READ_DRINK";
    public static final String PERMISSION_VIEW_ALL_TOPPINGS = "PERMISSION_VIEW_ALL_TOPPINGS";
}
