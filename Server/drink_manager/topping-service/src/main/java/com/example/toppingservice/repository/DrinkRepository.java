package com.example.toppingservice.repository;

import com.example.toppingservice.entity.DrinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface để tương tác với đối tượng DrinkEntity trong cơ sở dữ liệu.
 */
@Repository
public interface DrinkRepository extends JpaRepository<DrinkEntity, Long> {

    /**
     * Tìm kiếm một đối tượng DrinkEntity bằng tên đồ uống.
     *
     * @param name Tên đồ uống cần tìm kiếm
     * @return Optional chứa đối tượng DrinkEntity nếu tồn tại, hoặc Optional rỗng nếu không tìm thấy
     */
    Optional<DrinkEntity> findByNameDrink(String name);

}
