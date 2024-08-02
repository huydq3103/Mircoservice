package com.example.apigateway.client;

import com.example.apigateway.payload.response.AuthResponse;
import feign.FeignException;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


@FeignClient(name = "auth-service")
public interface AuthServiceClient {


    @GetMapping("/api/auth/validateToken")
    AuthResponse validateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    default Mono<AuthResponse> validateTokenReactive(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return Mono.fromCallable(() -> {
            try {
                System.out.println("Validating token: " + token);
                AuthResponse response = validateToken("Bearer  " + token);
                System.out.println("Token validated successfully: " + response);
                return response;
            } catch (FeignException e) {
                System.out.println("Error validating token: " + e.getMessage());
                throw e;
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
