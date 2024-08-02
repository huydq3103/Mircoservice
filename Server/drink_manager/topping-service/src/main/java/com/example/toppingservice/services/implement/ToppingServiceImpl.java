package com.example.toppingservice.services.implement;

import com.example.toppingservice.client.DrinkClient;
import com.example.toppingservice.dto.ToppingDTO;
import com.example.toppingservice.entity.ToppingEntity;
import com.example.toppingservice.exception.NotFoundException;
import com.example.toppingservice.exception.NotNullException;
import com.example.toppingservice.repository.DrinkRepository;
import com.example.toppingservice.repository.ToppingRepository;
import com.example.toppingservice.services.IToppingService;
import com.example.toppingservice.until.constants.MessageConstant;
import com.example.toppingservice.until.mapper.ToppingMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementing class for managing toppings.
 */
@Service
@Slf4j
public class ToppingServiceImpl implements IToppingService {

    @Autowired
    private ToppingRepository toppingRepository;

    @Autowired
    private DrinkClient drinkClient;

    @Autowired
    private MessageServiceImpl messageService;

    /**
     * Xóa một topping dựa trên ID của nó.
     *
     * @param idTopping ID của topping cần xóa.
     * @return ResponseEntity chỉ ra thành công hoặc thất bại của hoạt động.
     * @throws NotFoundException nếu không tìm thấy topping với ID đã cho.
     */
    @Override
    public String deleteTopping(Long idTopping) {

        if(Objects.isNull(idTopping)){
             throw new NotNullException(messageService.getMessage(MessageConstant.TOPPING_ID_NOT_NULL));
        }
        if (!toppingRepository.existsById(idTopping)) {
            throw new NotFoundException(messageService.getMessage(MessageConstant.TOPPING_NOT_FOUND));
        }

        toppingRepository.deleteById(idTopping);
        return MessageConstant.TOPPING_DELETED_SUCCESSFULLY;
    }

    /**
     * Lấy danh sách các topping được liên kết với một đồ uống dựa trên ID của đồ uống.
     *
     * @param id ID của đồ uống để lấy danh sách các topping.
     * @return Danh sách các đối tượng ToppingDTO liên kết với đồ uống.
     * @throws NotFoundException nếu không tìm thấy đồ uống với ID đã cho.
     */
    @Override
    @Transactional
    public List<ToppingDTO> getListToppingByIdDrink(Long id) {

        if(Objects.isNull(id)){
            throw new NotNullException(messageService.getMessage(MessageConstant.DRINK_ID_NOT_NULL));
        }
        if (!drinkClient.existsById(id)) {
            throw new NotFoundException(messageService.getMessage(MessageConstant.DRINK_NOT_FOUND));
        }

        List<ToppingEntity> result = toppingRepository.getToppingEntitiesByDrinkId(id);
        return result.stream()
                .map(ToppingMapper::convertEntityTODTO)
                .collect(Collectors.toList());
    }

    /**
     * Lưu một topping mới.
     *
     * @param toppingDTO Đối tượng ToppingDTO chứa dữ liệu của topping cần lưu.
     * @return Đối tượng ToppingDTO biểu diễn cho topping đã lưu.
     */
    @Override
    public ToppingDTO saveTopping(ToppingDTO toppingDTO) {
        ToppingEntity toppingEntity = toppingRepository.save(ToppingMapper.convertDTOToEntity(toppingDTO));
        return ToppingMapper.convertEntityTODTO(toppingEntity);
    }

    /**
     * Cập nhật một topping đã tồn tại.
     *
     * @param toppingDTO Đối tượng ToppingDTO chứa dữ liệu cập nhật của topping.
     * @return Đối tượng ToppingDTO biểu diễn cho topping sau khi cập nhật.
     * @throws NotFoundException nếu không tìm thấy topping với ID đã cho.
     */
    @Override
    public ToppingDTO updateTopping(ToppingDTO toppingDTO) {

        if(Objects.isNull(toppingDTO.getId())){
            throw new NotNullException(messageService.getMessage(MessageConstant.TOPPING_ID_NOT_NULL));
        }

        if (!toppingRepository.existsById(toppingDTO.getId())) {
            throw new NotFoundException(messageService.getMessage(MessageConstant.TOPPING_NOT_FOUND));
        }
        toppingRepository.save(ToppingMapper.convertDTOToEntity(toppingDTO));
        return toppingDTO;
    }

    /**
     * Lấy danh sách tất cả các topping.
     *
     * @return Danh sách các đối tượng ToppingDTO.
     */
    @Override
    public List<ToppingDTO> getAllTopping() {
        List<ToppingEntity> toppingEntities = toppingRepository.findAll();
        return toppingEntities.stream()
                .map(ToppingMapper::convertEntityTODTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ToppingEntity> findAllById(List<Long> ids) {
       return toppingRepository.findAllById(ids);
    }

}
