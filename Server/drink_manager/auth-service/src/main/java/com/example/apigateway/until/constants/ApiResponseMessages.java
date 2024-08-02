package com.example.apigateway.until.constants;

public class ApiResponseMessages {

    // Success responses
    public static final String CREATED = "201";
    public static final String OK = "200";

    // Client error responses
    public static final String BAD_REQUEST = "400";
    public static final String NOT_FOUND = "404";

    // Server error responses
    public static final String INTERNAL_SERVER_ERROR = "500";

    // Descriptions
    public static final String CREATED_DESC = "Đã thêm mới thành công";
    public static final String OK_DESC = "Thành công";
    public static final String BAD_REQUEST_DESC = "Yêu cầu không hợp lệ";
    public static final String NOT_FOUND_DESC = "Không tìm thấy";
    public static final String INTERNAL_SERVER_ERROR_DESC = "Lỗi server";
}
