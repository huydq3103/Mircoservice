package com.example.toppingservice.services;

import com.example.toppingservice.dto.ToppingDTO;
import com.example.toppingservice.entity.ToppingEntity;
import com.example.toppingservice.exception.NotFoundException;

import java.util.List;

public interface IToppingService {

    String deleteTopping(Long idTopping);

    List<ToppingDTO> getListToppingByIdDrink(Long id) throws NotFoundException;

    ToppingDTO saveTopping(ToppingDTO toppingDTO);

    ToppingDTO updateTopping(ToppingDTO toppingDTO) throws NotFoundException;

    List<ToppingDTO> getAllTopping();

    List<ToppingEntity> findAllById(List<Long> ids);

}
