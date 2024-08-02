package com.example.drinkservice.client;

import com.example.drinkservice.entity.ToppingEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "topping-service")
public interface ToppingClient {

    @GetMapping("/api/toppings/findAllById")
    List<ToppingEntity> findAllById(@RequestParam("ids") List<Long> ids);
}