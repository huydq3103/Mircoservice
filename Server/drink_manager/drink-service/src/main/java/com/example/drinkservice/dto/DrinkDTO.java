package com.example.drinkservice.dto;

import com.example.drinkservice.until.constants.ValidateConstant;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.example.drinkservice.until.PriceUtils.PriceUtils.roundPrice;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/**
 * DTO (Data Transfer Object) cho các thông tin về đồ uống.
 * Bao gồm các thuộc tính như id, tên, mô tả, giá và danh sách các ID của topping liên quan.
 */
public class DrinkDTO {

    private Long id;

    @NotEmpty(message = ValidateConstant.NAME_DRINK_NOT_EMPTY)
    @Size(max = 50, message = ValidateConstant.NAME_DRINK_MAX_SIZE)
    private String nameDrink;


    @Size(max = 1000, message = ValidateConstant.DESCRIPTION_DRINK_MAX_SIZE)
    private String description;

    @Min(value = 0, message = ValidateConstant.PRICE_DRINK_MUST_GREATER_ZERO)
    private int price;

    private List<Long> listIds;

    /**
     * Thiết lập giá của đồ uống và làm tròn giá thành số nguyên.
     *
     * @param price Giá của đồ uống
     */
    public void setPrice(int price) {
        this.price = roundPrice(price);
    }


}
