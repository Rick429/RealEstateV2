package com.salesianostriana.dam.realestatev2.users.controller;

import com.salesianostriana.dam.realestatev2.users.dto.CreateUserDto;
import com.salesianostriana.dam.realestatev2.users.dto.GetUserDto;
import com.salesianostriana.dam.realestatev2.users.dto.GetUserLoginDto;
import com.salesianostriana.dam.realestatev2.users.dto.UserDtoConverter;
import com.salesianostriana.dam.realestatev2.users.model.UserEntity;
import com.salesianostriana.dam.realestatev2.users.model.UserRole;
import com.salesianostriana.dam.realestatev2.users.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;

    @PostMapping("/auth/register/{role}")
    public ResponseEntity<GetUserDto> nuevoUsuario(@RequestBody CreateUserDto newUser, @PathVariable String role) {
        UserRole userRole = UserRole.valueOf(role.toUpperCase());
        UserEntity saved = userEntityService.save(newUser, userRole);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUserEntityToGetUserDto(saved));
    }



}
