package com.example.drinkservice.until.constants;

/**
 * Định nghĩa các hằng số tên bảng và cột liên quan đến các thực thể trong cơ sở dữ liệu.
 * Các hằng số này được sử dụng để tránh việc hard-code tên bảng và cột trong mã nguồn.
 */
public class EntityConstant {

    // Tên bảng cho thực thể Topping
    public static final String TOPPING_NAME_TABLE = "topping";

    // Tên bảng cho thực thể Drink
    public static final String DRINK_NAME_TABLE = "drink";

    // Tên cột ID cho thực thể Drink
    public static final String DRINK_ENTITY_ID = "drink_id";

    // Tên cột ID cho thực thể Topping
    public static final String TOPPING_ID = "topping_id";

    // Tên bảng liên kết giữa Drink và Topping
    public static final String DRINK_TOPPING_TABLE = "drink_topping";
}
