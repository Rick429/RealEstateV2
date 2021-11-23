package com.salesianostriana.dam.realestatev2.users.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetInteresadoDto {

    private UUID id;
    private String nombre, apellidos, direccion, email, telefono, avatar, mensaje;
    private LocalDateTime createdDate;

}
