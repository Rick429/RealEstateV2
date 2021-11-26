package com.salesianostriana.dam.realestatev2.dto;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetInteresaPropietarioDto {

    private UUID idVivienda;
    private String mensaje;
}
