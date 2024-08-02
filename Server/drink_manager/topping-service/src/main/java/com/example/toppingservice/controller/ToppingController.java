package com.example.toppingservice.controller;

import com.example.toppingservice.dto.ToppingDTO;
import com.example.toppingservice.entity.ToppingEntity;
import com.example.toppingservice.services.implement.ToppingServiceImpl;
import com.example.toppingservice.until.constants.ApiResponseMessages;
import com.example.toppingservice.until.constants.CommonConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/toppings")
/**
 * Controller quản lý các API  của Topping.
 */
@Validated
public class ToppingController {

    @Autowired
    private ToppingServiceImpl toppingService;

    /**
     * API để thêm mới một topping.
     *
     * @param toppingDTO Đối tượng DTO của topping để thêm mới.
     * @return ResponseEntity chứa thông tin về topping đã được lưu và HttpStatus.CREATED nếu thành công.
     */
    @Operation(summary = "Create new topping", description = "API để thêm mới một topping")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ApiResponseMessages.CREATED,
                    description = ApiResponseMessages.CREATED_DESC),
            @ApiResponse(responseCode = ApiResponseMessages.BAD_REQUEST,
                    description = ApiResponseMessages.BAD_REQUEST_DESC),
            @ApiResponse(responseCode = ApiResponseMessages.INTERNAL_SERVER_ERROR,
                    description = ApiResponseMessages.INTERNAL_SERVER_ERROR_DESC)
    })
    @PostMapping("/create")
    public ResponseEntity<?> addTopping(
            @Parameter(description = "DTO của topping để thêm mới", required = true)
            @RequestBody @Valid ToppingDTO toppingDTO) {
        return new ResponseEntity<>(toppingService.saveTopping(toppingDTO), HttpStatus.CREATED);
    }

    /**
     * API để cập nhật thông tin của một topping.
     *
     * @param toppingDTO Đối tượng DTO của topping để cập nhật thông tin.
     * @return ResponseEntity chứa thông tin về topping đã được cập nhật và HttpStatus.OK nếu thành công.
     */
    @Operation(summary = "Update an existing topping",
            description = "API để cập nhật thông tin của một topping")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ApiResponseMessages.OK,
                    description = ApiResponseMessages.OK_DESC),
            @ApiResponse(responseCode = ApiResponseMessages.BAD_REQUEST,
                    description = ApiResponseMessages.BAD_REQUEST_DESC),
            @ApiResponse(responseCode = ApiResponseMessages.NOT_FOUND,
                    description = ApiResponseMessages.NOT_FOUND_DESC),
            @ApiResponse(responseCode = ApiResponseMessages.INTERNAL_SERVER_ERROR,
                    description = ApiResponseMessages.INTERNAL_SERVER_ERROR_DESC)
    })
    @PutMapping("/update")
    public ResponseEntity<?> updateTopping(
            @Parameter(description = "DTO của topping để cập nhật thông tin", required = true)
            @RequestBody @Valid ToppingDTO toppingDTO) {
        return new ResponseEntity<>(toppingService.updateTopping(toppingDTO), HttpStatus.OK);
    }

    /**
     * API để lấy danh sách các topping của một đồ uống dựa trên ID của đồ uống.
     *
     * @param drinkID ID của đồ uống để lấy danh sách topping.
     * @return ResponseEntity chứa danh sách các topping và HttpStatus.OK nếu thành công.
     */
    @Operation(summary = "Get list of toppings by drink ID",
            description = "API để lấy danh sách các topping của một đồ uống")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ApiResponseMessages.OK,
                    description = ApiResponseMessages.OK_DESC),
            @ApiResponse(responseCode = ApiResponseMessages.BAD_REQUEST,
                    description = ApiResponseMessages.BAD_REQUEST_DESC),
            @ApiResponse(responseCode = ApiResponseMessages.INTERNAL_SERVER_ERROR,
                    description = ApiResponseMessages.INTERNAL_SERVER_ERROR_DESC)
    })
    @GetMapping("/get-list-topping")
    public ResponseEntity<?> getListTopping(
            @Parameter(description = "ID của đồ uống để lấy danh sách topping", required = true)
            @RequestParam Long drinkID) {
        return new ResponseEntity<>(toppingService.getListToppingByIdDrink(drinkID), HttpStatus.OK);
    }

    /**
     * API để lấy danh sách tất cả các topping có trong hệ thống.
     *
     * @return ResponseEntity chứa danh sách tất cả các topping và HttpStatus.OK nếu thành công.
     */
    @Operation(summary = "Get all toppings",
            description = "API để lấy danh sách tất cả các topping có trong hệ thống")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ApiResponseMessages.OK,
                    description = ApiResponseMessages.OK_DESC),
            @ApiResponse(responseCode = ApiResponseMessages.INTERNAL_SERVER_ERROR,
                    description = ApiResponseMessages.INTERNAL_SERVER_ERROR_DESC)
    })
    @GetMapping("/get-all-topping")
    public ResponseEntity<?> getAllTopping() {
        return new ResponseEntity<>(toppingService.getAllTopping(), HttpStatus.OK);
    }

    /**
     * API để xóa một topping dựa trên ID.
     *
     * @param id ID của topping cần xóa.
     * @return ResponseEntity chứa thông báo xóa thành công hoặc thất bại và HttpStatus tương ứng.
     */
    @Operation(summary = "Delete topping by ID", description = "API để xóa một topping dựa trên ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ApiResponseMessages.OK,
                    description = ApiResponseMessages.OK_DESC),
            @ApiResponse(responseCode = ApiResponseMessages.NOT_FOUND,
                    description = ApiResponseMessages.NOT_FOUND_DESC),
            @ApiResponse(responseCode = ApiResponseMessages.INTERNAL_SERVER_ERROR,
                    description = ApiResponseMessages.INTERNAL_SERVER_ERROR_DESC),
            @ApiResponse(responseCode = ApiResponseMessages.BAD_REQUEST,
                    description = ApiResponseMessages.BAD_REQUEST_DESC)
    })
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteTopping(
            @Parameter(description = "ID của topping cần xóa", required = true)
            @PathVariable(CommonConstant.ID) Long id) {
        return new ResponseEntity<>(toppingService.deleteTopping(id), HttpStatus.OK);
    }


    @GetMapping("/findAllById")
    public ResponseEntity<List<ToppingEntity>> findAllById(
            @RequestParam(name = "ids", required = true) List<Long> ids) {
        return ResponseEntity.ok(toppingService.findAllById(ids));
    }


}
