package com.example.toppingservice.until.mapper;

import com.example.toppingservice.dto.ToppingDTO;
import com.example.toppingservice.entity.ToppingEntity;

/**
 * Lớp ToppingMapper chứa các phương thức chuyển đổi giữa DTO và Entity của đối tượng Topping.
 */
public class ToppingMapper {

    /**
     * Chuyển đổi từ ToppingDTO sang ToppingEntity.
     * @param toppingDTO DTO của topping.
     * @return Entity tương ứng của topping.
     */
    public static ToppingEntity convertDTOToEntity(ToppingDTO toppingDTO){
        if (toppingDTO == null){
            return null;
        }
        ToppingEntity toppingEntity = ToppingEntity.builder()
                .id(toppingDTO.getId())
                .name(toppingDTO.getName())
                .price(toppingDTO.getPrice())
                .build();
        return toppingEntity;
    }

    /**
     * Chuyển đổi từ ToppingEntity sang ToppingDTO.
     * @param toppingEntity Entity của topping.
     * @return DTO tương ứng của topping.
     */
    public static ToppingDTO convertEntityTODTO(ToppingEntity toppingEntity){
        if (toppingEntity == null){
            return null;
        }
        ToppingDTO toppingDTO = ToppingDTO.builder()
                .id(toppingEntity.getId())
                .price(toppingEntity.getPrice())
                .name(toppingEntity.getName())
                .build();
        return toppingDTO;
    }
}
