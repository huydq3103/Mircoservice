package com.example.drinkservice.services;


import com.example.drinkservice.dto.DrinkDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDrinkService {

    DrinkDTO createDrink(DrinkDTO drinkDTO);

    Page<DrinkDTO> getDrinks(Pageable pageable);

    DrinkDTO updateDrink(DrinkDTO drinkDTO);

    String deleteDrink(Long id);

    boolean existsById(Long id);

}
