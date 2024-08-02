package com.example.toppingservice.until.mapper;

import com.example.toppingservice.dto.DrinkDTO;
import com.example.toppingservice.entity.DrinkEntity;
import com.example.toppingservice.exception.NotNullException;
import com.example.toppingservice.until.constants.MessageConstant;

/**
 * Lớp DrinkMapper chứa các phương thức chuyển đổi giữa DTO và Entity của đối tượng Drink.
 */
public class DrinkMapper {

    /**
     * Chuyển đổi từ DrinkDTO sang DrinkEntity.
     * @param drinkDTO DTO của đồ uống.
     * @return Entity tương ứng của đồ uống.
     * @throws NotNullException Ném ngoại lệ nếu DTO là null.
     */
    public static DrinkEntity convertDTOToEntity(DrinkDTO drinkDTO) {
        if (drinkDTO == null){
            throw new NotNullException(MessageConstant.DRINK_NOT_NULL);
        }
        return DrinkEntity.builder()
                .id(drinkDTO.getId())
                .nameDrink(drinkDTO.getNameDrink())
                .description(drinkDTO.getDescription())
                .price(drinkDTO.getPrice())
                .build();
    }

    /**
     * Chuyển đổi từ DrinkEntity sang DrinkDTO.
     * @param drinkEntity Entity của đồ uống.
     * @return DTO tương ứng của đồ uống.
     * @throws NotNullException Ném ngoại lệ nếu Entity là null.
     */
    public static DrinkDTO convertEntityTODTO(DrinkEntity drinkEntity) {
        if (drinkEntity == null){
            throw new NotNullException(MessageConstant.DRINK_NOT_NULL);
        }
        return DrinkDTO.builder()
                .id(drinkEntity.getId())
                .nameDrink(drinkEntity.getNameDrink())
                .description(drinkEntity.getDescription())
                .price(drinkEntity.getPrice())
                .build();
    }

}
