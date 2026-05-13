package com.example.user_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "account-service")
public interface AccountFeignClient {

    @PostMapping("/accounts")
    Object createAccount(@RequestParam("userId") Long userId);
}