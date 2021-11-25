package com.salesianostriana.dam.realestatev2.users.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserDto {

    private UUID id;
    private String avatar;
    private String name, lastname;
    private String email;
    private String role;


}
