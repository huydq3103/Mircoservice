package com.example.drinkservice.controller;

import com.example.drinkservice.dto.DrinkDTO;
import com.example.drinkservice.services.implement.DrinkServiceImpl;
import com.example.drinkservice.until.constants.ApiResponseMessages;
import com.example.drinkservice.until.constants.CommonConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/drinks")
@Validated
/**
 * Controller quản lý các API của đồ uống
 */
public class DrinkController {

    @Autowired
    private DrinkServiceImpl drinkService;

    /**
     * API tạo mới một đối tượng đồ uống.
     *
     * @param drinkDTO Đối tượng DTO của đồ uống để tạo mới.
     * @return ResponseEntity chứa thông tin về đối tượng đồ uống đã được tạo và HttpStatus.CREATED nếu thành công.
     */
    @Operation(summary = "Create a new drink", description = "API tạo mới một đối tượng đồ uống")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ApiResponseMessages.CREATED,
                    description = ApiResponseMessages.CREATED_DESC),
            @ApiResponse(responseCode = ApiResponseMessages.BAD_REQUEST,
                    description = ApiResponseMessages.BAD_REQUEST_DESC),
            @ApiResponse(responseCode = ApiResponseMessages.INTERNAL_SERVER_ERROR,
                    description = ApiResponseMessages.INTERNAL_SERVER_ERROR_DESC)
    })
    @PostMapping("/create-drink")
    public ResponseEntity<?> createDrink(
            @Parameter(description = "DTO của đồ uống để tạo mới", required = true)
            @RequestBody @Valid DrinkDTO drinkDTO) {
        return new ResponseEntity<>(drinkService.createDrink(drinkDTO), HttpStatus.CREATED);
    }

    /**
     * API cập nhật thông tin của một đồ uống.
     *
     * @param drinkDTO Đối tượng DTO của đồ uống để cập nhật thông tin.
     * @return ResponseEntity chứa thông tin về đối tượng đồ uống đã được cập nhật và HttpStatus.OK nếu thành công.
     */
    @Operation(summary = "Update an existing drink",
            description = "API cập nhật thông tin của một đồ uống")
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
    @PutMapping("/update-drink")
    public ResponseEntity<?> updateDrink(
            @Parameter(description = "DTO của đồ uống để cập nhật thông tin", required = true)
            @RequestBody @Valid DrinkDTO drinkDTO) {
        return ResponseEntity.ok(drinkService.updateDrink(drinkDTO));
    }

    /**
     * API lấy danh sách các đồ uống.
     *
     * @param page Trang hiện tại (mặc định là 0 nếu không có giá trị).
     * @param size Số lượng bản ghi trên mỗi trang (mặc định là 10 nếu không có giá trị).
     * @return ResponseEntity chứa danh sách các đồ uống trên trang hiện tại và HttpStatus.OK nếu thành công.
     */
    @Operation(summary = "Get all drinks with pagination",
            description = "API lấy danh sách các đồ uống với phân trang")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ApiResponseMessages.OK,
                    description = ApiResponseMessages.OK_DESC),
            @ApiResponse(responseCode = ApiResponseMessages.BAD_REQUEST,
                    description = ApiResponseMessages.BAD_REQUEST_DESC),
            @ApiResponse(responseCode = ApiResponseMessages.INTERNAL_SERVER_ERROR,
                    description = ApiResponseMessages.INTERNAL_SERVER_ERROR_DESC)
    })
    @GetMapping("/get-all-drink")
    public ResponseEntity<?> getAllDrinks(
            @Parameter(description = "Trang hiện tại"
                    , example = CommonConstant.DEFAULT_PAGE, required = false)
            @RequestParam(defaultValue = CommonConstant.DEFAULT_PAGE) int page,
            @Parameter(description = "Số lượng bản ghi trên mỗi trang"
                    , example = CommonConstant.DEFAULT_SIZE, required = false)
            @RequestParam(defaultValue = CommonConstant.DEFAULT_SIZE) int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<DrinkDTO> drinkDTOS = drinkService.getDrinks(pageable);

        return new ResponseEntity<>(drinkDTOS.getContent(), HttpStatus.OK);
    }

    /**
     * API xóa một đồ uống dựa trên ID.
     *
     * @param id ID của đồ uống cần xóa.
     * @return ResponseEntity chứa thông tin về việc xóa đồ uống và HttpStatus tương ứng.
     */
    @Operation(summary = "Delete drink by ID", description = "API xóa một đồ uống dựa trên ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ApiResponseMessages.OK,
                    description = ApiResponseMessages.OK_DESC),
            @ApiResponse(responseCode = ApiResponseMessages.NOT_FOUND,
                    description = ApiResponseMessages.NOT_FOUND_DESC),
            @ApiResponse(responseCode = ApiResponseMessages.INTERNAL_SERVER_ERROR,
                    description = ApiResponseMessages.INTERNAL_SERVER_ERROR_DESC),
            @ApiResponse(responseCode = ApiResponseMessages.BAD_REQUEST,
                    description = ApiResponseMessages.BAD_REQUEST_DESC),
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDrink(
            @Parameter(description = "ID của đồ uống cần xóa", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(drinkService.deleteDrink(id));
    }


    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        boolean exists = drinkService.existsById(id);
        return ResponseEntity.ok(exists);
    }

}
