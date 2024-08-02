package com.example.toppingservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "drink-service")
public interface DrinkClient {

    @GetMapping("/api/drinks/exists/{id}")
    boolean existsById(@PathVariable("id") Long id);
}
