package com.example.toppingservice.until.constants;

/**
 * Định nghĩa các hằng số cho các thông điệp thông báo lỗi trong quá trình validation.
 * Các hằng số này được sử dụng để xác định các key cho các thông điệp lỗi trong các trường hợp kiểm tra hợp lệ.
 */
public class ValidateConstant {

    // Các thông điệp lỗi cho đối tượng Drink
    public static final String NAME_DRINK_NOT_EMPTY = "name.drink.not.empty";
    public static final String NAME_DRINK_NOT_NULL = "name.drink.not.null";
    public static final String NAME_DRINK_MAX_SIZE = "name.drink.max.size";
    public static final String DESCRIPTION_DRINK_MAX_SIZE = "description.drink.max.size";
    public static final String PRICE_DRINK_MUST_GREATER_ZERO = "price.drink.must.greater.zero";

    // Các thông điệp lỗi cho đối tượng Topping
    public static final String NAME_TOPPING_NOT_EMPTY = "name.topping.not.empty";
    public static final String NAME_TOPPING_NOT_NULL = "name.topping.not.null";
    public static final String PRICE_TOPPING_MUST_GREATER_ZERO = "price.topping.must.greater.zero";
}
