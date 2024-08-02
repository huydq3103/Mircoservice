package com.example.drinkservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.example.drinkservice.until.PriceUtils.PriceUtils;
import com.example.drinkservice.until.constants.CommonConstant;
import com.example.drinkservice.until.constants.EntityConstant;
import com.example.drinkservice.until.constants.ValidateConstant;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = EntityConstant.DRINK_NAME_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
/**
 * class chứa các thuộc tính của dồ uống
 */
public class DrinkEntity {

    /**
     * ID của đồ uống.
     *
     * Được tự động sinh ra bằng chiến lược GenerationType.IDENTITY.
     * Sử dụng @JsonIgnore để tránh trường hợp set ID từ bên ngoài.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE) // tránh trường hợp set id
    private Long id;

    /**
     * Tên của đồ uống.
     *
     * Không được để trống và không được null.
     * Độ dài tối đa là 50 ký tự.
     * Cột được định nghĩa với kiểu VARCHAR(50).
     */
    @NotEmpty(message = ValidateConstant.NAME_DRINK_NOT_EMPTY)
    @Size(max = 50, message = ValidateConstant.NAME_DRINK_MAX_SIZE)
    @Column(columnDefinition = CommonConstant.VARCHAR_50)
    private String nameDrink;

    /**
     * Mô tả về đồ uống.
     *
     * Độ dài tối đa là 1000 ký tự.
     * Cột được định nghĩa với kiểu LONGTEXT.
     */
    @Size(max = 1000, message = ValidateConstant.DESCRIPTION_DRINK_MAX_SIZE)
    @Column(columnDefinition = CommonConstant.LONG_TEXT)
    private String description;

    /**
     * Giá của đồ uống, phải lớn hơn hoặc bằng 0.
     *
     * Giá trị nhỏ nhất là 0.
     * Cột được định nghĩa với kiểu INT.
     */
    @Min(value = 0, message = ValidateConstant.PRICE_DRINK_MUST_GREATER_ZERO)
    @Column(columnDefinition = CommonConstant.INT)
    private int price;

    /**
     * Ngày tạo đồ uống.
     *
     * Được định dạng theo mẫu định dạng ngày tháng chung.
     */
    @Column
    @JsonFormat(pattern = CommonConstant.DATE_FORMAT)
    private LocalDate createDate;



    /**
     * Setter cho giá của topping, làm tròn giá trị sử dụng phương thức roundPrice.
     * @param price Giá của topping trước khi làm tròn
     */
    public void setPrice(int price) {
        this.price = PriceUtils.roundPrice(price);
    }


    /**
     * Danh sách các topping của đồ uống.
     *
     * Tạo mối quan hệ nhiều-nhiều (Many-to-Many) giữa đồ uống và topping.
     * Sử dụng chế độ tải dữ liệu lười biếng (LAZY) để chỉ tải dữ liệu khi cần thiết.
     * Bảng trung gian được định nghĩa bởi {@link EntityConstant#DRINK_TOPPING_TABLE}.
     * Cột liên kết với đồ uống được định nghĩa bởi {@link EntityConstant#DRINK_ENTITY_ID}.
     * Cột liên kết với topping được định nghĩa bởi {@link EntityConstant#TOPPING_ID}.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = EntityConstant.DRINK_TOPPING_TABLE,
            joinColumns = @JoinColumn(name = EntityConstant.DRINK_ENTITY_ID),
            inverseJoinColumns = @JoinColumn(name = EntityConstant.TOPPING_ID))
    private List<ToppingEntity> toppings;


}
