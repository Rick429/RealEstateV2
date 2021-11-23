package com.salesianostriana.dam.realestatev2.users.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserDto {

    private String avatar;
    private String name, lastname;
    private String email;
    private String role;


}
