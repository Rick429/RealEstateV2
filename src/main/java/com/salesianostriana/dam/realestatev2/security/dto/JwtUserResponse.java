package com.salesianostriana.dam.realestatev2.security.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class JwtUserResponse {

    private String email;
    private String name, lastname;
    private String avatar;
    private String role;
    private String token;

}