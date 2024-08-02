package com.example.apigateway.payload.request;

import com.example.apigateway.until.constants.ValidateConstant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionRequest {
    @NotEmpty(message = ValidateConstant.PERMISSION_NOT_EMPTY)
    @NotNull(message = ValidateConstant.PERMISSION_NOT_NULL)
    private String permissionName;

}
