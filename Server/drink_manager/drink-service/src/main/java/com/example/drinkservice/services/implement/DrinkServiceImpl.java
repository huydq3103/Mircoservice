package com.example.drinkservice.services.implement;

import com.example.drinkservice.client.ToppingClient;
import com.example.drinkservice.dto.DrinkDTO;
import com.example.drinkservice.entity.DrinkEntity;
import com.example.drinkservice.entity.ToppingEntity;
import com.example.drinkservice.exception.NotFoundException;
import com.example.drinkservice.exception.NotNullException;
import com.example.drinkservice.repository.DrinkRepository;
import com.example.drinkservice.services.IDrinkService;
import com.example.drinkservice.until.constants.MessageConstant;
import com.example.drinkservice.until.mapper.DrinkMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
/**
 * Implementations of Drink Service
 */
public class DrinkServiceImpl implements IDrinkService {

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private ToppingClient toppingClient;

    @Autowired
    private MessageServiceImpl messageService;

    @Override
    @Transactional
    public DrinkDTO createDrink(DrinkDTO drinkDTO) {
        if (Objects.isNull(drinkDTO)) {
            throw new NotNullException(messageService.getMessage(MessageConstant.DRINK_NOT_NULL));
        }
        if (Objects.isNull(drinkDTO.getListIds())) {
            throw new NotNullException(messageService.getMessage(MessageConstant.LIST_TOPPING_NOT_NULL));
        }

        DrinkEntity drinkEntity = DrinkMapper.convertDTOToEntity(drinkDTO);
        drinkEntity.setCreateDate(LocalDate.now()); // Thiết lập ngày tạo là ngày hiện tại

        List<ToppingEntity> toppingEntities = findAllToppingsById(drinkDTO.getListIds());
        drinkEntity.setToppings(toppingEntities);

        drinkEntity = drinkRepository.save(drinkEntity);

        DrinkDTO result = DrinkMapper.convertEntityTODTO(drinkEntity);
        result.setListIds(drinkDTO.getListIds());
        return result;
    }

    @Override
    public Page<DrinkDTO> getDrinks(Pageable pageable) {
        Page<DrinkEntity> drinkEntities = drinkRepository.findAll(pageable);
        return drinkEntities.map(DrinkMapper::convertEntityTODTO);
    }

    @Override
    @Transactional
    public DrinkDTO updateDrink(DrinkDTO drinkDTO) {
        if (Objects.isNull(drinkDTO)) {
            throw new NotNullException(messageService.getMessage(MessageConstant.DRINK_NOT_NULL));
        }
        if (Objects.isNull(drinkDTO.getId())) {
            throw new NotNullException(messageService.getMessage(MessageConstant.DRINK_ID_NOT_NULL));
        }

        DrinkEntity drinkEntity = drinkRepository.findById(drinkDTO.getId())
                .orElseThrow(() -> new NotFoundException(messageService.getMessage(MessageConstant.DRINK_NOT_FOUND)));

        drinkEntity.setNameDrink(drinkDTO.getNameDrink());
        drinkEntity.setDescription(drinkDTO.getDescription());

        drinkEntity.setToppings(findAllToppingsById(drinkDTO.getListIds()));
        drinkRepository.save(drinkEntity);

        return drinkDTO;
    }

    @Override
    @Transactional
    public String deleteDrink(Long id) {
        if (Objects.isNull(id)) {
            throw new NotNullException(messageService.getMessage(MessageConstant.DRINK_ID_NOT_NULL));
        }
        if (!drinkRepository.existsById(id)) {
            throw new NotFoundException(messageService.getMessage(MessageConstant.DRINK_NOT_FOUND));
        }

        drinkRepository.deleteById(id);
        return messageService.getMessage(MessageConstant.DRINK_DELETED_SUCCESSFULLY);
    }

    @Override
    public boolean existsById(Long id) {
        return drinkRepository.existsById(id);
    }

    @CircuitBreaker(name = "toppingService", fallbackMethod = "findAllByIdFallback")
    public List<ToppingEntity> findAllToppingsById(List<Long> ids) {
        return toppingClient.findAllById(ids);
    }

    public List<ToppingEntity> findAllByIdFallback(List<Long> ids, Throwable throwable) {
        log.error("Error calling topping-service", throwable);
        return Collections.emptyList();
    }
}
