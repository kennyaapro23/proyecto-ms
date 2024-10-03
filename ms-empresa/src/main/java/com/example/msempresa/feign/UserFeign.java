package com.example.msempresa.feign;


import com.example.msempresa.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


@FeignClient(name = "ms-user-service", path = "/user")
public interface UserFeign {

    @GetMapping("/{id}")
    Optional<UserDto> getById(@PathVariable Integer id); // Cambiar a Optional<UserDto>
}