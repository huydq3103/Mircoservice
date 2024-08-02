package com.example.apigateway.until.constants;

/**
 * Định nghĩa các hằng số liên quan đến cấu hình MessageSource trong ứng dụng.
 * Các hằng số này bao gồm các tên file và tham số cần thiết cho việc xử lý ngôn ngữ.
 */
public class MessageSourceConstant {

    // Mã hóa mặc định cho các tệp tin message
    public static final String DEFAULT_UTF8 = "UTF-8";

    // Danh sách các tên file nguồn tin cậy cho MessageSource
    public static final String[] BASE_NAMES = {
            "messages/validation_messages",
            "messages/message"
    };

    // Tên tham số cho thay đổi ngôn ngữ
    public static final String PARAM_NAME_LANG = "lang";
}
