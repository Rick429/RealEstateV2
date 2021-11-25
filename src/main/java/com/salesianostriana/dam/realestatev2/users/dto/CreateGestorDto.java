package com.salesianostriana.dam.realestatev2.users.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateGestorDto {

    private String username;
    private String avatar;
    private String name, lastname;
    private String email;
    private String password;
    private String password2;
    private UUID inmobiliariaId;

}
