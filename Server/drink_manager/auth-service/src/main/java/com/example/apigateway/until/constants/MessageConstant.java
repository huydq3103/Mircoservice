package com.example.apigateway.until.constants;

/**
 * Định nghĩa các hằng số thông điệp cho các trường hợp trong ứng dụng.
 * Các hằng số này được sử dụng để tránh hard-code các thông điệp trong mã nguồn.
 */
public class MessageConstant {

    // Thông điệp khi một đồ uống đã tồn tại trong hệ thống
    public static final String DRINK_ALREADY_EXISTS = "drink.already.exists";

    // Thông điệp khi có lỗi xảy ra trong quá trình tạo đồ uống
    public static final String ERROR_CREATING_DRINK = "error.creating.drink";

    // Thông điệp khi có lỗi xảy ra trong quá trình cập nhật đồ uống
    public static final String ERROR_UPDATING_DRINK = "error.updating.drink";

    // Thông điệp khi không tìm thấy đồ uống
    public static final String DRINK_NOT_FOUND = "drink.not.found";

    // Thông điệp khi xóa đồ uống thành công
    public static final String DRINK_DELETED_SUCCESSFULLY = "drink.deleted.successfully";

    // Thông điệp khi có lỗi xảy ra trong quá trình xóa đồ uống
    public static final String ERROR_DELETING_DRINK = "error.deleting.drink";

    // Thông điệp khi không tìm thấy topping
    public static final String TOPPING_NOT_FOUND = "topping.not.found";

    // Thông điệp khi đồ uống không được null
    public static final String DRINK_NOT_NULL = "drink.not.null";

    // Thông điệp khi xóa topping thành công
    public static final String TOPPING_DELETED_SUCCESSFULLY = "topping.deleted.successfully";

    //Thông điệp đổi ngôn ngữ thành công
    public static final String LANGUAGE_UPDATED_SUCCESSFULLY = "hello";

    public static final String LIST_TOPPING_NOT_NULL = "not.null.list.topping";

    public static final String DRINK_ID_NOT_NULL = "not.null.drink";

    public static final String TOPPING_ID_NOT_NULL = "not.null.topping";

}
