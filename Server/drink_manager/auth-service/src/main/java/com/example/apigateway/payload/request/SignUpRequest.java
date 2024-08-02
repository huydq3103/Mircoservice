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
public class SignUpRequest {

    @NotEmpty(message = ValidateConstant.USER_NAME_NOT_EMPTY)
    @NotNull(message = ValidateConstant.USER_NAME_NOT_NULL)
    private String username;

    @NotEmpty(message = ValidateConstant.PASSWORD_NOT_EMPTY)
    @NotNull(message = ValidateConstant.USER_NAME_NOT_NULL)
    private String password;

}
