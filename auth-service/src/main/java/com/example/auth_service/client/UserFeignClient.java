package com.example.auth_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import com.example.auth_service.dto.UserCredentialsDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserFeignClient {
    
    @GetMapping("/users/email/{email}")
    UserCredentialsDTO getUserByEmail(@PathVariable("email") String email);
}