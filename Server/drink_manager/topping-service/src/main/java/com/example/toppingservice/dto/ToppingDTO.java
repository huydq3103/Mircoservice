package com.example.toppingservice.dto;

import com.example.toppingservice.until.PriceUtils.PriceUtils;
import com.example.toppingservice.until.constants.ValidateConstant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * DTO (Data Transfer Object) cho các thông tin về topping.
 * Bao gồm các thuộc tính như id, tên và giá của topping.
 */
public class ToppingDTO {


    private Long id;

    // Tên của topping không được để trống
    @NotEmpty(message = ValidateConstant.NAME_TOPPING_NOT_EMPTY)
    private String name;

    // Giá của topping phải lớn hơn hoặc bằng 0
    @PositiveOrZero(message = ValidateConstant.PRICE_TOPPING_MUST_GREATER_ZERO)
    private int price;

    /**
     * Thiết lập giá của topping và làm tròn giá thành số nguyên.
     *
     * @param price Giá của topping
     */
    public void setPrice(int price) {
        this.price = PriceUtils.roundPrice(price);
    }


}
