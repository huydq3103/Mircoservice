package com.example.toppingservice.repository;

import com.example.toppingservice.entity.ToppingEntity;
import com.example.toppingservice.until.constants.EntityConstant;
import com.example.toppingservice.until.constants.ProcedureNameConstant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface để tương tác với đối tượng ToppingEntity trong cơ sở dữ liệu.
 */
@Repository
public interface ToppingRepository extends JpaRepository<ToppingEntity, Long> {

    /**
     * Gọi procedure lấy danh sách các topping dựa trên ID của đồ uống.
     *
     * @param id ID của đồ uống để lấy danh sách topping tương ứng
     * @return Danh sách các entity ToppingEntity
     */
    @Procedure(value = ProcedureNameConstant.GET_TOPPING_BY_DRINK)
    List<ToppingEntity> getToppingEntitiesByDrinkId(@Param(EntityConstant.DRINK_ENTITY_ID) Long id);
}
