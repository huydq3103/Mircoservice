package com.example.toppingservice.until.PriceUtils;

import com.example.toppingservice.until.constants.CommonConstant;

/**
 * Lớp tiện ích để làm tròn giá của đồ uống.
 */
public class PriceUtils {

    /**
     * Phương thức để làm tròn giá của đồ uống về bội số của 100.
     *
     * @param price Giá của đồ uống cần làm tròn
     * @return Giá sau khi đã làm tròn
     */
    public static int roundPrice(int price) {
        // Nếu giá nhỏ hơn 0, trả về 0
        if (price < CommonConstant.ZERO)
            return CommonConstant.ZERO;

        // Làm tròn giá về bội số của 100
        return (price / CommonConstant.ONE_HUNDRED) * CommonConstant.ONE_HUNDRED;
    }
}
